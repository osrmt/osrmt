//************ AUTO GENERATED DO NOT EDIT *********//
package com.osrmt.modellibrary.reference.group;

public class ReportGroup implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final int ALLFIELDS = 1601;
	public static final int ARTIFACTDETAIL = 283;
	public static final int ARTIFACTSUMMARY = 278;

	private int ReportRefId = 0;
	public ReportGroup(int ReportRefId) {
		this.ReportRefId = ReportRefId;		
	}

	public int getReportRefId() {
		return ReportRefId;
	}

	public static ReportGroup get(int ReportRefId) {
		return new ReportGroup(ReportRefId);
	}

}
