package application.dataViewObjects.dataViewFactories;

import application.InternalStrings;
import application.dataViewObjects.DataViewObject;
import application.dataViewObjects.lights.DataViewObjectPointLight;

public class DataViewFactoryPointLight extends DataViewFactory {

	@Override
	public DataViewObject getDataViewObject(String type, Object data) {
		if (type.compareToIgnoreCase(InternalStrings.PointLightTypeName) == 0) {
			return new DataViewObjectPointLight(data);
		}
		return null;
	}
}
