package com.osrmt.www.common;
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

public class WebUser extends ApplicationUserModel {
	
	private int productRefId = 0;

	public WebUser() {
		super();
	}

	public int getProductRefId() {
		return productRefId;
	}

	public void setProductRefId(int productRefId) {
		this.productRefId = productRefId;
	}

	
}

