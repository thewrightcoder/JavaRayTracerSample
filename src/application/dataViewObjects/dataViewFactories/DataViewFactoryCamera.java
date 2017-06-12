package application.dataViewObjects.dataViewFactories;

import application.InternalStrings;
import application.dataViewObjects.DataViewObject;
import application.dataViewObjects.baseClasses.DataViewObjectCamera;

public class DataViewFactoryCamera extends DataViewFactory {

	@Override
	public DataViewObject getDataViewObject(String type, Object data) {
		if (type.compareToIgnoreCase(InternalStrings.CameraTypeName) == 0) {
			return new DataViewObjectCamera(data);
		}
		return null;
	}

}
