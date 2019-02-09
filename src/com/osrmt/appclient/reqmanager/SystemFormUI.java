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
package com.osrmt.appclient.reqmanager;

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
public class SystemFormUI extends UIJDialog {

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
	public SystemFormUI(JFrame frame) {
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
		DefaultFormBuilder builder = LayoutUtility.getDefaultBuilder(controlColumns, 80);
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.putClientProperty("jgoodies.noContentBorder", Boolean.TRUE);
		java.util.Enumeration e1 = controls.elements();
		while (e1.hasMoreElements()) {
			ApplicationControlModel rm = (ApplicationControlModel) e1.nextElement();
			LayoutUtility.addControl(rm, "User Defined", builder, rm.getControlText(), (Component) userDefinedList.get("" + rm.getAppControlUserDefinedId()));
			if (rm.getControlTypeRefDisplay().compareTo("Separator")==0) {
				builder.nextLine();
				builder.append("");
				builder.nextLine();
				builder.append(rm.getControlText());
				builder.nextLine();
			} else if (rm.getControlTypeRefDisplay().compareTo("Tab")==0){
				tabbedPane.addTab(rm.getControlText(),builder.getPanel());
				builder = LayoutUtility.getDefaultBuilder(controlColumns, 80);
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
