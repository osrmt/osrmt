//************ AUTO GENERATED DO NOT EDIT *********//
package com.osframework.modellibrary.reference.group;

public class ReportFramework implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final int ALLFIELDS = 1601;

	private int ReportRefId = 0;
	public ReportFramework(int ReportRefId) {
		this.ReportRefId = ReportRefId;		
	}

	public int getReportRefId() {
		return ReportRefId;
	}

	public static ReportFramework get(int ReportRefId) {
		return new ReportFramework(ReportRefId);
	}

}
