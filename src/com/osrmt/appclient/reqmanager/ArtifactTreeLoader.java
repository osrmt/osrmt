/**
 * 
 */
package com.osrmt.appclient.reqmanager;

import java.util.*;
import com.osrmt.appclient.services.*;

import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

import com.osframework.appclient.ui.common.SwingWorker;
import com.osframework.appclient.ui.controls.*;
import com.osframework.appclient.ui.tree.*;
import com.osrmt.modellibrary.reqmanager.*;

/**
 * TreeLoader dynamically loads the tree
 * 
 */
public class ArtifactTreeLoader {
	
	/**
	 * Tree being loaded
	 */
	private UIJTree tree = null;
	/**
	 * Tree Model being loaded
	 */
	private UITreeModel treeModel = null;
	/**
	 * Track which branches have been populated
	 */
	private Hashtable<Integer, Integer> branchTable = null;
	

	/**
	 * Create the tree loader
	 * 
	 * @param treeModel
	 */
	public ArtifactTreeLoader(int productRefId, final UITreeModel treeModel, final UIJTree tree) throws Exception{
		this.tree = tree;
		this.treeModel = treeModel;
		this.branchTable = new Hashtable<Integer, Integer>();
		if (treeModel != null && tree != null) {
			addListeners(productRefId);
			initialLoad(productRefId);
		}
	}
	

	
	private boolean isExpanded(TreePath path) {
		/*
		Enumeration e1 = tree.getExpandedDescendants(new TreePath((UITreeNode) treeModel.getRoot()));
		while (e1.hasMoreElements()) {
			TreePath expanded = (TreePath) e1.nextElement();
			if (expanded.equals(path)) {
				return true;
			}
		}
		return false;
		*/
		return false;
	}
	
	/**
	 * Add listeners to trigger loading
	 *
	 */
	private void addListeners(final int productRefId) {
		if (this.tree != null) {
			this.tree.addTreeSelectionListener(new TreeSelectionListener() {
				public void valueChanged(TreeSelectionEvent e) {
					if (e.getPath() != null) {
						UITreeNode treeNode = (UITreeNode) e.getPath().getLastPathComponent();
						if (treeNode.getUserObject() instanceof ArtifactModel) {
							ArtifactModel a = (ArtifactModel) treeNode.getUserObject();
							loadBranch(a.getProductRefId(), treeNode, a);
						}
					}
				}
			});
			this.tree.addTreeExpansionListener(new TreeExpansionListener() {

				public void treeCollapsed(TreeExpansionEvent event) {
					
				}

				public void treeExpanded(TreeExpansionEvent e) {
					if (e.getPath() != null) {
						UITreeNode treeNode = (UITreeNode) e.getPath().getLastPathComponent();
						if (treeNode.getUserObject() instanceof ArtifactModel) {
							ArtifactModel a = (ArtifactModel) treeNode.getUserObject();
							loadBranch(productRefId, treeNode, a);
						}
					}
				}
				
			});
		}
	}
	
	/**
	 * Load the branch if not already loaded
	 * 
	 * @param selected
	 */
	private void loadBranch(int productRefId, UITreeNode treeNode, ArtifactModel selected) {
		if (selected.getArtifactRefId() > 0) {
			//branchTable.put(selected.getArtifactRefId(), selected.getArtifactRefId());
			backgroundLoad(productRefId, treeNode, selected);
		}
	}
	
	/**
	 * Load the top level of children
	 * 
	 * @throws Exception
	 */
	private void initialLoad(int productRefId) throws Exception {
		Enumeration e1 = ((UITreeNode) treeModel.getRoot()).breadthFirstEnumeration();
		while (e1.hasMoreElements()) {
			UITreeNode node = (UITreeNode) e1.nextElement();
			if (node.getChildCount() == 0) {
				loadChildren(productRefId, node, false);
			}
		}
	}
	
	/**
	 * Load the entire branch in a background thread
	 * 
	 * @param branchNode
	 * @param selected
	 */
	private void backgroundLoad(final int productRefId, final UITreeNode branchNode, ArtifactModel selected) {
		SwingWorker sw = new SwingWorker(tree.getRootPane()) {

			@Override
			protected void doNonUILogic() throws Exception {
				Enumeration e1 = branchNode.children();
				while (e1.hasMoreElements()) {
					UITreeNode node = (UITreeNode) e1.nextElement();
					if (node.getChildCount() == 0) {
						loadChildren(productRefId, node, false);
					}
				}
				
			}

			@Override
			protected void doUIUpdateLogic() throws Exception {
				
			}
			
		};
		sw.start();
	}
	
	/**
	 * Recursively load all the child nodes
	 * 
	 * @param parent
	 * @throws Exception
	 */
	public void loadChildren(final int productRefId, final UITreeNode parent, boolean recursive) throws Exception {
		if (parent != null && parent.getUserObject() instanceof ArtifactModel) {
			ArtifactModel artifact = (ArtifactModel) parent.getUserObject();
			List<UITreeNode> nodes = RequirementServices.getRelatedArtifacts(productRefId, artifact, true, false);
			final int[] childIndicies = new int[nodes.size()];
			int i=0;
			for (final UITreeNode node : nodes) {
				parent.add(node);
				childIndicies[i] = i;
				i++;
			}
			javax.swing.SwingUtilities.invokeLater(new Runnable() {

				public void run() {
					treeModel.nodesWereInserted(parent, childIndicies);
				}
				
			});
			Thread.yield();
			if (recursive) {
				for (UITreeNode node : nodes) {
					loadChildren(productRefId, node, recursive);
				}
			}
			
		}
	}
}
