package dataTypes.baseClassDataTypes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import application.GuiWindow;
import application.Utilities;
import dataTypes.baseClassDataTypes.Renderer;
import dataTypes.geometryDataTypes.Geometry;

public class Renderer {

	public static Frame CurrentFrame = null;
	
	private int AASamples, imageWidth, imageHeight, startingX, startingY, finalX, finalY, maxTraceDepth;
	
	public static void renderScene() {
		SwingWorker<Integer, Void> worker = new SwingWorker<Integer, Void>() {
		    @Override
		    public Integer doInBackground() {
				
		    	if (Scene.TheScene().getFrames().size() > 0) {
					/* Render preview setup */
					JFrame f = new JFrame("ImageDrawing");
			        f.addWindowListener(new WindowAdapter() {
			            public void windowClosing(WindowEvent e) {
			               //System.exit(0);
			            }
			        });

			        JPanel gui = new JPanel(new BorderLayout());
			        gui.setBorder(new EmptyBorder(5,5,5,5));
			        
			        JLabel imageCanvas = new JLabel();
			        
			        JPanel imageCenter = new JPanel(new GridBagLayout());
			        imageCenter.add(imageCanvas);
			        
			        gui.add(imageCenter);
			        
			        f.setContentPane(gui);
			         /* End Render Preview setup */
					
					renderScene(f, imageCanvas);
		    	}
				return 0;
		    }
		};
		worker.execute();
	}
	
	private static void renderScene(JFrame f, JLabel imageCanvas) {
		List<Frame> frames = Scene.TheScene().getFrames();
		
		int AASamples = Scene.TheScene().getAntiAliasSamples();
		int imageWidth = Scene.TheScene().getImageWidth();
		int imageHeight = Scene.TheScene().getImageHeight();
		
		/* Render Preview setup for this frame */
        f.setMinimumSize(new Dimension(imageWidth+15, imageHeight+15));
        f.pack();
        f.setVisible(true);
        /* End Render Preview setup for this frame */
                
		for (int frameCounter = 0; frameCounter < frames.size(); ++frameCounter) {
			CurrentFrame = frames.get(frameCounter);
			Image image = new Image(imageWidth, imageHeight, true);
			final double sqrtThreadCount = 4.0f;
			final double threadWidth = imageWidth / sqrtThreadCount;
			final double threadHeight = imageHeight / sqrtThreadCount;
			
			// Keep track of every thread created.  When they're all done, write the image out.
			ArrayList<Thread> threadArray = new ArrayList<Thread>();
			
			// In Java, if I don't explicitly catch an exception in a multithreaded environment,
			// that thread dies silently behind the secenes.... unless I use an UncaughtExceptionHandler.
			// This specific implementation helped me catch the LightU/LightV null pointer issue (see Spot/Point lights)
			Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {

				@Override
				public void uncaughtException(Thread t, Throwable e) {
					System.out.println(t.getName()+": "+e);
				}
				
			});
			for (int vary = 0; vary < sqrtThreadCount; ++vary) {
				final int y = vary;
				for (int varx = 0; varx < sqrtThreadCount; ++varx) {
					final int x = varx;
					Thread thread = new Thread() {
						public void run() {
				    		Renderer renderer = new Renderer();
				    		renderer.setAASamples(AASamples);
				    		renderer.setImageSize(imageWidth, imageHeight);
				    		renderer.setRenderArea((int)(threadWidth * x), (int)(threadHeight * y), (int)(threadWidth * (x+1)), (int)(threadHeight * (y+1)));
				    		renderer.setMaxTraceDepth(5);
				    		renderer.renderFrame(CurrentFrame, imageCanvas, image);
						}
					};
					thread.start();
					threadArray.add(thread);
				}
			}
			
			boolean waiting = true;
			while (waiting) {
				waiting = false;
				for (int i = 0; i < threadArray.size(); ++i) {
					if (threadArray.get(i).isAlive()) {
						waiting = true;
						break;
					}
				}
			}
			
			image.writeImage(Utilities.getFrameName(frameCounter));
		}
	}
	
	public static void renderPreview(int frameIndex) {
		Frame f = Scene.TheScene().getFrames().get(frameIndex);
		renderPreview(f);
	}
	
	public static void renderPreview(Frame f) {
		if (f != null) {
			SwingWorker<Integer, Void> worker = new SwingWorker<Integer, Void>() {
			    @Override
			    public Integer doInBackground() {
			    	CurrentFrame = f;
					Renderer r = new Renderer();
					
					// Set minimum settings for preview render.
					r.setAASamples(1);
					r.setMaxTraceDepth(0);
					double fstop = f.getCamera().getFStop();
					f.getCamera().setFStop(0.0f);
					int softShadowSamples = Scene.TheScene().getSoftShadowSampleCount();
					Scene.TheScene().setSoftShadowSampleCount(1);
					int width = 320;
					int height = 240;
					r.setImageSize(width, height, Scene.TheScene().getAspectRatio());
					r.setRenderArea(0,0,width,height);
					
					r.renderFrame(f, GuiWindow.Get().getRenderPreviewLabel(), new Image(width, height, true));
					
					// Restore settings.
					f.getCamera().setFStop(fstop);
					Scene.TheScene().setSoftShadowSampleCount(softShadowSamples);;
					return 0;
			    }
			};
			worker.execute();
		} else {
			// No frame is selected. Clear the render preview.
			GuiWindow.Get().getRenderPreviewLabel().setIcon(null);
		}
	}
	
	public Image renderFrame(Frame f, JLabel imageCanvas, Image image) {
		Color curColor = null;
		
		double curX, curY;
		
		for (int y = startingY; y < finalY; ++y) {
			for (int x = startingX; x < finalX; ++x) {
				curX = (double)x/imageWidth;
				curY = (double)y/imageHeight;
				
				if (AASamples > 1) {
					double nX, nY;
					// For antialiasing, start with black
					// then add to it.
					curColor = new Color();
					for (int ySamples = 0; ySamples < AASamples; ++ySamples) {
						for (int xSamples = 0; xSamples < AASamples; ++xSamples) {
							nX = curX + Math.random() * 1.0/imageWidth;
							nY = curY + Math.random() * 1.0/imageHeight;
							
							curColor = curColor.add(trace(nX, nY, (double)imageWidth/imageHeight));
						}
					}
					// Average the colors.
					curColor = curColor.divide(AASamples * AASamples);
				}
				else {
					// For no antialiasing, just use the color returned by the one sample.
					curColor = trace(curX, curY, (double)imageWidth/imageHeight);
				}
				
				curColor = curColor.multiply(255);
				curColor.clamp();

				image.setPixelColor(x, y, curColor);
				// Manually update the image once this pixel is finished rendering.
				imageCanvas.setIcon(new ImageIcon(image.data));
			}
		}
		return image;
	}
	
	private Color trace(double x, double y, double aspectRatio) {
		Camera camera = CurrentFrame.getCamera();
		Vector3 S = null;
		Vector3 camPosition = camera.getPosition();
		Vector3 lookat = camera.getLookat();
		
		double w = 2 * Math.tan(Math.toRadians(camera.getFOV()) / 2) * lookat.subtract(camPosition).length();
		
		S = camera.getLeft().multiply(x - 0.5).multiply(w);
		S = S.subtract(camera.getUp().divide(aspectRatio).multiply(y - 0.5).multiply(w));
		
		// Depth of Field
		double fstop = camera.getFStop();
		if (fstop > 0.0f) {
			Vector3 camV = camPosition.cross(new Vector3(1+camPosition.y, -camPosition.z, camPosition.x)).getNormalized();
			Vector3 camU = camPosition.cross(camV).getNormalized();
			
			double aperture = 0.5 / fstop;
			
			camV = camV.multiply((Math.random() - 0.5) * aperture);
			camU = camU.multiply((Math.random() - 0.5) * aperture);
			
			camPosition = camPosition.add(camV).add(camU);
		}
		
		S = S.subtract(camPosition);
		S.normalize();
		return trace(camPosition, S, maxTraceDepth);
	}
	
	public static Color trace(Vector3 eye, Vector3 direction, int curTraceDepth) {
		boolean checkForHit = false;
		double closestDist = 0.0f, curDist = 0.0f;
		Geometry closestGeo = null;
		
		Iterator<Geometry> iter = CurrentFrame.sceneGeo.iterator();
		Geometry geo = null;
		while (iter.hasNext()) {
			geo = iter.next();
			curDist = geo.checkForHit(new Ray(eye, direction), CurrentFrame.getCamera().getNearClip(), CurrentFrame.getCamera().getFarClip());
			
			if (curDist > 0 && (closestGeo == null || curDist < closestDist)) {
				checkForHit = true;
				closestDist = curDist;
				closestGeo = geo;
			}
		}
		
		Color theColor = new Color();
		
		if (checkForHit) {
			HitRecord hitRec = new HitRecord();
			hitRec.hitDistance = closestDist;
			hitRec.hitGeometry = closestGeo;
			hitRec.hitPoint = eye.add(direction.multiply(hitRec.hitDistance));
			hitRec.hitOrigin = eye;
			hitRec.hitGeometry.getNormalAtPoint(hitRec);
			hitRec.traceDepth = curTraceDepth - 1;
			
			theColor = hitRec.hitGeometry.getColor(hitRec);
		}
		
		return theColor;
	}
	
	public void setAASamples(int samples) {
		AASamples = samples;
	}
	
	public void setImageSize(int x, int y) {
		imageWidth = x;
		imageHeight = y;
	}
	
	public void setImageSize(int x, int y, float aspectRatio) {
		float ar = (float)x / y;
		if (ar != aspectRatio) {
			if (aspectRatio < 1) {
				x *= aspectRatio;
			} else {
				y *= aspectRatio;
			}
		}
		imageWidth = x;
		imageHeight = y;
	}
	
	public void setRenderArea(int x1, int y1, int x2, int y2) {
		startingX = x1;
		startingY = y1;
		finalX = x2;
		finalY = y2;
	}
	
	public void setMaxTraceDepth(int depth) {
		maxTraceDepth = depth;
	}
}
