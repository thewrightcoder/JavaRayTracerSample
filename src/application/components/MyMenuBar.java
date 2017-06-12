package application.components;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

public class MyMenuBar extends JMenuBar {
	public static final long serialVersionUID = 1L;
	
	public MyMenuBar() {
		JMenu mnFile = new JMenu("File");
		add(mnFile);
		
		JMenuItem mntmNewFile = new NewFileMenuItem();
		mnFile.add(mntmNewFile);
		
		JMenuItem mntmLoad = new OpenFileMenuItem();
		mnFile.add(mntmLoad);
		
		JMenuItem mntmSave = new SaveMenuItem();
		mnFile.add(mntmSave);
		
		JMenuItem mntmSaveAs = new SaveAsMenuItem("Save As...", true);
		mnFile.add(mntmSaveAs);
		
		mnFile.add(new JSeparator());
		
		JMenuItem mntmAbout = new AboutFileMenuItem();
		mnFile.add(mntmAbout);
		
		mnFile.add(new JSeparator());
		
		JMenuItem mntmQuit = new QuitMenuItem();
		mnFile.add(mntmQuit);
	}
}
