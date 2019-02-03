package com.osframework.appclient.ui.components;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.osframework.appclient.services.ReferenceServices;
import com.osframework.appclient.ui.common.IParent;
import com.osframework.appclient.ui.common.LayoutUtility;
import com.osframework.appclient.ui.controls.UIEditorPane;
import com.osframework.appclient.ui.controls.UIProperties;
import com.osframework.appclient.ui.controls.UITextField;
import com.osframework.appclient.ui.listeners.TextDocListener;
import com.osframework.framework.logging.Debug;
import com.osframework.framework.utility.FileProcess;
import com.osframework.modellibrary.reference.group.FormButtonTextFramework;
import com.osframework.modellibrary.reference.group.FormTitleFramework;
import com.osframework.modellibrary.system.RecordFileModel;

public class AttachmentProperty extends JDialog {

	private JPanel jContentPane = null;
	private JPanel panelCenter = null;
	private JPanel panelSouth = null;
	private PanelOkCancel panelOkCancel = null;
	private RecordFileModel recordFile = new RecordFileModel();
	private IParent parent;
	
	/**
	 * This is the default constructor
	 */
	public AttachmentProperty(IParent parent, JFrame frame, RecordFileModel m) {
		super(frame,"", true);
		this.parent = parent;
		this.recordFile = m;
		UIProperties.configureUI();
		initialize();
		addControls();
		addListeners();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setLocation(UIProperties.getPoint200_200());
		this.setSize(UIProperties.getDIALOG_SIZE_450_330());
		this.setTitle(ReferenceServices.getDisplay(FormTitleFramework.ATTACHMENTPROPERTIES));
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getPanelCenter(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes panelCenter	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanelCenter() {
		if (panelCenter == null) {
			panelCenter = new JPanel();
			panelCenter.setLayout(new BorderLayout());
			panelCenter.add(getPanelSouth(), java.awt.BorderLayout.SOUTH);
		}
		return panelCenter;
	}

	/**
	 * This method initializes panelSouth	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanelSouth() {
		if (panelSouth == null) {
			panelSouth = new JPanel();
			panelSouth.setLayout(new BorderLayout());
			panelSouth.setPreferredSize(new java.awt.Dimension(40,40));
			panelSouth.add(getPanelOkCancel(), java.awt.BorderLayout.CENTER);
		}
		return panelSouth;
	}

	/**
	 * This method initializes panelOkCancel	
	 * 	
	 * @return com.osframework.appclient.ui.components.PanelOkCancel	
	 */
	private PanelOkCancel getPanelOkCancel() {
		if (panelOkCancel == null) {
			panelOkCancel = new PanelOkCancel();
		}
		return panelOkCancel;
	}
	
	private void addControls() {
		DefaultFormBuilder builder = LayoutUtility.getDefaultBuilder(1, 0);
		
		LayoutUtility.addControl(builder, ReferenceServices.getMsg(FormButtonTextFramework.ATTACHMENTDESCRIPTION), getDescription());
		LayoutUtility.addControl(builder, ReferenceServices.getMsg(FormButtonTextFramework.FILENAME), getFileName());
		LayoutUtility.addControl(builder, ReferenceServices.getMsg(FormButtonTextFramework.STORAGEDIRECTORY), getStorageFilePath());
		LayoutUtility.addControl(1,1,1,builder, ReferenceServices.getMsg(FormButtonTextFramework.SOURCEFILENAME), getSourceFile());
		
		this.getPanelCenter().add(builder.getPanel(), BorderLayout.CENTER);
	}
	
	private void addListeners() {
		this.getPanelOkCancel().addOkActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.setValue(recordFile);
				dispose();
			}
			
		});
		this.getPanelOkCancel().addCancelActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.setValue(null);
				dispose();
			}
			
		});
	}
	
	private UIEditorPane getSourceFile() {
		try {
			UIEditorPane control = new UIEditorPane();
			control.setText(recordFile.getSourceFile());
			control.getDocument().addDocumentListener(new TextDocListener() {
				public void call(String p) {
					recordFile.setSourceFile(p);
				}				
			});
			return control;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			return new UIEditorPane();
		}
	}

	private UITextField getFileName() {
		try {
			UITextField control = new UITextField(10);
			control.setText(recordFile.getFileName());
			control.getDocument().addDocumentListener(new TextDocListener() {
				public void call(String p) {
					recordFile.setFileName(p);
				}				
			});
			return control;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			return new UITextField();
		}
	}

	private UITextField getDescription() {
		try {
			UITextField control = new UITextField(10);
			control.setText(recordFile.getDescription());
			control.getDocument().addDocumentListener(new TextDocListener() {
				public void call(String p) {
					recordFile.setDescription(p);
				}				
			});
			return control;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			return new UITextField();
		}
	}

	private UITextField getStorageFilePath() {
		try {
			UITextField control = new UITextField(10);
			control.setText(FileProcess.getFilePath(recordFile.getStorageDirectory(), recordFile.getStorageFileName()));
			control.setEditable(false);
			return control;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			return new UITextField();
		}
	}



}  //  @jve:decl-index=0:visual-constraint="10,10"
