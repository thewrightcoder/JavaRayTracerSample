package application.dataViewObjects.baseClasses;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import application.GuiWindow;
import application.dataViewObjects.DataViewObject;

public class DataViewObjectBoolean extends DataViewObject {


	private static final long serialVersionUID = 1L;
	
	private Vector<ActionListener> valueChangedListeners = new Vector<ActionListener>();
	private boolean value = false;
	
	public DataViewObjectBoolean(String name, boolean newValue) {
		super();
		value = newValue;
		
		Box box = Box.createHorizontalBox();
		
		JLabel displayName = new JLabel();
		displayName.setText(name);
		displayName.setMaximumSize(displayName.getPreferredSize());
		
		JCheckBox checkBox = new JCheckBox();
		checkBox.setHorizontalAlignment(SwingConstants.RIGHT);
		checkBox.setMaximumSize(checkBox.getPreferredSize());
		checkBox.setSelected(value);
		checkBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				setValueInternal(((JCheckBox)e.getItem()).isSelected());
			}
		});
		
		box.add(displayName);
		box.add(Box.createHorizontalGlue());
		box.add(checkBox);
		
		box.setAlignmentX(Box.LEFT_ALIGNMENT);
		box.setMinimumSize(new Dimension(GuiWindow.Get().getDataViewerRealWidth(), box.getPreferredSize().height));
		box.setPreferredSize(box.getMinimumSize());
		box.setMaximumSize(box.getMinimumSize());
	
		add(box);
	}

	private void setValueInternal(boolean b) {
		value = b;
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
	
	public boolean getValue() {
		return value;
	}
}
