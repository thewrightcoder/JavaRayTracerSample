package application.components;

import java.awt.Component;
import java.util.EventObject;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import application.DisplayStrings;
import application.GuiWindow;
import application.treeViewObjects.TreeViewObject;
import dataTypes.baseClassDataTypes.Camera;
import application.dataViewObjects.dataViewFactories.*;

public class FrameOutliner extends JTree {
	
	private class FrameOutlinerTreeRenderer extends DefaultTreeCellRenderer {

		private static final long serialVersionUID = 1L;
		private final DefaultTreeCellRenderer defaultRenderer = new DefaultTreeCellRenderer();
		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
				boolean leaf, int row, boolean hasFocus) {
			
				return defaultRenderer.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
		}
	};
	
	private static final long serialVersionUID = 1L;
	
	private static final DataViewFactory[] dataViewObjectFactories = {
			// Geometry
			new DataViewFactoryPlane(), new DataViewFactorySphere(),
			
			// Lights
			new DataViewFactorySpotLight(), new DataViewFactoryPointLight(),
			
			// Shaders
			new DataViewFactoryFlatShader(), new DataViewFactoryBlinnShader(), new DataViewFactoryLambertShader(),
			new DataViewFactoryReflectiveShader(),
			
			// Scene and Frame stuff
			new DataViewFactoryCamera(), new DataViewFactorySceneData(),
			
			// Adding Gouraud Shader factory last on purpose.
			// It's primary purpose is debugging,
			// so I don't wanted it iterated over frequently.
			new DataViewFactoryGouraudShader()
	};
	
	private DefaultTreeModel model = null;
	private DefaultMutableTreeNode root = null;
	public FrameOutliner() {
		super();
		setRootVisible(false);
		setEditable(true);
		root = new DefaultMutableTreeNode();
		model = new DefaultTreeModel(root);
		setModel(model);
		model.setRoot(root);
		FrameOutlinerTreeRenderer renderer = new FrameOutlinerTreeRenderer();
		setCellRenderer(renderer);
		// No nodes on this tree should be editable, so disable editing for everything.
		setCellEditor(new DefaultTreeCellEditor(this, renderer) {
			@Override
			public boolean isCellEditable(EventObject event) {
				return false;
			}
		});
		
		addTreeSelectionListener(new TreeSelectionListener(){
			public void valueChanged(TreeSelectionEvent tse){
				final TreePath path = tse.getNewLeadSelectionPath();
				if (path != null) {
					Object element = path.getLastPathComponent();
					if (element instanceof TreeViewObject) {
						
						TreeViewObject tvo = (TreeViewObject)element;
						Component comp = null;
						
						for (int i = 0; i < dataViewObjectFactories.length; ++i) {
							comp = dataViewObjectFactories[i].getDataViewObject(tvo.getTypeName(), tvo.getObject());
							if (comp != null) {
								break;
							}
						}
						// can't delete Camera or Scene Data.
						if (comp == null || (tvo.getObject() instanceof Camera) || (tvo.getTypeName().compareTo(DisplayStrings.SceneDataName) == 0)) {
							GuiWindow.Get().setDelObjectButtonEnabled(false);
						} else {
							GuiWindow.Get().setDelObjectButtonEnabled(true);
						}
						GuiWindow.Get().updateDataViewer(comp);
					}
				}
			}
		});
	}
	
	public void resetFrameOutliner() {
		model.setRoot(null);
		root = null;
	}
	
	public void setDisplay(DefaultMutableTreeNode newNodes) {
		resetFrameOutliner();
		
		if (newNodes != null) {
			expandPath(new TreePath(newNodes.getPath()));
			setRootVisible(false);
			model.setRoot(newNodes);
			root = newNodes;
			setSelectionInterval(0,0);
		} else {
			GuiWindow.Get().updateDataViewer(null);
		}
	}
	
	public void updateTreeViewNode(Object o) {
		updateTreeViewNode(o, null);
	}
	
	private boolean updateTreeViewNode(Object o, TreeNode node) {
		if (node == null) {
			node = root;
		}
		TreeNode node2 = null;
		for (int i = 0; i < node.getChildCount(); ++i) {
			node2 = node.getChildAt(i);
			if (node2 instanceof TreeViewObject) {
				TreeViewObject theNode = (TreeViewObject)node2;
				if (theNode.getObject() == o) {
					model.nodeChanged(node2);
					return true;
				}
			} else {
				if (node2.getChildCount() > 0) {
					if (updateTreeViewNode(o, node2)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public void addNode(String parentName, TreeViewObject node) {
		for (int i = 0; i < root.getChildCount(); ++i) {
			DefaultMutableTreeNode node2 = (DefaultMutableTreeNode)root.getChildAt(i);
			if (node2.toString() == parentName) {
				node2.add(node);
				model.nodeChanged(node2);
				model.reload(node2);
				break;
			}
		}
	}
	public void removeNode(TreeViewObject node) {
		for (int i = 0; i < root.getChildCount(); ++i) {
			DefaultMutableTreeNode node2 = (DefaultMutableTreeNode)root.getChildAt(i);
			if (node2 == node.getParent()) {
				node2.remove(node);
				model.nodeChanged(node2);
				model.reload(node2);
				break;
			}
		}
	}
}
