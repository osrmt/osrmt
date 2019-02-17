package com.osframework.appclient.services;

import com.osframework.framework.logging.*;
import com.osframework.framework.utility.ClientProperty;
import com.osframework.modellibrary.reference.group.SystemMessageFramework;
import com.osframework.modellibrary.reference.security.*;

/**
 * Global access to client side settings
 *
 */
public class Application {
	
	private static ApplicationUserModel user = new ApplicationUserModel();
	private static Object object = null; 
	private static ClientProperty clientProperty = null;

	public static Object getObject() {
		return object;
	}

	public static void setObject(Object object) {
		Application.object = object;
	}

	public static ApplicationUserModel getUser() {
		if (user == null) {
			Debug.LogError("Application", ReferenceServices.getDisplay(SystemMessageFramework.NULLPOINTEREXCEPTION));
		}
		return user;
	}

	public static void setUser(ApplicationUserModel user) {
		Application.user.updateWith(user);
	}

	public static ClientProperty getClientProperty() {
		return clientProperty;
	}

	public static void setClientProperty(ClientProperty cp) {
		clientProperty = cp;
	}
	
}
