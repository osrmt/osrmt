package com.osframework.appclient.ui.controls;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;
import javax.swing.text.Document;

public class UIDoubleField extends JTextField  implements IGetDocument {

	private static final long serialVersionUID = 1L;
	private UIDoubleField thisField = this;
	public UIDoubleField() {
		super(8);
		this.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				thisField.selectAll();
			}
			public void focusLost(FocusEvent e) {
				
			}
		});
	}

	public UIDoubleField(String text) {
		super(text);
	}

	public UIDoubleField(int columns) {
		super(columns);
	}

	public UIDoubleField(String text, int columns) {
		super(text, columns);
	}

	public UIDoubleField(Document doc, String text, int columns) {
		super(doc, text, columns);
	}

	public void setText(double d) {
		super.setText("" + d);
	}
	
	public void setLocked(boolean locked) {
		super.setEditable(!locked);
	}

}
