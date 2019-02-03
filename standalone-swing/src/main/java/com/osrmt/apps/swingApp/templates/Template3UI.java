package com.osrmt.apps.swingApp.templates;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.osframework.appclient.ui.components.PanelOkCancel;
import com.osframework.appclient.ui.controls.UIJDialog;
import com.osframework.appclient.ui.controls.UIJPanel;
import com.osframework.appclient.ui.controls.UIProperties;

/* comments */
public class Template3UI extends UIJDialog {

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
	public Template3UI(JFrame frame) {
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
			//controlPanel = 
		}
		return controlPanel;
	}
	
	public void addControls(){}

	public int getControlColumns() {
		return controlColumns;
	}

	public void setControlColumns(int controlColumns) {
		this.controlColumns = controlColumns;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
