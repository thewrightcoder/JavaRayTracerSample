package dataTypes.shaderDataTypes;

import java.util.Iterator;

import org.w3c.dom.Element;

import application.DisplayStrings;
import application.InternalStrings;
import dataTypes.baseClassDataTypes.Color;
import dataTypes.baseClassDataTypes.HitRecord;
import dataTypes.baseClassDataTypes.Renderer;
import dataTypes.baseClassDataTypes.Vector3;
import dataTypes.lightDataTypes.Light;

public class BlinnShader extends LambertShader {

	protected double shininess = 0.0f;
	
	public BlinnShader(Element element) {
		super(element);
		if (element != null) { 
			shininess = Double.parseDouble(element.getAttribute("shininess"));
		} else
		{
			shininess = 0.1f;
		}
	}

	@Override
	public Color getColor(HitRecord hitRec) {
		
	    Color result = new Color();
	 
		Iterator<Light> iter = Renderer.CurrentFrame.sceneLights.iterator();
		Light curLight = null;
		
		Vector3 viewDir = hitRec.hitOrigin.subtract(hitRec.hitPoint);
		viewDir.normalize();
		
		Vector3 normal = hitRec.hitNormal;
		
		while (iter.hasNext()) {
			curLight = iter.next();
			
			Vector3 lightDir = curLight.getPosition().subtract(hitRec.hitPoint);
			lightDir.normalize();
			
			double specular = 0.0f;
			
			if (lightDir.dot(normal) > 0.0f) {
				Vector3 halfDir = lightDir.add(viewDir).getNormalized();
				double specAngle = Math.max(halfDir.dot(normal), 0.0f);
				specular = Math.pow(specAngle, shininess);
			}
			result = result.add(curLight.getColor().multiply(specular));
		}
		result.clamp();
		return result.add(super.getColor(hitRec));
	}

	@Override
	protected String constructXMLString() {
		return super.constructXMLString() + " shininess=\"" + shininess + "\"";
	}

	@Override
	public String getTypeName() {
		return InternalStrings.BlinnShaderTypeName;
	}

	@Override
	public String getDisplayTypeName() {
		return DisplayStrings.BlinnShaderName;
	}
	
	public double getShininess() {
		return shininess;
	}
	
	public void setShininess(double shine) {
		shininess = shine;
	}
}
