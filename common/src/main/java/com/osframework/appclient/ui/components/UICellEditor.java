package com.osframework.appclient.ui.components;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextField;

public class UICellEditor extends DefaultCellEditor {

	public UICellEditor(JCheckBox checkBox) {
		super(checkBox);
	}

	public UICellEditor(JComboBox comboBox) {
		super(comboBox);
	}

	public UICellEditor(JTextField textField) {
		super(textField);
	}
	
	
}

