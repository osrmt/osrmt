package com.osrmt.apps.swingApp.changecontrol;

import java.awt.event.ActionEvent;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.osframework.appclient.services.ReferenceServices;
import com.osframework.appclient.ui.common.ApplicationAction;
import com.osframework.appclient.ui.common.ApplicationActionList;
import com.osframework.appclient.ui.common.ControlState;
import com.osframework.appclient.ui.listeners.DoubleClickListener;
import com.osframework.appclient.ui.listeners.UIActionListener;
import com.osframework.framework.logging.Debug;
import com.osframework.modellibrary.reference.group.FormButtonTextFramework;
import com.osframework.modellibrary.reference.group.FormTitleFramework;
import com.osrmt.apps.swingApp.common.ApplicationObject;
import com.osrmt.apps.swingApp.services.IssueServices;
import com.osrmt.apps.swingApp.wizards.ReportWizard;
import com.osrmt.modellibrary.issue.IssueCriteria;
import com.osrmt.modellibrary.issue.IssueList;
import com.osrmt.modellibrary.issue.IssueModel;
import com.osrmt.modellibrary.reference.group.ActionGroup;
import com.osrmt.modellibrary.reference.group.IssueTypeGroup;

public class ChangeControlActions {
	
	private ChangeControlViewController controller;
	private JFrame frame;
	
	public ChangeControlActions(JFrame frame) {
		this.frame = frame;
	}

	public ApplicationActionList getActions(ChangeControlViewController ccvc) {
		controller = ccvc;
		
		ApplicationActionList actions = new ApplicationActionList();
		
		// FILE MENU
		actions.addApplicationAction(getFileNewIssue());
		actions.addApplicationAction(getFileRefresh());
		actions.addApplicationAction(getFilePrint());
		//actions.addApplicationAction(getFileImport());
		//actions.addApplicationAction(getFileExport());
		actions.addApplicationAction(getFileClose());

		// EDIT MENU
		actions.addApplicationAction(getEditItem());
		actions.addApplicationAction(getEditFind());
		
		
		return actions;
	}
	
	
	
	public ApplicationAction getEditItem() {
		return new ApplicationAction(ActionGroup.CHANGECONTROLEDIT,
			getIssueListSelected(),
		new DoubleClickListener() {
			public void call(ActionEvent me) {
				try {
					if (controller.getTableList().isRowSelected()) {						
						Object o = controller.getTableList().getSelectedRow();						
						if (o != null) {
							IssueModel m = (IssueModel) o;
							ChangeControlForm afc = new ChangeControlForm(frame);
							IssueModel am = IssueServices.getIssue(m.getIssueId());
							afc.setScrollList(controller.getTableList().getSelectedIndex(), 
									(IssueList) controller.getTableList().getResultList());
							afc.start(am, m);
						}
					}
				} catch (Exception ex) {
					Debug.LogException(this, ex);
				}
				resetSystemState();
			}
		});
	}
		
	
	public ApplicationAction getEditFind() {
		return new ApplicationAction(ActionGroup.CHANGECONTROLFIND,
				getAlwaysTrue(), 
		new UIActionListener(controller.ui) {
			public void actionExecuted(ActionEvent ae) {
				try {
					String searchString = JOptionPane.showInputDialog(frame,ReferenceServices.getMsg(FormButtonTextFramework.SEARCHANYPARTOFISSUES),
							ReferenceServices.getMsg(FormTitleFramework.SEARCH),JOptionPane.PLAIN_MESSAGE);
					IssueList list = IssueServices.getIssues(new IssueCriteria(), IssueTypeGroup.get(IssueTypeGroup.CHANGECONTROL));
					controller.setIssueList(issueSearch(list, searchString));
				} catch (Exception e) {
					Debug.LogException(this, e);
				}
				resetSystemState();
			}
		});
	}
	
	public IssueList issueSearch(IssueList list, String s) {
		IssueList searchList= new IssueList(); 
		Enumeration e1 = list.elements();
		while (e1.hasMoreElements()) {
			IssueModel m = (IssueModel) e1.nextElement();
			if (m.allToString().toLowerCase().contains(s)) {
				searchList.add(m);
			}
		}
		return searchList;
	}
	
	
	public ApplicationAction getFileNewIssue() {
		return new ApplicationAction(ActionGroup.CHANGECONTROLNEW,
				getAlwaysTrue(),
		new UIActionListener(controller.ui) {
		public void actionExecuted(ActionEvent ae) {
				ChangeControlForm afc = new ChangeControlForm(frame);
				afc.start(new IssueModel(), null);
		}
		});
	}
	
	public ApplicationAction getFileRefresh() {
		return new ApplicationAction(ActionGroup.CHANGECONTROLREFRESH,
				getProductOpenControlState(),
				 new UIActionListener(controller.ui) {
					public void actionExecuted(ActionEvent ae) throws Exception {
						controller.buildTree();
					}
				});
	}
	
	/*
	public ApplicationAction getFileImport() {
		return new ApplicationAction(ActionGroup.c,
				getProductOpenControlState(),
				 new UIActionListener(controller.ui) {
					public void actionExecuted(ActionEvent ae) throws Exception {
						IssueModel parentIssue = new IssueModel();
						if (controller.getReqTree().getSelectedNode() != null && controller.getReqTree().getSelectedNode().getUserObject() instanceof IssueModel) {
							final IssueModel a = (IssueModel) controller.getReqTree().getSelectedNode().getUserObject();
							parentIssue = a;
						}
						IssueListImportForm form = new IssueListImportForm(frame, parentIssue);						
						form.setVisible(true);
					}
				});
	}
	
	public ApplicationAction getFileExport() {
		return new ApplicationAction(ActionGroup.REQMGRFILEEXPORT,
				getProductOpenControlState(),
				 new UIActionListener(controller.ui) {
					public void actionExecuted(ActionEvent ae) throws Exception {
						//IssueListExportForm form = new IssueListExportForm(frame, controller.getCurrentIssueList());
						//form.setVisible(true);
					}
				});
	}
	*/
	
	public ApplicationAction getFilePrint() {
		return new ApplicationAction(ActionGroup.CHANGECONTROLPRINT,
				getIssueListSelected(),
				new UIActionListener(controller.ui) {
			public void actionExecuted(ActionEvent me) {
				try {
					IssueList list = new IssueList();
					Vector v = controller.getTableList().getSelectedRows();
					Enumeration e1 = v.elements();
					while (e1.hasMoreElements()) {
						IssueModel am = (IssueModel) e1.nextElement();
						list.add(am);
					}
					if (list.size() > 0) {
						ReportWizard wizard = new ReportWizard(list, controller.ui);
						wizard.start();
					}
				} catch (Exception ex) {
					Debug.LogException(this, ex);
				}
				resetSystemState();
			}
		});
	}
	
	
	public ApplicationAction getFileClose() {
		return new ApplicationAction(ActionGroup.CHANGECONTROLCLOSE,
				null,
				 new UIActionListener(controller.ui) {
					public void actionExecuted(ActionEvent ae) {
						try {
							controller.ui.setVisible(false);
						} catch (Exception ex) {
							Debug.LogException(this, ex);
						}
					}
				});
	}
	
		

	/**
	 * Remove any error messages
	 * Reset the toolbar
	 */
	public void resetSystemState() {
		try {
			Debug.displayGUIMessage("");
			controller.getToolBar().setEnabled();
			controller.getMenuBar().setEnabled();
		} catch (Exception ex) {
		}
	}
	
	private ControlState getProductOpenControlState() {
		return 	new ControlState() {
			public boolean getEnabled() {
				return ApplicationObject.getApplicationProductRefId()>0;
			}
		};
	}

	private ControlState getIssueListSelected() {
		return 	new ControlState() {
			public boolean getEnabled() {
				return controller.getTableList().isRowSelected();
			}
		};
	}
	
	private ControlState getAlwaysTrue() {
		return 	new ControlState() {
			public boolean getEnabled() {
				return true;
			}
		};
	}
	
	private boolean isTreeIssueSelected() {
		/*
		if (controller.getReqTree().isNodeSelected()) {
			Object o = controller.getReqTree().getSelectedObject();
			if (o != null && o instanceof IssueModel) {
				return true;
			}
		}
		*/
		return false;
	}

}
