package dataTypes.geometryDataTypes;

import org.w3c.dom.Element;

import application.InternalStrings;
import dataTypes.baseClassDataTypes.HitRecord;
import dataTypes.baseClassDataTypes.Ray;
import dataTypes.baseClassDataTypes.Vector3;

public class Plane extends Geometry {
	
	Vector3 normal = null;
	
	public Plane(Element element) {
		super(element);
		normal = Vector3.parseVec(element.getAttribute("normal"));
		normal.normalize();
	}
	
	public Plane() {
		super();
		normal = new Vector3(0.0, 1.0, 0.0);
	}

	public String toString() {
		return String.format("Plane%n\t\tNormal:\t%s%n\t%s", normal.toString(), super.toString());
	}
	
	public double checkForHit(Ray r, double nearClip, double farClip) {
		double denominator = normal.dot(r.direction());
		if (Math.abs(denominator) > 0.0001f) {
			return (normal.dot(position.subtract(r.origin()))) / denominator;
		}
		return -1;
	}
	
	public void getNormalAtPoint(HitRecord hitRec) {
		hitRec.hitNormal = normal;
	}
	
	protected String constructXMLString() {
		return String.format("type=\"Plane\" normal=%s", normal.getXML());
	}
	
	@Override
	public String getTypeName() {
		return InternalStrings.PlaneTypeName;
	}
	
	public Vector3 getNormal() {
		return normal;
	}
}

