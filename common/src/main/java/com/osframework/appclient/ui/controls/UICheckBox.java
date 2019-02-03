package com.osframework.appclient.ui.controls;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JCheckBox;

public class UICheckBox extends JCheckBox implements IGetBoolean {

	public UICheckBox() {
		super();
	}

	public UICheckBox(Icon icon) {
		super(icon);
	}

	public UICheckBox(Icon icon, boolean selected) {
		super(icon, selected);
	}

	public UICheckBox(String text) {
		super(text);
	}

	public UICheckBox(Action a) {
		super(a);
	}

	public UICheckBox(String text, boolean selected) {
		super(text, selected);
	}

	public UICheckBox(String text, Icon icon) {
		super(text, icon);
	}

	public UICheckBox(String text, Icon icon, boolean selected) {
		super(text, icon, selected);
	}

	public boolean getBooleanValue() {
		return this.isSelected();
	}

	public void setBooleanValue(boolean value) {
		this.setSelected(value);		
	}

	public void setLocked(boolean locked) {
		// TODO Auto-generated method stub
		
	}
	


}

