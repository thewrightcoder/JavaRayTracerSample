package dataTypes.dataTypeFactories;

import org.w3c.dom.Element;

import application.InternalStrings;
import dataTypes.geometryDataTypes.Geometry;
import dataTypes.geometryDataTypes.Sphere;

public class SphereFactory extends GeometryFactory {
	public Geometry getGeometry(Element element) {
		if (element.getAttribute("type").equalsIgnoreCase(InternalStrings.SphereTypeName)) {
			return new Sphere(element);
		}
		return null;
	}
	
	public Geometry getGeometry(String type) {
		if (type.equalsIgnoreCase(InternalStrings.SphereTypeName)) {
			return new Sphere();
		}
		return null;
	}
}
