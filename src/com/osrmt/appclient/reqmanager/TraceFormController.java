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

*/package com.osrmt.appclient.reqmanager;

import javax.swing.event.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.rmi.RemoteException;
import java.util.*;

import java.awt.BorderLayout;
import com.osrmt.modellibrary.reqmanager.*;
import com.osrmt.appclient.common.ApplicationObject;
import com.osrmt.appclient.system.*;
import com.osrmt.appclient.services.*;
import com.osframework.appclient.ui.common.*;
import com.osframework.appclient.ui.listeners.*;
import com.osframework.appclient.ui.controls.*;
import com.osframework.modellibrary.common.ResultList;
import com.osframework.modellibrary.reference.common.*;
import com.osframework.modellibrary.reference.group.ApplicationFramework;
import com.osframework.modellibrary.reference.group.FormButtonTextFramework;
import com.osframework.modellibrary.reference.security.*;
import com.osrmt.modellibrary.reference.group.*;
import com.osframework.datalibrary.common.DataAccessException;
import com.osframework.framework.logging.*;
import com.osframework.framework.utility.*;
import com.osframework.appclient.services.*;
import com.osframework.appclient.ui.components.*;

import com.jgoodies.forms.builder.ButtonBarBuilder;

/**
null
*/
public class TraceFormController extends TraceFormBaseController {

	private MultiColumnList traceList = new MultiColumnList();
	private TraceFormActions actions = new TraceFormActions();
	private ApplicationActionList applicationActionList;
	private JPanel listPanel = new JPanel(new BorderLayout());
	private JPanel controlNorthPanel = new JPanel(new BorderLayout());
	private TraceModel traceModel = new TraceModel();
	private JFrame frame;
	
	public TraceFormController(JFrame frame) {
		super(frame);
		this.frame = frame;
	}

	/**
	 * @param args
	 */
	public void start (ArtifactModel am) {
		customizePanels();
		ApplicationControlList list = SecurityServices.getAppControls(ApplicationFramework.get(ApplicationGroup.TRACEFORM));
		super.initialize(list, traceModel); 
		addControls();
		addListeners();
		initForm();
	}
	
	/**
	 * @param args
	 */
	public void start () {
		customizePanels();
		ApplicationControlList list = SecurityServices.getAppControls(ApplicationFramework.get(ApplicationGroup.TRACEFORM));
		super.initialize(list, traceModel); 
		addControls();
		addListeners();
		initForm();
	}
	
	private void customizePanels() {
		controlNorthPanel.setSize(100,200);
		super.ui.setControlColumns(2);
		super.ui.setControlPanel(controlNorthPanel);
		super.ui.getPanelCenter().add(controlNorthPanel, BorderLayout.NORTH);
		super.ui.getPanelCenter().add(listPanel, BorderLayout.CENTER);
	}

	private void initForm() {
		ui.setLocation(100,100);
		ui.setSize(UIProperties.getWINDOW_SIZE_800_600());
		ui.setTitle(ReferenceServices.getDisplay(ApplicationGroup.TRACEFORM));
		ui.setVisible(true);
	}

	private void addControls() {
		traceModel.setProductRefId(ApplicationObject.getApplicationProductRefId());
		applicationActionList = actions.getActions(this);
		TraceFormTools tools = new TraceFormTools();
		UIToolBar toolBar = tools.getToolBar(applicationActionList);
		//ui.setJMenuBar(tools.getMenuBar(applicationActionList));
		//ui.getPanelNorth().add(toolBar, BorderLayout.CENTER);
		listPanel.add(traceList, BorderLayout.CENTER);
		ui.getPanelOkCancel().getCmdOk().setVisible(false);
		ui.getPanelOkCancel().getCmdCancel().setText(ReferenceServices.getMsg(FormButtonTextFramework.CLOSE));
	}

	private void addListeners() {
		ui.getPanelOkCancel().getCmdCancel().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ui.dispose();
			}		
		});
		ui.getApplyButton().addActionListener(new UIActionListener(frame) {
			public void actionExecuted(ActionEvent e) {
				try {
					ArtifactList list = new ArtifactList();
					if (traceModel.getTraceTypeRefId() == TraceTypeGroup.TRACED) {
						list = RequirementServices.getArtifactsTraced(traceModel);
						ApplicationControlList acl1 = SecurityServices.getAppControlsByUser(traceModel.getTraceFromArtifactRefId(), ApplicationFramework.get(ApplicationGroup.ARTIFACTSEARCHRESULTS));
						list.setColumnOrder(acl1);
						ApplicationObject.trimDescription(list);
						traceList.setTableModel(list, 40);
					} else if (traceModel.getTraceTypeRefId() == TraceTypeGroup.NOTTRACED) {
						list = RequirementServices.getArtifactsNotTraced(traceModel);
						ApplicationControlList acl1 = SecurityServices.getAppControlsByUser(traceModel.getTraceFromArtifactRefId(), ApplicationFramework.get(ApplicationGroup.ARTIFACTSEARCHRESULTS));
						list.setColumnOrder(acl1);
						ApplicationObject.trimDescription(list);
						traceList.setTableModel(list, 40);
					} else { //(traceModel.getTraceTypeRefId() == TraceTypeGroup.MATRIX) {
						ResultList resultList = RequirementServices.getArtifactMatrix(traceModel);
						traceList.setTableModel(resultList, 80);
					}
				} catch (Exception ex) {
					Debug.LogException(this, ex);
					traceList.clear();
				}
			}			
		});
	}

}