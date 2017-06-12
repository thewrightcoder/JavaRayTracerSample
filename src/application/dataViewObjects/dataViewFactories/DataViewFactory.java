package application.dataViewObjects.dataViewFactories;

import application.dataViewObjects.DataViewObject;

public abstract class DataViewFactory {

	public abstract DataViewObject getDataViewObject(String type, Object data);
}
