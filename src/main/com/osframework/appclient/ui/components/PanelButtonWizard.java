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
import com.osframework.modellibrary.reference.group.FormButtonTextFramework;

/**
 * The PanelOkCancel provides a consistent look and feel
 * to an OK and cancel button on a panel 200 pixels wide, 43 pixels high.
 * The only external access to the components is to add an action
 * listener to either button.
 *
 */
public class PanelButtonWizard extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton cmdCancel = new JButton(ReferenceServices.getDisplay(FormButtonTextFramework.WIZARDCANCEL));
	private JButton cmdBack = new JButton(ReferenceServices.getDisplay(FormButtonTextFramework.WIZARDBACK));
	private JButton cmdNext = new JButton(ReferenceServices.getDisplay(FormButtonTextFramework.WIZARDNEXT));
	private JButton cmdFinish = new JButton(ReferenceServices.getDisplay(FormButtonTextFramework.WIZARDFINISH));

	/**
	 * This is the default constructor
	 */
	public PanelButtonWizard() {
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
		add(addPanel(), BorderLayout.CENTER);
	}
	
	private Component addPanel() {
        FormLayout layout = new FormLayout(
                "default:grow",
                "0:grow, p, 4dlu, p, 4dlu, p, 4dlu, p");
        PanelBuilder builder = new PanelBuilder(layout);
        builder.setDefaultDialogBorder();
        
        builder.nextRow();
        
        builder.add(ButtonBarFactory.buildWizardBar(cmdCancel, cmdBack, cmdNext, cmdFinish));
        return builder.getContainer();
	}
	
	public void setButtonState(boolean cancelEnabled, boolean backEnabled, boolean nextEnabled, boolean finishEnabled) {
		this.cmdCancel.setEnabled(cancelEnabled);
		this.cmdBack.setEnabled(backEnabled);
		this.cmdNext.setEnabled(nextEnabled);
		this.cmdFinish.setEnabled(finishEnabled);
	}

	public JButton getCmdBack() {
		return cmdBack;
	}

	public void setCmdBack(JButton cmdBack) {
		this.cmdBack = cmdBack;
	}

	public JButton getCmdCancel() {
		return cmdCancel;
	}

	public void setCmdCancel(JButton cmdCancel) {
		this.cmdCancel = cmdCancel;
	}

	public JButton getCmdFinish() {
		return cmdFinish;
	}

	public void setCmdFinish(JButton cmdFinish) {
		this.cmdFinish = cmdFinish;
	}

	public JButton getCmdNext() {
		return cmdNext;
	}

	public void setCmdNext(JButton cmdNext) {
		this.cmdNext = cmdNext;
	}

}
