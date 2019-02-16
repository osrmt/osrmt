package com.osframework.appclient.ui.components;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.osframework.appclient.services.ReferenceServices;
import com.osframework.framework.logging.Debug;
import com.osframework.modellibrary.reference.group.FormButtonTextFramework;
import com.osframework.modellibrary.reference.group.SystemMessageFramework;

/**
 * <pre>
 * Standard panel for buttons
 * 
 *   ---------------------------
 *  |                OK Cancel |
 *   ---------------------------
 *
 * Buttons are numbered from right to left
 * e.g. with OK Cancel cmdButton0 is Cancel
 * and cmdButton1 is OK
 *
 * typical usage:
 * 
 * UIPanelButton buttons = new UIPanelButton();
 * buttons.initialize(UIPanelButton.OKCANCEL);
 * </pre>
 */
public class UIPanelButton extends JPanel {

	private static final long serialVersionUID = 1L;
	public static final int OKCANCEL = 0;
	public static final int CLEAROKCANCEL = 1;
	public static final int APPLY = 2;
	public static final int CLOSEONLY = 3;
	public static final int ALLBUTTONS = 4;
	private JButton cmdButton3 = new JButton();
	private JButton cmdButton2 = new JButton();
	private JButton cmdButton1 = new JButton();
	private JButton cmdButton0 = new JButton();

	/**
	 * This is the default constructor
	 */
	public UIPanelButton() {
		super();
		initialize(0);
	}

	/**
	 * This method initializes the buttons 
	 * 
	 * @return void
	 */
	public void initialize(int UIPanelButtonConfig) {
		this.setLayout(new BorderLayout());
		this.removeAll();
		add(addPanel(UIPanelButtonConfig), BorderLayout.CENTER);
	}
	
	private Component addPanel(int UIPanelButtonConfig) {
        FormLayout layout = new FormLayout(
                "default:grow",
                "0:grow, p, 4dlu, p, 4dlu, p, 4dlu, p");
        PanelBuilder builder = new PanelBuilder(layout);
        builder.setDefaultDialogBorder();
        
        builder.nextRow();
        switch (UIPanelButtonConfig) {
        case APPLY:         	
        	cmdButton0.setText(getApplyDisplay());
            builder.add(ButtonBarFactory.buildRightAlignedBar(cmdButton0));
        	break;
        case OKCANCEL:         	
        	cmdButton1.setText(getOkDisplay());
        	cmdButton0.setText(getCancelDisplay());
            builder.add(ButtonBarFactory.buildOKCancelBar(cmdButton1, cmdButton0));
        	break;
        case CLEAROKCANCEL:         	
        	cmdButton2.setText(getClearDisplay());
        	cmdButton1.setText(getOkDisplay());
        	cmdButton0.setText(getCancelDisplay());
            builder.add(ButtonBarFactory.buildRightAlignedBar(cmdButton2, cmdButton1, cmdButton0));
        	break;
        case CLOSEONLY:         	
        	cmdButton0.setText(getCloseDisplay());
            builder.add(ButtonBarFactory.buildRightAlignedBar(cmdButton0));
        	break;
        case ALLBUTTONS:         	
        	cmdButton3.setText(getApplyDisplay());
        	cmdButton2.setText(getClearDisplay());
        	cmdButton1.setText(getOkDisplay());
        	cmdButton0.setText(getCancelDisplay());
            builder.add(ButtonBarFactory.buildRightAlignedBar(cmdButton3, cmdButton2, cmdButton1, cmdButton0));
        	break;
        default:
            Debug.LogError(this,ReferenceServices.getDisplay(SystemMessageFramework.SWITCHSTATEMENTFAILED) + ": " + UIPanelButtonConfig);
        }
        return builder.getContainer();
	}
	
	public void setButtonState(boolean cmd3enabled, boolean cmd2enabled, boolean cmd1enabled, boolean cmd0enabled) {
		this.cmdButton3.setEnabled(cmd3enabled);
		this.cmdButton2.setEnabled(cmd2enabled);
		this.cmdButton1.setEnabled(cmd1enabled);
		this.cmdButton0.setEnabled(cmd0enabled);
	}
	
	public static String getOkDisplay() {
		return ReferenceServices.getDisplay(FormButtonTextFramework.OK);
	}

	public static String getCancelDisplay() {
		return ReferenceServices.getDisplay(FormButtonTextFramework.CANCEL);
	}

	public static String getClearDisplay() {
		return ReferenceServices.getDisplay(FormButtonTextFramework.CLEAR);
	}

	public static String getCloseDisplay() {
		return ReferenceServices.getDisplay(FormButtonTextFramework.CLOSE);
	}

	public static String getApplyDisplay() {
		return ReferenceServices.getDisplay(FormButtonTextFramework.APPLY);
	}


	public JButton getCmdButton2() {
		return cmdButton2;
	}

	public void setCmdButton2(JButton cmdBack) {
		this.cmdButton2 = cmdBack;
	}

	public JButton getCmdButton3() {
		return cmdButton3;
	}

	public void setCmdButton3(JButton cmdCancel) {
		this.cmdButton3 = cmdCancel;
	}

	public JButton getCmdButton0() {
		return cmdButton0;
	}

	public void setCmdButton0(JButton cmdFinish) {
		this.cmdButton0 = cmdFinish;
	}

	public JButton getCmdButton1() {
		return cmdButton1;
	}

	public void setCmdButton1(JButton cmdNext) {
		this.cmdButton1 = cmdNext;
	}

}
