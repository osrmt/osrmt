package com.osframework.appclient.ui.controls;

import java.text.Format;

import javax.swing.JFormattedTextField;;

public class UIDateField extends JFormattedTextField implements IGetDocument {

	public UIDateField() {
		super();
	}

	public UIDateField(Format format) {
		super(format);
		//TODO need to utilize the format
	}

	public void setLocked(boolean locked) {
		super.setEditable(!locked);
	}


}
