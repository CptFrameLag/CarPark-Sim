public abstract class Car {

    private Location location;
    private int minutesLeft;
    private int minutesStayed;
    private boolean isPaying;
    private boolean hasRes = false;
    private int reserveTime;

    /**
     * Constructor for objects of class Car
     */
    public Car() {

    }
    
    public boolean hasReserved(){
    	return hasRes;
    }
    
    public void setReserved(){
    	hasRes = true;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getMinutesLeft() {
        return minutesLeft;
    }

    public void setMinutesLeft(int minutesLeft) {
        this.minutesLeft = minutesLeft;
    }
    
    public boolean getIsPaying() {
        return isPaying;
    }

    public void setIsPaying(boolean isPaying) {
        this.isPaying = isPaying;
    }

    public void tick() {
        minutesLeft--;
        minutesStayed++;
    }
    public void rTick(){
    	reserveTime--;
    }

    public void setReserveTime(int time){
    	reserveTime = time;
    }
    
    public int getReserveTime(){
    	return reserveTime;
    }
    
	public int getMinutesStayed() {
		return minutesStayed;
	}

}