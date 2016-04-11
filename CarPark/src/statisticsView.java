import java.awt.*;
import javax.swing.*;

public class statisticsView extends AbstractView {
	
	private Container contentContainer;
	private JPanel contentPanel;
	private JLabel label;
	
	public statisticsView(Dimension size, Simulator sim) {
		super(size, sim);
		updateView();
	}
	
	public void updateView() {
		contentContainer = getContentPane();
		contentPanel = new JPanel();
		label = new JLabel("Image and Text", JLabel.CENTER);
		
		contentPanel.add(label);
		contentContainer.add(contentPanel, BorderLayout.NORTH);
		
		
		
	}
}