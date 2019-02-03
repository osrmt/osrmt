package com.osframework.appclient.ui.components;

import java.awt.BorderLayout;

import com.osframework.appclient.ui.controls.UIJPanel;
import com.osframework.appclient.ui.controls.UIProperties;

/**
 * <pre>
 * Creates a panel of the form
 *   ---------------------------
 *  |                           |
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
 *  getPanelOkCancel()
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
 *		dialog.getButtonPanel().getCmdButton0().addActionListener(new UIActionListener(frame){
 *			public void actionExecuted(ActionEvent e) throws Exception {
 *				//TODO add actions on OK
 *				dialog.dispose();
 *			}
 *		});
 *		dialog.setVisible(true);
 *		</pre>
 */
public class UIStandardPanel extends UIJPanel {

	private static final long serialVersionUID = 1L;
	private UIJPanel northPanel = new UIJPanel(new BorderLayout());
	private UIJPanel southPanel = new UIJPanel(new BorderLayout());
	private UIJPanel centerPanel = new UIJPanel(new BorderLayout());
	private UIPanelButton buttonPanel = new UIPanelButton();
	private UIPanelStatusBar statusBar = new UIPanelStatusBar();
	private Object userObject = null;
	
	
	public UIStandardPanel() {
		initialize();
	}
	
	private void initialize() {
		this.setLayout(new BorderLayout());
		this.add(northPanel, BorderLayout.NORTH);
		this.add(buildSouthPanel(), BorderLayout.SOUTH);
		this.add(centerPanel, BorderLayout.CENTER);
		this.setSize(UIProperties.getDIALOG_SIZE_450_330());
		this.setLocation(UIProperties.getPoint200_200());
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
