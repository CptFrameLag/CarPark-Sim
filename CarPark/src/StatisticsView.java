import java.awt.*;
import javax.swing.*;

public class StatisticsView extends AbstractView {
	
	private Container contentContainer;
	private JPanel contentPanel;
	
	private Box box;
	private JLabel currentRev;
	private JLabel remainingRev;
	private JLabel entranceQueue;
	private JLabel exitQueue;
	private JLabel paymentQueu;
	
	public StatisticsView(Dimension size, Simulator sim) {
		super(size, sim);
		
		contentContainer = getContentPane();
		contentPanel = new JPanel();
		
		//Initiate labels and layout
		box = Box.createVerticalBox();
		currentRev = new JLabel("Today's current revenue: "+ sim.getCurrentRev());
		remainingRev = new JLabel("Current entrance queue size: "+ sim.getEntranceQue().getQueueSize());
		entranceQueue = new JLabel("Today's current revenue: "+ sim.getCurrentRev());
		paymentQueu = new JLabel("Today's current revenue: "+ sim.getCurrentRev());
		exitQueue = new JLabel("Current exit queue size: "+ sim.getExitQue().getQueueSize());
		

		box.add(currentRev);
		box.add(remainingRev);
		box.add(entranceQueue);
		box.add(paymentQueu);
		box.add(exitQueue);
		contentPanel.add(box);
		
		contentContainer.add(contentPanel, BorderLayout.NORTH);
		
	}
	
	public void updateView() {
		currentRev.setText("Today's current revenue: €"+ sim.getCurrentRev());
		remainingRev.setText("Remaining Revenue from cars still in the car park: €"+ sim.getRemainingRev());
		entranceQueue.setText("Current entrance queue size: "+ sim.getEntranceQue().getQueueSize());
		paymentQueu.setText("Current payment queue size: "+ sim.getExitQue().getQueueSize());
		exitQueue.setText("Current exit queue size: "+ sim.getExitQue().getQueueSize());
		
		//System.out.println(getExitQue().getQueueSize());
	}
}