package com.osframework.appclient.ui.controls;

import javax.swing.*;

import com.osframework.framework.logging.Debug;

import java.util.Enumeration;
import java.util.Vector;

public class UIMenuBar extends JMenuBar {
	
	private Vector items = new Vector();
	
	public void setEnabled() {
		try {
			Enumeration e1 = items.elements();
			while (e1.hasMoreElements()) {
				UIMenu menu = (UIMenu) e1.nextElement();
				menu.setEnabled();
			}
		} catch (Exception ex) {
			Debug.LogException(this, ex);
		}
	}
	
	public void addMenu(UIMenu item) {
		items.add(item);
		add(item);
	}

}
