import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PieView extends AbstractView {

	private Container contentContainer;
	private JPanel contentPanel;
	
	private double nrNormalCars =0;
	private double nrReservations =0;
	private double nrPassHolders =0;
	
	private JLabel ticketHolders;
	private JLabel resHolders;
	private JLabel passHolders;
	
	
	public PieView (Dimension size, Simulator sim) {
		super(size, sim);
		contentContainer = getContentPane();
		contentPanel = new JPanel();
		contentPanel.setLayout(null);
		
		ticketHolders = new JLabel(" "+ nrNormalCars);
		resHolders = new JLabel(" "+ nrReservations);
		passHolders = new JLabel(" "+ nrPassHolders);
		
		
		ticketHolders.setBounds(10, -120, size.width, size.height);
		resHolders.setBounds(10, 20, size.width, size.height);
		passHolders.setBounds(10, 160, size.width, size.height);
		
		contentPanel.add(ticketHolders);
		contentPanel.add(resHolders);
		contentPanel.add(passHolders);
		
		contentContainer.add(contentPanel);
	}
	
	public void updateView() {
		//Calculate the amount of cars of a specific typ in the car park.
		nrPassHolders =0;
		nrReservations =0;
		nrNormalCars =0;
		for(Car[][] carFloor : sim.getLocations().getCars()) {
    		for(Car[] carRow : carFloor) {
    			for(Car car : carRow) {
    				if (car == null) {
    	                break;
    	            }
                	if(car instanceof PassHolderCar){
                		nrPassHolders++;
                	}else if(car instanceof ReservationCar){
                		nrReservations++;
                	}else nrNormalCars++;
            	}
        	}
    	}
		repaint();
		ticketHolders.setText("Spaces occupied by ticket holders: " + nrNormalCars);
		resHolders.setText("Spaces occupied by reservation holders: " + nrReservations);
		passHolders.setText("Spaces occupied by pass holders: " + nrPassHolders);
	}
	
	@Override public void paint(Graphics g) {	
		g.setColor(Color.RED);
		g.fillArc(30, 50, 100, 100, 0, (int)(nrNormalCars*(360/(double)sim.getTotalSpaces())));
	
		g.setColor(Color.GREEN);
		g.fillArc(30, 190, 100, 100, 0, (int)(nrReservations*(360/(double)sim.getTotalSpaces())));
		
		g.setColor(Color.BLUE);
		g.fillArc(30, 330, 100, 100, 0, (int)(nrPassHolders*(360/(double)sim.getTotalSpaces())));
		
    }
}