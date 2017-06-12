package application.dataViewObjects;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public abstract class DataViewObject extends Box {
	
	private static final long serialVersionUID = 1L;
	
	public DataViewObject() {
		super(BoxLayout.PAGE_AXIS);
		setAlignmentX(JPanel.LEFT_ALIGNMENT);
	}
}
