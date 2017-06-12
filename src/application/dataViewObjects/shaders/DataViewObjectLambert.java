package application.dataViewObjects.shaders;

import application.dataViewObjects.baseClasses.DataViewObjectColor;
import dataTypes.shaderDataTypes.LambertShader;

public class DataViewObjectLambert extends DataViewObjectShader {

	private static final long serialVersionUID = 1L;

	public DataViewObjectLambert(Object o) {
		super(o);
		LambertShader s = (LambertShader)o;
		
		add(new DataViewObjectColor(s.getBaseColor()));
	}
}
