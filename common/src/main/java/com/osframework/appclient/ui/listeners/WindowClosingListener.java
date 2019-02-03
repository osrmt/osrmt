package com.osframework.appclient.ui.listeners;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public abstract class WindowClosingListener implements WindowListener {

	public void windowActivated(WindowEvent e) {
	}

	public abstract void windowClosed(WindowEvent e);
	
	public void windowClosing(WindowEvent e) {
		
	}
	
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
}
