package application.dataViewObjects.shaders;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import application.dataViewObjects.baseClasses.DataViewObjectNumber;
import dataTypes.shaderDataTypes.BlinnShader;

public class DataViewObjectBlinn extends DataViewObjectLambert {

	private static final long serialVersionUID = 1L;

	public DataViewObjectBlinn(Object o) {
		super(o);
		
		BlinnShader b = (BlinnShader)o;
		
		DataViewObjectNumber shininess = new DataViewObjectNumber("Shininess", b.getShininess());
		shininess.addValueChangedListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				b.setShininess(((DataViewObjectNumber)e.getSource()).getValue());;
			}
		});
		
		add(shininess);
	}
}
