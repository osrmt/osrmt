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
package com.osrmt.appclient.reqmanager;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;
import javax.swing.*;
import javax.swing.event.*;

import com.osrmt.modellibrary.reqmanager.*;
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
public class TraceFormBaseController {
	
	private TraceModel trace;
	public TraceModel getTrace() {
		return trace;
	}

	protected RuleScript script = null;
	protected TraceFormUI ui;

	public TraceFormBaseController(JFrame frame) {
		 ui = new TraceFormUI(frame);
	}

	
	public void initialize (ApplicationControlList controls, TraceModel m) {
		this.trace = m;
		script = new RuleScript(null,null);
		initializeUI(controls);
	}
	
	private void initializeUI(ApplicationControlList controls) {
		java.util.Enumeration e1 = controls.elements();
		while (e1.hasMoreElements()) {
			ApplicationControlModel rm = (ApplicationControlModel) e1.nextElement();
			if (rm.getControlRefDisplay().compareTo("TraceFrom")==0) {
				UIComboBox field = getTraceFrom();
				GUI.setGUIMessage(field, rm);
				if (rm.isDisabled()) {
 					field.setEnabled(false);
				} else {
					script.executeScript(rm.getInitScript(), field);
					script.bind(field, rm);
 				}
				ui.setTraceFrom(field);
			}
			if (rm.getControlRefDisplay().compareTo("TraceType")==0) {
				UIComboBox field = getTraceType();
				GUI.setGUIMessage(field, rm);
				if (rm.isDisabled()) {
 					field.setEnabled(false);
				} else {
					script.executeScript(rm.getInitScript(), field);
					script.bind(field, rm);
 				}
				ui.setTraceType(field);
			}
			if (rm.getControlRefDisplay().compareTo("TraceTo")==0) {
				UIComboBox field = getTraceTo();
				GUI.setGUIMessage(field, rm);
				if (rm.isDisabled()) {
 					field.setEnabled(false);
				} else {
					script.executeScript(rm.getInitScript(), field);
					script.bind(field, rm);
 				}
				ui.setTraceTo(field);
			}
			if (rm.getControlRefDisplay().compareTo("ApplyButton")==0) {
				UIButton field = getApplyButton();
				GUI.setGUIMessage(field, rm);
				if (rm.isDisabled()) {
 					field.setEnabled(false);
				} else {
					script.executeScript(rm.getInitScript(), field);
					script.bind(field, rm);
 				}
				field.setText(rm.getControlText());
				ui.setApplyButton(field);
			}
		}
		ui.addControls(controls);
		ui.setLocationRelativeTo(null);
	}
	
	
	private UIComboBox getTraceFrom() {
		try {
			ReferenceDisplayList list = ReferenceServices.getDisplayList(ReferenceGroup.Artifact, false);
			UIComboBox control = new UIComboBox(list);
			list.setSelectedRefId(trace.getTraceFromArtifactRefId());
			ReferenceListListener listener = (new ReferenceListListener() {
				public void call(int p) {
					try {
						trace.setTraceFromArtifactRefId(p);
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


	private UIComboBox getTraceType() {
		try {
			ReferenceDisplayList list = ReferenceServices.getDisplayList(ReferenceGroup.TraceType, false);
			UIComboBox control = new UIComboBox(list);
			list.setSelectedRefId(trace.getTraceTypeRefId());
			ReferenceListListener listener = (new ReferenceListListener() {
				public void call(int p) {
					try {
						trace.setTraceTypeRefId(p);
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


	private UIComboBox getTraceTo() {
		try {
			ReferenceDisplayList list = ReferenceServices.getDisplayList(ReferenceGroup.Artifact, false);
			UIComboBox control = new UIComboBox(list);
			list.setSelectedRefId(trace.getTraceToArtifactRefId());
			ReferenceListListener listener = (new ReferenceListListener() {
				public void call(int p) {
					try {
						trace.setTraceToArtifactRefId(p);
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


	private UIButton getApplyButton() {
		try {
			UIButton control = new UIButton();
			
			return control;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			return new UIButton();
		}
	}

	
}
