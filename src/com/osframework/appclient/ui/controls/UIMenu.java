package com.osframework.appclient.ui.controls;

import javax.swing.*;

import com.osframework.framework.logging.Debug;

import java.awt.event.*;
import java.util.*;
import com.osframework.appclient.ui.common.*;

public class UIMenu extends JMenu implements IParentMenu {

	private Vector menuItems = new Vector();
	
	public void setEnabled() {
		try {
			Enumeration e1 = menuItems.elements();
			while (e1.hasMoreElements()) {
				UIMenuItem menuItem = (UIMenuItem) e1.nextElement();
				menuItem.setEnabled();
			}
		} catch (Exception ex) {
			Debug.LogException(this, ex);
		}
	}
	
	public UIMenu() {
		this.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
			}
			public void mouseEntered(MouseEvent e) {
			}
			public void mouseExited(MouseEvent e) {
			}
			public void mousePressed(MouseEvent e) {
				setEnabled();
			}
			public void mouseReleased(MouseEvent e) {
			}
		});
	}

	public JMenuItem addMenuItem(UIMenuItem menuItem) {
		menuItems.add(menuItem);
		JMenuItem item = super.add(menuItem);
		return item;
	}
	

}
