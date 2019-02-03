package generator;
import models.*;
import java.sql.*;
import java.util.*;

import utilities.FileSystemUtil;
import utilities.GUI;

public class MainGenerator {
	
	public static String nl = "\n";
	public static String root = "osrmt";
	public static String framework = "osframework";
	public static String rootDirectory = "C:\\dev\\osrmt";
	public static String schemaAccessDbFile = rootDirectory + "\\trunk\\database\\osrmt_schema.mdb";
	public static String osrmtAccessDbFile = rootDirectory + "\\trunk\\database\\osrmt.mdb";
	public static String SystemVersion = "1";

	private TableLibraryList tableLibraryList = new TableLibraryList();
	private ModelLibraryList modelLibraryList = new ModelLibraryList();
	private IndexLibraryList indexLibraryList = new IndexLibraryList();
	private EjbLibraryList ejbLibraryList = new EjbLibraryList();
	private BusinessMethodList businessMethodList = new BusinessMethodList();
	private MessageList messageList = new MessageList();
	private ApplicationLibraryList applicationLibraryList = new ApplicationLibraryList();
	private ControlList controlList = new ControlList();
	private RequirementList requirementList = new RequirementList();
	
	public Properties props = new Properties();
	public static String propROOTDIR = "rootdir";
	public static String propGENSRCDIR = "gensrcdir";
	public static String propFRAMEWORKSRCDIR = "frameworksrcdir";
	public static String propMSGBUNDLEDIR = "msgbundledir";
	public static String propSCHEMADIR = "schemadir";
	public static String propCONFIGDIR = "configdir";
	public static String propUITEMPLATEDIR = "uitemplatedir";
	public static String propREQUIREMENTSDIR = "requirementsdirs";
	public static String propREPORTDIR = "reportdir";
	public static String license;
	
	public static String getLicense(String description) {
		if (license != null && description !=null) {
			String s = description.replace("/*","").replace("*/","");
			return "/*\r\n" + license.replace("//usage",description) + "\r\n*/";
		} else {
			return "/*\r\n" + license + "\r\n*/";
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			GUI.setClipboard("");
			System.out.println("Generating source for "+ MainGenerator.root);
			// Connect to access making available the Access db
			MainGenerator mg = new MainGenerator();
			mg.loadModels();
			mg.loadProperties();
			//mg.print();
			DatabaseSchema da = new DatabaseSchema(mg);
			da.CreateSchema();
			
			// create source directory structure from package names
			GenerateFileSystem gfs = new GenerateFileSystem(mg);
			gfs.CreateDirectories();
			
			// populate reference tables
			AutogenConfiguration ac = new AutogenConfiguration(mg);
			ac.CreateSearchColumns();
			
			// create model files
			GenerateModel gm = new GenerateModel(mg);
			gm.CreateModels();
			
			GenerateEjb ge = new GenerateEjb(mg);
			ge.CreateBeans();
			
			GenerateConfiguration gc = new GenerateConfiguration(mg);
			gc.CreateFiles();
			
			GenerateDataAdapter gd = new GenerateDataAdapter(mg);
			gd.CreateAdapters();
			
			//GenerateMessages gms = new GenerateMessages(mg);
			//gms.CreateProperties();
			
			GenerateReference gr = new GenerateReference(mg);
			
			GenerateApplications ga = new GenerateApplications(mg);
			ga.CreateApplications();
			
			AutogenReports ar = new AutogenReports(mg);
			ar.CreateReport();
			
		
		} catch(Exception ex){
			ex.printStackTrace(System.err);
		}
	}
	
	/*
	 * Populate the models: TableLibraryList, ModelLibraryList, etc. 
	 */
	public void loadModels() throws SQLException, ClassNotFoundException {
		
		tableLibraryList.load(Db.access);
		modelLibraryList.load(Db.access);
		indexLibraryList.load(Db.access);
		ejbLibraryList.load(Db.access);
		businessMethodList.load(Db.access);
		messageList.load(Db.access);
		applicationLibraryList.load(Db.access);
		controlList.load(Db.access);
		//requirementList.load(Db.access);
	}
	
	
	public void loadProperties() {
		this.props.put(propROOTDIR,rootDirectory + "\\trunk");
		this.props.put(propGENSRCDIR,props.get(propROOTDIR).toString() 
				+ "\\src");
		this.props.put(propFRAMEWORKSRCDIR,props.get(propGENSRCDIR).toString());
		//this.props.put(propMSGBUNDLEDIR,"C:\\ant\\workspace\\" + MainGenerator.root);
		this.props.put(propSCHEMADIR,rootDirectory + "\\output\\schema");
		this.props.put(propREPORTDIR,rootDirectory + "\\trunk\\database\\reports");
		this.props.put(propCONFIGDIR,props.get(propROOTDIR).toString());
		this.props.put(propUITEMPLATEDIR,props.get(propGENSRCDIR).toString() 
				+ "\\com\\" + MainGenerator.root + "\\appclient\\templates");
//		this.props.put(propREQUIREMENTSDIR,props.get(propROOTDIR).toString()
//				+ "\\website\\requirements");
		MainGenerator.license = FileSystemUtil.getContents(props.getProperty(propROOTDIR) + "\\resources\\","license.txt");
	}

	public BusinessMethodList getBusinessMethodList() {
		return businessMethodList;
	}

	public void setBusinessMethodList(BusinessMethodList businessMethodList) {
		this.businessMethodList = businessMethodList;
	}

	public IndexLibraryList getIndexLibraryList() {
		return indexLibraryList;
	}

	public void setIndexLibraryList(IndexLibraryList indexLibraryList) {
		this.indexLibraryList = indexLibraryList;
	}

	public ModelLibraryList getModelLibraryList() {
		return modelLibraryList;
	}

	public void setModelLibraryList(ModelLibraryList modelLibraryList) {
		this.modelLibraryList = modelLibraryList;
	}

	public TableLibraryList getTableLibraryList() {
		return tableLibraryList;
	}

	public void setTableLibraryList(TableLibraryList tableLibraryList) {
		this.tableLibraryList = tableLibraryList;
	}

	public EjbLibraryList getEjbLibraryList() {
		return ejbLibraryList;
	}

	public void setEjbLibraryList(EjbLibraryList ejbLibraryList) {
		this.ejbLibraryList = ejbLibraryList;
	}

	public MessageList getMessageList() {
		return messageList;
	}

	public void setMessageList(MessageList messageList) {
		this.messageList = messageList;
	}

	public ApplicationLibraryList getApplicationLibraryList() {
		return applicationLibraryList;
	}

	public void setApplicationLibraryList(
			ApplicationLibraryList applicationLibraryList) {
		this.applicationLibraryList = applicationLibraryList;
	}

	public ControlList getControlList() {
		return controlList;
	}

	public void setControlList(ControlList controlList) {
		this.controlList = controlList;
	}

	public RequirementList getRequirementList() {
		return requirementList;
	}

	public void setRequirementList(RequirementList requirementList) {
		this.requirementList = requirementList;
	}
	
	public static String getDisplayCode(String display) {
		if (display != null) {
			return display.toUpperCase().replace(" ","").replace("_","");
		}
		return display;
	}

	public static int getSequence(String pkey, String tableName) throws SQLException {
		String sql = "select max(" + pkey + ") from " + tableName + " where ref_id < 10000";
		ResultSet rset = Db.access.executeQuery(sql);
		int nbr=0;
		if (rset.next()) {
			nbr = rset.getInt(1);
		}
		rset.close();
		return nbr+1;
	}

	public static String getInsertReference() {
		return "insert into REFERENCE (ref_id, reference_group, display_code, display_sequence, display, record_type_ref_id)"
			+ " values (?, ?, ?, ?, ?, 320)";
	}
}
