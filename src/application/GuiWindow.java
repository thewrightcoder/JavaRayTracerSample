package application;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import application.components.*;
import dataTypes.baseClassDataTypes.Frame;
import dataTypes.baseClassDataTypes.Renderer;
import dataTypes.baseClassDataTypes.Scene;

import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JPanel;
import java.awt.GridBagConstraints;

public class GuiWindow {

	private JFrame frame = null;
	private OutlinerPane outliners = null;
	private RenderButton renderBtn = null;
	private DataViewer dataViewer = null;
	private JPanel renderPreview = null;
	private JLabel renderPreviewLabel = null;
	private static GuiWindow guiWindow = null;
	
	public static GuiWindow Get() {
		return guiWindow;
	}
	
	public static void StartGUI() {
		try { 
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
		} catch (UnsupportedLookAndFeelException e) {
			
		} catch (ClassNotFoundException e) {
			
		} catch (InstantiationException e) {
			
		} catch (IllegalAccessException e) {
			
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GuiWindow window = new GuiWindow();
					guiWindow = window;
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GuiWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		int width = 800;
		int height = 600;
		if (Utilities.isWindowsSystem()) {
			// Windows Look And Feel UI elements are slightly bigger than their Mac counterparts,
			// so I need to make the window a little larger.
			width = 830;
			height = 630;
		}
		frame = new JFrame();
		renderBtn = new RenderButton();
		
		outliners = new OutlinerPane();
		
		dataViewer = new DataViewer(width/2, height/2);
		
		renderPreview = new JPanel();
		renderPreview.setBorder(BorderFactory.createLoweredBevelBorder());
		renderPreview.setPreferredSize(new Dimension(320, 240));
		renderPreview.setMinimumSize(renderPreview.getPreferredSize());
		renderPreview.setMaximumSize((renderPreview.getPreferredSize()));
		GridBagLayout gbl_renderPreview = new GridBagLayout();
		gbl_renderPreview.rowWeights = new double[]{1.0};
		gbl_renderPreview.columnWeights = new double[]{1.0, 0.0};
		renderPreview.setLayout(gbl_renderPreview);
		
		renderPreviewLabel = new JLabel("");
		GridBagConstraints gbc_renderPreviewLabel = new GridBagConstraints();
		gbc_renderPreviewLabel.gridx = 1;
		gbc_renderPreviewLabel.gridy = 0;
		renderPreview.add(renderPreviewLabel, gbc_renderPreviewLabel);
		renderPreviewLabel.setPreferredSize(new Dimension(320, 240));
		renderPreviewLabel.setMinimumSize(renderPreviewLabel.getPreferredSize());
		renderPreviewLabel.setMaximumSize(renderPreviewLabel.getPreferredSize());
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(outliners, GroupLayout.PREFERRED_SIZE, 400, GroupLayout.PREFERRED_SIZE)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(dataViewer, GroupLayout.PREFERRED_SIZE, 375, GroupLayout.PREFERRED_SIZE)
								.addComponent(renderPreview, GroupLayout.PREFERRED_SIZE, 375, GroupLayout.PREFERRED_SIZE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(232)
							.addComponent(renderBtn, GroupLayout.PREFERRED_SIZE, 156, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(renderPreview, GroupLayout.PREFERRED_SIZE, 240, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(dataViewer, GroupLayout.PREFERRED_SIZE, 270, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(renderBtn, GroupLayout.PREFERRED_SIZE, 23, Short.MAX_VALUE))
						.addComponent(outliners, GroupLayout.PREFERRED_SIZE, 545, GroupLayout.PREFERRED_SIZE))
					.addGap(18))
		);

		frame.getContentPane().setLayout(groupLayout);
		
		//frame.add(mainBox);
		frame.setBounds(0, 0, width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new MyMenuBar();
		frame.setJMenuBar(menuBar);
		frame.setTitle("JavaRayTracerCodeSample");
		
	}
	
	public void resetOutliners() {
		outliners.resetOutliners();
	}
	
	public void updateRenderButtonState() {
		if (Scene.TheScene().getFrames().size() > 0) {
			renderBtn.setEnabled(true);
		}
		else {
			renderBtn.setEnabled(false);
		}
	}
	
	public void resetSceneOutliner() {
		outliners.resetSceneOutliner();
	}
	
	public void setSceneOutlinerSelectedIndex(int index) {
		outliners.setSceneOutlinerSelectedIndex(index);
	}
	
	public void addSceneOutlinerFrameEntry(Frame f) {
		outliners.addSceneOutlinerFrameEntry(f);
	}
	
	public void updateDataViewer(Component comp) {
		dataViewer.updateView(comp);
	}
	
	public int getDataViewerRealWidth() {
		return dataViewer.getRealWidth();
	}
	
	public int getDataViewerRealHeight() {
		return dataViewer.getRealHeight();
	}
	
	public void updateTreeViewNode(Object o) {
		outliners.updateTreeViewNode(o);
	}
	
	public void renderPreview() {
		Renderer.renderPreview(outliners.getCurrentlySelectedFrame());
		renderPreview.invalidate();
		renderPreviewLabel.invalidate();
	}
	
	public JLabel getRenderPreviewLabel() {
		return renderPreviewLabel;
	}
	
	public void setAddObjectButtonEnabled(boolean b) {
		outliners.setAddObjectButtonEnabled(b);
	}
	
	public void setDelObjectButtonEnabled(boolean b) {
		outliners.setDelObjectButtonEnabled(b);
	}
}
