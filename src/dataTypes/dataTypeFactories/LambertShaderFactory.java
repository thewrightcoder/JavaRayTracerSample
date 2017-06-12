package dataTypes.dataTypeFactories;

import org.w3c.dom.Element;

import application.InternalStrings;
import dataTypes.shaderDataTypes.LambertShader;
import dataTypes.shaderDataTypes.Shader;

public class LambertShaderFactory extends ShaderFactory {
	public Shader getShader(Element element) {
		if (element.getAttribute("type").equalsIgnoreCase(InternalStrings.LambertShaderTypeName)) {
			return new LambertShader(element);
		}
		return null;
	}
	
	@Override
	public Shader getShader(String type) {
		if (type.equalsIgnoreCase(InternalStrings.LambertShaderTypeName)) {
			return new LambertShader(null);
		}
		return null;
	}
}
