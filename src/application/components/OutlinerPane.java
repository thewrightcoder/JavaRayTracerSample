package application.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.tree.TreePath;

import application.DisplayStrings;
import application.GuiWindow;
import application.TypeNameMapping;
import application.Utilities;
import application.listViewObjects.ListDataObject;
import application.treeViewObjects.TreeViewObject;
import application.treeViewObjects.TreeViewObjectShader;
import dataTypes.baseClassDataTypes.Frame;
import dataTypes.baseClassDataTypes.Scene;
import dataTypes.geometryDataTypes.Geometry;
import dataTypes.lightDataTypes.Light;
import dataTypes.shaderDataTypes.Shader;

public class OutlinerPane extends JSplitPane {
	public static final long serialVersionUID = 1L;
	
	private SceneOutliner sceneOutliner = null;
	private FrameOutliner frameOutliner = null;
	
	private JButton frameAddBtn = null;
	private JButton frameDelBtn = null;
	private JButton sortUpBtn = null;
	private JButton sortDownBtn = null;
	private JButton addObjectBtn = null;
	private JButton delObjectBtn = null;
	
	private class sceneOutlinerListSelectionHandler implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			if (e.getValueIsAdjusting() == false) {
				SceneOutliner sceneOutliner = (SceneOutliner) e.getSource();
				ListSelectionModel model = sceneOutliner.getSelectionModel();
				final int frameCount = Scene.TheScene().getFrames().size();
				final int selectionIndex = model.getMinSelectionIndex();
				
				if (selectionIndex > -1) {
					if (selectionIndex == 0) {
						frameDelBtn.setEnabled(false);
						sortUpBtn.setEnabled(false);
						sortDownBtn.setEnabled(false);
					}
					else {
						frameDelBtn.setEnabled(true);
						sortUpBtn.setEnabled(selectionIndex > 1);
						sortDownBtn.setEnabled(frameCount > 1 && selectionIndex < frameCount);
					}
					
					addObjectBtn.setEnabled(true);
					
					GuiWindow.Get().renderPreview();
					frameOutliner.setDisplay(((ListDataObject)sceneOutliner.getSelectedValue()).getTreeView());
				}
			}
		}
	}
	
	public OutlinerPane() {
		super();
		setContinuousLayout(true);
		setOrientation(JSplitPane.VERTICAL_SPLIT);
		
		Box sceneHGroup = Box.createHorizontalBox();
		Box sceneVGroup = Box.createVerticalBox();
		
		sceneHGroup.add(sceneVGroup);
		
		JScrollPane left = new JScrollPane();
		sceneOutliner = new SceneOutliner();
		sceneOutliner.addListSelectionListener(new sceneOutlinerListSelectionHandler());
		left.setViewportView(sceneOutliner);
		sceneHGroup.add(left);
		
		frameAddBtn = new JButton("");
		frameAddBtn.setIcon(new ImageIcon(GuiWindow.class.getResource("/icons/film_strip_add.png")));
		frameAddBtn.setSize(32, 32);
		frameAddBtn.setEnabled(false);
		frameAddBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Scene.TheScene().addNewFrame();
			}
		});
		sceneVGroup.add(frameAddBtn);
		
		frameDelBtn = new JButton("");
		frameDelBtn.setIcon(new ImageIcon(GuiWindow.class.getResource("/icons/film_strip_delete.png")));
		frameDelBtn.setSize(32, 32);
		frameDelBtn.setEnabled(false);
		frameDelBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Scene.TheScene().removeFrame(sceneOutliner.getSelectedIndex() - 1);
				sceneOutliner.resetSceneOutliner();
				
				// We've just deleted the currently selected item...
				// So what's selected now?
				// Be safe, and force "Scene" to be selected.
				// "Scene" can't be deleted, so it's always safe.
				sceneOutliner.setSelectedIndex(0);
			}
		});
		sceneVGroup.add(frameDelBtn);
		
		sortUpBtn = new JButton("");
		sortUpBtn.setIcon(new ImageIcon(GuiWindow.class.getResource("/icons/up-arrow-filled-angle.png")));
		sortUpBtn.setSize(32, 32);
		sortUpBtn.setEnabled(false);
		sortUpBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Scene.TheScene().reorderFrames(sceneOutliner.getSelectedIndex() - 1, -1);
			}
		});
		sceneVGroup.add(sortUpBtn);
		
		sortDownBtn = new JButton("");
		sortDownBtn.setIcon(new ImageIcon(GuiWindow.class.getResource("/icons/down-arrow-filled-angle.png")));
		sortDownBtn.setSize(32, 32);
		sortDownBtn.setEnabled(false);
		sortDownBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Scene.TheScene().reorderFrames(sceneOutliner.getSelectedIndex() - 1, 1);
			}
		});
		sceneVGroup.add(sortDownBtn);
		
		JTextPane txtpnSceneOutliner = new JTextPane();
		txtpnSceneOutliner.setText("Scene Outliner");
		txtpnSceneOutliner.setEditable(false);
		left.setColumnHeaderView(txtpnSceneOutliner);
		
		StyledDocument doc = txtpnSceneOutliner.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		
		Box objectHGroup = Box.createHorizontalBox();
		Box objectVGroup = Box.createVerticalBox();
		objectVGroup.setAlignmentY(Box.TOP_ALIGNMENT);
		objectHGroup.add(objectVGroup);
		
		addObjectBtn = new JButton("");
		addObjectBtn.setIcon(new ImageIcon(GuiWindow.class.getResource("/icons/add_object.png")));
		addObjectBtn.setSize(32, 32);
		addObjectBtn.setEnabled(false);
		addObjectBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int index = sceneOutliner.getSelectedIndex();
				if (index == 0) {
					TypeNameMapping result = (TypeNameMapping)JOptionPane.showInputDialog(
							null,
							"Choose an object type to add:",
							"Type Picker",
							JOptionPane.PLAIN_MESSAGE,
							null,
							Utilities.getShaderTypes(),
							Utilities.getShaderTypes()[0]);
					if (result != null) {
						Shader shader = Scene.TheScene().makeShader(result.getTypeName());
						frameOutliner.addNode(DisplayStrings.ShadersName, new TreeViewObjectShader(shader, shader.getName(), shader.getTypeName(), shader.getDisplayTypeName()));
					}
				} 
				else if (index > 0) {
					TypeNameMapping result = (TypeNameMapping)JOptionPane.showInputDialog(
							null,
							"Choose an object type to add:",
							"Type Picker",
							JOptionPane.PLAIN_MESSAGE,
							null,
							Utilities.getGeometryAndLightTypes(),
							Utilities.getGeometryAndLightTypes()[0]);
					if (result != null) {
						Geometry geo = Scene.TheScene().makeGeometry(result.getTypeName());
						if (geo != null) {
							frameOutliner.addNode(DisplayStrings.GeometryName, new TreeViewObject(geo, geo.getTypeName(), geo.getTypeName()));
							Scene.TheScene().getFrames().get(index - 1).addGeometry(geo);
						} else {
							Light light = Scene.TheScene().makeLight(result.getTypeName());
							frameOutliner.addNode(DisplayStrings.LightsName, new TreeViewObject(light, light.getTypeName(), light.getTypeName()));
							Scene.TheScene().getFrames().get(index - 1).addLight(light);
						}
					}
				}
			}
		});
		objectVGroup.add(addObjectBtn);
		
		delObjectBtn = new JButton("");
		delObjectBtn.setIcon(new ImageIcon(GuiWindow.class.getResource("/icons/delete_object.png")));
		delObjectBtn.setSize(32, 32);
		delObjectBtn.setEnabled(false);
		delObjectBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TreePath path = frameOutliner.getPathForRow(frameOutliner.getMinSelectionRow());
				Object element = path.getLastPathComponent();
				if (element instanceof TreeViewObject) {
					TreeViewObject viewObject = (TreeViewObject)element;
					Object o = viewObject.getObject();
					if(o instanceof Geometry) {
						Scene.TheScene().removeGeometry((Geometry)o);
						// Geometry and Lights can only be deleted if they are in the currently selected frame.
						// Therefore, the render preview must be updated now.
						GuiWindow.Get().renderPreview();
					} else if (o instanceof Light) {
						Scene.TheScene().removeLight((Light)o);
						// Geometry and Lights can only be deleted if they are in the currently selected frame.
						// Therefore, the render preview must be updated now.
						GuiWindow.Get().renderPreview();
					} else if (o instanceof Shader) {
						Scene.TheScene().removeShader((Shader)o);
					}
					frameOutliner.removeNode(viewObject);
				}
			}
		});
		objectVGroup.add(delObjectBtn);
		
		JScrollPane right = new JScrollPane();
		frameOutliner = new FrameOutliner();
		right.setViewportView(frameOutliner);
		objectHGroup.add(right);
		
		JTextPane txtpnObjectOutliner = new JTextPane();
		txtpnObjectOutliner.setEditable(false);
		txtpnObjectOutliner.setText("Object Outliner");
		txtpnObjectOutliner.setEditable(false);
		right.setColumnHeaderView(txtpnObjectOutliner);
		
		doc = txtpnObjectOutliner.getStyledDocument();
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		
		setLeftComponent(sceneHGroup);
		setRightComponent(objectHGroup);
		
		setDividerLocation(200);
	}
	
	public void resetOutliners() {
		sceneOutliner.resetSceneOutliner();
		frameOutliner.resetFrameOutliner();
		// The scene data has probably changed
		// which means there might not be any frames left.
		// We want something selected still, so select
		// the scene by default.
		sceneOutliner.setSelectedIndex(0);
		
		// Update control button states
		frameAddBtn.setEnabled(true);
		frameDelBtn.setEnabled(false);
		sortUpBtn.setEnabled(false);
		sortDownBtn.setEnabled(false);
		addObjectBtn.setEnabled(false);
		delObjectBtn.setEnabled(false);
	}
	
	public void resetSceneOutliner() {
		sceneOutliner.resetSceneOutliner();
	}
	
	public void setSceneOutlinerSelectedIndex(int index) {
		sceneOutliner.setSelectedIndex(index);
	}
	
	public void addSceneOutlinerFrameEntry(Frame f) {
		sceneOutliner.addFrameEntry(f);
	}
	
	public void updateTreeViewNode(Object o) {
		frameOutliner.updateTreeViewNode(o);
	}
	
	public Frame getCurrentlySelectedFrame() {
		int index = sceneOutliner.getSelectedIndex();
		if (index > 0) {
			return Scene.TheScene().getFrames().get(index - 1);
		} else {
			return null;
		}
	}
	
	public void setAddObjectButtonEnabled(boolean b) {
		addObjectBtn.setEnabled(b);
	}
	
	public void setDelObjectButtonEnabled(boolean b) {
		delObjectBtn.setEnabled(b);
	}
}
