package com.osframework.appclient.ui.controls;

import javax.swing.JTree;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

public class UIJTree extends JTree {
	
	public UIJTree () {
		super();
		try {
			UIProperties.configureUI();
		} finally {
			
		}
	}

	public UIJTree(TreeModel newModel) {
		super(newModel);
	}

	public UIJTree(TreeNode root) {
		super(root);
	}
		

}
