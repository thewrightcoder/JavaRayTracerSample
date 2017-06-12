package application.treeViewObjects;

import dataTypes.shaderDataTypes.Shader;

public class TreeViewObjectShader extends TreeViewObject {

	private String typeNameDisplay = null;
	private Shader shader = null;
	
	private static final long serialVersionUID = 1L;

	public TreeViewObjectShader(Object obj, String dn, String tn, String tnd) {
		super(obj, dn, tn);
		shader = (Shader)obj;
		typeNameDisplay = tnd;
	}
	
	public String toString() {
		return shader.getName() + " - a " + typeNameDisplay;
	}
}
