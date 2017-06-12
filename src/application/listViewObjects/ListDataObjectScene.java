package application.listViewObjects;

import javax.swing.tree.DefaultMutableTreeNode;

import application.treeViewObjects.TreeViewObjectScene;

public class ListDataObjectScene extends ListDataObject {
	
	public ListDataObjectScene() {
	}
	
	public String toString() {
		return "Scene";
	}
	
	@Override
	public DefaultMutableTreeNode getTreeView() {
		return new TreeViewObjectScene();
	}

}
