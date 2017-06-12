package application;

import application.GuiWindow;
import application.Utilities;

public class ApplicationMain {
	
	public static void main(String[] args) {
		Utilities.setIsWindowsSystem(System.getProperty("os.name").contains("Win"));
		System.setProperty("apple.laf.useScreenMenuBar", "true");
		System.setProperty("apple.awt.application.name", "JavaRayTracerCodeSample");
		GuiWindow.StartGUI();
	}
}
