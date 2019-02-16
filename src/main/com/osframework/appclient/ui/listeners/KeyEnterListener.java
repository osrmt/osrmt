package com.osframework.appclient.ui.listeners;

import java.awt.event.*;

import javax.swing.KeyStroke;

public class KeyEnterListener implements KeyListener {
	
	private ActionListener action;

	public KeyEnterListener(ActionListener action) {
		super();
		this.action = action;
	}

	public void keyTyped(KeyEvent e) {

	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER
				|| e.getKeyCode() == KeyEvent.VK_ACCEPT) {
			action.actionPerformed(new ActionEvent(e.getSource(), 0, null)); 
		}

	}

	public void keyReleased(KeyEvent e) {

	}

}
