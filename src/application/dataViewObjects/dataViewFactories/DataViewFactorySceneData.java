package application.dataViewObjects.dataViewFactories;

import application.InternalStrings;
import application.dataViewObjects.DataViewObject;
import application.dataViewObjects.baseClasses.DataViewObjectSceneData;

public class DataViewFactorySceneData extends DataViewFactory {
	@Override
	public DataViewObject getDataViewObject(String type, Object data) {
		if (type.compareToIgnoreCase(InternalStrings.SceneDataTypeName) == 0) {
			return new DataViewObjectSceneData();
		}
		return null;
	}
}
