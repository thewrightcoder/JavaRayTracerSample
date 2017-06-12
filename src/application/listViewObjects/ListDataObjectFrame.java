package application.listViewObjects;

import javax.swing.tree.DefaultMutableTreeNode;

import application.treeViewObjects.TreeViewObjectFrame;
import dataTypes.baseClassDataTypes.Frame;

public class ListDataObjectFrame extends ListDataObject {

	private Frame f;
	
	public ListDataObjectFrame(Frame frame) {
		f = frame;
	}
	
	public String toString() {
		return f.getDisplayName();
	}
	
	@Override
	public DefaultMutableTreeNode getTreeView() {
		return new TreeViewObjectFrame(f);
	}

}
