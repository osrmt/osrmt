package com.osframework.appclient.ui.controls;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JCheckBox;

public class UIIndicatorField extends JCheckBox {

	public UIIndicatorField() {
		super();
	}

	public UIIndicatorField(Icon icon) {
		super(icon);
	}

	public UIIndicatorField(Icon icon, boolean selected) {
		super(icon, selected);
	}

	public UIIndicatorField(String text) {
		super(text);
	}

	public UIIndicatorField(Action a) {
		super(a);
	}

	public UIIndicatorField(String text, boolean selected) {
		super(text, selected);
	}

	public UIIndicatorField(String text, Icon icon) {
		super(text, icon);
	}

	public UIIndicatorField(String text, Icon icon, boolean selected) {
		super(text, icon, selected);
	}

}
