package application.dataViewObjects.baseClasses;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import application.GuiWindow;
import application.dataViewObjects.DataViewObject;
import dataTypes.baseClassDataTypes.Camera;

public class DataViewObjectCamera extends DataViewObject {

	private static final long serialVersionUID = 1L;
	
	public DataViewObjectCamera(Object o) {
		super();
		Camera c = (Camera)o;
		
		DataViewObjectVector3 position = new DataViewObjectVector3("Position", c.getPosition());
		position.addValueChangedListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				c.calculateDir();
				GuiWindow.Get().renderPreview();
			}
		});
		
		DataViewObjectNumber nearClip = new DataViewObjectNumber("Near Clip", c.getNearClip());
		nearClip.addValueChangedListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				c.setNearClip(((DataViewObjectNumber)e.getSource()).getValue());
				GuiWindow.Get().renderPreview();
			}
		});
		
		DataViewObjectNumber farClip = new DataViewObjectNumber("Far Clip", c.getFarClip());
		farClip.addValueChangedListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				c.setFarClip(((DataViewObjectNumber)e.getSource()).getValue());
				GuiWindow.Get().renderPreview();
			}
		});
		
		DataViewObjectVector3 up = new DataViewObjectVector3("Up", c.getUp());
		up.addValueChangedListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				c.calculateDir();
				GuiWindow.Get().renderPreview();
			}
		});
		
		DataViewObjectVector3 lookat = new DataViewObjectVector3("Look At", c.getLookat());
		lookat.addValueChangedListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				c.calculateDir();
				GuiWindow.Get().renderPreview();
			}
		});
		
		DataViewObjectNumber fStop = new DataViewObjectNumber("fstop", c.getFStop());
		fStop.addValueChangedListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				c.setFStop(((DataViewObjectNumber)e.getSource()).getValue());
			}
		});
		
		add(position);
		add(up);
		add(lookat);
		add(nearClip);
		add(farClip);
		add(fStop);
	}
}
