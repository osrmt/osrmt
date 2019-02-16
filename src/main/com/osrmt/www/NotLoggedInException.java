package com.osrmt.www;
import java.util.*;
import com.osframework.modellibrary.common.*;
import com.osframework.modellibrary.reference.security.*;
import com.osframework.appclient.services.*;
import com.osframework.appclient.ui.listeners.*;
import com.osframework.modellibrary.reference.common.*;
import com.osframework.modellibrary.reference.group.*;
import com.osframework.framework.logging.*;
import com.osframework.framework.utility.*;
import com.osrmt.www.services.*;
import javax.servlet.http.*;
import org.apache.struts.*;
import org.apache.struts.action.*;
import com.osrmt.modellibrary.reqmanager.*;

public class NotLoggedInException extends Exception {

	public NotLoggedInException() {
		super();
	}

	public NotLoggedInException(String message) {
		super(message);
	}

	public NotLoggedInException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotLoggedInException(Throwable cause) {
		super(cause);
	}

}

