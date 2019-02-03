package com.osframework.appclient.ui.listeners;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

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
public abstract class TextDocListener implements DocumentListener {
	
	public TextDocListener() {
	}

	public void insertUpdate(DocumentEvent e) {
		call(GUI.getText(e));
	}

	public void removeUpdate(DocumentEvent e) {
		call(GUI.getText(e));
	}

	public void changedUpdate(DocumentEvent e) {
		call(GUI.getText(e));
	}
	
	/**
	 * The implemented method will be called with every
	 * change to the Document.
	 * 
	 * @param documentText document contents
	 */
	public abstract void call(String documentText);

}
