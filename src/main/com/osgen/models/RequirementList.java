package models;

import java.util.*;
import java.sql.*;

import database.*;

public class RequirementList implements  Enumeration {

	private Vector list = new Vector(1000);
	private Enumeration enumeration;
	
	public Enumeration elements() {
		enumeration = list.elements();
		return enumeration;
	}
	
	public Object nextElement() {
		return enumeration.nextElement();
	}
	
	public boolean hasMoreElements() {
		return enumeration.hasMoreElements();
	}
	
	public void add(RequirementModel bmm) {
		this.list.add(bmm);
	}
	
	public void load(Access access) throws SQLException, ClassNotFoundException {
		String sql = "select requirementId, documentName, section, sequence, requirementName,"
			+ "description, relatedReqId, createDate, targetVersion, "
			+ " version from Requirement "
			+ " order by documentname, section, sequence";
		ResultSet rset = access.executeQuery(sql);
		while (rset.next()) {
			RequirementModel mlm = new RequirementModel();
			mlm.setRequirementId(rset.getInt("requirementId"));
			mlm.setDocumentName(rset.getString("documentName"));
			mlm.setSection(rset.getString("section"));
			mlm.setSequence(rset.getInt("sequence"));
			mlm.setRequirementName(rset.getString("requirementName"));
			mlm.setDescription(rset.getString("description"));
			if (rset.wasNull()) mlm.setDescription(null);
			mlm.setRelatedReqId(rset.getInt("relatedReqId"));
			mlm.setCreateDate(Access.getCalendarDate(rset.getTimestamp("createDate")));
			mlm.setVersion(rset.getString("version"));
			mlm.setTargetVersion(rset.getInt("targetVersion"));
			
			this.add(mlm);			
		}
		rset.close();
	}

}
