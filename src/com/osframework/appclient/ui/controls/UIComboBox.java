package com.osframework.appclient.ui.controls;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.event.ListDataEvent;
import com.osframework.appclient.ui.listeners.*;

public class UIComboBox extends JComboBox implements IGetCombo {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Initialize combo box editing
	 */
	public UIComboBox() {
		initialize();
	}

	/**
	 * Initialize combo box editing
	 * 
	 * @param aModel
	 */
	public UIComboBox(ComboBoxModel aModel) {
		super(aModel);
		initialize();
	}

	/**
	 * Initialize combo box editing
	 * 
	 * @param items
	 */
	public UIComboBox(Object[] items) {
		super(items);
		initialize();
	}

	/**
	 * Initialize combo box editing
	 */
	private void initialize() {
		UIComboDocument.enable(this);
	}

	/**
	 * Get the selected object
	 */
	public Object getValue() throws Exception {
		return this.getSelectedItem();
	}
	
	/**
	 * Select this object
	 */
	public void setValue(Object value) throws Exception {
		selectDisplayValue(getModel(), value);
	}
	
	@Override
	public void setBackground(Color bg) {
		super.setBackground(bg);
		if (getEditor() != null && getEditor().getEditorComponent()!=null) {
			getEditor().getEditorComponent().setBackground(bg);
		}
	}
	/**
	 * Iterates through the list to find an item matching value
	 * If the item is found it is selected
	 * @param jcb
	 * @param text
	 */
	public static boolean selectValue(ComboBoxModel jcb, Object matchObject) {
		try {
			if (jcb != null && matchObject != null) {
				for (int i=0; i<jcb.getSize(); i++) {
					Object o = jcb.getElementAt(i);
					if (o != null) {
						if (matchObject.equals(o)) {
							jcb.setSelectedItem(o);
							return true;
						}
					}
				}
			}
			return false;
		} catch (Exception ex) {
			com.osframework.framework.logging.Debug.LogException("UIComboBox",ex);
			return false;
		}
	}
	
	
	/**
	 * Iterates through the list to find an item matching value
	 * If the item is found it is selected
	 * @param jcb
	 * @param text
	 */
	public static boolean selectDisplayValue(ComboBoxModel jcb, Object match) {
		try {
			if (match != null && match.equals("")) {
				match = null;
			}
			if (jcb != null) {
				for (int i=0; i<jcb.getSize(); i++) {
					Object o = jcb.getElementAt(i);
					if (o != null) {
						if (match == null && o.toString().trim().equals("")) {
							jcb.setSelectedItem(o);
							return true;
						} else if (match != null && match.equals(o)) {
							jcb.setSelectedItem(o);
							return true;
						} else if (match != null && match.toString().equals(o.toString())) {
							jcb.setSelectedItem(o);
							return true;
						}
					}
				}
			}
			return false;
		} catch (Exception ex) {
			com.osframework.framework.logging.Debug.LogException("UIComboBox",ex);
			return false;
		}
	}
	
	@Override
	public String toString() {
		if (getSelectedItem() == null) {
			return "";
		} else {
			return getSelectedItem().toString(); 
		}
	}
	
	/* (non-Javadoc)
	 * @see com.patientis.framework.controls.IComponent#addNotifyModelListener(java.awt.event.ActionListener)
	 */
	public void addNotifyModelListener(final ActionListener listener) {
		// update model when control changes 
		ComboBoxModel comboBoxModel = getModel();
		comboBoxModel.addListDataListener(new UIListDataListener() {			
			@Override
			public void valueChangedExecuted(ListDataEvent e) {
				ActionEvent a = new ActionEvent(e.getSource(), 0, null);
				listener.actionPerformed(a);
			} 
		});
	}

	@Override
	public void setEnabled(boolean b) {
		super.setEnabled(b);
		if (getEditor() != null && getEditor().getEditorComponent()!=null) {
			getEditor().getEditorComponent().setEnabled(b);
		}
	}

	public void setLocked(boolean locked) {
		setEnabled(!locked);
	}
}
