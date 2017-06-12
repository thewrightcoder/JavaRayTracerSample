package dataTypes.dataTypeFactories;

import org.w3c.dom.Element;

import application.InternalStrings;
import dataTypes.lightDataTypes.Light;
import dataTypes.lightDataTypes.SpotLight;

public class SpotLightFactory extends LightFactory {
	public Light getLight(Element element) {
		if (element.getAttribute("type").equalsIgnoreCase(InternalStrings.SpotLightTypeName)) {
			return new SpotLight(element);
		}
		return null;
	}
	
	public Light getLight(String type) {
		if (type.equalsIgnoreCase(InternalStrings.SpotLightTypeName)) {
			return new SpotLight();
		}
		return null;
	}
}
