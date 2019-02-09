package com.osframework.appclient.ui.common;

import java.awt.Image;
import java.net.URL;
import java.util.Enumeration;

import javax.swing.*;

import com.osframework.appclient.ui.controls.*;
import com.osframework.framework.logging.Debug;
import com.osframework.modellibrary.reference.security.*;

public class MenuManager {
	
	private ApplicationControlList controls = new ApplicationControlList();
	
	private UIMenuBar menuBar = new UIMenuBar();
	private UIMenu currentMenu = null;
	private UIMenu subMenu = null;
	private UIMenuItem currentMenuItem = null;
	
	public MenuManager(ApplicationControlList controls) {
		this.controls = controls;
	}
	
	public UIMenuItem getLastMenuItem() {
		return currentMenuItem;
	}
	
	public void addSubMenu(String refDisplay) {
		UIMenu menu = new UIMenu();
		subMenu = menu;
		//TODO find all possible nulls and validate before you use
		//the object - protect from poor database configuration...
		if (currentMenu != null) {
			currentMenu.add(subMenu);
			
			Enumeration e1 = controls.elements();
			while (e1.hasMoreElements()) {
				ApplicationControlModel acm = (ApplicationControlModel) e1.nextElement();
				if (acm.getControlRefDisplay().compareTo(refDisplay)==0) {
					menu.setText(acm.getControlText());
				}
			}
		}
	}
	
	public void addMenuItem(String controlDisplay, ApplicationAction action, boolean appendSeparator) {
		addMenuItem(controlDisplay, action, appendSeparator, null);
	}
	
	public void addMenuItem(String controlDisplay, String overrideDisplay, ApplicationAction action, boolean appendSeparator) {
		addMenuItem(controlDisplay, overrideDisplay, action, appendSeparator, null, currentMenu);
	}
	
	public void addMenuItem(String controlDisplay, ApplicationAction action, boolean appendSeparator, KeyStroke keyStroke) {
		addMenuItem(controlDisplay, null, action, appendSeparator, keyStroke, currentMenu);
	}
	public void addSubMenuSeparator() {
		subMenu.addSeparator();
	}
	public void addSubMenuItem(String text, ApplicationAction action, boolean appendSeparator, KeyStroke keyStroke) {
		UIMenuItem menuItem = new UIMenuItem(); 
		menuItem.setText(text);
		if (action == null || action.getActionListener()==null) {
			menuItem.setEnabled(false);
		} else {
			menuItem.setApplicationAction(action);
			if (action.getUserObject() != null) {
				menuItem.setUserObject(action.getUserObject());
			}
		}
		if (keyStroke != null) {
			menuItem.setAccelerator(keyStroke);
		}
		currentMenuItem = menuItem;
		subMenu.addMenuItem(menuItem);
	}
	
	public void addSubMenuItemControl(String controlDisplay, ApplicationAction action, boolean appendSeparator, KeyStroke keyStroke) {
		Enumeration e1 = controls.elements();
		while (e1.hasMoreElements()) {
			ApplicationControlModel acm = (ApplicationControlModel) e1.nextElement();
			if (acm.getControlRefDisplay().compareTo(controlDisplay)==0) {
				UIMenuItem menuItem = new UIMenuItem(); 
				menuItem.setText(acm.getControlText());
				if (action == null || action.getActionListener()==null) {
					menuItem.setEnabled(false);
				} else {
					menuItem.setApplicationAction(action);
					if (action.getUserObject() != null) {
						menuItem.setUserObject(action.getUserObject());
					}
				}
				setIcon(menuItem, acm.getImagePath(), acm.getControlText());
				if (keyStroke != null) {
					menuItem.setAccelerator(keyStroke);
				}
				currentMenuItem = menuItem;
				subMenu.addMenuItem(menuItem);
				if (appendSeparator) {
					subMenu.addSeparator();
				}
			}
		}
	}
	

	
	public void addMenuItem(String refDisplay, String overrideDisplay, ApplicationAction action, boolean appendSeparator, KeyStroke keyStroke, IParentMenu parentMenu) {
		try {
		Enumeration e1 = controls.elements();
		while (e1.hasMoreElements()) {
			ApplicationControlModel acm = (ApplicationControlModel) e1.nextElement();
			if (acm.getControlRefDisplay().equalsIgnoreCase(refDisplay)) {
				UIMenuItem menuItem = new UIMenuItem(); 
				menuItem.setText(acm.getControlText());
				if (overrideDisplay != null) {
					menuItem.setText(overrideDisplay);
				}
				if (action == null || action.getActionListener() == null) {
					menuItem.setEnabled(false);
				} else {
					menuItem.setApplicationAction(action);
					if (action.getUserObject() != null) {
						menuItem.setUserObject(action.getUserObject());
					}
				}
				setIcon(menuItem, acm.getImagePath(), acm.getControlText());
				if (keyStroke != null) {
					menuItem.setAccelerator(keyStroke);
				}
				currentMenuItem = menuItem;
				parentMenu.addMenuItem(menuItem);
				if (appendSeparator) {
					parentMenu.addSeparator();
				}
			}
		}
		} catch (Exception ex) {
			Debug.LogException(this, ex);
		}
	}
	
	public void addMenu(String refDisplay) {
		Enumeration e1 = controls.elements();
		while (e1.hasMoreElements()) {
			ApplicationControlModel acm = (ApplicationControlModel) e1.nextElement();
			if (acm.getControlRefDisplay().compareTo(refDisplay)==0) {
				UIMenu menu = new UIMenu();
				menu.setText(acm.getControlText());
				currentMenu = menu;
				menuBar.add(menu);
			}
		}
	}
	
	public UIMenuBar getMenuBar() {
		return menuBar;
	}

	private void setIcon(JMenuItem menuItem, String imagePath, String altText) {
		if (imagePath != null) {
			Image image = GUI.getImage(imagePath, this);
			if (image != null) {
				menuItem.setIcon(new ImageIcon(image, altText));
			}
		}
	}
}
