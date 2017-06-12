package application.components;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import application.GuiWindow;
import application.Utilities;
import dataTypes.baseClassDataTypes.Scene;

public class OpenFileMenuItem extends JMenuItem {
	public static final long serialVersionUID = 1L;
	public OpenFileMenuItem() {
		super("Open...");
		setMnemonic(KeyEvent.VK_O);
		setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		setToolTipText("Open A File");
		addActionListener(new ActionListener() {
			@Override
				public void actionPerformed(ActionEvent evt) {
					FileDialog dialog = new FileDialog();
					int result = dialog.showOpenDialog(OpenFileMenuItem.this);
					if (result == JFileChooser.APPROVE_OPTION) {
						File f = dialog.getSelectedFile();
						try {
							Scene.TheScene().createEmptyScene(false);
							Scene.TheScene().setSceneName(Utilities.removeFileExtension(f.getAbsolutePath()));
							Scene.TheScene().parseSceneData(f.getCanonicalPath());
							GuiWindow.Get().updateRenderButtonState();
							GuiWindow.Get().setAddObjectButtonEnabled(true);
						} catch (IOException e) {
							System.out.println(String.format("IOException opening file named %s", f.getAbsolutePath()));
							e.printStackTrace();
						}
					}
		      }
		   });
	}
	
}
