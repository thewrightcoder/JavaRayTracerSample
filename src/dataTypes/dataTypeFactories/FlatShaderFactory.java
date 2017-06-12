package dataTypes.dataTypeFactories;

import org.w3c.dom.Element;

import application.InternalStrings;
import dataTypes.shaderDataTypes.FlatShader;
import dataTypes.shaderDataTypes.Shader;

public class FlatShaderFactory extends ShaderFactory {

	public Shader getShader(Element element) {
		if (element.getAttribute("type").equalsIgnoreCase(InternalStrings.FlatShaderTypeName)) {
			return new FlatShader(element);
		}
		return null;
	}
	
	@Override
	public Shader getShader(String type) {
		if (type.equalsIgnoreCase(InternalStrings.FlatShaderTypeName)) {
			return new FlatShader(null);
		}
		return null;
	}
}
