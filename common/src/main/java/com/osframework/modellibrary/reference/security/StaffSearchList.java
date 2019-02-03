/*
    //usage
    Copyright (C) 2006  Aron Lancout Smith

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA

*/
package com.osframework.modellibrary.reference.security;

import java.util.Enumeration;
import java.util.Vector;

import com.osframework.ejb.reference.common.IReferenceMap;
import com.osframework.ejb.reference.security.ISecurity;
import com.osframework.framework.logging.Debug;
import com.osframework.modellibrary.common.ResultColumnException;
import com.osframework.modellibrary.common.ResultColumnList;
import com.osframework.modellibrary.common.ResultList;

/**
null
*/
public class StaffSearchList extends ResultList implements  Enumeration, java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Vector list = new Vector(1000);
	private ResultColumnList columns = StaffSearchModel.getResultColumnList();
	private transient Enumeration enumeration = list.elements();
	
	public StaffSearchList() {
	
	} 
	public void sort() {
	}
	public StaffSearchList(int initialCapacity) {
		list = new Vector(initialCapacity);
	}

	public Enumeration elements() {
		enumeration = list.elements();
		return enumeration;
	}
	
	public Object nextElement() {
		return enumeration.nextElement();
	}
	
	public boolean hasMoreElements() {
		return enumeration.hasMoreElements();
	}
	
	public void add(StaffSearchModel m) {
		this.list.add(m);
		
	}

	public int size() {
		return this.list.size();
	}

	public int getRowCount() {
		return list.size();
	}

	public int getColumnCount() {
		return columns.getColumnCount();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		try {
			StaffSearchModel m = (StaffSearchModel) list.get(rowIndex);
			int position = columns.getOriginalPosition(columnIndex);
			return m.getDataAt(position);
		} catch (ResultColumnException e) {
			Debug.LogException(this, e);
			return null;
		}
	}
	public Comparable getValueAt(int rowIndex) {
		StaffSearchModel m = (StaffSearchModel) list.get(rowIndex);
		return m;
	}

	public Class[] getClasses() {
		return columns.getClasses();
	}
	
	public Vector getColumnNames() {
		return columns.getColumnNames();
	}

	public void setReferenceDisplay(IReferenceMap reference, ISecurity security) {
		Enumeration e1 = list.elements();
		while (e1.hasMoreElements()) {
			StaffSearchModel m = (StaffSearchModel) e1.nextElement();
 			m.setReferenceDisplay(reference, security);
		}
	}

	public void setColumnOrder(Vector fields) throws ResultColumnException { 
		Enumeration e1 = fields.elements();
		int pos=0;
		while (e1.hasMoreElements()){
			ApplicationControlModel acm = (ApplicationControlModel) e1.nextElement();
			columns.updatePosition(acm.getControlRefDisplay(), pos, acm.getControlText());
			pos++;
		}
		columns.setColumnCount(fields.size());
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("StaffSearchList");
		sb.append("\r\n");
		Enumeration e1 = list.elements();
		while (e1.hasMoreElements()) {
			StaffSearchModel m = (StaffSearchModel) e1.nextElement();
				sb.append(m);
				sb.append("\r\n");
		}
		return sb.toString();
	}
	
	public void setModelValueAt(Object aValue, int rowIndex, int columnIndex) {
		try {
			StaffSearchModel m = (StaffSearchModel) list.get(rowIndex);
			int position = columns.getOriginalPosition(columnIndex);
			//m.setDataAt(aValue, columnIndex);
		} catch (ResultColumnException e) {
			Debug.LogException(this, e);
		}
	}
}