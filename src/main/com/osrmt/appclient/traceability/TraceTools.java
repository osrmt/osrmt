package com.osrmt.appclient.traceability;

import java.awt.event.*;
import java.net.URL;
import java.util.Enumeration;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

import com.osframework.appclient.services.SecurityServices;
import com.osframework.appclient.ui.common.*;
import com.osframework.appclient.ui.controls.*;
import com.osframework.framework.logging.Debug;
import com.osframework.modellibrary.reference.group.ApplicationFramework;
import com.osframework.modellibrary.reference.security.*;
import com.osrmt.appclient.common.ApplicationObject;
import com.osrmt.modellibrary.reference.group.*;

public class TraceTools {
	
	public UIToolBar getToolBar(ApplicationActionList actions) {
		UIToolBar toolBar = new UIToolBar();
		ApplicationControlList controls = SecurityServices.getAppControlsByUser(ApplicationFramework.get(ApplicationGroup.TRACEABILITYTOOLBAR));
		Enumeration e1 = controls.elements();
		while (e1.hasMoreElements()) {
			ApplicationControlModel m = (ApplicationControlModel) e1.nextElement();
			toolBar.addNewButton(m, "Toolbar - Trace Left to Right", actions.getAction(ActionGroup.TRACELEFTTORIGHT));
			toolBar.addNewButton(m, "Toolbar - Trace Right to Left", actions.getAction(ActionGroup.TRACERIGHTTOLEFT));
			toolBar.addNewButton(m, "Toolbar - Untrace", actions.getAction(ActionGroup.TRACEEDITUNTRACE));
			toolBar.addNewButton(m, "Toolbar - Refresh", actions.getAction(ActionGroup.TRACEFILEREFRESH));
			toolBar.addNewButton(m, "Toolbar - Print", actions.getAction(ActionGroup.TRACEFILEPRINT));
			toolBar.addNewButton(m, "Toolbar - Criteria", actions.getAction(ActionGroup.TRACEEDITCRITERIA));
		}
		return toolBar;
	}
	
	public UIMenuBar getMenuBar(ApplicationActionList actions) {
		
		MenuManager menuManager = new MenuManager(SecurityServices.getAppControlsByUser(ApplicationFramework.get(ApplicationGroup.TRACEABILITYMENU)));
		menuManager.addMenu("Menu File");
		
		menuManager.addMenuItem("Menu File - Open Product", actions.getAction(ActionGroup.TRACEFILEOPENPRODUCT), false, Keys.getCntrl_O());
		menuManager.addMenuItem("Menu File - Refresh", actions.getAction(ActionGroup.TRACEFILEREFRESH), true, Keys.get_F5());
		menuManager.addMenuItem("Menu File - Print", actions.getAction(ActionGroup.TRACEFILEPRINT), true, Keys.getCntrl_P());
		menuManager.addMenuItem("Menu File - Close", actions.getAction(ActionGroup.TRACEFILECLOSE), false);

		menuManager.addMenu("Menu Edit");
		menuManager.addMenuItem("Menu Edit - Trace Left to Right", actions.getAction(ActionGroup.TRACELEFTTORIGHT), false, Keys.getShift_F2());
		menuManager.addMenuItem("Menu Edit - Trace Right to Left", actions.getAction(ActionGroup.TRACERIGHTTOLEFT), true, Keys.getShift_F2());
		menuManager.addMenuItem("Menu Edit - Untrace", actions.getAction(ActionGroup.TRACEEDITUNTRACE), true);
		menuManager.addMenuItem("Menu Edit - Criteria", actions.getAction(ActionGroup.TRACEEDITCRITERIA), false);
		
		return menuManager.getMenuBar();
		
	}
	

}
