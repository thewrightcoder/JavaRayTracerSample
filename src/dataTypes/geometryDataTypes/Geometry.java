package dataTypes.geometryDataTypes;

import java.util.List;

import org.w3c.dom.Element;

import dataTypes.baseClassDataTypes.Color;
import dataTypes.baseClassDataTypes.HitRecord;
import dataTypes.baseClassDataTypes.Ray;
import dataTypes.baseClassDataTypes.Scene;
import dataTypes.baseClassDataTypes.Vector3;
import dataTypes.shaderDataTypes.Shader;

// Base Geometry class.  All scene objects (not lights or materials) extend from this.
public abstract class Geometry {
	protected Vector3 position = null;
	protected Shader shader = null;
	
	public Geometry(Element element) {
		position = Vector3.parseVec(element.getAttribute("position"));
		
		updateShaderFromName(element.getAttribute("shaderName"));
	}
	
	public Geometry() {
		position = new Vector3();
		
		// Default to the first shader, if there is one.
		List<Shader> shaders = Scene.TheScene().getShaderList();
		if (shaders != null && shaders.size() > 0) {
			shader = Scene.TheScene().getShaderList().get(0);
		}
	}

	public abstract double checkForHit(Ray ray, double nearClip, double farClip);
	
	public abstract void getNormalAtPoint(HitRecord hitRec);
	
	public final boolean checkForShadowHit(Ray r, double nearClip, double farClip) {
		if (nearClip < farClip && nearClip > 0) {
			double test = checkForHit(r, nearClip, farClip);
			if (test < nearClip || test > farClip) {
				return false;
			}
		}
		return true;
	}
	
	public String toString() {
		return String.format("\tPosition:\t%s%n\t\tShader Name: \t%s", 
				position.toString(), 
				shader != null ? shader.getName() : "Shader is null!");
	}
	
	public Color getColor(HitRecord hitRec) {
		if (shader != null) {
			return shader.getColor(hitRec);
		}
		else {
			System.out.println("Shader is null.  Returning flat white color.");
			return new Color(1,1,1);
		}
	}
	
	public String getXML() {
		return String.format("<Geometry %s position=%s shaderName=\"%s\" />", constructXMLString(), position.getXML(), shader != null ? shader.getName() : "");
	}
	
	protected abstract String constructXMLString();
	
	public abstract String getTypeName();
	
	public Vector3 getPosition() {
		return position;
	}
	
	public Shader getShader() {
		return shader;
	}
	
	public final void updateShaderFromName(String name) {
		if (name == null) {
			shader = null;
		} else {
			shader = Scene.TheScene().getShader(name);
		}
	}
}
