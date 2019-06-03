package com.osframework.appclient.ui.controls;

import java.awt.*;

import javax.swing.UIManager;
import com.jgoodies.looks.LookUtils;
import com.jgoodies.looks.Options;
import com.jgoodies.looks.plastic.PlasticXPLookAndFeel;
import com.osframework.appclient.ui.common.GUI;
import com.osframework.framework.logging.Debug;

/**
 * UIProperties provides common UI settings for the system look and feel.
 *
 */
public class UIProperties {
	
	public static void setLookAndFeel() {
        String lafName =
            LookUtils.IS_OS_WINDOWS_XP
                ? Options.getCrossPlatformLookAndFeelClassName()
                : Options.getSystemLookAndFeelClassName();

        try {
            UIManager.setLookAndFeel(lafName);
        } catch (Exception e) {
        	Debug.LogExceptionOnly("GUI", e);
        }
	}
	
	/**
	 * Dimension of 700 pixels wide by 400 pixels high
	 */
	public static final Dimension getWINDOW_SIZE_700_400() {
		return new Dimension(700,400);
	}

	/**
	 * Dimension of 600 pixels wide by 400 pixels high
	 */
	public static final Dimension getWINDOW_SIZE_600_400() {
		return new Dimension(600,400);
	}
	/**
	 * Dimension of 640 pixels wide by 480 pixels high
	 */
	public static final Dimension getWINDOW_SIZE_640_480() {
		return new Dimension(640,480);
	}
	/**
	 * Dimension of 800 pixels wide by 600 pixels high
	 */
	public static final Dimension getWINDOW_SIZE_800_600() {
		return new Dimension(800,600);
	}

	public static final Dimension getWINDOW_SIZE_1000_600() {
		return new Dimension(1000,600);
	}
	public static final Dimension getWINDOW_SIZE_900_675() {
		return new Dimension(900,675);
	}

	public static final Dimension getDIALOG_SIZE_360_225 () {
		return new Dimension(360,225);
	}

	public static final Dimension getDIALOG_SIZE_450_330 () {
		return new Dimension(450,330);
	}

	public static final Dimension getDIALOG_SIZE_740_400() {
		return new Dimension(740,400);
	}

	public static final Dimension getDIALOG_SIZE_760_560() {
		return new Dimension(760,560);
	}

    public static void configureUI() {
    	GUI.setLookAndFeel();
    	
    }
    
    public static Point getPoint50_50() {
    	return new Point(50,50);
    }

    public static Point getPoint75_75() {
    	return new Point(75,75);
    }

    public static Point getPoint100_100() {
    	return new Point(100,100);
    }

    public static Point getPoint150_150() {
    	return new Point(150,150);
    }

    public static Point getPoint200_200() {
    	return new Point(200,200);
    }


}
