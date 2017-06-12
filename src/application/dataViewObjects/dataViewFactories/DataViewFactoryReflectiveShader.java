package application.dataViewObjects.dataViewFactories;

import application.InternalStrings;
import application.dataViewObjects.DataViewObject;
import application.dataViewObjects.shaders.DataViewObjectReflective;

public class DataViewFactoryReflectiveShader extends DataViewFactory {

	@Override
	public DataViewObject getDataViewObject(String type, Object data) {
		if (type.compareToIgnoreCase(InternalStrings.ReflectiveShaderTypeName) == 0) {
			return new DataViewObjectReflective(data);
		}
		return null;
	}

}
