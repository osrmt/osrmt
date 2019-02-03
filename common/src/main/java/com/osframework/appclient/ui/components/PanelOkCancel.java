package com.osframework.appclient.ui.components;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.FormLayout;
import com.osframework.appclient.services.ReferenceServices;
import com.osframework.appclient.ui.controls.UIJPanel;
import com.osframework.modellibrary.reference.group.FormButtonTextFramework;

/**
 * The PanelOkCancel provides a consistent look and feel
 * to an OK and cancel button on a panel 200 pixels wide, 43 pixels high.
 * The only external access to the components is to add an action
 * listener to either button.
 *
 */
public class PanelOkCancel extends UIJPanel {

	private static final long serialVersionUID = 1L;
	private JButton cmdOk = null;
	private JButton cmdCancel = null;

	/**
	 * This is the default constructor
	 */
	public PanelOkCancel() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setLayout(new BorderLayout());
		add(addOKCancel(), BorderLayout.CENTER);
	}
	
	private Component addOKCancel() {
        FormLayout layout = new FormLayout(
                "default:grow",
                "0:grow, p, 4dlu, p, 4dlu, p, 4dlu, p");
        PanelBuilder builder = new PanelBuilder(layout);
        builder.setDefaultDialogBorder();
        
        builder.nextRow();
        builder.add(ButtonBarFactory.buildOKCancelBar(
                this.getCmdOk(), this.getCmdCancel()));
        return builder.getContainer();
	}

	/**
	 * This method initializes cmdOk	
	 * 	
	 * @return javax.swing.JButton	
	 */
	public JButton getCmdOk() {
		if (cmdOk == null) {
			cmdOk = new JButton();
			cmdOk.setToolTipText("");
			cmdOk.setText(ReferenceServices.getDisplay(FormButtonTextFramework.OK));
		}
		return cmdOk;
	}

	/**
	 * This method initializes cmdCancel	
	 * 	
	 * @return javax.swing.JButton	
	 */
	public JButton getCmdCancel() {
		if (cmdCancel == null) {
			cmdCancel = new JButton();
			cmdCancel.setText(ReferenceServices.getDisplay(FormButtonTextFramework.CANCEL));
		}
		return cmdCancel;
	}
	
	public void addOkActionListener(ActionListener actionListener) {
		cmdOk.addActionListener(actionListener);
	}
	
	public void addCancelActionListener(ActionListener actionListener) {
		cmdCancel.addActionListener(actionListener);
	}

}
