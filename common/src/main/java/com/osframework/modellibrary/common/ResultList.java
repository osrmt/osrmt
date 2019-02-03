package com.osframework.modellibrary.common;

import java.util.GregorianCalendar;

import javax.swing.table.AbstractTableModel;

import com.osframework.framework.logging.Debug;
import com.osframework.framework.utility.CalendarUtility;

/**
 * ResultList provides the data behind the 
 * Multi Column lists
 *
 */
public abstract class ResultList extends AbstractTableModel {
	
	static final long serialVersionUID = 1L;
	boolean editCells = false;

	public ResultList() {
		super();
	}
	
	public abstract void sort();
	
	/**
	 * Get all classes for each column
	 * @return
	 */
	public abstract Class[] getClasses();
	
	public abstract java.util.Vector getColumnNames();
	
	public abstract Comparable getValueAt(int rowIndex);
		
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return editCells;
	}
	
	public void setEditcells(boolean edit) {
		editCells = edit;
	}
	
	public abstract int getRowCount();
	
	public abstract int getColumnCount();
	
	public abstract void setModelValueAt(Object aValue, int rowIndex, int columnIndex);
		
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if (columnIndex < getClasses().length) {
			return getClasses()[columnIndex];
		}
		return super.getColumnClass(columnIndex);
	}
	
	@Override
	public String getColumnName(int column) {
		if (column < getColumnNames().size()) {
			return getColumnNames().elementAt(column).toString();
		}
		return super.getColumnName(column);
	}
	
	/**
	 * @see javax.swing.table.AbstractTableModel#setValueAt(java.lang.Object, int, int)
	 */
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		Class columnClass = getClasses()[columnIndex];
		if (aValue == null) {
			if (columnClass.equals(Integer.class) || columnClass.equals(Double.class)) {
				// ignore not a valid value //TODO get visual feedback to table
			} else {
				setModelValueAt(aValue, rowIndex, columnIndex);
			}
		} else {
			if (columnClass.equals(Integer.class)) {
				if (aValue instanceof Integer) {
					setModelValueAt(aValue, rowIndex, columnIndex);
				} else if (aValue instanceof String) {
					try {
						setModelValueAt(new Integer(Integer.parseInt(aValue.toString())), rowIndex, columnIndex);
					} catch (Exception ex) {}
				} else if (columnClass.equals(Double.class)) {
					if (aValue instanceof Double) {
						setModelValueAt(aValue, rowIndex, columnIndex);
					} else if (aValue instanceof String) {
						try {
							setModelValueAt(new Double(Double.parseDouble(aValue.toString())), rowIndex, columnIndex);
						} catch (Exception ex) {}
					}
				}
			} else if (columnClass.equals(String.class)) {
				if (aValue instanceof String) {
					setModelValueAt(aValue, rowIndex, columnIndex);
				} else {
					setModelValueAt(aValue.toString(), rowIndex, columnIndex);
				}
			} else if (columnClass.equals(GregorianCalendar.class)) {
				if (aValue instanceof GregorianCalendar) {
					setModelValueAt(aValue, rowIndex, columnIndex);
				} else if (aValue instanceof String){
					try {
						if (CalendarUtility.shouldParseDate(aValue.toString(), CalendarUtility.LongFormat())) {
							setModelValueAt(CalendarUtility.Parse(aValue.toString(), CalendarUtility.LongFormat()), rowIndex, columnIndex);
						} else if (CalendarUtility.shouldParseDate(aValue.toString(), CalendarUtility.ShortDateFormat())) {
							setModelValueAt(CalendarUtility.Parse(aValue.toString(), CalendarUtility.ShortDateFormat()), rowIndex, columnIndex);
						}
					} catch (Exception ex) {}
				}
			} else {
				Debug.LogError(this,"Class not found " + columnClass);
			}
		}
		super.fireTableCellUpdated(rowIndex, columnIndex);
	}
}
