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
public class UserFormBaseController {
	
	private ApplicationUserModel applicationUser;
	public ApplicationUserModel getApplicationUser() {
		return applicationUser;
	}

	protected RuleScript script = null;
	protected UserFormUI ui;

	public UserFormBaseController(JFrame frame) {
		 ui = new UserFormUI(frame);
	}

	
	public void initialize (ApplicationControlList controls, ApplicationUserModel m) {
		this.applicationUser = m;
		script = new RuleScript(null,null);
		initializeUI(controls);
	}
	
	private void initializeUI(ApplicationControlList controls) {
		java.util.Enumeration e1 = controls.elements();
		while (e1.hasMoreElements()) {
			ApplicationControlModel rm = (ApplicationControlModel) e1.nextElement();
			if (rm.getControlRefDisplay().compareTo("LastName")==0) {
				UITextField field = getLastName();
				GUI.setGUIMessage(field, rm);
				if (rm.isDisabled()) {
 					field.setEnabled(false);
				} else {
					script.executeScript(rm.getInitScript(), field);
					script.bind(field, rm);
 				}
				ui.setLastName(field);
			}
			if (rm.getControlRefDisplay().compareTo("FirstName")==0) {
				UITextField field = getFirstName();
				GUI.setGUIMessage(field, rm);
				if (rm.isDisabled()) {
 					field.setEnabled(false);
				} else {
					script.executeScript(rm.getInitScript(), field);
					script.bind(field, rm);
 				}
				ui.setFirstName(field);
			}
			if (rm.getControlRefDisplay().compareTo("PositionRefId")==0) {
				UIComboBox field = getPositionRefId();
				GUI.setGUIMessage(field, rm);
				if (rm.isDisabled()) {
 					field.setEnabled(false);
				} else {
					script.executeScript(rm.getInitScript(), field);
					script.bind(field, rm);
 				}
				ui.setPositionRefId(field);
			}
			if (rm.getControlRefDisplay().compareTo("UserLogin")==0) {
				UITextField field = getUserLogin();
				GUI.setGUIMessage(field, rm);
				if (rm.isDisabled()) {
 					field.setEnabled(false);
				} else {
					script.executeScript(rm.getInitScript(), field);
					script.bind(field, rm);
 				}
				ui.setUserLogin(field);
			}
			if (rm.getControlRefDisplay().compareTo("Password")==0) {
				UIPasswordField field = getPassword();
				GUI.setGUIMessage(field, rm);
				if (rm.isDisabled()) {
 					field.setEnabled(false);
				} else {
					script.executeScript(rm.getInitScript(), field);
					script.bind(field, rm);
 				}
				ui.setPassword(field);
			}
			if (rm.getControlRefDisplay().compareTo("ActiveInd")==0) {
				UIIndicatorField field = getActiveInd();
				GUI.setGUIMessage(field, rm);
				if (rm.isDisabled()) {
 					field.setEnabled(false);
				} else {
					script.executeScript(rm.getInitScript(), field);
					script.bind(field, rm);
 				}
				ui.setActiveInd(field);
			}
			if (rm.getControlRefDisplay().compareTo("UserId")==0) {
				UIIntegerField field = getUserId();
				GUI.setGUIMessage(field, rm);
				if (rm.isDisabled()) {
 					field.setEnabled(false);
				} else {
					script.executeScript(rm.getInitScript(), field);
					script.bind(field, rm);
 				}
				ui.setUserId(field);
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
				UIUserField field = getCreateUserId();
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
				UIUserField field = getUpdateUserId();
				GUI.setGUIMessage(field, rm);
				if (rm.isDisabled()) {
 					field.setEnabled(false);
				} else {
					script.executeScript(rm.getInitScript(), field);
					script.bind(field, rm);
 				}
				ui.setUpdateUserId(field);
			}
			if (rm.getControlRefDisplay().compareTo("ChangePassword")==0) {
				ChangePasswordButton field = getChangePassword();
				GUI.setGUIMessage(field, rm);
				if (rm.isDisabled()) {
 					field.setEnabled(false);
				} else {
					script.executeScript(rm.getInitScript(), field);
					script.bind(field, rm);
 				}
				ui.setChangePassword(field);
			}
			if (rm.getControlRefDisplay().compareTo("AssignForms")==0) {
				AssignFormsButton field = getAssignForms();
				GUI.setGUIMessage(field, rm);
				if (rm.isDisabled()) {
 					field.setEnabled(false);
				} else {
					script.executeScript(rm.getInitScript(), field);
					script.bind(field, rm);
 				}
				ui.setAssignForms(field);
			}
		}
		ui.addControls(controls);
		ui.setLocationRelativeTo(null);
	}
	
	
	private UITextField getLastName() {
		try {
			UITextField control = new UITextField(10);
			control.setText(applicationUser.getLastName());
			control.getDocument().addDocumentListener(new TextDocListener() {
				public void call(String p) {
					applicationUser.setLastName(p);
				}				
			});
			return control;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			return new UITextField();
		}
	}

	private UITextField getFirstName() {
		try {
			UITextField control = new UITextField(10);
			control.setText(applicationUser.getFirstName());
			control.getDocument().addDocumentListener(new TextDocListener() {
				public void call(String p) {
					applicationUser.setFirstName(p);
				}				
			});
			return control;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			return new UITextField();
		}
	}

	private UIComboBox getPositionRefId() {
		try {
			ReferenceDisplayList list = ReferenceServices.getDisplayList(ReferenceGroup.Position, false);
			UIComboBox control = new UIComboBox(list);
			list.setSelectedRefId(applicationUser.getPositionRefId());
			ReferenceListListener listener = (new ReferenceListListener() {
				public void call(int p) {
					try {
						applicationUser.setPositionRefId(p);
					} catch (Exception ex) {
						Debug.LogException(this, ex);
					}
				}
			});
			new UIComboDocument(control, listener);
			return control;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			return new UIComboBox();
		}
	}


	private UITextField getUserLogin() {
		try {
			UITextField control = new UITextField(10);
			control.setText(applicationUser.getUserLogin());
			control.getDocument().addDocumentListener(new TextDocListener() {
				public void call(String p) {
					applicationUser.setUserLogin(p);
				}				
			});
			return control;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			return new UITextField();
		}
	}

	private UIPasswordField getPassword() {
		try {
			UIPasswordField control = new UIPasswordField();
			control.setText(applicationUser.getPassword());
			control.getDocument().addDocumentListener(new TextDocListener() {
				public void call(String p) {
					applicationUser.setPassword(p);
				}				
			});
			return control;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			return new UIPasswordField();
		}
	}

	private UIIndicatorField getActiveInd() {
		try {
			final UIIndicatorField control = new UIIndicatorField();
			control.setSelected(applicationUser.getActiveInd()==1);
			control.addChangeListener(new IndicatorListener() {
				public void call(ChangeEvent c) {
					int p = 0;
					if (control.isSelected()) {
						p = 1;
					}
					applicationUser.setActiveInd(p);
				}				
			});
			return control;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			return new UIIndicatorField();
		}
	}

	private UIIntegerField getUserId() {
		try {
			UIIntegerField control = new UIIntegerField();
			control.setText(applicationUser.getUserId());
			control.getDocument().addDocumentListener(new NumberDocListener() {
				public void call(int p) {
					//
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
			control.setText(CalendarUtility.Format(applicationUser.getCreateDt(), CalendarUtility.ShortDateFormat()));
			control.getDocument().addDocumentListener(new TextDocListener() {
				public void call(String p) {
					try { 
						if (CalendarUtility.shouldParseDate(p, CalendarUtility.ShortDateFormat())) {
						applicationUser.setCreateDt(CalendarUtility.Parse(p, CalendarUtility.ShortDateFormat()));
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

	private UIUserField getCreateUserId() {
		try {
			UIUserField control = new UIUserField();
			control.setText(applicationUser.getCreateUserName());
			return control;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			return new UIUserField();
		}
	}

	protected UIDateField getUpdateDt() {
		try {
			DateFormat formatDate = new SimpleDateFormat(CalendarUtility.ShortDateFormat()); 
			UIDateField control = new UIDateField(formatDate);
			control.setText(CalendarUtility.Format(applicationUser.getUpdateDt(), CalendarUtility.ShortDateFormat()));
			control.getDocument().addDocumentListener(new TextDocListener() {
				public void call(String p) {
					try { 
						if (CalendarUtility.shouldParseDate(p, CalendarUtility.ShortDateFormat())) {
						applicationUser.setUpdateDt(CalendarUtility.Parse(p, CalendarUtility.ShortDateFormat()));
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

	private UIUserField getUpdateUserId() {
		try {
			UIUserField control = new UIUserField();
			control.setText(applicationUser.getUpdateUserName());
			return control;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			return new UIUserField();
		}
	}

	private ChangePasswordButton getChangePassword() {
		try {
			ChangePasswordButton control = new ChangePasswordButton();
			
			return control;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			return new ChangePasswordButton();
		}
	}

	private AssignFormsButton getAssignForms() {
		try {
			AssignFormsButton control = new AssignFormsButton();
			
			return control;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			return new AssignFormsButton();
		}
	}

	
}
