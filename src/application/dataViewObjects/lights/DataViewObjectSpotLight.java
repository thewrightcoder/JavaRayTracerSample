package application.dataViewObjects.lights;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import application.GuiWindow;
import application.dataViewObjects.baseClasses.DataViewObjectNumber;
import application.dataViewObjects.baseClasses.DataViewObjectVector3;
import dataTypes.lightDataTypes.SpotLight;

public class DataViewObjectSpotLight extends DataViewObjectLight {

	private static final long serialVersionUID = 1L;

	public DataViewObjectSpotLight(Object light) {
		super(light);
		SpotLight l = (SpotLight)light;
		
		DataViewObjectVector3 lookat = new DataViewObjectVector3("Look At", l.getLookAt());
		lookat.addValueChangedListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				l.updateDirection();
				GuiWindow.Get().renderPreview();
			}
		});
		
		DataViewObjectNumber cn = new DataViewObjectNumber("Cone Angle", l.getOriginalConeAngle());
		cn.addValueChangedListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				l.setConeAngle(((DataViewObjectNumber)e.getSource()).getValue());
				GuiWindow.Get().renderPreview();
			}
		});
		
		DataViewObjectNumber pn = new DataViewObjectNumber("Penumbra Angle", l.getPenumbraAngle());
		pn.addValueChangedListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				l.setPenumbraAngle(((DataViewObjectNumber)e.getSource()).getValue());
				GuiWindow.Get().renderPreview();
			}
		});
		
		add(lookat);
		add(cn);
		add(pn);
	}
}
