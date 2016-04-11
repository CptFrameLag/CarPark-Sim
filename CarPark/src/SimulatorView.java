import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//This is the control Panel, It has buttons to control the simulation and displays some information
//Some of its buttons add views.


public class SimulatorView extends JFrame {

    private Simulator sim;
    private JPanel controlPanel;
    private JLabel stepsToDo;
    private JPanel viewPanel;
    
    
    public SimulatorView(Simulator sim) {
        this.sim=sim;
        
        this.setTitle("Simulation Control Panel");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        controlPanel = new JPanel();
        viewPanel = new JPanel();
        
        makeButtons();
        
        
        
        Container contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        contentPane.add(controlPanel);        
        contentPane.add(viewPanel);
        this.setSize(800, 200);
        this.setVisible(true);

        updateView();
    }

    public void updateView() {
        stepsToDo.setText("Steps to do: "+sim.getStepsToDo());
    }
    
     
    private void makeButtons(){
    	
    	JButton oneButton = new JButton("Do 1 Step");
        JButton tenButton = new JButton("Do 10 Steps");
        JButton hundredButton = new JButton("Do 100 Steps");
        JButton oneDay = new JButton("Do a day");
        stepsToDo = new JLabel("Steps to do: "+sim.getStepsToDo());
        JButton liveView =  new JButton("Live View");
        JButton statView = new JButton("Statistics");
        JButton limiter = new JButton("Toggle speed limit");
        
        
        ActionListener doADay = new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		sim.doADay();
        	}
        };
        
        ActionListener limitToggle = new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		sim.toggleLimit();
        	}
        };
        
        
        ActionListener addStatView = new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		sim.addStatView();
        	}
        };
        
        
        ActionListener addLiveView = new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		sim.addLiveView();
        	}
        };
        
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
        liveView.addActionListener(addLiveView);
        statView.addActionListener(addStatView);
        limiter.addActionListener(limitToggle);
        oneDay.addActionListener(doADay);
        controlPanel.add(oneButton);
        controlPanel.add(tenButton);
        controlPanel.add(hundredButton);
        controlPanel.add(oneDay);
        controlPanel.add(limiter);
        controlPanel.add(stepsToDo);
        viewPanel.add(liveView);
        viewPanel.add(statView);
    }
    
    

}
