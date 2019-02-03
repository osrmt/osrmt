package com.osframework.appclient.ui.controls;

import javax.swing.Action;
import javax.swing.Icon;

import com.osframework.appclient.services.ReferenceServices;
import com.osframework.modellibrary.reference.group.FormButtonTextFramework;

public class ChangePasswordButton extends UIButton {

	public ChangePasswordButton() {
		super();
		setText(ReferenceServices.getDisplay(FormButtonTextFramework.CHANGEPASSWORD));
	}

	public ChangePasswordButton(Action a) {
		super(a);
		setText(ReferenceServices.getDisplay(FormButtonTextFramework.CHANGEPASSWORD));
	}

	public ChangePasswordButton(Icon icon) {
		super(icon);
		setText(ReferenceServices.getDisplay(FormButtonTextFramework.CHANGEPASSWORD));
	}

	public ChangePasswordButton(String text, Icon icon) {
		super(text, icon);
		setText(ReferenceServices.getDisplay(FormButtonTextFramework.CHANGEPASSWORD));
	}

	public ChangePasswordButton(String text) {
		super(text);
		setText(ReferenceServices.getDisplay(FormButtonTextFramework.CHANGEPASSWORD));
	}

	
}
