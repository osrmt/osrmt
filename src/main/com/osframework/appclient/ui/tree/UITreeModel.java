package com.osframework.appclient.ui.tree;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import java.util.*;

import com.osframework.modellibrary.common.*;
import com.osframework.modellibrary.reference.common.ReferenceDisplay;
import com.osframework.modellibrary.reference.common.ReferenceDisplayList;

/**

 * TreeBuilder generates a DefaultTreeModel from Reference data
 *
 */
public class UITreeModel extends DefaultTreeModel {

	private static final long serialVersionUID = 1L;
	private Object userObject = null;
	
	public UITreeModel(DefaultMutableTreeNode root) {
		super(root);
	}

	public Object getUserObject() {
		return userObject;
	}

	public void setUserObject(Object userObject) {
		this.userObject = userObject;
	}
	
	
	
}
