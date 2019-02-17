package com.osrmt.appclient.artifact.form;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.util.*;
import java.text.*;
import javax.swing.event.*;
import javax.swing.text.*;

import com.jgoodies.forms.builder.*;
import com.jgoodies.forms.layout.*;

import com.osframework.appclient.ui.common.ControlPanel;
import com.osframework.appclient.ui.components.*;
import com.osframework.appclient.ui.controls.*;
import com.osframework.modellibrary.common.*;
import com.osframework.modellibrary.reference.security.*;
import com.osframework.appclient.services.*;
import com.osframework.appclient.ui.listeners.*;
import com.osframework.modellibrary.reference.common.*;
import com.osframework.modellibrary.reference.group.*;
import com.osframework.modellibrary.system.*;
import com.osframework.framework.logging.*;
import com.osframework.framework.utility.*;
import com.osrmt.modellibrary.reference.group.ApplicationGroup;
import com.osrmt.modellibrary.reference.group.FormTitleGroup;
import com.osrmt.modellibrary.reference.group.TableNameGroup;
import com.osrmt.modellibrary.reqmanager.ArtifactList;
import com.osrmt.modellibrary.reqmanager.ArtifactModel;


public class ArtifactListFilterForm extends UICenterSouthDialog {
	
	private static final long serialVersionUID = 1L;

	private JFrame frame;
	private UIPanelButton panelButton = new UIPanelButton();
	private ActionListener lastOkAction = null;
	private RecordParameterControlList parameterControlList = new RecordParameterControlList();
	private int artifactRefId = 0;
	
	public ArtifactListFilterForm(JFrame frame, int artifactRefId, RecordParameterControlList parameterControlList) {
		super(frame, false);
		this.frame = frame;
		this.artifactRefId = artifactRefId;
		this.parameterControlList = parameterControlList;
		initialize();
	}
	
	public void initialize() {
		try {
			initForm();
			addListeners();
		} catch (Exception ex) {
			Debug.LogException(this, ex);
		}
	}
	
	private void initForm() {
		setSize(UIProperties.getWINDOW_SIZE_640_480());
		setTitle(ReferenceServices.getDisplay(FormTitleGroup.FILTERARTIFACTLIST));
		this.panelButton.initialize(UIPanelButton.CLEAROKCANCEL);
		getSouthPanel().add(panelButton, BorderLayout.CENTER);
		getCenterPanel().add(buildParameterPanel(), BorderLayout.CENTER);
	}
	
	private JPanel buildParameterPanel() {
		ApplicationControlList list = SecurityServices.getAppControls(ViewFramework.get(0),artifactRefId,ApplicationFramework.get(ApplicationGroup.FILTERARTIFACTLIST));
		Enumeration e1 = list.elements();
		while (e1.hasMoreElements()) {
			ApplicationControlModel acm = (ApplicationControlModel) e1.nextElement();
			RecordParameterModel m = new RecordParameterModel();
			m.setApplicationControlId(acm.getApplicationControlId());
			m.setModelColumnRefId(acm.getModelColumnRefId());
			parameterControlList.add(m);
		}
		
		JPanel panel = ControlPanel.getPanel(frame, list, parameterControlList, 2);
		return panel;
	}
	
	private void addListeners() {
		panelButton.getCmdButton0().addActionListener(new UIActionListener(frame) {
			public void actionExecuted(ActionEvent e) throws Exception {
				dispose();
			}
		});
		panelButton.getCmdButton1().addActionListener(new UIActionListener(frame) {
			public void actionExecuted(ActionEvent e) throws Exception {
				if (lastOkAction != null) {
					lastOkAction.actionPerformed(e);
				}
				dispose();
			}
		});
	}

	public ActionListener getLastOkAction() {
		return lastOkAction;
	}

	public void setLastOkAction(ActionListener lastOkAction) {
		this.lastOkAction = lastOkAction;
	}

	public RecordParameterControlList getParameterControlList() {
		return parameterControlList;
	}
	
	public static ArtifactList filter(ArtifactList list, RecordParameterControlList params) {
		if (params == null || params.size() == 0 || !params.hasValues()) {
			return list;
		}
		ArtifactList filteredList = new ArtifactList();
		Enumeration e1 = list.elements();
		while (e1.hasMoreElements()) {
			ArtifactModel m = (ArtifactModel) e1.nextElement();
			if (matches(m, params)) {
				filteredList.add(m);
			}
		}
		return filteredList;
	}
	
	private static boolean matches(ArtifactModel m, RecordParameterControlList params){
		Enumeration e1 = params.elements();
		while (e1.hasMoreElements()) {
			RecordParameterModel rpm = (RecordParameterModel) e1.nextElement();
			if (inList(m.getModelColDataAt(rpm.getModelColumnRefId()), rpm.getRecordParameterValueList())) {
				return true;
			}
		}
		return false;
	}
	
	private static boolean inList(Object value, RecordParameterValueList list) {
		Enumeration e1 = list.elements();
		while (e1.hasMoreElements()) {
			RecordParameterValueModel m = (RecordParameterValueModel) e1.nextElement();
			try {
				if (m.getValue().equals(value)) {
					return true;
				}
			} catch (Exception ex) {
				Debug.LogError("ArtifactListForm",ReferenceServices.getMsg(SystemMessageFramework.VALUESNOTCOMPARABLE) + ": " + m.getValue() + " , " + value);
				Debug.LogException("ArtifactListForm", ex);
			}
		}
		return false;
	}

	public UIPanelButton getPanelButton() {
		return panelButton;
	}

	public void setPanelButton(UIPanelButton panelButton) {
		this.panelButton = panelButton;
	}

}

