package dataTypes.geometryDataTypes;

import org.w3c.dom.Element;

import application.InternalStrings;
import dataTypes.baseClassDataTypes.HitRecord;
import dataTypes.baseClassDataTypes.Ray;
import dataTypes.baseClassDataTypes.Vector3;

public class Sphere extends Geometry {
	
	protected double radius;
	
	public Sphere(Element element) {
		super(element);
		radius = Double.parseDouble(element.getAttribute("radius"));
	}
	
	public Sphere() {
		super();
		radius = 1.0f;
	}

	public String toString() {
		return String.format("Sphere%n\t\tRadius:\t%f%n\t%s", radius, super.toString());
	}
	
	public double checkForHit(Ray r, double nearClip, double farClip) {
		// origin of the Ray to the center of this sphere.
		Vector3 temp = r.origin().subtract(position); 
		
		double a = r.direction().dot(r.direction());
		double b = 2 * r.direction().dot(temp);
		double dc = temp.dot(temp) - Math.pow(radius, 2);
		
		double disc = Math.pow(b, 2) - 4 * a * dc;
		
		if (disc > 0) {
			disc = Math.sqrt(disc);
			double t = -b - disc;
			t = t / (2 * a);
			if (t < nearClip) {
				t = (-b + disc) / (2 * a);
			}
			
			if (t > nearClip && t < farClip) {
				return t;
			}
		}
		return -1;
	}

	public void getNormalAtPoint(HitRecord hitRec) {
		hitRec.hitNormal = hitRec.hitPoint.subtract(position);
		hitRec.hitNormal.normalize();
	}
	
	protected String constructXMLString() {
		return String.format("type=\"Sphere\" radius=\"%f\"", radius);
	}
	
	@Override
	public String getTypeName() {
		return InternalStrings.SphereTypeName;
	}
	
	public void setRadius(double r) {
		radius = r;
	}
	
	public double getRadius() {
		return radius;
	}
}
