package com.osframework.appclient.ui.controls;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.HeadlessException;

import javax.swing.JDialog;

import com.osframework.framework.logging.Debug;

public class UIJDialog extends JDialog {

	private JDialog thisDialog = this;
	private static final long serialVersionUID = 1L;
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
		try {
			
		} catch (Exception ex) {
			Debug.LogException(this, ex);
		}
    }
    
	public UIJDialog() throws HeadlessException {
		super();
		configureUI();
	}
	public UIJDialog(Dialog owner, boolean modal) throws HeadlessException {
		super(owner, modal);
		configureUI();
	}
	public UIJDialog(Dialog owner, String title, boolean modal) throws HeadlessException {
		super(owner, title, modal);
		configureUI();
	}
	public UIJDialog(Dialog owner, String title) throws HeadlessException {
		super(owner, title);
		configureUI();
	}
	public UIJDialog(Dialog owner) throws HeadlessException {
		super(owner);
		configureUI();
	}
	public UIJDialog(Frame owner, boolean modal) throws HeadlessException {
		super(owner, modal);
		configureUI();
	}
	public UIJDialog(Frame owner, String title, boolean modal) throws HeadlessException {
		super(owner, title, modal);
		configureUI();
	}
	public UIJDialog(Frame owner, String title) throws HeadlessException {
		super(owner, title);
		configureUI();
	}
	public UIJDialog(Frame owner) throws HeadlessException {
		super(owner);
		configureUI();
	}

}
