package com.osframework.appclient.ui.controls;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;
import javax.swing.text.Document;

public class UIIntegerField extends JTextField  implements IGetDocument {

	private static final long serialVersionUID = 1L;
	private UIIntegerField thisField = this;
	
	public UIIntegerField() {
		super(8);
		this.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				thisField.selectAll();
			}
			public void focusLost(FocusEvent e) {
				
			}
		});
	}

	public UIIntegerField(String text) {
		super(text);
	}

	public UIIntegerField(int columns) {
		super(columns);
	}

	public UIIntegerField(String text, int columns) {
		super(text, columns);
	}

	public UIIntegerField(Document doc, String text, int columns) {
		super(doc, text, columns);
	}

	public void setText(int i) {
		super.setText("" + i);
	}
	
	public void setLocked(boolean locked) {
		super.setEditable(!locked);
	}

}
