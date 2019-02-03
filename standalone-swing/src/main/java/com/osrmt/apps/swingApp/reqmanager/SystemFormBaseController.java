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
package com.osrmt.apps.swingApp.reqmanager;

import javax.swing.JFrame;

import com.osframework.framework.utility.RuleScript;
import com.osframework.modellibrary.reference.security.ApplicationControlList;
import com.osframework.modellibrary.reference.security.ApplicationControlModel;


/**
null
*/
public class SystemFormBaseController {
	
	protected RuleScript script = null;
	protected SystemFormUI ui;

	public SystemFormBaseController(JFrame frame) {
		 ui = new SystemFormUI(frame);
	}

	
	public void initialize (ApplicationControlList controls) {
		script = new RuleScript(null,null);
		initializeUI(controls);
	}
	
	private void initializeUI(ApplicationControlList controls) {
		java.util.Enumeration e1 = controls.elements();
		while (e1.hasMoreElements()) {
			ApplicationControlModel rm = (ApplicationControlModel) e1.nextElement();
		}
		ui.addControls(controls);
		ui.setLocationRelativeTo(null);
	}
	
	
	
}
