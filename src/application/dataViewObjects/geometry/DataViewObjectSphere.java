package application.dataViewObjects.geometry;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import application.GuiWindow;
import application.dataViewObjects.baseClasses.DataViewObjectNumber;
import dataTypes.geometryDataTypes.Sphere;

public class DataViewObjectSphere extends DataViewObjectGeometry {

	private static final long serialVersionUID = 1L;
	
	public DataViewObjectSphere(Object o) {
		super(o);
		Sphere s = (Sphere)o;
		
		DataViewObjectNumber radiusView = new DataViewObjectNumber("Radius", s.getRadius());
		radiusView.addValueChangedListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				s.setRadius(((DataViewObjectNumber)e.getSource()).getValue());
				GuiWindow.Get().renderPreview();
			}
		});
		add(radiusView);
	}
}
