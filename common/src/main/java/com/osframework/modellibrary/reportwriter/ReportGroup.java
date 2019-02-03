package com.osframework.modellibrary.reportwriter;

public class ReportGroup extends ReportElement {

	public ReportGroup(ReportField field, ReportGroupHeader band) {
		initialize(field, band, false);
	}
	
	public ReportGroup(ReportField field, ReportGroupHeader band, boolean newPage) {
		initialize(field, band, newPage);
	}
	
	public void initialize(ReportField field, ReportGroupHeader band, boolean newPage) {
		setName("group");
		setAttributes(getGroupAttributes(field.getFieldName()));
		if (newPage) {
			getAttributes().addAttribute("isStartNewPage","true");
		}
		children.addReportElement(getGroupExpression(field));
		children.addReportElement(band);
	}
	
	private ReportElement getGroupExpression(ReportField field) {
		ReportElement grpexp = new ReportElement();
		grpexp.setName("groupExpression");
		grpexp.setContent("<![CDATA[$F{" + field.getFieldName() + "}]]>");
		return grpexp;
	}
	
	public ReportAttributeList getGroupAttributes(String groupName) {
		return new ReportAttributeList("name",groupName,"minHeightToStartNewPage","50");
	}

}