package com.osrmt.www.services;
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
import org.apache.struts.*;
import org.apache.struts.action.*;

public class LocalReferenceServices extends BaseService {

	public LocalReferenceServices() {
		super();
	}

	public static ArrayList getProducts() {
		authenticateContainer();
		ReferenceList referenceList = ReferenceServices.getActiveReferenceList(ReferenceGroup.Product);
		ArrayList list = new ArrayList();
		Enumeration e1 = referenceList.elements();
		while (e1.hasMoreElements()) {
			list.add(e1.nextElement());
		}
		return list;
	}
	
	public static String getDisplay(int refId) {
		authenticateContainer();
		return ReferenceServices.getDisplay(refId);
	}
}

