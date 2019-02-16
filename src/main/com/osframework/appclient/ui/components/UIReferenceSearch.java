package com.osframework.appclient.ui.components;

import java.awt.*;
import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import com.jgoodies.forms.builder.*;
import com.jgoodies.forms.layout.*;
import com.osframework.appclient.ui.common.*;
import com.osframework.appclient.ui.components.*;
import com.osframework.appclient.ui.controls.UIJDialog;
import com.osframework.modellibrary.common.*;
import com.osframework.modellibrary.reference.common.*;
import com.osframework.modellibrary.reference.security.*;
import com.osframework.appclient.services.*;
import com.osframework.appclient.ui.controls.*;
import com.osframework.appclient.ui.listeners.DoubleClickListener;
import com.osframework.framework.logging.Debug;


/* comments */
public class UIReferenceSearch extends UIJDialog {

	private static final long serialVersionUID = 1L;
	protected JPanel jContentPane = null;
	protected JPanel panelNorth = null;
	protected JPanel panelSouth = null;
	protected JPanel panelCenter = null;
	protected UIPanelStatusBar panelStatusBar = null;
	protected PanelOkCancel PanelOkCancel = null;
	protected int controlColumns = 1;
	protected String referenceGroup;
	protected UIList list = new UIList(new DefaultListModel());
	private ActionListener actionListener;

	public String getReferenceGroup() {
		return referenceGroup;
	}

	public void setReferenceGroup(String referenceGroup) {
		this.referenceGroup = referenceGroup;
	}

	/**
	 * This is the default constructor
	 */
	public UIReferenceSearch(JFrame parentFrame) {
		super(parentFrame, true);
		this.setLocation(200,200);
	}
	
	public void start(String referenceGroup, boolean singleSelection) {
		start(referenceGroup, referenceGroup, singleSelection);
	}

	public void start(String referenceGroup, String title, boolean singleSelection) {
		if (singleSelection) {
			list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}
		this.referenceGroup = referenceGroup;
		initialize(title);
		addControls();
	}

	/**
	 * This method initializes panelNorth	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	public JPanel getPanelNorth() {
		if (panelNorth == null) {
			panelNorth = new JPanel();
			panelNorth.setPreferredSize(new java.awt.Dimension(20,20));
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
			panelSouth.add(getPanelStatusBar(), java.awt.BorderLayout.WEST);
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
	 * This method initializes panelStatusBar	
	 * 	
	 * @return com.osrmt.appclient.ui.components.PanelStatusBar	
	 */
	public UIPanelStatusBar getPanelStatusBar() {
		if (panelStatusBar == null) {
			panelStatusBar = new UIPanelStatusBar();
		}
		return panelStatusBar;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize(String title) {
		this.setSize(UIProperties.getDIALOG_SIZE_740_400());
		this.setContentPane(getJContentPane());
		this.setTitle(title);
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
	
		
	/**
	 * This method initializes PanelOkClearCancel	
	 * 	
	 * @return com.osrmt.appclient.ui.components.PanelOkClearCancel	
	 */
	private PanelOkCancel getPanelOkCancel() {
		if (PanelOkCancel == null) {
			PanelOkCancel = new PanelOkCancel();
			PanelOkCancel.setPreferredSize(new java.awt.Dimension(300,43));
		}
		return PanelOkCancel;
	}

	public PanelOkCancel getOkCancelPanel() {
		return getPanelOkCancel();
	}

	public void addControls() {
		try {
			DefaultFormBuilder builder = LayoutUtility.getDefaultBuilder(controlColumns, 0);
			ReferenceDisplayList referenceDisplayList = ReferenceServices.getDisplayList(referenceGroup, false);
			list.addList(referenceDisplayList.elements());
			LayoutUtility.addControl(1, 0, 1, builder,referenceGroup,new JScrollPane(list));
			if (referenceDisplayList.getSize() > 0) {
				list.setSelectedIndex(0);
			}
			panelCenter.add(builder.getPanel());
			this.PanelOkCancel.addCancelActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
		} catch (Exception ex) {
			Debug.LogException(this, ex);
		}
	}
	
	public ReferenceDisplay getSelectedValue() {	
		return (ReferenceDisplay) list.getSelectedValue();
	}

	public ReferenceDisplayList getSelectedValues() {
		ReferenceDisplayList selected = new ReferenceDisplayList();
		Object[] values = list.getSelectedValues();
		for (int i=0; i< values.length; i++) {
			selected.add((ReferenceDisplay) values[i]);
		}
		return selected;
	}
	
	public void addOkActionListener(ActionListener action) {
		this.actionListener = action;
		this.PanelOkCancel.addOkActionListener(actionListener);
		this.list.addMouseListener(new DoubleClickListener() {
			public void call(ActionEvent me) {
				actionListener.actionPerformed(me);
			}
		});
		this.list.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyChar() == KeyEvent.VK_ENTER) {
					ActionEvent ae = new ActionEvent(e.getSource(),e.getID(),null);
					actionListener.actionPerformed(ae);
				}
			}
			public void keyReleased(KeyEvent e) {}
			public void keyTyped(KeyEvent e) {}
		});
	}

	public int getControlColumns() {
		return controlColumns;
	}

	public void setControlColumns(int controlColumns) {
		this.controlColumns = controlColumns;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
