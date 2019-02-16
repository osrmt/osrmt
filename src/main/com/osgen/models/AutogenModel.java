package models;

public class AutogenModel {
	
	private String tableName;
	private String applicationName;
	private boolean searchColumnsOnly;
	
	public AutogenModel() {
		super();
	}

	public String getApplicationName() {
		return applicationName;
	}
	
	public String getControl(String javaType) {

		if (javaType.endsWith("RefId")) {
			return "UIComboBox";
		} else if (javaType.endsWith("Calendar")) {
			return "UIDateField";
		} else if (javaType.endsWith("Ind")) {
			return "UIIndicatorField";
		} else if (javaType.compareTo("double")==0) {
			return "UIDoubleField";
		} else if (javaType.compareTo("int")==0) {
			return "UIIntegerField";
		} else {
			return "UITextField";
		}
	}

	public String getModelName() {
		String s = tableName.toLowerCase();
		int pos = s.indexOf("_"); 
		if (pos > 0) {
			s = s.substring(0,pos) + s.substring(pos+1,pos+2).toUpperCase()
			+ s.substring(pos+2);
		}
		pos = s.indexOf("_", pos+1); 
		if (pos > 0) {
			s = s.substring(0,pos) + s.substring(pos+1,pos+2).toUpperCase()
			+ s.substring(pos+2);
		}
		pos = s.indexOf("_", pos+1); 
		if (pos > 0) {
			s = s.substring(0,pos) + s.substring(pos+1,pos+2).toUpperCase()
			+ s.substring(pos+2);
		}
		return s.substring(0,1).toUpperCase()
			+ s.replace("_","").substring(1)
			+ "Model";		
	}

	public String getObjectName() {
		String s= getModelName();
		return s.substring(0,1).toLowerCase()
			+ s.substring(1,s.length()-5);
	}

	public String getApplicationKey() {
		return applicationName.toUpperCase().replace(" ","").replace("_","");
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getTableName() {
		return tableName.toLowerCase();
	}

	public void setTableName(String tableName) {
		this.tableName = tableName.toLowerCase();
	}

	public boolean isSearchColumnsOnly() {
		return searchColumnsOnly;
	}

	public void setSearchColumnsOnly(boolean searchColumnsOnly) {
		this.searchColumnsOnly = searchColumnsOnly;
	}

	
}
