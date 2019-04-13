package com.osrmt.appclient.wizards;

import java.awt.*;
import java.awt.event.*;
import java.awt.print.*;
import java.util.*;
import javax.print.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.osframework.appclient.services.*;
import com.osframework.appclient.ui.common.*;
import com.osframework.appclient.ui.listeners.*;
import com.osframework.appclient.ui.components.*;
import com.osframework.appclient.ui.controls.*;
import com.osframework.framework.logging.Debug;
import com.osframework.framework.utility.FileProcess;
import com.osframework.framework.utility.FileSystemUtil;
import com.osframework.framework.utility.PrintUtility;
import com.osframework.modellibrary.reference.common.ReferenceDisplay;
import com.osframework.modellibrary.reference.common.ReferenceDisplayList;
import com.osframework.modellibrary.reference.group.FormButtonTextFramework;
import com.osframework.modellibrary.reference.security.ApplicationControlList;
import com.osframework.modellibrary.reference.security.ApplicationControlModel;
import com.osframework.modellibrary.system.*;
import com.osframework.modellibrary.reportwriter.*;
import com.osrmt.appclient.system.UISystemForm;
import com.osrmt.modellibrary.reference.group.ApplicationGroup;
import com.osrmt.modellibrary.reference.group.ReferenceGroup;
import com.osrmt.modellibrary.reference.group.TableNameGroup;

public class SystemFormWizard {
	
	UICenterSouthDialog screen1SelectApp;
	UICenterSouthDialog screen2SystemForm;
	UIList appList;
	JFrame frame;
	PanelButtonWizard screen1Buttons;
	PanelButtonWizard screen2Buttons;
	
	public SystemFormWizard(JFrame frame) {
		this.frame = frame;
	}

	public void start() throws Exception {
		screen1SelectApp = new UICenterSouthDialog(frame, false);
		screen2SystemForm = new UICenterSouthDialog(frame, false);
		buildScreen1();
		addScreen1Listeners();
		buildScreen2();
		addScreen2Listeners();
		screen1SelectApp.setVisible(true);
	}
	
	public void addScreen1Listeners() {
		
		appList.addListSelectionListener(new UIListSelectionListener(this.frame) {
			public void valueChangedExecuted(ListSelectionEvent e) {
				if (!appList.isSelectionEmpty()) {
					ReferenceDisplay rd = (ReferenceDisplay) appList.getSelectedValue();
					screen1SelectApp.setUserObject(rd);
				}
				screen1Buttons.getCmdNext().setEnabled(!appList.isSelectionEmpty());
			}			
		});
		screen1Buttons.getCmdCancel().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				screen1SelectApp.dispose();
			}
		});
		final UIActionListener nextActionListener = new UIActionListener(frame) {
			public void actionExecuted(ActionEvent e) {
				try {
					buildScreen2Panel();
					screen2SystemForm.setLocation(100,100);
					screen2SystemForm.setVisible(true);
					screen1SelectApp.setVisible(false);
				} catch (Exception ex) {
					Debug.LogException(this, ex);
				}
			}
		};
		screen1Buttons.getCmdNext().addActionListener(nextActionListener);
		appList.addMouseListener(new DoubleClickListener() {
			public void call(ActionEvent me) {
				try {
					nextActionListener.actionExecuted(me);
				} catch (Exception ex) {
					Debug.LogException(this, ex);
				}
			}
		});
		appList.addKeyListener(new KeyEnterListener(nextActionListener) {});
	}
	
	public void addScreen2Listeners() {
		screen2Buttons.getCmdCancel().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				screen2SystemForm.dispose();
			}
		});
		screen2Buttons.getCmdBack().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				screen1SelectApp.setVisible(true);
				screen2SystemForm.setVisible(false);
			}
		});
		screen2Buttons.getCmdFinish().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					screen2SystemForm.setVisible(false);
				} catch (Exception ex) {
					Debug.LogException(this, ex);
				}
			}
		});
	}
	
	private void buildScreen1() throws Exception {
		//TODO get this title from reference
		screen1SelectApp.setTitle(ReferenceServices.getMsg(FormButtonTextFramework.SELECTAPPLICATION));
		// Report list
		appList = new UIList(new DefaultListModel());
		ReferenceDisplayList list = ReferenceServices.getDisplayList(ReferenceGroup.Application, false);
		appList.addList(list.elements());
		// Buttons
		screen1Buttons = new PanelButtonWizard();
		screen1Buttons.setButtonState(true, false,false, false);
		screen1SelectApp.getSouthPanel().add(screen1Buttons, BorderLayout.CENTER);
		if (list.getSize() > 0) {
			appList.setSelectedIndex(0);			
			ReferenceDisplay rm = (ReferenceDisplay) appList.getSelectedValue();
			screen1SelectApp.setUserObject(rm);
			screen1Buttons.setButtonState(true, false,true, false);
		}
		screen1SelectApp.getCenterPanel().add(new JScrollPane(appList), BorderLayout.CENTER);
	}
	
	private void buildScreen2Panel() throws Exception {
		ReferenceDisplay rm = (ReferenceDisplay) screen1SelectApp.getUserObject();
		this.screen2SystemForm.setTitle(rm.getDisplay());
		screen2SystemForm.getCenterPanel().removeAll();
		screen2SystemForm.getCenterPanel().add(new UISystemForm(frame, rm.getRefId()), BorderLayout.CENTER);
		screen2SystemForm.setSize(UIProperties.getWINDOW_SIZE_1000_600());
	}

	private void buildScreen2() throws Exception {
		// Controls 
		// Buttons
		screen2Buttons = new PanelButtonWizard();
		screen2Buttons.getCmdFinish().setText(ReferenceServices.getMsg(FormButtonTextFramework.CLOSE));
		screen2Buttons.setButtonState(true, true, false, true);
		screen2SystemForm.getSouthPanel().add(screen2Buttons, BorderLayout.CENTER);
	}

}
