package com.osrmt.www.common;
import java.awt.*;
import java.io.UnsupportedEncodingException;
import java.util.*;

import com.osframework.modellibrary.common.*;
import com.osframework.modellibrary.reference.security.*;
import com.osframework.appclient.services.*;
import com.osframework.appclient.ui.common.ControlPanel;
import com.osframework.appclient.ui.common.FormControl;
import com.osframework.appclient.ui.common.GUI;
import com.osframework.appclient.ui.common.IHtmlApplicationControl;
import com.osframework.appclient.ui.common.UIFormLayout;
import com.osframework.appclient.ui.controls.ICustomBind;
import com.osframework.appclient.ui.controls.IGetCombo;
import com.osframework.appclient.ui.controls.IGetDocument;
import com.osframework.appclient.ui.controls.UILabel;
import com.osframework.appclient.ui.controls.UIValueList;
import com.osframework.appclient.ui.listeners.*;
import com.osframework.modellibrary.reference.common.*;
import com.osframework.modellibrary.reference.group.*;
import com.osframework.framework.logging.*;
import com.osframework.framework.utility.*;
import com.osrmt.modellibrary.reference.group.ApplicationGroup;
import com.osrmt.modellibrary.reqmanager.*;
import com.osrmt.www.common.*;
import com.osrmt.www.services.*;
import javax.servlet.http.*;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.text.*;
import javax.swing.text.html.HTMLEditorKit;

import org.apache.struts.*;
import org.apache.struts.action.*;

public class HtmlControlPanel {

	public HtmlControlPanel() {
		super();
	}
	
	public static String buildControls(String hiddenInputHtml, ApplicationControlList controls, IControlModel model, ServiceCall call, boolean readOnly) throws InvalidUserLoginException, InvalidUserPasswordException, Exception {
		StringBuilder sb = new StringBuilder(4096);
		sb.append("<table>");
		Enumeration e1 = controls.elements();
		while (e1.hasMoreElements()) {
			ApplicationControlModel rm = (ApplicationControlModel) e1.nextElement();
			ApplicationCustomControlModel custom = SecurityServices.getApplicationCustomControl(rm.getApplicationCustomControlId());
			rm.setCustom(custom);
			if (rm.getControlTypeRefId() != ControlTypeFramework.UITAB
					&& rm.getControlTypeRefId() != ControlTypeFramework.UISEPARATOR) {
				sb.append(getHtml(rm, model, readOnly));
			}
		}
		sb.append("</table>");
		sb.append("\r\n");
		return sb.toString();		
	}
	
	
	private static String getHtml(IHtmlApplicationControl fc, IControlModel model, boolean readOnly) throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder(64);
		sb.append("<tr>");
		sb.append("<td class=\"controllabel\">");
		//TODO escape html
		sb.append(fc.getLabel());
		sb.append("</td>");
		
		sb.append("<td>");
		if (readOnly || fc.isLocked() || fc.isDisabled()) {
			sb.append(new HtmlControl(fc, model).getValue());
		} else {
			sb.append(new HtmlControl(fc, model).getControl());
		}
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("\r\n");
		return sb.toString();
	}
	
}

