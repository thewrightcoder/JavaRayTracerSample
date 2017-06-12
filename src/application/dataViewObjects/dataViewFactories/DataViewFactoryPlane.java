package application.dataViewObjects.dataViewFactories;

import application.InternalStrings;
import application.dataViewObjects.DataViewObject;
import application.dataViewObjects.geometry.DataViewObjectPlane;

public class DataViewFactoryPlane extends DataViewFactory {

	@Override
	public DataViewObject getDataViewObject(String type, Object data) {
		if (type.compareToIgnoreCase(InternalStrings.PlaneTypeName) == 0) {
			return new DataViewObjectPlane(data);
		}
		return null;
	}

}
