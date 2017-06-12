package dataTypes.dataTypeFactories;

import org.w3c.dom.Element;

import dataTypes.shaderDataTypes.Shader;

public abstract class ShaderFactory {
	public abstract Shader getShader(Element element);
	public abstract Shader getShader(String type);
}
