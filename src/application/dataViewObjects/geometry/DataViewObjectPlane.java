package application.dataViewObjects.geometry;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import application.GuiWindow;
import application.dataViewObjects.baseClasses.DataViewObjectVector3;
import dataTypes.geometryDataTypes.Plane;

public class DataViewObjectPlane extends DataViewObjectGeometry {

	private static final long serialVersionUID = 1L;
	Plane p = null;
	public DataViewObjectPlane(Object plane) {
		super(plane);
		p = (Plane)plane;
		DataViewObjectVector3 vec = new DataViewObjectVector3("Normal", p.getNormal());
		vec.addValueChangedListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GuiWindow.Get().renderPreview();
			}
		});
		
		add(vec);
	}
}
