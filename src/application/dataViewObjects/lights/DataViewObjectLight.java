package application.dataViewObjects.lights;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import application.GuiWindow;
import application.dataViewObjects.DataViewObject;
import application.dataViewObjects.baseClasses.DataViewObjectColor;
import application.dataViewObjects.baseClasses.DataViewObjectNumber;
import application.dataViewObjects.baseClasses.DataViewObjectVector3;
import dataTypes.lightDataTypes.Light;

public class DataViewObjectLight extends DataViewObject {

	private static final long serialVersionUID = 1L;

	public DataViewObjectLight(Object light) {
		Light l = (Light)light;
		DataViewObjectNumber intens = new DataViewObjectNumber("Intensity", l.getIntensityValue());
		intens.addValueChangedListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				l.setIntensityValue(((DataViewObjectNumber)e.getSource()).getValue());
				GuiWindow.Get().renderPreview();
			}
		});
		
		DataViewObjectColor color = new DataViewObjectColor(l.getColor());
		color.addValueChangedListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GuiWindow.Get().renderPreview();
			}
		});
		
		DataViewObjectVector3 pos = new DataViewObjectVector3("Position", l.getPosition());
		pos.addValueChangedListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GuiWindow.Get().renderPreview();
			}
		});
		
		add(intens);
		add(color);
		add(pos);
	}
	
}
