import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//This is the controlling View, this one has buttons but no display, it is used to control the simulation, not display it

public class SimulatorView extends JFrame {

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
        JButton liveView =  new JButton("Live View");
        
        
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
        controlPanel.add(oneButton);
        controlPanel.add(tenButton);
        controlPanel.add(hundredButton);
        controlPanel.add(stepsToDo);
        controlPanel.add(liveView);
        
        
        Container contentPane = getContentPane();
        contentPane.add(controlPanel, BorderLayout.NORTH);
        //contentPane.add(stepLabel, BorderLayout.NORTH);
        
        //contentPane.add(population, BorderLayout.SOUTH);
        this.setSize(1000, 500);
        this.setVisible(true);

        updateView();
    }

    public void updateView() {
        stepsToDo.setText("Steps to do: "+sim.getStepsToDo());
    }
    
     
    
    
    

}
