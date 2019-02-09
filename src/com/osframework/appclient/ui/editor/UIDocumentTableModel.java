/**
 * 
 */
package com.osframework.appclient.ui.editor;

import java.awt.Image;
import java.util.*;
import javax.swing.table.*;
import javax.swing.*;

/**
 *
 *
 */
public class UIDocumentTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	public static final int textTableColumnCount = 2;
	public static final int iconColumnIndex = 0;
	public static final int textColumnIndex = 1;
	/**
	 * Represents the lines in the table.
	 * 
	 */
	private List<IDocumentLine> textLines = null;

	/**
	 * Construct the text table model with the specified lines.
	 * 
	 */
	public UIDocumentTableModel() {
		
	}

	/**
	 * Construct the text table model with the specified lines.
	 * 
	 */
	public UIDocumentTableModel(List<IDocumentLine> lines) {
		textLines = lines;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return columnIndex > 0;
	}
	
	/**
	 * Two columns only for the text table model
	 * 
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	public int getColumnCount() {
		return textTableColumnCount;
	}

	/**
	 * Number of rows
	 * 
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	public int getRowCount() {
		if (textLines == null) {
			return 0;
		} else {
			return textLines.size();
		}
	}

	/**
	 * Return the component or text at the specified column
	 * 
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		if (columnIndex == iconColumnIndex) {
			return null; 
		} else {
			return textLines.get(rowIndex).getValue();
		}
	}
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		if (textLines != null && columnIndex == textColumnIndex) {
			if (aValue == null) {
				textLines.get(rowIndex).setValue(null);
			} else {
				textLines.get(rowIndex).setValue(String.valueOf(aValue));
			}
			super.fireTableRowsUpdated(rowIndex, rowIndex);
		}
	}
	/**
	 * Get the document line
	 * 
	 * @param rowIndex
	 * @return
	 */
	public IDocumentLine getDocumentLine(int rowIndex) {
		return textLines.get(rowIndex);
	}
	
	public void insertTextRow(int rowIndex) {
		DocumentTextLineModel m = new DocumentTextLineModel();
		textLines.add(rowIndex, m);
		this.fireTableRowsInserted(rowIndex, rowIndex);
	}

	public List<IDocumentLine> getTextLines() {
		return textLines;
	}

	public void setTextLines(List<IDocumentLine> textLines) {
		this.textLines = textLines;
	}

	
}
