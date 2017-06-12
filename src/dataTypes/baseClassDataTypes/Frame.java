package dataTypes.baseClassDataTypes;

import java.util.ArrayList;
import java.util.List;

import application.Utilities;
import dataTypes.geometryDataTypes.Geometry;
import dataTypes.lightDataTypes.Light;

public class Frame {
	
	// Image settings
	protected Camera camera;
	
	protected String displayName = "";
	
	public List<Geometry> sceneGeo = new ArrayList<Geometry>();
	public List<Light> sceneLights = new ArrayList<Light>();
	
	public Frame(int index, int total, boolean makeCamera) {
		displayName = Utilities.getFrameName(index, total, "Frame", " ", false);
		if (makeCamera) {
			camera = new Camera();
		}
	}
	
	public void setCamera(Camera c) {
		camera = c;
	}
	
	public Camera getCamera() {
		return camera;
	}
	
	public String getXML() {
		String result = String.format("\t<Frame>%n\t\t%s%n", camera.getXML());
		for (int i = 0; i < sceneLights.size(); ++i) {
			result = String.format("%s\t\t%s%n", result, sceneLights.get(i).getXML());
		}
		for (int i = 0; i < sceneGeo.size(); ++i) {
			result = String.format("%s\t\t%s%n", result, sceneGeo.get(i).getXML());
		}
		result = String.format("%s\t</Frame>", result);
		return result;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public void setDisplayName(String name) {
		displayName = name;
	}

	public void addGeometry(Geometry geo) {
		sceneGeo.add(geo);
	}
	
	public void addLight(Light light) {
		sceneLights.add(light);
	}
}
