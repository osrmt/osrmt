package com.osframework.modellibrary.reportwriter;

public class ReportBand extends ReportElement {

	private int bandHeight = 15;
	
	public ReportBand() {
		initialize();
	}
	
	public ReportBand(int bandHeight) {
		this.bandHeight = bandHeight;
		initialize();
	}
	
	public void initialize() {
		setName("band");
		setAttributes(getBandAttributes(bandHeight));
	}
	
	public ReportAttributeList getBandAttributes(int bandHeight) {
		return new ReportAttributeList("height","" + bandHeight,"isSplitAllowed","true");
	}
}
