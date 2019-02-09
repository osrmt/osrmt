package com.osrmt.appclient.system;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.*;
import com.osframework.modellibrary.common.*;
import com.osframework.modellibrary.reference.security.*;
import com.osframework.appclient.services.*;
import com.osframework.appclient.ui.common.*;
import com.osframework.appclient.ui.components.UIStandardDialog;
import com.osframework.appclient.ui.controls.IGetDocument;
import com.osframework.appclient.ui.controls.UIComboBox;
import com.osframework.appclient.ui.controls.UIProperties;
import com.osframework.appclient.ui.listeners.*;
import com.osframework.modellibrary.reference.common.*;
import com.osframework.modellibrary.reference.group.*;
import com.osframework.framework.logging.*;
import com.osframework.framework.utility.*;
import com.osrmt.www.services.*;
import javax.servlet.http.*;
import javax.swing.FocusManager;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.struts.*;
import org.apache.struts.action.*;

import com.osrmt.appclient.services.RequirementServices;
import com.osrmt.modellibrary.reference.group.ApplicationGroup;
import com.osrmt.modellibrary.reference.group.RelationGroup;
import com.osrmt.modellibrary.reqmanager.*;

public class CustomControlForm extends UIStandardDialog {

	private ApplicationCustomControlModel applicationCustomControl = new ApplicationCustomControlModel();
	private JFrame frame;
	private ApplicationControlList controls = null;
	private ControlPanel controlPanel;
	private ApplicationCustomControlModel cancelModel;
	
	public CustomControlForm(JFrame frame) {
		super(frame, true);
		this.frame = frame;
	}
	
	public void start(ApplicationCustomControlModel original) {
		cancelModel = new ApplicationCustomControlModel();
		cancelModel.updateWith(original);
		cancelModel.resetModified();
		applicationCustomControl = original;
		initializeApplication();
	}

	public void initializeApplication () {
		controls = SecurityServices.getAppControlsByUser(ApplicationFramework.get(ApplicationGroup.APPLICATIONCUSTOMCONTROLFORM));
		setTitle(ReferenceServices.getDisplay(FormTitleFramework.CUSTOMCONTROL) + " " + applicationCustomControl.getCustomControlRefDisplay());
		setLocation(UIProperties.getPoint75_75());
		setSize(UIProperties.getWINDOW_SIZE_800_600());
		addListeners();
		controlPanel = new ControlPanel(frame);
		controlPanel.initialize(controls, 2, applicationCustomControl);
		JPanel panel = controlPanel.getPanel();
		getCenterPanel().add(panel, BorderLayout.CENTER);
		setup();
		setVisible(true);
		FocusManager fm = FocusManager.getCurrentManager(); 
		fm.focusNextComponent(panel.getComponent(0));
	}
	
	private void setup() {
		try {
			final UIComboBox combo = (UIComboBox) ((FormControl) controlPanel.getFormControls().get(0)).getComponent();
			combo.addItemListener(new ItemListener() {
	
				public void itemStateChanged(ItemEvent e) {
					try {
						ApplicationCustomControlModel m =SecurityServices.getApplicationCustomControlByRef(((ReferenceDisplay) combo.getSelectedItem()).getRefId());
						for (int i=0; i< controlPanel.getFormControls().size(); i++) {
							FormControl fc = (FormControl) controlPanel.getFormControls().get(i);
							if (fc.getApplicationControlModel().getModelColumnRefId() == ModelColumnFramework.APPLICATIONCUSTOMCONTROLCLASSNAME) {
								IGetDocument document = (IGetDocument) fc.getComponent();
								document.setText(m.getClassName());
							} else if (fc.getApplicationControlModel().getModelColumnRefId() == ModelColumnFramework.APPLICATIONCUSTOMCONTROLPOPULATESCRIPT) {
								IGetDocument document = (IGetDocument) fc.getComponent();
								document.setText(m.getPopulateScript());
							} else if (fc.getApplicationControlModel().getModelColumnRefId() == ModelColumnFramework.APPLICATIONCUSTOMCONTROLHTMLSCRIPT) {
								IGetDocument document = (IGetDocument) fc.getComponent();
								document.setText(m.getHtmlScript());
							}
						}
					} catch (Exception ex) {
						Debug.LogException(this, ex);
					}
				}
			
			});
		} catch (Exception ex) {
			Debug.LogException(this, ex);
		}
	}
	
	public void addListeners() {
		//TODO refresh the tree on save
		ActionListener save = new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					if (!GUI.requiredFieldsPopulated(controls, applicationCustomControl)) {
						return;
					}
					if (!controlPanel.hasAppliedChanges()) {
						return;
					}
					SecurityServices.UpdateApplicationCustomControl(applicationCustomControl);
					dispose();
				} catch (Exception ex) {
					Debug.LogException(this, ex);
				}
			}
		};
		ActionListener cancel = new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				applicationCustomControl.updateWith(cancelModel);
				applicationCustomControl.resetModified();
				dispose();
			}
		};
		getButtonPanel().getCmdButton0().addActionListener(cancel);
		getButtonPanel().getCmdButton1().addActionListener(save);
	}
	

}

