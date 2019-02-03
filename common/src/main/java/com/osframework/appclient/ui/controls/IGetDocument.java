package com.osframework.appclient.ui.controls;


import javax.swing.text.Document;


public interface IGetDocument {
	
	public Document getDocument();
	
	public void setEnabled(boolean enabled);
	
	public String getText();
	
	public void setText(String text);
	
	public void setLocked(boolean locked);
	
}
