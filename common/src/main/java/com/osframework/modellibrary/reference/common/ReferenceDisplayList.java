//************ AUTO GENERATED DO NOT EDIT *********//
package com.osframework.modellibrary.reference.common;

import java.util.Enumeration;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class ReferenceDisplayList extends Vector implements  Enumeration, ComboBoxModel, ListModel, java.io.Serializable {

	static final long serialVersionUID = 1L;
	
	public ReferenceDisplayList() {
		
	}
	
	public ReferenceDisplayList(int initialCapacity) {
		super(initialCapacity);
	}
	
	public void add(ReferenceDisplay m) {
		super.add(m);
	}
	
	/**
	 * Returns the reference display for the given id.
	 * Returns a valid empty ReferenceDisplay if the 
	 * id is not found.
	 * @param refId
	 * @return
	 */
	public ReferenceDisplay getDisplay(int refId) {
		Enumeration e1 = this.elements();
		while (e1.hasMoreElements()) {
			ReferenceDisplay rd = (ReferenceDisplay) e1.nextElement();
			if (rd.getRefId() == refId) {
				return rd;
			}
		}
		return new ReferenceDisplay();
	}

	public int getSize() {
		return super.size();
	}
	
	public Object getElementAt(int i) {
		return super.get(i);
	}
	
	private Vector dataListeners = new Vector();
	public void addListDataListener(ListDataListener dataListener) {
		dataListeners.add(dataListener);
	}
	
	public void removeListDataListener(ListDataListener dataListener) {
		dataListeners.remove(dataListener);
	}
	
	private Object selectedItem = null;
	public Object getSelectedItem() {
		return selectedItem;
	}
	
	//TODO The reference display and combo box model does
	// not handle multiple selection
	public void setSelectedItem(Object o ) {
		selectedItem = o;
		Enumeration e1 = dataListeners.elements();
		while (e1.hasMoreElements()) {
			ListDataListener l = (ListDataListener) e1.nextElement();
			l.contentsChanged(new ListDataEvent(this, 0, 0, size()));
		}
	}
	
	/**
	 * Sets the selected item for the reference id
	 * @param refId
	 */
	public void setSelectedRefId(int refId) {
		setSelectedItem(getDisplay(refId));
	}

	private Enumeration e1;
	public Enumeration elements() {
		e1 = super.elements();
		return e1;
	}
	public boolean hasMoreElements() {
		return e1.hasMoreElements();
	}

	public Object nextElement() {
		return e1.nextElement();
	}
	
	public ReferenceDisplay getFirst() {
		if (getSize() > 0) {
			return (ReferenceDisplay) elementAt(0);
		} else {
			return new ReferenceDisplay();
		}
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("ReferenceDisplayList");
		sb.append("\r\n");
		Enumeration e1 = elements();
		while (e1.hasMoreElements()) {
			ReferenceDisplay m = (ReferenceDisplay) e1.nextElement();
				sb.append(m);
				sb.append("\r\n");
		}
		return sb.toString();
	}
}