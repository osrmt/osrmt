/*
    //usage

    Copyright (C) 2006  Aron Lancout Smith

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA

*/
package com.osrmt.appclient.system;

import java.awt.*;
import javax.swing.*;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import com.jgoodies.forms.builder.*;
import com.jgoodies.forms.layout.*;
import com.osrmt.appclient.common.*;

import javax.swing.JLabel;
import javax.swing.JTextField;

import com.osrmt.appclient.services.*;
import com.osframework.appclient.ui.common.*;
import com.osframework.appclient.ui.components.*;
import com.osframework.appclient.ui.controls.UIJFrame;
import com.osframework.modellibrary.common.*;
import com.osrmt.modellibrary.reference.group.*;
import com.osframework.modellibrary.reference.security.*;
import com.osframework.appclient.services.*;
import com.osframework.appclient.ui.controls.*;

/**
null
*/
public class UserFormUI extends UIJDialog {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JPanel panelNorth = null;
	private JPanel panelSouth = null;
	private JPanel panelCenter = null;
	private PanelOkCancel panelOkCancel = null;
	private UIJPanel panelStatusBar = null;
	private JPanel controlPanel = null;
	private int controlColumns = 2;

	/**
	 * This is the default constructor
	 */
	public UserFormUI(JFrame frame) {
		super(frame, false);
		initialize();
	}


	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(UIProperties.getWINDOW_SIZE_640_480());
		this.setPreferredSize(UIProperties.getWINDOW_SIZE_640_480());
		this.setContentPane(getJContentPane());
	}

	
	/**
	 * This method initializes panelNorth	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	public JPanel getPanelNorth() {
		if (panelNorth == null) {
			panelNorth = new JPanel();
			panelNorth.setLayout(new BorderLayout());
			panelNorth.setPreferredSize(new java.awt.Dimension(23,23));
		}
		return panelNorth;
	}

	/**
	 * This method initializes panelSouth	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	public JPanel getPanelSouth() {
		if (panelSouth == null) {
			panelSouth = new JPanel();
			panelSouth.setLayout(new BorderLayout());
			panelSouth.setPreferredSize(new java.awt.Dimension(43,43));
			panelSouth.add(getPanelOkCancel(), java.awt.BorderLayout.EAST);
			panelSouth.add(getPanelStatusBar(), java.awt.BorderLayout.CENTER);
		}
		return panelSouth;
	}

	/**
	 * This method initializes panelCenter	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	public JPanel getPanelCenter() {
		if (panelCenter == null) {
			panelCenter = new JPanel();
			panelCenter.setLayout(new BorderLayout());
		}
		return panelCenter;
	}

	/**
	 * This method initializes panelOkCancel	
	 * 	
	 * @return com.osrmt.appclient.ui.components.PanelOkCancel	
	 */
	public PanelOkCancel getPanelOkCancel() {
		if (panelOkCancel == null) {
			panelOkCancel = new PanelOkCancel();
		}
		return panelOkCancel;
	}

	/**
	 * This method initializes panelStatusBar	
	 * 	
	 * @return com.osrmt.appclient.ui.components.PanelStatusBar	
	 */
	public UIJPanel getPanelStatusBar() {
		if (panelStatusBar == null) {
			panelStatusBar = new UIJPanel();
		}
		return panelStatusBar;
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getPanelNorth(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getPanelSouth(), java.awt.BorderLayout.SOUTH);
			jContentPane.add(getPanelCenter(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}
	
	/**
	 * Create a standard label
	 * 
	 * @param text
	 * @return
	 */
	public JLabel getLabel(String text) {
		JLabel label = new JLabel(text);
		label.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		label.setPreferredSize(new java.awt.Dimension(100,20));
		return label;
	}
	
	public void setControlPanel(JPanel panel) {
		this.controlPanel = panel;
	}
	
	public JPanel getControlPanel() {
		if (controlPanel == null) {
			controlPanel = getPanelCenter();
		}
		return controlPanel;
	}
	
	
	public void addControls(ApplicationControlList controls) {
		DefaultFormBuilder builder = LayoutUtility.getDefaultBuilder(controlColumns, 0);
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.putClientProperty("jgoodies.noContentBorder", Boolean.TRUE);
		java.util.Enumeration e1 = controls.elements();
		while (e1.hasMoreElements()) {
			ApplicationControlModel rm = (ApplicationControlModel) e1.nextElement();
			LayoutUtility.addControl(rm, "LastName", builder, rm.getControlText(), getLastName());
			LayoutUtility.addControl(rm, "FirstName", builder, rm.getControlText(), getFirstName());
			LayoutUtility.addControl(rm, "PositionRefId", builder, rm.getControlText(), getPositionRefId());
			LayoutUtility.addControl(rm, "UserLogin", builder, rm.getControlText(), getUserLogin());
			LayoutUtility.addControl(rm, "Password", builder, rm.getControlText(), getPassword());
			LayoutUtility.addControl(rm, "ActiveInd", builder, rm.getControlText(), getActiveInd());
			LayoutUtility.addControl(rm, "UserId", builder, rm.getControlText(), getUserId());
			LayoutUtility.addControl(rm, "CreateDt", builder, rm.getControlText(), getCreateDt());
			LayoutUtility.addControl(rm, "CreateUserId", builder, rm.getControlText(), getCreateUserId());
			LayoutUtility.addControl(rm, "UpdateDt", builder, rm.getControlText(), getUpdateDt());
			LayoutUtility.addControl(rm, "UpdateUserId", builder, rm.getControlText(), getUpdateUserId());
			LayoutUtility.addControl(rm, "ChangePassword", builder, rm.getControlText(), getChangePassword());
			LayoutUtility.addControl(rm, "AssignForms", builder, rm.getControlText(), getAssignForms());
			LayoutUtility.addControl(rm, "User Defined", builder, rm.getControlText(), (Component) userDefinedList.get("" + rm.getAppControlUserDefinedId()));
			if (rm.getControlTypeRefDisplay().compareTo("Separator")==0) {
				builder.nextLine();
				builder.append("");
				builder.nextLine();
				builder.append(rm.getControlText());
				builder.nextLine();
			} else if (rm.getControlTypeRefDisplay().compareTo("Tab")==0){
				tabbedPane.addTab(rm.getControlText(),builder.getPanel());
				builder = LayoutUtility.getDefaultBuilder(controlColumns, 0);
			}
		}
		if (tabbedPane.getTabCount()==0) {
			tabbedPane.addTab("",builder.getPanel());
		}
		getControlPanel().add(tabbedPane, BorderLayout.CENTER);
	}

	public void addUserDefined(Component c, int id) {
			userDefinedList.put("" + id, c);
	}
	private java.util.Hashtable userDefinedList = new java.util.Hashtable();
	private UITextField lastName = null;
	private UITextField firstName = null;
	private UIComboBox positionRefId = null;
	private UITextField userLogin = null;
	private UIPasswordField password = null;
	private UIIndicatorField activeInd = null;
	private UIIntegerField userId = null;
	private UIDateField createDt = null;
	private UIUserField createUserId = null;
	private UIDateField updateDt = null;
	private UIUserField updateUserId = null;
	private ChangePasswordButton changePassword = null;
	private AssignFormsButton assignForms = null;


	public UITextField getLastName() {
		if (this.lastName == null) {
			this.lastName = new UITextField();
			this.lastName.setBackground(Color.YELLOW);
		}
		return lastName;
	}

	public void setLastName(UITextField control) {
		this.lastName = control;
	}

	public UITextField getFirstName() {
		if (this.firstName == null) {
			this.firstName = new UITextField();
			this.firstName.setBackground(Color.YELLOW);
		}
		return firstName;
	}

	public void setFirstName(UITextField control) {
		this.firstName = control;
	}

	public UIComboBox getPositionRefId() {
		if (this.positionRefId == null) {
			this.positionRefId = new UIComboBox();
			this.positionRefId.setBackground(Color.YELLOW);
		}
		return positionRefId;
	}

	public void setPositionRefId(UIComboBox control) {
		this.positionRefId = control;
	}

	public UITextField getUserLogin() {
		if (this.userLogin == null) {
			this.userLogin = new UITextField();
			this.userLogin.setBackground(Color.YELLOW);
		}
		return userLogin;
	}

	public void setUserLogin(UITextField control) {
		this.userLogin = control;
	}

	public UIPasswordField getPassword() {
		if (this.password == null) {
			this.password = new UIPasswordField();
			this.password.setBackground(Color.YELLOW);
		}
		return password;
	}

	public void setPassword(UIPasswordField control) {
		this.password = control;
	}

	public UIIndicatorField getActiveInd() {
		if (this.activeInd == null) {
			this.activeInd = new UIIndicatorField();
			this.activeInd.setBackground(Color.YELLOW);
		}
		return activeInd;
	}

	public void setActiveInd(UIIndicatorField control) {
		this.activeInd = control;
	}

	public UIIntegerField getUserId() {
		if (this.userId == null) {
			this.userId = new UIIntegerField();
			this.userId.setBackground(Color.YELLOW);
		}
		return userId;
	}

	public void setUserId(UIIntegerField control) {
		this.userId = control;
	}

	public UIDateField getCreateDt() {
		if (this.createDt == null) {
			this.createDt = new UIDateField();
			this.createDt.setBackground(Color.YELLOW);
		}
		this.createDt.setPreferredSize(new Dimension(100,20));
		return createDt;
	}

	public void setCreateDt(UIDateField control) {
		this.createDt = control;
	}

	public UIUserField getCreateUserId() {
		if (this.createUserId == null) {
			this.createUserId = new UIUserField();
			this.createUserId.setBackground(Color.YELLOW);
		}
		return createUserId;
	}

	public void setCreateUserId(UIUserField control) {
		this.createUserId = control;
	}

	public UIDateField getUpdateDt() {
		if (this.updateDt == null) {
			this.updateDt = new UIDateField();
			this.updateDt.setBackground(Color.YELLOW);
		}
		this.updateDt.setPreferredSize(new Dimension(100,20));
		return updateDt;
	}

	public void setUpdateDt(UIDateField control) {
		this.updateDt = control;
	}

	public UIUserField getUpdateUserId() {
		if (this.updateUserId == null) {
			this.updateUserId = new UIUserField();
			this.updateUserId.setBackground(Color.YELLOW);
		}
		return updateUserId;
	}

	public void setUpdateUserId(UIUserField control) {
		this.updateUserId = control;
	}

	public ChangePasswordButton getChangePassword() {
		if (this.changePassword == null) {
			this.changePassword = new ChangePasswordButton();
			this.changePassword.setBackground(Color.YELLOW);
		}
		return changePassword;
	}

	public void setChangePassword(ChangePasswordButton control) {
		this.changePassword = control;
	}

	public AssignFormsButton getAssignForms() {
		if (this.assignForms == null) {
			this.assignForms = new AssignFormsButton();
			this.assignForms.setBackground(Color.YELLOW);
		}
		return assignForms;
	}

	public void setAssignForms(AssignFormsButton control) {
		this.assignForms = control;
	}

	public JPanel getMainPanel() { 
		return getJContentPane();
	}



	public int getControlColumns() {
		return controlColumns;
	}

	public void setControlColumns(int controlColumns) {
		this.controlColumns = controlColumns;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
