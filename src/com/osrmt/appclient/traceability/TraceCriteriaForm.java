package com.osrmt.appclient.traceability;

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
import com.osrmt.modellibrary.reqmanager.*;


public class TraceCriteriaForm extends UICenterSouthDialog {
	
	private static final long serialVersionUID = 1L;

	private JFrame frame;
	private UIPanelButton panelButton = new UIPanelButton();
	private ActionListener lastOkAction = null;
	private TraceTreeCriteria criteria = null;
	private ControlPanel cp;
	
	public TraceCriteriaForm(JFrame frame, TraceTreeCriteria criteria) {
		super(frame, false);
		this.frame = frame;
		this.criteria = criteria;
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
		setTitle(ReferenceServices.getDisplay(FormTitleGroup.TRACETRACECRITERIA));
		this.panelButton.initialize(UIPanelButton.OKCANCEL);
		getSouthPanel().add(panelButton, BorderLayout.CENTER);
		getCenterPanel().add(buildParameterPanel(), BorderLayout.CENTER);
	}
	
	private JPanel buildParameterPanel() {
		ApplicationControlList list = new ApplicationControlList();
		try {
			list = SecurityServices.getAppControls(ViewFramework.get(0),ApplicationFramework.get(ApplicationGroup.TRACETREECRITERIA), false);
		} catch (Exception ex) {
			
		}
		cp = new ControlPanel(frame);
		JPanel panel = cp.getPanel(frame, list, criteria, 2);
		return panel;
	}
	
	private void addListeners() {
		panelButton.getCmdButton0().addActionListener(new UIActionListener(frame) {
			public void actionExecuted(ActionEvent e) throws Exception {
				setVisible(false);
			}
		});
		panelButton.getCmdButton1().addActionListener(new UIActionListener(frame) {
			public void actionExecuted(ActionEvent e) throws Exception {
				updateCriteria();
				if (lastOkAction != null) {
					lastOkAction.actionPerformed(e);
				}
				setVisible(false);
			}
		});
	}
	
	private void updateCriteria() {
		Enumeration e1= cp.getFormControls().elements();
		while (e1.hasMoreElements()) {
			Component fc = (Component) e1.nextElement();
			if (fc instanceof UIValueList) {
				UIValueList list = (UIValueList) fc;
			}
		}

	}

	public ActionListener getLastOkAction() {
		return lastOkAction;
	}

	public void setLastOkAction(ActionListener lastOkAction) {
		this.lastOkAction = lastOkAction;
	}

	public UIPanelButton getPanelButton() {
		return panelButton;
	}

	public void setPanelButton(UIPanelButton panelButton) {
		this.panelButton = panelButton;
	}

}

