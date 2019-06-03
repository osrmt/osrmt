package com.osframework.appclient.system;

import java.awt.*;
import javax.swing.*;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Enumeration;

import com.jgoodies.forms.builder.*;
import com.jgoodies.forms.layout.*;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;

import com.osframework.appclient.ui.common.*;
import com.osframework.appclient.ui.components.*;
import com.osframework.appclient.ui.controls.UIJFrame;
import com.osframework.modellibrary.common.*;
import com.osframework.modellibrary.framework.SysLogList;
import com.osframework.modellibrary.framework.SysLogModel;
import com.osframework.modellibrary.reference.group.FormButtonTextFramework;
import com.osframework.modellibrary.reference.group.FormTitleFramework;
import com.osframework.modellibrary.reference.security.*;
import com.osframework.appclient.services.*;
import com.osframework.appclient.ui.controls.*;
import com.osframework.framework.logging.*;


import com.osframework.appclient.ui.components.UICenterSouthDialog;
import com.osframework.appclient.ui.listeners.UIListSelectionListener;

public class SystemLogForm extends UICenterSouthDialog {

	private UIEditorPane pane = new UIEditorPane();
	private MultiColumnList uiList = new MultiColumnList();
	private JFrame frame;
	private PanelOkCancel panelOkCancel; 
	
	public SystemLogForm(JFrame owner, boolean modal) throws HeadlessException {
		super(owner, modal);
		this.frame = owner;
		initialize();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SystemLogForm s = new SystemLogForm(new JFrame(), false);
		s.setVisible(true);
	}
	@Override
	public void initialize() {
		try {
			setTitle(ReferenceServices.getDisplay(FormTitleFramework.SYSTEMLOG));
			setSize(UIProperties.getWINDOW_SIZE_640_480());
			UISplitPanel jp = new UISplitPanel(getCenterList(), getEditorPanel(), true, 240);
			super.getCenterPanel().add(jp, BorderLayout.CENTER);
			super.getSouthPanel().add(getSouthOkCancel(), BorderLayout.CENTER);
			addListeners();			
		} catch (Exception ex) {
			Debug.LogException(this, ex);
		}
	}
	
	private void addListeners() {
		try {
			uiList.addListSelectionListener(new UIListSelectionListener(frame) {
				public void valueChangedExecuted(ListSelectionEvent e) throws Exception {
					if (uiList.getSelectedRow() != null) {
						SysLogModel slm = (SysLogModel) uiList.getSelectedRow();
						pane.setText(slm.Message);
						pane.setCaretPosition(0);
					} else {
						pane.setText("");
					}
					
				}
			});
			panelOkCancel.getCmdOk().setVisible(false);
			panelOkCancel.getCmdCancel().setText(ReferenceServices.getDisplay(FormButtonTextFramework.CLOSE));
			panelOkCancel.addCancelActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			
		} catch (Exception ex) {
			Debug.LogException(this, ex);			
		}
	}
	
	private JPanel getEditorPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(new JScrollPane(pane));
		return panel;
	}
	
	private JPanel getSouthOkCancel() {
		panelOkCancel = new PanelOkCancel();
		
		return panelOkCancel;
	}
	
	private JPanel getCenterList() {
		try {
			DebugService service = new DebugService();
			Calendar startTime = Calendar.getInstance();
			startTime.add(Calendar.MINUTE, -5);
			SysLogList list = service.GetLog(startTime, Calendar.getInstance());
			Enumeration e1 = list.elements();
			while (e1.hasMoreElements()) {
				SysLogModel m = (SysLogModel) e1.nextElement();
				try {
					m.Message = service.GetDetails(m.Record, m.LogDateTime);
				} catch (Exception ex) {}
			}
			uiList.setTableModel(list, 60);
			JPanel panel = new JPanel(new BorderLayout());
			panel.add(uiList, BorderLayout.CENTER);
			return panel;
			
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			return new UIJPanel();
		}
	}

}
