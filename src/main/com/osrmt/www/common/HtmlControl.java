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
import com.osframework.appclient.ui.controls.*;
import com.osframework.appclient.ui.common.*;
import com.osrmt.modellibrary.reqmanager.ArtifactList;

import javax.servlet.http.*;
import javax.swing.*;
import javax.swing.text.Document;

import org.apache.struts.*;
import org.apache.struts.action.*;

public class HtmlControl {
    
    private IHtmlApplicationControl control;
    private IControlModel model;
    
    public HtmlControl(IHtmlApplicationControl control, IControlModel model) {
        super();
        this.control = control;
        this.model = model;
    }
    
    
    public String getValue() {
        Object value = model.getModelColDataAt(control.getModelColumnRefId());
        if (value == null) {
            return "";
        } else {
            try {
                if (model.getModelColDatabaseDataType(control.getModelColumnRefId()) == DatabaseDataType.IntegerType) {
                    String fieldName = ReferenceServices.getDisplay(control.getModelColumnRefId());
                    if (fieldName.endsWith("RefId")) {
                        value = ReferenceServices.getDisplay(((Integer) value).intValue());
                    } else if (fieldName.endsWith("UserId")) {
                        ApplicationUserModel user = SecurityServices.getUser(((Integer) value).intValue());
                        if (user.getDisplayName() != null) {
                            value = user.getDisplayName();
                        } else {
                            value = user.getUserLogin();
                        }
                    }
                }
            } catch (Exception ex) {
                Debug.LogException(this, ex);
            }
            return value == null ? "" : value.toString();
        }
    }
    
    public String getControl() {
        String inputName = "modelRefId" + control.getModelColumnRefId();
        if (control.getHtmlScript() != null) {
            ControlScript cs = new ControlScript();
            cs.addReference(model,"model");
            cs.addReference(control,"control");
            cs.addReference(inputName,"inputname");
            Object returnValue = cs.execute(control.getHtmlScript(), String.class);
            return String.valueOf(returnValue);
        } else {
            return "";
        }
    }
    
    public static String getCombo(IHtmlApplicationControl control, IControlModel model, ReferenceDisplayList list) {
        StringBuilder sb = new StringBuilder(256);
        sb.append("<select name=\"modelRefId" + control.getModelColumnRefId() + "\">");
        for (int i=0; i<list.getSize(); i++) {
            ReferenceDisplay rd = (ReferenceDisplay) list.getElementAt(i);
            sb.append("<option value=\"" + rd.getRefId() + "\"");
            if (Converter.isSame(new Integer(rd.getRefId()), model.getModelColDataAt(control.getModelColumnRefId()))) {
                sb.append(" selected=\"true\"");
            }
            sb.append(">" + rd.getDisplay() + "</option>");
        }
        sb.append("</select>");
        return sb.toString();
    }
    
    public static String getTextArea(IHtmlApplicationControl control, IControlModel model) {
        StringBuilder sb = new StringBuilder(256);
        sb.append("<textarea wrap=\"normal\" cols=\"");
        sb.append(String.valueOf(control.getWidth() * 10));
        sb.append("\" rows=\"");
        sb.append(String.valueOf(control.getHeight()*2));
        sb.append("\" name=\"modelRefId");
        sb.append(control.getModelColumnRefId());
        sb.append("\">");
        Object value = model.getModelColDataAt(control.getModelColumnRefId());
        sb.append(value == null ? "" : String.valueOf(value));
        sb.append("</textarea>");
        return sb.toString();
    }
    
    public static String getTextInput(IHtmlApplicationControl control, IControlModel model) {
        StringBuilder sb = new StringBuilder(256);
        sb.append("<input size=\"");
        sb.append(String.valueOf(control.getWidth() * 10));
        sb.append("\" name=\"modelRefId");
        sb.append(control.getModelColumnRefId());
        sb.append("\" value=\"");
        Object value = model.getModelColDataAt(control.getModelColumnRefId());
        sb.append(value == null ? "" : String.valueOf(value));
        sb.append("\"/>");
        return sb.toString();
    }
    
    public static String getTable(ResultList list) {
        StringBuilder table = new StringBuilder();
        table.append("<table border=\"0\">");
        table.append("<tr bgcolor=\"#3399FF\">");
        Vector colNames = list.getColumnNames();
        for(int i=0; i<list.getColumnCount(); i++) {
            table.append("<td>");
            table.append(colNames.get(i));
            table.append("</td>");
        }
        table.append("</tr>");
        for(int i=0; i<list.getRowCount(); i++) {
            table.append("<tr>");
            for (int j = 0; j < list.getColumnCount(); j++) {
                table.append("<td>");
                if(list.getValueAt(i, j)!=null)
                    table.append(list.getValueAt(i, j));
                else
                    table.append("");
                table.append("</td>");
            }
            table.append("</tr>");
        }
        table.append("</table>");
        return table.toString();
    }
    
}

