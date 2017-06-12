package dataTypes.baseClassDataTypes;

import java.util.ArrayList;
import java.util.regex.*;

public class Vector3 {
	public double x, y, z;
	
	public Vector3() {
		x = 0.0f;
		y = 0.0f;
		z = 0.0f;
	}
	
	public Vector3(double _x) {
		x = _x;
		y = 0.0f;
		z = 0.0f;
	}
	
	public Vector3(double _x, double _y) {
		x = _x;
		y = _y;
		z = 0.0f;
	}
	
	public Vector3(double _x, double _y, double _z) {
		x = _x;
		y = _y;
		z = _z;
	}
	
	public Vector3(Vector3 other){
		x = other.x;
		y = other.y;
		z = other.z;
	}
	
	public static Vector3 parseVec(String text) {
		
		ArrayList < Double > myDoubles = new ArrayList < Double >();
		Matcher matcher = Pattern.compile( "[-+]?\\d*\\.?\\d+([eE][-+]?\\d+)?" ).matcher( text );

	    while ( matcher.find() )
	    {
	        double element = Double.parseDouble( matcher.group() );
	        myDoubles.add( element );
	    }
		
		Vector3 vec = new Vector3();
		vec.x = myDoubles.get(0);
		vec.y = myDoubles.get(1);
		vec.z = myDoubles.get(2);
		
		return vec;
	}
	
	public String toString() {
		return String.format("Vector3: < %f, %f, %f >", x, y, z);
	}
	
	public double length() {
		return Math.sqrt(lengthSquared());
	}
	
	public double lengthSquared() {
		return x * x + y * y + z * z;
	}
	
	public void normalize() {
		double len = length();
		x = x / len;
		y = y / len;
		z = z / len;
	}
	
	public Vector3 getNormalized() {
		Vector3 temp = new Vector3(this);
		temp.normalize();
		return temp;
	}
	
	public double dot(Vector3 other) {
		return ((x * other.x) + (y * other.y) + (z * other.z));
	}
	
	public Vector3 cross(Vector3 other) {
		return new Vector3(
						y * other.z - z * other.y,
						z * other.x - x * other.z,
						x * other.y - y * other.x
					  );
	}
	
	public Vector3 add(Vector3 other) {
		Vector3 vec = new Vector3(this);
		vec.x += other.x;
		vec.y += other.y;
		vec.z += other.z;
		return vec;
	}
	
	
	public Vector3 subtract(Vector3 other) {
		Vector3 vec = new Vector3(this);
		vec.x -= other.x;
		vec.y -= other.y;
		vec.z -= other.z;
		return vec;
	}
	
	
	public Vector3 multiply(double s) {
		Vector3 vec = new Vector3(this);
		vec.x *= s;
		vec.y *= s;
		vec.z *= s;
		return vec;
	}
	
	public Vector3 divide(double s) {
		Vector3 vec = new Vector3(this);
		vec.x /= s;
		vec.y /= s;
		vec.z /= s;
		return vec;
	}
	
	public void negate() {
		x *= -1;
		y *= -1;
		z *= -1;
	}
	
	public String getXML() {
		return String.format("\"%f %f %f\"", x, y, z);
	}
}
