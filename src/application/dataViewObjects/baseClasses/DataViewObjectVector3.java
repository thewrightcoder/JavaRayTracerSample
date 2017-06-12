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
import dataTypes.baseClassDataTypes.Vector3;

public class DataViewObjectVector3 extends DataViewObject {

	private static final long serialVersionUID = 1L;
	
	// For basic data types (int, bool, etc), 
	// this list is needed so that the original value 
	// can be updated when the UI changes.
	// For more complex types (Vector3, Color, etc)
	// The values will update automatically, however
	// things might still need to respond to the data changing
	// (Camera vectors require math to fully update, for example).
	private Vector<ActionListener> valueChangedListeners = new Vector<ActionListener>();
	
	public DataViewObjectVector3(String n, Vector3 vec) {
		super();
		Box box = Box.createHorizontalBox();
		
		JLabel name = new JLabel();
		name.setText(n);
		name.setHorizontalAlignment(SwingConstants.LEFT);
		name.setMaximumSize(name.getPreferredSize());
		
		JLabel x = new JLabel();
		x.setText("X");
		x.setMaximumSize(x.getPreferredSize());
		
		ImprovedFormattedTextField xText = new ImprovedFormattedTextField(Utilities.getDecimalFormatter(), vec.x);
		xText.setColumns(4);
		xText.setHorizontalAlignment(SwingConstants.RIGHT);
		xText.setMaximumSize(xText.getPreferredSize());
		xText.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				vec.x = Double.parseDouble(((ImprovedFormattedTextField)e.getSource()).getText());
				notifyListeners();
			}
		});
		
		JLabel y= new JLabel();
		y.setText(" Y");
		y.setMaximumSize(y.getPreferredSize());
		
		ImprovedFormattedTextField yText = new ImprovedFormattedTextField(Utilities.getDecimalFormatter(), vec.y);
		yText.setColumns(4);
		yText.setHorizontalAlignment(SwingConstants.RIGHT);
		yText.setMaximumSize(yText.getPreferredSize());
		yText.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				vec.y = Double.parseDouble(((ImprovedFormattedTextField)e.getSource()).getText());
				notifyListeners();
			}
		});
		
		JLabel z = new JLabel();
		z.setText(" Z");
		z.setMaximumSize(z.getPreferredSize());
		
		ImprovedFormattedTextField zText = new ImprovedFormattedTextField(Utilities.getDecimalFormatter(), vec.z);
		zText.setColumns(4);
		zText.setHorizontalAlignment(SwingConstants.RIGHT);
		zText.setMaximumSize(zText.getPreferredSize());
		zText.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				vec.z = Double.parseDouble(((ImprovedFormattedTextField)e.getSource()).getText());
				notifyListeners();
			}
		});
		
		box.add(name);
		box.add(Box.createHorizontalGlue());
		box.add(x);
		box.add(xText);
		box.add(y);
		box.add(yText);
		box.add(z);
		box.add(zText);
		
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
	
	private void notifyListeners() {
		ActionEvent e = new ActionEvent(this, ActionEvent.ACTION_FIRST, "VectorUpdated");
		for (int i = 0; i < valueChangedListeners.size(); ++i) {
			valueChangedListeners.get(i).actionPerformed(e);
		}
	}
}
