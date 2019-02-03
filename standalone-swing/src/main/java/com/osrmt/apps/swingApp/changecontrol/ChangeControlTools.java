package com.osrmt.apps.swingApp.changecontrol;

import java.util.Enumeration;

import com.osframework.appclient.services.SecurityServices;
import com.osframework.appclient.ui.common.ApplicationActionList;
import com.osframework.appclient.ui.common.Keys;
import com.osframework.appclient.ui.common.MenuManager;
import com.osframework.appclient.ui.controls.UIMenuBar;
import com.osframework.appclient.ui.controls.UIToolBar;
import com.osframework.modellibrary.reference.group.ApplicationFramework;
import com.osframework.modellibrary.reference.security.ApplicationControlList;
import com.osframework.modellibrary.reference.security.ApplicationControlModel;
import com.osrmt.modellibrary.reference.group.ActionGroup;
import com.osrmt.modellibrary.reference.group.ApplicationGroup;

public class ChangeControlTools {
	
	public UIToolBar getToolBar(ApplicationActionList actions) {
		UIToolBar toolBar = new UIToolBar();
		ApplicationControlList controls = SecurityServices.getAppControlsByUser(ApplicationFramework.get(ApplicationGroup.CHANGECONTROLTOOLBAR));
		Enumeration e1 = controls.elements();
		while (e1.hasMoreElements()) {
			ApplicationControlModel m = (ApplicationControlModel) e1.nextElement();
			toolBar.addNewButton(m, "Toolbar - New", actions.getAction(ActionGroup.CHANGECONTROLNEW));
			toolBar.addNewButton(m, "Toolbar - Refresh", actions.getAction(ActionGroup.CHANGECONTROLREFRESH));
			toolBar.addNewButton(m, "Toolbar - Print", actions.getAction(ActionGroup.CHANGECONTROLPRINT));
		}
		return toolBar;
	}
	
	public UIMenuBar getMenuBar(ApplicationActionList actions) {
		
		MenuManager menuManager = new MenuManager(SecurityServices.getAppControlsByUser(ApplicationFramework.get(ApplicationGroup.CHANGECONTROLMENU)));
		menuManager.addMenu("Menu File");
		
		menuManager.addMenuItem("Menu File - New", actions.getAction(ActionGroup.CHANGECONTROLNEW), true, Keys.getCntrl_N());
		menuManager.addMenuItem("Menu File - Refresh", actions.getAction(ActionGroup.CHANGECONTROLREFRESH), true, Keys.get_F5());
		//menuManager.addMenuItem("Menu File - Import", actions.getAction(ActionGroup.REQMGRFILEIMPORT), false);
		//menuManager.addMenuItem("Menu File - Export", actions.getAction(ActionGroup.REQMGRFILEEXPORT), true);
		menuManager.addMenuItem("Menu File - Close", actions.getAction(ActionGroup.CHANGECONTROLCLOSE), false);

		menuManager.addMenu("Menu Edit");
		menuManager.addMenuItem("Menu Edit - Item", actions.getAction(ActionGroup.CHANGECONTROLEDIT), true, Keys.getShift_F2());
		menuManager.addMenuItem("Menu Edit - Find", actions.getAction(ActionGroup.CHANGECONTROLFIND), false);
		
		return menuManager.getMenuBar();
		
	}
	

}
