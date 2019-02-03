package com.osframework.appclient.ui.controls;


import javax.swing.ButtonModel;


public interface IGetBoolean {
	
	public boolean getBooleanValue();
	
	public void setBooleanValue(boolean value);
	
	public ButtonModel getModel();
	
	public void setEnabled(boolean enabled);
	
	public void setLocked(boolean locked);
}
