package com.osframework.appclient.ui.controls;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import com.osframework.appclient.ui.common.IParentMenu;
import com.osframework.framework.logging.Debug;

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
