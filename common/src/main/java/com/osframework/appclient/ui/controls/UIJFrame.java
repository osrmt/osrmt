package com.osframework.appclient.ui.controls;

import javax.swing.JFrame;

import com.osframework.framework.logging.Debug;

public class UIJFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JFrame thisFrame = this;
	
	public UIJFrame() {
		try {
			
		} catch (Exception ex) {
			Debug.LogException(this, ex);
		}
	}
	
	
}
