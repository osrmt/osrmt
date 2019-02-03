package com.osframework.appclient.ui.listeners;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public abstract class IndicatorListener implements ChangeListener {

	public void stateChanged(ChangeEvent e) {
		call(e);
	}

	public abstract void call(ChangeEvent e);

}
