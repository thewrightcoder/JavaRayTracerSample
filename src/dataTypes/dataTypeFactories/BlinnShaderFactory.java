package dataTypes.dataTypeFactories;

import org.w3c.dom.Element;

import application.InternalStrings;
import dataTypes.shaderDataTypes.BlinnShader;
import dataTypes.shaderDataTypes.Shader;

public class BlinnShaderFactory extends ShaderFactory {

	@Override
	public Shader getShader(Element element) {
		if (element.getAttribute("type").equalsIgnoreCase(InternalStrings.BlinnShaderTypeName)) {
			return new BlinnShader(element);
		}
		return null;
	}
	
	@Override
	public Shader getShader(String type) {
		if (type.equalsIgnoreCase(InternalStrings.BlinnShaderTypeName)) {
			return new BlinnShader(null);
		}
		return null;
	}
}
