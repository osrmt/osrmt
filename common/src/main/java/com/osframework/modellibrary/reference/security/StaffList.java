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
import java.util.Hashtable;

import com.osframework.ejb.reference.common.IReferenceMap;
import com.osframework.ejb.reference.security.ISecurity;

/**
null
*/
public class StaffList implements  Enumeration, java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Hashtable list = new Hashtable(1000);
	private transient Enumeration enumeration = list.elements();
	
	public StaffList() {
	
	} 

	public StaffList(int initialCapacity) {
		list = new Hashtable(initialCapacity);
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
	
	public void add(StaffModel m) {
		this.list.put("" + m.getStaffId(), m);
		
	}
	public StaffModel get(int id) {
		Object o = this.list.get("" + id);
		if (o == null) {
			return new StaffModel();
		} else { 
			return (StaffModel) o;
		}
	}

	public int size() {
		return this.list.size();
	}

	public void setReferenceDisplay(IReferenceMap reference, ISecurity security) {
		Enumeration e1 = list.elements();
		while (e1.hasMoreElements()) {
			StaffModel m = (StaffModel) e1.nextElement();
 			m.setReferenceDisplay(reference, security);
		}
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("StaffList");
		sb.append("\r\n");
		Enumeration e1 = list.elements();
		while (e1.hasMoreElements()) {
			StaffModel m = (StaffModel) e1.nextElement();
				sb.append(m);
				sb.append("\r\n");
		}
		return sb.toString();
	}
}