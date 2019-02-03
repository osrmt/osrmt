package com.osframework.appclient.ui.listeners;

import java.util.GregorianCalendar;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.osframework.appclient.ui.common.GUI;
import com.osframework.framework.logging.Debug;
import com.osframework.framework.utility.CalendarUtility;

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
public abstract class DateDocListener implements DocumentListener {
	
	public DateDocListener() {
	}

	public void insertUpdate(DocumentEvent e) {
		callDate(GUI.getText(e));
	}

	public void removeUpdate(DocumentEvent e) {
		callDate(GUI.getText(e));
	}

	public void changedUpdate(DocumentEvent e) {
		callDate(GUI.getText(e));
	}
	
	public void callDate(String s) {
		try {
			if (CalendarUtility.shouldParseDate(s, CalendarUtility.ShortDateFormat())) {
				call (CalendarUtility.Parse(s, CalendarUtility.ShortDateFormat()));
			} else if (CalendarUtility.shouldParseDate(s, CalendarUtility.LongFormat())) {
				call (CalendarUtility.Parse(s, CalendarUtility.LongFormat()));
			}
		} catch (Exception ex) {
			Debug.LogException(this, ex);
		}
	}
	/**
	 * The implemented method will be called with every
	 * change to the Document.
	 * 
	 * @param documentText document contents
	 */
	public abstract void call(GregorianCalendar calendar);

}
