package com.osframework.appclient.ui.components;

import java.util.*;

import javax.swing.*;

/*
 * 		UIList list = new UIList(new DefaultListModel());
 *		list.addItem("a","A");
 *		list.addItem("b","B");
 *		list.addItem("c","C");
 *		list.moveDown(1);
 *		list.moveUp(2);
 *		for (int i=0; i<list.getListModel().getSize(); i++) {
 *			display(list.getListModel().getElementAt(i));
 *		}
 */
public class UIList extends JList {
	
	private DefaultListModel listModel; 
	
	public UIList(DefaultListModel list) {
		super(list);
		listModel = list;
	}

	public void addList(Enumeration e1) {
		while (e1.hasMoreElements()) {
			Object o = e1.nextElement();
			addItem(o.toString(), o);
		}
	}
	
	public void addItem(String display, Object o) {
		UIItem item = new UIItem(display, o);
		listModel.addElement(item);
	}
	
	public boolean moveDown(int position) {
		if (position >= 0 && position < listModel.getSize()-1) {
			Object removed = listModel.remove(position);
			listModel.insertElementAt(removed,position+1);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean moveBottom(int position) {
		if (position >= 0 && position < listModel.getSize()-1) {
			Object removed = listModel.remove(position);
			listModel.insertElementAt(removed,listModel.getSize());
			return true;
		} else {
			return false;
		}
	}
	
	public boolean moveUp(int position) {
		if (position >0 && position < listModel.getSize()) {
			Object removed = listModel.remove(position);
			listModel.insertElementAt(removed,position-1);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean moveTop(int position) {
		if (position >0 && position < listModel.getSize()) {
			Object removed = listModel.remove(position);
			listModel.insertElementAt(removed,0);
			return true;
		} else {
			return false;
		}
	}
	
	public DefaultListModel getListModel() {
		return listModel;
	}
	
	//TODO this does not work since we cant update the display
	// need to override toString or the display
	public Object getSelectedValue() {
		Object o = super.getSelectedValue();
		if (o instanceof UIItem) {
			return ((UIItem) o).getUserObject();
		} else {
			return o;
		}
	}

	//TODO this does not work since we cant update the display
	// need to override toString or the display
	public UIItem getSelectedItem() {
		Object o = super.getSelectedValue();
		if (o instanceof UIItem) {
			return (UIItem) o;
		} else {
			return new UIItem(null,null);
		}
	}

	public Object[] getSelectedValues() {
		Object[] objects = super.getSelectedValues();
		if (objects != null && objects.length > 0) {
			if (objects[0] instanceof UIItem) {
				for (int i=0; i< objects.length; i++) {
					objects[i] = ((UIItem) objects[i]).getUserObject();
				}
			}			
		}
		return objects;
	}

}
