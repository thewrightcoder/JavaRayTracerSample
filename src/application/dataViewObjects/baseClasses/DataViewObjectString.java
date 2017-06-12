package application.dataViewObjects.baseClasses;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JTextField;

import application.GuiWindow;
import application.dataViewObjects.DataViewObject;

public class DataViewObjectString extends DataViewObject {

	private static final long serialVersionUID = 1L;
	
	private Vector<ActionListener> valueChangedListeners = new Vector<ActionListener>();
	private String value = null;
	
	public DataViewObjectString(String name, String text) {
		super();
		value = text;
		Box box = Box.createHorizontalBox();
		
		JLabel nameText = new JLabel();
		nameText.setText(name);
		nameText.setMaximumSize(nameText.getPreferredSize());
		
		JTextField textField = new JTextField();
		textField.setText(text);
		textField.setColumns(10);
		textField.setHorizontalAlignment(JTextField.RIGHT);
		textField.setMaximumSize(textField.getPreferredSize());
		textField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				value = ((JTextField)e.getSource()).getText();
				ActionEvent event = new ActionEvent(DataViewObjectString.this, ActionEvent.ACTION_FIRST, "Value Changed");
				for (int i = 0; i < valueChangedListeners.size(); ++i) {
					valueChangedListeners.get(i).actionPerformed(event);
				}
			}
		});
		
		box.add(nameText);
		box.add(Box.createHorizontalGlue());
		box.add(textField);
		
		box.setAlignmentX(Box.LEFT_ALIGNMENT);
		box.setMinimumSize(new Dimension(GuiWindow.Get().getDataViewerRealWidth(), box.getPreferredSize().height));
		box.setPreferredSize(box.getMinimumSize());
		box.setMaximumSize(box.getPreferredSize());
		
		add(box);
	}
	
	public void addValueChangedListener(ActionListener l) {
		valueChangedListeners.add(l);
	}
	
	public void removeValueChangedListener(ActionListener l) {
		valueChangedListeners.remove(l);
	}
	
	public String getValue() {
		return value;
	}
}
