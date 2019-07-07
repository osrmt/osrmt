package com.osframework.appclient.ui.components;

import java.awt.*;
import javax.swing.*;

import com.osframework.appclient.ui.controls.*;

/**
 * Creates a dialog of the form
 *   ---------------------------
 *  |                           |
 *  |                           |
 *  |                           |
 *   ---------------------------
 *  |                           |
 *   ---------------------------
 *  add the center and south panel
 *
 */
public class UICenterSouthDialog extends UIJDialog {

	private UIJPanel southPanel = new UIJPanel(new BorderLayout());
	private UIJPanel centerPanel = new UIJPanel(new BorderLayout());
	private Object userObject = null;
	
	public UICenterSouthDialog(Dialog owner, boolean modal)
			throws HeadlessException {
		super(owner, modal);
		initialize();
	}

	public UICenterSouthDialog(Frame owner, boolean modal)
			throws HeadlessException {
		super(owner, modal);
		initialize();
	}
	
	public void initialize() {
		this.setResizable(true);
		this.setLayout(new BorderLayout());
		this.add(southPanel, BorderLayout.SOUTH);
		this.add(centerPanel, BorderLayout.CENTER);
		this.setSize(UIProperties.getDIALOG_SIZE_450_330());
		this.setLocation(200,200);
	}

	public UIJPanel getCenterPanel() {
		return centerPanel;
	}

	public void setCenterPanel(UIJPanel centerPanel) {
		this.centerPanel = centerPanel;
	}

	public UIJPanel getSouthPanel() {
		return southPanel;
	}

	public void setSouthPanel(UIJPanel southPanel) {
		this.southPanel = southPanel;
	}

	public Object getUserObject() {
		return userObject;
	}

	public void setUserObject(Object userObject) {
		this.userObject = userObject;
	}

	
	
}
