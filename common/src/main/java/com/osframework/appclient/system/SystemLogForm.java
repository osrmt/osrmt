package com.osframework.appclient.system;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Enumeration;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;

import com.osframework.appclient.services.ReferenceServices;
import com.osframework.appclient.ui.components.MultiColumnList;
import com.osframework.appclient.ui.components.PanelOkCancel;
import com.osframework.appclient.ui.components.UICenterSouthDialog;
import com.osframework.appclient.ui.controls.UIEditorPane;
import com.osframework.appclient.ui.controls.UIJPanel;
import com.osframework.appclient.ui.controls.UIProperties;
import com.osframework.appclient.ui.controls.UISplitPanel;
import com.osframework.appclient.ui.listeners.UIListSelectionListener;
import com.osframework.framework.logging.Debug;
import com.osframework.framework.logging.DebugService;
import com.osframework.modellibrary.framework.SysLogList;
import com.osframework.modellibrary.framework.SysLogModel;
import com.osframework.modellibrary.reference.group.FormButtonTextFramework;
import com.osframework.modellibrary.reference.group.FormTitleFramework;

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
