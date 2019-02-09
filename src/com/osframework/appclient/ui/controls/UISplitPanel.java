package com.osframework.appclient.ui.controls;

import javax.swing.JPanel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import java.awt.BorderLayout;
import java.awt.Dimension;

public class UISplitPanel extends JPanel {

	private JPanel panelWest = null;
	private JPanel panelEast = null;
	private int dividerLocation = 200;
	private boolean topBottomSplit = false;
	/**
	 * This is the default constructor
	 */
	public UISplitPanel(JPanel westPanel, JPanel eastPanel) {
		this(westPanel, eastPanel, false, 200);
	}

	/**
	 * This is the default constructor
	 */
	public UISplitPanel(JPanel westPanel, JPanel eastPanel, boolean topBottomSplit, int dividerLocation) {
		super(new BorderLayout());
		this.dividerLocation = dividerLocation;
		this.topBottomSplit = topBottomSplit;
		this.panelWest = westPanel;
		this.panelEast = eastPanel;
		initialize();
	}

	
	
	public void setPanelEast(JPanel panelEast) {
		this.panelEast.removeAll();
		this.panelEast = panelEast;
		initialize();
	}

	public void setPanelWest(JPanel panelWest) {
		this.panelWest = panelWest;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.removeAll();
		this.setSize(UIProperties.getDIALOG_SIZE_450_330());
		int split = JSplitPane.HORIZONTAL_SPLIT;
		if (topBottomSplit) {
			split = JSplitPane.VERTICAL_SPLIT;
		}
		JSplitPane splitPane = new JSplitPane(split, getPanelWest(), getPanelEast());
		splitPane.setDividerLocation(dividerLocation);
		add(splitPane, BorderLayout.CENTER);
//		this.add(getPanelWest(), BorderLayout.CENTER);
//		this.add(getPanelEast(), BorderLayout.EAST);
//		getPanelEast().setPreferredSize(new Dimension(dividerLocation,100));
	}

	/**
	 * This method initializes panelWest	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	public JPanel getPanelWest() {
		if (panelWest == null) {
			panelWest = new JPanel();
		}
		return panelWest;
	}

	/**
	 * This method initializes panelEast	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	public JPanel getPanelEast() {
		if (panelEast == null) {
			panelEast = new JPanel();
		}
		return panelEast;
	}

}
