package application.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import dataTypes.baseClassDataTypes.Renderer;
import dataTypes.baseClassDataTypes.Scene;

public class RenderButton extends JButton {
	
	static final long serialVersionUID = 1L;
	public RenderButton() {
		super("Render Button");
		setText("Render");
		addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				if (Scene.TheScene().getSceneName().isEmpty()) {
					JOptionPane.showMessageDialog(null, 
							"This scene has no name.  Please save the scene before attempting to render it.", 
							"Invalid Scene Name", 
							JOptionPane.ERROR_MESSAGE);
				}
				else {
					Renderer.renderScene();
				}
			}
		});
		
		setEnabled(false);
	}
}
