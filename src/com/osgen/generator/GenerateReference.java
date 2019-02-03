//TODO Generate models with a useful toString() output.
//TODO Generate models with a check that all not null db fields are filled out
package generator;

import java.util.Enumeration;
import java.sql.*;
import database.*;
import models.*;
import utilities.FileSystemUtil;

public class GenerateReference {
	private MainGenerator maingen = null;
	private String nl = "\r\n";
	
	public GenerateReference(MainGenerator m) {
		try {
			this.maingen = m;
			String genSourceDir = maingen.props.getProperty(MainGenerator.propGENSRCDIR);
			String frameworkSrcDir = maingen.props.getProperty(MainGenerator.propFRAMEWORKSRCDIR);
			String javaPath = genSourceDir + "\\com\\" + MainGenerator.root + "\\modellibrary\\reference\\group\\";
			String sysJavaPath = frameworkSrcDir + "\\com\\" + MainGenerator.framework + "\\modellibrary\\reference\\group\\";
			FileSystemUtil.CreateDirectory(javaPath);
			FileSystemUtil.CreateDirectory(sysJavaPath);
			CreateGroups(false, javaPath, "package com." + MainGenerator.root + ".modellibrary.reference.group;");
			CreateGroups(true, sysJavaPath, "package com." + MainGenerator.framework + ".modellibrary.reference.group;");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void CreateGroups(boolean framework, String javaPath, String packageName) throws Exception {
		
		StringBuffer sbg = new StringBuffer(1024);
		
		Access access = new Access();
		access.connectToAccessDatabase(MainGenerator.osrmtAccessDbFile,"admin","");
		String sql = "select r.ref_id, r.display_code, r.display, g.unique_display_code_ind, g.reference_group, g.reference_group as group_display, " 
					+ " g.system_assigned_version_nbr, g.framework_ind"
					+ " from reference_group g"
					+ " left join reference as r on g.reference_group = r.reference_group"
					+ " where g.reference_group"
					+ " order by g.reference_group_id, r.display_code";

		ResultSet rset = access.executeQuery(sql);
		String prevGroup = "";
		String prevGroupDisplay = "";
		StringBuffer sb = new StringBuffer();
		String displayCode = "";
		int refId = 0;
		while (rset.next()) {
			int frameworkInd = rset.getInt("framework_ind");
			String group = rset.getString("reference_group");
			refId = rset.getInt("ref_id");
				
			displayCode = rset.getString("display_code");
			if (rset.wasNull() || displayCode == null || displayCode.compareTo("")==0) {
				displayCode = null;
			}
			String groupDisplay = rset.getString("group_display");
			if (group.compareTo(prevGroup)!=0) {
				String className = prevGroupDisplay + "Group";
				if (framework) {
					className = prevGroupDisplay + "Framework";
				}
				String content = "//************ AUTO GENERATED DO NOT EDIT *********//" + nl
				+ packageName + nl + nl
				+ "public class " + className + " implements java.io.Serializable {" + nl + nl
				+ "	private static final long serialVersionUID = 1L;" + nl + nl
				+ sb.toString() + nl + getMethods(framework, prevGroupDisplay)
				+ "}" + nl;
				if (prevGroupDisplay.compareTo("")!=0) {
					sbg.append("	public static final String " + prevGroupDisplay + " = \"" + prevGroup + "\";" + nl + nl);
				}
				FileSystemUtil.CreateFile(javaPath, className + ".java", content);
				sb = new StringBuffer();
				//TODO change from displaysequence to something more defintive
				//     to determine if the codes should be listed
				if (displayCode != null  && rset.getInt("unique_display_code_ind")==1) {
					sb.append("	public static final int " + displayCode + " = " + refId + ";" + nl);
				}
			} else {
				if (framework && frameworkInd ==0) {
					// do not add values to the framework group files but
					// we need the groups for parameters to ref_id's
					//TODO need a full split of framework/application reference
					// this changes forces the framework to be rebuilt per app (potentially)
				} else {
					//TODO it also conflicts with ordering values...
					if (displayCode != null  && rset.getInt("unique_display_code_ind")==1) {
						sb.append("	public static final int " + displayCode + " = " + refId + ";" + nl);
					}
				}
			}
			prevGroup = group;
			prevGroupDisplay = groupDisplay;
		}
		rset.close();
		
		sbg.append("	public static final String " + prevGroupDisplay + " = \"" + prevGroup + "\";" + nl + nl);
		String className = prevGroupDisplay + "Group";
		if (framework) {
			className = prevGroupDisplay + "Framework";
		}
		String content = MainGenerator.getLicense(null) + nl
		+ packageName + nl + nl
		+ "public class " + className + " implements java.io.Serializable {" + nl + nl
		+ "	private static final long serialVersionUID = 1L;" + nl + nl
		+ sb.toString() + nl+ getMethods(framework, prevGroupDisplay)
		+ "}" + nl;
		FileSystemUtil.CreateFile(javaPath, className + ".java", content);
		
		String contentGroup = MainGenerator.getLicense(null) + nl
		+ packageName + nl + nl
		+ "public class ReferenceGroup implements java.io.Serializable {" + nl + nl
		+ sbg.toString() + nl
		+ "}" + nl;
		FileSystemUtil.CreateFile(javaPath, "ReferenceGroup.java", contentGroup);

	}
	//TODO Could validate the instantiating ref id exists
	private String getMethods(boolean framework, String groupName) {
		if (groupName == null || groupName.compareTo("")==0) {
			return "";
		}
		StringBuffer sb = new StringBuffer(256);
		String groupRefId = groupName.substring(0,1) + groupName.substring(1) + "RefId";
		sb.append("	private int " + groupRefId + " = 0;" + nl);  	
		if (framework) {
			sb.append("	public " + groupName + "Framework(int " + groupRefId + ") {" + nl);
		} else {
			sb.append("	public " + groupName + "Group(int " + groupRefId + ") {" + nl);
		}
		
		sb.append("		this." + groupRefId + " = " + groupRefId + ";		" + nl
		+ "	}" + nl + nl);
	
		sb.append("	public int get" + groupRefId + "() {" + nl
				+ "		return " + groupRefId + ";" + nl
				+ "	}" + nl + nl);
		if (framework) {
			sb.append("	public static " + groupName + "Framework get(int " + groupName + "RefId) {" + nl
				+ "		return new " + groupName + "Framework(" + groupName + "RefId);" + nl
				+ "	}" + nl + nl);
		} else {
			sb.append("	public static " + groupName + "Group get(int " + groupName + "RefId) {" + nl
					+ "		return new " + groupName + "Group(" + groupName + "RefId);" + nl
					+ "	}" + nl + nl);
		}
		return sb.toString();
	}
	

}
