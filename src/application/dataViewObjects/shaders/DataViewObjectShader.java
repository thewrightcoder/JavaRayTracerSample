package application.dataViewObjects.shaders;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import application.GuiWindow;
import application.dataViewObjects.DataViewObject;
import application.dataViewObjects.baseClasses.DataViewObjectString;
import dataTypes.shaderDataTypes.Shader;

public class DataViewObjectShader extends DataViewObject {

	private static final long serialVersionUID = 1L;
	
	public DataViewObjectShader(Object o) {
		super();
		
		Shader s = (Shader)o;
		
		DataViewObjectString str = new DataViewObjectString("Shader Name", s.getName());
		str.addValueChangedListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				s.setName(((DataViewObjectString)e.getSource()).getValue());
				GuiWindow.Get().updateTreeViewNode(o);
			}
		});
		
		add(str);
	}
}
