package com.osframework.appclient.ui.editor;

import java.io.IOException;
import java.util.*;

import com.osframework.framework.utility.FileSystemUtil;
import com.osframework.modellibrary.reportwriter.*;

public class ReportDocument extends ReportBase {

		private static final long serialVersionUID = 1L;
		private ReportStyle styleNormal = new ReportStyle();
		private ReportStyle styleGroup = new ReportStyle("group",10,true,false);
		private ReportStyle styleHeader = new ReportStyle("header",8,false,true);
		private ReportStyle styleGroupPage = new ReportStyle("groupPage",16,true,false);
		private List<IDocumentLine> lines = null;
		private int ypos = 0;
		
		public ReportDocument() {
		}
		
		public ReportDocument(List<IDocumentLine> lines) {
			this.lines = lines;
		}
		
		public static void main (String[] args) {
			try {
				ReportDocument report = new ReportDocument();
				FileSystemUtil.createFile("C:\\temp","test.jrxml",
						report.getContent());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public String getContent() {
			StringBuffer sb = new StringBuffer(1024);
			sb.append(new ReportRoot());
			ReportJasper report = new ReportJasper();
			report.addChildren(getReportElements());
			sb.append(report);
			return sb.toString();
		}
		
		public ReportElementList getReportElements() {
			ReportElementList elements = new ReportElementList();
			elements.addReportElement(new ReportProperties());
			elements.addReportElement(styleNormal);
			elements.addReportElement(styleGroup);
			elements.addReportElement(styleHeader);
			elements.addReportElement(styleGroupPage);
			elements.addReportElement(new ReportAsIs(getXml()));
			Enumeration e1 = getFieldList().elements();
			while (e1.hasMoreElements()) {
				ReportElement r = (ReportElement) e1.nextElement();
				elements.addReportElement(r);
			}
			// this calculates the height
			ReportElementList textFields = getTextFields();
			ReportBand band = new ReportBand(ypos);
			band.addChildren(textFields);		
			elements.addReportElement(new ReportDetail(band));
			return elements;
		}


		public String getXml() {
			return "<queryString language=\"xPath\"><![CDATA[/report/line]]></queryString>\n";
		}
		 
		
		public ReportElementList getFieldList() {
			ReportElementList fields = new ReportElementList();
			for (int i=0; i<lines.size(); i++) {
				fields.addReportElement(new ReportField("fld" + i,ReportField.VARCHAR));
			}
			return fields;
		}
		
		public ReportElementList getTextFields() {
			ReportElementList textfields = new ReportElementList();

			for (int i=0; i<lines.size(); i++) {
				ReportElement re = null;
				ReportField fld = new ReportField("fld" + i, ReportField.VARCHAR);
				if (lines.get(i) instanceof ImageLineModel) {
					re = getImageReportElement(fld, i);
				} else {
					re = getTextReportElement(fld, i);
				}
				textfields.addReportElement(re);
			}
			

			return textfields;
		}
		
		private ReportElement getTextReportElement(ReportField fld, int i) {
			ReportPosition pos = new ReportPosition(styleNormal, 0,ypos,534,16);
			ReportTextField textField = new ReportTextField(
			pos, 
			new ReportTextElement(ReportTextElement.leftAlignment), 
			new ReportTextFieldExpression(fld));
			ypos += 16;
			return textField;
		}
		
		private ReportElement getImageReportElement(ReportField fld, int i) {
			ImageLineModel image = (ImageLineModel) lines.get(i);
			
			ReportPosition pos = new ReportPosition(styleNormal, 0,ypos,534,image.getValue().getHeight(null));
			ReportImage imageField = new ReportImage(
			pos, 
			new ReportGraphicElement(), 
			new ReportImageExpression(fld));
			ypos += image.getValue().getHeight(null);
			return imageField;
		}
	}
