package com.osframework.appclient.ui.components;

import java.awt.HeadlessException;
import java.awt.MenuItem;
import java.awt.MenuShortcut;

public class ApplicationMenuItem extends MenuItem {

	private static final long serialVersionUID = 1L;
	private Object object;
	
	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public ApplicationMenuItem() throws HeadlessException {
		super();
	}

	public ApplicationMenuItem(Object o) throws HeadlessException {
		super();
		setObject(o);
	}

	public ApplicationMenuItem(String label) throws HeadlessException {
		super(label);
	}

	public ApplicationMenuItem(String label, MenuShortcut s)
			throws HeadlessException {
		super(label, s);
	}

}
