/**
 * 
 */
package com.osframework.appclient.ui.editor;

import java.awt.Component;
import java.awt.Insets;

import javax.swing.DefaultCellEditor;
import javax.swing.JEditorPane;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 *
 */
public class TextCellEditor extends javax.swing.AbstractCellEditor implements javax.swing.table.TableCellEditor {

	private static final long serialVersionUID = 1L;
	private javax.swing.JEditorPane pane = new JEditorPane();
	
	public TextCellEditor() {
		pane.setMargin(new Insets(0,0,0,0));
	}

	public Object getCellEditorValue() {
		return pane.getText();
	}

	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		if (value == null) {
			pane.setText("");
		} else {
			pane.setText(String.valueOf(value));
		}
		return pane;
	}

	

}
