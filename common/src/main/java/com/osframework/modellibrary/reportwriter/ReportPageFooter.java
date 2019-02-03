package com.osframework.modellibrary.reportwriter;

public class ReportPageFooter extends ReportElement {
	
	private String nl = "\r\n";

	public ReportPageFooter() {
		initialize();
	}
	
	private void initialize() {
		
	}
	
	public String toString() {
		return "	<pageFooter>" + nl
		+ "		<band height=\"27\"  isSplitAllowed=\"true\" >" + nl
		+ "		<textField isStretchWithOverflow=\"false\" isBlankWhenNull=\"true\" evaluationTime=\"Now\" hyperlinkType=\"None\"  hyperlinkTarget=\"Self\" >" + nl
		+ "		<reportElement" + nl
		+ "			x=\"325\"" + nl
		+ "			y=\"4\"" + nl
		+ "			width=\"170\"" + nl
		+ "			height=\"19\"" + nl
		+ "			key=\"textField\"/>" + nl
		+ "		<textElement textAlignment=\"Right\">" + nl
		+ "			<font/>" + nl
		+ "		</textElement>" + nl
		+ "	<textFieldExpression   class=\"java.lang.String\"><![CDATA[\"Page \" + $V{PAGE_NUMBER} + \" of \"]]></textFieldExpression>" + nl
		+ "	</textField>" + nl
		+ "	<textField isStretchWithOverflow=\"false\" isBlankWhenNull=\"true\" evaluationTime=\"Report\" hyperlinkType=\"None\"  hyperlinkTarget=\"Self\" >" + nl
		+ "		<reportElement" + nl
		+ "			x=\"499\"" + nl
		+ "			y=\"4\"" + nl
		+ "			width=\"36\"" + nl
		+ "			height=\"19\"" + nl
		+ "			key=\"textField\"/>" + nl
		+ "		<textElement>" + nl
		+ "			<font/>" + nl
		+ "		</textElement>" + nl
		+ "	<textFieldExpression   class=\"java.lang.String\"><![CDATA[\"\" + $V{PAGE_NUMBER}]]></textFieldExpression>" + nl
		+ "	</textField>" + nl
		+ "	<textField isStretchWithOverflow=\"false\" isBlankWhenNull=\"true\" evaluationTime=\"Now\" hyperlinkType=\"None\"  hyperlinkTarget=\"Self\" >" + nl
		+ "		<reportElement" + nl
		+ "			x=\"1\"" + nl
		+ "			y=\"6\"" + nl
		+ "			width=\"209\"" + nl
		+ "			height=\"19\"" + nl
		+ "			key=\"textField\"/>" + nl
		+ "		<textElement>" + nl
		+ "			<font/>" + nl
		+ "		</textElement>" + nl
		+ "	<textFieldExpression   class=\"java.util.Date\"><![CDATA[new Date()]]></textFieldExpression>" + nl
		+ "	</textField>" + nl
		+ "	</band>" + nl
		+ "	</pageFooter>" + nl;
		
	}
}
