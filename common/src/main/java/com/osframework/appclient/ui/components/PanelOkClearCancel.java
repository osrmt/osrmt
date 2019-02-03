package com.osframework.appclient.ui.components;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.osframework.appclient.services.ReferenceServices;
import com.osframework.modellibrary.reference.group.FormButtonTextFramework;

/**
 * The PanelOkCancel provides a consistent look and feel
 * to an OK and cancel button on a panel 200 pixels wide, 43 pixels high.
 * The only external access to the components is to add an action
 * listener to either button.
 *
 */
public class PanelOkClearCancel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton cmdOk = null;
	private JButton cmdCancel = null;
	private JButton cmdClear = null;

	/**
	 * This is the default constructor
	 */
	public PanelOkClearCancel() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setHgap(15);
		flowLayout.setAlignment(java.awt.FlowLayout.RIGHT);
		flowLayout.setVgap(10);
		this.setLayout(flowLayout);
		this.setSize(300, 43);
		this.setPreferredSize(new java.awt.Dimension(300,43));
		this.add(getCmdOk(), null);
		this.add(getCmdClear(), null);
		this.add(getCmdCancel(), null);
	}

	/**
	 * This method initializes cmdOk	
	 * 	
	 * @return javax.swing.JButton	
	 */
	public JButton getCmdOk() {
		if (cmdOk == null) {
			cmdOk = new JButton();
			cmdOk.setPreferredSize(new java.awt.Dimension(75,23));
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
			cmdCancel.setPreferredSize(new java.awt.Dimension(75,23));
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

	public void addClearActionListener(ActionListener actionListener) {
		cmdClear.addActionListener(actionListener);
	}

	/**
	 * This method initializes cmdClear	
	 * 	
	 * @return javax.swing.JButton	
	 */
	public JButton getCmdClear() {
		if (cmdClear == null) {
			cmdClear = new JButton();
			cmdClear.setPreferredSize(new java.awt.Dimension(75,23));
			cmdClear.setText(ReferenceServices.getDisplay(FormButtonTextFramework.CLEAR));
		}
		return cmdClear;
	}

}
