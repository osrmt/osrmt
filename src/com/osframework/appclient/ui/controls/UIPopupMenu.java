/**
 * 
 */
package com.osframework.appclient.ui.controls;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import com.osframework.appclient.ui.common.*;
/**
 * 
 *
 */
public class UIPopupMenu extends JPopupMenu implements IParentMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public UIPopupMenu() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param label
	 */
	public UIPopupMenu(String label) {
		super(label);
		// TODO Auto-generated constructor stub
	}

	public JMenuItem addMenuItem(UIMenuItem menuItem) {
		return add(menuItem);
	}

}
