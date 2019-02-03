package models;

public class EjbLibraryModel {
	
	private String javaPackage;
	private String bean;
	private String version;
	private int overwriteBeanInd;
	private String tableName;
	private String beanType;
	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBeanType() {
		return beanType;
	}
	
	public boolean isEntity() {
		return beanType.compareTo("Entity")==0;
	}
	
	public boolean isSession() {
		return beanType.compareTo("Session")==0;
	}

	public void setBeanType(String beanType) {
		this.beanType = beanType;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		if (tableName != null) {
			this.tableName = tableName.toLowerCase();
		}
	}

	public int getOverwriteBeanInd() {
		return overwriteBeanInd;
	}

	public void setOverwriteBeanInd(int overwriteBeanInd) {
		this.overwriteBeanInd = overwriteBeanInd;
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
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
	public String getBean() {
		return bean;
	}

	public void setBean(String bean) {
		this.bean = bean;
	}
	
	public String getBeanName() {
		return bean + "Bean";
	}
	
	public String getDataAdapterName() {
		return getBeanName().replaceAll("UpdateBean","DataAdapter").replaceAll("SearchBean","SearchDataAdapter");	
	}
	
	public String toString() {
		return "EjbLibraryModel:" + javaPackage
		+ "," + bean;
	}
}
