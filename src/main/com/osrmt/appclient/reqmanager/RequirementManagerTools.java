package com.osrmt.appclient.reqmanager;

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

public class RequirementManagerTools {
	
	private static MenuManager menuManager = null;
	
	public UIToolBar getToolBar(ApplicationActionList actions) {
		UIToolBar toolBar = new UIToolBar();
		ApplicationControlList controls = SecurityServices.getAppControlsByUser(ApplicationFramework.get(ApplicationGroup.OSRMTTOOLBAR));
		Enumeration e1 = controls.elements();
		while (e1.hasMoreElements()) {
			//TODO must match the display - should be replaced with a unique
			// reference code but controls has duplicate display codes and
			// is skipped by the reference generator.
			ApplicationControlModel m = (ApplicationControlModel) e1.nextElement();
			toolBar.addNewButton(m, "Toolbar - New", actions.getAction(ActionGroup.REQMGRFILENEW));
			toolBar.addNewButton(m, "Toolbar - Open", actions.getAction(ActionGroup.REQMGRFILEOPEN));
			toolBar.addNewButton(m, "Toolbar - Refresh", actions.getAction(ActionGroup.REQMGRFILEREFRESH));
			toolBar.addNewButton(m, "Toolbar - Print", actions.getAction(ActionGroup.REQMGRFILEPRINT));
			toolBar.addNewButton(m, "Toolbar - Delete", actions.getAction(ActionGroup.REQMGREDITDELETE));
		//	toolBar.addNewButton(m, "Toolbar - Cut", null);
			toolBar.addNewButton(m, "Toolbar - Move Up", actions.getAction(ActionGroup.REQMGRMOVEUP));
			toolBar.addNewButton(m, "Toolbar - Move Down", actions.getAction(ActionGroup.REQMGRMOVEDOWN));
			toolBar.addNewButton(m, "Toolbar - Traceability", actions.getAction(ActionGroup.VIEWTRACEABILITY));
			toolBar.addNewButton(m, "Toolbar - Change Control", actions.getAction(ActionGroup.VIEWCHANGECONTROL));
		}
		return toolBar;
	}
	
	public UIMenuBar getMenuBar(ApplicationActionList actions) {
		
		menuManager = new MenuManager(SecurityServices.getAppControlsByUser(ApplicationFramework.get(ApplicationGroup.OSRMTMAINMENU)));
		menuManager.addMenu("Menu File");
		/*
		 * Submenu
		menuManager.addSubMenu("Menu File - New");
		menuManager.addSubMenuItemControl("Menu File - New Product", actions.getAction(ActionGroup.REQMGRNEWPRODUCT), true, Keys.getCntrl_N());
		ApplicationSecurityList list = SecurityServices.getAppSecurity(ApplicationGroup.ARTIFACTFORM);
		Enumeration e1 = list.elements();
		while (e1.hasMoreElements()) {
			ApplicationSecurityModel asm = (ApplicationSecurityModel) e1.nextElement();
			ApplicationAction action = actions.getAction(ActionGroup.REQMGRFILENEW);
			action.setUserObject(asm);
			menuManager.addSubMenuItem(asm.getAppTypeRefDisplay(),action , false, null);
		}
		*/
		menuManager.addMenuItem("Menu File - New Product", actions.getAction(ActionGroup.REQMGRNEWPRODUCT), true, Keys.getCntrl_N());
		ApplicationSecurityList list = SecurityServices.getAppSecurity(ApplicationFramework.get(ApplicationGroup.ARTIFACTFORM));
		Enumeration e1 = list.elements();
		while (e1.hasMoreElements()) {
			ApplicationSecurityModel asm = (ApplicationSecurityModel) e1.nextElement();
			ApplicationViewModel avm = SecurityServices.getApplicationView(asm.getApplicationViewId());
			ApplicationAction action = actions.getAction(ActionGroup.REQMGRFILENEW);
			action.setUserObject(asm);
			menuManager.addMenuItem("Menu File - New", "New " + avm.getAppTypeRefDisplay(), action, false);
		}
		menuManager.addMenuItem("Menu File - Open", actions.getAction(ActionGroup.REQMGRFILEOPEN), false, Keys.getCntrl_O());
		menuManager.addMenuItem("Menu File - Close", actions.getAction(ActionGroup.REQMGRFILECLOSE), true);
		menuManager.addMenuItem("Menu File - Refresh", actions.getAction(ActionGroup.REQMGRFILEREFRESH), false, Keys.get_F5());
		menuManager.addMenuItem("Menu File - Change Password", actions.getAction(ActionGroup.REQMGRCHANGEPASSWORD), true);
		menuManager.addMenuItem("Menu File - Import", actions.getAction(ActionGroup.REQMGRFILEIMPORT), false);
		menuManager.addMenuItem("Menu File - Export", actions.getAction(ActionGroup.REQMGRFILEEXPORT), true);
		//menuManager.addMenuItem("Menu File - Print", actions.getAction(ActionGroup.REQMGRFILEPRINT), true, Keys.getCntrl_P());
		//TODO Application Settings get last 4 projects from application settings
		//menuManager.addMenuItem(ControlsGroup.MENUFILERECENT, actions.getAction(ActionGroup.REQMGRFILERECENT), true);
		menuManager.addMenuItem("Menu File - Exit", actions.getAction(ActionGroup.REQMGRFILEEXIT), false);

		menuManager.addMenu("Menu Edit");
		menuManager.addMenuItem("Menu Edit - Artifact", actions.getAction(ActionGroup.REQMGREDITARTIFACT), true, Keys.getShift_F2());
		menuManager.addMenuItem("Menu Edit - Move Up", actions.getAction(ActionGroup.REQMGRMOVEUP), false);
		menuManager.addMenuItem("Menu Edit - Move Down", actions.getAction(ActionGroup.REQMGRMOVEDOWN), true);
		//menuManager.addMenuItem("Menu Edit - Cut", actions.getAction(ActionGroup.REQMGREDITCUT), false);
		menuManager.addMenuItem("Menu Edit - Copy", actions.getAction(ActionGroup.REQMGREDITCOPY), false);
		menuManager.addMenuItem("Menu Edit - Paste", actions.getAction(ActionGroup.REQMGREDITPASTE), true);
		menuManager.addMenuItem("Menu Edit - Delete", actions.getAction(ActionGroup.REQMGREDITDELETE), true,Keys.get_Del());
		menuManager.addMenuItem("Menu Edit - Find", actions.getAction(ActionGroup.REQMGREDITFIND), false);
		menuManager.addMenuItem("Menu Edit - Replace", actions.getAction(ActionGroup.REQMGREDITREPLACE), false);
		//TODO error populating systemform since control display model different convention
		// need to use the reference tree
		menuManager.addMenu("Menu Tools");
		menuManager.addMenuItem("Menu Tools - Spelling", actions.getAction(ActionGroup.REQMGRTOOLSSPELLING), true);
		menuManager.addMenuItem("Menu Tools - Reports", actions.getAction(ActionGroup.REQMGRTOOLSREPORTS), false, Keys.getCntrl_R());
		menuManager.addMenuItem("Menu Tools - Impact", actions.getAction(ActionGroup.REQMGRTOOLSIMPACT), false);
		menuManager.addMenuItem("Menu Tools - Trace", actions.getAction(ActionGroup.REQMGRTOOLSTRACE), true);
		menuManager.addMenuItem("Menu Tools - Filter", actions.getAction(ActionGroup.REQMGRTOOLSFILTER), true);
		menuManager.addMenuItem("Menu Tools - Customize", actions.getAction(ActionGroup.REQMGRTOOLSCUSTOMIZE), false);
		menuManager.addMenuItem("Menu Tools - Options", actions.getAction(ActionGroup.REQMGRTOOLSOPTIONS), false);
		
		menuManager.addMenu("Menu Admin");
		menuManager.addMenuItem("Menu Admin - Users", actions.getAction(ActionGroup.REQMGRADMINUSERS), false);
		menuManager.addMenuItem("Menu Admin - Reference", actions.getAction(ActionGroup.REQMGRADMINREF), false);
		menuManager.addMenuItem("Menu Admin - Positions", actions.getAction(ActionGroup.REQMGRADMINPOSITIONS), false);
		menuManager.addMenuItem("Menu Admin - User Groups", actions.getAction(ActionGroup.REQMGRADMINUSERGROUPS), false);
		menuManager.addMenuItem("Menu Admin - Projects", actions.getAction(ActionGroup.REQMGRADMINPROJECTS), false);
		
		menuManager.addMenu("Menu System");
		menuManager.addMenuItem("Menu System - Forms", actions.getAction(ActionGroup.REQMGRSYSTEMFORMS), false);
		menuManager.addMenuItem("Menu System - Reference", actions.getAction(ActionGroup.REQMGRSYSTEMREF), true);
		menuManager.addMenuItem("Menu System - Export", actions.getAction(ActionGroup.REQMGRSYSTEMEXPORT), false);
		menuManager.addMenuItem("Menu System - Import", actions.getAction(ActionGroup.REQMGRSYSTEMIMPORT), true);
		menuManager.addMenuItem("Menu System - Debug", actions.getAction(ActionGroup.REQMGRSYSTEMDEBUG), true);
		menuManager.addMenuItem("Menu System - New Artifact", actions.getAction(ActionGroup.REQMGRSYSTEMNEWARTIFACT), false);
		menuManager.addMenuItem("Menu System - New Artifact Field", actions.getAction(ActionGroup.REQMGRSYSTEMNEWARTIFACTFIELD), false);
		menuManager.addMenuItem("Menu System - Baseline", actions.getAction(ActionGroup.REQMGRSYSTEMBASELINE), true);
		menuManager.addMenuItem("Menu System - Options", actions.getAction(ActionGroup.REQMGRSYSTEMOPTIONS), false);
		
		menuManager.addMenu("Menu View");
		menuManager.addMenuItem("Menu View - Change Control", actions.getAction(ActionGroup.VIEWCHANGECONTROL), true);
		menuManager.addMenuItem("Menu View - Traceability", actions.getAction(ActionGroup.VIEWTRACEABILITY), false);
		
		menuManager.addMenu("Menu Help");
		menuManager.addMenuItem("Menu Help - OSRMT Help", actions.getAction(ActionGroup.REQMGRHELPOSRMT), true);
		menuManager.addMenuItem("Menu Help - System Log", actions.getAction(ActionGroup.REQMGRSYSTEMLOG), false);
		menuManager.addMenuItem("Menu Help - Check for updates", actions.getAction(ActionGroup.REQMGRHELPUPDATES), true);
		menuManager.addMenuItem("Menu Help - About", actions.getAction(ActionGroup.REQMGRHELPABOUT), false);
		
		return menuManager.getMenuBar();
		
	}

	public static MenuManager getMenuManager() {
		return menuManager;
	}

	
	
}
