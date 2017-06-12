package dataTypes.lightDataTypes;

import java.util.Iterator;

import org.w3c.dom.Element;

import application.InternalStrings;
import dataTypes.baseClassDataTypes.HitRecord;
import dataTypes.baseClassDataTypes.Ray;
import dataTypes.baseClassDataTypes.Renderer;
import dataTypes.baseClassDataTypes.Scene;
import dataTypes.baseClassDataTypes.Vector3;
import dataTypes.geometryDataTypes.Geometry;

public class SpotLight extends Light {
	private double halfConeAngle, halfPenumbraAngle, originalConeAngle, originalPenumbraAngle;
	private Vector3 lookat = null;
	private Vector3 direction = null;
	
	public SpotLight(Element element) {
		super(element);
		setConeAngle(Double.parseDouble(element.getAttribute("coneAngle")));
		setPenumbraAngle(Double.parseDouble(element.getAttribute("penumbraAngle")));
		lookat = Vector3.parseVec(element.getAttribute("lookat"));

		updateDirection();
	}
	
	public SpotLight() {
		super();
		setConeAngle(40.0f);
		setPenumbraAngle(10.0f);
		lookat = new Vector3();
		
		updateDirection();
	}

	public double getIntensity(HitRecord hitRec) {
		double bias = hitRec.bias;
		double farClip = 0.0f;
		double sampleCount = Scene.TheScene().getSoftShadowSampleCount();
		double currentIntensity = 0.0f;
		
		boolean shadowFound = false;
		
		Vector3 lightDir = null;
		Vector3 jitterPosition = null;
		Vector3 origin = null;
		
		// Each time this gets called, lightU and lightV get created and destroyed
		// This means this function is thread UNsafe, so create a thread lock here
		// to prevent accessing a null pointer.
		synchronized(this) {
			if (sampleCount > 1) {
				setupJitterAxis(getDirection(hitRec));
			}
			
			for (int i = 0; i < sampleCount; ++i) {
				lightDir = direction;
				jitterPosition = position;
				if (sampleCount > 1) {
					// For a spot light, don't jitter the light position
					// unless there is more than 1 soft shadow sample.
					jitterPosition = position.add(lightU.multiply(Math.random() - 0.5f));
					jitterPosition = jitterPosition.add(lightV.multiply(Math.random() - 0.5f));
				}
				
				lightDir = lightDir.subtract(jitterPosition);
				lightDir.normalize();
				jitterPosition = jitterPosition.subtract(hitRec.hitPoint);
				farClip = jitterPosition.length() - bias;
				jitterPosition.normalize();
				
				origin = hitRec.hitPoint.add(jitterPosition.multiply(bias));
				
				Iterator<Geometry> iter = Renderer.CurrentFrame.sceneGeo.iterator();
				while (iter.hasNext()) {
					Geometry geo = iter.next();
					shadowFound = geo.checkForShadowHit(new Ray(origin, jitterPosition), bias, farClip);
					if (shadowFound) {
						break;
					}
				}
				
				if (!shadowFound) {
					Vector3 invLightPos = jitterPosition;
					invLightPos.negate();
					double currentDot = invLightPos.dot(lightDir);
					if (currentDot > halfConeAngle) {
						if (currentDot < halfPenumbraAngle) {
							currentIntensity += (intensity - ((currentDot - halfPenumbraAngle) / (halfConeAngle - halfPenumbraAngle)));
						}
						else {
							currentIntensity += intensity;
						}
					}
				}
			}
			clearJitterAxis();
		}
		currentIntensity /= sampleCount;
		
		return currentIntensity;
	}
	
	public Vector3 getLookAt() {
		return lookat;
	}
	
	public double getOriginalConeAngle() {
		return originalConeAngle;
	}
	
	public void setConeAngle(double angle) {
		originalConeAngle = angle;
		halfConeAngle = Math.cos(Math.toRadians(originalConeAngle / 2));
	}
	
	public double getPenumbraAngle() {
		return originalPenumbraAngle;
	}
	
	public void setPenumbraAngle(double angle) {
		originalPenumbraAngle = angle;
		halfPenumbraAngle = Math.cos(Math.toRadians(originalPenumbraAngle / 2));
	}
	
	public void updateDirection() {
		direction = lookat.subtract(position);
		direction.normalize();
	}
	
	public String toString() {
		return String.format("Spot Light:%n\t\tHalf Cone Angle:\t%f%n\t\tHalf Penumbra Angle:\t%f%n%s", halfConeAngle, halfPenumbraAngle, super.toString());
	}
	
	protected String constructXMLString() {
		return String.format("type=\"%s\" coneAngle=\"%f\" penumbraAngle=\"%f\" lookat=%s", getTypeName(), originalConeAngle, originalPenumbraAngle, lookat.getXML());
	}
	
	public String getTypeName() {
		return InternalStrings.SpotLightTypeName;
	}
}
