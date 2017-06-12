package dataTypes.dataTypeFactories;

import org.w3c.dom.Element;

import dataTypes.geometryDataTypes.Geometry;

public abstract class GeometryFactory {
	public abstract Geometry getGeometry(Element node);
	public abstract Geometry getGeometry(String type);
}
