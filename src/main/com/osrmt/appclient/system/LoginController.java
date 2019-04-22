package com.osrmt.appclient.system;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.*;
import java.util.Enumeration;

import javax.swing.*;

import java.io.*;
import com.osframework.framework.utility.ClientUtility;
import com.osframework.appclient.ui.common.*;
import com.osframework.appclient.ui.controls.*;
import com.osframework.appclient.ui.components.*;
import com.osframework.appclient.ui.listeners.KeyEnterListener;
import com.osframework.appclient.ui.listeners.UIActionListener;
import com.osframework.datalibrary.common.*;
import com.osframework.ejb.reference.security.*;
import com.osframework.framework.logging.*;
import com.osframework.framework.utility.*;
import com.osframework.appclient.services.*;
import com.osframework.modellibrary.reference.group.ApplicationFramework;
import com.osframework.modellibrary.reference.group.FormButtonTextFramework;
import com.osframework.modellibrary.reference.group.FormTitleFramework;
import com.osframework.modellibrary.reference.group.SystemInfoFramework;
import com.osframework.modellibrary.reference.group.SystemMessageFramework;
import com.osframework.modellibrary.reference.security.*;
import com.osrmt.GlobalConstants;
import com.osrmt.appclient.setting.*;
import com.osrmt.modellibrary.reference.group.*;
/**
 * Login Controller authenticates the user setting the parent
 * forms object to the users UserModel. 
 *
 */
public class LoginController extends LoginBaseController {

	private IParent parent;
	private UIComboBox connectionList;
	
	/**
	 * Parent form starts the FindPatient module to initiate a new search
	 * 
	 * @param parent
	 */
	public void start(IParent parent) {
		this.parent = parent;
		ui.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		GUI.registerDebug(ui,ui.getPanelStatusBar());
		// intialize the base controls
		//Application.setClientProperty(ClientProperty.loadClient());
		ApplicationUserModel defaultUser = new ApplicationUserModel();
		//defaultUser.setUserLogin(Application.getClientProperty().getUsername());
		//TODO this is a little messy redundant call initialize with ui
		ui.setControlColumns(1);
		ApplicationControlList controls = new ApplicationControlList();
		try {
			if (Db.getAccess().getConnProperty() == null) {
				throw new ConnectionXmlNotFoundException();
			}
			controls = SecurityServices.getAppControls(
					ApplicationFramework.get(ApplicationGroup.LOGIN), true);
			if (controls.size() == 0) {
				ConnectionProperty cp = Db.getAccess().getConnProperty();
				testConnection(cp);
				controls = SecurityServices.getAppControls(
						ApplicationFramework.get(ApplicationGroup.LOGIN), true);
				SecurityServices.clearSecurityCache();
			}
			super.initialize(controls, defaultUser);
			loadEnvironments(controls);
			// add custom controls and listeners
			addListeners();
			// show the form
			//TODO Size and location should be in properties
			ui.setTitle(ReferenceServices.getMsg(FormTitleFramework.LOGONTOOSRMT)
					+ " " + ReferenceServices.getMsg(SystemMessageFramework.VERSION) + " " + GlobalConstants.APP_VERSION);
			ui.setIconImage(GUI.getImage("osrmt.gif",this));
			setCenterLocation();
			ui.setVisible(true);
			SwingUtilities.invokeLater(new Runnable() {

				public void run() {
					UISplash.getInstance().setVisible(false);
				}
				
			});
			setFirstSelected();
			String lastUsername = ClientUtility.getProperty(ClientUtility.login_lastusername);
			if (lastUsername != null) {
				defaultUser.setUserLogin(lastUsername);
				ui.getUserName().setText(lastUsername);
			}
			if (defaultUser.getUserLogin() == null || defaultUser.getUserLogin().length() == 0) {
				ui.getUserName().requestFocus();
			} else {
				ui.getPassword().requestFocus();
			}
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			ex.printStackTrace();
			showError(ex);
		}
	}
	
	private void setFirstSelected() {
		try {
			ComboBoxModel cbm = connectionList.getModel();
			if (cbm.getSize() > 0) {
				String environment = cbm.getElementAt(0).toString();
				if (GUI.selectValue(connectionList, environment)) {
					connectionList.getEditor().setItem(environment);
					if (cbm.getSize() == 1) {
						connectionList.setEnabled(false);
					}
				}
			}

		} catch (Exception ex) {
			Debug.LogException(this, ex);
		}
	}
	
	private void setCenterLocation() {
		ui.setSize(308,363);//UIProperties.getDIALOG_SIZE_360_225());
        Dimension dim =
            Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int)(dim.getWidth() - ui.getWidth())/2;
        int y = (int)(dim.getHeight() - ui.getHeight())/2;
        ui.setLocation(x,y);

	}
	
	private void loadEnvironments(ApplicationControlList acl) {
		try {
			// If the connection failed allow the ability to browse
			connectionList = ui.getEnvironment();
			ConnectionList list = ConnectionList.loadConnections();
			connectionList.setModel(list);
			//TODO list.add(getBrowseConnection());
		} catch (Exception ex) {
			Debug.LogException(this, ex);
		}
	}
	
	/**
	 * Add the specific OK/Cancel listeners
	 *
	 */
	private void addListeners() {
		// Clicking OK with a selected patient will set the parents
		// form value to the PatientSearchModel selected
		// otherwise null
		ActionListener okAction = getOkAction();
		ui.getPanelOkCancel().addOkActionListener(okAction);
		ui.getPassword().addKeyListener(new KeyEnterListener(okAction));
		ui.getUserName().addKeyListener(new KeyEnterListener(okAction));
		// Clicking cancel simply disposes the form
		ui.getPanelOkCancel().addCancelActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}	
		});
	}
	
	private ActionListener getOkAction() {
		return new UIActionListener(ui) {
			public void actionExecuted(ActionEvent e) {
				try {
					try {
						if (!GUI.selectValue(connectionList, connectionList.getEditor().getItem().toString())) {
							connectionList.getEditor().setItem("");
						}
					} catch (Exception ex) {
						Debug.LogException(this, ex);
						connectionList.getEditor().setItem("");
					}
					final ConnectionProperty cp = (ConnectionProperty) connectionList.getSelectedItem();
					testConnection(cp);
					ApplicationUserModel user1 = getApplicationUser();
					AuthenticationSetting.initialize();
					final ApplicationUserModel user = SecurityServices.authenticate(user1, true, AuthenticationSetting.isLdap());
					if (user != null) {
						ClientUtility.setProperty(ClientUtility.login_lastusername, user.getUserLogin());
						if (user.isPasswordReset()) {
							ChangePasswordFormController cpc = new ChangePasswordFormController(ui);
							cpc.start(user);
							cpc.setOklistener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									// set application user
									parent.setValue(user);
									GUI.unregisterDebug(ui);
									ui.dispose();								
								}
							});
						} else {
							// set application user
							parent.setValue(user);
							GUI.unregisterDebug(ui);
							ui.setDefaultCloseOperation(javax.swing.JFrame.DO_NOTHING_ON_CLOSE);
							ui.dispose();
						}
						DataFormatSetting.initialize();
					} else {
						ui.getPassword().setEnabled(true);
						ui.getPassword().selectAll();
					}
				} catch (Exception e1) {
					ui.getPassword().setEnabled(true);
					e1.printStackTrace();
					showError (e1);
					Debug.LogException(this, e1);
				}
			}	
		};
	}
	
	private void showError(Exception ex) {
		JPanel panel = new JPanel(new BorderLayout());
		JEditorPane pane = new JEditorPane();
		pane.setText(getStackTrace(ex));
		pane.setCaretPosition(0);
		panel.add(new JScrollPane(pane), BorderLayout.CENTER);
		JFrame frame = new JFrame();
		frame.setSize(640, 320);
		frame.setLocation(150, 150);
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	private void testConnection(ConnectionProperty cp) throws Exception {
		if (cp.isJ2EE()) { 
			return;
		}
		Db.setConnection(cp);
		if (Db.countTable("reference_group") == 0) {
			throw new EmptyReferenceTableDatabaseNotInitializedException();
		}
		
	}
	
	public static String getStackTrace( Throwable t ) 
    { 
	    StringWriter sw = new StringWriter(); 
	    PrintWriter  pw = new PrintWriter( sw, true ); 
	    t.printStackTrace( pw ); 
	    String s = sw.toString(); 
	    pw.close(); 
	    return s; 
    } 


}