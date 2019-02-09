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
package com.osrmt.appclient.system;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;
import javax.swing.*;
import javax.swing.event.*;

import com.osframework.modellibrary.reference.security.*;
import com.osrmt.appclient.common.*;
import com.osframework.appclient.ui.common.*;
import com.osframework.appclient.ui.listeners.*;
import com.osframework.modellibrary.common.*;
import com.osframework.modellibrary.reference.common.*;
import com.osrmt.modellibrary.reference.group.*;
import com.osframework.modellibrary.reference.security.*;
import com.osframework.modellibrary.reference.group.ApplicationFramework;
import com.osframework.modellibrary.reference.group.RecordTypeFramework;
import com.osframework.framework.logging.*;
import com.osframework.framework.utility.*;
import com.osrmt.appclient.services.*;
import com.osframework.appclient.ui.components.*;
import com.osframework.appclient.ui.controls.*;
import com.osframework.appclient.services.*;


/**
null
*/
public class ApplicationSecurityUserFormBaseController {
	
	private ApplicationSecurityModel applicationSecurity;
	public ApplicationSecurityModel getApplicationSecurity() {
		return applicationSecurity;
	}
	private ApplicationViewModel avm;

	protected RuleScript script = null;
	protected ApplicationSecurityUserFormUI ui;

	public ApplicationSecurityUserFormBaseController(JFrame frame) {
		 ui = new ApplicationSecurityUserFormUI(frame);
	}

	
	public void initialize (ApplicationControlList controls, ApplicationSecurityModel m) {
		this.applicationSecurity = m;
		avm = SecurityServices.getApplicationView(m.getApplicationViewId());
		script = new RuleScript(null,null);
		initializeUI(controls);
	}
	
	private void initializeUI(ApplicationControlList controls) {
		java.util.Enumeration e1 = controls.elements();
		while (e1.hasMoreElements()) {
			ApplicationControlModel rm = (ApplicationControlModel) e1.nextElement();
			if (rm.getControlRefDisplay().compareTo("ApplicationSecurityId")==0) {
				UIIntegerField field = getApplicationSecurityId();
				GUI.setGUIMessage(field, rm);
				if (rm.isDisabled()) {
 					field.setEnabled(false);
				} else {
					script.executeScript(rm.getInitScript(), field);
					script.bind(field, rm);
 				}
				ui.setApplicationSecurityId(field);
			}
			if (rm.getControlRefDisplay().compareTo("TableKeyId")==0) {
				UIIntegerField field = getTableKeyId();
				GUI.setGUIMessage(field, rm);
				if (rm.isDisabled()) {
 					field.setEnabled(false);
				} else {
					script.executeScript(rm.getInitScript(), field);
					script.bind(field, rm);
 				}
				ui.setTableKeyId(field);
			}
			if (rm.getControlRefDisplay().compareTo("TableRefId")==0) {
				UIIntegerField field = getTableRefId();
				GUI.setGUIMessage(field, rm);
				if (rm.isDisabled()) {
 					field.setEnabled(false);
				} else {
					script.executeScript(rm.getInitScript(), field);
					script.bind(field, rm);
 				}
				ui.setTableRefId(field);
			}
			if (rm.getControlRefDisplay().compareTo("ViewRefId")==0) {
				UIIntegerField field = getViewRefId();
				GUI.setGUIMessage(field, rm);
				if (rm.isDisabled()) {
 					field.setEnabled(false);
				} else {
					script.executeScript(rm.getInitScript(), field);
					script.bind(field, rm);
 				}
				ui.setViewRefId(field);
			}
			if (rm.getControlRefDisplay().compareTo("AppTypeRefId")==0) {
				UIIntegerField field = getAppTypeRefId();
				GUI.setGUIMessage(field, rm);
				if (rm.isDisabled()) {
 					field.setEnabled(false);
				} else {
					script.executeScript(rm.getInitScript(), field);
					script.bind(field, rm);
 				}
				ui.setAppTypeRefId(field);
			}
			if (rm.getControlRefDisplay().compareTo("ApplicationRefId")==0) {
				UIIntegerField field = getApplicationRefId();
				GUI.setGUIMessage(field, rm);
				if (rm.isDisabled()) {
 					field.setEnabled(false);
				} else {
					script.executeScript(rm.getInitScript(), field);
					script.bind(field, rm);
 				}
				ui.setApplicationRefId(field);
			}
			if (rm.getControlRefDisplay().compareTo("ReadOnlyInd")==0) {
				UIIntegerField field = getReadOnlyInd();
				GUI.setGUIMessage(field, rm);
				if (rm.isDisabled()) {
 					field.setEnabled(false);
				} else {
					script.executeScript(rm.getInitScript(), field);
					script.bind(field, rm);
 				}
				ui.setReadOnlyInd(field);
			}
			if (rm.getControlRefDisplay().compareTo("CreateDt")==0) {
				UIDateField field = getCreateDt();
				GUI.setGUIMessage(field, rm);
				if (rm.isDisabled()) {
 					field.setEnabled(false);
				} else {
					script.executeScript(rm.getInitScript(), field);
					script.bind(field, rm);
 				}
				ui.setCreateDt(field);
			}
			if (rm.getControlRefDisplay().compareTo("CreateUserId")==0) {
				UIIntegerField field = getCreateUserId();
				GUI.setGUIMessage(field, rm);
				if (rm.isDisabled()) {
 					field.setEnabled(false);
				} else {
					script.executeScript(rm.getInitScript(), field);
					script.bind(field, rm);
 				}
				ui.setCreateUserId(field);
			}
			if (rm.getControlRefDisplay().compareTo("UpdateDt")==0) {
				UIDateField field = getUpdateDt();
				GUI.setGUIMessage(field, rm);
				if (rm.isDisabled()) {
 					field.setEnabled(false);
				} else {
					script.executeScript(rm.getInitScript(), field);
					script.bind(field, rm);
 				}
				ui.setUpdateDt(field);
			}
			if (rm.getControlRefDisplay().compareTo("UpdateUserId")==0) {
				UIIntegerField field = getUpdateUserId();
				GUI.setGUIMessage(field, rm);
				if (rm.isDisabled()) {
 					field.setEnabled(false);
				} else {
					script.executeScript(rm.getInitScript(), field);
					script.bind(field, rm);
 				}
				ui.setUpdateUserId(field);
			}
			if (rm.getControlRefDisplay().compareTo("UpdateCount")==0) {
				UIIntegerField field = getUpdateCount();
				GUI.setGUIMessage(field, rm);
				if (rm.isDisabled()) {
 					field.setEnabled(false);
				} else {
					script.executeScript(rm.getInitScript(), field);
					script.bind(field, rm);
 				}
				ui.setUpdateCount(field);
			}
			if (rm.getControlRefDisplay().compareTo("ActiveInd")==0) {
				UIIntegerField field = getActiveInd();
				GUI.setGUIMessage(field, rm);
				if (rm.isDisabled()) {
 					field.setEnabled(false);
				} else {
					script.executeScript(rm.getInitScript(), field);
					script.bind(field, rm);
 				}
				ui.setActiveInd(field);
			}
			if (rm.getControlRefDisplay().compareTo("SystemAssignedVersionNbr")==0) {
				UIIntegerField field = getSystemAssignedVersionNbr();
				GUI.setGUIMessage(field, rm);
				if (rm.isDisabled()) {
 					field.setEnabled(false);
				} else {
					script.executeScript(rm.getInitScript(), field);
					script.bind(field, rm);
 				}
				ui.setSystemAssignedVersionNbr(field);
			}
			if (rm.getControlRefDisplay().compareTo("RecordTypeRefId")==0) {
				UIIntegerField field = getRecordTypeRefId();
				GUI.setGUIMessage(field, rm);
				if (rm.isDisabled()) {
 					field.setEnabled(false);
				} else {
					script.executeScript(rm.getInitScript(), field);
					script.bind(field, rm);
 				}
				ui.setRecordTypeRefId(field);
			}
		}
		ui.addControls(controls);
		ui.setLocationRelativeTo(null);
	}
	
	
	private UIIntegerField getApplicationSecurityId() {
		try {
			UIIntegerField control = new UIIntegerField();
			control.setText(applicationSecurity.getApplicationSecurityId());
			control.getDocument().addDocumentListener(new NumberDocListener() {
				public void call(int p) {
					applicationSecurity.setApplicationSecurityId(p);
				}				
			});
			return control;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			return new UIIntegerField();
		}
	}

	private UIIntegerField getTableKeyId() {
		try {
			UIIntegerField control = new UIIntegerField();
			control.setText(applicationSecurity.getTableKeyId());
			control.getDocument().addDocumentListener(new NumberDocListener() {
				public void call(int p) {
					applicationSecurity.setTableKeyId(p);
				}				
			});
			return control;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			return new UIIntegerField();
		}
	}

	private UIIntegerField getTableRefId() {
		try {
			UIIntegerField control = new UIIntegerField();
			control.setText(applicationSecurity.getTableRefId());
			control.getDocument().addDocumentListener(new NumberDocListener() {
				public void call(int p) {
					applicationSecurity.setTableRefId(p);
				}				
			});
			return control;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			return new UIIntegerField();
		}
	}

	private UIIntegerField getViewRefId() {
		try {
			UIIntegerField control = new UIIntegerField();
			control.setText(avm.getViewRefId());
			control.getDocument().addDocumentListener(new NumberDocListener() {
				public void call(int p) {
					avm.setViewRefId(p);
				}				
			});
			return control;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			return new UIIntegerField();
		}
	}

	private UIIntegerField getAppTypeRefId() {
		try {
			UIIntegerField control = new UIIntegerField();
			control.setText(avm.getAppTypeRefId());
			control.getDocument().addDocumentListener(new NumberDocListener() {
				public void call(int p) {
					avm.setAppTypeRefId(p);
				}				
			});
			return control;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			return new UIIntegerField();
		}
	}

	private UIIntegerField getApplicationRefId() {
		try {
			UIIntegerField control = new UIIntegerField();
			control.setText(avm.getApplicationRefId());
			control.getDocument().addDocumentListener(new NumberDocListener() {
				public void call(int p) {
					avm.setApplicationRefId(p);
				}				
			});
			return control;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			return new UIIntegerField();
		}
	}

	private UIIntegerField getReadOnlyInd() {
		try {
			UIIntegerField control = new UIIntegerField();
			control.setText(applicationSecurity.getReadOnlyInd());
			control.getDocument().addDocumentListener(new NumberDocListener() {
				public void call(int p) {
					applicationSecurity.setReadOnlyInd(p);
				}				
			});
			return control;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			return new UIIntegerField();
		}
	}

	protected UIDateField getCreateDt() {
		try {
			DateFormat formatDate = new SimpleDateFormat(CalendarUtility.ShortDateFormat()); 
			UIDateField control = new UIDateField(formatDate);
			control.setText(CalendarUtility.Format(applicationSecurity.getCreateDt(), CalendarUtility.ShortDateFormat()));
			control.getDocument().addDocumentListener(new TextDocListener() {
				public void call(String p) {
					try { 
						if (CalendarUtility.shouldParseDate(p, CalendarUtility.ShortDateFormat())) {
						applicationSecurity.setCreateDt(CalendarUtility.Parse(p, CalendarUtility.ShortDateFormat()));
						}
					} catch (Exception ex) { 
						Debug.LogException(this, ex);
					}
				}				
			});
			return control;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			return new UIDateField();
		}
	}

	private UIIntegerField getCreateUserId() {
		try {
			UIIntegerField control = new UIIntegerField();
			control.setText(applicationSecurity.getCreateUserId());
			control.getDocument().addDocumentListener(new NumberDocListener() {
				public void call(int p) {
					applicationSecurity.setCreateUserId(p);
				}				
			});
			return control;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			return new UIIntegerField();
		}
	}

	protected UIDateField getUpdateDt() {
		try {
			DateFormat formatDate = new SimpleDateFormat(CalendarUtility.ShortDateFormat()); 
			UIDateField control = new UIDateField(formatDate);
			control.setText(CalendarUtility.Format(applicationSecurity.getUpdateDt(), CalendarUtility.ShortDateFormat()));
			control.getDocument().addDocumentListener(new TextDocListener() {
				public void call(String p) {
					try { 
						if (CalendarUtility.shouldParseDate(p, CalendarUtility.ShortDateFormat())) {
						applicationSecurity.setUpdateDt(CalendarUtility.Parse(p, CalendarUtility.ShortDateFormat()));
						}
					} catch (Exception ex) { 
						Debug.LogException(this, ex);
					}
				}				
			});
			return control;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			return new UIDateField();
		}
	}

	private UIIntegerField getUpdateUserId() {
		try {
			UIIntegerField control = new UIIntegerField();
			control.setText(applicationSecurity.getUpdateUserId());
			control.getDocument().addDocumentListener(new NumberDocListener() {
				public void call(int p) {
					applicationSecurity.setUpdateUserId(p);
				}				
			});
			return control;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			return new UIIntegerField();
		}
	}

	private UIIntegerField getUpdateCount() {
		try {
			UIIntegerField control = new UIIntegerField();
			control.setText(applicationSecurity.getUpdateCount());
			control.getDocument().addDocumentListener(new NumberDocListener() {
				public void call(int p) {
					applicationSecurity.setUpdateCount(p);
				}				
			});
			return control;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			return new UIIntegerField();
		}
	}

	private UIIntegerField getActiveInd() {
		try {
			UIIntegerField control = new UIIntegerField();
			control.setText(applicationSecurity.getActiveInd());
			control.getDocument().addDocumentListener(new NumberDocListener() {
				public void call(int p) {
					applicationSecurity.setActiveInd(p);
				}				
			});
			return control;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			return new UIIntegerField();
		}
	}

	private UIIntegerField getSystemAssignedVersionNbr() {
		try {
			UIIntegerField control = new UIIntegerField();
			control.setText(applicationSecurity.getSystemAssignedVersionNbr());
			control.getDocument().addDocumentListener(new NumberDocListener() {
				public void call(int p) {
					applicationSecurity.setSystemAssignedVersionNbr(p);
				}				
			});
			return control;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			return new UIIntegerField();
		}
	}

	private UIIntegerField getRecordTypeRefId() {
		try {
			UIIntegerField control = new UIIntegerField();
			control.setText(applicationSecurity.getRecordTypeRefId());
			control.getDocument().addDocumentListener(new NumberDocListener() {
				public void call(int p) {
					applicationSecurity.setRecordTypeRefId(p);
				}				
			});
			return control;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			return new UIIntegerField();
		}
	}

	
}
