package application.treeViewObjects;

import java.util.Iterator;

import javax.swing.tree.DefaultMutableTreeNode;

import application.DisplayStrings;
import application.InternalStrings;
import dataTypes.baseClassDataTypes.Scene;
import dataTypes.shaderDataTypes.Shader;

public class TreeViewObjectScene extends TreeViewObject {

	private static final long serialVersionUID = 1L;
	
	public TreeViewObjectScene() {
		super(Scene.TheScene(), DisplayStrings.SceneName, InternalStrings.SceneTypeName);
		Scene s = Scene.TheScene();
		
		DefaultMutableTreeNode shaders = null;
		
		add(new TreeViewObject(null, DisplayStrings.SceneDataName, InternalStrings.SceneDataTypeName));
		
		shaders = new DefaultMutableTreeNode(DisplayStrings.ShadersName);
		add(shaders);
		Shader shader = null;
		Iterator<Shader> iter = s.getShaderList().iterator();
		while(iter.hasNext()) {
			shader = iter.next();
			shaders.add(new TreeViewObjectShader(shader, shader.getName(), shader.getTypeName(), shader.getDisplayTypeName()));
		}
	}
}
