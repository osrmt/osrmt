package com.osrmt.apps.swingApp.wizards;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import com.osframework.appclient.ui.components.PanelButtonWizard;
import com.osframework.appclient.ui.controls.UIJDialog;
import com.osframework.appclient.ui.controls.UIJPanel;
import com.osframework.appclient.ui.listeners.UIActionListener;


/**
 * 
 * Represents a single screen in the Wizard.
 * 
 *
 */
public abstract class WizardScreen extends UIJDialog {
	
	protected PanelButtonWizard buttons = new PanelButtonWizard();
	private JFrame frame;
	private Object userObject;
	private UIJPanel controlPanel = new UIJPanel(new BorderLayout());
	private JMenuBar menuBar;

	public WizardScreen(JFrame owner) throws HeadlessException {
		super(owner, false);
		this.frame = owner;
		setLayout(new BorderLayout());
		add(controlPanel, BorderLayout.CENTER);
	}
	
	/**
	 * Display the screen with previousScreen null if the first screen
	 * and nextScreen null if the last screen
	 * 
	 * @param previousScreen
	 * @param nextScreen
	 */
	public void initialize(WizardScreen previousScreen, WizardScreen nextScreen) {
		setTitle(getTitle());
		setSize(getSize());
		setLocation(getLocation());
		enableButtons(previousScreen, nextScreen);
		controlPanel.removeAll();
		JPanel centerPanel = getCenterPanel();
		centerPanel.setBorder(Borders.DIALOG_BORDER);
		controlPanel.setBorder(Borders.DIALOG_BORDER);
		controlPanel.add(centerPanel, BorderLayout.CENTER);
		controlPanel.add(buttons, BorderLayout.SOUTH);
		if (menuBar != null) {
			setJMenuBar(menuBar);
		}
	}
	
	private void enableButtons(final WizardScreen previousScreen, final WizardScreen nextScreen) {
		buttons.getCmdCancel().addActionListener(new UIActionListener(frame){
			public void actionExecuted(ActionEvent e) throws Exception {
				dispose();				
			}
		});
		// first screen
		if (previousScreen == null) {
			buttons.getCmdBack().setEnabled(false);
		} else {
			buttons.getCmdBack().setEnabled(true);
			buttons.getCmdBack().addActionListener(new UIActionListener(frame) {
				public void actionExecuted(ActionEvent e) throws Exception {
					previousScreen.setVisible(true);
					setVisible(false);
				}
			});
		}
		if (nextScreen == null) {
			buttons.getCmdNext().setEnabled(false);
			buttons.getCmdFinish().setEnabled(true);
			buttons.getCmdFinish().addActionListener(new UIActionListener(frame) {
				public void actionExecuted(ActionEvent e) throws Exception {
					if (getFinishAction()!=null) {
						getFinishAction().actionPerformed(e);
					}
					dispose();
				}
			});
		} else {
			buttons.getCmdNext().setEnabled(getUserObject()!= null);
			buttons.getCmdNext().addActionListener(new UIActionListener(frame) {
				public void actionExecuted(ActionEvent e) throws Exception {
					if (getNextAction()!=null) {
						getNextAction().actionPerformed(e);
					}
					nextScreen.setVisible(true);
					setVisible(false);
				}
			});
			buttons.getCmdFinish().setEnabled(false);
		}
	}
	
	/**
	 * Get the title for the screen
	 */
	public abstract String getTitle();

	/**
	 * Get the title for the screen
	 */
	public abstract Dimension getSize();

	/**
	 * Get the location
	 */
	public abstract Point getLocation();

	/**
	 * Get the center panel
	 */
	public abstract JPanel getCenterPanel();
	
	/**
	 * Optionally have a finish action before disposing on finish
	 * 
	 * @return
	 */
	public abstract ActionListener getFinishAction();

	/**
	 * Optionally have a next action before moving to the next screen
	 * 
	 * @return
	 */
	public abstract ActionListener getNextAction();

	public Object getUserObject() {
		return userObject;
	}

	public void setUserObject(Object userObject) {
		this.userObject = userObject;
	}

	public PanelButtonWizard getButtons() {
		return buttons;
	}

	public void setButtons(PanelButtonWizard buttons) {
		this.buttons = buttons;
	}

	public void setMenuBar(JMenuBar menuBar) {
		this.menuBar = menuBar;
	}

	
}
