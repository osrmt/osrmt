package com.osframework.appclient.ui.components;

import java.awt.event.ActionEvent;

import javax.swing.JPanel;
import javax.swing.Timer;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.osframework.appclient.ui.common.LayoutUtility;
import com.osframework.appclient.ui.controls.UILabel;
import com.osframework.modellibrary.reference.security.ApplicationControlModel;


/**
 * The PanelStatusBar provides a consistent look and feel
 * to a status bar on a panel 200 pixels wide, 43 pixels high.
 * The only external access to set the status text message
 *
 */
public class UIPanelStatusBar extends JPanel {

	private static final long serialVersionUID = 1L;
	private UILabel labelStatus = null;
	private int messageTimeDisplayMs = 2000;

	/**
	 * This is the default constructor
	 */
	public UIPanelStatusBar() {
		super();
		initialize();
	}

	/**
	 * Display the message for messageTimeDisplayMs MilliSeconds 
	 * or 0 no timelimit
	 */
	public UIPanelStatusBar(int messageDisplayTimeMilliSeconds) {
		super();
		this.messageTimeDisplayMs = messageDisplayTimeMilliSeconds;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		DefaultFormBuilder builder = LayoutUtility.getDefaultBuilder(1, 0);
		ApplicationControlModel acm = new ApplicationControlModel();
		labelStatus = new UILabel();
		labelStatus.setText("");
		labelStatus.setForeground(new java.awt.Color(153,0,0));
		this.add(labelStatus, null);
		LayoutUtility.addControl(acm, builder, this);
	}
	
	/**
	 * Set the status bar text message.  Message will be cleared
	 * two seconds later.
	 * @param msg
	 */
	public void setMessage(String msg) {
		this.labelStatus.setText(msg);
		if (msg != null && msg.trim().length()>0 && messageTimeDisplayMs != 0) {
			Timer timer = new Timer(messageTimeDisplayMs, new java.awt.event.ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setMessage(null);
				}
			});
			timer.setRepeats(false);
			timer.start();
		}
	}

}
