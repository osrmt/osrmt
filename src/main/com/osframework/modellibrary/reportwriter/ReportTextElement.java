package com.osframework.modellibrary.reportwriter;

import com.osframework.framework.logging.Debug;

public class ReportTextElement extends ReportElement {
	
	public final static int leftAlignment = 0;
	public final static int rightAlignment = 1;
	public final static int centerAlignment = 2;

	public ReportTextElement(int alignment) {
		setName("textElement");
		ReportAttributeList list = new ReportAttributeList();
        list.addAttribute("textAlignment",getAlignment(alignment));
		setAttributes(list);
	}
	
	public static String getAlignment(int alignment) {
		switch (alignment) {
		case leftAlignment: return "Left";
		case rightAlignment: return "Right";
		case centerAlignment: return "Center";
		}
		Debug.LogWarning("ReportTextElement", "alignment " + alignment + " not found");
		return "Left";
	}
	
	
}
