package generator;

import java.util.*;
import java.sql.*;

import models.AutogenModel;
import models.TableLibraryModel;

import utilities.FileSystemUtil;
import database.Access;

public class AutogenReports {

	private String nl = "\r\n";
	private MainGenerator maingen = null;
	public class ReportField {
		private String sqlType;
		private String column;
		private int columnSize;
		
		public String getColumn() {
			return column;
		}
		public void setColumn(String column) {
			this.column = column;
		}
		public int getColumnSize() {
			return columnSize;
		}
		public void setColumnSize(int columnSize) {
			if (columnSize > this.columnSize) {
				this.columnSize = columnSize;
			}
		}
		public String getSqlType() {
			return sqlType;
		}
		public void setSqlType(String sqlType) {
			this.sqlType = sqlType;
		}
		
	}

	public AutogenReports(MainGenerator m) {
		super();
		this.maingen = m;
	}
	
	private Vector fields = new Vector();
	private ReportField pageGroup = new ReportField();
	private Vector groups = new Vector();
	
	public void CreateReport() throws Exception {
		
		try {
			String sql = "select *"
				+ " from autogen_reports where activeInd=1";

			ResultSet rset = Db.access.executeQuery(sql);
			while (rset.next()) {
				String reportName = rset.getString("ReportName");
				String reportSql = rset.getString("sql");
				String template = rset.getString("template");
				String destination = rset.getString("destinationDir");
				int groups = rset.getInt("groups");
				int columns = rset.getInt("columns");
				
				buildFields(reportSql);
				buildGroups(groups);
				String report = createReport(reportName, template, reportSql, groups, columns);
				FileSystemUtil.CreateFile(destination, reportName + "Report.java", report);
			}
			rset.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void buildGroups(int nbrGroups) {
		Enumeration e1 = fields.elements();
		int cnt = 0;
		while (e1.hasMoreElements()) {
			ReportField f = (ReportField) e1.nextElement();
			if (cnt ==0) {
				pageGroup = f;
			} else if (cnt-1 < nbrGroups) {
				groups.add(f);
			} 
			cnt++;
		}
	}

	private void buildFields(String reportSql) throws Exception {
		Access access = new Access();
		access.connectToAccessDatabase(MainGenerator.osrmtAccessDbFile,"admin","");
		ResultSet rset = access.executeQuery(reportSql);
		ResultSetMetaData data = rset.getMetaData();
		int cnt = 0;
		boolean found = false;
		while (rset.next() && cnt < 100) {
			found = true;
			for (int i=0; i< data.getColumnCount(); i++) {
				String colName = data.getColumnName(i+1);
				String dataType = data.getColumnTypeName(i+1);
				int colSize = colName.length();
				String s = rset.getString(i+1);
				if (s != null && !rset.wasNull() && s.length() > colSize) {
					colSize = s.length();
				}
				
				if (i >= fields.size()) {
					ReportField f = new ReportField();
					f.column = colName;
					f.sqlType = dataType;
					f.setColumnSize(colSize);
					fields.add(f);
				} else {
					ReportField f = (ReportField) fields.elementAt(i);
					f.column = colName;
					f.sqlType = dataType;
					f.setColumnSize(colSize);
				}
			}
			cnt++;
		}
		rset.close();
		if (!found) throw new Exception("no data found to build report");
	}
	
	public String createReport(String reportName, String template, String sql, int groups, int columns) {
		String s = FileSystemUtil.getContents("reports",template);
		s = s.replace("!SQL!",sql);
		s = s.replace("!FIELDS!", getFields());
		s = s.replace("!REPORTELEMENTS!", getReportElements());
		s = s.replace("!HEADERFIELDS!", getHeaderFields(groups, columns));
		s = s.replace("!TEXTFIELDS!", getTextFields(groups, columns));
		s = s.replace("!GROUPS!", getGroups(groups));
		s = s.replace("!TARGETDIR!",getTargetDir(reportName));
		return s;
	}
	
	private String getTargetDir(String reportName) {
		String reportDir = maingen.props.getProperty(MainGenerator.propREPORTDIR);
		return "\"" + reportDir.replace("\\","\\\\") + "\",\"" + reportName + ".jrxml\"";
	}
	
	private String getGroups(int nbrGroups) {
		StringBuffer sb = new StringBuffer(1024);
		sb.append(getGroupPage());
		Enumeration e1 = groups.elements();
		boolean first = true;
		while (e1.hasMoreElements()) {
			ReportField f = (ReportField) e1.nextElement();
			String xpos = "0";
			String reportPos = "pos";
			String reportGroupBand = "groupBand";
			String reportList = "list";
			if (first) {
				xpos = "0";
				reportPos = "ReportPosition pos";
				reportGroupBand = "ReportBand groupBand";
				reportList = "ReportElementList list";
			}
			sb.append("		" + reportPos + " = new ReportPosition(styleGroup, " + xpos + ",5,200,15);" + nl
					+ "		ReportTextField " + f.column + " = new ReportTextField(" + nl
					+ "		pos, " + nl
					+ "		new ReportTextElement(ReportTextElement." + getAlignment(f) + ")," + nl 
					+ "		new ReportTextFieldExpression(field" + f.column + "));" + nl + nl
					+ "		" + reportGroupBand + " = new ReportBand(20);" + nl
					+ "		" + reportList + " = new ReportElementList();" + nl
					+ "		list.addReportElement(" + f.column + ");" + nl
					+ "		groupBand.addChildren(list);" + nl
					+ "		elements.addReportElement(new ReportGroup(field" + f.column + "," + nl 
					+ "			new ReportGroupHeader(groupBand), false));" + nl + nl);
			first = false;
		}
		return sb.toString();
	}
	private String getGroupPage() {
		return "		ReportBand groupPageBand = new ReportBand(0);" + nl
				+ "		elements.addReportElement(new ReportGroup(field" + pageGroup.column + "," + nl 
				+ "			new ReportGroupHeader(groupPageBand), true));" + nl + nl;
	}
	
	private String getFields() {
		StringBuffer sb = new StringBuffer(1024);
		Enumeration e1 = fields.elements();
		while (e1.hasMoreElements()) {
			ReportField f = (ReportField) e1.nextElement();
			sb.append(nl + "	private ReportField field" + f.column + " = new ReportField(\"" + f.column + "\", ReportField." + f.sqlType + ");");			
		}
		return sb.toString();
	}
	
	private String getReportElements() {
		StringBuffer sb = new StringBuffer(1024);
		Enumeration e1 = fields.elements();
		while (e1.hasMoreElements()) {
			ReportField f = (ReportField) e1.nextElement();
			sb.append(nl + "		fields.addReportElement(new ReportField(\"" + f.column + "\",ReportField." + f.sqlType + "));");
		}
		return sb.toString();
	}
	
	
	private String getHeaderFields(int groups, int columns) {
		StringBuffer sb = new StringBuffer(1024);
		Enumeration e1 = fields.elements();
		boolean first = true;
		int cnt=0;
		sb.append("		ReportPosition grpPagePos = new ReportPosition(styleGroup, 200,0,200,15);" + nl
				+ "		ReportTextField " + pageGroup.column + " = new ReportTextField(" + nl
				+ "		grpPagePos, " + nl
				+ "		new ReportTextElement(ReportTextElement." + getAlignment(pageGroup) + ")," + nl 
				+ "		new ReportTextFieldExpression(field" + pageGroup.column + "));" + nl + nl
				+ "		textfields.addReportElement(" + pageGroup.column + ");");
		while (e1.hasMoreElements() && cnt < (columns+groups)) {
			ReportField f = (ReportField) e1.nextElement();
			if (cnt > groups) {
				if (first) {
					sb.append(nl + "		ReportPosition pos = new ReportPosition(styleHeader, 0,20," + getSize(f) + ",11);");
				} else {
					sb.append(nl + "		pos = new ReportPosition(styleHeader, pos.nextPosX(),20," + getSize(f) + ",11);");
				}
				sb.append(nl + "		ReportStaticText " + f.column + " = new ReportStaticText(" + nl
						+ "		pos, " + nl
						+ "		new ReportTextElement(ReportTextElement." + getAlignment(f) + "), " + nl
						+ "		new ReportText(\"" + f.column + "\"));" + nl
						+ "		textfields.addReportElement(" + f.column + ");");
				first = false;
			}
			cnt++;
		}
		return sb.toString();
	}
	
	private String getTextFields(int groups, int columns) {
		StringBuffer sb = new StringBuffer(1024);
		Enumeration e1 = fields.elements();
		boolean first = true;
		int cnt=0;
		while (e1.hasMoreElements() && cnt < (columns+groups)) {
			ReportField f = (ReportField) e1.nextElement();
			if (cnt > groups) {
				if (first) {
					sb.append(nl + "		ReportPosition pos = new ReportPosition(styleNormal, 0,0," + getSize(f) + ",11);");
				} else {
					sb.append(nl + "		pos = new ReportPosition(styleNormal, pos.nextPosX(),0," + getSize(f) + ",11);");
				}
				sb.append(nl + "		ReportTextField " + f.column + " = new ReportTextField(" + nl
						+ "		pos, " + nl
						+ "		new ReportTextElement(ReportTextElement." + getAlignment(f) + "), " + nl
						+ "		new ReportTextFieldExpression(field" + f.column + "));" + nl
						+ "		textfields.addReportElement(" + f.column + ");");
				first = false;
			}
			cnt++;
		}
		return sb.toString();
	}
	
	private String getAlignment(ReportField f) {
		if (f.sqlType.compareTo("INTEGER")==0 || f.sqlType.compareTo("DOUBLE")==0) {
			return "rightAlignment";
		} else {
			return "leftAlignment";
		}
	}

	private String getSize(ReportField f) {
		if (f.sqlType.compareTo("INTEGER")==0 || f.sqlType.compareTo("DOUBLE")==0) {
			return "40";
		} else {
			int size = f.getColumnSize() * 5;
			if (size < 20 ) {
				size = 20;
			} else if (size > 125) {
				size = 100;
			}
			return "" + size;
		}
	}

}
