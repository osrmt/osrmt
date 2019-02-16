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
package com.osframework.modellibrary.reportwriter;

import java.util.GregorianCalendar;

import com.osframework.appclient.services.ReferenceServices;
import com.osframework.framework.logging.Debug;
import com.osframework.modellibrary.reference.group.SystemMessageFramework;


/**
null
*/
public class ReportModel extends ReportDataModel implements Comparable {

	private static final long serialVersionUID = 1L;
	
	private String fileName;
	private String storageDirectory;
	
	public ReportModel() {

	}
	
	public int compareTo(Object arg0) {
		return 0;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setModelColDataAt(Object o, int modelCol) {
		switch (modelCol) {
		case 1359: setFileName(((String) o)); break;
		case 877: setReportRefDisplay(((String) o)); break;
		case 728: setReportSql(((String) o)); break;
		case 1608: setStorageDirectory(((String) o)); break;
		default: super.setModelColDataAt(o, modelCol);
		}
		
	}
	
	public Object getModelColDataAt(int modelCol) {
		if (modelCol == 1359) return getFileName();
		if (modelCol == 877) return getReportRefDisplay();
		if (modelCol == 728) return getReportSql();
		if (modelCol == 1608) return getStorageDirectory();
		return super.getModelColDataAt(modelCol);
	}

	@Override
	public void updateWith(ReportModel m) {
		super.updateWith(m);
		setFileName(m.getFileName());
		setStorageDirectory(m.getStorageDirectory());
	}

	public String getStorageDirectory() {
		return storageDirectory;
	}

	public void setStorageDirectory(String storageDirectory) {
		this.storageDirectory = storageDirectory;
	}
	
	
}