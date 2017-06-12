package application.components;

import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

import application.Utilities;

public class DataViewer extends JPanel {

	private static final long serialVersionUID = 1L;

	private JPanel panel = null;
	
	private int realWidth, realHeight;
	
	public DataViewer(int width, int height) {
		final int borderSize = 40;
		Border border = BorderFactory.createLoweredBevelBorder();
		
		panel = new JPanel();
		panel.setAlignmentX(JPanel.LEFT_ALIGNMENT);
		add(panel);
		
		realWidth = width - borderSize;
		realHeight = height - borderSize;
		
		if (Utilities.isWindowsSystem()) {
			// UI elements in windows look and feel are slightly larger
			// Accommodate them by making the border larger.
			realWidth -= borderSize;
			realHeight -= borderSize;
		}
		
		setSize(width, height);
		panel.setSize(width-borderSize, height-borderSize);
		
		setBorder(border);
	}
	
	public void updateView(Component comp) {
		panel.removeAll();
		if (comp != null) {
			panel.add(comp);
		}
		validate();
	}
	
	public int getRealHeight() {
		return realHeight;
	}
	
	public int getRealWidth() {
		return realWidth;
	}
}
