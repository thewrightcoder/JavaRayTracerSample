package dataTypes.baseClassDataTypes;

public class Ray {
	private Vector3[] ray = { null, null };
	public Ray() {
		
	}
	
	public Ray(Vector3 v1, Vector3 v2) {
		ray[0] = v1;
		ray[1] = v2;
	}
	
	public Ray(Ray r) {
		ray[0] = r.ray[0];
		ray[1] = r.ray[1];
	}
	
	public Vector3 origin() {
		return ray[0];
	}
	
	public Vector3 direction() {
		return ray[1];
	}
	
	public Vector3 pointAtT(double t) {
		// point = p0 + t*p1
		return (ray[0].add(ray[1].multiply(t)));
	}
}
