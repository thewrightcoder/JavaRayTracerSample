package dataTypes.shaderDataTypes;

import org.w3c.dom.Element;

import application.DisplayStrings;
import application.InternalStrings;
import dataTypes.baseClassDataTypes.Color;
import dataTypes.baseClassDataTypes.HitRecord;
import dataTypes.baseClassDataTypes.Renderer;
import dataTypes.baseClassDataTypes.Vector3;

public class ReflectiveShader extends BlinnShader {

	public ReflectiveShader(Element element) {
		super(element);
	}

	@Override
	public Color getColor(HitRecord hitRec) {
		if (hitRec.traceDepth > 0) {
			
			Vector3 view = hitRec.hitPoint.subtract(hitRec.hitOrigin);
			view.normalize();
			// R = I−2(N⋅I)N
			Vector3 reflectiveDir = view.subtract(hitRec.hitNormal.multiply(hitRec.hitNormal.dot(view)*2));
			reflectiveDir.normalize();
			
			Color reflectColor = Renderer.trace(hitRec.hitPoint.add(reflectiveDir.multiply(0.1f)), reflectiveDir, hitRec.traceDepth);
			Color blinnColor = super.getColor(hitRec);

			return blinnColor.multiply(reflectColor);
		}
		return color;
	}

	@Override
	protected String constructXMLString() {
		// Reflective shaders don't have their own properties.
		return super.constructXMLString();
	}

	@Override
	public String getTypeName() {
		return InternalStrings.ReflectiveShaderTypeName;
	}

	@Override
	public String getDisplayTypeName() {
		return DisplayStrings.ReflectiveShaderName;
	}

}
