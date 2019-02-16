//TODO Generate models with a useful toString() output.
//TODO Generate models with a check that all not null db fields are filled out
package generator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import models.*;
import utilities.FileSystemUtil;

public class GenerateApplications {
	private MainGenerator maingen = null;
	private String nl = "\r\n";
	
	public GenerateApplications(MainGenerator m) {
		this.maingen = m;
	}

	public void CreateApplications() throws java.io.IOException {
		
		String genSourceDir = maingen.props.getProperty(MainGenerator.propGENSRCDIR);
		Enumeration e1 = maingen.getApplicationLibraryList().elements();
		// each model library has a package of format example
		// com." + MainGenerator.root + ".modellibrary.registration.patient
		// each . will equate to a directory
		while (e1.hasMoreElements()) {
			// Data Model
			ApplicationModel am = (ApplicationModel) e1.nextElement();
			
			String basecontrollerSource = getBaseControllerSource(am);
			String frameworkSourceDir = maingen.props.getProperty(MainGenerator.propFRAMEWORKSRCDIR);
			String javaPath;
			if (am.getJavaPackage().indexOf("framework")>0) {
				javaPath = am.getJavaPath(frameworkSourceDir);
			} else {
				javaPath = am.getJavaPath(genSourceDir);
			}
			FileSystemUtil.CreateDirectory(javaPath);
			FileSystemUtil.CreateFile(javaPath, am.getAppName() + "BaseController.java", basecontrollerSource);
			
			String controllerSource = getControllerSource(am);
			FileSystemUtil.CreateDirectory(javaPath);
			if (!FileSystemUtil.Exists(javaPath, am.getAppName() + "Controller.java")) {
				FileSystemUtil.CreateFile(javaPath, am.getAppName() + "Controller.java", controllerSource);
			}
			String actionsSource = getActionsSource(am);
			String toolsSource = getToolsSource(am);
			if (am.getFormActionsInd()==1 && !FileSystemUtil.Exists(javaPath, am.getAppName() + "Actions.java")) {
				FileSystemUtil.CreateFile(javaPath, am.getAppName() + "Actions.java", actionsSource);
				FileSystemUtil.CreateFile(javaPath, am.getAppName() + "Tools.java", toolsSource);
			}
			
			String uiSource = getUISource(am, maingen.props.getProperty(MainGenerator.propUITEMPLATEDIR));
			FileSystemUtil.CreateDirectory(javaPath);
			FileSystemUtil.CreateFile(javaPath, am.getAppName() + "UI.java", uiSource);
			
		}
	}
	
	public String getUISource(ApplicationModel am, String path) {
		String template = FileSystemUtil.getContents(path, am.getTemplate() + ".java");
		String s = MainGenerator.getLicense(am.getDescription()) + nl
		+ template.replace(am.getTemplate(),am.getUIName());
		s = s.replace("com." + MainGenerator.root + ".appclient.templates",am.getJavaPackage());
		s = s.replace("//controlPanel = ","controlPanel = " + am.getControlPanel() + ";");
		String classComments = 	"/**" + nl
		+ am.getDescription() + nl
		+ "*/" + nl;
		s = s.replace("/* comments */" + nl, classComments);
		if (am.getDialogInd()==0) {
			s = s.replace("JDialog","JFrame");
			s = s.replace("JFrame frame","");
			s = s.replace("super(frame, false);","super();");
		}
		s = s.replace("FrameTitle",am.getAppName());
		return s.replace("public void addControls(){}",getUIControls(am));
	}
	
	private String getUIControls(ApplicationModel am) {
		StringBuffer sb = new StringBuffer(8*1024);
		//if (am.getSecurityRefInd()==1) {
			sb.append(nl + "	public void addControls(ApplicationControlList controls) {" + nl);
	/*				+ "		ApplicationControlList controls = SecurityServices.getAppControls(viewRefId, ApplicationGroup."+ 
					am.getAppName().toUpperCase() + ");" + nl);
		} else if (am.getSecurityRefInd()==2) {
			sb.append(nl + "	public void addControls(int appTypeRefId) {" + nl
					+ "		ApplicationControlList controls = SecurityServices.getAppControlsByUser(appTypeRefId, ApplicationGroup."+ 
					am.getAppName().toUpperCase() + ");" + nl);
		} else {
			sb.append(nl + "	public void addControls() {" + nl
					+ "		ApplicationControlList controls = SecurityServices.getAppControlsByUser(ApplicationGroup."+ 
					am.getAppName().toUpperCase() + ");" + nl);
		}
		*/
		sb.append("		DefaultFormBuilder builder = LayoutUtility.getDefaultBuilder(controlColumns, " + am.getControlWidth() + ");" + nl);
		if (am.getTabsInd()==1) {
			sb.append("		JTabbedPane tabbedPane = new JTabbedPane();"+ nl
	        + "		tabbedPane.putClientProperty(\"jgoodies.noContentBorder\", Boolean.TRUE);" + nl);
		}
				sb.append( "		java.util.Enumeration e1 = controls.elements();" + nl
				+ "		while (e1.hasMoreElements()) {" + nl
				+ "			ApplicationControlModel rm = (ApplicationControlModel) e1.nextElement();" + nl);
		Enumeration e1 = maingen.getControlList().elements(); 
		while (e1.hasMoreElements()) {
			ControlModel cm = (ControlModel) e1.nextElement();
			if (cm.getAppName().compareTo(am.getAppName())==0) {
				sb.append("			LayoutUtility.addControl(rm, \"" + cm.getControlName()+ "\", builder, rm.getControlText(), get" + cm.getControlName() + "());" + nl);
			}
		}
		sb.append("			LayoutUtility.addControl(rm, \"User Defined\", builder, rm.getControlText(), (Component) userDefinedList.get(\"\" + rm.getAppControlUserDefinedId()));" + nl);
		sb.append("			if (rm.getControlTypeRefDisplay().compareTo(\"Separator\")==0) {" + nl
		+ "				builder.nextLine();" + nl
		+ "				builder.append(\"\");" + nl
		+ "				builder.nextLine();" + nl
		+ "				builder.append(rm.getControlText());" + nl
		+ "				builder.nextLine();" + nl);
		if (am.getTabsInd()==1) {
			sb.append("			} else if (rm.getControlTypeRefDisplay().compareTo(\"Tab\")==0){" + nl
			+ "				tabbedPane.addTab(rm.getControlText(),builder.getPanel());" + nl
			+ "				builder = LayoutUtility.getDefaultBuilder(controlColumns, " + am.getControlWidth() + ");" + nl);
		}
		sb.append("			}" + nl);
		sb.append("		}" + nl);
		if (am.getTabsInd()==1) {
			sb.append(
					"		if (tabbedPane.getTabCount()==0) {" + nl
					+ "			tabbedPane.addTab(\"\",builder.getPanel());" + nl
					+ "		}" + nl
					+ "		getControlPanel().add(tabbedPane, BorderLayout.CENTER);"+nl);
		} else {
			sb.append("		getControlPanel().add(builder.getPanel(), BorderLayout.CENTER);"+nl);
		}
		sb.append("	}" + nl + nl);
		sb.append("	public void addUserDefined(Component c, int id) {" + nl
				+ "			userDefinedList.put(\"\" + id, c);" + nl
				+ "	}" + nl
				+ "	private java.util.Hashtable userDefinedList = new java.util.Hashtable();" + nl);

		Enumeration e2 = maingen.getControlList().elements();
		while (e2.hasMoreElements()) {
			ControlModel cm = (ControlModel) e2.nextElement();
			if (cm.getAppName().compareTo(am.getAppName())==0) {
				sb.append("	private " + cm.getUiControl() + " " + cm.getControlVariable() + " = null;" + nl);
			}
		}
		sb.append(nl + nl);
		
		Enumeration e3 = maingen.getControlList().elements();
		while (e3.hasMoreElements()) {
			ControlModel cm = (ControlModel) e3.nextElement();
			if (cm.getAppName().compareTo(am.getAppName())==0) {
				sb.append("	public " + cm.getUiControl() + " get" + cm.getControlName() + "() {" + nl
					+ "		if (this." + cm.getControlVariable() + " == null) {" + nl
					+ "			this." + cm.getControlVariable() + " = new " + cm.getUiControl() + "();" + nl
					+ "			this." + cm.getControlVariable() + ".setBackground(Color.YELLOW);" + nl
					+ "		}" + nl);
				if (cm.isFormattedDate()) {
					sb.append("		this." + cm.getControlVariable() + ".setPreferredSize(new Dimension(100,20));" + nl);
				}
				sb.append("		return " + cm.getControlVariable() + ";" + nl 
					+ "	}" + nl + nl);
				sb.append("	public void set" + cm.getControlName() + "(" + cm.getUiControl() + " control) {" + nl
					+ "		this." + cm.getControlVariable() + " = control;" + nl
					+ "	}" + nl + nl);
			}
		}
		sb.append("	public JPanel getMainPanel() { " + nl
				+ "		return getJContentPane();" + nl
				+ "	}" + nl + nl);
		return sb.toString();
	}
	
	public String getControllerSource(ApplicationModel am) {
		StringBuffer sb = new StringBuffer(8*1024);
		sb.append(MainGenerator.getLicense(am.getDescription()) + "package " + am.getJavaPackage() + ";" + nl
				+ "" + nl
				+ "import javax.swing.event.*;" + nl
				+ "import javax.swing.*;" + nl
				+ "import java.awt.event.*;" + nl
				+ "import java.awt.*;" + nl
				+ "import java.util.*;" + nl
				+ "" + nl
				+ am.getModelImport() + nl
				+ "import com." + MainGenerator.root + ".appclient.system.*;" + nl
				+ "import com." + MainGenerator.root + ".appclient.services.*;" + nl
				+ "import com." + MainGenerator.framework + ".appclient.ui.common.*;" + nl
				+ "import com." + MainGenerator.framework + ".appclient.ui.listeners.*;" + nl
				+ "import com." + MainGenerator.framework + ".appclient.ui.controls.*;" + nl
				+ "import com." + MainGenerator.framework + ".modellibrary.reference.common.*;" + nl
				+ "import com." + MainGenerator.framework + ".modellibrary.reference.security.*;" + nl
				+ "import com." + MainGenerator.root + ".modellibrary.reference.group.*;" + nl
				+ "import com." + MainGenerator.framework + ".framework.logging.*;" + nl
				+ "import com." + MainGenerator.framework + ".framework.utility.*;" + nl
				+ "import com." + MainGenerator.framework + ".appclient.services.*;" + nl
				+ "" + nl
				+ "" + nl
				+ "/**" + nl
				+ am.getDescription() + nl
				+ "*/" + nl
				+ "public class " + am.getControllerName() + " extends " + am.getBaseControllerName() + " {" + nl + nl
				+ " // private MultiColumnList yourCustomControl = new MultiColumnList();" + nl 
				+ " // private UITreeModel yourCustomModel;" + nl);
				if (am.getFormActionsInd() == 1) {
					sb.append("	private " + am.getActionsName() + " actions = new " 
							+ am.getActionsName() + "();" + nl
							+ "	private ApplicationActionList applicationActionList;" + nl);
				}
				sb.append("	private JFrame frame;" +nl + nl
				+ "	public ApplicationSecurityUserFormController(JFrame frame) {" + nl
				+ "		super(frame);" + nl
				+ "		this.frame = frame;" +nl 
				+ "	}");
				sb.append(nl + "	/**" + nl
				+ "	 * @param args" + nl
				+ "	 */" + nl );
				if (maingen.getControlList().getFirstModel(am.getAppName()).length() > 0) {
					sb.append("	public void start (" + maingen.getControlList().getFirstModel(am.getAppName()) + " m) {" + nl
					+ "		//ApplicationControlList list = SecurityServices.getAppControls(ApplicationGroup.YourApp);" + nl
					+ "		ApplicationControlList list = new ApplicationControlList();" + nl
					+ "		super.initialize(list, m); "+ nl);
				} else {
					sb.append("	public void start () {" + nl
							+ "		//ApplicationControlList list = SecurityServices.getAppControls(ApplicationGroup.YourApp);" + nl
							+ "		ApplicationControlList list = new ApplicationControlList();" + nl
							+ "		super.initialize(list); "+ nl);
				}
				sb.append("		addControls();" + nl
				+ "		addListeners();" + nl
				+ "		initForm();" + nl
				+ "	}" + nl + nl
				+ "	private void initForm() {" + nl
				+ "		ui.setLocation(UIProperties.getPoint100_100());" + nl
				+ "		//ui.setSize(UIProperties.getWINDOW_SIZE_640_480());" + nl
				+ "		ui.setTitle(\"\");" + nl
				+ "		ui.setVisible(true);" + nl
				+ "	}" + nl + nl
				+ "	private void addControls() {" + nl);
				if (am.getFormActionsInd()==1) {
					sb.append( "		applicationActionList = actions.getActions(this);" + nl
							+ "		" + am.getToolsName() + " tools = new " + am.getToolsName() + "();" + nl 
							+ "		UIToolBar toolBar = tools.getToolBar(applicationActionList);" + nl
							+ "		ui.setJMenuBar(tools.getMenuBar(applicationActionList));" + nl);
				}
				sb.append("	}" + nl + nl
						+ "	private void addListeners() {" + nl
						+ "		ui.getPanelOkCancel().getCmdCancel().addActionListener(new UIActionListener(ui){" + nl
						+ "			public void actionExecuted(ActionEvent e) throws Exception {" + nl
						+ "				ui.dispose();" + nl
						+ "			}" + nl
						+ "		});" + nl
						+ "	}" + nl + nl);					
				sb.append( "}");
		
		return sb.toString();
		
	}
	
	public String getActionsSource(ApplicationModel am) {
		StringBuffer sb = new StringBuffer(8*1024);
		sb.append(MainGenerator.getLicense(am.getDescription()) + "package " + am.getJavaPackage() + ";" + nl
				+ "" + nl
				+ "import javax.swing.event.*;" + nl
				+ "import javax.swing.*;" + nl
				+ "import java.awt.event.*;" + nl
				+ "import java.awt.*;" + nl
				+ "import java.util.*;" + nl
				+ "" + nl
				+ am.getModelImport() + nl
				+ "import com." + MainGenerator.root + ".appclient.system.*;" + nl
				+ "import com." + MainGenerator.root + ".appclient.services.*;" + nl
				+ "import com." + MainGenerator.framework + ".appclient.ui.common.*;" + nl
				+ "import com." + MainGenerator.framework + ".appclient.ui.listeners.*;" + nl
				+ "import com." + MainGenerator.framework + ".appclient.ui.controls.*;" + nl
				+ "import com." + MainGenerator.framework + ".modellibrary.reference.common.*;" + nl
				+ "import com." + MainGenerator.root + ".modellibrary.reference.group.*;" + nl
				+ "import com." + MainGenerator.framework + ".framework.logging.*;" + nl
				+ "import com." + MainGenerator.framework + ".framework.utility.*;" + nl
				+ "import com." + MainGenerator.framework + ".appclient.services.*;" + nl
				+ "" + nl
				+ "" + nl
				+ "/**" + nl
				+ am.getDescription() + nl
				+ "*/" + nl
				+ "public class " + am.getActionsName() + " {" + nl + nl
				+ "	private " + am.getControllerName() + " controller;" + nl + nl
				+ "	public ApplicationActionList getActions(" + am.getControllerName() + " c) {" + nl
				+ " 	controller = c;" + nl + nl
				+ "		ApplicationActionList actions = new ApplicationActionList();" + nl
				+ "		/* EXAMPLE " + nl
				+ "		actions.addApplicationAction(new ApplicationAction(ActionGroup.REQMGRFILECLOSE," + nl
				+ "			new ControlState(){" + nl
				+ "				public boolean getEnabled() {" + nl
				+ "					return ApplicationObject.getApplicationProductRefId()>0;" + nl
				+ "				}" + nl
				+ "			}," + nl
				+ "			new UIActionListener(controller.ui) {" + nl
				+ "			public void actionExecuted(ActionEvent ae) throws Exception {" + nl
				+ "				ApplicationObject ao = (ApplicationObject) Application.getObject();" + nl
				+ "				ao.setProductRefId(0);" + nl
				+ "				controller.buildTree();" + nl
				+ "			}" + nl
				+ "		})); */" + nl
				+ "		return actions;" + nl
				+ "	}" + nl + nl
				+ "}");
		
		return sb.toString();
		
	}
	
	public String getToolsSource(ApplicationModel am) {
		StringBuffer sb = new StringBuffer(8*1024);
		sb.append(MainGenerator.getLicense(am.getDescription()) + "package " + am.getJavaPackage() + ";" + nl
				+ "" + nl
				+ "import javax.swing.event.*;" + nl
				+ "import javax.swing.*;" + nl
				+ "import java.awt.event.*;" + nl
				+ "import java.awt.*;" + nl
				+ "import java.util.*;" + nl
				+ "" + nl
				+ am.getModelImport() + nl
				+ "import com." + MainGenerator.root + ".appclient.system.*;" + nl
				+ "import com." + MainGenerator.root + ".appclient.services.*;" + nl
				+ "import com." + MainGenerator.framework + ".appclient.ui.common.*;" + nl
				+ "import com." + MainGenerator.framework + ".appclient.ui.listeners.*;" + nl
				+ "import com." + MainGenerator.framework + ".appclient.ui.controls.*;" + nl
				+ "import com." + MainGenerator.framework + ".modellibrary.reference.common.*;" + nl
				+ "import com." + MainGenerator.framework + ".modellibrary.reference.security.*;" + nl
				+ "import com." + MainGenerator.root + ".modellibrary.reference.group.*;" + nl
				+ "import com." + MainGenerator.framework + ".framework.logging.*;" + nl
				+ "import com." + MainGenerator.framework + ".framework.utility.*;" + nl
				+ "import com." + MainGenerator.framework + ".appclient.services.*;" + nl
				+ "" + nl
				+ "" + nl
				+ "/**" + nl
				+ am.getDescription() + nl
				+ "*/" + nl
				+ "public class " + am.getToolsName() + " {" + nl + nl
				+ "	public UIToolBar getToolBar(ApplicationActionList actions) {" + nl
				+ "		UIToolBar toolBar = new UIToolBar();" + nl
				+ "		/*" + nl
				+ "		ApplicationControlList controls = SecurityServices.getAppControlsByUser(ApplicationFramework.get(ApplicationGroup.YourApp));" + nl
				+ "		Enumeration e1 = controls.elements();" + nl
				+ "		while (e1.hasMoreElements()) {" + nl
				+ "			ApplicationControlModel m = (ApplicationControlModel) e1.nextElement();" + nl
				+ "			toolBar.addNewButton(m, ControlsGroup.YourAppTOOLBARNEW, actions.getAction(ActionGroup.YourAppFILENEW));" + nl
				+ "		}" + nl
				+ "		*/" + nl
				+ "		return toolBar;" + nl
				+ "	}" + nl
				+ "	public JMenuBar getMenuBar(ApplicationActionList actions) {" + nl					
				+ "		MenuManager menuManager = new MenuManager(new ApplicationControlList());" + nl
				+ "		/*" + nl
				+ "		MenuManager menuManager = new MenuManager(SecurityServices.getAppControlsByUser(ApplicationFramework.get(ApplicationGroup.YourAppMENU)));" + nl
				+ "		menuManager.addMenu(\"Menu File\");" + nl
				+ "		menuManager.addSubMenu(\"Menu File - New\");" + nl
				+ "		ApplicationSecurityList list = SecurityServices.getAppSecurity(ApplicationGroup.YourSubType);" + nl
				+ "		Enumeration e1 = list.elements();" + nl
				+ "		while (e1.hasMoreElements()) {" + nl
				+ "			ApplicationSecurityModel asm = (ApplicationSecurityModel) e1.nextElement();" + nl
				+ "			ApplicationAction action = actions.getAction(ActionGroup.YourAppFILENEW);" + nl
				+ "			action.setUserObject(asm);" + nl
				+ "			menuManager.addSubMenuItem(asm.getAppTypeRefDisplay(),action , false, null);" + nl
				+ "		}" + nl
				+ "		menuManager.addMenuItem(\"Menu File - Open\", actions.getAction(ActionGroup.YourAppFILEOPEN), false, Keys.YourAccelerator);" + nl
				+ "		*/" + nl
				+ "		return menuManager.getMenuBar();" + nl
				+ "	}" + nl
				+ "}");
		
		return sb.toString();
		
	}
	
	public String getBaseControllerSource(ApplicationModel am ) {
		StringBuffer sb = new StringBuffer(8*1024);
		
		sb.append(MainGenerator.getLicense(am.getDescription()) + nl
		+ "package " + am.getJavaPackage() + ";" + nl
		+ "" + nl
		+ "import java.awt.*;" + nl
		+ "import java.awt.event.*;" + nl
		+ "import java.util.*;" + nl
		+ "import java.text.*;" + nl
		+ "import javax.swing.*;" + nl
		+ "import javax.swing.event.*;" + nl
		+ "" + nl
		+ am.getModelImport() + nl 
		+ "import com." + MainGenerator.root + ".appclient.common.*;" + nl
		+ "import com." + MainGenerator.framework + ".appclient.ui.common.*;" + nl
		+ "import com." + MainGenerator.framework + ".appclient.ui.listeners.*;" + nl
		+ "import com." + MainGenerator.framework + ".modellibrary.common.*;" + nl
		+ "import com." + MainGenerator.framework + ".modellibrary.reference.common.*;" + nl
		+ "import com." + MainGenerator.root + ".modellibrary.reference.group.*;" + nl
		+ "import com." + MainGenerator.framework + ".modellibrary.reference.security.*;" + nl
		+ "import com." + MainGenerator.framework + ".modellibrary.reference.group.ApplicationFramework;" + nl
		+ "import com." + MainGenerator.framework + ".modellibrary.reference.group.RecordTypeFramework;" + nl
		+ "import com." + MainGenerator.framework + ".framework.logging.*;" + nl
		+ "import com." + MainGenerator.framework + ".framework.utility.*;" + nl
		+ "import com." + MainGenerator.root + ".appclient.services.*;" + nl
		+ "import com." + MainGenerator.framework + ".appclient.ui.components.*;" + nl
		+ "import com." + MainGenerator.framework + ".appclient.ui.controls.*;" + nl
		+ "import com." + MainGenerator.framework + ".appclient.services.*;" + nl
		+ "" + nl
		+ "" + nl
		+ "/**" + nl
		+ am.getDescription() + nl
		+ "*/" + nl
		+ "public class " + am.getBaseControllerName() + " {" + nl
		+ "	" + nl
		+ this.maingen.getControlList().getModelVariables(am.getAppName()));
		sb.append("	protected RuleScript script = null;" + nl);
		if (am.getDialogInd() == 1) {
			sb.append("	protected " + am.getUIName() + " ui;" + nl + nl
					+ "	public " + am.getBaseControllerName() + "(JFrame frame) {" + nl
					+ "		 ui = new " + am.getUIName() + "(frame);" + nl
					+ "	}" + nl + nl); 
		} else {
			sb.append("	protected " + am.getUIName() + " ui = new " + am.getUIName() + "();" + nl + nl);
		}
		sb.append("");
		if (am.getSecurityRefInd()==1) {
			sb.append("	private int viewRefId = 0;"
					+ "	" + nl
					+ "	public void initialize (ApplicationControlList controls, int viewRefId");
			/*} else if (am.getSecurityRefInd()==2){
			sb.append("	private int appTypeRefId = 0;"
					+ "	" + nl
					+ "	public void initialize (ApplicationControlList controls, int appTypeRefId, ");
		*/} else {
			sb.append("	" + nl
					+ "	public void initialize (ApplicationControlList controls");
		}
		String firstModel = maingen.getControlList().getFirstModel(am.getAppName());
		if (firstModel != null && firstModel.length() > 0) {
			sb.append(", " + maingen.getControlList().getFirstModel(am.getAppName()) + " m) {" + nl);
			sb.append("		this." + maingen.getControlList().getFirstVariable(am.getAppName()) + " = m;" + nl);
		} else {
			sb.append(") {" + nl);
		}
		
		sb.append("		script = new RuleScript(null,null);" + nl);
		if (am.getSecurityRefInd()==1) {
			sb.append("		this.viewRefId = viewRefId;" + nl);
		}// else 	if (am.getSecurityRefInd()==2) {
		//	sb.append("		this.appTypeRefId = appTypeRefId;" + nl);
		//}
		sb.append("		initializeUI(controls);" + nl);
		sb.append("	}" + nl
		+ "	" + nl
		+ "	private void initializeUI(ApplicationControlList controls) {" + nl);
		/*if (am.getSecurityRefInd()==1) {
			sb.append("		//req# 17 User is authenticated and authorized to use this registration form" + nl
					+ "		//req# 22 System displays all controls for the selected registration form       " + nl
					+ "		ApplicationControlList controls = SecurityServices.getAppControls(viewRefId, " + nl); 
		/*} else if (am.getSecurityRefInd()==2) {
			sb.append("		//req# 17 User is authenticated and authorized to use this registration form" + nl
					+ "		//req# 22 System displays all controls for the selected registration form       " + nl
					+ "		ApplicationControlList controls = SecurityServices.getAppControlsByUser(appTypeRefId, " + nl); 
		} else {
			sb.append("		ApplicationControlList controls = SecurityServices.getAppControlsByUser(" + nl); 
		}
				sb.append( "			ApplicationGroup." + am.getAppName().toUpperCase() + ");" + nl
		*/		sb.append( "		java.util.Enumeration e1 = controls.elements();" + nl
				+ "		while (e1.hasMoreElements()) {" + nl
				+ "			ApplicationControlModel rm = (ApplicationControlModel) e1.nextElement();" + nl);
		
		Enumeration e1 = maingen.getControlList().elements();
		while (e1.hasMoreElements()) {
			ControlModel cm = (ControlModel) e1.nextElement();
			if (cm.getAppName().compareTo(am.getAppName())==0) {
				sb.append("			if (rm.getControlRefDisplay().compareTo(\"" + cm.getControlName()+ "\")==0) {"+ nl
						+ "				" + cm.getUiControl() + " field = get" + cm.getControlName() + "();" + nl
						+ "				GUI.setGUIMessage(field, rm);" + nl
						+ "				if (rm.isDisabled()) {" + nl
						+ " 					field.setEnabled(false);" + nl
						+ "				} else {" + nl
						+ "					script.executeScript(rm.getInitScript(), field);" + nl
						+ "					script.bind(field, rm);" + nl
						+ " 				}" + nl);
				if (cm.getUiControl().compareTo("UIButton")==0) {
					sb.append("				field.setText(rm.getControlText());" + nl);
				}
						sb.append( "				ui.set" + cm.getControlName() + "(field);" + nl
						+ "			}" + nl);
			}
		}
		
		if (am.getUserDefinedModel() != null) {
			sb.append("		if (rm.getControlRefDisplay().compareTo(\"User Defined\")==0) {" + nl
					  + "			Component field = createComponent(rm);" + nl
					  + "			if (rm.isDisabled()) {" + nl
					  + "					field.setEnabled(false);" + nl
					  + "			} else {" + nl
					  + "				script.executeScript(rm.getInitScript(), field);" + nl
					  + "				script.bind(field,rm);" + nl
					  + "				}" + nl
					  + "			ui.addUserDefined(field, rm.getAppControlUserDefinedId());" + nl
					  + "	}" + nl);
		}
		
		sb.append("		}" + nl);
		/*
		if (am.getSecurityRefInd()==1) {
			sb.append("		ui.addControls(viewRefId);" + nl);
		} else if (am.getSecurityRefInd()==2) {
			sb.append("		ui.addControls(appTypeRefId);" + nl);
		} else {
			sb.append("		ui.addControls(controls);" + nl);
		}
		*/
		sb.append("		ui.addControls(controls);" + nl);
		sb.append("		ui.setLocationRelativeTo(null);" + nl
		+ "	}" + nl
		+ "	" + nl
		+ "	" + nl);		
		Enumeration e2 = maingen.getControlList().elements();
		while (e2.hasMoreElements()) {
			ControlModel cm = (ControlModel) e2.nextElement();
			if (cm.getAppName().compareTo(am.getAppName())==0) {
				if (cm.isFormattedDate()) {
					sb.append("	protected " + cm.getUiControl() + " get" + cm.getControlName() + "() {" + nl
						+ "		try {" + nl
						+ "			DateFormat formatDate = new SimpleDateFormat(CalendarUtility.ShortDateFormat()); " + nl 
						+ "			" + cm.getUiControl() + " control = new " + cm.getUiControl() + "(formatDate);" + nl
						+ "			control.setText(CalendarUtility.Format(" + cm.getGetter() + ", CalendarUtility.ShortDateFormat()));" + nl
						+ "			control.getDocument().addDocumentListener(new " + cm.getListener() + "() {" + nl
						+ "				public void call(String p) {" + nl
						+ "					try { " + nl
						+ "						if (CalendarUtility.shouldParseDate(p, CalendarUtility.ShortDateFormat())) {" + nl
						+ "						" + cm.getSetter() + nl
						+ "						}" + nl
						+ "					} catch (Exception ex) { " + nl
						+ "						Debug.LogException(this, ex);" + nl
						+ "					}" + nl
						+ "				}				" + nl
						+ "			});" + nl
						+ "			return control;" + nl
						+ "		} catch (Exception ex) {" + nl
						+ "			Debug.LogException(this, ex);" + nl
						+ "			return new " + cm.getUiControl() + "();" + nl
						+ "		}" + nl
						+ "	}" + nl + nl); 
				} else if (cm.isDocumentListener()) {
					sb.append("	private " + cm.getUiControl() + " get" + cm.getControlName() + "() {" + nl
					+ "		try {" + nl
					+ "			" + cm.getUiControl() + " control = new " + cm.getUiControl() + "(" + cm.getConstructorColumns() + ");" + nl);
					if (cm.getGetter() != null && cm.getGetter().length() > 0 && cm.getGetter().compareTo("//")!=0) {
						sb.append( "			control.setText(" + cm.getGetter() + ");" + nl);
					}
					sb.append( "			control.getDocument().addDocumentListener(new " + cm.getListener() + "() {" + nl
					+ "				public void call("+ cm.getJavaDataType() + " p) {" + nl
					+ "					" + cm.getSetter() + nl
					+ "				}				" + nl
					+ "			});" + nl
					+ "			return control;" + nl
					+ "		} catch (Exception ex) {" + nl
					+ "			Debug.LogException(this, ex);" + nl
					+ "			return new " + cm.getUiControl() + "();" + nl
					+ "		}" + nl
					+ "	}" + nl + nl);
				} else if (cm.isIndicatorListener()) {
					sb.append("	private " + cm.getUiControl() + " get" + cm.getControlName() + "() {" + nl
					+ "		try {" + nl
					+ "			final " + cm.getUiControl() + " control = new " + cm.getUiControl() + "(" + cm.getConstructorColumns() + ");" + nl
					+ "			control.setSelected(" + cm.getGetter() + "==1);" + nl
					+ "			control.addChangeListener(new " + cm.getListener() + "() {" + nl
					+ "				public void call(ChangeEvent c) {" + nl
					+ "					int p = 0;" + nl
					+ "					if (control.isSelected()) {" + nl
					+ "						p = 1;" + nl
					+ "					}" + nl
					+ "					" + cm.getSetter() + nl
					+ "				}				" + nl
					+ "			});" + nl
					+ "			return control;" + nl
					+ "		} catch (Exception ex) {" + nl
					+ "			Debug.LogException(this, ex);" + nl
					+ "			return new " + cm.getUiControl() + "();" + nl
					+ "		}" + nl
					+ "	}" + nl + nl);
				} else if (cm.isListListener()) {
					sb.append("	private " + cm.getUiControl() + " get" + cm.getControlName() + "() {" + nl
					+ "		try {" + nl
					+ "			" + cm.getUiControl() + " control = new " + cm.getUiControl() + "(" + cm.getListGroup() + ");" + nl
					+ "			control.addListSelectionListener(new " + cm.getListener() + "() {" + nl
					+ "				public void call("+ cm.getJavaDataType() + " p) {" + nl
					+ "					try {" + nl
					+ "						" + cm.getSetter() + nl
					+ "					} catch (Exception ex) {" + nl
					+ "						Debug.LogException(this, ex);" + nl
					+ "					}" + nl
					+ "				}" + nl
					+ "			});" + nl
					+ "			return control;" + nl
					+ "		} catch (Exception ex) {" + nl
					+ "			Debug.LogException(this, ex);" + nl
					+ "			return new " + cm.getUiControl() + "();" + nl
					+ "		}" + nl
					+ "	}" + nl
					+ "" + nl + nl);					
				} else if (cm.isComboListener()) {
					sb.append("	private " + cm.getUiControl() + " get" + cm.getControlName() + "() {" + nl
					+ "		try {" + nl);
					if (cm.getListGroup() == null) {
						sb.append( "			ReferenceDisplayList list = new ReferenceDisplayList();" + nl);
					} else {
						sb.append( "			ReferenceDisplayList list = " + cm.getListGroup() + ";" + nl);
					}
					sb.append( "			" + cm.getUiControl() + " control = new " + cm.getUiControl() + "(list);" + nl);
					if (cm.getGetter() != null) {
						sb.append("			list.setSelectedRefId(" + cm.getGetter() + ");" + nl); 
					}
					if (cm.getJavaDataType().compareTo("int")==0) {
					sb.append("			ReferenceListListener listener = (new " + cm.getListener() + "() {" + nl
					+ "				public void call("+ cm.getJavaDataType() + " p) {" + nl
					+ "					try {" + nl
					+ "						" + cm.getSetter() + nl
					+ "					} catch (Exception ex) {" + nl
					+ "						Debug.LogException(this, ex);" + nl
					+ "					}" + nl
					+ "				}" + nl
					+ "			});" + nl
					+ "			new UIComboDocument(control, listener);" + nl);
					} else {
						sb.append("			new UIComboDocument(control, null);" + nl);
					}
					sb.append("			return control;" + nl
					+ "		} catch (Exception ex) {" + nl
					+ "			Debug.LogException(this, ex);" + nl
					+ "			return new " + cm.getUiControl() + "();" + nl
					+ "		}" + nl
					+ "	}" + nl
					+ "" + nl + nl);					
				} else if (cm.isCustomSearch()) {
					sb.append("	private " + cm.getUiControl() + " get" + cm.getControlName() + "() {" + nl
					+ "		try {" + nl
					+ "			" + cm.getUiControl() + " control = new " + cm.getUiControl() + "(\"" + cm.getReferenceSearch() + "\");" + nl
					+ "			control.setText(\"\" + " + cm.getGetter() + ");" + nl
					+ "			control.addDocumentListener(new " + cm.getListener() + "() {" + nl
					+ "				public void call("+ cm.getJavaDataType() + " text) {" + nl
					+ "					try { " + nl
					+ "						try {" + nl
					+ "							int p = Integer.parseInt(text);" + nl
					+ "							" + cm.getSetter() + nl
					+ "						} catch (NumberFormatException nfe) {" + nl
					+ "							int p = 0;" + nl
					+ "							" + cm.getSetter() + nl
					+ "						}" + nl
					+ "					} catch (Exception ex) { " + nl
					+ "						Debug.LogException(this, ex);" + nl
					+ "					}" + nl
					+ "				}				" + nl
					+ "			});" + nl
					+ "			return control;" + nl
					+ "		} catch (Exception ex) {" + nl
					+ "			Debug.LogException(this, ex);" + nl
					+ "			return new " + cm.getUiControl() + "();" + nl
					+ "		}" + nl
					+ "	}" + nl + nl); 
				} else if (cm.isUserDefined()) {
					sb.append("	private " + cm.getUiControl() + " get" + cm.getControlName() + "() {" + nl
					+ "		try {" + nl
					+ "			" + cm.getUiControl() + " control = new " + cm.getUiControl() + "(" + cm.getConstructorColumns() + ");" + nl
					+ "			control.setText(" + cm.getGetter() + ");" + nl
					+ "			control.getDocument().addDocumentListener(new " + cm.getListener() + "() {" + nl
					+ "				public void call("+ cm.getJavaDataType() + " p) {" + nl
					+ "					" + cm.getSetter() + nl
					+ "				}				" + nl
					+ "			});" + nl
					+ "			return control;" + nl
					+ "		} catch (Exception ex) {" + nl
					+ "			Debug.LogException(this, ex);" + nl
					+ "			return new " + cm.getUiControl() + "();" + nl
					+ "		}" + nl
					+ "	}" + nl + nl);
				} else if (cm.isCustomControl()) {
					sb.append("	private " + cm.getUiControl() + " get" + cm.getControlName() + "() {" + nl
					+ "		try {" + nl
					+ "			" + cm.getUiControl() + " control = new " + cm.getUiControl() + "();" + nl
					+ "			" + cm.getSetter() + nl
					+ "			return control;" + nl
					+ "		} catch (Exception ex) {" + nl
					+ "			Debug.LogException(this, ex);" + nl
					+ "			return new " + cm.getUiControl() + "();" + nl
					+ "		}" + nl
					+ "	}" + nl + nl);
				} else {
					System.err.println(cm.getUiControl() + " not handled in getBaseControllerSource.  Edit the ControlModel.");
				}
			}
		}
		
		//TODO cant associate a single user defined model with a base controller
		if (am.getUserDefinedModel() != null) {
		sb.append(nl +  "	public Component createComponent(ApplicationControlModel m) {" + nl
		+ "		try {" + nl
		+ "			Enumeration e1 = m.getAppControlUserDefinedList().elements();" + nl
		+ "			if (e1.hasMoreElements()) {" + nl
		+ "				AppControlUserDefinedModel am = (AppControlUserDefinedModel) e1.nextElement();" + nl
		+ "				UserDefinedControl udc = new UserDefinedControl(" + am.getUserDefinedModel() + ", am);" + nl
		+ "				return udc.createComponent();" + nl
		+ "			} else {" + nl
		+ "				return new JTextField(5);" + nl
		+ "			}" + nl
		+ "		} catch (Exception ex) {" + nl
		+ "			Debug.LogException(this, ex);" + nl
		+ "			JTextField field = new JTextField(10);" + nl
		+ "			field.setText(ex.getMessage());" + nl
		+ "			return (Component) field;" + nl
		+ "		}" + nl
		+ "	}	" + nl);
		}

		sb.append("	" + nl + "}" + nl);

		return sb.toString();
	}
	

}
