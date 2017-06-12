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

public class DataViewObjectDimension extends DataViewObject {
	
	private static final long serialVersionUID = 1L;

	private int xValue = 0;
	private int yValue = 0;
	private Vector<ActionListener> xValueChangedListeners = new Vector<ActionListener>();
	private Vector<ActionListener> yValueChangedListeners = new Vector<ActionListener>();
	
	public DataViewObjectDimension(String name, int x, int y) {
		super();
		Box box = Box.createHorizontalBox();
		
		JLabel displayName = new JLabel();
		displayName.setText(name);
		displayName.setMaximumSize(displayName.getMaximumSize());
		
		ImprovedFormattedTextField textFieldX = new ImprovedFormattedTextField(Utilities.getIntegerFormatter(), x);
		textFieldX.setColumns(4);
		textFieldX.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldX.setMaximumSize(textFieldX.getPreferredSize());
		textFieldX.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				xValue = Integer.parseInt(((ImprovedFormattedTextField)e.getSource()).getText());
				ActionEvent event = new ActionEvent(DataViewObjectDimension.this, ActionEvent.ACTION_FIRST, "X Value Changed");
				for (int i = 0; i < xValueChangedListeners.size(); ++i) {
					xValueChangedListeners.get(i).actionPerformed(event);
				}
			}
		});
		
		JLabel seperator = new JLabel();
		seperator.setText(" x ");
		seperator.setMaximumSize(seperator.getPreferredSize());
		
		ImprovedFormattedTextField textFieldY = new ImprovedFormattedTextField(Utilities.getIntegerFormatter(), y);
		textFieldY.setColumns(4);
		textFieldY.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldY.setMaximumSize(textFieldY.getPreferredSize());
		textFieldY.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				yValue = Integer.parseInt(((ImprovedFormattedTextField)e.getSource()).getText());
				ActionEvent event = new ActionEvent(DataViewObjectDimension.this, ActionEvent.ACTION_FIRST, "Y Value Changed");
				for (int i = 0; i < xValueChangedListeners.size(); ++i) {
					yValueChangedListeners.get(i).actionPerformed(event);
				}
			}
		});
		
		box.add(displayName);
		box.add(Box.createHorizontalGlue());
		box.add(textFieldX);
		box.add(seperator);
		box.add(textFieldY);
		
		box.setAlignmentX(Box.LEFT_ALIGNMENT);
		box.setMinimumSize(new Dimension(GuiWindow.Get().getDataViewerRealWidth(), box.getPreferredSize().height));
		box.setPreferredSize(box.getMinimumSize());
		box.setMaximumSize(box.getMinimumSize());
		
		add(box);
	}
	
	public void addXValueChangedListener(ActionListener l) {
		xValueChangedListeners.add(l);
	}
	
	public void removeXValueChangedListner(ActionListener l) {
		xValueChangedListeners.remove(l);
	}

	public void addYValueChangedListener(ActionListener l) {
		yValueChangedListeners.add(l);
	}
	
	public void removeYValueChangedListner(ActionListener l) {
		yValueChangedListeners.remove(l);
	}
	
	public int getXValue() {
		return xValue;
	}
	
	public int getYValue() {
		return yValue;
	}
}
