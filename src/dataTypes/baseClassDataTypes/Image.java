package dataTypes.baseClassDataTypes;

import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.*;

public class Image {
	public BufferedImage data = null;
	
	public Image(){
		
	}
	
	public Image(int w, int h, boolean draw){
		data = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		if (draw) {
			// Set the default pixel color to a neutral grey.
			// If a pixel renders incorrectly, it usually renders to black.
			// If the default is black, and the error is black, it's much harder to find.
			for (int y = 0; y < h; ++y) {
				for (int x = 0; x < w; ++x) {
					setPixelColor(x, y, 128, 128, 128);
				}
			}
		}
	}
	
	public void readImage(String filename){
		try {
			data = ImageIO.read(new File(filename));
		} catch (IOException e) {
			System.out.println(String.format("Invalid image suppled: %s does not exist or is of an unsupported format.", filename));
			e.printStackTrace();
		}
	}
	
	public void writeImage(String filename){
		try {
		    if (data != null) {
		    	String filetype = filename.substring(filename.length()-3);
		    	File outputfile = new File(filename);
		    	ImageIO.write(data, filetype, outputfile);
		    }
		    else {
		    	System.out.println(String.format("Trying to write image %s with no data stored.", filename));
		    }
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	
	public void setPixelColor(int x, int y, int r, int g, int b){
		int value = (r << 16) + (g << 8) + b;
		data.setRGB(x, y, value);
	}
	
	public void setPixelColor(int x, int y, Color color) {
		setPixelColor(x, y, (int)color.R, (int)color.G, (int)color.B);
	}
}
