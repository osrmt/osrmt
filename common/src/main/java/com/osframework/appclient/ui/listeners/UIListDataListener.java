package com.osframework.appclient.ui.listeners;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public abstract class UIListDataListener implements ListDataListener {

/**
 * Default constructor
 */
public UIListDataListener() {
	
}

/**
 * Execute valueChangedExecuted(ListDataEvent)
 */
public void contentsChanged(ListDataEvent e) {
	valueChanged(e);		
}

/**
 * Execute valueChangedExecuted(ListDataEvent)
 */
public void intervalAdded(ListDataEvent e) {
	valueChanged(e);
}

/**
 * Execute valueChangedExecuted(ListDataEvent)
 */
public void intervalRemoved(ListDataEvent e) {
	valueChanged(e);
}

/**
 * Execute valueChangedExecuted(ListDataEvent)
 */
public void valueChanged(ListDataEvent e) {
	try {
		valueChangedExecuted(e);
	} catch (Exception ex) {
		com.osframework.framework.logging.Debug.LogException(this,ex);
	}
}

/**
 * valueChangedExecuted is executed with every change
 * in the list
 * 
 * @param e
 * @throws Exception
 */
public abstract void valueChangedExecuted(ListDataEvent e) throws Exception;
}
