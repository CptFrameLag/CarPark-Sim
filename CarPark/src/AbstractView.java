import javax.swing.*;
import java.awt.*;

public abstract class AbstractView extends JFrame {
	
	public AbstractView(Dimension size) {
		setSize(size);
		this.setVisible(true);
	}
	
	public void updateView() {
		
	}
}