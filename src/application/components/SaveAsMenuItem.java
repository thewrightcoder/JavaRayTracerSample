package application.components;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.Toolkit;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import application.Utilities;
import dataTypes.baseClassDataTypes.Scene;

public class SaveAsMenuItem extends JMenuItem {
	public final static long serialVersionUID = 1L;

	protected File fileToSave = null;
	private final String[] options = { "Overwrite", "Cancel" };
	
	protected class SaveAsActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent evt) {
			FileDialog dialog = new FileDialog();
			int result = dialog.showSaveDialog(SaveAsMenuItem.this);
			if (result == JFileChooser.APPROVE_OPTION) {
				fileToSave = dialog.getSelectedFile();
				if (fileToSave.exists()) {
					int choice = JOptionPane.showOptionDialog(SaveAsMenuItem.this, 
																"File already exists. Overwrite file?", 
																"Warning", 
																JOptionPane.DEFAULT_OPTION, 
																JOptionPane.WARNING_MESSAGE, 
																null, 
																options, 
																options[0]); 
					if (choice == JOptionPane.YES_OPTION) {
						doSave();
					}
				} else {
					doSave();
				}
			}
		}
	}
	
	public SaveAsMenuItem(String name, boolean doFullSetup) {
		super(name);
		if (doFullSetup) {
			setMnemonic(KeyEvent.VK_S);
			setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask() + ActionEvent.SHIFT_MASK));
			setToolTipText("Save File With New Name");
			addActionListener(new SaveAsActionListener());
		}
	}
	
	protected void doSave() {
		BufferedWriter output = null;
		try {
			if (Utilities.getFileExtension(fileToSave).compareToIgnoreCase("rml") != 0) {
				String fileName = fileToSave.getAbsolutePath();
				Utilities.removeFileExtension(fileName);
				fileName += ".rml";
				fileToSave = new File(fileName);
			}
			output = new BufferedWriter(new FileWriter(fileToSave));
            output.write(Scene.TheScene().getXML());
            output.close();
            Scene.TheScene().setSceneName(Utilities.removeFileExtension(fileToSave.getName()));
            System.out.println(String.format("Saved RML file as %s", fileToSave.getName()));
		} catch (IOException e) {
			System.out.println(String.format("IOException writing file named %s", fileToSave.getName()));
			e.printStackTrace();
		} 
	}
}
