package com.osframework.appclient.ui.components;

public class UIItem {
	
	private String display = "";
	private Object userObject;

	public UIItem(String display, Object o) {
		super();
		this.display = display;
		this.userObject = o;
	}

	
	public String getDisplay() {
		return display;
	}


	public void setDisplay(String display) {
		this.display = display;
	}


	public Object getUserObject() {
		return userObject;
	}


	public void setUserObject(Object userObject) {
		this.userObject = userObject;
	}


	public String toString() {
		return display;
	}

}
