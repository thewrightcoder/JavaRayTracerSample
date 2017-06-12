package application.components;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import application.Utilities;;

public class FileDialog extends JFileChooser {
	private static final long serialVersionUID = 1L;

	private class RMLFilter extends FileFilter {
		public boolean accept(File f) {
			if (f.isDirectory()) {
				return true;
			} else if (Utilities.getFileExtension(f).equalsIgnoreCase("rml")) {
				return true;
			}
			
			return false;
		}
		
		public String getDescription() {
			return "RML";
		}
	}
	
	public FileDialog() {
		super();
		setFileFilter(new RMLFilter());
		setAcceptAllFileFilterUsed(false);
		File workingDirectory = new File(System.getProperty("user.dir"));
		setCurrentDirectory(workingDirectory);
	}
}
