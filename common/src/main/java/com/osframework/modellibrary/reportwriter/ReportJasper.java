package com.osframework.modellibrary.reportwriter;

public class ReportJasper extends ReportElement {

	public ReportJasper() {
		initialize();
	}
	
	public ReportJasper(ReportElement element) {
		initialize();
		children.addReportElement(element);
	}
	
	public void initialize() {
		setName("jasperReport");
		setAttributes(getAttributeList());
	}
	
	private ReportAttributeList getAttributeList() {
		ReportAttributeList list = new ReportAttributeList();
        list.addAttribute("name","artifact_detail");
        list.addAttribute("columnCount","1");
        list.addAttribute("printOrder","Vertical");
        list.addAttribute("orientation","Portrait");
        list.addAttribute("pageWidth","595");
        list.addAttribute("pageHeight","842");
        list.addAttribute("columnWidth","270");
        list.addAttribute("columnSpacing","15");
        list.addAttribute("leftMargin","20");
        list.addAttribute("rightMargin","20");
        list.addAttribute("topMargin","30");
        list.addAttribute("bottomMargin","30");
        list.addAttribute("whenNoDataType","NoPages");
        list.addAttribute("isTitleNewPage","false");
        list.addAttribute("isSummaryNewPage","false");
        return list;
	}
	
}
