package application.dataViewObjects.baseClasses;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import application.GuiWindow;
import application.Utilities;
import application.components.ImprovedFormattedTextField;
import application.dataViewObjects.DataViewObject;

public class DataViewObjectNumber extends DataViewObject {
	
	private static final long serialVersionUID = 1L;

	private Vector<ActionListener> valueChangedListeners = new Vector<ActionListener>();
	
	private double value = 0.0f;
	private ImprovedFormattedTextField textField = null;
	
	public DataViewObjectNumber(String name, double newValue) {
		super();
		value = newValue;
		
		Box box = Box.createHorizontalBox();
		
		JLabel displayName = new JLabel();
		displayName.setText(name);
		
		textField = new ImprovedFormattedTextField(Utilities.getDecimalFormatter(), value);
		textField.setColumns(4);
		textField.setHorizontalAlignment(SwingConstants.RIGHT);
		textField.setMaximumSize(textField.getPreferredSize());
		textField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setValueInternal(((ImprovedFormattedTextField)e.getSource()).getText());
			}
		});
		
		box.add(displayName);
		box.add(Box.createHorizontalGlue());
		box.add(textField);
		
		box.setAlignmentX(Box.LEFT_ALIGNMENT);
		box.setMinimumSize(new Dimension(GuiWindow.Get().getDataViewerRealWidth(), box.getPreferredSize().height));
		box.setPreferredSize(box.getMinimumSize());
		box.setMaximumSize(box.getMinimumSize());

		add(box);
	}
	
	private void setValueInternal(String text) {
		value = Double.parseDouble(text);
		ActionEvent e = new ActionEvent(this, ActionEvent.ACTION_FIRST, "ValueChanged");
		for (int i = 0; i < valueChangedListeners.size(); ++i) {
			valueChangedListeners.get(i).actionPerformed(e);
		}
	}
	
	public void addValueChangedListener(ActionListener l) {
		valueChangedListeners.add(l);
	}
	
	public void removeValueChangedListener(ActionListener l) {
		valueChangedListeners.remove(l);
	}
	
	public double getValue() {
		return value;
	}
}
