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

import com.osframework.framework.utility.RuleScript;
import com.osframework.modellibrary.reference.security.ApplicationControlList;
import com.osframework.modellibrary.reference.security.ApplicationControlModel;


/**
null
*/
public class RequirementManagerBaseController {
	
	protected RuleScript script = null;
	protected RequirementManagerUI ui = new RequirementManagerUI();

	private int viewRefId = 0;	
	public void initialize (ApplicationControlList controls, int viewRefId) {
		script = new RuleScript(null,null);
		this.viewRefId = viewRefId;
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
