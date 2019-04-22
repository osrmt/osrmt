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
import com.osrmt.modellibrary.reqmanager.*;

public class ArtifactListExportForm extends UICenterSouthDialog {
	
	private static final long serialVersionUID = 1L;

	private ArtifactList artifactList;
	private JFrame frame;
	private PanelOkCancel okCancel = new PanelOkCancel();
	private ArtifactExportModel exportModel = new ArtifactExportModel();
	private Vector formControls = new Vector();

	public ArtifactListExportForm(JFrame frame, ArtifactList list) {
		super(frame, false);
		this.frame = frame;
		this.artifactList = list;
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
		setTitle(ReferenceServices.getDisplay(FormTitleGroup.ARTIFACTLISTEXPORT));
		getSouthPanel().add(okCancel, BorderLayout.CENTER);
		getCenterPanel().add(new JScrollPane(buildExportPanel()), BorderLayout.CENTER);
		setSize(UIProperties.getWINDOW_SIZE_700_400());
	}
	
	private void addListeners() {
		okCancel.addCancelActionListener(new UIActionListener(frame) {
			public void actionExecuted(ActionEvent e) throws Exception {
				dispose();
			}
		});
		okCancel.addOkActionListener(new UIActionListener(frame) {
			public void actionExecuted(ActionEvent e) throws Exception {
				updateModel();
				exportArtifacts();
				dispose();
			}
		});
	}

	private JPanel buildExportPanel() {
		ApplicationControlList list = SecurityServices.getAppControls(ApplicationFramework.get(ApplicationGroup.ARTIFACTLISTEXPORT));
		
		ControlPanel cp = new ControlPanel(frame);
		cp.initialize(list, 1, exportModel);
		formControls = cp.getFormControls();
		return cp.getPanel();
	}
	
	//TODO rewrite this hardcoded form...
	private void updateModel() {
		Enumeration e1= this.formControls.elements();
		while (e1.hasMoreElements()) {
			FormControl fc = (FormControl) e1.nextElement();
			if (fc.getModelColumnRefId() == ModelColumnFramework.ARTIFACTEXPORTMODELSELECTED) {
				if (fc.getComponent() instanceof UIOptionGroup) {
					UIOptionGroup optionGroup = (UIOptionGroup) fc.getComponent();
					if (optionGroup.getSelected() == 0) {
						exportModel.setExportList(true);
					} else {
						exportModel.setExportAllArtifacts(true);
					}
				} else {
					Debug.LogError(this,"control is not UIOptionGroup");
				}				
			} else if (fc.getModelColumnRefId() == ModelColumnFramework.ARTIFACTEXPORTMODELPRIMARYKEY) {
				if (fc.getComponent() instanceof UICheckBox) {
					UICheckBox checkBox = (UICheckBox) fc.getComponent();
					exportModel.setRemovePrimaryKey(checkBox.isSelected());
				} else {
					Debug.LogError(this,"control is not UICheckBox");
				}
			} else if (fc.getModelColumnRefId() == ModelColumnFramework.ARTIFACTEXPORTMODELTREE) {
				if (fc.getComponent() instanceof UICheckBox) {
					UICheckBox checkBox = (UICheckBox) fc.getComponent();
					exportModel.setExportRelationships(checkBox.isSelected());
				} else {
					Debug.LogError(this,"control is not UICheckBox");
				}
			} else if (fc.getModelColumnRefId() == ModelColumnFramework.ARTIFACTEXPORTMODELDEPENDENCIES) {
				if (fc.getComponent() instanceof UICheckBox) {
					UICheckBox checkBox = (UICheckBox) fc.getComponent();
					exportModel.setExportDependencies(checkBox.isSelected());
				} else {
					Debug.LogError(this,"control is not UICheckBox");
				}
			}
		}
	}

	private void exportArtifacts() {
		if (artifactList == null || artifactList.size() == 0) {
			final UIReferenceSearch referenceSearch = new UIReferenceSearch(frame);
			referenceSearch.start(ReferenceGroup.Artifact, ReferenceServices.getMsg(SystemMessageFramework.SELECTARTIFACTTYPESTOEXPORT), false);
			referenceSearch.setSize(UIProperties.getDIALOG_SIZE_450_330());
			referenceSearch.addOkActionListener(new UIActionListener(frame) {
				public void actionExecuted(ActionEvent e) {
					try {
						//TODO should force selection before ok
						ArtifactList list = new ArtifactList(4096);
						if (referenceSearch.getSelectedValues() != null) {
							Enumeration e1 = referenceSearch.getSelectedValues().elements();
							while (e1.hasMoreElements()) {
								ReferenceDisplay artifactType = (ReferenceDisplay) e1.nextElement();
								list.addAll(RequirementServices.getAllItems(artifactType.getRefId()));
							}
							Debug.displayGUIMessage(list.size() + " " + ReferenceServices.getMsg(SystemMessageFramework.ARTIFACTSEXPORTEDTO)
									+ " " + exportModel.getFilePath());
							export(list);
							referenceSearch.dispose();
						}
					} catch (Exception ex) {
						Debug.LogException(this, ex);
					}
				}
			});
			referenceSearch.setVisible(true);

		} else {
			export(artifactList);
		}
	}
	
	private void export(ArtifactList list) {
		RequirementServices.exportArtifacts(list, exportModel);
	}
}
