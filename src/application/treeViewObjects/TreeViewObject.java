package application.treeViewObjects;

import javax.swing.tree.DefaultMutableTreeNode;

public class TreeViewObject extends DefaultMutableTreeNode {

	private static final long serialVersionUID = 1L;
	
	protected Object o = null;
	protected String typeName = null;
	protected String displayName = null;
	
	public TreeViewObject(Object obj, String dn, String tn) {
		o = obj;
		displayName = dn;
		typeName = tn;
	}
	
	public String toString() {
		return displayName;
	}
	
	final public Object getObject() {
		return o;
	}
	
	final public String getTypeName() {
		return typeName;
	}
	
	final public String getDisplayName() {
		return displayName;
	}
}
