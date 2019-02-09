package com.osframework.appclient.ui.listeners;

import java.awt.Cursor;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.event.*;

import com.osframework.framework.logging.Debug;

public abstract class UIListDataSelectionListener implements ListDataListener {

	private JFrame frame = null;	

	public UIListDataSelectionListener(JFrame frame) {
		this.frame = frame;
	}
	
	public void contentsChanged(ListDataEvent e) {
		valueChanged(e);		
	}
	
	public void intervalAdded(ListDataEvent e) {
		valueChanged(e);
	}
	
	public void intervalRemoved(ListDataEvent e) {
		valueChanged(e);
	}

	public void valueChanged(ListDataEvent e) {
		try {
			if (frame != null) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			}
			valueChangedExecuted(e);
			if (frame != null) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			if (frame != null) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		}
	}

	public abstract void valueChangedExecuted(ListDataEvent e) throws Exception;
}
