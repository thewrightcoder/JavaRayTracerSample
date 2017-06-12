package dataTypes.baseClassDataTypes;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Color {
	public double R, G, B = 0.0f;
	
	public Color() {
	}
	
	public Color(double r, double g, double b) {
		R = r;
		G = g;
		B = b;
	}
	
	public Color(Color color) {
		R = color.R;
		G = color.G;
		B = color.B;
	}
	
	public static Color parseColor(String text) {
		ArrayList < Double > myDoubles = new ArrayList < Double >();
		Matcher matcher = Pattern.compile( "[-+]?\\d*\\.?\\d+([eE][-+]?\\d+)?" ).matcher( text );

	    while ( matcher.find() )
	    {
	        double element = Double.parseDouble( matcher.group() );
	        myDoubles.add( element );
	    }
		
	    Color color = new Color();
		color.R = myDoubles.get(0);
		color.G = myDoubles.get(1);
		color.B = myDoubles.get(2);
		
		return color;
	}
	
	public String toString() {
		return String.format("Color: < %f, %f, %f >", R, G, B);
	}
	
	public Color add(double r, double g, double b) {
		Color newColor = new Color(this);
		newColor.R += r;
		newColor.G += g;
		newColor.B += b;
		return newColor;
	}
	
	public Color add(Color other) {
		return this.add(other.R, other.G, other.B);
	}
	
	public Color subtract(double r, double g, double b) {
		Color newColor = new Color(this);
		newColor.R -= r;
		newColor.G -= g;
		newColor.B -= b;
		return newColor;
	}
	
	public Color subtract(Color other) {
		return this.subtract(other);
	}
	
	public Color multiply(double s) {
		Color newColor = new Color(this);
		newColor.R *= s;
		newColor.G *= s;
		newColor.B *= s;
		return newColor;
	}
	
	public Color divide(double s) {
		Color newColor = new Color(this);
		newColor.R /= s;
		newColor.G /= s;
		newColor.B /= s;
		return newColor;
	}
	
	public void clamp() {
		if (R > 255) {
			R = 255;
		}
		else if (R < 0) {
			R = 0;
		}
		
		if (G > 255) {
			G = 255;
		}
		else if (G < 0) {
			G = 0;
		}
		
		if (B > 255) {
			B = 255;
		}
		else if (B < 0) {
			B = 0;
		}
	}
	
	public String getXML() {
		return String.format("\"%f %f %f\"", R, G, B);
	}

	public Color multiply(Color c2) {
		Color result = new Color(this);
		result.R *= c2.R;
		result.G *= c2.G;
		result.B *= c2.B;
		return result;
	}
}
