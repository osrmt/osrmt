package com.osrmt.appclient.system;

import java.awt.*;
import javax.swing.*;
import com.osframework.framework.utility.*;
import com.osframework.appclient.ui.common.*;
import com.osframework.appclient.ui.controls.UIProperties;

public class UISplash extends JWindow {
	
	private static UISplash splash = null;
	
	private UISplash() {
		
	}
	
	public static UISplash getInstance() {
		if (splash == null) {
			splash = new UISplash();
		}
		return splash;
	}
	
	 public void start() {
		 //http://pdphoto.org/PictureDetail.php?mat=pdef&pg=7807
	        Image image = GUI.getImage("logo.png", this);
	        JLabel label = new JLabel(new ImageIcon(image));
	        getContentPane().add(label);
	        pack();
	        Dimension dim =
	            Toolkit.getDefaultToolkit().getScreenSize();
	        int x = (int)(dim.getWidth() - getWidth())/2;
	        int y = (int)(dim.getHeight() - getHeight())/2;
	        setLocation(x,y);
	    }
}
