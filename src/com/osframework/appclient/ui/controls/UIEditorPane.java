package com.osframework.appclient.ui.controls;

import java.awt.*;
import java.awt.Insets;
import java.awt.event.*;
import java.io.IOException;
import java.net.URL;

import javax.swing.*;
import javax.swing.text.*;

import com.jgoodies.forms.factories.Borders;

public class UIEditorPane extends JTextPane implements IGetDocument {

	private static final long serialVersionUID = 1L;

	public UIEditorPane() {
		super();
		customize();
	}

	public UIEditorPane(StyledDocument doc) {
		super(doc);
		customize();
	}
	
	public void customize() {
		try {
			  Object MyFocusActionKey = new Object(); 
			  Object MyFocusActionKey2 = new Object(); 

		 KeyStroke PressedShiftTab = KeyStroke.getKeyStroke( KeyEvent.VK_TAB, KeyEvent.SHIFT_DOWN_MASK, false); 
		 KeyStroke PressedTab = KeyStroke.getKeyStroke( KeyEvent.VK_TAB, 0, false); 

		 ActionMap actionMap = getActionMap(); 
		    actionMap.put(MyFocusActionKey, 
		                  new AbstractAction("MyFocusAction") { 
		       public void actionPerformed(ActionEvent e) { 
		    	   try {
			          Object source = e.getSource(); 
			          if (source instanceof Component) { 
			              FocusManager.getCurrentKeyboardFocusManager() 
			                   .focusNextComponent((Component) source); 
			          } 
		    	   } catch (Exception ex) {}
		       } 
		    }); 
			 ActionMap actionMap2 = getActionMap(); 
			    actionMap2.put(MyFocusActionKey2, 
			                  new AbstractAction("MyFocusAction2") { 
			       public void actionPerformed(ActionEvent e) { 
			    	   try {
				          Object source = e.getSource(); 
				          if (source instanceof Component) { 
				              FocusManager.getCurrentKeyboardFocusManager() 
				                   .focusPreviousComponent((Component) source); 
				          } 
			    	   } catch (Exception ex) {}
			       } 
			    }); 
		    getInputMap().put(PressedShiftTab, MyFocusActionKey2); 
		    getInputMap().put(PressedTab, MyFocusActionKey); 
		} catch (Exception ex) {
			
		}
	}
	
	public void setLocked(boolean locked) {
		super.setEditable(!locked);
	}

	
}
