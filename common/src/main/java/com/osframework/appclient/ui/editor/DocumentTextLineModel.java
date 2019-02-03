package com.osframework.appclient.ui.editor;

import java.awt.Component;

public class DocumentTextLineModel implements IDocumentLine {

	private String text = null;

	public Object getValue() {
		return text;
	}

	public void setValue(Object value) {
		if (value == null) {
			text = null;
		} else {
			text = String.valueOf(value);
		}
	}

	public String getIReportValue() {
		if (text == null) {
			return "";
		} else {
			return text;
		}
	}

	/**
	 * @see com.osframework.appclient.ui.editor.IDocumentLine#getRendererComponent()
	 */
	public Component getRendererComponent() {
		/*
		javax.swing.JLabel label = new JLabel();
		if (text != null) {
			label.setFont(Font.decode("Verdana-Plain-12"));
			label.setText(text);
		}
		return label;
		*/
		TextCellRenderer tc = new TextCellRenderer();
		tc.setText(text);
		return tc;
	}

	public int getRowHeight() {
		if (text == null) {
			return 16;
		} else {
			int i=0;
			int pos=text.indexOf("\n");
			while (pos > -1) {
				i++;
				pos=text.indexOf("\n", pos+1);
			}
			return (i+1)*16;
		}
	}
	
	
}
