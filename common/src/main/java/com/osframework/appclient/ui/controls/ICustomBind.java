package com.osframework.appclient.ui.controls;

import javax.swing.event.ChangeListener;

public interface ICustomBind {

	public void setEnabled(boolean enabled);

	public void setLocked(boolean locked);
	
	public void addChangeListener(ChangeListener change);

}

