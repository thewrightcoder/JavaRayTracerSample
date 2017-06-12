package application;

import java.io.File;
import java.text.NumberFormat;

import dataTypes.baseClassDataTypes.Scene;

public class Utilities {
	
	private static NumberFormat integerFormatter = null;
	private static NumberFormat percentFormatter = null;
	private static NumberFormat decimalFormatter = null;
	
	private static boolean isWindows = false;
	
	private static final TypeNameMapping[] GeometryTypes = { new TypeNameMapping(InternalStrings.PlaneTypeName, DisplayStrings.PlaneName), 
															 new TypeNameMapping(InternalStrings.SphereTypeName, DisplayStrings.SphereName) };
	
	private static final TypeNameMapping[] LightTypes = { new TypeNameMapping(InternalStrings.PointLightTypeName, DisplayStrings.PointLightName), 
														  new TypeNameMapping(InternalStrings.SpotLightTypeName, DisplayStrings.SpotLightName) };

	private static final TypeNameMapping[] ShaderTypes = { new TypeNameMapping(InternalStrings.BlinnShaderTypeName, DisplayStrings.BlinnShaderName),
														   new TypeNameMapping(InternalStrings.FlatShaderTypeName, DisplayStrings.FlatShaderName),
														   new TypeNameMapping(InternalStrings.GouraudShaderTypeName, DisplayStrings.GouraudShaderName),
														   new TypeNameMapping(InternalStrings.LambertShaderTypeName, DisplayStrings.LambertShaderName),
														   new TypeNameMapping(InternalStrings.ReflectiveShaderTypeName, DisplayStrings.ReflectiveShaderName)};
	
	// temp hack
	private static final TypeNameMapping[] GeometryAndLightTypes = { new TypeNameMapping(InternalStrings.PlaneTypeName, DisplayStrings.PlaneName), 
																	 new TypeNameMapping(InternalStrings.SphereTypeName, DisplayStrings.SphereName),
																	 new TypeNameMapping(InternalStrings.PointLightTypeName, DisplayStrings.PointLightName), 
																	 new TypeNameMapping(InternalStrings.SpotLightTypeName, DisplayStrings.SpotLightName) };
	
	private Utilities() {
	}
	
	public static String getFileExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        else {
        	// No extension was found.
        	ext = "";
        }
        return ext;
	}
	
	public static String removeFileExtension(String fileName) {
		int index = fileName.lastIndexOf('.');
		if (index >= 0) {
			return fileName.substring(0, index);
		} else {
			// No file extension was found.
			return fileName;
		}
	}
	
	public static String getFrameName(int frameIndex) {
		return getFrameName(frameIndex, Scene.TheScene().getFrames().size());
	}
	
	public static String getFrameName(int frameIndex, int frameCount) {
		return getFrameName(frameIndex, frameCount, Scene.TheScene().getSceneName(), "_frame_", true);
	}
	
	public static String getFrameName(int frameIndex, int frameCount, String frameName, String appendToName, boolean addFileExtension) {
		if (frameCount == 1) {
			// In this case, don't append a number.
		}
		else {
			String padding = "";
			while (frameCount >= 10) {
				padding += "0";
				frameCount /= 10;
			}
			int frameTens = 0;
			while (frameIndex >= 10) {
				++frameTens;
				frameIndex /= 10;
			}
			frameName += appendToName + padding.substring(frameTens) + frameIndex;
		}
		// TODO: support output file type picking.
		if (addFileExtension) {
			frameName += ".png";
		}
		return frameName;
	}

	public static NumberFormat getIntegerFormatter() {
		if (integerFormatter == null) {
			integerFormatter = NumberFormat.getIntegerInstance();
			integerFormatter.setGroupingUsed(false);
		}
		return integerFormatter;
	}
	
	public static NumberFormat getDecimalFormatter() {
		if (decimalFormatter == null) {
			decimalFormatter = NumberFormat.getInstance();
			decimalFormatter.setGroupingUsed(false);
		}
		return decimalFormatter;
	}
	
	public static NumberFormat getPercentFormatter() {
		if (percentFormatter == null) {
			percentFormatter = NumberFormat.getInstance();
			percentFormatter.setGroupingUsed(false);
			percentFormatter.setMinimumIntegerDigits(0);
			percentFormatter.setMaximumIntegerDigits(3);
			// TODO: limit values???
		}
		return percentFormatter;
	}
	
	public static TypeNameMapping[] getGeometryTypes() {
		return GeometryTypes;
	}
	
	public static TypeNameMapping[] getLightTypes() {
		return LightTypes;
	}
	
	public static TypeNameMapping[] getGeometryAndLightTypes() {
		return GeometryAndLightTypes;
	}
	
	public static TypeNameMapping[] getShaderTypes() {
		return ShaderTypes;
	}
	
	public static boolean isWindowsSystem() {
		return isWindows;
	}
	
	public static void setIsWindowsSystem(boolean b) {
		isWindows = b;
	}
}
