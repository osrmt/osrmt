package com.osrmt.appclient.setup;
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

public class SkipSourceTableException extends Exception {

	public SkipSourceTableException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SkipSourceTableException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public SkipSourceTableException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public SkipSourceTableException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}

