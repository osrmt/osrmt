package com.osframework.modellibrary.reportwriter;

import com.osframework.framework.utility.FileProcess;

public class ReportRoot extends ReportElement {

	private String content;
	
	public ReportRoot() {
		content = 
			"<?xml version=\"1.0\" encoding=\"UTF-8\"  ?>" + FileProcess.nl() + 
			"<!-- Created for OSRMT Open Source Requirements Management Tool by Aron Smith -->"  + FileProcess.nl() + 
			"<!DOCTYPE jasperReport PUBLIC \"//JasperReports//DTD Report Design//EN\" \"http://jasperreports.sourceforge.net/dtds/jasperreport.dtd\">" + FileProcess.nl();

	}
	
	public static ReportRoot get() {
		return new ReportRoot();
	}
	
	public String toString() {
		return content;
	}
	
	
}
