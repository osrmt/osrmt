package com.osframework.appclient.ui.controls;

import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

import com.jgoodies.forms.factories.Borders;
import com.osframework.appclient.ui.common.*;
import com.osframework.framework.logging.Debug;
import com.osframework.modellibrary.reference.security.ApplicationControlModel;

public class UIToolBar extends JToolBar {
	
	Vector buttons = new Vector();
	
	public void setEnabled() {
		try {
			Enumeration e1 = buttons.elements();
			while (e1.hasMoreElements()) {
				UIToolbarButton tb = (UIToolbarButton) e1.nextElement();
				tb.setEnabled();
			}
		} catch (Exception ex) {
			Debug.LogException(this, ex);
		}
	}
	
	public void addNewButton(ApplicationControlModel m, String controlDisplay, ApplicationAction action) {
		if (m.getControlRefDisplay().compareTo(controlDisplay)==0) {
			if (action == null) {
			    add(newButton(m.getImagePath(), null,
	                    m.getControlRefDisplay(),
	                    m.getControlText()));
			} else {
		    add(newButton(m.getImagePath(), action,
                    m.getControlRefDisplay(),
                    m.getControlText()));
			}
		}		
	}

	/**
	 * Create the button
	 * 
	 * @param imageName
	 * @param action
	 * @param toolTipText
	 * @param altText
	 * @return
	 */
	private UIToolbarButton newButton(String imageName, ApplicationAction action, String toolTipText, String altText) {
	    //Look for the image.
	    String imgLocation = "/resources/images/" + imageName;
	    URL imageURL = getClass().getResource(imgLocation);

	    //Create and initialize the button.
	    UIToolbarButton button = new UIToolbarButton();
	    button.setApplicationAction(action);
	    button.setToolTipText(altText);
	    if (action == null || action.getActionListener() == null) {
	    	button.setEnabled(false);
	    } else {
	    	button.addActionListener(action.getActionListener());
	    }

	    if (imageURL != null) {                      //image found
	        button.setIcon(new ImageIcon(imageURL, altText));
	    } else {                                     //no image found
	        button.setText(altText);
	        Debug.LogError(this, "Resource not found: " + imgLocation);
	    }
	    buttons.add(button);
	    return button;
	}


}
