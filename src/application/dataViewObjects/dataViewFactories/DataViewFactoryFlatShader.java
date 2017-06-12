package application.dataViewObjects.dataViewFactories;

import application.InternalStrings;
import application.dataViewObjects.DataViewObject;
import application.dataViewObjects.shaders.DataViewObjectFlatShader;

public class DataViewFactoryFlatShader extends DataViewFactory {

	@Override
	public DataViewObject getDataViewObject(String type, Object data) {
		if (type.compareToIgnoreCase(InternalStrings.FlatShaderTypeName) == 0) {
			return new DataViewObjectFlatShader(data);
		}
		return null;
	}
}
