package application.dataViewObjects.shaders;

import application.dataViewObjects.baseClasses.DataViewObjectColor;
import dataTypes.shaderDataTypes.FlatShader;

public class DataViewObjectFlatShader extends DataViewObjectShader {

	private static final long serialVersionUID = 1L;

	public DataViewObjectFlatShader(Object o) {
		super(o);

		FlatShader s = (FlatShader)o;
		
		add(new DataViewObjectColor(s.getBaseColor()));
	}
}
