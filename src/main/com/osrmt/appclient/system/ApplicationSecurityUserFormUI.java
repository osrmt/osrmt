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
public class ApplicationSecurityUserFormUI extends UIJDialog {

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
	public ApplicationSecurityUserFormUI(JFrame frame) {
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
		java.util.Enumeration e1 = controls.elements();
		while (e1.hasMoreElements()) {
			ApplicationControlModel rm = (ApplicationControlModel) e1.nextElement();
			LayoutUtility.addControl(rm, "ApplicationSecurityId", builder, rm.getControlText(), getApplicationSecurityId());
			LayoutUtility.addControl(rm, "TableKeyId", builder, rm.getControlText(), getTableKeyId());
			LayoutUtility.addControl(rm, "TableRefId", builder, rm.getControlText(), getTableRefId());
			LayoutUtility.addControl(rm, "ViewRefId", builder, rm.getControlText(), getViewRefId());
			LayoutUtility.addControl(rm, "AppTypeRefId", builder, rm.getControlText(), getAppTypeRefId());
			LayoutUtility.addControl(rm, "ApplicationRefId", builder, rm.getControlText(), getApplicationRefId());
			LayoutUtility.addControl(rm, "ReadOnlyInd", builder, rm.getControlText(), getReadOnlyInd());
			LayoutUtility.addControl(rm, "CreateDt", builder, rm.getControlText(), getCreateDt());
			LayoutUtility.addControl(rm, "CreateUserId", builder, rm.getControlText(), getCreateUserId());
			LayoutUtility.addControl(rm, "UpdateDt", builder, rm.getControlText(), getUpdateDt());
			LayoutUtility.addControl(rm, "UpdateUserId", builder, rm.getControlText(), getUpdateUserId());
			LayoutUtility.addControl(rm, "UpdateCount", builder, rm.getControlText(), getUpdateCount());
			LayoutUtility.addControl(rm, "ActiveInd", builder, rm.getControlText(), getActiveInd());
			LayoutUtility.addControl(rm, "SystemAssignedVersionNbr", builder, rm.getControlText(), getSystemAssignedVersionNbr());
			LayoutUtility.addControl(rm, "RecordTypeRefId", builder, rm.getControlText(), getRecordTypeRefId());
			LayoutUtility.addControl(rm, "User Defined", builder, rm.getControlText(), (Component) userDefinedList.get("" + rm.getAppControlUserDefinedId()));
			if (rm.getControlTypeRefDisplay().compareTo("Separator")==0) {
				builder.nextLine();
				builder.append("");
				builder.nextLine();
				builder.append(rm.getControlText());
				builder.nextLine();
			}
		}
		getControlPanel().add(builder.getPanel(), BorderLayout.CENTER);
	}

	public void addUserDefined(Component c, int id) {
			userDefinedList.put("" + id, c);
	}
	private java.util.Hashtable userDefinedList = new java.util.Hashtable();
	private UIIntegerField applicationSecurityId = null;
	private UIIntegerField tableKeyId = null;
	private UIIntegerField tableRefId = null;
	private UIIntegerField viewRefId = null;
	private UIIntegerField appTypeRefId = null;
	private UIIntegerField applicationRefId = null;
	private UIIntegerField readOnlyInd = null;
	private UIDateField createDt = null;
	private UIIntegerField createUserId = null;
	private UIDateField updateDt = null;
	private UIIntegerField updateUserId = null;
	private UIIntegerField updateCount = null;
	private UIIntegerField activeInd = null;
	private UIIntegerField systemAssignedVersionNbr = null;
	private UIIntegerField recordTypeRefId = null;


	public UIIntegerField getApplicationSecurityId() {
		if (this.applicationSecurityId == null) {
			this.applicationSecurityId = new UIIntegerField();
			this.applicationSecurityId.setBackground(Color.YELLOW);
		}
		return applicationSecurityId;
	}

	public void setApplicationSecurityId(UIIntegerField control) {
		this.applicationSecurityId = control;
	}

	public UIIntegerField getTableKeyId() {
		if (this.tableKeyId == null) {
			this.tableKeyId = new UIIntegerField();
			this.tableKeyId.setBackground(Color.YELLOW);
		}
		return tableKeyId;
	}

	public void setTableKeyId(UIIntegerField control) {
		this.tableKeyId = control;
	}

	public UIIntegerField getTableRefId() {
		if (this.tableRefId == null) {
			this.tableRefId = new UIIntegerField();
			this.tableRefId.setBackground(Color.YELLOW);
		}
		return tableRefId;
	}

	public void setTableRefId(UIIntegerField control) {
		this.tableRefId = control;
	}

	public UIIntegerField getViewRefId() {
		if (this.viewRefId == null) {
			this.viewRefId = new UIIntegerField();
			this.viewRefId.setBackground(Color.YELLOW);
		}
		return viewRefId;
	}

	public void setViewRefId(UIIntegerField control) {
		this.viewRefId = control;
	}

	public UIIntegerField getAppTypeRefId() {
		if (this.appTypeRefId == null) {
			this.appTypeRefId = new UIIntegerField();
			this.appTypeRefId.setBackground(Color.YELLOW);
		}
		return appTypeRefId;
	}

	public void setAppTypeRefId(UIIntegerField control) {
		this.appTypeRefId = control;
	}

	public UIIntegerField getApplicationRefId() {
		if (this.applicationRefId == null) {
			this.applicationRefId = new UIIntegerField();
			this.applicationRefId.setBackground(Color.YELLOW);
		}
		return applicationRefId;
	}

	public void setApplicationRefId(UIIntegerField control) {
		this.applicationRefId = control;
	}

	public UIIntegerField getReadOnlyInd() {
		if (this.readOnlyInd == null) {
			this.readOnlyInd = new UIIntegerField();
			this.readOnlyInd.setBackground(Color.YELLOW);
		}
		return readOnlyInd;
	}

	public void setReadOnlyInd(UIIntegerField control) {
		this.readOnlyInd = control;
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

	public UIIntegerField getCreateUserId() {
		if (this.createUserId == null) {
			this.createUserId = new UIIntegerField();
			this.createUserId.setBackground(Color.YELLOW);
		}
		return createUserId;
	}

	public void setCreateUserId(UIIntegerField control) {
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

	public UIIntegerField getUpdateUserId() {
		if (this.updateUserId == null) {
			this.updateUserId = new UIIntegerField();
			this.updateUserId.setBackground(Color.YELLOW);
		}
		return updateUserId;
	}

	public void setUpdateUserId(UIIntegerField control) {
		this.updateUserId = control;
	}

	public UIIntegerField getUpdateCount() {
		if (this.updateCount == null) {
			this.updateCount = new UIIntegerField();
			this.updateCount.setBackground(Color.YELLOW);
		}
		return updateCount;
	}

	public void setUpdateCount(UIIntegerField control) {
		this.updateCount = control;
	}

	public UIIntegerField getActiveInd() {
		if (this.activeInd == null) {
			this.activeInd = new UIIntegerField();
			this.activeInd.setBackground(Color.YELLOW);
		}
		return activeInd;
	}

	public void setActiveInd(UIIntegerField control) {
		this.activeInd = control;
	}

	public UIIntegerField getSystemAssignedVersionNbr() {
		if (this.systemAssignedVersionNbr == null) {
			this.systemAssignedVersionNbr = new UIIntegerField();
			this.systemAssignedVersionNbr.setBackground(Color.YELLOW);
		}
		return systemAssignedVersionNbr;
	}

	public void setSystemAssignedVersionNbr(UIIntegerField control) {
		this.systemAssignedVersionNbr = control;
	}

	public UIIntegerField getRecordTypeRefId() {
		if (this.recordTypeRefId == null) {
			this.recordTypeRefId = new UIIntegerField();
			this.recordTypeRefId.setBackground(Color.YELLOW);
		}
		return recordTypeRefId;
	}

	public void setRecordTypeRefId(UIIntegerField control) {
		this.recordTypeRefId = control;
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
