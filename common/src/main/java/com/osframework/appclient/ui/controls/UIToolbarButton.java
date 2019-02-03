package com.osframework.appclient.ui.controls;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;

import com.osframework.appclient.ui.common.ApplicationAction;

public class UIToolbarButton extends JButton {

	private Object userObject = null;
	private ApplicationAction applicationAction = null;
	
	public UIToolbarButton() {
		super();
		configure();
	}

	public UIToolbarButton(Icon icon) {
		super(icon);
		configure();
	}

	public UIToolbarButton(String text) {
		super(text);
		configure();
	}

	public UIToolbarButton(Action a) {
		super(a);
		configure();
	}

	public UIToolbarButton(String text, Icon icon) {
		super(text, icon);
		configure();
	}
	
	public void configure() {
		this.setSize(24,24);
		this.setPreferredSize(new java.awt.Dimension(24,24));
		this.setBorderPainted(false);
		this.setFocusable(false);
	}

	
	public void setEnabled() {
		if (applicationAction != null) {
			this.setEnabled(applicationAction.getEnabled());
		} else {
			this.setEnabled(false);
		}
	}

	public ApplicationAction getApplicationAction() {
		return applicationAction;
	}

	public void setApplicationAction(ApplicationAction action) {
		this.applicationAction = action;
	}

	public Object getUserObject() {
		return userObject;
	}

	public void setUserObject(Object userObject) {
		this.userObject = userObject;
	}

}
