package application.dataViewObjects.geometry;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

import application.GuiWindow;
import application.dataViewObjects.DataViewObject;
import application.dataViewObjects.baseClasses.DataViewObjectComboBox;
import application.dataViewObjects.baseClasses.DataViewObjectVector3;
import dataTypes.baseClassDataTypes.Scene;
import dataTypes.geometryDataTypes.Geometry;

public class DataViewObjectGeometry extends DataViewObject {

	private static final long serialVersionUID = 1L;

	public DataViewObjectGeometry(Object g) {
		super();
		Geometry geo = (Geometry)g;
		
		// TODO: scene geo needs names!
		String[] names = Scene.TheScene().getAllShaderNames();
		if (names.length > 1) {
			
			DataViewObjectComboBox comboBox = new DataViewObjectComboBox("Shader Name", names, geo.getShader() != null ? geo.getShader().getName() : names[0]);
			comboBox.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String s = (String)e.getSource();
					geo.updateShaderFromName(s);
					GuiWindow.Get().renderPreview();
				}
			});
			add(comboBox);
		} else {
			// There aren't any shaders yet.
			// Inform the user that they need to make shaders!
			JLabel noShaders1 = new JLabel();
			noShaders1.setText(String.format("There aren't any shaders.  Create at least one"));
			noShaders1.setPreferredSize(new Dimension(320, 24));
			noShaders1.setMaximumSize(noShaders1.getPreferredSize());
			noShaders1.setMinimumSize(noShaders1.getPreferredSize());
			
			JLabel noShaders2 = new JLabel();
			noShaders2.setText(String.format("shader before setting shaders on geometry."));
			noShaders2.setPreferredSize(new Dimension(320, 24));
			noShaders2.setMaximumSize(noShaders2.getPreferredSize());
			noShaders2.setMinimumSize(noShaders2.getPreferredSize());
			
			add(noShaders1);
			add(noShaders2);
		}
		
		DataViewObjectVector3 vec = new DataViewObjectVector3("Position", geo.getPosition());
		vec.addValueChangedListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GuiWindow.Get().renderPreview();
			}
		});
		
		
		add(vec);
	}
}
