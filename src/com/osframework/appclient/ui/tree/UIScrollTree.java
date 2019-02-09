package com.osframework.appclient.ui.tree;

import java.awt.Component;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;

import com.osframework.appclient.ui.common.*;
import com.osframework.appclient.ui.controls.UIJTree;
import com.osframework.appclient.ui.controls.UIProperties;
import com.osframework.framework.logging.Debug;
import com.osframework.modellibrary.common.*;
import com.osframework.modellibrary.reference.common.ReferenceDisplay;

public class UIScrollTree extends JScrollPane {

	private static final long serialVersionUID = 1L;
	private UIJTree tree = null;
	private IParent parent;
	private UITreeModel treeModel;
	private DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();

	public UITreeModel getTreeModel() {
		return treeModel;
	}

	//TODO all controls that are panels/scrollbar based need to GO 
	public void setTreeModel(UITreeModel treeModel) {
		this.treeModel = treeModel;
	}

	public UIScrollTree() {
		super();
		initialize();
		tree = new UIJTree();
		tree.setCellRenderer(renderer);
	}

	public UIScrollTree(IParent parent) {
		super();
		UIProperties.configureUI();
		this.parent = parent;
		initialize();
		tree = new UIJTree();
		tree.setCellRenderer(renderer);
		//instrument = Instrumenter.createInstrument(tree);

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setVisible(true);
	}

	public void display (UITreeModel dtm, TreeSelectionListener treeSelectionListener) {
		setTreeModel(dtm);
		tree = new UIJTree();
		tree.setCellRenderer(renderer);
		tree.setModel(dtm);
		tree.addTreeSelectionListener(treeSelectionListener);
		tree.setEditable(false);
		this.setViewportView(tree);
	}
	
	public boolean isNodeSelected() {
		return tree.getSelectionPath() != null;
	}

    public DefaultMutableTreeNode getSelectedNode() {
    	TreePath   selPath = tree.getSelectionPath();
    	if(selPath != null) {
    	    return (DefaultMutableTreeNode)selPath.getLastPathComponent();
    	}
    	return null;
    }
    
    public DefaultMutableTreeNode[] getSelectedNodes() {
    	TreePath[]  selPaths = tree.getSelectionPaths();
    	if(selPaths != null) {
    		DefaultMutableTreeNode[] nodes = new DefaultMutableTreeNode[selPaths.length];
    		for (int i=0; i< nodes.length; i++) {
    			nodes[i] = (DefaultMutableTreeNode) (selPaths[i].getLastPathComponent());
    		}
    	    return nodes;
    	}
    	return null;
    }
    
    public UIJTree getTree() {
    	return this.tree;
    }

    public Object getSelectedObject() {
    	TreePath   selPath = tree.getSelectionPath();
    	if(selPath != null) {
    		DefaultMutableTreeNode selected = (DefaultMutableTreeNode)selPath.getLastPathComponent();
    		return selected.getUserObject();
    	}
    	return null;
    }

    public Vector getSelectedUserObjects() {
    	Vector v= new Vector();
    	TreePath[]   selPaths = tree.getSelectionPaths();
    	if(selPaths != null) {
    		for (int i=0; i<selPaths.length; i++) {    			
        		DefaultMutableTreeNode selected = (DefaultMutableTreeNode)selPaths[i].getLastPathComponent();
        		v.add(selected.getUserObject());
    		}
    	}
    	return v;
    }

	public void setTree(UIJTree tree) {
		this.tree = tree;
	}

	public boolean hasFocus() {
		return super.hasFocus() || this.tree.hasFocus();
	}

	public DefaultTreeCellRenderer getRenderer() {
		return renderer;
	}

	public void setRenderer(DefaultTreeCellRenderer renderer) {
		this.renderer = renderer;
	}

	
}
