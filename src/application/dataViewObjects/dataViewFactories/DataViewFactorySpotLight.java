package application.dataViewObjects.dataViewFactories;

import application.InternalStrings;
import application.dataViewObjects.DataViewObject;
import application.dataViewObjects.lights.DataViewObjectSpotLight;

public class DataViewFactorySpotLight extends DataViewFactory {

	@Override
	public DataViewObject getDataViewObject(String type, Object data) {
		if (type.compareToIgnoreCase(InternalStrings.SpotLightTypeName) == 0) {
			return new DataViewObjectSpotLight(data);
		}
		return null;
	}

}
