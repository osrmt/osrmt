package com.osframework.modellibrary.common;

import com.osframework.modellibrary.reference.security.*;

public class ServiceCall implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private ApplicationUserModel user;
	private int actionRefId;
	private Object application;
	
	public ServiceCall() {
		// TODO Auto-generated constructor stub
	}
	
	public ServiceCall(ApplicationUserModel user) {
		this.user = user;
	}

	public Object getApplication() {
		return application;
	}

	public void setApplication(Object application) {
		this.application = application;
	}

	public int getActionRefId() {
		return actionRefId;
	}

	public void setActionRefId(int actionRefId) {
		this.actionRefId = actionRefId;
	}
	
	public int getUserId() {
		if (user == null) {
			return 0;
		} else {
			return getUser().getUserId();
		}
	}

	public int getPositionRefId() {
		if (user == null) {
			return 0;
		} else {
			return getUser().getPositionRefId();
		}
	}

	public ApplicationUserModel getUser() {
		return user;
	}

	public void setUser(ApplicationUserModel user) {
		this.user = user;
	}

	
}
