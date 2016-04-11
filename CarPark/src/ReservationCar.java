
public class ReservationCar extends Car {
	
	private boolean paidYet;
	
	public ReservationCar(boolean paid){
		paidYet = paid;
	}
	
	public boolean getPaidYet(){
		return paidYet;
	}
	
	public void setPaidYet(boolean paidYet){
		this.paidYet = paidYet;
	}
}
