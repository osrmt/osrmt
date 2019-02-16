package com.osframework.appclient.ui.editor;

import java.awt.Component;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.JTable;

public class ImageLineModel implements IDocumentLine {

	private Image image = null;
	private String path = null;
	
	public ImageLineModel(Image image, String path) {
		this.image = image;
		this.path = path;
	}

	public Image getValue() {
		return image;
	}

	public void setValue(Object value) {
		if (value == null) {
			image = null;
		} else if (value instanceof Image) {
			image = (Image) value;
		} else {
			throw new java.lang.IllegalArgumentException(String.valueOf(value));
		}
	}

	public String getIReportValue() {
		return path;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Component getRendererComponent() {
		JLabel label = new JLabel();
		label.setIcon(new javax.swing.ImageIcon(image));
		return label;
	}

	public int getRowHeight() {
		int height = image.getHeight(null);
		if (height > 0) {
			return height;
		} else {
			return 16;
		}
	}
	
	
}
