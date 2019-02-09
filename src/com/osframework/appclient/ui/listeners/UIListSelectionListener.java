package com.osframework.appclient.ui.listeners;

import java.awt.Cursor;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.osframework.framework.logging.Debug;

public abstract class UIListSelectionListener implements ListSelectionListener {

	private JFrame frame = null;
	

	public UIListSelectionListener(JFrame frame) {
		this.frame = frame;
	}

	public void valueChanged(ListSelectionEvent e) {
		if (!e.getValueIsAdjusting()) {
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
				//TODO should send visual message
			}
		}
	}

	public abstract void valueChangedExecuted(ListSelectionEvent e) throws Exception;
}
