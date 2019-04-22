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
import com.osframework.appclient.ui.common.FormControl;
import com.osframework.appclient.ui.common.GUI;
import com.osframework.appclient.ui.components.*;
import com.osframework.appclient.ui.controls.*;
import com.osframework.modellibrary.common.*;
import com.osframework.modellibrary.reference.security.*;
import com.osframework.appclient.services.*;
import com.osframework.appclient.ui.listeners.*;
import com.osframework.modellibrary.reference.common.*;
import com.osframework.modellibrary.reference.group.*;
import com.osframework.modellibrary.system.RecordParameterControlList;
import com.osframework.modellibrary.system.RecordParameterModel;
import com.osframework.framework.logging.*;
import com.osframework.framework.utility.*;
import com.osrmt.appclient.services.*;
import com.osrmt.modellibrary.reference.group.ApplicationGroup;
import com.osrmt.modellibrary.reference.group.FormTitleGroup;
import com.osrmt.modellibrary.reference.group.RelationGroup;
import com.osrmt.modellibrary.reqmanager.*;

public class ArtifactListImportForm extends UIStandardDialog {
	
	private static final long serialVersionUID = 1L;

	private JFrame frame;
	private ArtifactExportModel exportModel = new ArtifactExportModel();
	private UIValueList previewList = new UIValueList();
	private ArtifactModel parentArtifact = new ArtifactModel();

	public ArtifactListImportForm(JFrame frame, ArtifactModel parentArtifact) {
		super(frame);
		this.frame = frame;
		this.parentArtifact = parentArtifact;
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
		setTitle(ReferenceServices.getDisplay(FormTitleGroup.ARTIFACTLISTIMPORT));
		getCenterPanel().add(buildExportPanel(), BorderLayout.NORTH);
		getCenterPanel().add(previewList, BorderLayout.CENTER);
		setSize(UIProperties.getWINDOW_SIZE_700_400());
	}
	
	private void addListeners() {
		getButtonPanel().getCmdButton0().addActionListener(new UIActionListener(frame) {
			public void actionExecuted(ActionEvent e) throws Exception {
				dispose();
			}
		});
		getButtonPanel().getCmdButton1().addActionListener(new UIActionListener(frame) {
			public void actionExecuted(ActionEvent e) throws Exception {
				importArtifacts(exportModel.getFilePath());
				dispose();
			}
		});
		getButtonPanel().initialize(UIPanelButton.CLEAROKCANCEL);
		getButtonPanel().getCmdButton2().setVisible(true);
		getButtonPanel().getCmdButton2().setText(ReferenceServices.getMsg(FormButtonTextFramework.PREVIEW));
		getButtonPanel().getCmdButton2().addActionListener(new UIActionListener(frame) {
			public void actionExecuted(ActionEvent e) throws Exception {
				ArtifactList list = RequirementServices.importArtifacts(exportModel.getFilePath());
				ReferenceServices.setReference(list.elements());
				previewList.setTableModel(list,60);
			}
		});
	}

	private JPanel buildExportPanel() {
		ApplicationControlList list = SecurityServices.getAppControls(ApplicationFramework.get(ApplicationGroup.ARTIFACTLISTIMPORT));		
		ControlPanel cp = new ControlPanel(frame);
		cp.initialize(list, 1, exportModel);
		
		Enumeration e1= cp.getFormControls().elements();
		while (e1.hasMoreElements()) {
			FormControl fc = (FormControl) e1.nextElement();
			if (fc.getModelColumnRefId() == ModelColumnFramework.ARTIFACTEXPORTIMPORTUNDERPARENT) {
				if (fc.getComponent() instanceof UICheckBox) {
					UICheckBox checkBox = (UICheckBox) fc.getComponent();
					if (parentArtifact.getArtifactId() == 0) {
						checkBox.setSelected(false);
						checkBox.setEnabled(false);
					} else {
						checkBox.setText(parentArtifact.getArtifactName());
					}
				} else {
					Debug.LogError(this,"control is not UIOptionGroup");
				}				
			}
		}
		return cp.getPanel();
	}
	
	private void importArtifacts(String filepath) {
		try {
			ArtifactList list = RequirementServices.importArtifacts(exportModel.getFilePath());
			if (parentArtifact.getArtifactId() > 0) {
				RequirementServices.UpdateArtifact(list, parentArtifact, RelationGroup.get(RelationGroup.RELATED), false);
			} else {
				RequirementServices.UpdateArtifact(list);
			}
			Debug.displayGUIMessage(list.size() + " " + ReferenceServices.getMsg(SystemMessageFramework.ARTIFACTSIMPORTED));
		} catch (Exception ex) {
			Debug.LogException(this, ex);
		}
	}

	public ArtifactModel getParentArtifact() {
		return parentArtifact;
	}

	public void setParentArtifact(ArtifactModel parentArtifact) {
		this.parentArtifact = parentArtifact;
	}

	
}
