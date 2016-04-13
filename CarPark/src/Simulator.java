import java.util.Random;
import java.util.concurrent.locks.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;

public class Simulator {

    private CarQueue entranceCarQueue;
    private CarQueue paymentCarQueue;
    private CarQueue exitCarQueue;
    private ArrayList<Car> reservations;
    private SimulatorView simulatorView;
    private StatisticsView statView;
    private LocationManager locman;
    private ArrayList<AbstractView> views;
    
    //parking cost per hour in Euro's
    private Lock lock = new ReentrantLock();
    private boolean limiter;
    
    private int parkingCostPH = 2;
    private double remainingRev = 0;
    private double currentRev = 0;
    
    private int day = 0;
    private int hour = 0;
    private int minute = 0;
    
    private int stepsToDo = 0;

    private int tickPause = 100;

    int numberOfFloors = 3;
    int numberOfRows = 6;
    int numberOfPlaces = 30;
    
    private int weekDayArrivals= 50; // average number of arriving cars per hour
    private int weekendArrivals = 90; // average number of arriving cars per hour

    private int enterSpeed = 3; // number of cars that can enter per minute
    private int paymentSpeed = 5; // number of cars that can pay per minute
    private int exitSpeed = 9; // number of cars that can leave per minute
    
    private double passHolderRatio = 0.1;
    private double reservationCarRatio = 0.1;
    private double passHolderReservationRatio = 0.2;
	
    
    
    
    public Simulator() {
    	limiter = true;
        entranceCarQueue = new CarQueue();
        paymentCarQueue = new CarQueue();
        exitCarQueue = new CarQueue();
        reservations = new ArrayList<Car>();
        locman = new LocationManager(this);
        stepsToDo += 100;
        simulatorView = new SimulatorView(this);
        views = new ArrayList<AbstractView>();
    }

    public void run() {
    	
        while(true) {
        	if(stepsToDo>0){
        		stepsToDo--;
        		tick();
        		
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
    
    //Revenue getters
    public double getRemainingRev() {
    	return remainingRev;
    }
    public double getCurrentRev() {
		return currentRev;
	}
    
    //Queue getters
    public CarQueue getEntranceQue() {
		return entranceCarQueue;
	}
    public CarQueue getExitQue() {
		return exitCarQueue;
	}
    public CarQueue getPaymentQue() {
		return paymentCarQueue;
	}
    
    //Size getters
    public int getNumberOfFloors(){
    	return numberOfFloors;
    }
    public int getNumberOfRows(){
    	return numberOfRows;
    }
    public int getNumberOfPlaces(){
    	return numberOfPlaces;
    }
    
    //controlpanel getters and functions
    public int getStepsToDo(){
    	return stepsToDo;
    }
    
    
    public void toggleLimit(){
    	if(limiter == true){
    		limiter = false;
    	}else{
    		limiter = true;
    	}
    	return;
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
    
    public void doADay(){
    	stepsToDo += 1440;
    }
    
    
    
    public void addLiveView(){
    	lock.lock();
    	views.add(new CarParkLiveView(this));
    	lock.unlock();
    	
    }
    
    public void addStatView(){
    	lock.lock();
    	statView = new StatisticsView(new Dimension(480,480),this);
    	views.add(statView);
    	lock.unlock();
    	
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
        	if(locman.getFreeSpotsCount()<=0){
        		break;
        	}
        	Car car = new AdHocCar();
        	double carGen = random.nextDouble();
        	if(carGen<=passHolderRatio+reservationCarRatio){
        		if(carGen<=passHolderRatio){
        			car = new PassHolderCar(random.nextInt(10000000));
        		}
        		if(carGen<=passHolderRatio+reservationCarRatio && carGen>passHolderRatio){
        			car = new ReservationCar(true);
        		}
        	}
        	if(car == null){
        		System.err.println("weird shit happenin");
        	}
        	
        	if(car instanceof PassHolderCar){
        		carGen = random.nextDouble();
        		if(carGen<=passHolderReservationRatio){
        			car.setReserveTime(random.nextInt(120));
        			car.setReserved();
        			reservations.add(car);
        			locman.reserve();
        		}else{
        			entranceCarQueue.addCar(car);
        		}
        	}
        	
        	if(car instanceof ReservationCar){
        		car.setReserveTime(random.nextInt(120));
        		car.setReserved();
        		reservations.add(car);
        		locman.reserve();
        	}
        	
            if(car instanceof AdHocCar){
            	entranceCarQueue.addCar(car);
            }
           
        }
            
        
        for(int i = 0; i< reservations.size(); i++ ){
        	Car car = reservations.remove(0);
        	car.rTick();
        	if(car.getReserveTime()<=0){
        		entranceCarQueue.addCar(car);
        	}else{
        		reservations.add(car);
        		
        	}
        }
        

        // Remove car from the front of the queue and assign to a parking space.
        for (int i = 0; i < enterSpeed; i++) {
            Car car = entranceCarQueue.removeCar();
            if (car == null) {
                break;
            }
            Location freeLocation;
            if(car.hasReserved()){
            	freeLocation = locman.getReservedLocation();
            }else{
            	freeLocation = locman.getFirstFreeLocation();
            }

            
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
            currentRev += car.getMinutesStayed() * parkingCostPH/60;
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
        
        //Calculate the remaining revenue.
        remainingRev = 0;
        for(Car[][] carFloor : locman.getCars()) {
    		for(Car[] carRow : carFloor) {
    			for(Car car : carRow) {
    				if (car == null) {
    	                break;
    	            }
    				
        			remainingRev += car.getMinutesStayed() * parkingCostPH/60;
            	}
        	}
    	}
        
        // Update all the views and the control panel.
        updateViews();
        

        // Pause.
        if(limiter == true){
	        try {
	            Thread.sleep(tickPause);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
        } /*else{
        	try {
	            Thread.sleep(10);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
        } */
    }
    
    private void updateViews(){
    	lock.lock();
    	Iterator<AbstractView> viewIt = views.iterator();
        while(viewIt.hasNext()){
        	viewIt.next().updateView();
        }
        lock.unlock();
        simulatorView.updateView();
        
    }
}
