package com.osframework.modellibrary.reportwriter;

public class ReportProperties extends ReportElement {
	
	public ReportProperties() {
		initialize();
	}
	
	public void initialize() {
		children.addReportElement(new ReportElement("property", 
				new ReportAttributeList("name","ireport.scriptlethandling","value","0")));
		
		children.addReportElement(new ReportElement("property", 
				new ReportAttributeList("name","ireport.encoding","value","UTF-8")));
		
		children.addReportElement(new ReportElement("import", new ReportAttribute("value","java.util.*")));
		
		children.addReportElement(new ReportElement("import", new ReportAttribute("value","net.sf.jasperreports.engine.*")));
		
		children.addReportElement(new ReportElement("import", new ReportAttribute("value","net.sf.jasperreports.engine.data.*")));
	}

}
