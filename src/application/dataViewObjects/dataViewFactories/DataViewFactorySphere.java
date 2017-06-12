package application.dataViewObjects.dataViewFactories;

import application.InternalStrings;
import application.dataViewObjects.DataViewObject;
import application.dataViewObjects.geometry.DataViewObjectSphere;

public class DataViewFactorySphere extends DataViewFactory {

	@Override
	public DataViewObject getDataViewObject(String type, Object data) {
		if (type.compareToIgnoreCase(InternalStrings.SphereTypeName) == 0) {
			return new DataViewObjectSphere(data);
		}
		return null;
	}

}
