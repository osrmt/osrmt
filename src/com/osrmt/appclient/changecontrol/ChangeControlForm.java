package com.osrmt.appclient.changecontrol;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.*;

import com.osframework.modellibrary.common.*;
import com.osframework.modellibrary.reference.security.*;
import com.osframework.appclient.services.*;
import com.osframework.appclient.ui.common.ControlPanel;
import com.osframework.appclient.ui.common.GUI;
import com.osframework.appclient.ui.common.SwingWorker;
import com.osframework.appclient.ui.components.ButtonNextPrevious;
import com.osframework.appclient.ui.components.FormPref;
import com.osframework.appclient.ui.components.UIPanelButton;
import com.osframework.appclient.ui.components.UIStandardDialog;
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

import com.osrmt.appclient.common.ApplicationObject;
import com.osrmt.appclient.services.IssueServices;
import com.osrmt.appclient.services.RequirementServices;
import com.osrmt.modellibrary.reference.group.ApplicationGroup;
import com.osrmt.modellibrary.reference.group.FormButtonTextGroup;
import com.osrmt.modellibrary.reference.group.FormTitleGroup;
import com.osrmt.modellibrary.reference.group.IssueTypeGroup;
import com.osrmt.modellibrary.reference.group.RelationGroup;
import com.osrmt.modellibrary.reqmanager.*;
import com.osrmt.modellibrary.issue.*;

public class ChangeControlForm extends UIStandardDialog {
	
	private IssueModel issue = new IssueModel();
	private IssueModel cachedFromList = new IssueModel();
	private JFrame frame;
	private ControlPanel controlPanel;
	private ButtonNextPrevious buttonNextPrevious;
	final private ArrayList scrollList = new ArrayList();
	private static FormPref formPref = null;
	private ApplicationControlList controls = null;

	public ChangeControlForm(JFrame frame) {
		super(frame);
		this.frame = frame;
		initPreviousNext();
	}

	public ChangeControlForm(JFrame frame, boolean modal) {
		super(frame, modal);
		this.frame = frame;
	}
	
	public void start(IssueModel issue, IssueModel cachedIssueFromList) {
		this.issue = issue;
		this.cachedFromList = cachedIssueFromList;
		init();
		initializeApplication();
	}

	private void initPreviousNext() {
		try {
			super.getButtonPanel().initialize(UIPanelButton.ALLBUTTONS);
			buttonNextPrevious = new ButtonNextPrevious();
			super.getButtonPanel().getCmdButton1().setText(ReferenceServices.getDisplay(FormButtonTextFramework.APPLY));
			super.getButtonPanel().getCmdButton3().setText(ReferenceServices.getDisplay(FormButtonTextFramework.WIZARDBACK));
			super.getButtonPanel().getCmdButton3().setVisible(true);
			super.getButtonPanel().getCmdButton2().setText(ReferenceServices.getDisplay(FormButtonTextFramework.WIZARDNEXT));
			super.getButtonPanel().getCmdButton2().setVisible(true);
			this.buttonNextPrevious.init(super.getButtonPanel().getCmdButton3(),
					super.getButtonPanel().getCmdButton2(), getButtonPanel().getCmdButton1(), scrollList, getReloadListener());
		} catch (Exception ex) {
			Debug.LogException(this, ex);
		}
	}
	
	private ActionListener getReloadListener() {
		return new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if (buttonNextPrevious != null && scrollList != null) {
					if (scrollList.size() > buttonNextPrevious.getPointer()) {
						Integer issueId = (Integer) scrollList.get(buttonNextPrevious.getPointer());
						loadModelByPointer(issueId.intValue());
						buttonNextPrevious.setState(false);
					} 
				}
			}
		};
	}

	private void loadModelByPointer(int issueId) {
		IssueList list = (IssueList) ApplicationObject.getChangeControlViewController().getTableList().getResultList();
		Enumeration e1 = list.elements();
		while (e1.hasMoreElements()) {
			IssueModel cachedFromList = (IssueModel) e1.nextElement();
			IssueModel am = IssueServices.getIssue(cachedFromList.getIssueId());
			if (am.getIssueId() == issueId) {
				this.start(am, cachedFromList);
				return;
			}
		}
	}
	
	public void init() {
		if (formPref == null) {
			formPref = new FormPref(UIProperties.getPoint75_75(), UIProperties.getWINDOW_SIZE_800_600());
		}
		setLocation(formPref.getLocation());
		setSize(formPref.getSize());
		
		this.addComponentListener(new ComponentListener() {
			public void componentResized(ComponentEvent e) {
				formPref.setSize(e.getComponent().getSize());
			}
			public void componentMoved(ComponentEvent e) {
				formPref.setLocation(e.getComponent().getLocation());
			}
			public void componentShown(ComponentEvent e) {}
			public void componentHidden(ComponentEvent e) {}
		});
		addListeners();
	}
	
	public void addListeners() {
		super.getButtonPanel().getCmdButton2().setVisible(true);
		super.getButtonPanel().getCmdButton3().setVisible(true);
		//TODO refresh the tree on save
		ActionListener save = new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					if (!GUI.requiredFieldsPopulated(controls, issue)) {
						return;
					}
					if (!controlPanel.hasAppliedChanges()) {
						return;
					}
					if (!controlPanel.validateScript()) {
						return;
					}
					int updatedIssueId = IssueServices.UpdateIssue(issue, IssueTypeGroup.get(IssueTypeGroup.CHANGECONTROL)).getPrimaryKeyId();
					if (cachedFromList != null) {
						cachedFromList.updateWith(IssueServices.getIssue(issue.getIssueId()));
					} 
					
					updateList(updatedIssueId);
					getButtonPanel().getCmdButton1().setEnabled(false);
					getButtonPanel().getCmdButton0().setText(ReferenceServices.getDisplay(FormButtonTextGroup.CLOSE));
					buttonNextPrevious.setState(false);
				} catch (Exception ex) {
					Debug.LogException(this, ex);
				}
			}
		};
		ActionListener cancel = new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (getButtonPanel().getCmdButton0().getText().equals(ReferenceServices.getDisplay(FormButtonTextGroup.CLOSE))
						|| issue.getIssueId() == 0) {
					dispose();
				} else {
					loadModelByPointer(issue.getIssueId());
					buttonNextPrevious.setState(false);
				}
			}
		};
		getButtonPanel().getCmdButton0().addActionListener(cancel);
		getButtonPanel().getCmdButton1().addActionListener(save);
	}
	
	/**
	 * Add or update list
	 * 
	 * @param updatedArtifactId
	 */
	private void updateList(final int updatedIssueId) {
		
		SwingWorker sw = new SwingWorker(this) {
			protected void doNonUILogic() throws Exception {
				Thread.sleep(250);
				IssueModel m = IssueServices.getIssue(updatedIssueId);
				boolean found = false;
				if (ApplicationObject.getIssueList() != null) {
					Enumeration e1 = ApplicationObject.getIssueList().elements();
					while (e1.hasMoreElements()) {
						IssueModel am = (IssueModel) e1.nextElement();
						if (am.getIssueId() == m.getIssueId()) {
							am.updateWith(m);
							found = true;
							break;
						}
					}
					// else append
					if (!found) {
						setUserObject(m);
					}
				}

			}
			protected void doUIUpdateLogic() throws Exception {
				if (getUserObject() != null && getUserObject() instanceof IssueModel)  {
					IssueModel m = (IssueModel) getUserObject();
					ApplicationObject.getIssueList().add(m);
					ApplicationObject.getChangeControlViewController().getTableList().setTableModel(ApplicationObject.getIssueList(),80);							
				}
				ApplicationObject.getChangeControlViewController().getTableList().repaint();
			}
		};
		sw.start();
	}

	public void setScrollList(int index, IssueList issueList) {
		try {
			scrollList.clear();
			Enumeration e1 = issueList.elements();
			while (e1.hasMoreElements()) {
				IssueModel am = (IssueModel) e1.nextElement();
				scrollList.add(new Integer(am.getIssueId()));
			}
			buttonNextPrevious.setPointer(index);
			buttonNextPrevious.setState(false);
		} catch (Exception ex) {
			Debug.LogException(this, ex);
		}
	}
	
	/**
	 * @param args
	 */
	public void initializeApplication () {
		getButtonPanel().getCmdButton1().setEnabled(false);
		getButtonPanel().getCmdButton0().setText(ReferenceServices.getDisplay(FormButtonTextGroup.CLOSE));
		controls = SecurityServices.getAppControlsByUser(ApplicationFramework.get(ApplicationGroup.CHANGECONTROLFORM));
		setTitle(ReferenceServices.getDisplay(FormTitleGroup.CHANGECONTROL));
		if (!issue.isNew()) {
			setTitle(ReferenceServices.getDisplay(FormTitleGroup.CHANGECONTROL)
					+ " " + issue.getIssueName());
		}
		controlPanel = new ControlPanel(frame);
		if (controls.size() > 0) {
			ApplicationViewModel avm = SecurityServices.getApplicationView(controls.getFirst().getApplicationViewId());
			controlPanel.setInitScript(avm.getInitScript());
			controlPanel.setValidationScript(avm.getValidationScript());
		}
		controlPanel.setChangedListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if (controlPanel.isControlsEnabled()) {
					getButtonPanel().getCmdButton1().setEnabled(true);
				}
				getButtonPanel().getCmdButton2().setEnabled(false);
				getButtonPanel().getCmdButton3().setEnabled(false);
				getButtonPanel().getCmdButton0().setText(ReferenceServices.getDisplay(FormButtonTextGroup.CANCEL));
			}
		});
		controlPanel.initialize(controls, 2, issue);
		JPanel panel = controlPanel.getPanel();
		getCenterPanel().removeAll();
		getCenterPanel().add(panel, BorderLayout.CENTER);
		setVisible(true);
		FocusManager fm = FocusManager.getCurrentManager(); 
		fm.focusNextComponent(panel.getComponent(0));
	}
	


}

