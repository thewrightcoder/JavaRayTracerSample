package application.dataViewObjects.dataViewFactories;

import application.InternalStrings;
import application.dataViewObjects.DataViewObject;
import application.dataViewObjects.shaders.DataViewObjectBlinn;

public class DataViewFactoryBlinnShader extends DataViewFactory {

	@Override
	public DataViewObject getDataViewObject(String type, Object data) {
		if (type.compareToIgnoreCase(InternalStrings.BlinnShaderTypeName) == 0) {
			return new DataViewObjectBlinn(data);
		}
		return null;
	}

}
