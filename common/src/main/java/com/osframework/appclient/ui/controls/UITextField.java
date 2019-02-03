package com.osframework.appclient.ui.controls;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;
import javax.swing.text.Document;

public class UITextField extends JTextField  implements IGetDocument {
	
	private UITextField thisTextField = this;

	public UITextField() {
		super(12);
		customize();
	}

	public UITextField(String text) {
		super(text);
		customize();
	}

	public UITextField(int columns) {
		super(columns);
		customize();
	}

	public UITextField(String text, int columns) {
		super(text, columns);
		customize();
	}

	public UITextField(Document doc, String text, int columns) {
		super(doc, text, columns);
		customize();
	}
	
	public void customize() {
		this.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				try {
					thisTextField.selectAll();
				} catch (Exception ex) {}
			}
			public void focusLost(FocusEvent e) {
			}
		});
	}

	public void setLocked(boolean locked) {
		super.setEditable(!locked);
	}

}
