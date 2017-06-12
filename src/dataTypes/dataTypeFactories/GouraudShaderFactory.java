package dataTypes.dataTypeFactories;

import org.w3c.dom.Element;

import application.InternalStrings;
import dataTypes.shaderDataTypes.GouraudShader;
import dataTypes.shaderDataTypes.Shader;

public class GouraudShaderFactory extends ShaderFactory {
	public Shader getShader(Element element) {
		if (element.getAttribute("type").equalsIgnoreCase(InternalStrings.GouraudShaderTypeName)) {
			return new GouraudShader(element);
		}
		return null;
	}
	
	@Override
	public Shader getShader(String type) {
		if (type.equalsIgnoreCase(InternalStrings.GouraudShaderTypeName)) {
			return new GouraudShader(null);
		}
		return null;
	}
}
