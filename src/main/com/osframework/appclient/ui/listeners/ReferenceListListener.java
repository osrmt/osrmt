package com.osframework.appclient.ui.listeners;

import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

import com.osframework.modellibrary.reference.common.*;

/**
 * ReferenceListListener is used to return the selected reference id from the
 * list of ReferenceDisplay models.  Example:
 * 			JList control = new JList(ReferenceServices.getReferenceDisplayGroup(LocationGroup.GroupId));
 * 			control.addListSelectionListener(new ReferenceListListener() {
 * 				public void call(int p) {
 * 					patient.getVisit().setLocationRefId(p);
 * 				}
 * 			});
 * ReferenceListListener may also be used with a JComboBox.  Example:
 * JComboBox control = new JComboBox(ReferenceServices.getReferenceDisplayGroup(GenderGroup.GroupId));
 * control.addItemListener(new ReferenceListListener() {
 * 	public void call(int p) {
 * 		try {
 * 			patient.setGenderRefId(p);
 * 		} catch (Exception ex) {
 * 			Debug.LogException(this, ex);
 * 		}
 * }});
 */
public abstract class ReferenceListListener implements ListSelectionListener, ItemListener {
	
	/**
	 * Triggered when the combo list is selected
	 */
	public void itemStateChanged(ItemEvent ie) {
		call(getValue(ie));
	}
	
	/**
	 * Called to get the reference id selected
	 */
	public int getValue(ItemEvent e) {
    	try {
    		JComboBox list = (JComboBox) e.getSource();
        	if (list != null && list.getSelectedIndex() > -1) {
        		ReferenceDisplay rm = (ReferenceDisplay) list.getSelectedItem();
        		if (rm == null) {
        			return 0;
        		} else {
        			return rm.getRefId();
        		}
        	} else {
        		return 0;
        	}
    	} catch (Exception ex) {
    		//ex.printStackTrace(System.err);
    		return 0;
    	}
	}
	
	/**
	 * Triggered when the list selection changes
	 * 
	 */
    public void valueChanged(ListSelectionEvent e)
    { 
    	if (!e.getValueIsAdjusting()) {
    		call(getValue(e));
    	}
    }
    
    public void call(Object o) {
    	ReferenceDisplay rd = (ReferenceDisplay) o;
    	call (rd.getRefId());
    }

	
    /**
     * Implementations of this method are passed the 
     * Reference id selected from the list.
     * 
     * @param id
     */
	public abstract void call(int id);
	
	/**
	 * Gets the selected ID from the Reference List
	 * 
	 * @param e
	 * @return
	 */
	private int getValue(ListSelectionEvent e) {
    	try {
    		if (e.getValueIsAdjusting()) return 0;
    		JList list = (JList) e.getSource();
        	if (list != null && list.getSelectedIndex() > -1) {
        		ReferenceDisplay rm = (ReferenceDisplay) list.getSelectedValue();
        		if (rm == null) {
        			return 0;
        		} else {
        			return rm.getRefId();
        		}
        	} else {
        		return 0;
        	}
    	} catch (Exception ex) {
    		ex.printStackTrace(System.err);
    		return 0;
    	}
	}

}
