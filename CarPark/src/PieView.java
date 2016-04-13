import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

@SuppressWarnings("serial")
public class PieView extends AbstractView {

	private int nrNormalCars =0;
	private int nrReservations =0;
	private int nrPassholders =0;
	
	private Image pieImage;
	
	public PieView (Dimension size, Simulator sim) {
		super(size, sim);
		pieImage = createImage(size.width, size.height);
	}
	
	@Override public void updateView() {
		nrPassholders =0;
		nrReservations =0;
		nrNormalCars =0;
		for(Car[][] carFloor : sim.getLocations().getCars()) {
    		for(Car[] carRow : carFloor) {
    			for(Car car : carRow) {
    				if (car == null) {
    	                break;
    	            }
                	if(car instanceof PassHolderCar){
                		nrPassholders++;
                	}else if(car instanceof ReservationCar){
                		nrReservations++;
                	}else nrNormalCars++;
            	}
        	}
    	}
		Graphics graphics = pieImage.getGraphics();
		paint(graphics);
		repaint();
	}
	
	@Override public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 200, 200);
		
		g.setColor(Color.RED);
		g.fillArc(0, 10, 100, 100, 0, nrNormalCars);
		g.setColor(Color.GREEN);
		g.fillArc(120, 10, 100, 100, 0, nrReservations);
		g.setColor(Color.BLUE);
		g.fillArc(240, 10, 100, 100, 0, nrPassholders);
    }
}