package application.dataViewObjects.baseClasses;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import application.DisplayStrings;
import application.dataViewObjects.DataViewObject;
import dataTypes.baseClassDataTypes.Scene;

public class DataViewObjectSceneData extends DataViewObject {

	private static final long serialVersionUID = 1L;

	public DataViewObjectSceneData() {
		super();
		
		Scene s = Scene.TheScene();
		
		DataViewObjectDimension imageSize = new DataViewObjectDimension(DisplayStrings.ImageSizeName, s.getImageWidth(), s.getImageHeight());
		imageSize.addXValueChangedListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				s.setImageWidth(((DataViewObjectDimension)e.getSource()).getXValue());
			}
		});
		imageSize.addYValueChangedListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				s.setImageHeight(((DataViewObjectDimension)e.getSource()).getYValue());
			}
		});
		
		
		DataViewObjectInt aaSamples = new DataViewObjectInt(DisplayStrings.AntiAliasSamplesName, s.getRawAntiAliasSamples());
		aaSamples.addValueChangedListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				s.updateAntiAliasSamples(((DataViewObjectInt)e.getSource()).getValue());
			}
		});
		
		DataViewObjectInt ssSamples = new DataViewObjectInt(DisplayStrings.SoftShadowSamplesName, s.getSoftShadowSampleCount());
		ssSamples.addValueChangedListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				s.setSoftShadowSampleCount(((DataViewObjectInt)e.getSource()).getValue());
			}
		});
		
		
		add(imageSize);
		add(aaSamples);
		add(ssSamples);
	}
}
