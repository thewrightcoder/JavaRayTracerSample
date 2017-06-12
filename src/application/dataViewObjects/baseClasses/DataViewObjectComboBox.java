package application.dataViewObjects.baseClasses;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import application.GuiWindow;
import application.dataViewObjects.DataViewObject;

public class DataViewObjectComboBox extends DataViewObject {

	private static final long serialVersionUID = 1L;
	
	private JComboBox<String> comboBox = null;
	private Vector<ActionListener> listeners = new Vector<ActionListener>();
	
	public DataViewObjectComboBox(String name, String[] options, String defaultOption) {
		super();
		
		Box box = Box.createHorizontalBox();
		box.setAlignmentX(Box.LEFT_ALIGNMENT);
		
		JLabel label = new JLabel();
		label.setText(name);
		label.setMaximumSize(label.getPreferredSize());
		
		comboBox = new JComboBox<String>();
		for (int i = 0; i < options.length; ++i) {
			comboBox.addItem(options[i]);
		}
		comboBox.setSelectedItem(defaultOption);
		comboBox.setMaximumSize(comboBox.getPreferredSize());
		comboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String item = getSelectedItem();
				ActionEvent event = new ActionEvent(item, ActionEvent.ACTION_FIRST, "SelectionChanged");
				for (int i = 0; i < listeners.size(); ++i) {
					listeners.get(i).actionPerformed(event);
				}
			}			
		});
		
		box.add(label);
		box.add(Box.createHorizontalGlue());
		box.add(comboBox);
		box.setPreferredSize(new Dimension(GuiWindow.Get().getDataViewerRealWidth(), box.getPreferredSize().height));
		box.setMaximumSize(box.getPreferredSize());
		box.setMinimumSize(box.getPreferredSize());
		
		add(box);
	}
	
	public String getSelectedItem() {
		return (String)comboBox.getSelectedItem();
	}
	
	public void addActionListener(ActionListener l) {
		listeners.add(l);
	}
	
	public void removeActionListener(ActionListener l) {
		listeners.remove(l);
	}

}
