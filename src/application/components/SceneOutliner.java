package application.components;

import java.util.Iterator;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

import application.listViewObjects.ListDataObject;
import application.listViewObjects.ListDataObjectFrame;
import application.listViewObjects.ListDataObjectScene;
import dataTypes.baseClassDataTypes.Frame;
import dataTypes.baseClassDataTypes.Scene;

public class SceneOutliner extends JList<ListDataObject> {
	public static final long serialVersionUID = 1L;
	private DefaultListModel<ListDataObject> model = null;
	
	public SceneOutliner() {
		super();
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		setLayoutOrientation(JList.VERTICAL);
		model = new DefaultListModel<ListDataObject>();
		setModel(model);
	}
	
	public void resetSceneOutliner() {
		if (model.getSize() > 0) {
			clearSelection();
			model.removeAllElements();
		}
		model.addElement(new ListDataObjectScene());
		Iterator<Frame> frameIter = Scene.TheScene().getFrames().iterator();
		while (frameIter.hasNext()) {
			model.addElement(new ListDataObjectFrame(frameIter.next()));
		}
	}
	
	public void addFrameEntry(Frame f) {
		model.addElement(new ListDataObjectFrame(f));
	}
}
