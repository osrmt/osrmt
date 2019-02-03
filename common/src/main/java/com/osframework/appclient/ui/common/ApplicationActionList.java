package com.osframework.appclient.ui.common;

import java.util.Enumeration;
import java.util.Vector;

public class ApplicationActionList extends Vector {
	

	public void addApplicationAction(ApplicationAction aa) {
		this.add(aa);
	}
	
	public boolean hasAction(int actionRefId) {
		if (this.size() == 0) {
			return false;
		} else {
			Enumeration e1 = this.elements();
			while (e1.hasMoreElements()) {
				ApplicationAction action = (ApplicationAction) e1.nextElement();
				if (action.getActionRefId() == actionRefId) {
					return true;
				}
			}
			return false;
		}
	}
	
	public ApplicationAction getAction(int actionRefId) {
		Enumeration e1 = this.elements();
		while (e1.hasMoreElements()) {
			ApplicationAction action = (ApplicationAction) e1.nextElement();
			if (action.getActionRefId() == actionRefId) {
				return action;
			}
		}
		return new ApplicationAction();
	}

	
}
