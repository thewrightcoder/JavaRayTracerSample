package dataTypes.shaderDataTypes;

import org.w3c.dom.Element;

import application.DisplayStrings;
import application.InternalStrings;
import dataTypes.baseClassDataTypes.Color;
import dataTypes.baseClassDataTypes.HitRecord;

public class FlatShader extends Shader{

	protected Color color = null;
	
	public FlatShader(Element element) {
		super(element);
		if (element != null) {
			color = Color.parseColor(element.getAttribute("color"));
		} else {
			color = new Color(0.5f, 0.5f, 0.5f);
		}
	}
	
	public String toString() {
		return String.format("\tFlat Shader%n\t%s%n\t%s", super.toString(), color.toString());
	}
	
	public Color getColor(HitRecord hitRec) {
		return color;
	}
	
	public Color getBaseColor() {
		return color;
	}
	
	protected String constructXMLString() {
		return String.format("type=\"Flat\" color=%s", color.getXML());
	}

	@Override
	public String getTypeName() {
		return InternalStrings.FlatShaderTypeName;
	}

	@Override
	public String getDisplayTypeName() {
		return DisplayStrings.FlatShaderName;
	}
}
