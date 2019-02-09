package com.osframework.modellibrary.reportwriter;

public class ReportGroupHeader extends ReportElement {

	public ReportGroupHeader(ReportBand band) {
		initialize();
		children.addReportElement(band);
	}
		
	public void initialize() {
		setName("groupHeader");
	}
	
}
