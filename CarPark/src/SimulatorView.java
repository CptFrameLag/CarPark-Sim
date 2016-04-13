import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//This is the control Panel, It has buttons to control the simulation and displays some information
//Some of its buttons add views.


public class SimulatorView extends JFrame {

    private Simulator sim;
    
    private JPanel controlPanel;
    private JPanel viewPanel;
    private JPanel stepsPanel;
    private JPanel ratioPanel;
    private JPanel bizzyPanel;
    private JPanel speedPanel;
    
    
    private JLabel stepsToDo;
    //bizzyPanel buttons
    private JLabel weekday;
    private JLabel weekendday;
    
    //speedPanel Buttons
    private JLabel enterSpeed;
    private JLabel paymentSpeed;
    private JLabel exitSpeed;
    
    //ratioPanel Buttons
    private JLabel resRatio;
    private JLabel passRatio;
    private JLabel passResRatio;
    
    
    
    public SimulatorView(Simulator sim) {
        this.sim=sim;
        
        this.setTitle("Simulation Control Panel");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        makeButtons();
        
        
        
        Container contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        contentPane.add(controlPanel);
        contentPane.add(bizzyPanel);
        contentPane.add(speedPanel);
        contentPane.add(ratioPanel);
        contentPane.add(viewPanel);
        contentPane.add(stepsPanel);
        this.setSize(1000, 300);
        this.setVisible(true);

        updateView();
    }

    public void updateView() {
        stepsToDo.setText("Steps to do: "+sim.getStepsToDo());
    }
    
    public void updateLabels(){
    	weekday.setText("Weekday cars/hr: "+sim.getWeekDayArrivals());
        weekendday.setText("Weekend cars/hr: "+sim.getWeekendArrivals());
        
        
        //speedPanel Buttons
        enterSpeed.setText("Entering Speed in cars/min: "+sim.getEnterSpeed());
        paymentSpeed.setText("Payment Speed in cars/min: "+sim.getPaymentSpeed());
        exitSpeed.setText("Exiting speed in cars/min: "+sim.getExitSpeed());
        
        //ratioPanel Buttons
        resRatio.setText("Reservation percentage: "+100*sim.getReservationCarRatio());
        passRatio.setText("Passholder percentage: "+100*sim.getPassHolderRatio());
        passResRatio.setText("Percentage of passholders reserving: "+100*sim.getPassHolderReservationRatio());
    }
    
     
    private void makeButtons(){
    	
    	controlPanel = new JPanel();
        bizzyPanel = new JPanel();
        speedPanel = new JPanel();
        ratioPanel = new JPanel();
        viewPanel = new JPanel();
        stepsPanel = new JPanel();
        
        
        
    	//controlPanel buttons
    	JButton oneButton = new JButton("Do 1 Step");
        JButton tenButton = new JButton("Do 10 Steps");
        JButton hundredButton = new JButton("Do 100 Steps");
        JButton oneDay = new JButton("Do a day");
        
        //bizzyPanel buttons
        weekday = new JLabel("Weekday cars/hr: "+sim.getWeekDayArrivals());
        weekendday = new JLabel("Weekend cars/hr: "+sim.getWeekendArrivals());
        JButton setWeek = new JButton("Change");
        JButton setWeekend = new JButton("Change");
        
        
        //speedPanel Buttons
        enterSpeed = new JLabel("Entering Speed in cars/min: "+sim.getEnterSpeed());
        paymentSpeed = new JLabel("Payment Speed in cars/min: "+sim.getPaymentSpeed());
        exitSpeed = new JLabel("Exiting speed in cars/min: "+sim.getExitSpeed());
        JButton setEnter = new JButton("Change");
        JButton setPay = new JButton("Change");
        JButton setExit = new JButton("Change");
        
        
        //ratioPanel Buttons
        resRatio = new JLabel("Reservation percentage: "+100*sim.getReservationCarRatio());
        passRatio = new JLabel("Passholder percentage: "+100*sim.getPassHolderRatio());
        passResRatio = new JLabel("Reserving passholders: "+100*sim.getPassHolderReservationRatio());
        JButton setRes = new JButton("Change");
        JButton setPass = new JButton("Change");
        JButton setPassRes = new JButton("Change");
        

        //viewPanel Buttons
        JButton liveView =  new JButton("Live View");
        JButton statView = new JButton("Statistics");
        //stepsPanel Buttons
        JButton limiter = new JButton("Toggle speed limit");
        stepsToDo = new JLabel("Steps to do: "+sim.getStepsToDo());
        
        
        
        
        
        
        
        
        //Control Actionlisteners
        
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
        
        ActionListener doADay = new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		sim.doADay();
        	}
        };
        
        
        //bizzy ActionListeners
        ActionListener setWeekSpeed = new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		String input = JOptionPane.showInputDialog("Please set a new value");
        		int inti = 50;
        		try{
        			inti = Integer.parseInt(input);
        		}catch(Exception ex){
        			ex.printStackTrace();
        		}
        		sim.setWeekDayArrivals(inti);
        		updateLabels();
        	}
        };
        ActionListener setWeekendSpeed = new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		String input = JOptionPane.showInputDialog("Please set a new value");
        		int inti = 90;
        		try{
        			inti = Integer.parseInt(input);
        		}catch(Exception ex){
        			ex.printStackTrace();
        		}
        		sim.setWeekendArrivals(inti);
        		updateLabels();
        	}
        };
        
        //Speed Actionlisteners
        
        ActionListener setEnterSpeed = new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		String input = JOptionPane.showInputDialog("Please set a new value");
        		int inti = 3;
        		try{
        			inti = Integer.parseInt(input);
        		}catch(Exception ex){
        			ex.printStackTrace();
        		}
        		sim.setEnterSpeed(inti);
        		updateLabels();
        	}
        };
        ActionListener setPaySpeed = new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		String input = JOptionPane.showInputDialog("Please set a new value");
        		int inti = 5;
        		try{
        			inti = Integer.parseInt(input);
        		}catch(Exception ex){
        			ex.printStackTrace();
        		}
        		sim.setPaymentSpeed(inti);
        		updateLabels();
        	}
        };
        ActionListener setExitSpeed = new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		String input = JOptionPane.showInputDialog("Please set a new value");
        		int inti = 9;
        		try{
        			inti = Integer.parseInt(input);
        		}catch(Exception ex){
        			ex.printStackTrace();
        		}
        		sim.setExitSpeed(inti);
        		updateLabels();
        	}
        };
        
        //ratio ActionListeners
        ActionListener setResRatio = new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		String input = JOptionPane.showInputDialog("Please set a new value");
        		double dbl = 0.1;
        		try{
        			dbl = Double.parseDouble(input)/100;
        		}catch(Exception ex){
        			ex.printStackTrace();
        		}
        		sim.setReservationCarRatio(dbl);
        		updateLabels();
        	}
        };
        ActionListener setPassRatio = new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		String input = JOptionPane.showInputDialog("Please set a new value");
        		double dbl = 0.1;
        		try{
        			dbl = Double.parseDouble(input)/100;
        		}catch(Exception ex){
        			ex.printStackTrace();
        		}
        		sim.setPassHolderRatio(dbl);
        		updateLabels();
        	}
        };
        ActionListener setPassRessRatio = new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		String input = JOptionPane.showInputDialog("Please set a new value");
        		double dbl = 0.2;
        		try{
        			dbl = Double.parseDouble(input)/100;
        		}catch(Exception ex){
        			ex.printStackTrace();
        		}
        		sim.setPassHolderReservationRatio(dbl);
        		updateLabels();
        	}
        };
        
      //View ActionListeners
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
        
        //Steps Actionlistener
        ActionListener limitToggle = new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		sim.toggleLimit();
        	}
        };
        
        
        //adding actionlisteners
        oneButton.addActionListener(oneButtonAction);
        tenButton.addActionListener(tenButtonAction);
        hundredButton.addActionListener(hundredButtonAction);
        oneDay.addActionListener(doADay);
        
        setWeek.addActionListener(setWeekSpeed);
        setWeekend.addActionListener(setWeekendSpeed);
        
        setEnter.addActionListener(setEnterSpeed);
        setPay.addActionListener(setPaySpeed);
        setExit.addActionListener(setExitSpeed);
        
        setRes.addActionListener(setResRatio);
        setPass.addActionListener(setPassRatio);
        setPassRes.addActionListener(setPassRessRatio);
        
        
        liveView.addActionListener(addLiveView);
        statView.addActionListener(addStatView);
        limiter.addActionListener(limitToggle);
        
        
        //adding buttons to panels
        controlPanel.add(oneButton);
        controlPanel.add(tenButton);
        controlPanel.add(hundredButton);
        controlPanel.add(oneDay);
        bizzyPanel.add(weekday);
        bizzyPanel.add(setWeek);
        bizzyPanel.add(weekendday);
        bizzyPanel.add(setWeekend);
        speedPanel.add(enterSpeed);
        speedPanel.add(setEnter);
        speedPanel.add(paymentSpeed);
        speedPanel.add(setPay);
        speedPanel.add(exitSpeed);
        speedPanel.add(setExit);
        ratioPanel.add(resRatio);
        ratioPanel.add(setRes);
        ratioPanel.add(passRatio);
        ratioPanel.add(setPass);
        ratioPanel.add(passResRatio);
        ratioPanel.add(setPassRes);
        viewPanel.add(liveView);
        viewPanel.add(statView);
        stepsPanel.add(limiter);
        stepsPanel.add(stepsToDo);
        
    }
    
    

}
