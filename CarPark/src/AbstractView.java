import javax.swing.*;
import java.awt.*;

public abstract class AbstractView extends JFrame {
	private Simulator sim;
	
	public AbstractView(Dimension size, Simulator sim) {
		this.sim = sim;
		setSize(size);
		this.setVisible(true);
	}
	
	public void updateView() {
		
	}
}