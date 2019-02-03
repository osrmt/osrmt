package com.osframework.modellibrary.common;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Vector;
/**
 * The result column list is used to manage a result models
 * list of columns for the abstract data model.
 */
public class ResultColumnList implements Serializable {

	private static final long serialVersionUID = 1L;
	private int columnCount = 0;
	private Vector columns = new Vector();
	private Class[] classes = null;
	private Vector columnNames = null;

	public int getColumnCount() {
		return columnCount;
	}

	public void setColumnCount(int columnCount) {
		this.columnCount = columnCount;
	}

	
	/**
	 * Add a new column in position originalPos and sorted into
	 * position pos
	 * @param fieldName
	 * @param originalPos
	 * @param pos
	 */
	public void addColumn(String fieldName, Class colClass) {
		ResultColumn col = new ResultColumn(fieldName,columnCount,colClass);
		columns.add(col);
		columnCount++;
	}
	
	/**
	 * Updates the new position of a column and resorts list
	 * 
	 * @param fieldname
	 * @param position
	 * @throws ResultColumnException
	 */
	public void updatePosition(String fieldname, int position, String display) throws ResultColumnException {
		Enumeration e1 = columns.elements();
		ResultColumn col = null;
		while (e1.hasMoreElements()) {
			ResultColumn rc = (ResultColumn) e1.nextElement();
			if (rc.getFieldname().compareTo(fieldname)==0) {
				col = rc;
				col.setColumnDisplay(display);
				break;
			}
		}
		if (col != null) {
			columns.remove(col);
			columns.insertElementAt(col,position);
		} else {
			throw new ResultColumnException(fieldname);
		}
	}
	
	public int getOriginalPosition(int position) throws ResultColumnException  {
		ResultColumn col = getColumn(position);
		return col.getOriginalPosition();
	}

	//TODO rewrite this class!
	public ResultColumn getColumn(int position) throws ResultColumnException {
		if (position < columnCount) {
			return (ResultColumn) columns.elementAt(position);
		}
		throw new ResultColumnException("" + position);
	}
	
	public Class[] getClasses() {
		if (classes == null) {
			classes = new Class[columnCount];
			Vector v = new Vector();
			for (int i=0; i < columnCount; i++) {
				ResultColumn col = (ResultColumn) columns.elementAt(i);
				classes[i] = col.getColumnClass();
			}
			return classes;
		} else {
			return classes;
		}
	}

	public Vector getColumnNames() {
		if (columnNames == null) {
			columnNames = new Vector(columnCount);
			for (int i=0; i < columnCount; i++) {
				ResultColumn col = (ResultColumn) columns.elementAt(i);
				columnNames.add(col.getColumnDisplay());
			}
			return columnNames;
		} else {
			return columnNames;
		}
	}
	
	public String toString() {
		try {
			StringBuffer sb =new StringBuffer();
			sb.append(this.getColumnCount() + " columns");
			for (int i=0; i<this.getColumnCount(); i++) {
				ResultColumn rc = this.getColumn(i);
				sb.append(rc.getColumnDisplay() + "(" + rc.getColumnClass() + ")");
				sb.append("\t");
			}
			sb.append("\n");
			
			return sb.toString();
		} catch (ResultColumnException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public static void main(String[] args) {
		try {
			ResultColumnList list = new ResultColumnList();
			list.addColumn("name",String.class);
			list.addColumn("dob",DbCalendar.class);
			list.addColumn("sex",Integer.class);
			list.addColumn("active",Integer.class);
			list.updatePosition("dob",0,"BirthDate");
			list.updatePosition("active",1,"Gender");
			list.updatePosition("sex",1,"Patient Name");
			list.setColumnCount(3);
			for (int i=0; i < list.getColumnCount(); i++) {
				ResultColumn col = list.getColumn(i);
			}
			Vector names = list.getColumnNames();
			Enumeration e1 = names.elements();
			while (e1.hasMoreElements()) {
				System.out.print(e1.nextElement().toString() + "\t");
			}
		} catch (ResultColumnException e) {
			e.printStackTrace();
		}
		
	}

}
