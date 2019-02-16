package com.osframework.appclient.ui.common;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.ScrollPane;

import javax.swing.*;
import javax.swing.text.JTextComponent;

import com.osframework.framework.logging.Debug;
import com.osframework.modellibrary.reference.security.*;
import com.jgoodies.forms.builder.*;
import com.jgoodies.forms.layout.*;
import java.util.*;

public class LayoutUtility {

	/**
	 * 1 column - 50 units(u) (max) wide label, 3u gap, 80u wide field
	 *       User name: [_________]   
	 *           Login: [_________]
	 * Separators should have a colspan of 3
	 * 
	 * n columns - 50u label, 3u gap, 80u field, 7u gap, 50u label etc.
	 * 
	 *    Patient Name: [_________]         MRN: [_________]   
	 *          Domain: [_________]       Login: [_________]   
	 * Separators should have a colspan of 7 or (n*4)-1
	 * 
	 * @param columns
	 * @param dluWidth 0 = grow
	 * @return
	 */
	public static DefaultFormBuilder getDefaultBuilder(int columns, int dluWidth) {
		String width = "min:grow";
		if (dluWidth > 0) {
			width = dluWidth + "dlu";
		}
		String height = "7dlu";
		String colDef = "right:max(50dlu;p), 3dlu, " + width + ", " + height;
		if (columns <2) {
			colDef = "right:max(50dlu;p), 3dlu, " + width;
		} else {
			for (int i=1; i<columns; i++) {
				if (i == columns-1) {
					colDef += ", right:max(50dlu;p), 3dlu, " + width;
				} else {
					colDef += ", right:max(50dlu;p), 3dlu, " + width + ", " + height;
				}
			}
		}
	    FormLayout layout = new FormLayout(colDef,"");
	    DefaultFormBuilder builder = new DefaultFormBuilder(layout);
	    builder.setDefaultDialogBorder();
	    return builder;
	}
	
	
	public static void addControl(DefaultFormBuilder builder, String label, java.awt.Component component) {
		ApplicationControlModel rm = new ApplicationControlModel();
		rm.setControlRefDisplay(label);
		rm.setUnitWidth(1);
		rm.setScrollpaneInd(0);
		rm.setGrowHeight(0);
		addControl(rm, label,builder,label,component);
	}
	
	public static void addControl(int growHeightInd, int scrollPaneInd, int colSpan, DefaultFormBuilder builder, String label, java.awt.Component component) {
		ApplicationControlModel rm = new ApplicationControlModel();
		rm.setControlRefDisplay(label);
		rm.setUnitWidth(colSpan);
		rm.setScrollpaneInd(scrollPaneInd);
		rm.setGrowHeight(growHeightInd);
		addControl(rm, label,builder,label,component);
	}
	
	public static void addControl(ApplicationControlModel rm, DefaultFormBuilder builder, java.awt.Component component) {
		addControl(rm, rm.getControlRefDisplay(), builder, rm.getControlText(), component);
	}
	/**
	 * If the control matches the control name add the label and the control
	 * to the builder.
	 * 
	 * If the column span is greater than the remaining columns wrap to the 
	 * next line.
	 * 
	 * @param rm
	 * @param builder
	 * @param label
	 * @param component
	 */
	public static void addControl(ApplicationControlModel rm, String controlName, DefaultFormBuilder builder, String label, java.awt.Component component) {
		try {
		if (rm.getControlRefDisplay().compareTo(controlName)==0) {
			if (label == null || label.length()==0) {
				label = " ";
			}
			if ((builder.getColumn() + rm.getUnitWidth()) > builder.getColumnCount()) {
				builder.nextLine();
			}
			if (rm.getUnitWidth() > builder.getColumnCount()) {
				rm.setUnitWidth(builder.getColumnCount());
			}		
			if (rm.getScrollpaneInd()==1) {
				component = new JScrollPane(component);
			}
			if (rm.getGrowHeight()==1) {
				CellConstraints cc = new CellConstraints();
		        //component.setPreferredSize(new Dimension(200,200));
		        //builder.appendRow(new RowSpec("top:6dlu:grow"));
		        builder.appendGlueRow();
		        builder.append(label);
		        builder.appendGlueRow();
		        builder.appendRow(new RowSpec("top:6dlu:grow(0.25)"));
		        //builder.add(new JScrollPane(component),cc.xywh(builder.getColumn(), builder.getRow(), 
		        //		rm.getColumnSpan(), 2, "fill, fill"));
		        builder.add(component,
	                    cc.xywh(builder.getColumn(), builder.getRow(),rm.getUnitWidth(),2, "fill, fill"));
		        builder.appendRow(new RowSpec("top:18dlu:none"));
		        builder.nextLine(3);

			} else { 
		        builder.appendRow(new RowSpec("top:18dlu:none"));
				builder.append(label, component, rm.getUnitWidth());
			}
		}
		} catch (Exception ex) {
			Debug.LogException("LayoutUtility", ex);
		}
	}

	
}
