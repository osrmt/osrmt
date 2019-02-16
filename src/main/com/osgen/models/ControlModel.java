package models;

public class ControlModel {
	
	private String controlName;
	private String rootModel;
	private String getter;
	private String setter;
	private String uiControl;
	private String version;
	private String appName;
	private String listGroup;
	private String container;
	private String referenceSearch;
	private String javaDataType;
	
	public String getJavaDataType() {
		return javaDataType;
	}
	public void setJavaDataType(String javaDataType) {
		this.javaDataType = javaDataType;
	}
	public String getReferenceSearch() {
		return referenceSearch;
	}
	public void setReferenceSearch(String referenceSearch) {
		this.referenceSearch = referenceSearch;
	}
	public String getContainer() {
		return container;
	}
	public void setContainer(String container) {
		this.container = container;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getControlName() {
		return controlName;
	}
	public String getControlVariable() {
		return controlName.substring(0,1).toLowerCase()
		+ controlName.substring(1);
	}
	public void setControlName(String controlName) {
		this.controlName = controlName;
	}
	public String getGetter() {
		return getter;
	}
	public void setGetter(String getter) {
		this.getter = getter;
	}
	public String getRootModel() {
		return rootModel;
	}
	public void setRootModel(String rootModel) {
		this.rootModel = rootModel;
	}
	public String getSetter() {
		if (setter == null) {
			return "";
		} else {
			return setter;
		}
	}
	public void setSetter(String setter) {
		this.setter = setter;
	}
	public String getUiControl() {
		return uiControl;
	}
	public void setUiControl(String uiControl) {
		this.uiControl = uiControl;
	}
	
	public String getListener() {
		if (isFormattedDate()) {
			return "TextDocListener";
		} else if (isDocumentListener()) {
			if (getUiControl().compareTo("UIIntegerField")==0) {
				return "NumberDocListener";
			} else if (getUiControl().compareTo("UIDoubleField")==0) {
				return "DoubleDocListener";
			} else {
				return "TextDocListener";
			}
		} else if (isIndicatorListener()) {
			return "IndicatorListener";
		} else if (isComboListener()) {
			return "ReferenceListListener";
		} else if (isListListener()) {
			return "ReferenceListListener";
		} else if (isUserDefined()) {
			return "UserDefinedListener";
		} else if (isCustomSearch()) {
			return "TextDocListener";
		} else {
			System.err.println("ControlModel.getListener failed to handled");
			return "TextDocListener";
		}
	}
	public boolean isFormattedDate() {
		return getUiControl().compareTo("UIDateField")==0;
	}
	public boolean isDocumentListener() {
		//TODO change this to compare type not anything else
		return getUiControl().compareTo("JTextField")==0
		|| getUiControl().compareTo("UITextField")==0
		|| getUiControl().compareTo("UIPasswordField")==0
		|| getUiControl().compareTo("UIIntegerField")==0
		|| getUiControl().compareTo("UIDoubleField")==0
		|| getUiControl().compareTo("JTextPane")==0
		|| getUiControl().compareTo("JTextArea")==0
		|| getUiControl().compareTo("UIEditorPane")==0;
	}
	public boolean isListListener() {
		return getUiControl().compareTo("JList")==0;
	}
	public boolean isIndicatorListener() {
		return getUiControl().compareTo("UIIndicatorField")==0;
	}
	public boolean isCustomControl() {
		return getUiControl().compareTo("MultiColumnList")==0
		|| getUiControl().compareTo("RelationshipList")==0
		|| getUiControl().compareTo("UIAttachmentList")==0
		|| getUiControl().compareTo("UIButton")==0
		|| getUiControl().compareTo("UIUserField")==0
		|| getUiControl().compareTo("AssignFormsButton")==0
		|| getUiControl().compareTo("ChangePasswordButton")==0
		|| getUiControl().compareTo("ApplicationCustomControl")==0;
	}
	public boolean isComboListener() {
		return getUiControl().compareTo("JComboBox")==0
		|| getUiControl().compareTo("UIComboBox")==0;
	}
	public boolean isCustomSearch() {
		return getUiControl().compareTo("ReferenceSearch")==0;
	}
	public boolean isUserDefined() {
		return getUiControl().compareTo("UserDefined")==0;
	}
	public String getConstructorColumns() {
		if (getUiControl().compareTo("JTextField")==0
		|| getUiControl().compareTo("UITextField")==0) {
			return "10";
		} else {
			return "";
		}
	}
	public String getListGroup() {
		return listGroup;
	}
	public void setListGroup(String listGroup) {
		this.listGroup = listGroup;
	}
	
}
