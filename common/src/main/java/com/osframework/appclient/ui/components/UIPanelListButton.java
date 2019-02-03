package com.osframework.appclient.ui.components;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionListener;

import com.jgoodies.forms.builder.ButtonStackBuilder;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.osframework.appclient.services.ReferenceServices;
import com.osframework.appclient.ui.controls.UIJPanel;
import com.osframework.appclient.ui.controls.UIValueList;
import com.osframework.modellibrary.reference.group.FormButtonTextFramework;

/**
 * <pre>
 * PanelAddRemove is a panel with a value list 
 * and five+ buttons.  The components are exposed to allow
 * actionListeners, visibility, text etc. to be customized.
 * 
 *  ------------- 
 * |             |  ButtonR1
 * |             |  ButtonR2
 * |             |  ...
 * |             |
 *  -------------
 * ButtonB1 Button B2...
 * </pre>
 */
public class UIPanelListButton extends UIJPanel {

	public static int NO_RIGHT_SIDE_BUTTONS = 1;
	private JPanel panelSouth = null;
	protected UIValueList listControl = null;
	private Object userObject;
	
	protected JButton buttonB1 = new JButton(ReferenceServices.getDisplay(FormButtonTextFramework.ADDROW));
	protected JButton buttonB2 = new JButton(ReferenceServices.getDisplay(FormButtonTextFramework.REMOVEROW));
	protected JButton buttonB3 = new JButton(ReferenceServices.getDisplay(FormButtonTextFramework.PROPERTIES));
	protected JButton buttonR1 = new JButton(ReferenceServices.getDisplay(FormButtonTextFramework.PRINT));
	protected JButton buttonR2 = new JButton(ReferenceServices.getDisplay(FormButtonTextFramework.PREVIEW));
	protected JButton buttonR3 = new JButton("...");
	protected JButton buttonR4 = new JButton("...");
	protected JButton buttonR5 = new JButton("...");
	protected JPanel customPanel = new JPanel(new BorderLayout());
	protected JPanel listPanel = new JPanel(new BorderLayout());
	protected Vector selectionListeners = new Vector();
	
	/**
	 * This is the default constructor
	 */
	public UIPanelListButton() {
		super(new BorderLayout());
		initialize(0);
	}

	/**
	 * This is the default constructor
	 */
	public UIPanelListButton(int modifier) {
		super(new BorderLayout());
		initialize(modifier);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize(int modifier) {
		this.add(getPanelSouth(), java.awt.BorderLayout.SOUTH);
		JPanel panel = new JPanel();
		panel.setBorder(Borders.DIALOG_BORDER);
		customPanel.setBorder(Borders.DIALOG_BORDER);
		panel.setLayout(new BorderLayout());
		panel.add(buildButtonStack(), BorderLayout.NORTH);
		panel.add(customPanel, BorderLayout.CENTER);
		if (modifier != NO_RIGHT_SIDE_BUTTONS) {
			this.add(panel, BorderLayout.EAST);
		}
		this.add(listPanel, java.awt.BorderLayout.CENTER);
		getPanelSouth().add(buildAddRemovePropertiesPanel(), BorderLayout.CENTER);
	}

	
	
	/**
	 * This method initializes panelSouth	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanelSouth() {
		if (panelSouth == null) {
			panelSouth = new JPanel();
			//panelSouth.setPreferredSize(new java.awt.Dimension(43,43));
			panelSouth.setLayout(new BorderLayout());
		}
		return panelSouth;
	}

    public JButton getButtonB1() {
		return buttonB1;
	}

	public void setButtonB1(JButton addButton) {
		this.buttonB1 = addButton;
	}

	public JButton getButtonB3() {
		return buttonB3;
	}

	public void setButtonB3(JButton propertiesButton) {
		this.buttonB3 = propertiesButton;
	}

	public JButton getButtonB2() {
		return buttonB2;
	}

	public void setButtonB2(JButton removeButton) {
		this.buttonB2 = removeButton;
	}
	
	public int getSelectedIndex() {
		return listControl.getSelectedIndex();
	}
	
	/**
	 * Adds a listener to be notified when a selection occurs
	 * 
	 * @param listener
	 */
	public void addListSelectionListener(ListSelectionListener listener) {
		selectionListeners.add(listener); 
		listControl.addListSelectionListener(listener);
	}	


	public void setListControl(UIValueList list) {
		listPanel.removeAll();
		Enumeration e1 = selectionListeners.elements();
		while (e1.hasMoreElements()) {
			list.addListSelectionListener((ListSelectionListener) e1.nextElement());
		}
		listControl = list;
		listPanel.add(listControl, java.awt.BorderLayout.CENTER);
	}
		
	public UIValueList getListControl() {
		return listControl;
	}
	
	public JButton getButtonR2() {
		return buttonR2;
	}

	public void setButtonR2(JButton previewButton) {
		this.buttonR2 = previewButton;
	}

	public JButton getButtonR1() {
		return buttonR1;
	}

	public JButton getButtonR3() {
		return this.buttonR3;
	}

	public JButton getButtonR4() {
		return this.buttonR4;
	}

	public JButton getButtonR5() {
		return this.buttonR5;
	}

	public void setButtonR1(JButton printButton) {
		this.buttonR1 = printButton;
	}

	protected JPanel buildButtonStack() {
        JPanel panel = new JPanel();
        CellConstraints cc = new CellConstraints();
        panel.setBorder(Borders.DIALOG_BORDER);
        ButtonStackBuilder builder = new ButtonStackBuilder();
        builder.addGridded(buttonR1);                      
        builder.addRelatedGap();                   
        builder.addGridded(buttonR2);   
        builder.addRelatedGap();                   
        builder.addGridded(buttonR3);
        buttonR3.setVisible(false);
        builder.addRelatedGap();                   
        builder.addGridded(buttonR4);   
        builder.addUnrelatedGap();
        buttonR4.setVisible(false);
        panel.add(builder.getPanel(),  cc.xy(3, 1));                   
        return panel;
	}

	protected Component buildAddRemovePropertiesPanel() {
        FormLayout layout = new FormLayout(
                        "fill:default:grow",
                        "fill:p:grow, 4dlu, p");
                        
        PanelBuilder builder = new PanelBuilder(layout);
        builder.setDefaultDialogBorder();

        builder.add(ButtonBarFactory.buildAddRemovePropertiesLeftBar(
            buttonB1, buttonB2, buttonB3
        ));

        return builder.getContainer();
    }
	
	public JPanel getCustomPanel() {
		return customPanel;
	}
	public void setCustomPanel(JPanel customPanel) {
		this.customPanel = customPanel;
	}

	public Object getUserObject() {
		return userObject;
	}

	public void setUserObject(Object userObject) {
		this.userObject = userObject;
	}

	
}
