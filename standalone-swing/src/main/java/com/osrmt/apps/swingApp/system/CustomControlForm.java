package com.osrmt.apps.swingApp.system;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.FocusManager;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.osframework.appclient.services.ReferenceServices;
import com.osframework.appclient.services.SecurityServices;
import com.osframework.appclient.ui.common.ControlPanel;
import com.osframework.appclient.ui.common.FormControl;
import com.osframework.appclient.ui.common.GUI;
import com.osframework.appclient.ui.components.UIStandardDialog;
import com.osframework.appclient.ui.controls.IGetDocument;
import com.osframework.appclient.ui.controls.UIComboBox;
import com.osframework.appclient.ui.controls.UIProperties;
import com.osframework.framework.logging.Debug;
import com.osframework.modellibrary.reference.common.ReferenceDisplay;
import com.osframework.modellibrary.reference.group.ApplicationFramework;
import com.osframework.modellibrary.reference.group.FormTitleFramework;
import com.osframework.modellibrary.reference.group.ModelColumnFramework;
import com.osframework.modellibrary.reference.security.ApplicationControlList;
import com.osframework.modellibrary.reference.security.ApplicationCustomControlModel;
import com.osrmt.modellibrary.reference.group.ApplicationGroup;

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

