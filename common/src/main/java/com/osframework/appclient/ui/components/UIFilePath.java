package com.osframework.appclient.ui.components;

import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.text.Document;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.osframework.appclient.ui.controls.IGetDocument;
import com.osframework.appclient.ui.controls.UIJPanel;
import com.osframework.framework.logging.Debug;
import com.osframework.framework.utility.FileProcess;

public class UIFilePath extends UIJPanel implements IGetDocument {

	private static final long serialVersionUID = 1L;
	private JTextField textFilePath = null;
	private JButton cmdSearch = null;
	private JFrame frame;
	private String title;
	
	/**
	 * This is the default constructor
	 */
	public UIFilePath() {
		super();
		initialize();
	}

	/**
	 * 
	 */
	public UIFilePath(JFrame frame, String title) {
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
				"150dlu,4dlu,15dlu",
				"pref");
				layout.setRowGroups(new int[][]{{1}});

				UIJPanel panel = new UIJPanel(layout);

				CellConstraints cc = new CellConstraints();
				//panel.add(new UILabel( ReferenceServices.getDisplay(FormButtonTextFramework.FILENAME) + ":"),cc.xy(1,1));
				panel.add(getTextFilePath(),cc.xy(1,1));
				panel.add(getCmdSearch(),cc.xy(3,1));

				this.setSize(300,100);
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
					FileDialog fileDialog = new FileDialog(new JFrame(),title, FileDialog.LOAD);
					fileDialog.setVisible(true);
					textFilePath.setText(FileProcess.getFilePath(fileDialog.getDirectory(), fileDialog.getFile()));
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

	public String getText() {
		try {
			if (getTextFilePath().getDocument() != null) {
				int len =  getTextFilePath().getDocument().getLength();
				if (len > 0) {
					return getTextFilePath().getDocument().getText(0,len);
				} 
			}
			return null;
		} catch (Exception ex) {
			Debug.LogException(this, ex);
			return null;
		}
	}

	public void setLocked(boolean locked) {
		// TODO Auto-generated method stub
		
	}
	public void setText(String text) {
		textFilePath.setText(text);
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
