package com.osframework.appclient.ui.controls;

import java.awt.BorderLayout;
import java.awt.LayoutManager;

import javax.swing.JPanel;

import com.osframework.appclient.ui.common.GUI;

public class UIJPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
    public UIJPanel() {
		super(new BorderLayout());
	}

	public UIJPanel(LayoutManager layout) {
		super(layout);
	}

	/**
     * Configures the UI; tries to set the system look on Mac, 
     * <code>WindowsLookAndFeel</code> on general Windows, and
     * <code>Plastic3DLookAndFeel</code> on Windows XP and all other OS.<p>
     * 
     * The JGoodies Swing Suite's <code>ApplicationStarter</code>,
     * <code>ExtUIManager</code>, and <code>LookChoiceStrategies</code>
     * classes provide a much more fine grained algorithm to choose and
     * restore a look and theme.
     */
    protected void configureUI() {
    	GUI.setLookAndFeel();
    }

}
