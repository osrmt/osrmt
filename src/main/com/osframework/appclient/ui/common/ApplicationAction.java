package com.osframework.appclient.ui.common;

import java.awt.event.ActionListener;


public class ApplicationAction {
	
	private int actionRefId = 0;
	private ActionListener actionListener = null;
	private ControlState controlState = null;
	private Object userObject = null;
	
	public ApplicationAction() {
		
	}
	
	//TODO if the system groups were in the framework 
	// we could replace this with an ActionGroup class 
	public ApplicationAction(int actionRefId, ControlState controlState, ActionListener actionListener) {
		this.actionRefId = actionRefId;
		this.actionListener = actionListener;
		this.controlState = controlState;
	}
		
	public ActionListener getActionListener() {
		return actionListener;
	}
	
	public int getActionRefId() {
		return actionRefId;
	}
	
	public boolean getEnabled() {
		if (controlState == null) {
			return true;
		} else {
			return controlState.getEnabled();
		}
	}
	
	public ControlState getControlState() {
		return controlState;
	}

	public Object getUserObject() {
		return userObject;
	}

	public void setUserObject(Object userObject) {
		this.userObject = userObject;
	}

	public void setActionListener(ActionListener actionListener) {
		this.actionListener = actionListener;
	}
	
	

}
