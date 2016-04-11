import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//This is the controlling View, this one has buttons but no display, it is used to control the simulation, not display it

public class SimulatorView extends JFrame {
    private CarParkView carParkView;
    private Simulator sim;
    private JPanel controlPanel;
    private JLabel stepsToDo;
    
    
    public SimulatorView(Simulator sim) {
        this.sim=sim;
        
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        controlPanel = new JPanel();
        
        JButton oneButton = new JButton("Do 1 Step");
        JButton tenButton = new JButton("Do 10 Steps");
        JButton hundredButton = new JButton("Do 100 Steps");
        stepsToDo = new JLabel("Steps to do: "+sim.getStepsToDo());
        
        
        
        ActionListener oneButtonAction = new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		sim.doOneStep();
        	}
        };
        
        ActionListener tenButtonAction = new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		sim.doTenSteps();
        	}
        };
        
        ActionListener hundredButtonAction = new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		sim.doHundredSteps();
        	}
        };
        
        oneButton.addActionListener(oneButtonAction);
        tenButton.addActionListener(tenButtonAction);
        hundredButton.addActionListener(hundredButtonAction);
        controlPanel.add(oneButton);
        controlPanel.add(tenButton);
        controlPanel.add(hundredButton);
        controlPanel.add(stepsToDo);
        
        
        carParkView = new CarParkView(sim);
        Container contentPane = getContentPane();
        contentPane.add(controlPanel, BorderLayout.NORTH);
        //contentPane.add(stepLabel, BorderLayout.NORTH);
        contentPane.add(carParkView, BorderLayout.CENTER);
        //contentPane.add(population, BorderLayout.SOUTH);
        this.setSize(1000, 500);
        this.setVisible(true);

        updateView();
    }

    public void updateView() {
        carParkView.updateView();
        stepsToDo.setText("Steps to do: "+sim.getStepsToDo());
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
                        		color = Color.red;
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
