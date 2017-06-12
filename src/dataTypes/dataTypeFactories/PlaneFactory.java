package dataTypes.dataTypeFactories;

import org.w3c.dom.Element;

import application.InternalStrings;
import dataTypes.geometryDataTypes.Geometry;
import dataTypes.geometryDataTypes.Plane;

public class PlaneFactory extends GeometryFactory {
	public Geometry getGeometry(Element element) {
		if (element.getAttribute("type").equalsIgnoreCase(InternalStrings.PlaneTypeName)) {
			return new Plane(element);
		}
		else {
			return null;
		}
	}

	public Geometry getGeometry(String type) {
		if (type.equalsIgnoreCase(InternalStrings.PlaneTypeName)) {
			return new Plane();
		}
		else {
			return null;
		}
	}
}
