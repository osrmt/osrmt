package com.osrmt.apps.swingApp.wizards;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;

import com.osframework.appclient.services.ReferenceServices;
import com.osframework.appclient.ui.components.PanelButtonWizard;
import com.osframework.appclient.ui.components.UICenterSouthDialog;
import com.osframework.appclient.ui.components.UIList;
import com.osframework.appclient.ui.controls.UIProperties;
import com.osframework.appclient.ui.listeners.DoubleClickListener;
import com.osframework.appclient.ui.listeners.KeyEnterListener;
import com.osframework.appclient.ui.listeners.UIActionListener;
import com.osframework.appclient.ui.listeners.UIListSelectionListener;
import com.osframework.framework.logging.Debug;
import com.osframework.modellibrary.reference.common.ReferenceDisplay;
import com.osframework.modellibrary.reference.common.ReferenceDisplayList;
import com.osframework.modellibrary.reference.group.FormButtonTextFramework;
import com.osrmt.apps.swingApp.system.UISystemForm;
import com.osrmt.modellibrary.reference.group.ReferenceGroup;

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
		screen2SystemForm.setSize(UIProperties.getWINDOW_SIZE_800_600());
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
