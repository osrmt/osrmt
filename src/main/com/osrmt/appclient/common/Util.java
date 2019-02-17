package com.osrmt.appclient.common;

import java.util.*;
import com.osframework.appclient.ui.common.*;
import com.osframework.appclient.services.*;
import com.osframework.modellibrary.common.*;
import com.osframework.modellibrary.reference.common.*;
import com.osframework.modellibrary.reference.group.*;
import com.osframework.modellibrary.reference.security.*;

public class Util {

	/**
	 * Return the form control matching the model reference id
	 * Components is a collection of FormControls
	 * 
	 * @param components
	 * @param modelRefId
	 * @return
	 */
	public static FormControl getFormControl(Vector components, int modelRefId) {
		Enumeration e1 = components.elements();
		while (e1.hasMoreElements()) {
			FormControl fc = (FormControl) e1.nextElement();
			if (fc.getModelColumnRefId() == modelRefId) {
				return fc;
			}
		}
		return null;
	}
	
	public static void addCode(ReferenceDisplayList list, String refGroup, String... codes) throws Exception {
		for (String code : codes) {
			 list.add(ReferenceServices.getDisplay(refGroup, code));
		}
	}
	
	public static void add(ReferenceDisplayList list, String refGroup, String code) throws Exception {
		addCode(list, refGroup, code);
	}
	public static void add(ReferenceDisplayList list, String refGroup, String code1, String code2) throws Exception {
		addCode(list, refGroup, code1, code2);
	}
	public static void add(ReferenceDisplayList list, String refGroup, String code1, String code2, String code3) throws Exception {
		addCode(list, refGroup, code1, code2, code3);
	}
	public static void add(ReferenceDisplayList list, String refGroup, String code1, String code2, String code3, String code4) throws Exception {
		addCode(list, refGroup, code1, code2, code3, code4);
	}
	
	public static ReferenceDisplayList getUserList() {
		ReferenceDisplayList list = new ReferenceDisplayList();
		Enumeration e1 = SecurityServices.getAllUsers().elements();
		while (e1.hasMoreElements()) {
			ApplicationUserModel user = (ApplicationUserModel) e1.nextElement();
			ReferenceDisplay rd = new ReferenceDisplay();
			rd.setRefId(user.getUserId());
			rd.setDisplay(user.getDisplayName());
			list.add(rd);
		}
		return list;
	}
}
