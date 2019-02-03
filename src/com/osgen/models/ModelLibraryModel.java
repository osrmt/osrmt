package models;
import java.util.*;

public class ModelLibraryModel {
	
	private String javaPackage;
	private int tableInd;
	private String tableName;
	private String version;
	private String parentTable;
	private String setParentId;
	private int tableModelInd;
	private int listModelInd;
	private String description;
	private int extendVectorInd;
	private int userDefinedInd;
	
	public int getUserDefinedInd() {
		return userDefinedInd;
	}

	public void setUserDefinedInd(int userDefinedInd) {
		this.userDefinedInd = userDefinedInd;
	}

	public int getExtendVectorInd() {
		return extendVectorInd;
	}

	public void setExtendVectorInd(int extendVectorInd) {
		this.extendVectorInd = extendVectorInd;
	}

	public int getTableModelInd() {
		return tableModelInd;
	}

	public void setTableModelInd(int tableModelInd) {
		this.tableModelInd = tableModelInd;
	}

	public String getSetParentId() {
		return setParentId;
	}

	public void setSetParentId(String setParentId) {
		this.setParentId = setParentId;
	}

	public String getParentTable() {
		if (parentTable != null) {
			return parentTable.toLowerCase();
		} else {
			return null;
		}
	}

	public void setParentTable(String parentTable) {
		if (parentTable != null) {
			this.parentTable = parentTable.toLowerCase();
		}
	}

	public String getJavaPath(String genSourceDir) {
		return genSourceDir + "\\" 
		+ getJavaPackage().replace('.','\\');
	}
	
	public String getJavaPackage() {
		return javaPackage;
	}
	public void setJavaPackage(String javaPackage) {
		this.javaPackage = javaPackage;
	}
	public int getTableInd() {
		return tableInd;
	}
	public void setTableInd(int tableInd) {
		this.tableInd = tableInd;
	}
	public String getTableName() {
		return tableName.toLowerCase();
	}
	public void setTableName(String tableName) {
		this.tableName = tableName.toLowerCase();
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
	public boolean isParent(ModelLibraryList list) {
		Enumeration e0 = list.elements();
		while (e0.hasMoreElements()) {
			ModelLibraryModel t = (ModelLibraryModel) e0.nextElement();
			if (t.getParentTable() != null && this.getTableName()!=null) {
				if (t.getParentTable().compareTo(this.getTableName())==0) {
					return true;
				}
			}
		}
		return false;
	}
	
	public String toString() {
		return "ModelLibraryModel:" + javaPackage
		+ "," + tableInd 
		+ "," + tableName;
	}
	
	public String getModelName() {
		return getClassName() + "Model";
	}

	public String getUpdateName() {
		return getClassName() + "Update";
	}

	public String getDataModelName() {
		return getClassName() + "DataModel";
	}

	public String getDbAdapterName() {
		return getClassName() + "DbAdapter";
	}

	public String getDataAdapterName() {
		return getClassName() + "DataAdapter";
	}

	public String getListName() {
		return getClassName() + "List";
	}

	public String getListVariableName() {
		return getClassName().substring(0,1).toLowerCase()
		+ getClassName().substring(1) + "List";
	}

	/*
	 * Converts a typical column name 
	 * e.g. PATIENT, PATIENT_VISIT into a 
	 * java format e.g. PatientModel, PatientVistModel
	 */
	public String getClassName() {
		String col = getTableName();
		return FormatName(col);
	}
	
	public String FormatName(String col) {
		StringBuffer sb = new StringBuffer(20);
		boolean underscored = false;
		if (col == null) {
			return null;
		}
		
		for (int i=0; i < col.length(); i++) {
			// do not retain the underscores
			if (col.substring(i,i+1).compareTo("_")!=0) {
				// capitalize if there was an underscore in
				// the last character
				if (underscored || i==0) {
					sb.append(col.substring(i,i+1).toUpperCase());
				} else {
					sb.append(col.substring(i,i+1).toLowerCase());
				}
			}
			if (col.substring(i,i+1).compareTo("_")==0) {
				underscored = true;
			} else {
				underscored = false;
			}
		}
		return sb.toString();
	}

	public int getListModelInd() {
		return listModelInd;
	}

	public void setListModelInd(int listModelInd) {
		this.listModelInd = listModelInd;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
