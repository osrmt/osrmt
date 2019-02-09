package com.osframework.appclient.ui.controls;

import java.awt.Dimension;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.UIManager;

import com.jgoodies.looks.LookUtils;
import com.jgoodies.looks.Options;
import com.osframework.appclient.ui.common.GUI;
import com.osframework.appclient.ui.listeners.WindowClosingListener;
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
