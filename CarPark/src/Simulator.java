import java.util.Random;

public class Simulator {

    private CarQueue entranceCarQueue;
    private CarQueue paymentCarQueue;
    private CarQueue exitCarQueue;
    private SimulatorView simulatorView;

    private int day = 0;
    private int hour = 0;
    private int minute = 0;
    
    private int stepsToDo = 0;

    private int tickPause = 100;

    int weekDayArrivals= 50; // average number of arriving cars per hour
    int weekendArrivals = 90; // average number of arriving cars per hour

    int enterSpeed = 3; // number of cars that can enter per minute
    int paymentSpeed = 5; // number of cars that can pay per minute
    int exitSpeed = 9; // number of cars that can leave per minute
    
    double passHolderRatio = 0.1;
    
    public Simulator() {
        entranceCarQueue = new CarQueue();
        paymentCarQueue = new CarQueue();
        exitCarQueue = new CarQueue();
        simulatorView = new SimulatorView(3, 6, 30, this);
    }

    public void run(int simulationLength) {
    	stepsToDo += simulationLength;
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
    
    public void doOneStep(){
    	stepsToDo++;
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
        	Car car;
        	if(random.nextDouble()>passHolderRatio){
        		car = new AdHocCar();
        	}else{
        		car = new PassHolderCar();
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
            // Find a space for this car.
            Location freeLocation = simulatorView.getFirstFreeLocation();
            if (freeLocation != null) {
                simulatorView.setCarAt(freeLocation, car);
                int stayMinutes = (int) (15 + random.nextFloat() * 10 * 60);
                car.setMinutesLeft(stayMinutes);
            }
        }

        // Perform car park tick.
        simulatorView.tick();

        // Add leaving cars to the exit queue.
        while (true) {
            Car car = simulatorView.getFirstLeavingCar();
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
            // TODO Handle payment.
            
            exitCarQueue.addCar(car);
        }

        // Let cars leave.
        for (int i = 0; i < exitSpeed; i++) {
            Car car = exitCarQueue.removeCar();
            if (car == null) {
                break;
            }
            simulatorView.removeCarAt(car.getLocation());
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