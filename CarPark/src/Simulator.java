import java.util.Random;
import java.util.*;
import javax.swing.*;
import java.awt.*;

public class Simulator {

    private CarQueue entranceCarQueue;
    private CarQueue paymentCarQueue;
    private CarQueue exitCarQueue;
    private SimulatorView simulatorView;
    private LocationManager locman;
    private ArrayList<AbstractView> views;
    
    //parking cost per hour in Euro's
    private int parkingCostPH = 2;
    
    private int day = 0;
    private int hour = 0;
    private int minute = 0;
    
    private int stepsToDo = 0;

    private int tickPause = 100;

    int numberOfFloors = 3;
    int numberOfRows = 6;
    int numberOfPlaces = 30;
    
    int weekDayArrivals= 50; // average number of arriving cars per hour
    int weekendArrivals = 90; // average number of arriving cars per hour

    int enterSpeed = 3; // number of cars that can enter per minute
    int paymentSpeed = 5; // number of cars that can pay per minute
    int exitSpeed = 9; // number of cars that can leave per minute
    
    double passHolderRatio = 0.1;
    double reservationCarRatio = 0.1;
    
    
    public Simulator() {
        entranceCarQueue = new CarQueue();
        paymentCarQueue = new CarQueue();
        exitCarQueue = new CarQueue();
        locman = new LocationManager(this);
        stepsToDo += 100;
        simulatorView = new SimulatorView(this);
        views = new ArrayList<AbstractView>();
        views.add(new statisticsView(new Dimension(480,480),this));
        
    }

    public void run() {
    	
        while(true) {
        	if(stepsToDo>0){
        		tick();
        		stepsToDo--;
        	}else{
        		
                    try {
						Thread.sleep(tickPause);
					} catch (InterruptedException e) {
						System.out.println("sleep failed");
						e.printStackTrace();
					}
               
        	}
        }
    }
    
    public LocationManager getLocations(){
    	return locman;
    }
    
    public int getRemainingRevenue(){
    	int remainingRev;
    	
    	for(Car tmpCar : locman.getCars()) {
    		remainingRev += (tmpCar.getMinutesLeft() * parkingCostPH);
    	}
    	return 0;
    }
    public int getNumberOfFloors(){
    	return numberOfFloors;
    }
    public int getNumberOfRows(){
    	return numberOfRows;
    }
    public int getNumberOfPlaces(){
    	return numberOfPlaces;
    }
    public int getStepsToDo(){
    	return stepsToDo;
    }
    
   
    public void doOneStep(){
    	stepsToDo++;
    }
    
    public void doTenSteps(){
    	stepsToDo +=10;
    }
    
    public void doHundredSteps(){
    	stepsToDo += 100;
    }

    private void tick() {
        // Advance the time by one minute.
        minute++;
        while (minute > 59) {
            minute -= 60;
            hour++;
        }
        while (hour > 23) {
            hour -= 24;
            day++;
        }
        while (day > 6) {
            day -= 7;
        }

        Random random = new Random();

        // Get the average number of cars that arrive per hour.
        int averageNumberOfCarsPerHour = day < 5
                ? weekDayArrivals
                : weekendArrivals;

        // Calculate the number of cars that arrive this minute.
        double standardDeviation = averageNumberOfCarsPerHour * 0.1;
        double numberOfCarsPerHour = averageNumberOfCarsPerHour + random.nextGaussian() * standardDeviation;
        int numberOfCarsPerMinute = (int)Math.round(numberOfCarsPerHour / 60);

        // Add the cars to the back of the queue.
        for (int i = 0; i < numberOfCarsPerMinute; i++) {
        	Car car = new AdHocCar();;
        	double carGen = random.nextDouble();
        	if(carGen<=passHolderRatio+reservationCarRatio){
        		if(carGen<=passHolderRatio){
        			car = new PassHolderCar(random.nextInt(10000000));
        		}
        		if(carGen<=passHolderRatio+reservationCarRatio && carGen>passHolderRatio){
        			car = new ReservationCar(true);
        		}
        	}else{
        		car = new AdHocCar();
        	}
        	
            if(car!=null){
            	entranceCarQueue.addCar(car);
            }
            
        }

        // Remove car from the front of the queue and assign to a parking space.
        for (int i = 0; i < enterSpeed; i++) {
            Car car = entranceCarQueue.removeCar();
            if (car == null) {
                break;
            }

            Location freeLocation = locman.getFirstFreeLocation();
            if (freeLocation != null) {
                locman.setCarAt(freeLocation, car);
                int stayMinutes = (int) (15 + random.nextFloat() * 10 * 60);
                car.setMinutesLeft(stayMinutes);
            }
        }

        // Perform car park tick.
        locman.tick();

        // Add leaving cars to the exit queue.
        while (true) {
            Car car = locman.getFirstLeavingCar();
            if (car == null) {
                break;
            }
            
            car.setIsPaying(true);
            
            if(car instanceof PassHolderCar){
            	exitCarQueue.addCar(car);
            }else{
            	paymentCarQueue.addCar(car);
            }
            
        }

        // Let cars pay.
        for (int i = 0; i < paymentSpeed; i++) {
            Car car = paymentCarQueue.removeCar();
            if (car == null) {
                break;
            }
            
            exitCarQueue.addCar(car);
        }

        // Let cars leave.
        for (int i = 0; i < exitSpeed; i++) {
            Car car = exitCarQueue.removeCar();
            if (car == null) {
                break;
            }
            locman.removeCarAt(car.getLocation());
            // Bye!
        }

        // Update the car park view.
        simulatorView.updateView();

        // Pause.
        try {
            Thread.sleep(tickPause);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
