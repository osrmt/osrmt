package com.osframework.modellibrary.framework;

import java.util.*;

import com.osframework.modellibrary.common.ResultList;

public class SysLogList extends ResultList implements Enumeration{
	
	private Vector list = new Vector(1000);
	private Enumeration enum1;
	
	public Enumeration elements() {
		enum1 = list.elements();
		return enum1;
	}
	
	public void sort() {
	}
	
	public boolean hasMoreElements() {
		return enum1.hasMoreElements();
	}

	public Object nextElement() {
		return enum1.nextElement();
	}
	
	public void add(SysLogModel sysLog) {
		list.add(sysLog);
	}
	
	public void clear() {
		list.clear();
	}

	public Class[] getClasses() {
		return SysLogModel.getClasses();
	}

	public Vector getColumnNames() {
		return SysLogModel.getColumnNames();
	}

	public Comparable getValueAt(int rowIndex) {
		return (Comparable) list.elementAt(rowIndex);
	}

	public int getColumnCount() {
		return SysLogModel.columnCount();
	}

	public int getRowCount() {
		return list.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		SysLogModel m = (SysLogModel) list.elementAt(rowIndex);
		if (m != null) {
			return m.getField(columnIndex);
		} else {
			return null;
		}
	}
	
	public void setModelValueAt(Object aValue, int rowIndex, int columnIndex) {
		SysLogModel m = (SysLogModel) list.elementAt(rowIndex);
		if (m != null && aValue != null) {
			m.setField(aValue, columnIndex);
		}
	}

}
