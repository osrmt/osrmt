package com.osframework.appclient.ui.editor;

import java.awt.Component;
import java.awt.Insets;

import javax.swing.JEditorPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class TextCellRenderer extends JEditorPane implements TableCellRenderer {

	public TextCellRenderer() {
		setMargin(new Insets(0,0,0,0));
	}
	
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		return this;
	}

}
