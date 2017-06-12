package dataTypes.shaderDataTypes;

import java.util.List;

import java.util.Iterator;

import org.w3c.dom.Element;

import application.DisplayStrings;
import application.InternalStrings;
import dataTypes.baseClassDataTypes.Color;
import dataTypes.baseClassDataTypes.HitRecord;
import dataTypes.baseClassDataTypes.Renderer;
import dataTypes.baseClassDataTypes.Vector3;
import dataTypes.lightDataTypes.Light;

public class LambertShader extends Shader {
	protected Color color = null;
	
	public LambertShader(Element element) {
		super(element);
		if (element != null) {
			color = Color.parseColor(element.getAttribute("color"));
		} else {
			color = new Color(0.5f, 0.5f, 0.5f);
		}
	}
	
	public String toString() {
		return String.format("\tLambert Shader%n\t%s%n\t%s", super.toString(), color.toString());
	}
	
	public Color getColor(HitRecord hitRec) {
		// If there are no lights, 'incidence' is the vector from the hit point to the camera
		// Otherwise, it's the vector from the hit point to the current light.
		Vector3 incidence = null;
		Color result = new Color(0, 0, 0);
		double angle = 1.0;
		
		List<Light> lights = Renderer.CurrentFrame.sceneLights;
		
		if (lights.size() > 0) {
			Iterator<Light> iter = lights.iterator();
			while (iter.hasNext()) {
				Light curLight = iter.next();
				incidence = curLight.getPosition().subtract(hitRec.hitPoint).getNormalized();
				angle = hitRec.hitNormal.dot(incidence);
				if (angle > 0) {
					result = result.add(curLight.getColor().multiply(curLight.getIntensity(hitRec)).multiply(angle));
				}
			}
			result = color.multiply(result);
			result.clamp();
			return result;
		} else {
			incidence = hitRec.hitPoint.subtract(hitRec.hitOrigin).getNormalized();
			incidence.negate();
			return color.multiply(hitRec.hitNormal.dot(incidence));
		}
	}
	
	public Color getBaseColor() {
		return color;
	}
	
	protected String constructXMLString() {
		return String.format("type=\"%s\" color=%s", getTypeName(), color.getXML());
	}

	@Override
	public String getTypeName() {
		return InternalStrings.LambertShaderTypeName;
	}

	@Override
	public String getDisplayTypeName() {
		return DisplayStrings.LambertShaderName;
	}
}
