package com.osrmt.appclient.wizards;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.factories.Borders;
import com.osframework.appclient.services.*;
import com.osframework.appclient.ui.common.*;
import com.osframework.appclient.ui.controls.*;
import com.osframework.appclient.ui.components.*;
import com.osframework.appclient.ui.listeners.*;
import com.osframework.framework.logging.Debug;
import com.osframework.modellibrary.common.*;
import com.osframework.modellibrary.reference.security.*;
import com.osframework.modellibrary.system.*;


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
