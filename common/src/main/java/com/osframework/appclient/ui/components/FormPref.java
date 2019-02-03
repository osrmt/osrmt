package com.osframework.appclient.ui.components;

import java.awt.Dimension;
import java.awt.Point;

public class FormPref {
	
	private Point location;
	private Dimension size;

	public FormPref(Point location, Dimension size) {
		super();
		this.location = location;
		this.size = size;
	}

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
	}

	public Dimension getSize() {
		return size;
	}

	public void setSize(Dimension size) {
		this.size = size;
	}

	
}

