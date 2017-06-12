package dataTypes.baseClassDataTypes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import org.xml.sax.SAXException;

import application.GuiWindow;
import application.Utilities;
import dataTypes.dataTypeFactories.*;
import dataTypes.geometryDataTypes.Geometry;
import dataTypes.lightDataTypes.Light;
import dataTypes.shaderDataTypes.Shader;

public class Scene {

	// Object Factories
	private final static GeometryFactory[] geoFactories = { new SphereFactory(), new PlaneFactory() };
	private final static ShaderFactory[] shaderFactories = { new FlatShaderFactory(), new LambertShaderFactory(), new BlinnShaderFactory(), new ReflectiveShaderFactory(), new GouraudShaderFactory() };
	private final static LightFactory[] lightFactories = { new PointLightFactory(), new SpotLightFactory() };
	
	// Scene Data
	private List<Frame> frameList = new ArrayList<Frame>();
	private List<Shader> shaderList = new ArrayList<Shader>();
	private int imageWidth = 0;
	private int imageHeight = 0;
	private int antiAliasSamples = 1;
	private int antiAliasSamplesFromFile = 1;
	private int softShadowSampleCount = 1;
	private String sceneName = "";
	
	
	private static Scene theScene = null;
	
	public static Scene TheScene() {
		if (theScene == null) {
			theScene = new Scene();
		}
		
		return theScene;
	}
	
	public void parseSceneData(String filename){
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			
			Document doc = db.parse(filename);
			
			// Global Scene Data
			// There should only be one of these, and we only care about the first one.
			NodeList sceneData = doc.getElementsByTagName("SceneData");
			Element sceneElement = (Element)sceneData.item(0);
			imageWidth = Integer.parseInt(sceneElement.getAttribute("imageWidth"));
			imageHeight = Integer.parseInt(sceneElement.getAttribute("imageHeight"));
			
			String sampleCountString = sceneElement.getAttribute("AASamples");
			if (!sampleCountString.isEmpty()) {
				updateAntiAliasSamples(Integer.parseInt(sampleCountString));
			}
			else {
				updateAntiAliasSamples(1);
			}
			
			sampleCountString = sceneElement.getAttribute("softShadowSamples");
			if(!sampleCountString.isEmpty()) {
				softShadowSampleCount = Integer.parseInt(sampleCountString);
			}
			
			// Parse scene shaders.  
			// Shaders can't be animated the same way geometry can, 
			// so they're global to the scene, not frame specific.
			NodeList shaders = doc.getElementsByTagName("Shader");
			for (int shaderCounter = 0; shaderCounter < shaders.getLength(); ++shaderCounter) {
				Element element = (Element)shaders.item(shaderCounter);
				String shaderName = element.getAttribute("name");
				if (getShader(shaderName) == null) {
					for (int i = 0; i < shaderFactories.length; ++i) {
						Shader shader = shaderFactories[i].getShader(element);
						if (shader != null) {
							shaderList.add(shader);
							break;
						}
					}
				}
			}
			
			
			NodeList frames = doc.getElementsByTagName("Frame");
			int frameTotal = frames.getLength();
			for (int frameCounter = 0; frameCounter < frameTotal; ++frameCounter) {
				Frame newFrame = new Frame(frameCounter, frameTotal, false);
				frameList.add(newFrame);
				
				Element thisFrameElement = (Element)frames.item(frameCounter);
				
				String displayName = thisFrameElement.getAttribute("displayName");
				
				if (!displayName.isEmpty()) {
					newFrame.setDisplayName(displayName);
				}
				
				// Parse frame settings
				newFrame.setCamera(new Camera((Element)thisFrameElement.getElementsByTagName("Camera").item(0)));
				
				// Parse frame lights.
				NodeList lightList = thisFrameElement.getElementsByTagName("Light");
				for (int index = 0; index < lightList.getLength(); ++index) {
					Element elem = (Element)lightList.item(index);
					for (int i = 0; i < lightFactories.length; ++i) {
						Light light = lightFactories[i].getLight(elem);
						if (light != null) {
							newFrame.sceneLights.add(light);
							break;
						}
					}
				}
				
				// Parse frame geometry.
				NodeList geoList = thisFrameElement.getElementsByTagName("Geometry");
				for (int index = 0; index < geoList.getLength(); ++index) {
					Element elem = (Element)geoList.item(index);
					for (int i = 0; i < geoFactories.length; ++i) {
						Geometry geo = geoFactories[i].getGeometry(elem);
						if (geo != null) {
							newFrame.sceneGeo.add(geo);
							break;
						}
					}
				}
			}
			
			printSceneData();
			GuiWindow.Get().resetOutliners();
			
		} catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch(SAXException se) {
			se.printStackTrace();
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public void updateAntiAliasSamples(int newCount) {
		antiAliasSamplesFromFile = newCount;
		if (antiAliasSamplesFromFile < 1) {
			antiAliasSamples = 1;
		}
		else if (antiAliasSamplesFromFile > 1) {
			if ((antiAliasSamplesFromFile & (antiAliasSamplesFromFile - 1)) == 0) {
				// the user-defined antiAliasSamples is a power of 2
				// sqrt it and use that per-axis
				// the int type-cast is to make the compiler 
				// stop complaining about type incompatability
				// but since antiAliasSamples is going to be a power of 2, 
				// there won't be a need for double precision, so no loss of data
				antiAliasSamples = (int)Math.sqrt(antiAliasSamplesFromFile);
			}
			// The other case is that someone was naughty and didn't use a power of 2
			// In that case, assume they want that many samples on each axis.
			antiAliasSamples = antiAliasSamplesFromFile;
		}
	}
	
	public void printSceneData() {
		System.out.println("Scene Data:");
		System.out.println(String.format("\tImage Size:\t%d x %d", imageWidth, imageHeight));
		System.out.println(String.format("\tAnti Alias Samples:\t%d", antiAliasSamples));
		System.out.println(String.format("\tSoft Shadow Samples: \t%d", softShadowSampleCount));
		System.out.println("");
		System.out.println(String.format("Total Scene Shaders: %d", shaderList.size()));
		for (int i = 0; i < shaderList.size(); ++i) {
			System.out.println(shaderList.get(i).toString());
			System.out.println("");
		}
		
		System.out.println(String.format("Total Frames: %d", frameList.size()));
		for (int frame = 0; frame < frameList.size(); ++frame) {
			Frame curFrame = frameList.get(frame);
			System.out.println(String.format("Frame #%d", frame));
			System.out.println(String.format("\tImage Name:\t%s", Utilities.getFrameName(frame)));
			for (int i = 0; i < curFrame.sceneLights.size(); ++i) {
				System.out.println(String.format("\tLight: %n\t\t%s", curFrame.sceneLights.get(i).toString()));
				System.out.println("");
			}
			for (int i = 0; i < curFrame.sceneGeo.size(); ++i) {
				System.out.println(String.format("\tObject: %n\t\t%s", curFrame.sceneGeo.get(i).toString()));
				System.out.println("");
			}
		}
	}
	
	public List<Frame> getFrames() {
		return frameList;
	}

	public List<Shader> getShaderList() {
		return shaderList;
	}
	
	public void reorderFrames(int oldIndex, int direction) {
		Frame movingFrame = frameList.get(oldIndex);
		frameList.remove(oldIndex);
		frameList.add(oldIndex + direction, movingFrame);
		GuiWindow.Get().resetSceneOutliner();
		GuiWindow.Get().setSceneOutlinerSelectedIndex(oldIndex + direction + 1);
	}
	
	public void addNewFrame() {
		final int count = frameList.size();
		Frame newFrame = new Frame(count, count + 1, true);
		frameList.add(newFrame);
		GuiWindow.Get().addSceneOutlinerFrameEntry(newFrame);
	}
	
	public void removeFrame(int index) {
		frameList.remove(index);
	}
	
	public Shader getShader(String name) {
		Iterator<Shader> iter = shaderList.iterator();
		
		while(iter.hasNext()) {
			Shader curShader = iter.next();
			if (curShader.getName().compareTo(name) == 0) {
				return curShader;
			}
		}
		return null;
	}
	
	public String[] getAllShaderNames() {
		String[] names = new String[shaderList.size() +1];
		int index = 1;
		Iterator<Shader> iter = shaderList.iterator();
		names[0] = "No Shader";
		while (iter.hasNext()) {
			names[index++] = iter.next().getName();
		}
		
		return names;
	}
	
	public int getImageWidth() {
		return imageWidth;
	}
	
	public void setImageWidth(int w) {
		imageWidth = w;
	}
	
	public int getImageHeight() {
		return imageHeight;
	}
	
	public void setImageHeight(int h) {
		imageHeight = h;
	}
	
	public int getAntiAliasSamples() {
		return antiAliasSamples;
	}

	public int getRawAntiAliasSamples() {
		return antiAliasSamplesFromFile;
	}
	
	public int getSoftShadowSampleCount() {
		return softShadowSampleCount;
	}

	public void setSoftShadowSampleCount(int newSamples) {
		softShadowSampleCount = newSamples;
	}
	
	public String getSceneName() {
		return sceneName;
	}
	
	public void setSceneName(String newName) {
		sceneName = newName;
	}
	
	public float getAspectRatio() {
		return (float)imageWidth / imageHeight;
	}
	
	public void createEmptyScene(boolean addFrame) {
		if (frameList.size() > 0 || addFrame) {
			frameList = new ArrayList<Frame>();
			if (addFrame) {
				frameList.add(new Frame(0, 1, true));
			}
			shaderList = new ArrayList<Shader>();
			imageWidth = 1024;
			imageHeight = 768;
			antiAliasSamples = 1;
			antiAliasSamplesFromFile = 1;
			softShadowSampleCount = 1;
			sceneName = "";
			GuiWindow.Get().resetOutliners();
			GuiWindow.Get().updateRenderButtonState();
		}
	}
	
	public String getXML() {
		String result = String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?>%n<Scene>%n");
		result = String.format("%s\t<SceneData imageWidth=\"%d\" imageHeight=\"%d\" AASamples=\"%d\" softShadowSamples=\"%d\" />%n", result, imageWidth, imageHeight, antiAliasSamplesFromFile, softShadowSampleCount);
		for (int i = 0; i < shaderList.size(); ++i) {
			result = String.format("%s\t%s%n", result, shaderList.get(i).getXML());
		}
		for (int i = 0; i < frameList.size(); ++i) {
			result = String.format("%s%s%n", result, frameList.get(i).getXML());
		}
		
		result = String.format("%s</Scene>", result);
		return result;
	}
	
	public Shader makeShader(String type) {
		for (int i = 0; i < shaderFactories.length; ++i) {
			Shader s = shaderFactories[i].getShader(type);
			if (s != null) {
				shaderList.add(s);
				return s;
			}
		}
		return null;
	}
	
	public Geometry makeGeometry(String type) {
		for (int i = 0; i < geoFactories.length; ++i) {
			Geometry g = geoFactories[i].getGeometry(type);
			if (g != null) {
				return g;
			}
		}
		return null;
	}
	
	public Light makeLight(String type) {
		for (int i = 0; i < lightFactories.length; ++i) {
			Light l = lightFactories[i].getLight(type);
			if (l != null) {
				return l;
			}
		}
		return null;
	}

	public void removeShader(Shader s) {
		shaderList.remove(s);
		for (int i = 0; i < frameList.size(); ++i) {
			Frame f = frameList.get(i);
			for (int j = 0; j < f.sceneGeo.size(); ++j) {
				Geometry geo = f.sceneGeo.get(j);
				if (geo.getShader() == s) {
					// There isn't a shader here anymore.
					geo.updateShaderFromName(null);
				}
			}
		}
	}
	
	public void removeLight(Light l) {
		for (int i = 0; i < frameList.size(); ++i) {
			Frame f = frameList.get(i);
			f.sceneLights.remove(l);
		}
	}
	
	public void removeGeometry(Geometry g) {
		for (int i = 0; i < frameList.size(); ++i) {
			Frame f = frameList.get(i);
			f.sceneGeo.remove(g);
		}
	}
}
