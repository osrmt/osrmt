package com.osframework.appclient.ui.components;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionListener;

import com.jgoodies.forms.builder.ButtonStackBuilder;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.osframework.appclient.services.ReferenceServices;
import com.osframework.framework.logging.Debug;
import com.osframework.modellibrary.reference.group.FormButtonTextFramework;
import com.osframework.modellibrary.reference.group.SystemMessageFramework;

/**
 * 
 * PanelAddRemove is a panel with a multi column list 
 * and five buttons.  The components are exposed to allow
 * actionListeners, visibility, text etc. to be customized.
 * 
 *  ------------- 
 * |             |  Print
 * |             |  Preview
 * |             |
 * |             |
 *  -------------
 * Add Remove Properties...
 */
public class PanelAddRemove extends JPanel {

	public static int NO_RIGHT_SIDE_BUTTONS = 1;
	private JPanel panelSouth = null;
	private Component listControl = null;
	private Object userObject;
	
	private JButton addButton = new JButton(ReferenceServices.getDisplay(FormButtonTextFramework.ADDROW));
	private JButton removeButton = new JButton(ReferenceServices.getDisplay(FormButtonTextFramework.REMOVEROW));
	private JButton propertiesButton = new JButton(ReferenceServices.getDisplay(FormButtonTextFramework.PROPERTIES));
	private JButton printButton = new JButton(ReferenceServices.getDisplay(FormButtonTextFramework.PRINT));
	private JButton previewButton = new JButton(ReferenceServices.getDisplay(FormButtonTextFramework.PREVIEW));
	private JButton right3Button = new JButton("...");
	private JButton right4Button = new JButton("...");
	private JPanel customPanel = new JPanel(new BorderLayout());
	private JPanel listPanel = new JPanel(new BorderLayout());
	private Vector selectionListeners = new Vector();
	
	/**
	 * This is the default constructor
	 */
	public PanelAddRemove() {
		super(new BorderLayout());
		initialize(0);
	}

	/**
	 * This is the default constructor
	 */
	public PanelAddRemove(int modifier) {
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

    public JButton getAddButton() {
		return addButton;
	}

	public void setAddButton(JButton addButton) {
		this.addButton = addButton;
	}

	public JButton getPropertiesButton() {
		return propertiesButton;
	}

	public void setPropertiesButton(JButton propertiesButton) {
		this.propertiesButton = propertiesButton;
	}

	public JButton getRemoveButton() {
		return removeButton;
	}

	public void setRemoveButton(JButton removeButton) {
		this.removeButton = removeButton;
	}
	
	public int getSelectedIndex() {
		if (listControl instanceof MultiColumnList) {
			return ((MultiColumnList) listControl).getSelectedIndex();
		} else if (listControl instanceof UIList) {
			return ((UIList) listControl).getSelectedIndex();
		} else {
			Debug.LogError(this, ReferenceServices.getMsg(SystemMessageFramework.SWITCHSTATEMENTFAILED) + " " + listControl.getClass());
			return -1;
		}
	}
	
	/**
	 * Adds a listener to be notified when a selection occurs
	 * 
	 * @param listener
	 */
	public void addListSelectionListener(ListSelectionListener listener) {
		selectionListeners.add(listener); 
		if (listControl instanceof MultiColumnList) {
			((MultiColumnList) listControl).addListSelectionListener(listener);
		} else if (listControl instanceof UIList) {
			((UIList) listControl).addListSelectionListener(listener);
		} else {
			Debug.LogError(this, ReferenceServices.getMsg(SystemMessageFramework.SWITCHSTATEMENTFAILED) + " " + listControl.getClass());
		}
	}	


	public void setListControl(MultiColumnList list) {
		listPanel.removeAll();
		Enumeration e1 = selectionListeners.elements();
		while (e1.hasMoreElements()) {
			list.addListSelectionListener((ListSelectionListener) e1.nextElement());
		}
		listControl = list;
		listPanel.add(listControl, java.awt.BorderLayout.CENTER);
	}
	
	public void setListControl(UIList list) {
		listPanel.removeAll();
		Enumeration e1 = selectionListeners.elements();
		while (e1.hasMoreElements()) {
			list.addListSelectionListener((ListSelectionListener) e1.nextElement());
		}
		listControl = list;
		listPanel.add(new JScrollPane(listControl), java.awt.BorderLayout.CENTER);
	}
	
	public Component getListControl() {
		return listControl;
	}
	
	public JButton getPreviewButton() {
		return previewButton;
	}

	public void setPreviewButton(JButton previewButton) {
		this.previewButton = previewButton;
	}

	public JButton getPrintButton() {
		return printButton;
	}

	public JButton getRight3Button() {
		return this.right3Button;
	}

	public JButton getRight4Button() {
		return this.right4Button;
	}

	public void setPrintButton(JButton printButton) {
		this.printButton = printButton;
	}

	private JPanel buildButtonStack() {
        JPanel panel = new JPanel();
        CellConstraints cc = new CellConstraints();
        panel.setBorder(Borders.DIALOG_BORDER);
        ButtonStackBuilder builder = new ButtonStackBuilder();
        builder.addGridded(printButton);                      
        builder.addRelatedGap();                   
        builder.addGridded(previewButton);   
        builder.addRelatedGap();                   
        builder.addGridded(right3Button);
        right3Button.setVisible(false);
        builder.addRelatedGap();                   
        builder.addGridded(right4Button);   
        builder.addUnrelatedGap();
        right4Button.setVisible(false);
        panel.add(builder.getPanel(),  cc.xy(3, 1));                   
        return panel;
	}

	private Component buildAddRemovePropertiesPanel() {
        FormLayout layout = new FormLayout(
                        "fill:default:grow",
                        "fill:p:grow, 4dlu, p");
                        
        PanelBuilder builder = new PanelBuilder(layout);
        builder.setDefaultDialogBorder();

        builder.add(ButtonBarFactory.buildAddRemovePropertiesLeftBar(
            addButton, removeButton, propertiesButton
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
