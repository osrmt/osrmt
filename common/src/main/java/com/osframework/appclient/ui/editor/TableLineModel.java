package com.osframework.appclient.ui.editor;

import java.awt.Component;

import javax.swing.JTable;

public class TableLineModel implements IDocumentLine {

	private JTable table = null;

	public JTable getValue() {
		return table;
	}

	public void setValue(Object value) {
		if (value == null) {
			table = null;
		} else if (value instanceof JTable) {
			table = (JTable) value;
		} else {
			throw new java.lang.IllegalArgumentException(String.valueOf(value));
		}
	}

	public String getIReportValue() {
		return "unable to display table";
	}

	public Component getRendererComponent() {
		return table;
	}

	public int getRowHeight() {
		int height = table.getRowCount() * 16;
		if (height > 0) {
			return height;
		} else {
			return 16;
		}
	}
	
	
}
