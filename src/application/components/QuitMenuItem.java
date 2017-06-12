package application.components;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class QuitMenuItem extends JMenuItem {
	public static final long serialVersionUID = 1L;
	
	public QuitMenuItem() {
		super("Quit");
		setMnemonic(KeyEvent.VK_Q);
		setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		setToolTipText("Exit Application");
		addActionListener(new ActionListener() {
			@Override
				public void actionPerformed(ActionEvent evt) {
					System.exit(0);
		      }
		   });
	}
}
