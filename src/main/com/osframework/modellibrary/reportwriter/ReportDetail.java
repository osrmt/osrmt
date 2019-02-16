package com.osframework.modellibrary.reportwriter;

public class ReportDetail extends ReportElement {

	public ReportDetail(ReportBand band) {
		initialize();
		children.addReportElement(band);
	}
		
	public void initialize() {
		setName("detail");
	}
	
}
