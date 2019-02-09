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

import com.osframework.appclient.ui.common.*;
import com.osframework.appclient.ui.components.*;
import com.osframework.appclient.ui.controls.*;
import com.osframework.modellibrary.common.*;
import com.osframework.modellibrary.reference.security.*;
import com.osframework.appclient.services.*;
import com.osframework.appclient.ui.listeners.*;
import com.osframework.modellibrary.reference.common.*;
import com.osframework.modellibrary.reference.group.*;
import com.osframework.framework.logging.*;
import com.osframework.framework.utility.*;
import com.osrmt.appclient.common.ApplicationObject;
import com.osrmt.appclient.services.RequirementServices;
import com.osrmt.modellibrary.reference.group.ApplicationGroup;
import com.osrmt.modellibrary.reqmanager.*;

public class BaselineForm extends UIStandardDialog {
	
	private static final long serialVersionUID = 1L;

	private JFrame frame;
	private BaselineModel baseline = new BaselineModel();
	
	public BaselineForm(JFrame frame) {
		super(frame);
		this.frame = frame;
		initialize();
		addListeners();
	}	
	
	public void initialize() {
		setTitle(ReferenceServices.getDisplay(FormTitleFramework.SYSTEMBASELIINE));
		ApplicationControlList acl = SecurityServices.getAppControls(ApplicationFramework.get(ApplicationGroup.BASELINEFORM));
		JPanel panel = ControlPanel.getPanel(frame, acl, baseline, 1);
		getCenterPanel().add(panel, BorderLayout.CENTER);
	}
	
	
	private void addListeners() {
		getButtonPanel().getCmdButton0().addActionListener(new UIActionListener(frame){
			public void actionExecuted(ActionEvent e) throws Exception {
				dispose();
			}
		});
		// OK
		getButtonPanel().getCmdButton1().addActionListener(new UIActionListener(frame){
			public void actionExecuted(ActionEvent e) throws Exception {
				if (baseline.getBaselineName() != null && baseline.getBaselineName().toString().length() > 0) {
					
					if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(frame, SystemServices.calculateBaseline())) {
						ApplicationObject ao = (ApplicationObject) Application.getObject();
						int productRefId = ao.getProductRefId();
						if (productRefId > 0) {
							int baselineId = RequirementServices.createBaseline(baseline.getBaselineName()).getPrimaryKeyId();
							dispose();
						}
					}

				}
			}
		});
	}
	
}

