package models;

import java.util.*;

public class RequirementModel {
	
	private int requirementId;
	private String documentName;
	private String section;
	private int sequence;
	private String requirementName;
	private String description;
	private int relatedReqId;
	private GregorianCalendar createDate;
	private String version;
	private int targetVersion;
	
	
	public int getTargetVersion() {
		return targetVersion;
	}
	public void setTargetVersion(int targetVersion) {
		this.targetVersion = targetVersion;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public GregorianCalendar getCreateDate() {
		return createDate;
	}
	public void setCreateDate(GregorianCalendar createDate) {
		this.createDate = createDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDocumentName() {
		return documentName;
	}
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}
	public int getRelatedReqId() {
		return relatedReqId;
	}
	public void setRelatedReqId(int relatedReqId) {
		this.relatedReqId = relatedReqId;
	}
	public int getRequirementId() {
		return requirementId;
	}
	public void setRequirementId(int requirementId) {
		this.requirementId = requirementId;
	}
	public String getRequirementName() {
		return requirementName;
	}
	public void setRequirementName(String requirementName) {
		this.requirementName = requirementName;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public int getSequence() {
		return sequence;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	
}
