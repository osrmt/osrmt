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
import com.jgoodies.forms.builder.*;
import com.jgoodies.forms.layout.*;
import com.osrmt.appclient.services.*;
import com.osframework.appclient.ui.common.*;
import com.osframework.appclient.ui.components.*;
import com.osframework.appclient.ui.controls.UIJFrame;
import com.osframework.modellibrary.common.*;
import com.osrmt.modellibrary.reference.group.*;
import com.osframework.modellibrary.reference.security.*;
import com.osframework.appclient.services.*;
import com.osrmt.appclient.common.*;
import com.osframework.appclient.ui.controls.*;

public class RequirementManagerUI extends UIJFrame {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	private JPanel panelCenter = null;
	private JPanel panelNorth = null;
	private JPanel panelSouth = null;
	private JSplitPane splitPane = null;
	private JPanel panelWest = null;
	private JPanel panelEast = null;

	private int controlColumns = 1;
	private JPanel controlPanel = null;
	
	/**
	 * This is the default constructor
	 */
	public RequirementManagerUI() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(UIProperties.getWINDOW_SIZE_640_480());
		this.setContentPane(getJContentPane());
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
			jContentPane.add(getPanelCenter(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes panelCenter	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanelCenter() {
		if (panelCenter == null) {
			panelCenter = new JPanel();
			panelCenter.setLayout(new BorderLayout());
			panelCenter.add(getPanelNorth(), java.awt.BorderLayout.NORTH);
			panelCenter.add(getPanelSouth(), java.awt.BorderLayout.SOUTH);
			panelCenter.add(getSplitPane(), java.awt.BorderLayout.CENTER);
		}
		return panelCenter;
	}

	/**
	 * This method initializes panelNorth	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanelNorth() {
		if (panelNorth == null) {
			panelNorth = new JPanel();
			panelNorth.setLayout(new BorderLayout());
			panelNorth.setPreferredSize(new java.awt.Dimension(32,32));
		}
		return panelNorth;
	}

	/**
	 * This method initializes panelSouth	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanelSouth() {
		if (panelSouth == null) {
			panelSouth = new JPanel();
			panelSouth.setLayout(new BorderLayout());
			panelSouth.setPreferredSize(new java.awt.Dimension(23,23));
		}
		return panelSouth;
	}

	/**
	 * This method initializes splitPane	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */
	private JSplitPane getSplitPane() {
		if (splitPane == null) {
			splitPane = new JSplitPane();
			splitPane.setDividerLocation(200);
			splitPane.setLeftComponent(getPanelWest());
			splitPane.setRightComponent(getPanelEast());
		}
		return splitPane;
	}

	/**
	 * This method initializes panelWest	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanelWest() {
		if (panelWest == null) {
			panelWest = new JPanel();
			panelWest.setLayout(new BorderLayout());
		}
		return panelWest;
	}

	/**
	 * This method initializes panelEast	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanelEast() {
		if (panelEast == null) {
			panelEast = new JPanel();
			panelEast.setLayout(new BorderLayout());
			panelEast.add(getControlPanel(), BorderLayout.CENTER);
		}
		return panelEast;
	}


	public JPanel getControlPanel() {
		if (controlPanel == null) {
			controlPanel = new JPanel();
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
	
	public JPanel getWestPanel() {
		return this.getPanelWest();
	}

	public JPanel getEastPanel() {
		return this.getPanelEast();
	}

	public JPanel getNorthPanel() {
		return this.getPanelNorth();
	}

	public JPanel getSouthPanel() {
		return this.getPanelSouth();
	}



	
}  //  @jve:decl-index=0:visual-constraint="10,10"
