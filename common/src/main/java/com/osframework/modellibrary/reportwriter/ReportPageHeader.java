package com.osframework.modellibrary.reportwriter;

public class ReportPageHeader extends ReportElement {

	public ReportPageHeader(ReportBand band) {
		initialize();
		children.addReportElement(band);
	}
		
	public void initialize() {
		setName("pageHeader");
	}
	
}
