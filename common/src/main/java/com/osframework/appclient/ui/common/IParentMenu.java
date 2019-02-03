/**
 * 
 */
package com.osframework.appclient.ui.common;

import javax.swing.JMenuItem;

import com.osframework.appclient.ui.controls.UIMenuItem;

/**
 * 
 *
 */
public interface IParentMenu {

	public JMenuItem addMenuItem(UIMenuItem menuItem);
	
	public void addSeparator();
}
