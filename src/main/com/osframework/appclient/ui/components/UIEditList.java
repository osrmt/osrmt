package com.osframework.appclient.ui.components;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.*;
import java.text.*;
import javax.swing.event.*;
import javax.swing.table.*;
import javax.swing.text.*;

import com.jgoodies.forms.builder.*;
import com.jgoodies.forms.layout.*;

import com.osframework.appclient.ui.common.ControlPanel;
import com.osframework.appclient.ui.common.GUI;
import com.osframework.appclient.ui.components.*;
import com.osframework.appclient.ui.controls.*;
import com.osframework.modellibrary.common.*;
import com.osframework.modellibrary.reference.security.*;
import com.osframework.appclient.services.*;
import com.osframework.appclient.ui.listeners.*;
import com.osframework.modellibrary.reference.common.*;
import com.osframework.modellibrary.reference.group.*;
import com.osframework.framework.logging.*;
import com.osframework.framework.utility.*;


public class UIEditList extends UIValueList  {

	
	public void setTableModel(ResultList list, int colMinWidth) {
		super.setTableModel(list, colMinWidth);
		list.setEditcells(true);
		this.getJtable().setRowHeight((int)(this.getJtable().getRowHeight()*1.25));
		for (int i=0; i< list.getColumnCount(); i++) {
			setEditComponent(i);
		}
	}
	
	public void setEditComponent(int columnIndex) {
		//TODO must consolidate these switch statements or replace
		//in favor of OOP
		if (getClass(columnIndex).equals(GregorianCalendar.class)) {
			UIDateField dateField = new UIDateField();
			setEditComponent(columnIndex, dateField);
		} else if (getClass(columnIndex).equals(Integer.class)) {
			UIIntegerField intField = new UIIntegerField();
			setEditComponent(columnIndex, intField);
		} else if (getClass(columnIndex).equals(Double.class)) {
			UIDoubleField doubleField = new UIDoubleField();
			setEditComponent(columnIndex, doubleField);
		} else {
			setEditComponent(columnIndex, new UITextField());
		}
	}
	
	public void setEditComponent(int columnIndex, JTextField component) {		
		JTableHeader header = getJtable().getTableHeader();
		TableColumnModel tmodel =  header.getColumnModel();
		if (columnIndex >= 0 && columnIndex<tmodel.getColumnCount()) {
			tmodel.getColumn(columnIndex).setCellEditor(getCellEditor(component));
		}
	}
	
	private DefaultCellEditor getCellEditor(JTextField component) {
		DefaultCellEditor dce = new DefaultCellEditor(component);
		dce.setClickCountToStart(1);
		return dce;
	}
	

	public static void main(String[] args) {
		try {
			ReferenceList list = new ReferenceList();
			ReferenceModel m = new ReferenceModel();
			m.setRefId(10000);
			m.setDisplay("Test");
			m.setDisplayCode("TEST");
			m.setDescription("Description of this item");
			list.add(m);
			JFrame frame = new JFrame();
			frame.setSize(600,400);
			frame.getContentPane().setLayout(new BorderLayout());
			JPanel panel = new JPanel( new BorderLayout());
			ApplicationControlList acl = new ApplicationControlList();
			ApplicationControlModel acm = new ApplicationControlModel();
			acm.setControlRefDisplay("createDt");
			acm.setControlText("Created");
			acl.add(acm);
			acm = new ApplicationControlModel();
			acm.setControlRefDisplay("description");
			acm.setControlText("Description");
			acl.add(acm);
			list.setColumnOrder(acl);
			UIEditList uilist = new UIEditList();
			uilist.setTableModel(list, 60);
			uilist.setEditComponent(0);
			uilist.setEditComponent(1);
			uilist.getJtable().setEditingColumn(0);
			uilist.getJtable().setEditingRow(0);
			uilist.getJtable().setRowSelectionAllowed(false);
			UIPanelListButton panelButtons = new UIPanelListButton();
			panelButtons.setListControl(uilist);
			panel.add(panelButtons, BorderLayout.CENTER);
			frame.getContentPane().add(panel, BorderLayout.CENTER);
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();			
		}
	}
	
}
