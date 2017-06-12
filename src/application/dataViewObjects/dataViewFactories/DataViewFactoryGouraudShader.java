package application.dataViewObjects.dataViewFactories;

import application.InternalStrings;
import application.dataViewObjects.DataViewObject;
import application.dataViewObjects.shaders.DataViewObjectGouraudShader;

public class DataViewFactoryGouraudShader extends DataViewFactory {

	@Override
	public DataViewObject getDataViewObject(String type, Object data) {
		if (type.compareToIgnoreCase(InternalStrings.GouraudShaderTypeName) == 0) {
			return new DataViewObjectGouraudShader(data);
		}
		return null;
	}
}
