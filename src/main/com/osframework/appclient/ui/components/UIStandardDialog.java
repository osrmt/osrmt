package com.osframework.appclient.ui.components;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

import javax.swing.*;

import com.osframework.appclient.ui.common.GUI;
import com.osframework.appclient.ui.controls.*;
import com.osframework.appclient.ui.listeners.UIActionListener;

/**
 * <pre>
 * Creates a dialog of the form
 *   ---------------------------
 *  | Toolbar                   |
 *   ---------------------------
 *  |                           |
 *  |                           |
 *  |                           |
 *  |                           |
 *   ---------------------------
 *  | Statusbar       OK Cancel |
 *   ---------------------------
 *  Panels:
 *  getNorthPanel()
 *  getCenterPanel()
 *  getSouthPanel()
 *  getSouthWestPanel()
 *  getSouthEastPanel()
 *  getButtonPanel()
 *
 *  Typical usage:
 *  
 *  UIStandardDialog dialog = new UIStandardDialog(frame);
 *  		final JFrame frame = new JFrame();
 *		final UIStandardDialog dialog = new UIStandardDialog(frame);
 *		// Cancel
 *		dialog.getButtonPanel().getCmdButton0().addActionListener(new UIActionListener(frame){
 *			public void actionExecuted(ActionEvent e) throws Exception {
 *				dialog.dispose();
 *			}
 *		});
 *		// OK
 *		dialog.getButtonPanel().getCmdButton1().addActionListener(new UIActionListener(frame){
 *			public void actionExecuted(ActionEvent e) throws Exception {
 *				//TODO add actions on OK
 *				dialog.dispose();
 *			}
 *		});
 *		dialog.setVisible(true);
 *		</pre>
 */
public class UIStandardDialog extends UIJDialog {

	private static final long serialVersionUID = 1L;
	private UIJPanel northPanel = new UIJPanel(new BorderLayout());
	private UIJPanel southPanel = new UIJPanel(new BorderLayout());
	private UIJPanel centerPanel = new UIJPanel(new BorderLayout());
	private UIPanelButton buttonPanel = new UIPanelButton();
	private UIPanelStatusBar statusBar = new UIPanelStatusBar();
	private Object userObject = null;
	
	public static void main (String[] args) {
		final JFrame frame = new JFrame();
		final UIStandardDialog dialog = new UIStandardDialog(frame);
		// Cancel
		dialog.getButtonPanel().getCmdButton0().addActionListener(new UIActionListener(frame){
			public void actionExecuted(ActionEvent e) throws Exception {
				dialog.dispose();
			}
		});
		// OK
		dialog.getButtonPanel().getCmdButton0().addActionListener(new UIActionListener(frame){
			public void actionExecuted(ActionEvent e) throws Exception {
				//TODO add actions on OK
				dialog.dispose();
			}
		});
		dialog.setVisible(true);
	}
	
	public UIStandardDialog(Frame frame) {
		super(frame, false);
		initialize();
	}
	
	public UIStandardDialog(Frame frame, boolean modal) {
		super(frame, modal);
		initialize();
	}
	
	
	
	public UIStandardDialog(Dialog owner, String title, boolean modal) throws HeadlessException {
		super(owner, title, modal);
		initialize();
	}

	public UIStandardDialog(Dialog owner, boolean modal) throws HeadlessException {
		super(owner, modal);
		initialize();
	}

	private void initialize() {
		this.setResizable(true);
		this.setLayout(new BorderLayout());
		this.add(northPanel, BorderLayout.NORTH);
		this.add(buildSouthPanel(), BorderLayout.SOUTH);
		this.add(centerPanel, BorderLayout.CENTER);
		this.setSize(UIProperties.getDIALOG_SIZE_450_330());
		this.setLocation(UIProperties.getPoint200_200());
		GUI.registerDebug(this, getStatusBar());
		this.addWindowStateListener(new WindowStateListener(){
			public void windowStateChanged(WindowEvent e) {
				if (e.getID() == WindowEvent.WINDOW_CLOSED) {
					GUI.unregisterDebug(getStatusBar());
				}
			}
		});

	}

	public UIJPanel buildSouthPanel() {
		southPanel.add(statusBar, BorderLayout.CENTER);
		southPanel.add(buttonPanel, BorderLayout.EAST);
		return southPanel;
	}
	
	public UIJPanel getCenterPanel() {
		return centerPanel;
	}

	public void setCenterPanel(UIJPanel centerPanel) {
		this.centerPanel = centerPanel;
	}

	public UIJPanel getSouthPanel() {
		return southPanel;
	}

	public void setSouthPanel(UIJPanel southPanel) {
		this.southPanel = southPanel;
	}

	public Object getUserObject() {
		return userObject;
	}

	public void setUserObject(Object userObject) {
		this.userObject = userObject;
	}

	public UIPanelButton getButtonPanel() {
		return buttonPanel;
	}

	public void setButtonPanel(UIPanelButton buttonPanel) {
		this.buttonPanel = buttonPanel;
	}

	public UIJPanel getNorthPanel() {
		return northPanel;
	}

	public void setNorthPanel(UIJPanel northPanel) {
		this.northPanel = northPanel;
	}

	public UIPanelStatusBar getStatusBar() {
		return statusBar;
	}

	public void setStatusBar(UIPanelStatusBar statusBar) {
		this.statusBar = statusBar;
	}

	
	
}
