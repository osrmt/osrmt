package com.osframework.modellibrary.reportwriter;

public class ReportStyle extends ReportElement {

	private String styleName = "Normal";
	private int fontSize = 8;
	private boolean bold = false;
	private boolean underline = false;
	private boolean defaultStyle = false;
	
	public ReportStyle() {
		initialize();
	}
	
	public ReportStyle(String styleName, int fontSize, boolean bold, boolean underline) {
		setStyleName(styleName);
		setFontSize(fontSize);
		setBold(bold);
		setUnderline(underline);
		initialize();
	}
	
	public void initialize() {
		setName("style");
		setAttributes(getAttributeList());
		defaultStyle = true;
	}
	
	private ReportAttributeList getAttributeList() {
		ReportAttributeList list = new ReportAttributeList();
        list.addAttribute("name",getStyleName());
        list.addAttribute("isDefault",getBoolean(defaultStyle));
        list.addAttribute("fontName","Arial");
        list.addAttribute("fontSize","" + getFontSize());
        list.addAttribute("isBold",getBoolean(isBold()));
        list.addAttribute("isItalic","false");
        list.addAttribute("isUnderline",getBoolean(isUnderline()));
        list.addAttribute("isStrikeThrough","false");
        list.addAttribute("pdfFontName","Helvetica");
        list.addAttribute("pdfEncoding","Cp1252");
        list.addAttribute("isPdfEmbedded","false");
        return list;
	}
	
	private String getBoolean(boolean b) {
		if (b) {
			return "true";
		} else {
			return "false";
		}
	}

	public String getStyleName() {
		return styleName;
	}

	public void setStyleName(String styleName) {
		this.styleName = styleName;
	}

	public boolean isBold() {
		return bold;
	}

	public void setBold(boolean bold) {
		this.bold = bold;
	}

	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	public boolean isUnderline() {
		return underline;
	}

	public void setUnderline(boolean underline) {
		this.underline = underline;
	}
	
}
