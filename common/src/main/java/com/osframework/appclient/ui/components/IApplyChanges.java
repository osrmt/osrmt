package com.osframework.appclient.ui.components;

public interface IApplyChanges {
	
	public boolean hasOutstandingChanges();
	
	public boolean applyOutstandingChanges();
	
	public boolean applyOutstandingChanges(String label);
	

}

