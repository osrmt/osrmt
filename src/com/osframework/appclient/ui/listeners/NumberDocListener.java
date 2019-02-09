package com.osframework.appclient.ui.listeners;

import javax.swing.event.*;

import com.osframework.appclient.ui.common.GUI;

/**
 * TextDocListener is used to return the entered from the Document model
 * Example:
 *			JTextField control = new JTextField(10);
 *			control.getDocument().addDocumentListener(new TextDocListener() {
 *				public void call(String p) {
 *					findPatient.setPatientName(p);
 *				}				
 *			});
 */
public abstract class NumberDocListener implements DocumentListener {
	
	public NumberDocListener() {
	}

	public void insertUpdate(DocumentEvent e) {
		call(getNumber(GUI.getText(e)));
	}

	public void removeUpdate(DocumentEvent e) {
		call(getNumber(GUI.getText(e)));
	}

	public void changedUpdate(DocumentEvent e) {
		call(getNumber(GUI.getText(e)));
	}
	
	private int getNumber(String s) {
		if (s == null) {
			return 0;
		} else {
			try {
				return Integer.parseInt(s);
			} catch (Exception ex) {
				return 0;
			}
		}
	}
	
	/**
	 * The implemented method will be called with every
	 * change to the Document.
	 * 
	 * @param documentText document contents
	 */
	public abstract void call(int intValue);

}
