package dataTypes.baseClassDataTypes;

import org.w3c.dom.Element;

public class Camera {
	protected Vector3 position;
	protected Vector3 up;
	protected Vector3 left;
	protected Vector3 lookat; // The position in 3d space this camera is looking
	protected Vector3 dir; // The normalized vector pointing from the camera's position to lookat
	protected double fstop = 0.0f;
	protected double FOV = 60.0f;
	protected double nearClip;
	protected double farClip;
	
	
	public Camera(Element element) {
		fstop = Double.parseDouble(element.getAttribute("fstop"));
		nearClip = Double.parseDouble(element.getAttribute("nearClip"));
		farClip = Double.parseDouble(element.getAttribute("farClip"));
		
		position = Vector3.parseVec(element.getAttribute("position"));
		lookat = Vector3.parseVec(element.getAttribute("lookat"));
		up = Vector3.parseVec(element.getAttribute("up"));
		
		calculateDir();
	}
	
	public Camera() {
		fstop = 0.0f;
		nearClip = 0.01f;
		farClip = 100000.0f;
		
		up = new Vector3(0, 1 , 0);
		lookat = new Vector3(0, 0, 0);
		position = new Vector3(0, 0, -10);
		
		calculateDir();
	}

	public Vector3 getUp() {
		return up;
	}
	
	public Vector3 getDir() {
		return dir;
	}
	
	public void calculateDir() {
		dir = lookat.subtract(position).getNormalized();
		// Side effects! :(
		// Changing 'dir' changes what left is, so do this too.
		calculateLeft();
	}
	
	public Vector3 getPosition() {
		return position;
	}
	
	public Vector3 getLookat() {
		return lookat;
	}
	
	public Vector3 getLeft() {
		return left;
	}
	
	public void calculateLeft() {
		left = dir.cross(up);
	}
	
	public double getFOV() {
		return FOV;
	}
	
	public double getNearClip() {
		return nearClip;
	}
	
	public void setNearClip(double clip) {
		nearClip = clip;
	}
	
	public double getFarClip() {
		return farClip;
	}
	
	public void setFarClip(double clip) {
		farClip = clip;
	}
	
	public double getFStop() {
		return fstop;
	}
	
	public void setFStop(double value) {
		fstop = value;
	}
	
	public String getXML() {
		return String.format("<Camera nearClip=\"%f\" farClip=\"%f\" fstop=\"%f\" position=%s lookat=%s up=%s />", nearClip, farClip, fstop, position.getXML(), lookat.getXML(), up.getXML());
	}
}
