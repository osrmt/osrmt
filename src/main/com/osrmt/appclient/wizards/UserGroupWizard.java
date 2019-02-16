package com.osrmt.appclient.wizards;

import java.awt.*;
import java.awt.event.*;
import java.awt.print.*;
import java.util.*;

import javax.print.*;
import javax.swing.*;
import javax.swing.event.*;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.osframework.appclient.services.*;
import com.osframework.appclient.ui.common.*;
import com.osframework.appclient.ui.listeners.*;
import com.osframework.appclient.ui.components.*;
import com.osframework.appclient.ui.controls.*;
import com.osframework.framework.logging.Debug;
import com.osframework.framework.utility.*;
import com.osframework.modellibrary.common.*;
import com.osframework.modellibrary.reference.common.*;
import com.osframework.modellibrary.reference.security.*;
import com.osframework.modellibrary.reportwriter.*;
import com.osframework.modellibrary.system.*;
import com.osrmt.appclient.services.ReqReferenceServices;
import com.osrmt.appclient.system.*;
import com.osrmt.modellibrary.reference.group.*;
import com.osframework.modellibrary.reference.group.ApplicationFramework;

public class UserGroupWizard {
	
	private JFrame frame;
	private UIValueList uiGroupList = new UIValueList();
	private UIValueList uiUserGroupList = new UIValueList();
	private WizardScreen screen1;
	private WizardScreen screen2;
	private WizardScreen screen3;
	private String selectedGroup = null;

	public UserGroupWizard(JFrame owner) throws HeadlessException {
		this.frame = owner;
	}
	
	
	public void start() {
		screen1 = getScreen1();
		screen2 = getScreen2();
		screen3 = getScreen3();
		screen1.initialize(null, screen2);
		// override next being available - until selected
		screen1.getButtons().getCmdNext().setEnabled(false);
		uiGroupList.addListSelectionListener(new UIListSelectionListener(frame){
			public void valueChangedExecuted(ListSelectionEvent e) throws Exception {
				screen1.getButtons().getCmdNext().setEnabled(true);
				if (uiGroupList.getSelectedRow() instanceof ReferenceModel) {
					selectedGroup = ((ReferenceModel) uiGroupList.getSelectedRow()).getDisplay();
				}
			}
		});
		uiGroupList.addMouseListener(new DoubleClickListener() {
			public void call(ActionEvent me) {
				screen1.getButtons().getCmdNext().getActionListeners()[0].actionPerformed(me);
			}
		});
		uiUserGroupList.addListSelectionListener(new UIListSelectionListener(frame){
			public void valueChangedExecuted(ListSelectionEvent e) throws Exception {
				screen2.getButtons().getCmdNext().setEnabled(true);
			}
		});
		uiUserGroupList.addMouseListener(new DoubleClickListener() {
			public void call(ActionEvent me) {
				screen2.getButtons().getCmdNext().getActionListeners()[0].actionPerformed(me);
			}
		});
		screen1.setVisible(true);
	}
	
	public WizardScreen getScreen1() {
		return new WizardScreen(frame) {
			private static final long serialVersionUID = 1L;

			/**
			 * Build the panel with a list of users
			 */
			public JPanel getCenterPanel() {
				return getGroupPanel();
			}
			
			public ActionListener getFinishAction() {
				return null;
			}

			/**
			 * Store the selected user in this screen
			 */
			public ActionListener getNextAction() {
				return new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						ReferenceModel m = (ReferenceModel) uiGroupList.getSelectedRow();
						screen1.setUserObject(m);
						addScreen2Listeners();
						screen2.initialize(screen1, screen3);
						
					}
				};
			}

			public Dimension getSize() {
				return UIProperties.getWINDOW_SIZE_600_400();
			}
			
			public Point getLocation() {
				return UIProperties.getPoint200_200();
			}

			public String getTitle() {
				return ReferenceServices.getDisplay(
						FormTitleGroup.USERGROUPWIZARDSELECTGROUP);
			}
			
		};
	}
	
	public WizardScreen getScreen2() {
		return new WizardScreen(frame) {
			private static final long serialVersionUID = 1L;

			/**
			 * Build the panel with a list of users
			 */
			public JPanel getCenterPanel() {
				return getUserGroupListPanel();
			}
			
			public ActionListener getFinishAction() {
				return null;
			}

			/**
			 * Store the selected user in this screen
			 */
			public ActionListener getNextAction() {
				return new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						ApplicationUserGroupModel m = (ApplicationUserGroupModel) uiUserGroupList.getSelectedRow();
						screen2.setUserObject(m);
						screen3.initialize(screen2, null);						
					}
				};
			}

			public Dimension getSize() {
				return UIProperties.getWINDOW_SIZE_600_400();
			}
			
			public Point getLocation() {
				return UIProperties.getPoint200_200();
			}

			public String getTitle() {
				return ReferenceServices.getDisplay(
						FormTitleGroup.USERGROUPWIZARDSELECTUSER) + ": " + selectedGroup;
			}
			
		};
	}
	
	public WizardScreen getScreen3() {
		return new WizardScreen(frame) {
			private static final long serialVersionUID = 1L;

			/**
			 * Build the panel with a list of users
			 */
			public JPanel getCenterPanel() {
				return getUserGroupForm();
			}
			
			public ActionListener getFinishAction() {
				return new UIActionListener(frame) {
					public void actionExecuted(ActionEvent e) throws Exception {
						dispose();
					}
				};
			}

			/**
			 * Store the selected user in this screen
			 */
			public ActionListener getNextAction() {
				return null;
			}

			public Dimension getSize() {
				return UIProperties.getWINDOW_SIZE_600_400();
			}
			
			public Point getLocation() {
				return UIProperties.getPoint200_200();
			}

			public String getTitle() {
				return getSelectUserGroup().getUserGroupRefDisplay();
			}
			
		};
	}
	
	private ReferenceModel getSelectedReferenceGroup() {
		if (screen1.getUserObject() != null) {
			return (ReferenceModel) screen1.getUserObject();
		} else {
			return new ReferenceModel();
		}
	}
	
	private void addScreen2Listeners() {
		uiUserGroupList.addMouseListener(new DoubleClickListener() {
			public void call(ActionEvent me) {
				screen2.getButtons().getCmdNext().getActionListeners()[0].actionPerformed(me);
			}
		});
	}
	
	private ApplicationUserGroupModel getSelectUserGroup() {
		if (screen2.getUserObject() != null) {
			return (ApplicationUserGroupModel) screen2.getUserObject();
		} else {
			return new ApplicationUserGroupModel();
		}
	}
	
	private JPanel getGroupPanel() {
		try {
			uiGroupList.setTableModel(getGroupList(), 60);
			
			UIJPanel panel = new UIJPanel();
			panel.add(uiGroupList, BorderLayout.CENTER);
			return panel;
		} catch (Exception e) {
			Debug.LogException(this, e);
			return new UIJPanel();
		}
	}

	private JPanel getUserGroupListPanel() {
		try {
			uiUserGroupList.setTableModel(getUserGroupList(), 60);
			UIPanelListButton panel = new UIPanelListButton(UIPanelListButton.NO_RIGHT_SIDE_BUTTONS);;
			panel.getButtonB3().setVisible(false);//properties
			panel.getButtonB2().setVisible(true);//remove
			panel.getButtonB1().setVisible(true);//add
			panel.getButtonB1().addActionListener(addNewUserGroupListener(uiUserGroupList));
			panel.getButtonB2().addActionListener(addRemoveUserGroupListener(uiUserGroupList));
			panel.setListControl(uiUserGroupList);
			return panel;
		} catch (Exception e) {
			Debug.LogException(this, e);
			return new UIJPanel();
		}
	}
	

	/**
	 * Panel with user list and add button
	 * @return
	 */
	private JPanel getUserGroupForm() {
		
		try {
			ApplicationControlList acl = SecurityServices.getAppControlsByUser(ApplicationFramework.get(ApplicationGroup.USERGROUPFORM));
			ApplicationUserGroupModel rm = getSelectUserGroup();
			final ApplicationUserGroupModel updatedUserGroup = SecurityServices.getApplicationUserGroup(rm.getApplicationUserGroupId());
			JPanel panel = ControlPanel.getPanel(frame, acl, updatedUserGroup);

			final UIStandardPanel applyPanel = new UIStandardPanel();
			applyPanel.getCenterPanel().add(panel, BorderLayout.CENTER);
			applyPanel.getButtonPanel().initialize(UIPanelButton.APPLY);
			applyPanel.getButtonPanel().getCmdButton0().addActionListener(new UIActionListener(frame){
				public void actionExecuted(ActionEvent e) throws Exception {
					SecurityServices.updateApplicationUserGroup(updatedUserGroup);
					applyPanel.getButtonPanel().getCmdButton0().setEnabled(false);
				}
			});
			
			return applyPanel;
		} catch (Exception e) {
			Debug.LogException(this, e);
			return new UIJPanel();
		}
	}
	
	private ApplicationUserGroupList getUserGroupList() throws ResultColumnException {
		ApplicationUserGroupList list = SecurityServices.getUserGroup(getSelectedReferenceGroup().getRefId()); 
		ApplicationControlList acl1 = SecurityServices.getAppControlsByUser(ApplicationFramework.get(ApplicationGroup.USERGROUPSEARCHRESULTS));
		list.setColumnOrder(acl1);
		return list;
	}
	
	private ReferenceList getGroupList() throws ResultColumnException {
		ReferenceList list = ReferenceServices.getActiveReferenceList(ReferenceGroup.UserGroup);
		ApplicationControlList acl1 = SecurityServices.getAppControlsByUser(ApplicationFramework.get(ApplicationGroup.REFERENCESEARCHRESULTS));
		list.setColumnOrder(acl1);
		return list;
	}

	private ActionListener addNewUserGroupListener(final MultiColumnList uiRefList) {
		final UIStandardDialog dialog = new UIStandardDialog(frame);
		final ApplicationUserGroupModel addUserGroup = new ApplicationUserGroupModel();
		addUserGroup.setUserGroupRefId(getSelectedReferenceGroup().getRefId());
		return new UIActionListener(frame) {
			public void actionExecuted(ActionEvent e) throws Exception {
				dialog.setTitle(ReferenceServices.getDisplay(FormTitleGroup.USERGROUPADDNEW));
				dialog.getButtonPanel().getCmdButton0().addActionListener(new UIActionListener(frame) {
					public void actionExecuted(ActionEvent e) throws Exception {
						dialog.dispose();
					}
				});
				dialog.getButtonPanel().getCmdButton1().addActionListener(new UIActionListener(frame) {
					public void actionExecuted(ActionEvent e) throws Exception {
						SecurityServices.updateApplicationUserGroup(addUserGroup).getPrimaryKeyId();
						TimedAction action = new TimedAction(0.1) {
							public void executeTask() {
								try {
//									uiRefList.setTableModel(getReferenceList(), 60);
									screen2.initialize(screen1, screen3);
									screen2.invalidate();
									screen2.validate();
								} catch (Exception ex) {
//									Debug.LogException(this, ex);
								}
							};
						};
						screen2.getButtons().getCmdFinish().setEnabled(true);
						screen2.getButtons().getCmdFinish().addActionListener(new UIActionListener(frame) {
							public void actionExecuted(ActionEvent e) throws Exception {
								screen2.dispose();
							}
						});
						dialog.dispose();
					}
				});
				dialog.getCenterPanel().add(buildAddUserGroupControlPanel(addUserGroup), BorderLayout.CENTER);
				dialog.pack();
				dialog.setVisible(true);			
			}
		};
	}
	
	private ActionListener addRemoveUserGroupListener(final MultiColumnList uiUserGroupList) {
		return new UIActionListener(frame) {
			public void actionExecuted(ActionEvent e) {
				try {
					final ApplicationUserGroupModel m = (ApplicationUserGroupModel) uiUserGroupList.getSelectedRow();
					if (!m.isNew()) {
						m.setNotActive();
						SecurityServices.updateApplicationUserGroup(m);
						uiUserGroupList.setTableModel(getUserGroupList(), 60);
					}
				} catch (Exception ex) {
					Debug.LogException(this, ex);
				}
			}
		};
	}
	
	private JPanel buildAddUserGroupControlPanel(ApplicationUserGroupModel rm) {
		try {
			ApplicationControlList list = SecurityServices.getAppControlsByUser(ApplicationFramework.get(ApplicationGroup.USERGROUPFORM));
			JPanel panel = ControlPanel.getPanel(frame, list, rm);
			return panel;
		} catch (Exception ex) {
			return new JPanel();
		}
	}



}
