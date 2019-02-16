//************ AUTO GENERATED DO NOT EDIT *********//
package com.osrmt.modellibrary.reqmanager;

import java.util.Enumeration;
import com.osrmt.modellibrary.reqmanager.*;


/**
null
*/
public class ArtifactModel extends ArtifactDataModel implements Comparable {
	
	private static final long serialVersionUID = 1L;
	private static boolean useLongName = false;
	private boolean longToStringName = false;
	/*
	 * The report values are only set by the report writer bean
	 */
	private ArtifactList reportParents = new ArtifactList();
	private int reportTreeDepth = 0;
	private ArtifactList reportComponentUseCaseMainFlows = new ArtifactList();
	private ArtifactList reportComponentDetails = new ArtifactList();
	private ArtifactList reportComponentUseCaseAltFlows = new ArtifactList();
	private ArtifactList reportChildDependencies = new ArtifactList();
	private ArtifactList reportParentDependencies = new ArtifactList();
	private ArtifactDocumentList reportArtifactDocumentList = new ArtifactDocumentList(); 
	
	public ArtifactModel() {

	}
	
	public String allToString(){
		return super.toString();
	}
	
	public String toString() {
		if (longToStringName) {
			return getDisplayName();
		} else {
			return this.getArtifactName();
		}
	}
	
	public int compareTo(Object arg0) {
		if (arg0 instanceof ArtifactModel) {
			ArtifactModel m = (ArtifactModel) arg0;
			Integer i = new Integer(this.getArtifactSeq());
			Integer j = new Integer(m.getArtifactSeq());
			int comp = i.compareTo(j);
			if (comp == 0) {
				
				return this.getCreateDt().compareTo(m.getCreateDt());
			} else {
				return comp;
			}
		} else {
			return 0;
		}
	}
	
	public String getDisplayName() {
		StringBuffer sb = new StringBuffer(64);
		if (useLongName) {
			if (getArtifactName() != null && getArtifactName().length() > 0) {
				sb.append(getArtifactRefDisplay().substring(0,1));
				if (getArtifactNbr() > 0) {
					sb.append(getArtifactNbr());
				}
				sb.append(": ");
			}
			sb.append(getArtifactName());
		} else {
			sb.append(getArtifactName());
		}
		return sb.toString();
	}

	public static boolean isUseLongName() {
		return useLongName;
	}

	public static void setUseLongName(boolean useLongName) {
		ArtifactModel.useLongName = useLongName;
	}

	public boolean isLongToStringName() {
		return longToStringName;
	}

	public void setLongToStringName(boolean longToStringName) {
		this.longToStringName = longToStringName;
	}

	public ArtifactList getReportParents() {
		return reportParents;
	}

	public void setReportParents(ArtifactList parents) {
		this.reportParents = parents;
	}

	public int getReportTreeDepth() {
		return reportTreeDepth;
	}

	public void setReportTreeDepth(int treeDepth) {
		this.reportTreeDepth = treeDepth;
	}

	public ArtifactList getReportComponentDetails() {
		return reportComponentDetails;
	}

	public void setReportComponentDetails(ArtifactList reportComponentDetails) {
		this.reportComponentDetails = reportComponentDetails;
	}

	public ArtifactList getReportComponentUseCaseAltFlows() {
		return reportComponentUseCaseAltFlows;
	}

	public void setReportComponentUseCaseAltFlows(
			ArtifactList reportComponentUseCaseAltFlows) {
		this.reportComponentUseCaseAltFlows = reportComponentUseCaseAltFlows;
	}

	public ArtifactList getReportComponentUseCaseMainFlows() {
		return reportComponentUseCaseMainFlows;
	}

	public void setReportComponentUseCaseMainFlows(
			ArtifactList reportComponentUseCaseMainFlows) {
		this.reportComponentUseCaseMainFlows = reportComponentUseCaseMainFlows;
	}

	public ArtifactList getReportChildDependencies() {
		return reportChildDependencies;
	}

	public void setReportChildDependencies(ArtifactList reportChildDependencies) {
		this.reportChildDependencies = reportChildDependencies;
	}

	public ArtifactList getReportParentDependencies() {
		return reportParentDependencies;
	}

	public void setReportParentDependencies(ArtifactList reportParentDependencies) {
		this.reportParentDependencies = reportParentDependencies;
	}

	public ArtifactDocumentList getReportArtifactDocumentList() {
		return reportArtifactDocumentList;
	}

	public void setReportArtifactDocumentList(
			ArtifactDocumentList reportArtifactDocumentList) {
		this.reportArtifactDocumentList = reportArtifactDocumentList;
	}

	public void updateWith(ArtifactHistoryModel m) {

			this.setArtifactId(m.getArtifactId());
			this.setProductRefId(m.getProductRefId());
			this.setVersionRefId(m.getVersionRefId());
			this.setArtifactRefId(m.getArtifactRefId());
			this.setArtifactNbr(m.getArtifactNbr());
			this.setArtifactSeq(m.getArtifactSeq());
			this.setArtifactLevelRefId(m.getArtifactLevelRefId());
			this.setComponentTypeRefId(m.getComponentTypeRefId());
			this.setLastUpdatedBaselineId(m.getLastUpdatedBaselineId());
			this.setArtifactName(m.getArtifactName());
			this.setDescription(m.getDescription());
			this.setStatusRefId(m.getStatusRefId());
			this.setPriorityRefId(m.getPriorityRefId());
			this.setComplexityRefId(m.getComplexityRefId());
			this.setAuthorRefId(m.getAuthorRefId());
			this.setAssignedRefId(m.getAssignedRefId());
			this.setCategoryRefId(m.getCategoryRefId());
			this.setAssignedUserId(m.getAssignedUserId());
			this.setAssignedUserGroupId(m.getAssignedUserGroupId());
			this.setEffort(m.getEffort());
			this.setRationale(m.getRationale());
			this.setOrigin(m.getOrigin());
			this.setGoal(m.getGoal());
			this.setContext(m.getContext());
			this.setPrecondition(m.getPrecondition());
			this.setPostcondition(m.getPostcondition());
			this.setSummary(m.getSummary());
			this.setExternalReferences(m.getExternalReferences());
			this.setPlannedVersionRefId(m.getPlannedVersionRefId());
			this.setLastModifiedVersionRefId(m.getLastModifiedVersionRefId());
			this.setRemovedInd(m.getRemovedInd());
			this.setModuleRefId(m.getModuleRefId());
			this.setOriginCategoryRefId(m.getOriginCategoryRefId());
			this.setCreateDt(m.getCreateDt());
			this.setCreateUserId(m.getCreateUserId());
			this.setUpdateDt(m.getUpdateDt());
			this.setUpdateUserId(m.getUpdateUserId());
			this.setUpdateCount(m.getUpdateCount());
			this.setActiveInd(m.getActiveInd());
			this.setSystemAssignedVersionNbr(m.getSystemAssignedVersionNbr());
			this.setRecordTypeRefId(m.getRecordTypeRefId());
			this.setCustomText1(m.getCustomText1());
			this.setCustomText2(m.getCustomText2());
			this.setCustomText3(m.getCustomText3());
			this.setCustomText4(m.getCustomText4());
			this.setCustom1RefId(m.getCustom1RefId());
			this.setCustom2RefId(m.getCustom2RefId());
			this.setCustom3RefId(m.getCustom3RefId());
			this.setCustom4RefId(m.getCustom4RefId());
			this.setCustomDate1(m.getCustomDate1());
			this.setCustomDate2(m.getCustomDate2());
			this.setCustomMemo1(m.getCustomMemo1());
			this.setCustomMemo2(m.getCustomMemo2());
			this.setCustomInt1(m.getCustomInt1());
			this.setCustomInt2(m.getCustomInt2());
			this.setCustomDouble1(m.getCustomDouble1());

	}
		
}