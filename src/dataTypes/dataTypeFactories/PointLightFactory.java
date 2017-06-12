package dataTypes.dataTypeFactories;

import org.w3c.dom.Element;

import application.InternalStrings;
import dataTypes.lightDataTypes.Light;
import dataTypes.lightDataTypes.PointLight;

public class PointLightFactory extends LightFactory {
	public Light getLight(Element element) {
		if (element.getAttribute("type").equalsIgnoreCase(InternalStrings.PointLightTypeName)) {
			return new PointLight(element);
		}
		return null;
	}
	
	public Light getLight(String type) {
		if (type.equalsIgnoreCase(InternalStrings.PointLightTypeName)) {
			return new PointLight();
		}
		return null;
	}
}
