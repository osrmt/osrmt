package com.osframework.ejb.reportwriter;
import java.util.*;
import com.osframework.modellibrary.common.*;
import com.osframework.modellibrary.reference.security.*;
import com.osframework.appclient.services.*;
import com.osframework.appclient.ui.listeners.*;
import com.osframework.modellibrary.reference.common.*;
import com.osframework.modellibrary.reference.group.*;
import com.osframework.framework.logging.*;
import com.osframework.framework.utility.*;
import javax.servlet.http.*;

public class ReportSqlMissingException extends Exception {

	public ReportSqlMissingException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ReportSqlMissingException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ReportSqlMissingException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ReportSqlMissingException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}

