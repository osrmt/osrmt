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
public class ApplicationControlFormUI extends UIJDialog {

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
	public ApplicationControlFormUI(JFrame frame) {
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
			LayoutUtility.addControl(rm, "DisplaySequence", builder, rm.getControlText(), getDisplaySequence());
			LayoutUtility.addControl(rm, "ControlText", builder, rm.getControlText(), getControlText());
			LayoutUtility.addControl(rm, "AppControlUserDefinedId", builder, rm.getControlText(), getAppControlUserDefinedId());
			LayoutUtility.addControl(rm, "ControlFormat", builder, rm.getControlText(), getControlFormat());
			LayoutUtility.addControl(rm, "SourceRefId", builder, rm.getControlText(), getSourceRefId());
			LayoutUtility.addControl(rm, "DefaultValue", builder, rm.getControlText(), getDefaultValue());
			LayoutUtility.addControl(rm, "LockedInd", builder, rm.getControlText(), getLockedInd());
			LayoutUtility.addControl(rm, "DisabledInd", builder, rm.getControlText(), getDisabledInd());
			LayoutUtility.addControl(rm, "RequiredInd", builder, rm.getControlText(), getRequiredInd());
			LayoutUtility.addControl(rm, "VisibleInd", builder, rm.getControlText(), getVisibleInd());
			LayoutUtility.addControl(rm, "InitScript", builder, rm.getControlText(), getInitScript());
			LayoutUtility.addControl(rm, "FocusLostScript", builder, rm.getControlText(), getFocusLostScript());
			LayoutUtility.addControl(rm, "FocusGainedScript", builder, rm.getControlText(), getFocusGainedScript());
			LayoutUtility.addControl(rm, "ImagePath", builder, rm.getControlText(), getImagePath());
			LayoutUtility.addControl(rm, "GrowHeight", builder, rm.getControlText(), getGrowHeight());
			LayoutUtility.addControl(rm, "ScrollpaneInd", builder, rm.getControlText(), getScrollpaneInd());
			LayoutUtility.addControl(rm, "UnitWidth", builder, rm.getControlText(), getUnitWidth());
			LayoutUtility.addControl(rm, "CreateDt", builder, rm.getControlText(), getCreateDt());
			LayoutUtility.addControl(rm, "CreateUserId", builder, rm.getControlText(), getCreateUserId());
			LayoutUtility.addControl(rm, "UpdateDt", builder, rm.getControlText(), getUpdateDt());
			LayoutUtility.addControl(rm, "UpdateUserId", builder, rm.getControlText(), getUpdateUserId());
			LayoutUtility.addControl(rm, "UpdateCount", builder, rm.getControlText(), getUpdateCount());
			LayoutUtility.addControl(rm, "ActiveInd", builder, rm.getControlText(), getActiveInd());
			LayoutUtility.addControl(rm, "SystemAssignedVersionNbr", builder, rm.getControlText(), getSystemAssignedVersionNbr());
			LayoutUtility.addControl(rm, "RecordTypeRefId", builder, rm.getControlText(), getRecordTypeRefId());
			LayoutUtility.addControl(rm, "ControlDescription", builder, rm.getControlText(), getControlDescription());
			LayoutUtility.addControl(rm, "ModelColumnRefId", builder, rm.getControlText(), getModelColumnRefId());
			LayoutUtility.addControl(rm, "ApplicationCustomControlId", builder, rm.getControlText(), getApplicationCustomControlId());
			LayoutUtility.addControl(rm, "ControlTypeRefId", builder, rm.getControlText(), getControlTypeRefId());
			LayoutUtility.addControl(rm, "UnitHeight", builder, rm.getControlText(), getUnitHeight());
			LayoutUtility.addControl(rm, "GrowWidth", builder, rm.getControlText(), getGrowWidth());
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
	private UIIntegerField displaySequence = null;
	private UITextField controlText = null;
	private UIIntegerField appControlUserDefinedId = null;
	private UITextField controlFormat = null;
	private UIComboBox sourceRefId = null;
	private UITextField defaultValue = null;
	private UIIndicatorField lockedInd = null;
	private UIIndicatorField disabledInd = null;
	private UIIndicatorField requiredInd = null;
	private UIIndicatorField visibleInd = null;
	private UIEditorPane initScript = null;
	private UIEditorPane focusLostScript = null;
	private UIEditorPane focusGainedScript = null;
	private UITextField imagePath = null;
	private UIDoubleField growHeight = null;
	private UIIndicatorField scrollpaneInd = null;
	private UIIntegerField unitWidth = null;
	private UIDateField createDt = null;
	private UIUserField createUserId = null;
	private UIDateField updateDt = null;
	private UIUserField updateUserId = null;
	private UIIntegerField updateCount = null;
	private UIIndicatorField activeInd = null;
	private UIIntegerField systemAssignedVersionNbr = null;
	private UIComboBox recordTypeRefId = null;
	private UITextField controlDescription = null;
	private UIComboBox modelColumnRefId = null;
	private ApplicationCustomControl applicationCustomControlId = null;
	private UIComboBox controlTypeRefId = null;
	private UIIntegerField unitHeight = null;
	private UIIntegerField growWidth = null;


	public UIIntegerField getDisplaySequence() {
		if (this.displaySequence == null) {
			this.displaySequence = new UIIntegerField();
			this.displaySequence.setBackground(Color.YELLOW);
		}
		return displaySequence;
	}

	public void setDisplaySequence(UIIntegerField control) {
		this.displaySequence = control;
	}

	public UITextField getControlText() {
		if (this.controlText == null) {
			this.controlText = new UITextField();
			this.controlText.setBackground(Color.YELLOW);
		}
		return controlText;
	}

	public void setControlText(UITextField control) {
		this.controlText = control;
	}

	public UIIntegerField getAppControlUserDefinedId() {
		if (this.appControlUserDefinedId == null) {
			this.appControlUserDefinedId = new UIIntegerField();
			this.appControlUserDefinedId.setBackground(Color.YELLOW);
		}
		return appControlUserDefinedId;
	}

	public void setAppControlUserDefinedId(UIIntegerField control) {
		this.appControlUserDefinedId = control;
	}

	public UITextField getControlFormat() {
		if (this.controlFormat == null) {
			this.controlFormat = new UITextField();
			this.controlFormat.setBackground(Color.YELLOW);
		}
		return controlFormat;
	}

	public void setControlFormat(UITextField control) {
		this.controlFormat = control;
	}

	public UIComboBox getSourceRefId() {
		if (this.sourceRefId == null) {
			this.sourceRefId = new UIComboBox();
			this.sourceRefId.setBackground(Color.YELLOW);
		}
		return sourceRefId;
	}

	public void setSourceRefId(UIComboBox control) {
		this.sourceRefId = control;
	}

	public UITextField getDefaultValue() {
		if (this.defaultValue == null) {
			this.defaultValue = new UITextField();
			this.defaultValue.setBackground(Color.YELLOW);
		}
		return defaultValue;
	}

	public void setDefaultValue(UITextField control) {
		this.defaultValue = control;
	}

	public UIIndicatorField getLockedInd() {
		if (this.lockedInd == null) {
			this.lockedInd = new UIIndicatorField();
			this.lockedInd.setBackground(Color.YELLOW);
		}
		return lockedInd;
	}

	public void setLockedInd(UIIndicatorField control) {
		this.lockedInd = control;
	}

	public UIIndicatorField getDisabledInd() {
		if (this.disabledInd == null) {
			this.disabledInd = new UIIndicatorField();
			this.disabledInd.setBackground(Color.YELLOW);
		}
		return disabledInd;
	}

	public void setDisabledInd(UIIndicatorField control) {
		this.disabledInd = control;
	}

	public UIIndicatorField getRequiredInd() {
		if (this.requiredInd == null) {
			this.requiredInd = new UIIndicatorField();
			this.requiredInd.setBackground(Color.YELLOW);
		}
		return requiredInd;
	}

	public void setRequiredInd(UIIndicatorField control) {
		this.requiredInd = control;
	}

	public UIIndicatorField getVisibleInd() {
		if (this.visibleInd == null) {
			this.visibleInd = new UIIndicatorField();
			this.visibleInd.setBackground(Color.YELLOW);
		}
		return visibleInd;
	}

	public void setVisibleInd(UIIndicatorField control) {
		this.visibleInd = control;
	}

	public UIEditorPane getInitScript() {
		if (this.initScript == null) {
			this.initScript = new UIEditorPane();
			this.initScript.setBackground(Color.YELLOW);
		}
		return initScript;
	}

	public void setInitScript(UIEditorPane control) {
		this.initScript = control;
	}

	public UIEditorPane getFocusLostScript() {
		if (this.focusLostScript == null) {
			this.focusLostScript = new UIEditorPane();
			this.focusLostScript.setBackground(Color.YELLOW);
		}
		return focusLostScript;
	}

	public void setFocusLostScript(UIEditorPane control) {
		this.focusLostScript = control;
	}

	public UIEditorPane getFocusGainedScript() {
		if (this.focusGainedScript == null) {
			this.focusGainedScript = new UIEditorPane();
			this.focusGainedScript.setBackground(Color.YELLOW);
		}
		return focusGainedScript;
	}

	public void setFocusGainedScript(UIEditorPane control) {
		this.focusGainedScript = control;
	}

	public UITextField getImagePath() {
		if (this.imagePath == null) {
			this.imagePath = new UITextField();
			this.imagePath.setBackground(Color.YELLOW);
		}
		return imagePath;
	}

	public void setImagePath(UITextField control) {
		this.imagePath = control;
	}

	public UIDoubleField getGrowHeight() {
		if (this.growHeight == null) {
			this.growHeight = new UIDoubleField();
			this.growHeight.setBackground(Color.YELLOW);
		}
		return growHeight;
	}

	public void setGrowHeight(UIDoubleField control) {
		this.growHeight = control;
	}

	public UIIndicatorField getScrollpaneInd() {
		if (this.scrollpaneInd == null) {
			this.scrollpaneInd = new UIIndicatorField();
			this.scrollpaneInd.setBackground(Color.YELLOW);
		}
		return scrollpaneInd;
	}

	public void setScrollpaneInd(UIIndicatorField control) {
		this.scrollpaneInd = control;
	}

	public UIIntegerField getUnitWidth() {
		if (this.unitWidth == null) {
			this.unitWidth = new UIIntegerField();
			this.unitWidth.setBackground(Color.YELLOW);
		}
		return unitWidth;
	}

	public void setUnitWidth(UIIntegerField control) {
		this.unitWidth = control;
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

	public UIComboBox getRecordTypeRefId() {
		if (this.recordTypeRefId == null) {
			this.recordTypeRefId = new UIComboBox();
			this.recordTypeRefId.setBackground(Color.YELLOW);
		}
		return recordTypeRefId;
	}

	public void setRecordTypeRefId(UIComboBox control) {
		this.recordTypeRefId = control;
	}

	public UITextField getControlDescription() {
		if (this.controlDescription == null) {
			this.controlDescription = new UITextField();
			this.controlDescription.setBackground(Color.YELLOW);
		}
		return controlDescription;
	}

	public void setControlDescription(UITextField control) {
		this.controlDescription = control;
	}

	public UIComboBox getModelColumnRefId() {
		if (this.modelColumnRefId == null) {
			this.modelColumnRefId = new UIComboBox();
			this.modelColumnRefId.setBackground(Color.YELLOW);
		}
		return modelColumnRefId;
	}

	public void setModelColumnRefId(UIComboBox control) {
		this.modelColumnRefId = control;
	}

	public ApplicationCustomControl getApplicationCustomControlId() {
		if (this.applicationCustomControlId == null) {
			this.applicationCustomControlId = new ApplicationCustomControl();
			this.applicationCustomControlId.setBackground(Color.YELLOW);
		}
		return applicationCustomControlId;
	}

	public void setApplicationCustomControlId(ApplicationCustomControl control) {
		this.applicationCustomControlId = control;
	}

	public UIComboBox getControlTypeRefId() {
		if (this.controlTypeRefId == null) {
			this.controlTypeRefId = new UIComboBox();
			this.controlTypeRefId.setBackground(Color.YELLOW);
		}
		return controlTypeRefId;
	}

	public void setControlTypeRefId(UIComboBox control) {
		this.controlTypeRefId = control;
	}

	public UIIntegerField getUnitHeight() {
		if (this.unitHeight == null) {
			this.unitHeight = new UIIntegerField();
			this.unitHeight.setBackground(Color.YELLOW);
		}
		return unitHeight;
	}

	public void setUnitHeight(UIIntegerField control) {
		this.unitHeight = control;
	}

	public UIIntegerField getGrowWidth() {
		if (this.growWidth == null) {
			this.growWidth = new UIIntegerField();
			this.growWidth.setBackground(Color.YELLOW);
		}
		return growWidth;
	}

	public void setGrowWidth(UIIntegerField control) {
		this.growWidth = control;
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
