package com.osframework.modellibrary.reportwriter;

public class ReportPosition extends ReportElement {
	
	private int x;
	private int width;
	
	public int nextPosX(){
		return x+width+5;
	}

	public ReportPosition(ReportStyle style, int x, int y, int width, int height) {
		setName("reportElement");
		this.x = x;
		this.width = width;
		ReportAttributeList list = new ReportAttributeList();
		list.addAttribute("style", style.getStyleName());
        list.addAttribute("x","" + x);
        list.addAttribute("y","" + y);
        list.addAttribute("width","" + width);
        list.addAttribute("height","" + height);
        list.addAttribute("key","textField");
        list.addAttribute("isRemoveLineWhenBlank","true");
        list.addAttribute("positionType","Float");
		setAttributes(list);
	}
	
}
