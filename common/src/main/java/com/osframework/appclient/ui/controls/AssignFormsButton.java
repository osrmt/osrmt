package com.osframework.appclient.ui.controls;

import javax.swing.Action;
import javax.swing.Icon;

import com.osframework.appclient.services.ReferenceServices;
import com.osframework.modellibrary.reference.group.FormButtonTextFramework;

public class AssignFormsButton extends UIButton {

	public AssignFormsButton() {
		super();
		setText(ReferenceServices.getDisplay(FormButtonTextFramework.ASSIGNFORMS));
	}

	public AssignFormsButton(Action a) {
		super(a);
		setText(ReferenceServices.getDisplay(FormButtonTextFramework.ASSIGNFORMS));
	}

	public AssignFormsButton(Icon icon) {
		super(icon);
		setText(ReferenceServices.getDisplay(FormButtonTextFramework.ASSIGNFORMS));
	}

	public AssignFormsButton(String text, Icon icon) {
		super(text, icon);
		setText(ReferenceServices.getDisplay(FormButtonTextFramework.ASSIGNFORMS));
	}

	public AssignFormsButton(String text) {
		super(text);
		setText(ReferenceServices.getDisplay(FormButtonTextFramework.ASSIGNFORMS));
	}

	
}
