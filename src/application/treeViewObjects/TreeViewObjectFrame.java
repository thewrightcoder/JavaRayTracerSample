package application.treeViewObjects;

import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

import application.DisplayStrings;
import application.InternalStrings;
import dataTypes.baseClassDataTypes.Frame;
import dataTypes.geometryDataTypes.Geometry;
import dataTypes.lightDataTypes.Light;

public class TreeViewObjectFrame extends TreeViewObject {

	private static final long serialVersionUID = 1L;
	
	Frame f = null;
	
	public TreeViewObjectFrame(Frame frame) {
		super(frame, frame.getDisplayName(), InternalStrings.FrameTypeName);
		f = frame;
		DefaultMutableTreeNode lights = new DefaultMutableTreeNode(DisplayStrings.LightsName);
		DefaultMutableTreeNode geo = new DefaultMutableTreeNode(DisplayStrings.GeometryName);
		
		List<Light> lightList = f.sceneLights;
		List<Geometry> geoList = f.sceneGeo; 
		
		Light l = null;
		String type = null;
		// TODO: Lights need a better display name than their type.
		for (int i = 0; i < lightList.size(); ++i) {
			l = lightList.get(i);
			type = l.getTypeName();
			lights.add(new TreeViewObject(l, type, type));
		}
		
		Geometry g = null;
		// TODO: geometry needs a better display name than it's type.
		for (int i = 0; i < geoList.size(); ++i) {
			g = geoList.get(i);
			type = g.getTypeName();
			geo.add(new TreeViewObject(g, type, type));
		}
		
		add(new TreeViewObject(f.getCamera(), DisplayStrings.CameraName, InternalStrings.CameraTypeName));
		add(lights);
		add(geo);		
	}
}
