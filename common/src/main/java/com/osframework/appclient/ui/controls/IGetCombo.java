package com.osframework.appclient.ui.controls;


import javax.swing.ComboBoxModel;


public interface IGetCombo {
	
	public ComboBoxModel getModel();
	
	public void setEnabled(boolean enabled);
	
	public void setLocked(boolean locked);
	
}
