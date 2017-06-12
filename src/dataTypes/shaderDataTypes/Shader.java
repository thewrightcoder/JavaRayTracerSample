package dataTypes.shaderDataTypes;

import org.w3c.dom.Element;

import dataTypes.baseClassDataTypes.Color;
import dataTypes.baseClassDataTypes.HitRecord;

abstract public class Shader {
	private String name;
	
	public Shader(Element element) {
		if (element != null) {
			name = element.getAttribute("name");
		} else {
			name = "New " + getDisplayTypeName();
		}
	}
	
	abstract public Color getColor(HitRecord hitRec);
	
	public String toString() {
		return String.format("Shader Name:\t%s", name);
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String newName) {
		name = newName;
	}
	
	public String getXML() {
		return String.format("<Shader name=\"%s\" %s />", name, constructXMLString());
	}
	
	protected abstract String constructXMLString();
	
	public abstract String getTypeName();
	
	public abstract String getDisplayTypeName();
}
