package com.osframework.appclient.ui.components;

import java.awt.*;
import java.awt.Component;
import java.awt.event.*;

import javax.swing.JPanel;
import javax.swing.JDialog;
import javax.swing.JTabbedPane;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.factories.Borders;
import com.osframework.appclient.ui.common.*;
import com.osframework.appclient.ui.controls.UIEditorPane;
import com.osframework.appclient.ui.controls.UIJDialog;
import com.osframework.appclient.ui.controls.UIProperties;
import com.osframework.appclient.ui.controls.UITextField;
import com.osframework.framework.logging.Debug;
import com.osframework.modellibrary.reference.security.ApplicationControlList;
import com.osframework.modellibrary.reference.security.ApplicationControlModel;
import com.osframework.modellibrary.system.RecordFileModel;

import javax.swing.*;
import javax.swing.border.Border;

public class UIInputBox extends UIJDialog {

	private JPanel jContentPane = null;
	private JPanel panelCenter = null;
	private JPanel panelSouth = null;
	private PanelOkCancel panelOkCancel = null;
	private static final long serialVersionUID = 1L;
	private UIEditorPane editorControl = null;
	private UITextField textControl = null;
	private ActionListener okAction = null;
	
	/**
	 * This is the default constructor
	 */
	public UIInputBox(ActionListener okActionListener, String title, JFrame frame, String label, String defaultText, Dimension d, boolean multiLine) {
		super(frame,title, true);
		if (d == null) {
			d = new Dimension(340,120);
		}
		okAction = okActionListener;
		initialize(d);
		addControls(label, defaultText, multiLine);
		addListeners();
	}

	/**
	 * This is the default constructor
	 */
	public UIInputBox(ActionListener okActionListener, String title, JDialog frame, String label, String defaultText, Dimension d, boolean multiLine) {
		super(frame,title, true);
		okAction = okActionListener;
		if (d == null) {
			d = new Dimension(340,120);
		}
		initialize(d);
		addControls(label, defaultText, multiLine);
		addListeners();
	}

	/**
	 * This is the default constructor
	 */
	public UIInputBox(ActionListener okActionListener, String title, JDialog frame, String label) {
		this(okActionListener, title, frame, label, null, null, false);
	}
	
	/**
	 * This is the default constructor
	 */
	public UIInputBox(ActionListener okActionListener, String title, JFrame frame, String label) {
		this(okActionListener, title, frame, label, null, null, false);
	}
	
	public void setOkActionListener(ActionListener action) {
		this.okAction = action;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize(Dimension d) {
		this.setSize(d);
		this.setContentPane(getJContentPane());
		this.setLocation(200,200);
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
			panelCenter.add(getPanelSouth(), java.awt.BorderLayout.SOUTH);
		}
		return panelCenter;
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
			panelSouth.setPreferredSize(new java.awt.Dimension(40,40));
			panelSouth.add(getPanelOkCancel(), java.awt.BorderLayout.CENTER);
		}
		return panelSouth;
	}

	/**
	 * This method initializes panelOkCancel	
	 * 	
	 * @return com.osframework.appclient.ui.components.PanelOkCancel	
	 */
	private PanelOkCancel getPanelOkCancel() {
		if (panelOkCancel == null) {
			panelOkCancel = new PanelOkCancel();
		}
		return panelOkCancel;
	}
	
	private void addControls(String label, String defaultText, boolean multiLine) {
		DefaultFormBuilder builder = LayoutUtility.getDefaultBuilder(1, 0);
		
		
		if (multiLine) {
			LayoutUtility.addControl(1,1,1,builder, label, getEditorControl(defaultText));
		} else {
			UITextField textField = getTextControl(defaultText);
			LayoutUtility.addControl(builder, label, textField);
		}
		
		this.getPanelCenter().add(builder.getPanel(), BorderLayout.CENTER);
	}
	
	public String getInputText() {
		if (editorControl != null) {
			return editorControl.getText();
		} else {
			return textControl.getText();
		}
	}
	
	private void addListeners() {
		this.getPanelOkCancel().addOkActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				okAction.actionPerformed(e);
				dispose();
			}
			
		});
		this.getTextControl("").addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				okAction.actionPerformed(e);
				dispose();
			}
			
		});
		this.getPanelOkCancel().addCancelActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
			
		});
	}
	
	private UIEditorPane getEditorControl(String defaultText) {
		try {
			editorControl = new UIEditorPane();
			editorControl.setBorder(Borders.DIALOG_BORDER);
			editorControl.setText(defaultText);
			return editorControl;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			return new UIEditorPane();
		}
	}

	private UITextField getTextControl(String defaultText) {
		try {
			if (textControl == null) {
				textControl = new UITextField(10);
				textControl.setText(defaultText);
			}
			return textControl;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			return new UITextField();
		}
	}


}  //  @jve:decl-index=0:visual-constraint="10,10"
