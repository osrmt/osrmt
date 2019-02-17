package com.osrmt.appclient.system;
import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.osframework.modellibrary.common.*;
import com.osframework.modellibrary.reference.security.*;
import com.osframework.appclient.services.*;
import com.osframework.appclient.ui.controls.UIJPanel;
import com.osframework.appclient.ui.controls.UILabel;
import com.osframework.appclient.ui.listeners.*;
import com.osframework.modellibrary.reference.common.*;
import com.osframework.modellibrary.reference.group.*;
import com.osframework.framework.logging.*;
import com.osframework.framework.utility.*;
import com.osrmt.www.services.*;
import javax.servlet.http.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.text.Document;

import org.apache.struts.*;
import org.apache.struts.action.*;
import com.osrmt.modellibrary.reqmanager.*;

public class ApplicationCustomControl extends UIJPanel {

	private static final long serialVersionUID = 1L;
	private JTextField textFilePath = null;
	private JButton cmdSearch = null;
	private JFrame frame;
	private String title;
	private ApplicationControlModel applicationControl = null;
	private ApplicationCustomControlModel applicationCustomControl = null;
	
	/**
	 * This is the default constructor
	 */
	public ApplicationCustomControl() {
		super();
		initialize();
	}

	/**
	 * 
	 */
	public ApplicationCustomControl(JFrame frame, String title) {
		super(new BorderLayout());
		this.frame = frame;
		this.title = title;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		
		FormLayout layout = new FormLayout(
				"pref, 4dlu,100dlu,4dlu,15dlu",
				"pref");
				layout.setRowGroups(new int[][]{{1}});

				UIJPanel panel = new UIJPanel(layout);

				CellConstraints cc = new CellConstraints();
				panel.add(new UILabel(""),cc.xy(1,1));
				panel.add(getTextFilePath(),cc.xy(3,1));
				panel.add(getCmdSearch(),cc.xy(5,1));

		add(panel, BorderLayout.CENTER);
	}

	/**
	 * This method initializes textLocation	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	public JTextField getTextFilePath() {
		if (textFilePath == null) {
			textFilePath = new JTextField(10);
			textFilePath.setEditable(false);
		}
		return textFilePath;
	}


	/**
	 * This method initializes cmdSearch	
	 * 	
	 * @return javax.swing.JButton	
	 */
	public JButton getCmdSearch() {
		if (cmdSearch == null) {
			cmdSearch = new JButton("...");
			cmdSearch.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					CustomControlForm form = new CustomControlForm(frame);
					ApplicationCustomControlModel cust = SecurityServices.getApplicationCustomControl(applicationCustomControl.getApplicationCustomControlId());
					form.start(cust);
					applicationControl.setApplicationCustomControlId(cust.getApplicationCustomControlId());
					setApplicationControl(applicationControl);
					
				}
			});
		}
		return cmdSearch;
	}
	
	public Document getDocument() {
		return getTextFilePath().getDocument();
	}

	public JTextField getTextField() {
		return getTextFilePath();
	}

	public ApplicationControlModel getApplicationControl() {
		return applicationControl;
	}

	public void setApplicationControl(ApplicationControlModel applicationControl) {
		this.applicationControl = applicationControl;
		this.applicationCustomControl = SecurityServices.getApplicationCustomControl(this.applicationControl.getApplicationCustomControlId());
		getTextField().setText(applicationCustomControl.getCustomControlRefDisplay());
	}

	
}

