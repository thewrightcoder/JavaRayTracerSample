package application.components;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import application.GuiWindow;
import dataTypes.baseClassDataTypes.Scene;

public class NewFileMenuItem extends JMenuItem {
	public final static long serialVersionUID = 1L;
	
	public NewFileMenuItem() {
		super("New");
		setMnemonic(KeyEvent.VK_N);
		setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		setToolTipText("Create a New Empty Scene");
		addActionListener(new ActionListener() {
			@Override
				public void actionPerformed(ActionEvent evt) {
					Scene.TheScene().createEmptyScene(true);
					GuiWindow.Get().setAddObjectButtonEnabled(true);
		      }
		   });
	}
}
