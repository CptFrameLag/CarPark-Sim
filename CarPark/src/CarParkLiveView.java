import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.JPanel;




public class CarParkLiveView extends AbstractView {
	private CarParkView carParkView;
	private JPanel legendPanel;
	
	public CarParkLiveView(Simulator sim){
		super(new Dimension(1000,500),sim);
		carParkView = new CarParkView(sim);
		makeLegendPanel();
        Container contentPane = getContentPane();
        contentPane.add(carParkView, BorderLayout.CENTER);
        contentPane.add(legendPanel, BorderLayout.SOUTH);
	}
	
	private void makeLegendPanel(){
		legendPanel = new JPanel();
		legendPanel.add(new JLabel("Legend:     Red: Normal Car     Green: Car with Reservation     Blue: Car with Parking Pass"));
		
	}
	
	public void updateView() {
        carParkView.updateView();
        
    }
	
	
	
    private class CarParkView extends JPanel {
        
        private Dimension size;
        private Image carParkImage;
        private Simulator sim;
    
        /**
         * Constructor for objects of class CarPark
         */
        public CarParkView(Simulator sim) {
            size = new Dimension(0, 0);
            this.sim = sim;
        }
    
        /**
         * Overridden. Tell the GUI manager how big we would like to be.
         */
        public Dimension getPreferredSize() {
            return new Dimension(800, 500);
        }
    
        /**
         * Overriden. The car park view component needs to be redisplayed. Copy the
         * internal image to screen.
         */
        public void paintComponent(Graphics g) {
            if (carParkImage == null) {
                return;
            }
    
            Dimension currentSize = getSize();
            if (size.equals(currentSize)) {
                g.drawImage(carParkImage, 0, 0, null);
            }
            else {
                // Rescale the previous image.
                g.drawImage(carParkImage, 0, 0, currentSize.width, currentSize.height, null);
            }
        }
    
        public void updateView() {
            // Create a new car park image if the size has changed.
            if (!size.equals(getSize())) {
                size = getSize();
                carParkImage = createImage(size.width, size.height);
            }
            if(carParkImage == null){
            	return;
            }
            
            Graphics graphics = carParkImage.getGraphics();
            for(int floor = 0; floor < sim.getNumberOfFloors(); floor++) {
                for(int row = 0; row < sim.getNumberOfRows(); row++) {
                    for(int place = 0; place < sim.getNumberOfPlaces(); place++) {
                        Location location = new Location(floor, row, place);
                        Car car = sim.getLocations().getCarAt(location);
                        Color color = Color.white;
                        if(car!=null){
                        	if(car instanceof PassHolderCar){
                        		color = Color.blue;
                        	}else{
                        		if(car instanceof ReservationCar){
                        			color = Color.green;
                        		}else{
                        			color = Color.red;
                        		}
                        	}
                        }
                        drawPlace(graphics, location, color);
                    }
                }
            }
            repaint();
        }
    
        /**
         * Paint a place on this car park view in a given color.
         */
        private void drawPlace(Graphics graphics, Location location, Color color) {
            graphics.setColor(color);
            graphics.fillRect(
                    location.getFloor() * 260 + (1 + (int)Math.floor(location.getRow() * 0.5)) * 75 + (location.getRow() % 2) * 20,
                    60 + location.getPlace() * 10,
                    20 - 1,
                    10 - 1); // TODO use dynamic size or constants
        }
    }
}
