package com.osframework.appclient.ui.listeners;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import com.osframework.framework.logging.Debug;

public abstract class UIActionListener implements ActionListener {
	
	private JFrame frame = null;
	
	public UIActionListener() {

	}

	public UIActionListener(JFrame frame) {
		this.frame = frame;
	}

	public void actionPerformed(ActionEvent e) {
		try {
			//TODO This style of forcing an object parameter instead of
			// an int should be used throughout the beans
			if (frame != null) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			}
			actionExecuted(e);
			if (frame != null) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			if (frame != null) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
			//TODO should send visual message
		}
	}
	
	public abstract void actionExecuted(ActionEvent e) throws Exception ;

}
