package dataTypes.lightDataTypes;

import org.w3c.dom.Element;

import dataTypes.baseClassDataTypes.Color;
import dataTypes.baseClassDataTypes.HitRecord;
import dataTypes.baseClassDataTypes.Vector3;

public abstract class Light {
	protected Color color = null;
	protected double intensity = 0.0f;
	protected Vector3 position = null;
	
	// Used for soft shadow position jittering.
	protected Vector3 lightU = null;
	protected Vector3 lightV = null;
	
	public Light(Element element) {
		color = Color.parseColor(element.getAttribute("color"));
		position = Vector3.parseVec(element.getAttribute("position"));
		intensity = Double.parseDouble(element.getAttribute("intensity"));
	}
	
	public Light() {
		color = new Color(0.5, 0.5, 0.5);
		position = new Vector3();
		intensity = 1.0f;
	}

	public abstract double getIntensity(HitRecord hitRec);
	
	public Vector3 getDirection(HitRecord hitRec) {
		Vector3 dir = position.subtract(hitRec.hitPoint);
		dir.normalize();
		return dir;
	}
	
	public void setupJitterAxis(Vector3 direction) {
		// This creates a random vector that 
		// is perpendicular to the direction of the light from the hit point
		lightU = direction.cross(new Vector3(1+direction.y, -direction.z, direction.x).getNormalized());
		lightU.normalize();
		// This creates a vector perpendicular to the previous vector... 
		// which makes this also random!
		lightV = direction.cross(lightU);
		lightV.normalize();
	}
	
	public void clearJitterAxis() {
		lightU = null;
		lightV = null;
	}
	
	public Vector3 getPosition() {
		return position;
	}
	
	public void setPosition(Vector3 newPosition) {
		position = newPosition;
	}
	
	public Color getColor() {
		return color;
	}
	
	public abstract String getTypeName();
	
	public String toString() {
		return String.format("\t\t%s%n\t\tPosition:\t%s%n\t\tIntensity:\t%f", color.toString(), position.toString(), intensity);
	}
	
	public String getXML() {
		return String.format("<Light %s color=%s intensity=\"%f\" position=%s />", constructXMLString(), color.getXML(), intensity, position.getXML());
	}
	
	protected abstract String constructXMLString();
	
	public void setIntensityValue(double i) {
		intensity = i;
	}
	
	public double getIntensityValue() {
		return intensity;
	}
}
