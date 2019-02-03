package com.osframework.appclient.ui.controls;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JPasswordField;
import javax.swing.text.Document;

public class UIPasswordField extends JPasswordField {

	private UIPasswordField thisPasswordField = this;
	
	public UIPasswordField() {
		super();
		customize();
	}

	public UIPasswordField(String text) {
		super(text);
		customize();
	}

	public UIPasswordField(int columns) {
		super(columns);
		customize();
	}

	public UIPasswordField(String text, int columns) {
		super(text, columns);
		customize();
	}

	public UIPasswordField(Document doc, String txt, int columns) {
		super(doc, txt, columns);
		customize();
	}

	public void customize() {
		this.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				try {
					thisPasswordField.selectAll();
				} catch (Exception ex) {}
			}
			public void focusLost(FocusEvent e) {
			}
		});
	}
}
