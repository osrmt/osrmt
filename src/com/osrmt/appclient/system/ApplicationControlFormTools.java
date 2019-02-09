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

*/package com.osrmt.appclient.system;

import javax.swing.event.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

import com.osframework.modellibrary.reference.security.*;
import com.osrmt.appclient.system.*;
import com.osrmt.appclient.services.*;
import com.osframework.appclient.ui.common.*;
import com.osframework.appclient.ui.listeners.*;
import com.osframework.appclient.ui.controls.*;
import com.osframework.modellibrary.reference.common.*;
import com.osframework.modellibrary.reference.security.*;
import com.osrmt.modellibrary.reference.group.*;
import com.osframework.framework.logging.*;
import com.osframework.framework.utility.*;
import com.osframework.appclient.services.*;


/**
null
*/
public class ApplicationControlFormTools {

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
		MenuManager menuManager = new MenuManager(new ApplicationControlList());
		/*
		MenuManager menuManager = new MenuManager(SecurityServices.getAppControlsByUser(ApplicationGroup.YourAppMENU));
		menuManager.addMenu(ControlsGroup.YourAppMENUFILE);
		menuManager.addSubMenu(ControlsGroup.YourAppMENUFILENEW);
		ApplicationSecurityList list = SecurityServices.getAppSecurity(ApplicationGroup.YourSubType);
		Enumeration e1 = list.elements();
		while (e1.hasMoreElements()) {
			ApplicationSecurityModel asm = (ApplicationSecurityModel) e1.nextElement();
			ApplicationAction action = actions.getAction(ActionGroup.YourAppFILENEW);
			action.setUserObject(asm);
			menuManager.addSubMenuItem(asm.getAppTypeRefDisplay(),action , false, null);
		}
		menuManager.addMenuItem(ControlsGroup.YourAppFILEOPEN, actions.getAction(ActionGroup.YourAppFILEOPEN), false, Keys.YourAccelerator);
		*/
		return menuManager.getMenuBar();
	}
}