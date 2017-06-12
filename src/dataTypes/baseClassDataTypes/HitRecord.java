package dataTypes.baseClassDataTypes;

import dataTypes.geometryDataTypes.Geometry;

public class HitRecord {
	public Geometry hitGeometry = null;
	public Vector3 hitPoint = null;
	public Vector3 hitNormal = null;
	public Vector3 hitOrigin = null;
	public double hitDistance = 0.0f;
	public double bias = 0.00000001f;
	public int traceDepth = 5;
}
