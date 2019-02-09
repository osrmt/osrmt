package com.osframework.appclient.ui.controls;

import javax.swing.*;

import com.osframework.appclient.ui.common.ApplicationAction;

public class UIMenuItem extends JMenuItem {
	
	private Object userObject = null;
	private ApplicationAction action = null;
	
	
	public void setEnabled() {
		if (action != null) {
			this.setEnabled(action.getEnabled());
		} else {
			this.setEnabled(false);
		}
	}

	public void setApplicationAction(ApplicationAction action) {
		this.action = action;
		this.addActionListener(action.getActionListener());
	}
	
	public ApplicationAction getApplicationAction() {
		return this.action;
	}

	public Object getUserObject() {
		return userObject;
	}

	public void setUserObject(Object userObject) {
		this.userObject = userObject;
	}

	public UIMenuItem() {
		super();
	}

	public UIMenuItem(Action a) {
		super(a);
	}

	public UIMenuItem(Icon icon) {
		super(icon);
	}

	public UIMenuItem(String text, Icon icon) {
		super(text, icon);
	}

	public UIMenuItem(String text, int mnemonic) {
		super(text, mnemonic);
	}

	public UIMenuItem(String text) {
		super(text);
	}

}
