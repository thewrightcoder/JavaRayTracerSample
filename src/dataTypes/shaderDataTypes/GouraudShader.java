package dataTypes.shaderDataTypes;

import org.w3c.dom.Element;

import application.DisplayStrings;
import application.InternalStrings;
import dataTypes.baseClassDataTypes.Color;
import dataTypes.baseClassDataTypes.HitRecord;

public class GouraudShader extends Shader {
	
	public GouraudShader(Element element) {
		super(element);
	}
	
	public String toString() {
		return String.format("\tGouraud Shader%n\t%s%n\t\tColor is determined by the normal of the surface at a given point, "
				+ 				"%n\t\tin the form of R=X, G=Y, B=Z.", super.toString());
	}
	
	public Color getColor(HitRecord hitRec) {
		Color theColor = new Color();
		theColor.R = Math.abs(hitRec.hitNormal.x);
		theColor.G = Math.abs(hitRec.hitNormal.y);
		theColor.B = Math.abs(hitRec.hitNormal.z);
		
		return theColor;
	}
	
	protected String constructXMLString() {
		return "type=\"Gouraud\"";
	}

	@Override
	public String getTypeName() {
		return InternalStrings.GouraudShaderTypeName;
	}

	@Override
	public String getDisplayTypeName() {
		return DisplayStrings.GouraudShaderName;
	}
}
