package dataTypes.dataTypeFactories;

import org.w3c.dom.Element;

import application.InternalStrings;
import dataTypes.shaderDataTypes.ReflectiveShader;
import dataTypes.shaderDataTypes.Shader;

public class ReflectiveShaderFactory extends ShaderFactory {

	@Override
	public Shader getShader(Element element) {
		if (element.getAttribute("type").equalsIgnoreCase(InternalStrings.ReflectiveShaderTypeName)) {
			return new ReflectiveShader(element);
		}
		return null;
	}

	@Override
	public Shader getShader(String type) {
		if (type.equalsIgnoreCase(InternalStrings.ReflectiveShaderTypeName)) {
			return new ReflectiveShader(null);
		}
		return null;
	}

}
