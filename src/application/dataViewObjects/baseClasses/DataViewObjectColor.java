package application.dataViewObjects.baseClasses;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

import application.GuiWindow;
import application.dataViewObjects.DataViewObject;

public class DataViewObjectColor extends DataViewObject {
	
	private static final long serialVersionUID = 1L;

	// For basic data types (int, bool, etc), 
	// this list is needed so that the original value 
	// can be updated when the UI changes.
	// For more complex types (Vector3, Color, etc)
	// The values will update automatically, however
	// things might still need to respond to the data changing
	// (Camera vectors require math to fully update, for example).
	private Vector<ActionListener> valueChangedListeners = new Vector<ActionListener>();
	
	public DataViewObjectColor(dataTypes.baseClassDataTypes.Color c) {
		super();
		Box box = Box.createHorizontalBox();
		
		JLabel name = new JLabel();
		name.setText("Color");
		name.setMaximumSize(name.getPreferredSize());
		
		JButton button = new JButton("");
		Color colorCopy = new Color((float)c.R, (float)c.G, (float)c.B);
		JPanel panel = new JPanel();
		panel.setOpaque(true);
		panel.setBackground(colorCopy);
		panel.setForeground(colorCopy);
		button.add(panel);
		button.setPreferredSize(new Dimension(50, 40));
		button.setMaximumSize(button.getPreferredSize());
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Color newColor = JColorChooser.showDialog(DataViewObjectColor.this, "Color Picker", colorCopy);
				if (newColor != null) {
					c.R = newColor.getRed() / 255.0f;
					c.G = newColor.getGreen() / 255.0f;
					c.B = newColor.getBlue() / 255.0f;
					panel.setBackground(newColor);
					notifyListeners();
				}
			}
		});
		
		box.add(name);
		box.add(Box.createHorizontalGlue());
		box.add(button);
		
		box.setPreferredSize(new Dimension(GuiWindow.Get().getDataViewerRealWidth(), box.getPreferredSize().height));
		box.setMinimumSize(box.getPreferredSize());
		box.setMaximumSize(box.getPreferredSize());
		box.setAlignmentX(Box.LEFT_ALIGNMENT);
		
		add(box);
	}
	
	public void addValueChangedListener(ActionListener l) {
		valueChangedListeners.add(l);
	}
	
	public void removeValueChangedListener(ActionListener l) {
		valueChangedListeners.remove(l);
	}
	
	private void notifyListeners() {
		ActionEvent e = new ActionEvent(this, ActionEvent.ACTION_FIRST, "VectorUpdated");
		for (int i = 0; i < valueChangedListeners.size(); ++i) {
			valueChangedListeners.get(i).actionPerformed(e);
		}
	}
}
