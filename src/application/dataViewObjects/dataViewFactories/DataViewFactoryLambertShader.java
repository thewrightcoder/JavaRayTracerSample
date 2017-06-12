package application.dataViewObjects.dataViewFactories;

import application.InternalStrings;
import application.dataViewObjects.DataViewObject;
import application.dataViewObjects.shaders.DataViewObjectLambert;

public class DataViewFactoryLambertShader extends DataViewFactory {

	@Override
	public DataViewObject getDataViewObject(String type, Object data) {
		if (type.compareToIgnoreCase(InternalStrings.LambertShaderTypeName) == 0) {
			return new DataViewObjectLambert(data);
		}
		return null;
	}
}
