package com.osrmt.modellibrary.reports;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import com.osframework.framework.utility.FileProcess;
import com.osframework.framework.utility.FileSystemUtil;
import com.osframework.modellibrary.reportwriter.*;

public class ArtifactDetailReport extends ReportBase {
	
	private Hashtable properties = new Hashtable();
	public static String keySql = "sql";
	// remember to add the style below
	private ReportStyle styleNormal = new ReportStyle();
	private ReportStyle styleGroup = new ReportStyle("group",10,true,false);
	private ReportStyle styleHeader = new ReportStyle("header",8,false,true);
	private ReportStyle styleGroupPage = new ReportStyle("groupPage",16,true,false);

	public ArtifactDetailReport() {
	}
	
	public ArtifactDetailReport(Hashtable properties) {
		this.properties = properties;
	}
	
	public static void main (String[] args) {
		try {
			ArtifactDetailReport report = new ArtifactDetailReport();
			FileSystemUtil.createFile("C:\\ant\\osrmt\\database\\reports","ArtifactDetailTemplate.jrxml",
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
		elements.addReportElement(new ReportQueryString(getSql()));
		Enumeration e1 = getFieldList().elements();
		while (e1.hasMoreElements()) {
			ReportElement r = (ReportElement) e1.nextElement();
			elements.addReportElement(r);
		}
		addGroups(elements);
		
		ReportBand headerBand = new ReportBand(35);
		headerBand.addChildren(getHeaderFields());	
		elements.addReportElement(new ReportPageHeader(headerBand));
		
		ReportBand band = new ReportBand(11);
		band.addChildren(getTextFields());		
		elements.addReportElement(new ReportDetail(band));
		elements.addReportElement(new ReportPageFooter());
		return elements;
	}
	
	public void addGroups(ReportElementList elements) {
		
				ReportBand groupPageBand = new ReportBand(0);
		elements.addReportElement(new ReportGroup(fieldProduct,
			new ReportGroupHeader(groupPageBand), true));

		ReportPosition pos = new ReportPosition(styleGroup, 0,5,200,15);
		ReportTextField ArtifactType = new ReportTextField(
		pos, 
		new ReportTextElement(ReportTextElement.leftAlignment),
		new ReportTextFieldExpression(fieldArtifactType));

		ReportBand groupBand = new ReportBand(20);
		ReportElementList list = new ReportElementList();
		list.addReportElement(ArtifactType);
		groupBand.addChildren(list);
		elements.addReportElement(new ReportGroup(fieldArtifactType,
			new ReportGroupHeader(groupBand), false));


	}

	public String getSql() {
		if (properties.containsKey(keySql)) {
			return properties.get(keySql).toString();
		} else {
			return "SELECT r1.display AS Product, r2.display AS ArtifactType, a.artifact_nbr as ArtifactNbr, a.artifact_name as ArtifactName, r5.display as Version, r3.display AS Status, r4.display AS Priority, a.description, a.effort as Effort, a.create_dt as CreateDate FROM artifact a, reference r1, reference r2, reference r3, reference r4, reference r5 WHERE a.active_ind=1 And a.product_ref_id=r1.ref_id And a.artifact_ref_id=r2.ref_id And a.status_ref_id=r3.ref_id And a.priority_ref_id=r4.ref_id And a.version_ref_id = r5.ref_id ORDER BY r1.display, r2.display_sequence, r5.display_sequence, r3.display_sequence";
		}
	}
	 
	
	private ReportField fieldProduct = new ReportField("Product", ReportField.VARCHAR);
	private ReportField fieldArtifactType = new ReportField("ArtifactType", ReportField.VARCHAR);
	private ReportField fieldArtifactNbr = new ReportField("ArtifactNbr", ReportField.INTEGER);
	private ReportField fieldArtifactName = new ReportField("ArtifactName", ReportField.VARCHAR);
	private ReportField fieldVersion = new ReportField("Version", ReportField.VARCHAR);
	private ReportField fieldStatus = new ReportField("Status", ReportField.VARCHAR);
	private ReportField fieldPriority = new ReportField("Priority", ReportField.VARCHAR);
	private ReportField fielddescription = new ReportField("description", ReportField.VARCHAR);
	private ReportField fieldEffort = new ReportField("Effort", ReportField.DOUBLE);
	private ReportField fieldCreateDate = new ReportField("CreateDate", ReportField.DATETIME);

	public ReportElementList getFieldList() {
		ReportElementList fields = new ReportElementList();
		
		fields.addReportElement(new ReportField("Product",ReportField.VARCHAR));
		fields.addReportElement(new ReportField("ArtifactType",ReportField.VARCHAR));
		fields.addReportElement(new ReportField("ArtifactNbr",ReportField.INTEGER));
		fields.addReportElement(new ReportField("ArtifactName",ReportField.VARCHAR));
		fields.addReportElement(new ReportField("Version",ReportField.VARCHAR));
		fields.addReportElement(new ReportField("Status",ReportField.VARCHAR));
		fields.addReportElement(new ReportField("Priority",ReportField.VARCHAR));
		fields.addReportElement(new ReportField("description",ReportField.VARCHAR));
		fields.addReportElement(new ReportField("Effort",ReportField.DOUBLE));
		fields.addReportElement(new ReportField("CreateDate",ReportField.DATETIME));
	
		return fields;
	}
	
	public ReportElementList getHeaderFields() {
		ReportElementList textfields = new ReportElementList();
				ReportPosition grpPagePos = new ReportPosition(styleGroup, 200,0,200,15);
		ReportTextField Product = new ReportTextField(
		grpPagePos, 
		new ReportTextElement(ReportTextElement.leftAlignment),
		new ReportTextFieldExpression(fieldProduct));

		textfields.addReportElement(Product);
		ReportPosition pos = new ReportPosition(styleHeader, 0,20,40,11);
		ReportStaticText ArtifactNbr = new ReportStaticText(
		pos, 
		new ReportTextElement(ReportTextElement.rightAlignment), 
		new ReportText("ArtifactNbr"));
		textfields.addReportElement(ArtifactNbr);
		pos = new ReportPosition(styleHeader, pos.nextPosX(),20,100,11);
		ReportStaticText ArtifactName = new ReportStaticText(
		pos, 
		new ReportTextElement(ReportTextElement.leftAlignment), 
		new ReportText("ArtifactName"));
		textfields.addReportElement(ArtifactName);
		pos = new ReportPosition(styleHeader, pos.nextPosX(),20,35,11);
		ReportStaticText Version = new ReportStaticText(
		pos, 
		new ReportTextElement(ReportTextElement.leftAlignment), 
		new ReportText("Version"));
		textfields.addReportElement(Version);
		pos = new ReportPosition(styleHeader, pos.nextPosX(),20,55,11);
		ReportStaticText Status = new ReportStaticText(
		pos, 
		new ReportTextElement(ReportTextElement.leftAlignment), 
		new ReportText("Status"));
		textfields.addReportElement(Status);
		pos = new ReportPosition(styleHeader, pos.nextPosX(),20,60,11);
		ReportStaticText Priority = new ReportStaticText(
		pos, 
		new ReportTextElement(ReportTextElement.leftAlignment), 
		new ReportText("Priority"));
		textfields.addReportElement(Priority);
		pos = new ReportPosition(styleHeader, pos.nextPosX(),20,100,11);
		ReportStaticText description = new ReportStaticText(
		pos, 
		new ReportTextElement(ReportTextElement.leftAlignment), 
		new ReportText("description"));
		textfields.addReportElement(description);

		return textfields;
	}

	public ReportElementList getTextFields() {
		ReportElementList textfields = new ReportElementList();
		
		ReportPosition pos = new ReportPosition(styleNormal, 0,0,40,11);
		ReportTextField ArtifactNbr = new ReportTextField(
		pos, 
		new ReportTextElement(ReportTextElement.rightAlignment), 
		new ReportTextFieldExpression(fieldArtifactNbr));
		textfields.addReportElement(ArtifactNbr);
		pos = new ReportPosition(styleNormal, pos.nextPosX(),0,100,11);
		ReportTextField ArtifactName = new ReportTextField(
		pos, 
		new ReportTextElement(ReportTextElement.leftAlignment), 
		new ReportTextFieldExpression(fieldArtifactName));
		textfields.addReportElement(ArtifactName);
		pos = new ReportPosition(styleNormal, pos.nextPosX(),0,35,11);
		ReportTextField Version = new ReportTextField(
		pos, 
		new ReportTextElement(ReportTextElement.leftAlignment), 
		new ReportTextFieldExpression(fieldVersion));
		textfields.addReportElement(Version);
		pos = new ReportPosition(styleNormal, pos.nextPosX(),0,55,11);
		ReportTextField Status = new ReportTextField(
		pos, 
		new ReportTextElement(ReportTextElement.leftAlignment), 
		new ReportTextFieldExpression(fieldStatus));
		textfields.addReportElement(Status);
		pos = new ReportPosition(styleNormal, pos.nextPosX(),0,60,11);
		ReportTextField Priority = new ReportTextField(
		pos, 
		new ReportTextElement(ReportTextElement.leftAlignment), 
		new ReportTextFieldExpression(fieldPriority));
		textfields.addReportElement(Priority);
		
		pos = new ReportPosition(styleNormal, pos.nextPosX(),0,100,11);
		ReportTextField description = new ReportTextField(
		pos, 
		new ReportTextElement(ReportTextElement.leftAlignment), 
		new ReportTextFieldExpression(fielddescription));
		textfields.addReportElement(description);

		return textfields;
	}
	
}
