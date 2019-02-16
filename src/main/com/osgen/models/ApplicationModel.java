package models;

public class ApplicationModel {
	
	private String javaPackage;
	private String appName;
	private String version;
	private String modelImport;
	private String template;
	private String description;
	private String controlPanel;
	private int securityRefInd;
	private int tabsInd;
	private String userDefinedModel;
	private int controlWidth = 0;
	private int formActionsInd = 0;
	private int JPanelInd = 0;
	private int dialogInd;
	
	public int getJPanelInd() {
		return JPanelInd;
	}

	public void setJPanelInd(int panelInd) {
		JPanelInd = panelInd;
	}

	public int getFormActionsInd() {
		return formActionsInd;
	}

	public void setFormActionsInd(int formActionsInd) {
		this.formActionsInd = formActionsInd;
	}

	public int getControlWidth() {
		return controlWidth;
	}

	public void setControlWidth(int controlWidth) {
		this.controlWidth = controlWidth;
	}

	public String getUserDefinedModel() {
		return userDefinedModel;
	}

	public void setUserDefinedModel(String userDefinedModel) {
		this.userDefinedModel = userDefinedModel;
	}

	public int getTabsInd() {
		return tabsInd;
	}

	public void setTabsInd(int tabsInd) {
		this.tabsInd = tabsInd;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getBaseControllerName() {
		return appName + "BaseController";
	}
	
	public String getControllerName() {
		return appName + "Controller";
	}
	
	public String getToolsName() {
		return appName + "Tools";
	}
	
	public String getActionsName() {
		return appName + "Actions";
	}
	
	public String getUIName() {
		return appName + "UI";
	}
	
	public String getModelImport() {
		return modelImport;
	}


	public void setModelImport(String modelImport) {
		this.modelImport = modelImport;
	}


	public String getJavaPath(String genSourceDir) {
		return genSourceDir + "\\" 
		+ getJavaPackage().replace('.','\\');
	}
	

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
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

	public String toString() {
		return "ApplicationModel:" + appName
		+ "," + javaPackage;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getControlPanel() {
		return controlPanel;
	}

	public void setControlPanel(String controlPanel) {
		this.controlPanel = controlPanel;
	}

	public int getSecurityRefInd() {
		return securityRefInd;
	}

	public void setSecurityRefInd(int securityRefInd) {
		this.securityRefInd = securityRefInd;
	}

	public int getDialogInd() {
		return dialogInd;
	}

	public void setDialogInd(int dialogInd) {
		this.dialogInd = dialogInd;
	}
	
	
}
