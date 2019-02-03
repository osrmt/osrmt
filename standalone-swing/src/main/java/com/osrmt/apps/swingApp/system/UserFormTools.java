/*
    //usage

    Copyright (C) 2006  Aron Lancout Smith

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA

*/
package com.osrmt.apps.swingApp.system;

import javax.swing.JMenuBar;

import com.osframework.appclient.services.SecurityServices;
import com.osframework.appclient.ui.common.ApplicationActionList;
import com.osframework.appclient.ui.common.MenuManager;
import com.osframework.appclient.ui.controls.UIToolBar;
import com.osframework.modellibrary.reference.group.ApplicationFramework;
import com.osrmt.modellibrary.reference.group.ActionGroup;
import com.osrmt.modellibrary.reference.group.ApplicationGroup;


/**
null
*/
public class UserFormTools {

	public UIToolBar getToolBar(ApplicationActionList actions) {
		UIToolBar toolBar = new UIToolBar();
		/*
		ApplicationControlList controls = SecurityServices.getAppControlsByUser(ApplicationGroup.YourApp);
		Enumeration e1 = controls.elements();
		while (e1.hasMoreElements()) {
			ApplicationControlModel m = (ApplicationControlModel) e1.nextElement();
			toolBar.addNewButton(m, ControlsGroup.YourAppTOOLBARNEW, actions.getAction(ActionGroup.YourAppFILENEW));
		}
		*/
		return toolBar;
	}
	
	public JMenuBar getMenuBar(ApplicationActionList actions) {
		MenuManager menuManager = new MenuManager(SecurityServices.getAppControlsByUser(ApplicationFramework.get(ApplicationGroup.USERADMINMENU)));
		menuManager.addMenu("Menu User");
		menuManager.addMenuItem("Menu User - Password", actions.getAction(ActionGroup.USERADMINPASSWORD), false);
		menuManager.addMenuItem("Menu User - Security", actions.getAction(ActionGroup.USERADMINSECURITY), false);
		
		return menuManager.getMenuBar();
	}
}