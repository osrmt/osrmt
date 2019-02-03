package com.osrmt.apps.swingApp.wizards;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;

import com.osframework.appclient.services.ReferenceServices;
import com.osframework.appclient.services.SecurityServices;
import com.osframework.appclient.ui.common.IReceiveMessage;
import com.osframework.appclient.ui.common.ISApplicationMediator;
import com.osframework.appclient.ui.common.ISEvent;
import com.osframework.appclient.ui.common.UIContext;
import com.osframework.appclient.ui.components.MultiColumnList;
import com.osframework.appclient.ui.components.PanelAddRemove;
import com.osframework.appclient.ui.controls.UIJPanel;
import com.osframework.appclient.ui.controls.UIProperties;
import com.osframework.appclient.ui.listeners.DoubleClickListener;
import com.osframework.appclient.ui.listeners.UIActionListener;
import com.osframework.appclient.ui.listeners.UIListSelectionListener;
import com.osframework.framework.logging.Debug;
import com.osframework.modellibrary.common.ResultColumnException;
import com.osframework.modellibrary.reference.group.ApplicationFramework;
import com.osframework.modellibrary.reference.group.FormButtonTextFramework;
import com.osframework.modellibrary.reference.group.FormTitleFramework;
import com.osframework.modellibrary.reference.security.ApplicationControlList;
import com.osframework.modellibrary.reference.security.ApplicationUserList;
import com.osframework.modellibrary.reference.security.ApplicationUserModel;
import com.osrmt.apps.swingApp.system.UserFormController;
import com.osrmt.modellibrary.reference.group.ApplicationGroup;


public class UserAdminWizard {
	
	private JFrame frame;
	private MultiColumnList uiUserList = new MultiColumnList();
	private WizardScreen screen1;
	private WizardScreen screen2;
	private UserAdminWizard self = this;

	public UserAdminWizard(JFrame owner) throws HeadlessException {
		this.frame = owner;
	}
	
	public void start() {
		screen1 = getScreen1();
		screen2 = getScreen2();
		screen1.initialize(null, screen2);
		// override next being available - until selected
		screen1.getButtons().getCmdNext().setEnabled(false);
		uiUserList.addListSelectionListener(new UIListSelectionListener(frame){
			public void valueChangedExecuted(ListSelectionEvent e) throws Exception {
				screen1.getButtons().getCmdNext().setEnabled(true);
			}
		});
		uiUserList.addMouseListener(new DoubleClickListener() {
			public void call(ActionEvent me) {
				screen1.getButtons().getCmdNext().getActionListeners()[0].actionPerformed(me);
			}
		});
		ISApplicationMediator.getInstance().register(new IReceiveMessage() {

			public boolean receive(ISEvent event, Object value) throws Exception {
				if (event.equals(ISEvent.APPLIEDCHANGES) && value.equals(UIContext.contextUserWizard)) {
					screen2.getButtons().getCmdFinish().setEnabled(true);
					return true;
				}
				return false;
			}
			
		}, self);
		screen1.setVisible(true);
	}
	
	public WizardScreen getScreen1() {
		return new WizardScreen(frame) {
			private static final long serialVersionUID = 1L;

			/**
			 * Build the panel with a list of users
			 */
			public JPanel getCenterPanel() {
				return getUserListPanel();
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
						ApplicationUserModel m = (ApplicationUserModel) uiUserList.getSelectedRow();
						screen1.setUserObject(m);
						screen2.initialize(screen1, null);
						screen2.setTitle(ReferenceServices.getMsg(FormTitleFramework.USERMAINTENANCE) + ": " + m.getUserLogin());
						screen2.getButtons().getCmdFinish().setEnabled(false);
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
				return ReferenceServices.getMsg(FormButtonTextFramework.SELECTUSER);
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
				return getUserForm(getSelectedUser());
			}
			
			public ActionListener getFinishAction() {
				return new UIActionListener(frame) {
					public void actionExecuted(ActionEvent e) throws Exception {
						ISApplicationMediator.getInstance().deregister(self);
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
				return ReferenceServices.getMsg(FormTitleFramework.USERMAINTENANCE) + ": " + getSelectedUser().getUserLogin();
			}
			
		};
	}
	
	private ApplicationUserModel getSelectedUser() {
		if (screen1.getUserObject() != null) {
			return (ApplicationUserModel) screen1.getUserObject();
		} else {
			return new ApplicationUserModel();
		}
	}
	
	private JPanel getUserForm(ApplicationUserModel user) {
		UserFormController ufc = new UserFormController(frame);
		ufc.start(user, true);
		screen2.setMenuBar(ufc.getMenu());
		return ufc.getMainPanel();
	}
	
	/**
	 * Panel with user list and add button
	 * @return
	 */
	private JPanel getUserListPanel() {
		
		try {
			uiUserList.setTableModel(getUserList(), 60);
			
			PanelAddRemove panel = new PanelAddRemove(PanelAddRemove.NO_RIGHT_SIDE_BUTTONS);
			panel.getRemoveButton().setVisible(false);
			panel.getPropertiesButton().setVisible(false);
			addNewUserListener(uiUserList, panel.getAddButton());
			panel.setListControl(uiUserList);
			return panel;
		} catch (Exception e) {
			Debug.LogException(this, e);
			return new UIJPanel();
		}
	}
	
	private ApplicationUserList getUserList() throws ResultColumnException {
		ApplicationUserList users = SecurityServices.getAllUsers();
		ApplicationControlList acl1 = SecurityServices.getAppControlsByUser(ApplicationFramework.get(ApplicationGroup.USERSEARCHRESULTS));
		users.setColumnOrder(acl1);
		return users;
	}
	
	private void addNewUserListener(final MultiColumnList uiUserList, JButton addButton) {
		addButton.addActionListener(new UIActionListener(frame) {
			public void actionExecuted(ActionEvent e) throws Exception {
				UserFormController ufc = new UserFormController(frame);
				ApplicationUserModel newUser = new ApplicationUserModel();
				ufc.setOkAction(new UIActionListener(frame) {
					public void actionExecuted(ActionEvent e) throws Exception {
						uiUserList.setTableModel(getUserList(), 60);
						screen1.getButtons().getCmdFinish().setEnabled(true);
						screen1.getButtons().getCmdFinish().addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								screen1.dispose();
							}
						});
					}
				});
				ufc.start(newUser, false);				
			}
		});
	}

}
