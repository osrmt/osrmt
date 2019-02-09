package com.osrmt.appclient.system;

import java.awt.*;
import java.awt.event.*;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.*;
import javax.swing.event.*;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.factories.Borders;
import com.osframework.appclient.services.*;
import com.osframework.appclient.ui.common.*;
import com.osframework.appclient.ui.components.*;
import com.osframework.appclient.ui.controls.*;
import com.osframework.framework.logging.Debug;
import com.osframework.framework.utility.TimedAction;
import com.osframework.modellibrary.reference.common.*;
import com.osframework.modellibrary.reference.group.*;
import com.osframework.modellibrary.reference.security.*;
import com.osframework.modellibrary.system.RecordFileModel;

import javax.swing.*;
import javax.swing.border.Border;


import com.osframework.appclient.ui.controls.UIJFrame;
import com.osframework.appclient.ui.listeners.DoubleClickListener;
import com.osframework.appclient.ui.listeners.UIActionListener;
import com.osframework.appclient.ui.listeners.UIListSelectionListener;
import com.osrmt.modellibrary.reference.group.ApplicationGroup;
import com.osrmt.modellibrary.reference.group.ControlTypeGroup;
import com.osrmt.modellibrary.reference.group.ControlsGroup;

public class UISystemForm extends UIJPanel {
	
	private static final long serialVersionUID = 1L;

	private class SystemFormSelection {
		public int listViewRefId = 0;
		public int listAppTypeRefId = 0;
		public int listApplicationRefId = 0;
		public int tabIndex = 0;
		public Vector panelAddRemoveList = new Vector();
		public String toString() {
			return "listViewRefId: " + listViewRefId + "\t"
			+ "listAppTypeRefId: " + listAppTypeRefId + "\t" 
			+ "listApplicationRefId: " + listApplicationRefId + "\t" 
			+ "tabIndex: " + tabIndex + "\t";
		}
	}
	
	private JFrame frame;
	private UIJPanel northPanel = new UIJPanel(new BorderLayout());
	private UIJPanel centerPanel = new UIJPanel(new BorderLayout());
	private UIJPanel southPanel = new UIJPanel(new BorderLayout());
	private UIJPanel centerEastPanel = new UIJPanel(new BorderLayout());
	private UIJPanel centerWestPanel = new UIJPanel(new BorderLayout());
	private PanelAddRemove viewListPanel = new PanelAddRemove(PanelAddRemove.NO_RIGHT_SIDE_BUTTONS);
	private SystemFormSelection selection = new SystemFormSelection();
	private UITabbedPane tabbedPane = new UITabbedPane();

	
	public UISystemForm(JFrame frame, int applicationRefId) {
		super(new BorderLayout());
		this.frame = frame;
		this.setBorder(Borders.DIALOG_BORDER);
		initialize();
		selection.listApplicationRefId = applicationRefId;
		addControls();
		updateAppType();
		addListeners();
		if (tabbedPane.getTabCount() > 0) {
			selection.tabIndex = 0;
			updateAppType();
			buildViewList();
		}
	}
	
	private void addListeners() {
		//TODO must do something to avoid recreating listeners twice when control panel recreated
		viewListPanel.addListSelectionListener(new UIListSelectionListener(frame) {
			public void valueChangedExecuted(ListSelectionEvent e) {
				UIList list = (UIList) viewListPanel.getListControl();
				ReferenceDisplay rd = (ReferenceDisplay) list.getSelectedValue();
				selection.listViewRefId = rd.getRefId();
				displayControlList();
			}
		});
		tabbedPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent me) {
				selection.tabIndex = tabbedPane.getSelectedIndex();
				updateAppType();
				buildViewList();
			}			
		});
		this.viewListPanel.getRemoveButton().addActionListener(new UIActionListener(frame) {
			public void actionExecuted(ActionEvent e) throws Exception {
				PanelAddRemove controlPanel = (PanelAddRemove) selection.panelAddRemoveList.elementAt(selection.tabIndex);
				final UIList uilist = (UIList) controlPanel.getListControl();
				ReferenceModel rm = ReferenceServices.getReference(selection.listViewRefId);
				if (rm.getSystemAssignedVersionNbr() > 0) {
					//TODO replace systeversionnbr with recordTypeRefId and make a UI notification
					Debug.LogError(this, ReferenceServices.getMsg(SystemMessageFramework.YOUMAYNOTREMOVEASYSTEMVIEW));
				} else {
					ListModel list = uilist.getListModel();
					ApplicationControlList acl = new ApplicationControlList();
					for (int i=0; i< list.getSize(); i++) {
						UIItem item = (UIItem) list.getElementAt(i);
						ApplicationControlModel acm = (ApplicationControlModel) item.getUserObject(); 
						acm.setNotActive();
						acl.add(acm);
					}
					//TODO should do this as one transaction
					if (SecurityServices.UpdateApplicationControl(acl).getRowsUpdated() > 0) {
						rm.setNotActive();
						ReferenceServices.updateReference(rm);
					}
				}
			}			
		});
		this.viewListPanel.getAddButton().addActionListener(new UIActionListener(frame) {
			public void actionExecuted(ActionEvent e) throws Exception {
				final UIInputBox inputBox = new UIInputBox(null,ReferenceServices.getMsg(FormButtonTextFramework.ADDVIEW),frame,ReferenceServices.getMsg(FormButtonTextFramework.VIEW));
				UIActionListener action = new UIActionListener(frame) {
					public void actionExecuted(ActionEvent e) throws Exception {
						ReferenceDisplayList displays = ReferenceServices.getDisplayList(ReferenceGroup.View,inputBox.getInputText());
						if (displays.getSize() > 0 ) {
							Debug.LogError(this, ReferenceServices.getMsg(SystemMessageFramework.DUPLICATEVALUEFOUND));
						} else {
							SecurityServices.duplicateApplicationControlView(inputBox.getInputText(), ViewFramework.get(selection.listViewRefId), selection.listAppTypeRefId, ApplicationFramework.get(selection.listApplicationRefId));
							displays = ReferenceServices.getDisplayList(ReferenceGroup.View,inputBox.getInputText());
							if (displays.getSize() == 1) {
								ReferenceDisplay rd = displays.getFirst();
								UIList vlist = (UIList) viewListPanel.getListControl();
								vlist.addItem(rd.getDisplay(), rd);
							}
						}
					}					
				};
				inputBox.setOkActionListener(action);
				inputBox.setVisible(true);
			}			
		});
		this.viewListPanel.getPropertiesButton().addActionListener(new UIActionListener(frame) {
			@Override
			public void actionExecuted(ActionEvent e) throws Exception {
				final UIStandardDialog dialog = new UIStandardDialog(frame);
				dialog.setSize(UIProperties.getWINDOW_SIZE_600_400());
				dialog.getCenterPanel().add(getViewPropertiesPanel(), BorderLayout.CENTER);
				dialog.getButtonPanel().getCmdButton1().setVisible(false);
				dialog.getButtonPanel().getCmdButton1().setText(ReferenceServices.getDisplay(
						com.osframework.modellibrary.reference.group.FormButtonTextFramework.CLOSE));
				dialog.getButtonPanel().getCmdButton0().addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						dialog.setVisible(false);
					}
				});
				dialog.setVisible(true);
			}			
		});
	}
	
	/**
	 * Panel with user list and add button
	 * @return
	 */
	private UIJPanel getViewPropertiesPanel() {
		
		try {
			ApplicationControlList acl = SecurityServices.getAppControlsByUser(ApplicationFramework.get(ApplicationGroup.APPLICATIONVIEWFORM));
			final ApplicationViewModel avm = SecurityServices.getApplicationView(ViewFramework.get(selection.listViewRefId), selection.listAppTypeRefId, ApplicationFramework.get(selection.listApplicationRefId));
			JPanel panel = ControlPanel.getPanel(frame, acl, avm);

			final UIStandardPanel applyPanel = new UIStandardPanel();
			applyPanel.getCenterPanel().add(panel, BorderLayout.CENTER);
			applyPanel.getButtonPanel().initialize(UIPanelButton.APPLY);
			applyPanel.getButtonPanel().getCmdButton0().addActionListener(new UIActionListener(frame){
				public void actionExecuted(ActionEvent e) throws Exception {
					ApplicationViewList list = new ApplicationViewList();
					list.add(avm);
					SecurityServices.UpdateApplicationView(list);
					applyPanel.getButtonPanel().getCmdButton0().setEnabled(false);
				}
			});
			
			return applyPanel;
		} catch (Exception e) {
			Debug.LogException(this, e);
			return new UIJPanel();
		}
	}
	
	private void updateAppType() {
		PanelAddRemove controlPanel = (PanelAddRemove) selection.panelAddRemoveList.elementAt(selection.tabIndex);
		Integer appTypeInteger = (Integer) controlPanel.getUserObject();
		selection.listAppTypeRefId = appTypeInteger.intValue();
	}
	
	private void initialize() {
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(560,320));
		centerWestPanel.setPreferredSize(new Dimension(260,200));
		centerEastPanel.setPreferredSize(new Dimension(480,200));
	}
	
	private void addControls() {
		this.add(northPanel, BorderLayout.NORTH);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(southPanel, BorderLayout.SOUTH);
		centerPanel.add(centerWestPanel, BorderLayout.WEST);
		centerPanel.add(centerEastPanel, BorderLayout.CENTER);
		buildViewList();
		UITabbedPane viewListPane = new UITabbedPane();
		viewListPane.addTab(ReferenceServices.getMsg(FormButtonTextFramework.FORMVIEWS), this.viewListPanel);
		centerWestPanel.add(viewListPane, BorderLayout.CENTER);
		centerEastPanel.add(getTabs(), BorderLayout.CENTER);
		centerWestPanel.setPreferredSize(null);
		displayControlList();
	}
	
	private void buildViewList() {
		UIList list = new UIList(new DefaultListModel());
		ReferenceDisplayList itemList = SecurityServices.getViews(selection.listAppTypeRefId, ApplicationFramework.get(selection.listApplicationRefId));
		Enumeration e1 = itemList.elements();
		while (e1.hasMoreElements()) {
			ReferenceDisplay rm = (ReferenceDisplay) e1.nextElement();
			if (rm.getRefId() == 0) {
				rm.setDisplay(ReferenceServices.getMsg(FormButtonTextFramework.DEFAULT));
			}
			list.addItem(rm.getDisplay(), rm);
		}
		viewListPanel.setListControl(list);
		if (itemList.getSize() > 0) {
			UIList uiList = (UIList) viewListPanel.getListControl();
			uiList.setSelectedIndex(0);
		}
	}
	
	private UITabbedPane getTabs() {
		ReferenceDisplayList list = SecurityServices.getAppTypes(ApplicationFramework.get(selection.listApplicationRefId));
		Enumeration e1 = list.elements();
		while (e1.hasMoreElements()) {
			ReferenceDisplay rd = (ReferenceDisplay) e1.nextElement();
			if (rd.getRefId() ==0) {
				rd.setDisplay(ReferenceServices.getMsg(FormButtonTextFramework.DEFAULT));
			}
			tabbedPane.addTab(rd.getDisplay(),createTab(rd.getRefId(), selection.listApplicationRefId));
		}
		//TODO need to handle no rows in application control table
		return tabbedPane;
	}
	
	private void displayControlList() {
		UIList controlList = new UIList(new DefaultListModel());
		//TODO this throws a debug message if control size is 0 
		ApplicationControlList controls = SecurityServices.getAppControls(ViewFramework.get(selection.listViewRefId), selection.listAppTypeRefId, ApplicationFramework.get(selection.listApplicationRefId), true);
		controls.sortList();
		Enumeration e1 = controls.elements();
		while (e1.hasMoreElements()) {
			ApplicationControlModel cm = (ApplicationControlModel) e1.nextElement();
			controlList.addItem(cm.getControlText() + " " + GUI.toUpperCase(cm.getControlTypeRefDisplay()), cm);
		}
		if (selection.panelAddRemoveList.size() ==0 ) {
			//TODO need more than this!
			PanelAddRemove controlPanel = new PanelAddRemove();
			controlPanel.setUserObject(new Integer(0));
			selection.panelAddRemoveList.add(controlPanel);
		}
		PanelAddRemove controlPanel = (PanelAddRemove) selection.panelAddRemoveList.elementAt(selection.tabIndex);
		controlPanel.setListControl(controlList);
		if (controls.size() > 0) {
			controlList.setSelectedIndex(0);
		}
		controlPanel.validate();
	}
	
	public Component createTab(int appTypeRefId, int applicationRefId) {
		PanelAddRemove controlPanel = new PanelAddRemove();
		//TODO swap out text
		addControlPanelListeners(controlPanel);
		controlPanel.setUserObject(new Integer(appTypeRefId));
		selection.panelAddRemoveList.add(controlPanel);
		JPanel p = new JPanel(new BorderLayout());
		p.add(controlPanel, BorderLayout.CENTER);
		return p;
	}
	
	private void addControlPanelListeners(final PanelAddRemove controlPanel) {
		controlPanel.getPrintButton().setText(ReferenceServices.getMsg(FormButtonTextFramework.MOVETOP));
		controlPanel.getPreviewButton().setText(ReferenceServices.getMsg(FormButtonTextFramework.MOVEUP));
		controlPanel.getRight3Button().setText(ReferenceServices.getMsg(FormButtonTextFramework.MOVEDOWN));
		controlPanel.getRight3Button().setVisible(true);
		controlPanel.getRight4Button().setText(ReferenceServices.getMsg(FormButtonTextFramework.MOVEBOTTOM));
		controlPanel.getRight4Button().setVisible(true);
		controlPanel.getPrintButton().addActionListener(new UIActionListener(frame) {
			public void actionExecuted(ActionEvent e) throws Exception {
				int position = controlPanel.getSelectedIndex();
				UIList uiList = (UIList) controlPanel.getListControl();
				if (uiList.moveTop(position) && updateSequence(uiList)) {
					uiList.setSelectedIndex(0);
				}
			}
		});
		controlPanel.getPreviewButton().addActionListener(new UIActionListener(frame) {
			public void actionExecuted(ActionEvent e) throws Exception {
				int position = controlPanel.getSelectedIndex();
				UIList uiList = (UIList) controlPanel.getListControl();
				if (uiList.moveUp(position) && updateSequence(uiList)) {
					uiList.setSelectedIndex(position-1);
				}
			}
		});
		controlPanel.getRight3Button().addActionListener(new UIActionListener(frame) {
			public void actionExecuted(ActionEvent e) throws Exception {
				int position = controlPanel.getSelectedIndex();
				UIList uiList = (UIList) controlPanel.getListControl();
				if (uiList.moveDown(position) && updateSequence(uiList)) {
					uiList.setSelectedIndex(position+1);
				}
			}
		});
		controlPanel.getRight4Button().addActionListener(new UIActionListener(frame) {
			public void actionExecuted(ActionEvent e) throws Exception {
				int position = controlPanel.getSelectedIndex();
				UIList uiList = (UIList) controlPanel.getListControl();
				if (uiList.moveBottom(position) && updateSequence(uiList)) {
					uiList.setSelectedIndex(uiList.getModel().getSize()-1);
				}
			}
		});
		controlPanel.getRemoveButton().addActionListener(new UIActionListener(frame) {
			public void actionExecuted(ActionEvent e) throws Exception {
				UIList uilist = (UIList) controlPanel.getListControl();
				ApplicationControlModel acm = (ApplicationControlModel) uilist.getSelectedValue();
				acm.setActiveInd(0);
				if (SecurityServices.UpdateApplicationControl(acm).getRowsUpdated()> 0) {
					DefaultListModel list = uilist.getListModel();
					list.removeElementAt(uilist.getSelectedIndex());
				}
			}			
		});
		controlPanel.getAddButton().addActionListener(new UIActionListener(frame) {
			public void actionExecuted(ActionEvent e) throws Exception {
				final UIList uilist = (UIList) controlPanel.getListControl();
				ApplicationControlModel acm = (ApplicationControlModel) uilist.getSelectedValue();
				final UIList dialogList = new UIList(new DefaultListModel());
				createReferenceList(dialogList, uilist);
				PanelOkCancel panelOkCancel = new PanelOkCancel();
				final UICenterSouthDialog dialog = new UICenterSouthDialog(frame, false);
				final ApplicationViewModel avm = SecurityServices.getApplicationView(acm.getApplicationViewId());
				dialog.setTitle(ReferenceServices.getMsg(FormButtonTextFramework.ADDVIEW) + avm.getViewRefDisplay() + " - " + avm.getApplicationRefDisplay());
				dialog.getSouthPanel().add(panelOkCancel, BorderLayout.CENTER);
				dialog.getCenterPanel().add(new JScrollPane(dialogList), BorderLayout.CENTER);
				panelOkCancel.getCmdOk().addActionListener(new UIActionListener(frame) {
					public void actionExecuted(ActionEvent e) throws Exception {
						addApplicationControls(avm, dialogList.getSelectedValues());
						dialog.dispose();
					}
				});
				panelOkCancel.getCmdCancel().addActionListener(new UIActionListener(frame) {
					public void actionExecuted(ActionEvent e) throws Exception {
						dialog.dispose();
					}
				});
				dialog.setVisible(true);
			}			
		});
		final ActionListener propertiesAction = new UIActionListener(frame) {
			public void actionExecuted(ActionEvent e) throws Exception {
				UIList uilist = (UIList) controlPanel.getListControl();
				final ApplicationControlModel acm = (ApplicationControlModel) uilist.getSelectedValue();
				final UIItem selectedItem = uilist.getSelectedItem(); 
				ApplicationControlFormController acfc = new ApplicationControlFormController(frame);
				acfc.setActionListener(new UIActionListener(frame){
					public void actionExecuted(ActionEvent e) throws Exception {
						selectedItem.setDisplay(acm.getControlText());		
					}
				});
				acfc.start(acm);
			}			
		};
		controlPanel.getPropertiesButton().addActionListener(propertiesAction);
	}
	
	private void createReferenceList(UIList dialogList, UIList uilist) {
		ReferenceModel rm = ReferenceServices.getReference(selection.listApplicationRefId);
		ReferenceDisplayList list = ReferenceServices.getDisplayList(ReferenceGroup.Controls, rm.getDisplayCode(), false);
		Enumeration e2 = list.elements();
		while (e2.hasMoreElements()) {
			ReferenceDisplay rd = (ReferenceDisplay) e2.nextElement();
			boolean inList = false;
			for (int i=0; i<uilist.getListModel().size(); i++) {
				UIItem item = (UIItem) uilist.getListModel().elementAt(i);
				ApplicationControlModel acm = (ApplicationControlModel) item.getUserObject();
				if (acm.getControlRefId() == rd.getRefId()) {
					inList = true;
					break;
				}
			}
			if (!inList) {
				ApplicationControlModel m = new ApplicationControlModel();
				//m.setApplicationRefId(selection.listApplicationRefId);
				//m.setAppTypeRefId(selection.listAppTypeRefId);
				//m.setViewRefId(selection.listViewRefId);
				m.setControlRefId(rd.getRefId());
				m.setControlText(rd.getDisplay());
				dialogList.addItem(rd.getDisplay(), m);
			}
		}		
		ApplicationControlModel m = new ApplicationControlModel();
		//m.setApplicationRefId(selection.listApplicationRefId);
		//m.setAppTypeRefId(selection.listAppTypeRefId);
		//m.setViewRefId(selection.listViewRefId);
		m.setControlTypeRefId(ControlTypeGroup.UITAB);
		m.setControlText(ReferenceServices.getMsg(FormButtonTextFramework.NEWTAB));
		dialogList.addItem(ReferenceServices.getMsg(FormButtonTextFramework.NEWTAB), m);
		
		ApplicationControlModel m2 = new ApplicationControlModel();
		m2.updateWith(m);
		m2.setControlTypeRefId(ControlTypeGroup.UISEPARATOR);
		m2.setControlText(ReferenceServices.getMsg(FormButtonTextFramework.NEWSEPARATOR));
		dialogList.addItem(ReferenceServices.getMsg(FormButtonTextFramework.NEWSEPARATOR), m);
	}
	
	private void addApplicationControls(ApplicationViewModel avm, Object[] objects) {
		if (objects != null) {
			ApplicationControlList acl = new ApplicationControlList();
			for (int i=0; i< objects.length; i++) {
				ApplicationControlModel acm = (ApplicationControlModel) objects[i];
				acm.setApplicationViewId(avm.getApplicationViewId());
				acl.add(acm);
			}
			SecurityServices.UpdateApplicationControl(acl);
			new TimedAction(0.25) {
				public void executeTask() {
					displayControlList();
				}
			};
		}
	}
	
	private boolean updateSequence(UIList uilist) {
		ListModel list = uilist.getListModel();
		ApplicationControlList acl = new ApplicationControlList();
		for (int i=0; i< list.getSize(); i++) {
			//TODO disaster cant tell if list has UIItem or Acm
			UIItem item = (UIItem) list.getElementAt(i);
			ApplicationControlModel acm = (ApplicationControlModel) item.getUserObject(); 
			acm.setDisplaySequence(i);
			acl.add(acm);
		}
		return SecurityServices.UpdateApplicationControl(acl).getRowsUpdated() > 0;
	}

}
