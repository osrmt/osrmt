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
public class ApplicationControlFormBaseController {
	
	private ApplicationControlModel applicationControl;
	public ApplicationControlModel getApplicationControl() {
		return applicationControl;
	}

	protected RuleScript script = null;
	protected ApplicationControlFormUI ui;

	public ApplicationControlFormBaseController(JFrame frame) {
		 ui = new ApplicationControlFormUI(frame);
	}

	
	public void initialize (ApplicationControlList controls, ApplicationControlModel m) {
		this.applicationControl = m;
		script = new RuleScript(null,null);
		initializeUI(controls);
	}
	
	private void initializeUI(ApplicationControlList controls) {
		java.util.Enumeration e1 = controls.elements();
		while (e1.hasMoreElements()) {
			ApplicationControlModel rm = (ApplicationControlModel) e1.nextElement();
			if (rm.getControlRefDisplay().compareTo("DisplaySequence")==0) {
				UIIntegerField field = getDisplaySequence();
				GUI.setGUIMessage(field, rm);
				if (rm.isDisabled()) {
 					field.setEnabled(false);
				} else {
					script.executeScript(rm.getInitScript(), field);
					script.bind(field, rm);
 				}
				ui.setDisplaySequence(field);
			}
			if (rm.getControlRefDisplay().compareTo("ControlText")==0) {
				UITextField field = getControlText();
				GUI.setGUIMessage(field, rm);
				if (rm.isDisabled()) {
 					field.setEnabled(false);
				} else {
					script.executeScript(rm.getInitScript(), field);
					script.bind(field, rm);
 				}
				ui.setControlText(field);
			}
			if (rm.getControlRefDisplay().compareTo("AppControlUserDefinedId")==0) {
				UIIntegerField field = getAppControlUserDefinedId();
				GUI.setGUIMessage(field, rm);
				if (rm.isDisabled()) {
 					field.setEnabled(false);
				} else {
					script.executeScript(rm.getInitScript(), field);
					script.bind(field, rm);
 				}
				ui.setAppControlUserDefinedId(field);
			}
			if (rm.getControlRefDisplay().compareTo("ControlFormat")==0) {
				UITextField field = getControlFormat();
				GUI.setGUIMessage(field, rm);
				if (rm.isDisabled()) {
 					field.setEnabled(false);
				} else {
					script.executeScript(rm.getInitScript(), field);
					script.bind(field, rm);
 				}
				ui.setControlFormat(field);
			}
			if (rm.getControlRefDisplay().compareTo("SourceRefId")==0) {
				UIComboBox field = getSourceRefId();
				GUI.setGUIMessage(field, rm);
				if (rm.isDisabled()) {
 					field.setEnabled(false);
				} else {
					script.executeScript(rm.getInitScript(), field);
					script.bind(field, rm);
 				}
				ui.setSourceRefId(field);
			}
			if (rm.getControlRefDisplay().compareTo("DefaultValue")==0) {
				UITextField field = getDefaultValue();
				GUI.setGUIMessage(field, rm);
				if (rm.isDisabled()) {
 					field.setEnabled(false);
				} else {
					script.executeScript(rm.getInitScript(), field);
					script.bind(field, rm);
 				}
				ui.setDefaultValue(field);
			}
			if (rm.getControlRefDisplay().compareTo("LockedInd")==0) {
				UIIndicatorField field = getLockedInd();
				GUI.setGUIMessage(field, rm);
				if (rm.isDisabled()) {
 					field.setEnabled(false);
				} else {
					script.executeScript(rm.getInitScript(), field);
					script.bind(field, rm);
 				}
				ui.setLockedInd(field);
			}
			if (rm.getControlRefDisplay().compareTo("DisabledInd")==0) {
				UIIndicatorField field = getDisabledInd();
				GUI.setGUIMessage(field, rm);
				if (rm.isDisabled()) {
 					field.setEnabled(false);
				} else {
					script.executeScript(rm.getInitScript(), field);
					script.bind(field, rm);
 				}
				ui.setDisabledInd(field);
			}
			if (rm.getControlRefDisplay().compareTo("RequiredInd")==0) {
				UIIndicatorField field = getRequiredInd();
				GUI.setGUIMessage(field, rm);
				if (rm.isDisabled()) {
 					field.setEnabled(false);
				} else {
					script.executeScript(rm.getInitScript(), field);
					script.bind(field, rm);
 				}
				ui.setRequiredInd(field);
			}
			if (rm.getControlRefDisplay().compareTo("VisibleInd")==0) {
				UIIndicatorField field = getVisibleInd();
				GUI.setGUIMessage(field, rm);
				if (rm.isDisabled()) {
 					field.setEnabled(false);
				} else {
					script.executeScript(rm.getInitScript(), field);
					script.bind(field, rm);
 				}
				ui.setVisibleInd(field);
			}
			if (rm.getControlRefDisplay().compareTo("InitScript")==0) {
				UIEditorPane field = getInitScript();
				GUI.setGUIMessage(field, rm);
				if (rm.isDisabled()) {
 					field.setEnabled(false);
				} else {
					script.executeScript(rm.getInitScript(), field);
					script.bind(field, rm);
 				}
				ui.setInitScript(field);
			}
			if (rm.getControlRefDisplay().compareTo("FocusLostScript")==0) {
				UIEditorPane field = getFocusLostScript();
				GUI.setGUIMessage(field, rm);
				if (rm.isDisabled()) {
 					field.setEnabled(false);
				} else {
					script.executeScript(rm.getInitScript(), field);
					script.bind(field, rm);
 				}
				ui.setFocusLostScript(field);
			}
			if (rm.getControlRefDisplay().compareTo("FocusGainedScript")==0) {
				UIEditorPane field = getFocusGainedScript();
				GUI.setGUIMessage(field, rm);
				if (rm.isDisabled()) {
 					field.setEnabled(false);
				} else {
					script.executeScript(rm.getInitScript(), field);
					script.bind(field, rm);
 				}
				ui.setFocusGainedScript(field);
			}
			if (rm.getControlRefDisplay().compareTo("ImagePath")==0) {
				UITextField field = getImagePath();
				GUI.setGUIMessage(field, rm);
				if (rm.isDisabled()) {
 					field.setEnabled(false);
				} else {
					script.executeScript(rm.getInitScript(), field);
					script.bind(field, rm);
 				}
				ui.setImagePath(field);
			}
			if (rm.getControlRefDisplay().compareTo("GrowHeight")==0) {
				UIDoubleField field = getGrowHeight();
				GUI.setGUIMessage(field, rm);
				if (rm.isDisabled()) {
 					field.setEnabled(false);
				} else {
					script.executeScript(rm.getInitScript(), field);
					script.bind(field, rm);
 				}
				ui.setGrowHeight(field);
			}
			if (rm.getControlRefDisplay().compareTo("ScrollpaneInd")==0) {
				UIIndicatorField field = getScrollpaneInd();
				GUI.setGUIMessage(field, rm);
				if (rm.isDisabled()) {
 					field.setEnabled(false);
				} else {
					script.executeScript(rm.getInitScript(), field);
					script.bind(field, rm);
 				}
				ui.setScrollpaneInd(field);
			}
			if (rm.getControlRefDisplay().compareTo("UnitWidth")==0) {
				UIIntegerField field = getUnitWidth();
				GUI.setGUIMessage(field, rm);
				if (rm.isDisabled()) {
 					field.setEnabled(false);
				} else {
					script.executeScript(rm.getInitScript(), field);
					script.bind(field, rm);
 				}
				ui.setUnitWidth(field);
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
				UIComboBox field = getRecordTypeRefId();
				GUI.setGUIMessage(field, rm);
				if (rm.isDisabled()) {
 					field.setEnabled(false);
				} else {
					script.executeScript(rm.getInitScript(), field);
					script.bind(field, rm);
 				}
				ui.setRecordTypeRefId(field);
			}
			if (rm.getControlRefDisplay().compareTo("ControlDescription")==0) {
				UITextField field = getControlDescription();
				GUI.setGUIMessage(field, rm);
				if (rm.isDisabled()) {
 					field.setEnabled(false);
				} else {
					script.executeScript(rm.getInitScript(), field);
					script.bind(field, rm);
 				}
				ui.setControlDescription(field);
			}
			if (rm.getControlRefDisplay().compareTo("ModelColumnRefId")==0) {
				UIComboBox field = getModelColumnRefId();
				GUI.setGUIMessage(field, rm);
				if (rm.isDisabled()) {
 					field.setEnabled(false);
				} else {
					script.executeScript(rm.getInitScript(), field);
					script.bind(field, rm);
 				}
				ui.setModelColumnRefId(field);
			}
			if (rm.getControlRefDisplay().compareTo("ApplicationCustomControlId")==0) {
				ApplicationCustomControl field = getApplicationCustomControlId();
				GUI.setGUIMessage(field, rm);
				if (rm.isDisabled()) {
 					field.setEnabled(false);
				} else {
					script.executeScript(rm.getInitScript(), field);
					script.bind(field, rm);
 				}
				ui.setApplicationCustomControlId(field);
			}
			if (rm.getControlRefDisplay().compareTo("ControlTypeRefId")==0) {
				UIComboBox field = getControlTypeRefId();
				GUI.setGUIMessage(field, rm);
				if (rm.isDisabled()) {
 					field.setEnabled(false);
				} else {
					script.executeScript(rm.getInitScript(), field);
					script.bind(field, rm);
 				}
				ui.setControlTypeRefId(field);
			}
			if (rm.getControlRefDisplay().compareTo("UnitHeight")==0) {
				UIIntegerField field = getUnitHeight();
				GUI.setGUIMessage(field, rm);
				if (rm.isDisabled()) {
 					field.setEnabled(false);
				} else {
					script.executeScript(rm.getInitScript(), field);
					script.bind(field, rm);
 				}
				ui.setUnitHeight(field);
			}
			if (rm.getControlRefDisplay().compareTo("GrowWidth")==0) {
				UIIntegerField field = getGrowWidth();
				GUI.setGUIMessage(field, rm);
				if (rm.isDisabled()) {
 					field.setEnabled(false);
				} else {
					script.executeScript(rm.getInitScript(), field);
					script.bind(field, rm);
 				}
				ui.setGrowWidth(field);
			}
		}
		ui.addControls(controls);
		ui.setLocationRelativeTo(null);
	}
	
	
	private UIIntegerField getDisplaySequence() {
		try {
			UIIntegerField control = new UIIntegerField();
			control.setText(applicationControl.getDisplaySequence());
			control.getDocument().addDocumentListener(new NumberDocListener() {
				public void call(int p) {
					applicationControl.setDisplaySequence(p);
				}				
			});
			return control;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			return new UIIntegerField();
		}
	}

	private UITextField getControlText() {
		try {
			UITextField control = new UITextField(10);
			control.setText(applicationControl.getControlText());
			control.getDocument().addDocumentListener(new TextDocListener() {
				public void call(String p) {
					applicationControl.setControlText(p);
				}				
			});
			return control;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			return new UITextField();
		}
	}

	private UIIntegerField getAppControlUserDefinedId() {
		try {
			UIIntegerField control = new UIIntegerField();
			control.setText(applicationControl.getAppControlUserDefinedId());
			control.getDocument().addDocumentListener(new NumberDocListener() {
				public void call(int p) {
					applicationControl.setAppControlUserDefinedId(p);
				}				
			});
			return control;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			return new UIIntegerField();
		}
	}

	private UITextField getControlFormat() {
		try {
			UITextField control = new UITextField(10);
			control.setText(applicationControl.getControlFormat());
			control.getDocument().addDocumentListener(new TextDocListener() {
				public void call(String p) {
					applicationControl.setControlFormat(p);
				}				
			});
			return control;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			return new UITextField();
		}
	}

	private UIComboBox getSourceRefId() {
		try {
			ReferenceDisplayList list = new ReferenceDisplayList();
			UIComboBox control = new UIComboBox(list);
			list.setSelectedRefId(applicationControl.getSourceRefId());
			ReferenceListListener listener = (new ReferenceListListener() {
				public void call(int p) {
					try {
						applicationControl.setSourceRefId(p);
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


	private UITextField getDefaultValue() {
		try {
			UITextField control = new UITextField(10);
			control.setText(applicationControl.getDefaultValue());
			control.getDocument().addDocumentListener(new TextDocListener() {
				public void call(String p) {
					applicationControl.setDefaultValue(p);
				}				
			});
			return control;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			return new UITextField();
		}
	}

	private UIIndicatorField getLockedInd() {
		try {
			final UIIndicatorField control = new UIIndicatorField();
			control.setSelected(applicationControl.getLockedInd()==1);
			control.addChangeListener(new IndicatorListener() {
				public void call(ChangeEvent c) {
					int p = 0;
					if (control.isSelected()) {
						p = 1;
					}
					applicationControl.setLockedInd(p);
				}				
			});
			return control;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			return new UIIndicatorField();
		}
	}

	private UIIndicatorField getDisabledInd() {
		try {
			final UIIndicatorField control = new UIIndicatorField();
			control.setSelected(applicationControl.getDisabledInd()==1);
			control.addChangeListener(new IndicatorListener() {
				public void call(ChangeEvent c) {
					int p = 0;
					if (control.isSelected()) {
						p = 1;
					}
					applicationControl.setDisabledInd(p);
				}				
			});
			return control;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			return new UIIndicatorField();
		}
	}

	private UIIndicatorField getRequiredInd() {
		try {
			final UIIndicatorField control = new UIIndicatorField();
			control.setSelected(applicationControl.getRequiredInd()==1);
			control.addChangeListener(new IndicatorListener() {
				public void call(ChangeEvent c) {
					int p = 0;
					if (control.isSelected()) {
						p = 1;
					}
					applicationControl.setRequiredInd(p);
				}				
			});
			return control;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			return new UIIndicatorField();
		}
	}

	private UIIndicatorField getVisibleInd() {
		try {
			final UIIndicatorField control = new UIIndicatorField();
			control.setSelected(applicationControl.getVisibleInd()==1);
			control.addChangeListener(new IndicatorListener() {
				public void call(ChangeEvent c) {
					int p = 0;
					if (control.isSelected()) {
						p = 1;
					}
					applicationControl.setVisibleInd(p);
				}				
			});
			return control;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			return new UIIndicatorField();
		}
	}

	private UIEditorPane getInitScript() {
		try {
			UIEditorPane control = new UIEditorPane();
			control.setText(applicationControl.getInitScript());
			control.getDocument().addDocumentListener(new TextDocListener() {
				public void call(String p) {
					applicationControl.setInitScript(p);
				}				
			});
			return control;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			return new UIEditorPane();
		}
	}

	private UIEditorPane getFocusLostScript() {
		try {
			UIEditorPane control = new UIEditorPane();
			control.setText(applicationControl.getFocusLostScript());
			control.getDocument().addDocumentListener(new TextDocListener() {
				public void call(String p) {
					applicationControl.setFocusLostScript(p);
				}				
			});
			return control;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			return new UIEditorPane();
		}
	}

	private UIEditorPane getFocusGainedScript() {
		try {
			UIEditorPane control = new UIEditorPane();
			control.setText(applicationControl.getFocusGainedScript());
			control.getDocument().addDocumentListener(new TextDocListener() {
				public void call(String p) {
					applicationControl.setFocusGainedScript(p);
				}				
			});
			return control;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			return new UIEditorPane();
		}
	}

	private UITextField getImagePath() {
		try {
			UITextField control = new UITextField(10);
			control.setText(applicationControl.getImagePath());
			control.getDocument().addDocumentListener(new TextDocListener() {
				public void call(String p) {
					applicationControl.setImagePath(p);
				}				
			});
			return control;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			return new UITextField();
		}
	}

	private UIDoubleField getGrowHeight() {
		try {
			UIDoubleField control = new UIDoubleField();
			control.setText(applicationControl.getGrowHeight());
			control.getDocument().addDocumentListener(new DoubleDocListener() {
				public void call(double p) {
					applicationControl.setGrowHeight(p);
				}				
			});
			return control;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			return new UIDoubleField();
		}
	}

	private UIIndicatorField getScrollpaneInd() {
		try {
			final UIIndicatorField control = new UIIndicatorField();
			control.setSelected(applicationControl.getScrollpaneInd()==1);
			control.addChangeListener(new IndicatorListener() {
				public void call(ChangeEvent c) {
					int p = 0;
					if (control.isSelected()) {
						p = 1;
					}
					applicationControl.setScrollpaneInd(p);
				}				
			});
			return control;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			return new UIIndicatorField();
		}
	}

	private UIIntegerField getUnitWidth() {
		try {
			UIIntegerField control = new UIIntegerField();
			control.setText(applicationControl.getUnitWidth());
			control.getDocument().addDocumentListener(new NumberDocListener() {
				public void call(int p) {
					applicationControl.setUnitWidth(p);
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
			control.setText(CalendarUtility.Format(applicationControl.getCreateDt(), CalendarUtility.ShortDateFormat()));
			control.getDocument().addDocumentListener(new TextDocListener() {
				public void call(String p) {
					try { 
						if (CalendarUtility.shouldParseDate(p, CalendarUtility.ShortDateFormat())) {
						applicationControl.setCreateDt(CalendarUtility.Parse(p, CalendarUtility.ShortDateFormat()));
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
			control.setText(applicationControl.getCreateUserName());
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
			control.setText(CalendarUtility.Format(applicationControl.getUpdateDt(), CalendarUtility.ShortDateFormat()));
			control.getDocument().addDocumentListener(new TextDocListener() {
				public void call(String p) {
					try { 
						if (CalendarUtility.shouldParseDate(p, CalendarUtility.ShortDateFormat())) {
						applicationControl.setUpdateDt(CalendarUtility.Parse(p, CalendarUtility.ShortDateFormat()));
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
			control.setText(applicationControl.getUpdateUserName());
			return control;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			return new UIUserField();
		}
	}

	private UIIntegerField getUpdateCount() {
		try {
			UIIntegerField control = new UIIntegerField();
			control.setText(applicationControl.getUpdateCount());
			control.getDocument().addDocumentListener(new NumberDocListener() {
				public void call(int p) {
					applicationControl.setUpdateCount(p);
				}				
			});
			return control;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			return new UIIntegerField();
		}
	}

	private UIIndicatorField getActiveInd() {
		try {
			final UIIndicatorField control = new UIIndicatorField();
			control.setSelected(applicationControl.getActiveInd()==1);
			control.addChangeListener(new IndicatorListener() {
				public void call(ChangeEvent c) {
					int p = 0;
					if (control.isSelected()) {
						p = 1;
					}
					applicationControl.setActiveInd(p);
				}				
			});
			return control;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			return new UIIndicatorField();
		}
	}

	private UIIntegerField getSystemAssignedVersionNbr() {
		try {
			UIIntegerField control = new UIIntegerField();
			control.setText(applicationControl.getSystemAssignedVersionNbr());
			control.getDocument().addDocumentListener(new NumberDocListener() {
				public void call(int p) {
					applicationControl.setSystemAssignedVersionNbr(p);
				}				
			});
			return control;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			return new UIIntegerField();
		}
	}

	private UIComboBox getRecordTypeRefId() {
		try {
			ReferenceDisplayList list = ReferenceServices.getDisplayList(ReferenceGroup.RecordType, false);
			UIComboBox control = new UIComboBox(list);
			list.setSelectedRefId(applicationControl.getRecordTypeRefId());
			ReferenceListListener listener = (new ReferenceListListener() {
				public void call(int p) {
					try {
						applicationControl.setRecordTypeRefId(p);
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


	private UITextField getControlDescription() {
		try {
			UITextField control = new UITextField(10);
			control.setText(applicationControl.getControlDescription());
			control.getDocument().addDocumentListener(new TextDocListener() {
				public void call(String p) {
					applicationControl.setControlDescription(p);
				}				
			});
			return control;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			return new UITextField();
		}
	}

	private UIComboBox getModelColumnRefId() {
		try {
			ReferenceDisplayList list = ReferenceServices.getDisplayList(ReferenceGroup.ModelColumn, false);
			UIComboBox control = new UIComboBox(list);
			list.setSelectedRefId(applicationControl.getModelColumnRefId());
			ReferenceListListener listener = (new ReferenceListListener() {
				public void call(int p) {
					try {
						applicationControl.setModelColumnRefId(p);
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


	private ApplicationCustomControl getApplicationCustomControlId() {
		try {
			ApplicationCustomControl control = new ApplicationCustomControl();
						control.setApplicationControl(applicationControl);
			return control;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			return new ApplicationCustomControl();
		}
	}

	private UIComboBox getControlTypeRefId() {
		try {
			ReferenceDisplayList list = ReferenceServices.getDisplayList(ReferenceGroup.ControlType, false);
			UIComboBox control = new UIComboBox(list);
			list.setSelectedRefId(applicationControl.getControlTypeRefId());
			ReferenceListListener listener = (new ReferenceListListener() {
				public void call(int p) {
					try {
						applicationControl.setControlTypeRefId(p);
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


	private UIIntegerField getUnitHeight() {
		try {
			UIIntegerField control = new UIIntegerField();
			control.setText(applicationControl.getUnitHeight());
			control.getDocument().addDocumentListener(new NumberDocListener() {
				public void call(int p) {
					applicationControl.setUnitHeight(p);
				}				
			});
			return control;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			return new UIIntegerField();
		}
	}

	private UIIntegerField getGrowWidth() {
		try {
			UIIntegerField control = new UIIntegerField();
			control.setText(applicationControl.getGrowWidth());
			control.getDocument().addDocumentListener(new NumberDocListener() {
				public void call(int p) {
					applicationControl.setGrowWidth(p);
				}				
			});
			return control;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			return new UIIntegerField();
		}
	}

	
}
