package dataTypes.dataTypeFactories;

import org.w3c.dom.Element;

import dataTypes.lightDataTypes.Light;

public abstract class LightFactory {
	public abstract Light getLight(Element element);
	public abstract Light getLight(String type);
}
