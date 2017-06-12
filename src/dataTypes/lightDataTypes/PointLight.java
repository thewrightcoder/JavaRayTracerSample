package dataTypes.lightDataTypes;

import org.w3c.dom.Element;

import application.InternalStrings;
import dataTypes.baseClassDataTypes.HitRecord;
import dataTypes.baseClassDataTypes.Ray;
import dataTypes.baseClassDataTypes.Renderer;
import dataTypes.baseClassDataTypes.Scene;
import dataTypes.baseClassDataTypes.Vector3;
import dataTypes.geometryDataTypes.Geometry;

import java.util.Iterator;

public class PointLight extends Light {
	
	private boolean forceHardShadows = false;
	
	public PointLight (Element element) {
		super(element);
		forceHardShadows = Boolean.parseBoolean(element.getAttribute("forceHardShadows"));
	}
	
	public PointLight() {
		super();
		forceHardShadows = false;
	}

	public double getIntensity(HitRecord hitRec) {
		double bias = hitRec.bias;
		double farClip = 0.0f;
		double sampleCount = Scene.TheScene().getSoftShadowSampleCount();
		double currentIntensity = 0.0f;
		
		boolean shadowFound = false;
		
		Vector3 lightDir = null;
		Vector3 origin = null;
		
		if (sampleCount > 1 && !forceHardShadows) {
			// Each time this gets called, lightU and lightV get created and destroyed
			// This means this function is thread UNsafe, so create a thread lock here
			// to prevent accessing a null pointer.
			synchronized(this) {
				setupJitterAxis(getDirection(hitRec));
				
				for (int i = 0; i < sampleCount; ++i) {
					lightDir = position.add(lightU.multiply(Math.random() - 0.5f));
					lightDir = lightDir.add(lightV.multiply(Math.random() - 0.5f));
					lightDir = lightDir.subtract(hitRec.hitPoint);
					farClip = lightDir.length() - bias;
					lightDir.normalize();
					
					origin = hitRec.hitPoint.add(lightDir.multiply(bias));
					
					Iterator<Geometry> iter = Renderer.CurrentFrame.sceneGeo.iterator();
					while (iter.hasNext()) {
						Geometry geo = iter.next();
						shadowFound = geo.checkForShadowHit(new Ray(origin, lightDir), bias, farClip);
						if (shadowFound) {
							break;
						}
					}
					
					if (!shadowFound) {
						currentIntensity += intensity;
					}
				}
				clearJitterAxis();
			}
			
			currentIntensity /= sampleCount;
		}
		else {
			// If there aren't any soft shadow samples, don't jitter
			lightDir = getDirection(hitRec);
			origin = hitRec.hitPoint.add(lightDir.multiply(bias));
			double lightDistance = (hitRec.hitPoint.subtract(position)).length();
			
			Iterator<Geometry> iterator = Renderer.CurrentFrame.sceneGeo.iterator();
			Geometry curGeo = null;
			while (iterator.hasNext()) {
				curGeo = iterator.next();
				if (curGeo.checkForShadowHit(new Ray(origin, lightDir), bias, lightDistance)) {
					return 0.0;
				}
			}
			currentIntensity = intensity;
		}
		
		return currentIntensity;
		
	}
	
	public boolean getForceHardShadows() {
		return forceHardShadows;
	}
	
	public void setForceHardShadows(boolean b) {
		forceHardShadows = b;
	}
	
	public String toString() {
		return String.format("\tPoint Light:%n%s", super.toString());
	}
	
	protected String constructXMLString() {
		return "type=\""+getTypeName()+"\" forceHardShadows=\""+Boolean.toString(forceHardShadows)+"\"";
	}
	
	public String getTypeName() {
		return InternalStrings.PointLightTypeName;
	}
}
