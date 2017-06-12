package application.dataViewObjects.lights;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import application.GuiWindow;
import application.dataViewObjects.baseClasses.DataViewObjectBoolean;
import dataTypes.lightDataTypes.PointLight;

public class DataViewObjectPointLight extends DataViewObjectLight {

	private static final long serialVersionUID = 1L;

	public DataViewObjectPointLight(Object light) {
		super(light);
		PointLight l = (PointLight)light;
		DataViewObjectBoolean hardShadows = new DataViewObjectBoolean("Force Hard Shadows", l.getForceHardShadows());
		hardShadows.addValueChangedListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				l.setForceHardShadows(((DataViewObjectBoolean)e.getSource()).getValue());
				GuiWindow.Get().renderPreview();
			}
		});
		
		add(hardShadows);
	}
}
