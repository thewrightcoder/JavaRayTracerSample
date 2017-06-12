package application.dataViewObjects.shaders;

import java.awt.Dimension;

import javax.swing.JLabel;

import application.GuiWindow;

public class DataViewObjectGouraudShader extends DataViewObjectShader {

	private static final long serialVersionUID = 1L;

	public DataViewObjectGouraudShader(Object o) {
		super(o);
		
		JLabel text = new JLabel();
		text.setText("A Gouraud shader's color is defined by the normal at the hit location, in the form of X->R, Y->G, and Z->B.  It has no settings other than it's name.");
		text.setPreferredSize(new Dimension(GuiWindow.Get().getDataViewerRealWidth(), GuiWindow.Get().getDataViewerRealWidth()));
		text.setMaximumSize(text.getPreferredSize());
		
		add(text);
	}
}
