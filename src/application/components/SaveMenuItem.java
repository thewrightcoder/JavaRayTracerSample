package application.components;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.KeyStroke;
import dataTypes.baseClassDataTypes.Scene;

public class SaveMenuItem extends SaveAsMenuItem {

	private static final long serialVersionUID = 1L;

	protected class SaveActionListener extends SaveAsActionListener {
		@Override
		public void actionPerformed(ActionEvent evt) {
			if (Scene.TheScene().getSceneName().isEmpty()) {
				Scene.TheScene().createEmptyScene(true);
				super.actionPerformed(evt);
			} else {
				String fileName = Scene.TheScene().getSceneName();

				fileToSave = new File(fileName);
				doSave();
			}
		}
	}
	
	public SaveMenuItem() {
		super("Save", false);
		
		setMnemonic(KeyEvent.VK_S);
		setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		setToolTipText("Save File");
	
		addActionListener(new SaveActionListener());
	}
}
