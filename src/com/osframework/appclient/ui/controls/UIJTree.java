package com.osframework.appclient.ui.controls;

import javax.swing.*;
import javax.swing.tree.*;

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
