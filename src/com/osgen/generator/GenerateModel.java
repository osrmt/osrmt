//TODO Generate models with a useful toString() output.
//TODO Generate models with a check that all not null db fields are filled out
package generator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import javax.swing.event.ListDataListener;

import com.osframework.modellibrary.common.Comparison;

import models.*;
import utilities.FileSystemUtil;

public class GenerateModel {
	private MainGenerator maingen = null;
	private String nl = "\r\n";
	private int startSeq=0;
	
	public GenerateModel(MainGenerator m) {
		this.maingen = m;
	}

	//TODO Only implemented tablelistmodel and vector list so remove all else
	public void CreateModels() throws java.io.IOException {
		
		String genSourceDir = maingen.props.getProperty(MainGenerator.propGENSRCDIR);
		Enumeration e1 = maingen.getModelLibraryList().elements();
		// each model library has a package of format example
		// com." + MainGenerator.root + ".modellibrary.registration.patient
		// each . will equate to a directory
		try {
			startSeq = MainGenerator.getSequence("REF_ID","REFERENCE");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		while (e1.hasMoreElements()) {
			// Data Model
			ModelLibraryModel mlm = (ModelLibraryModel) e1.nextElement();
			//System.out.println(mlm.getModelName());
			String modelSource = getModelSource(mlm);
			String frameworkSourceDir = maingen.props.getProperty(MainGenerator.propFRAMEWORKSRCDIR);
			String javaPath;
			if (mlm.getJavaPackage().indexOf("framework")>0) {
				javaPath = mlm.getJavaPath(frameworkSourceDir);
			} else {
				javaPath = mlm.getJavaPath(genSourceDir);
			}
			FileSystemUtil.CreateDirectory(javaPath);
			FileSystemUtil.CreateFile(javaPath, mlm.getDataModelName() + ".java", modelSource);
			
			// Model List
			String listSource;
			if (mlm.getExtendVectorInd() ==1) {
				listSource = getVectorListSource(mlm);
			} else {
				listSource = getListSource(mlm);
			}
			if (mlm.getJavaPackage().indexOf("framework")>0) {
				javaPath = mlm.getJavaPath(frameworkSourceDir);
			} else {
				javaPath = mlm.getJavaPath(genSourceDir);
			}
			FileSystemUtil.CreateDirectory(javaPath);
			
			FileSystemUtil.CreateFile(javaPath, mlm.getListName() + ".java", listSource);

			// Main Model 
			String extSource = getExtendedModelSource(mlm);
			if (mlm.getJavaPackage().indexOf("framework")>0) {
				javaPath = mlm.getJavaPath(frameworkSourceDir);
			} else {
				javaPath = mlm.getJavaPath(genSourceDir);
			}
			FileSystemUtil.CreateDirectory(javaPath);
			if (!FileSystemUtil.Exists(javaPath, mlm.getModelName() + ".java")) {
				FileSystemUtil.CreateFile(javaPath, mlm.getModelName() + ".java", extSource);
			}
		}
		
	}
	
	public String getModelSource(ModelLibraryModel m) {
		String ref_id = "_ref_id";
		String ref_display = "_ref_display";
		String RefId = "RefId";
		String RefDisplay = "RefDisplay";
		String user_id = "_user_id";
		String user_name = "_user_name";
		String UserId = "UserId";
		String UserName = "UserName";
		boolean doCerner = false;
		if (doCerner) {
			ref_id = "_cd";
			ref_display = "_display";
			RefId = "Cd";
			RefDisplay = "Display";
		}
		
		StringBuffer sb = new StringBuffer(1024);
		sb.append(MainGenerator.getLicense(m.getDescription()) + nl
		+ "package " + m.getJavaPackage() + ";" +nl
		+ "import java.util.*;" + nl
		+ "import com." + MainGenerator.framework + ".framework.locale.*;" + nl 
		+ "import com." + MainGenerator.framework + ".framework.logging.*;" + nl 
		+ "import com." + MainGenerator.framework + ".framework.utility.*;" + nl 
		+ "import com." + MainGenerator.framework + ".framework.exceptions.*;" + nl 
		+ "import com." + MainGenerator.framework + ".ejb.reference.common.*;" + nl
		+ "import com." + MainGenerator.framework + ".ejb.reference.security.*;" + nl
		+ "import com." + MainGenerator.framework + ".modellibrary.common.*;" + nl 
		+ "import com." + MainGenerator.framework + ".modellibrary.reference.group.*;" + nl 
		+ "import com." + MainGenerator.framework + ".appclient.services.*;" + nl 
		+ "import com." + MainGenerator.framework + ".modellibrary.reference.security.*;" + nl + nl
		+ "/**" + nl
		+ m.getDescription() + nl
		+ "*/" + nl
		+ "public class " + m.getDataModelName() + " ");
		if (m.getUserDefinedInd()==1) {
			sb.append("extends ExtendedModel implements IControlModel, java.io.Serializable, IExtendedModel");
		} else {
			sb.append("implements IControlModel, java.io.Serializable");
		}
		int fieldcnt = 0;
		Enumeration e20 = maingen.getTableLibraryList().elements();
		while (e20.hasMoreElements()) {
			TableLibraryModel t = (TableLibraryModel) e20.nextElement();
			if (t.getTableName().compareTo(m.getTableName())==0) {
				fieldcnt++;
			}
		}
		
		sb.append(" {" + nl
		+ "" + nl
		+ "	static final long serialVersionUID = 1L;" + nl
		+ "	private int hashsize = " + fieldcnt + ";" + nl
		+ "	private Hashtable modifiedFields = new Hashtable(hashsize);" +nl 
		+ "	private boolean referenceSet = false;"+ nl+ nl);
		
		
		Enumeration e0 = maingen.getModelLibraryList().elements();
		while (e0.hasMoreElements()) {
			ModelLibraryModel t = (ModelLibraryModel) e0.nextElement();
			if (t.getParentTable() != null) {
				if (t.getParentTable().compareTo(m.getTableName())==0) {
					sb.append("\tprivate " + t.getListName() + " " + t.getListVariableName() +  " = new " + t.getListName() + "();" + nl);
				}
			}
		}
		sb.append(nl);
		Enumeration e1 = maingen.getTableLibraryList().elements();
		while (e1.hasMoreElements()) {
			TableLibraryModel t = (TableLibraryModel) e1.nextElement();
			if (t.getTableName().compareTo(m.getTableName())==0) {
				sb.append("\t/** " + t.getComments() + " " + t.getRequirementNumber() + " */" + nl);
				if (t.getJavaType().compareTo("GregorianCalendar")==0 && m.getTableModelInd()==1) {
					sb.append("\tprivate DbCalendar " + t.getJavaFieldName() + t.getDefaultAssignment(m.getTableModelInd()) + ";" + nl + nl);
				} else {
					sb.append("\tprivate " + t.getJavaType() + " " + t.getJavaFieldName() + t.getDefaultAssignment() + ";" + nl + nl);
				}
				if (t.getLowerColumnName().endsWith(ref_id)) {
					sb.append("\tprivate String " + t.getJavaFieldName().replaceAll(RefId,RefDisplay) + "=\"\";" + nl + nl);
				}
				if (t.getLowerColumnName().endsWith(user_id)) {
					sb.append("\tprivate String " + t.getJavaFieldName().replaceAll(UserId,UserName) + "=\"\";" + nl + nl);
				}
			}
		}
		sb.append(nl);
		Enumeration e2 = maingen.getTableLibraryList().elements();
		while (e2.hasMoreElements()) {
			TableLibraryModel t = (TableLibraryModel) e2.nextElement();
			if (t.getTableName().compareTo(m.getTableName())==0) {
				//getter
				sb.append("\t/** " + nl);
				sb.append("\t * " + t.getComments() + nl);
				sb.append("\t * " + t.getRequirementNumber() + nl);
				sb.append("\t */ "+ nl);
				if (m.getTableModelInd()==1 && t.getJavaType().compareTo("GregorianCalendar")==0) {
					sb.append("\tpublic DbCalendar " + t.getJavaFieldGetter() + "() {" + nl);
				} else {
					sb.append("\tpublic " + t.getJavaType() + " " + t.getJavaFieldGetter() + "() {" + nl);
				}
				sb.append("\t\treturn " + t.getJavaFieldName() + ";" + nl);
				sb.append("\t}" + nl + nl);
				//setter
				sb.append("\t/** " + nl);
				sb.append("\t * " + t.getComments() + nl);
				sb.append("\t * " + t.getRequirementNumber() + nl);
				if (t.getRequiredInd()==1) {
					sb.append("\t * Required database field." + nl);
				}
				sb.append("\t */ "+ nl);
				if (t.getJavaType().compareTo("GregorianCalendar")==0 && m.getTableModelInd()==1) {
					sb.append("\tpublic void " + t.getJavaFieldSetter() + "(" + t.getJavaType() + " " + t.getJavaFieldName() + ") {" + nl);
					sb.append("\t\tif (!Comparison.areEqual(this." + t.getJavaFieldName() + ", " + t.getJavaFieldName() + ")) {" + nl);
					sb.append("\t\t\tthis." + t.getJavaFieldName() + " = new DbCalendar();" + nl);
					sb.append("\t\t\tif (" + t.getJavaFieldName() + " != null) {" + nl);
					sb.append("\t\t\t	this." + t.getJavaFieldName() + ".setTimeInMillis(" + t.getJavaFieldName() + ".getTimeInMillis());"+ nl);
					sb.append("\t\t\t}" + nl);
					sb.append("\t\t\tsetModified(\"" + t.getJavaFieldName() + "\");" + nl);
					sb.append("\t\t};" + nl);
					sb.append("\t}" + nl + nl);
				} else if (t.getJavaType().compareTo("GregorianCalendar")==0) {
					sb.append("\tpublic void " + t.getJavaFieldSetter() + "(" + t.getJavaType() + " " + t.getJavaFieldName() + ") {" + nl);
					sb.append("\t\tif (!Comparison.areEqual(this." + t.getJavaFieldName() + ", " + t.getJavaFieldName() + ")) {" + nl);
					sb.append("\t\tif (" + t.getJavaFieldName() + " != null) {;" + nl);
					sb.append("\t\t\tif (this." + t.getJavaFieldName() + " == null) {" + nl);
					sb.append("\t\t\t\tthis." + t.getJavaFieldName() + " = new GregorianCalendar();" + nl);
					sb.append("\t\t\t}" + nl);
					sb.append("\t\t	this." + t.getJavaFieldName() + ".setTimeInMillis(" + t.getJavaFieldName() + ".getTimeInMillis());"+ nl);
					sb.append("\t\t}" + nl);
					sb.append("\t\t\tsetModified(\"" + t.getJavaFieldName() + "\");" + nl);
					sb.append("\t\t};" + nl);
					sb.append("\t}" + nl + nl);
				} else {
					sb.append("\tpublic void " + t.getJavaFieldSetter() + "(" + t.getJavaType() + " " + t.getJavaFieldName() + ") {" + nl);
					Enumeration es1 = maingen.getTableLibraryList().elements();
					while (es1.hasMoreElements()) {
						TableLibraryModel t2 = (TableLibraryModel) es1.nextElement();
						if (t2.getTableName().compareTo(m.getTableName())==0) {
							if (t2.getLowerColumnName().compareTo(t.getLowerColumnName() + "_indexed")==0) {
								sb.append("\t\tif (" +t.getJavaFieldName() + " != null) {" + nl); 										
								sb.append("\t\t\t" + t2.getJavaFieldSetter() + "(" +t.getJavaFieldName() + ".toUpperCase());" + nl); 										
								sb.append("\t\t}" + nl); 										
							}
						}
					}
					sb.append("\t\tif (!Comparison.areEqual(this." + t.getJavaFieldName() + ", " + t.getJavaFieldName() + ")) {" + nl);
					sb.append("\t\t\tthis." + t.getJavaFieldName() + " = " + t.getJavaFieldName() + ";" + nl);
					sb.append("\t\t\tsetModified(\"" + t.getJavaFieldName() + "\");" + nl);
					sb.append("\t\t};" + nl);
					sb.append("\t}" + nl + nl);
				}

				
				if (t.getJavaType().compareTo("GregorianCalendar")==0 && m.getTableModelInd()==1) {
					sb.append("\t/** " + nl);
					sb.append("\t * " + t.getComments() + nl);
					sb.append("\t * " + t.getRequirementNumber() + nl);
					sb.append("\t */ "+ nl);
					sb.append("\tpublic void " + t.getJavaFieldSetter() + "(long milliseconds) {" + nl);
					sb.append("\t\tif (this." + t.getJavaFieldName() + "==null) {" + nl);
					sb.append("\t\t\tthis." + t.getJavaFieldName() + " = new DbCalendar();" + nl);
					sb.append("\t\t}" + nl);
					sb.append("\t\tif (!Comparison.areEqual(this." + t.getJavaFieldName() + ", " + t.getJavaFieldName() + ")) {" + nl);
					sb.append("\t\t\tthis." + t.getJavaFieldName() + ".setTimeInMillis(milliseconds);" + nl);
					sb.append("\t\t\tsetModified(\"" + t.getJavaFieldName() + "\");" + nl);
					sb.append("\t\t}" + nl + nl);					
					sb.append("\t}" + nl + nl);					
				} else if (t.getJavaType().compareTo("GregorianCalendar")==0) {
					sb.append("\t/** " + nl);
					sb.append("\t * " + t.getComments() + nl);
					sb.append("\t * " + t.getRequirementNumber() + nl);
					sb.append("\t */ "+ nl);
					sb.append("\tpublic void " + t.getJavaFieldSetter() + "(long milliseconds) {" + nl);
					sb.append("\t\tif (this." + t.getJavaFieldName() + "==null) {" + nl);
					sb.append("\t\t\tthis." + t.getJavaFieldName() + "= new GregorianCalendar();" + nl);
					sb.append("\t\t}" + nl);
					sb.append("\t\tif (!Comparison.areEqual(this." + t.getJavaFieldName() + ", " + t.getJavaFieldName() + ")) {" + nl);
					sb.append("\t\t\tthis." + t.getJavaFieldName() + ".setTimeInMillis(milliseconds);" + nl);
					sb.append("\t\t\tsetModified(\"" + t.getJavaFieldName() + "\");" + nl);
					sb.append("\t\t}" + nl + nl);					
					sb.append("\t}" + nl + nl);					
				}
				if (t.getLowerColumnName().endsWith(ref_id)) {
					sb.append("\t/** " + nl);
					sb.append("\t * " + t.getComments() + nl);
					sb.append("\t * " + t.getRequirementNumber() + nl);
					sb.append("\t */ "+ nl);
					sb.append("\tpublic String " + t.getJavaFieldGetter().replaceAll(RefId,RefDisplay) + "() {" + nl);
					sb.append("\t\treturn " + t.getJavaFieldName().replaceAll(RefId,RefDisplay) + ";" + nl);
					sb.append("\t}" + nl + nl);
					sb.append("\t/** " + nl);
					sb.append("\t * " + t.getComments() + nl);
					sb.append("\t * " + t.getRequirementNumber() + nl);
					sb.append("\t */ "+ nl);
					sb.append("\tpublic void " + t.getJavaFieldSetter().replaceAll(RefId,RefDisplay) + "(String display) {" + nl);
					sb.append("\t\tthis." + t.getJavaFieldName().replaceAll(RefId,RefDisplay) + " = display;" + nl);
					sb.append("\t}" + nl + nl);
				}
				if (t.getLowerColumnName().endsWith(user_id)) {
					sb.append("\t/** " + nl);
					sb.append("\t * " + t.getComments() + nl);
					sb.append("\t * " + t.getRequirementNumber() + nl);
					sb.append("\t */ "+ nl);
					sb.append("\tpublic String " + t.getJavaFieldGetter().replaceAll(UserId,UserName) + "() {" + nl);
					sb.append("\t\treturn " + t.getJavaFieldName().replaceAll(UserId,UserName) + ";" + nl);
					sb.append("\t}" + nl + nl);
					sb.append("\t/** " + nl);
					sb.append("\t * " + t.getComments() + nl);
					sb.append("\t * " + t.getRequirementNumber() + nl);
					sb.append("\t */ "+ nl);
					sb.append("\tpublic void " + t.getJavaFieldSetter().replaceAll(UserId,UserName) + "(String userName) {" + nl);
					sb.append("\t\tthis." + t.getJavaFieldName().replaceAll(UserId,UserName) + " = userName;" + nl);
					sb.append("\t}" + nl + nl);
				}
				if (t.getLowerColumnName().endsWith("_ind")) {
					sb.append("\t/** " + nl);
					sb.append("\t * " + t.getComments() + nl);
					sb.append("\t * " + t.getRequirementNumber() + nl);
					sb.append("\t */ "+ nl);
					sb.append("\tpublic boolean " + t.getIndFieldGetter() + "() {" + nl);
					sb.append("\t\treturn " + t.getJavaFieldName() + " == 1;" + nl);
					sb.append("\t}" + nl + nl);
					sb.append("\t/** " + nl);
					sb.append("\t * " + t.getComments() + nl);
					sb.append("\t * " + t.getRequirementNumber() + nl);
					sb.append("\t */ "+ nl);
					sb.append("\tpublic boolean " + t.getNotIndFieldGetter() + "() {" + nl);
					sb.append("\t\treturn " + t.getJavaFieldName() + " == 0;" + nl);
					sb.append("\t}" + nl + nl);
					sb.append("\t/** " + nl);
					sb.append("\t * " + t.getComments() + nl);
					sb.append("\t * " + t.getRequirementNumber() + nl);
					sb.append("\t */ "+ nl);
					sb.append("\tpublic void " + t.getIndFieldSetter() + "() {" + nl);
					sb.append("\t\tthis." + t.getJavaFieldSetter() + "(1);" + nl);
					sb.append("\t}" + nl + nl);
					sb.append("\tpublic void " + t.getNotIndFieldSetter() + "() {" + nl);
					sb.append("\t\tthis." + t.getJavaFieldSetter() + "(0);" + nl);
					sb.append("\t}" + nl + nl);
				}
			}
		}
		Enumeration e4 = maingen.getModelLibraryList().elements();
		while (e4.hasMoreElements()) {
			ModelLibraryModel t = (ModelLibraryModel) e4.nextElement();
			if (t.getParentTable() != null) {
				if (t.getParentTable().compareTo(m.getTableName())==0) {
					sb.append("\tpublic " + t.getListName() + " get" + t.getListName() +  "() {" + nl);
					sb.append("\t\treturn " + t.getListVariableName() +  ";" + nl);
					sb.append("\t}" + nl + nl);
					sb.append("\tpublic void set" + t.getListName() +  "(" + t.getListName() + " m) {" + nl);
					sb.append("\t\t" + t.getListVariableName() +  " = m;" + nl);
					sb.append("\t}" + nl + nl);
				}
			}
		}
		sb.append(nl);
		
		sb.append("public static ResultColumnList getResultColumnList() {" + nl);
		sb.append("	ResultColumnList columns = new ResultColumnList();" + nl);
		Enumeration e18 = maingen.getTableLibraryList().elements();
		int pos = 0;
		while (e18.hasMoreElements()) {
			TableLibraryModel t = (TableLibraryModel) e18.nextElement();
			if (t.getTableName().compareTo(m.getTableName())==0) {
				sb.append("		columns.addColumn(\""
						+ t.getJavaFieldName()+ "\", " + t.getClassName() + ");" + nl);
				pos++;
				if (t.getLowerColumnName().endsWith(ref_id)) {
					sb.append("		columns.addColumn(\""
							+ t.getJavaFieldName().replaceAll(RefId,RefDisplay)+ "\", String.class);" + nl);
					pos++;
				}
				if (t.getLowerColumnName().endsWith(user_id)) {
					sb.append("		columns.addColumn(\""
							+ t.getJavaFieldName().replaceAll(UserId,UserName)+ "\", String.class);" + nl);
					pos++;
				}
			}
		}
		sb.append("		return columns;" + nl);
		sb.append("	}" + nl);
		
		
		sb.append("		" + nl
				+ "\t/** " + nl
				+ "\t * Flags this field as being modified" + nl
				+ "\t */ "+ nl
				+ "	public void setReferenceDisplay(IReferenceMap reference, ISecurity security) {" + nl
				+ "		try {" + nl
				+ "			if (referenceSet) {" + nl
				+ "				return;" + nl
				+ "			}" + nl
				+ "		referenceSet = true;" + nl);
		Enumeration e6 = maingen.getTableLibraryList().elements();
		while (e6.hasMoreElements()) {
			TableLibraryModel t = (TableLibraryModel) e6.nextElement();
			if (t.getTableName().compareTo(m.getTableName())==0) {
				if (t.getLowerColumnName().endsWith(ref_id)) {
					sb.append("			if (reference != null && this." + t.getJavaFieldName() + " > 0) " + t.getJavaFieldSetter().replaceAll(RefId,RefDisplay) 
						+ "(reference.getDisplay(this." + t.getJavaFieldName() 
						+ "));" + nl);
				}
				if (t.getLowerColumnName().endsWith(user_id)) {
					sb.append("			if (security != null && this." + t.getJavaFieldName() + " > 0) " + t.getJavaFieldSetter().replaceAll(UserId,UserName) 
						+ "(security.getUser(this." + t.getJavaFieldName() 
						+ ").getDisplayName());" + nl);
				}
			}
		}
		sb.append("		} catch (Exception e) {" + nl
				+ "			Debug.LogException(this, e);" + nl
				+ "		}" + nl
				+ "	}");
		sb.append("		" + nl
		+ "\t/** " + nl
		+ "\t * Flags this field as being modified" + nl
		+ "\t */ "+ nl
		+ "\tpublic void setModified(String fieldName) {" + nl
		+ "\t\tif (!modifiedFields.containsKey(fieldName)) {" + nl
		+ "\t\t\tmodifiedFields.put(fieldName, \"1\");" + nl
		+ "\t\t}" + nl
		+ "\t}" + nl + nl
		+ "\t/** " + nl
		+ "\t * Resets flagged fields so none are marked as modified" + nl
		+ "\t */ "+ nl
		+ "\tpublic void resetModified() {" + nl
		+ "\t\tmodifiedFields = new Hashtable(hashsize);" + nl
		+ "\t}" + nl + nl
		+ "\tpublic boolean hasModified() {" + nl
		+ "\t\treturn modifiedFields.size() > 0;" + nl
		+ "\t}" + nl + nl
		+ "	" + nl);
		
		
		sb.append("		" + nl
		+ "\t/** " + nl
		+ "\t * Copys the values of all modified fields to object m" + nl
		+ "\t */ "+ nl
		+ "\tpublic void copyModifiedTo(" + m.getDataModelName() + " m) {" + nl
		+ "\t\tEnumeration e1 = modifiedFields.keys();" + nl
		+ "\t\twhile (e1.hasMoreElements()) {" + nl
		+ "\t\t\tString fieldName = (String) e1.nextElement();" + nl + nl);

		Enumeration e3 = maingen.getTableLibraryList().elements();
		int cnt=0;
		while (e3.hasMoreElements()) {
			TableLibraryModel t = (TableLibraryModel) e3.nextElement();
			if (t.getTableName().compareTo(m.getTableName())==0) {
				if (cnt==0) { sb.append("\t\t\t"); }
				if (cnt==1) { sb.append("\t\t\telse "); }
				sb.append(
				"if (fieldName.compareTo(\"" + t.getJavaFieldName() + "\")==0)" + nl
			  + "\t\t\t\tm." + t.getJavaFieldSetter() + "(this." + t.getJavaFieldGetter() + "());"+nl);
				if (cnt>0) {
					sb.append("			else ");
				}			  
			    cnt++;
			}
		}
		sb.append("\t\t" + nl);
		sb.append("\t\t\t\tDebug.LogError(this, ExceptionInfo.invalidCopyModifiedField + \" \" + fieldName);" + nl);
		sb.append("\t}" + nl);
		sb.append( "}" + nl + nl);
		
		sb.append("	/**" + nl
				+ "	 * Update this object with the data from m" + nl
				+ "	 */");
		sb.append(nl);
		sb.append("	public void updateWith (" + m.getModelName()+ " m) {" + nl
		+ "" + nl);
		Enumeration e10 = maingen.getTableLibraryList().elements();
		while (e10.hasMoreElements()) {
			TableLibraryModel tlm = (TableLibraryModel) e10.nextElement();
			if (tlm.getTableName().compareTo(m.getTableName())==0) {
				sb.append("		this." + tlm.getJavaFieldSetter() + "(m." + tlm.getJavaFieldGetter() + "());" + nl);
				if (tlm.getLowerColumnName().endsWith(ref_id)) {
					sb.append("		this." + tlm.getJavaFieldSetter().replaceAll(RefId,RefDisplay) + "(m." + tlm.getJavaFieldGetter().replaceAll(RefId,RefDisplay) + "());" + nl);
				}
				if (tlm.getLowerColumnName().endsWith(user_id)) {
					sb.append("		this." + tlm.getJavaFieldSetter().replaceAll(UserId,UserName) + "(m." + tlm.getJavaFieldGetter().replaceAll(UserId,UserName) + "());" + nl);
				}
			}
		}
		sb.append( "	}" + nl + nl);
		
		if (m.getTableName().toLowerCase().endsWith("_history")) {
			String origTable = m.getTableName().substring(0,m.getTableName().length() - "_history".length());
			String origModel = m.getModelName().replaceAll("HistoryModel","Model");
			sb.append("	public void updateWith (" + origModel + " m) {" + nl
					+ "" + nl);
			Enumeration e19 = maingen.getTableLibraryList().elements();
			while (e19.hasMoreElements()) {
				TableLibraryModel tlm = (TableLibraryModel) e19.nextElement();
				if (tlm.getTableName().compareTo(origTable)==0) {
					sb.append("		this." + tlm.getJavaFieldSetter() + "(m." + tlm.getJavaFieldGetter() + "());" + nl);
				}
			}
			sb.append( "	}" + nl + nl);
		}
		
		sb.append("	/**" + nl
				+ "	 * Compare the two objects" + nl
				+ "	 */");
		sb.append(nl);
		sb.append("	public boolean isEqualTo (" + m.getModelName()+ " m) {" + nl
		+ "" + nl);
		Enumeration e15 = maingen.getTableLibraryList().elements();
		while (e15.hasMoreElements()) {
			TableLibraryModel tlm = (TableLibraryModel) e15.nextElement();
			if (tlm.getTableName().compareTo(m.getTableName())==0) {
				if (!tlm.getJavaFieldGetter().equals("getUpdateUserId")
						&& !tlm.getJavaFieldGetter().equals("getUpdateDt")) {
					sb.append("		if (!Comparison.areEqual(this." + tlm.getJavaFieldGetter() + "(),  m." + tlm.getJavaFieldGetter() + "())) return false;" + nl);
				}
			}
		}
		sb.append( "		return true;" + nl);
		sb.append( "	}" + nl);
		
		
		sb.append("	/**" + nl
				+ "	 * Returns a list of all field names which are required and are null" + nl
				+ "	 */");
		sb.append(nl);
		sb.append("	public Vector getMissingRequired () {" + nl
		+ "" + nl 
		+ "		Vector v = new Vector();" + nl);
		Enumeration e16 = maingen.getTableLibraryList().elements();
		while (e16.hasMoreElements()) {
			TableLibraryModel tlm = (TableLibraryModel) e16.nextElement();
			if (tlm.getTableName().compareTo(m.getTableName())==0) {
				if (tlm.getRequiredInd()==1) {
					if (tlm.getJavaType().compareTo("String")==0 || 
							tlm.getJavaType().compareTo("GregorianCalendar")==0 ||
							tlm.getJavaType().compareTo("DbCalendar")==0) {
						sb.append("		if (" + tlm.getJavaFieldGetter() + "()== null) v.add(\"" + tlm.getJavaFieldName() + "\");" + nl);
					}
				}
			}
		}
		sb.append( "		return v;" + nl);
		sb.append( "	}" + nl);
						
		
		sb.append("	public String toString() {" + nl
			 + "		try {" + nl
			 + "			StringBuffer sb = new StringBuffer(1024);" + nl
			 + "			sb.append(\"" + m.getModelName() + ":\\r\\n\");" + nl); 	

		Enumeration e5 = maingen.getTableLibraryList().elements();
		while (e5.hasMoreElements()) {
			TableLibraryModel tlm = (TableLibraryModel) e5.nextElement();
			if (tlm.getTableName().compareTo(m.getTableName())==0) {
				if (tlm.getJavaType().indexOf("Calendar")>=0) {
					sb.append("			sb.append(\"" + tlm.getColumnName().toLowerCase() + ":\" + CalendarUtility.Format(" + tlm.getJavaFieldGetter() + "(),AppFormats.getLongDateTimeFormat()));" + nl); 
					sb.append("			sb.append(\"\\r\\n\");" + nl); 
				} else {
					sb.append("			sb.append(\"" + tlm.getColumnName().toLowerCase() + ":\" + " + tlm.getJavaFieldGetter() + "());" + nl); 
					sb.append("			sb.append(\"\\r\\n\");" + nl); 
				}
				if (tlm.getLowerColumnName().endsWith(ref_id)) {
					sb.append("			sb.append(\"" + tlm.getColumnName().toLowerCase().replaceAll(ref_id,ref_display) + ":\" + " + tlm.getJavaFieldGetter().replaceAll(RefId,RefDisplay) + "());" + nl);
					sb.append("			sb.append(\"\\r\\n\");" + nl); 
				}
				if (tlm.getLowerColumnName().endsWith(user_id)) {
					sb.append("			sb.append(\"" + tlm.getColumnName().toLowerCase().replaceAll(user_id,user_name) + ":\" + " + tlm.getJavaFieldGetter().replaceAll(UserId,UserName) + "());" + nl);
					sb.append("			sb.append(\"\\r\\n\");" + nl); 
				}
			}
		}
		
		Enumeration e17 = maingen.getModelLibraryList().elements();
		while (e17.hasMoreElements()) {
			ModelLibraryModel t = (ModelLibraryModel) e17.nextElement();
			if (t.getParentTable() != null) {
				if (t.getParentTable().compareTo(m.getTableName())==0) {
					sb.append("			sb.append(" + t.getListVariableName() + ".toString());" + nl);
				}
			}
		}
		
		sb.append("			return sb.toString();" + nl);
		sb.append("		} catch (Exception ex) {" + nl);
		sb.append("			return \"" + m.getModelName() + ":\"; " + nl);
		sb.append("		}" + nl);
		sb.append("	}" + nl); 
		
/*		sb.append("	public static Vector getColumnNames() {" + nl
				+ "		Vector v = getOriginalOrderColumnNames();" + nl
				+ "		Vector sorted = new Vector(v.size());" + nl
				+ "		for (int i=0; i<v.size(); i++) {" + nl
				+ "			sorted.add(v.get(getColumnOrder(i)));" + nl
				+ "		}" + nl
				+ "		return sorted;" + nl
				+ "}" + nl + nl);
		
		sb.append("	public static Vector getOriginalOrderColumnNames() {" + nl);
		sb.append("		Vector v = new Vector();" + nl);
		Enumeration e7 = maingen.getTableLibraryList().elements();
		while (e7.hasMoreElements()) {
			TableLibraryModel tlm = (TableLibraryModel) e7.nextElement();
			if (tlm.getTableName().compareTo(m.getTableName())==0) {
				sb.append("		v.add(\"" + tlm.getColumnName().toLowerCase() + "\");" + nl);
				if (tlm.getLowerColumnName().endsWith(ref_id)) {
					sb.append("		v.add(\"" + tlm.getColumnName().toLowerCase().replaceAll(ref_id, ref_display) + "\");" + nl);
				}
			}
		}
		sb.append("		return v;" + nl);
		sb.append("	}" + nl + nl);
*/
				
		if (m.getTableModelInd()==1) {
			Hashtable modelHash = new Hashtable();
			sb.append("	public void setModelColDataAt(Object o, int modelCol) {" + nl);
			Enumeration e24 = maingen.getTableLibraryList().elements();
			sb.append("			switch (modelCol) {" + nl);
			while (e24.hasMoreElements()) {
				TableLibraryModel tlm = (TableLibraryModel) e24.nextElement();
				if (tlm.getTableName().compareTo(m.getTableName())==0) {
					int modelColumnRefId = 0;
					String sql = "select ref_id"
						+ " from reference where reference_group = 'ModelColumn'"
						+ " and display_code = ?";
					Vector params = new Vector();
					String code = tlm.getTableName().replace("_","").toUpperCase()
					+ tlm.getJavaFieldName().toUpperCase();
					params.add(code);
					try {
						ResultSet rset = Db.access.executeQuery(sql,params);
						if (rset.next()) {
							modelColumnRefId = rset.getInt("ref_id");
						}
						rset.close();
						if (modelColumnRefId==0) {
							modelColumnRefId = startSeq;
							startSeq++;
							sql = MainGenerator.getInsertReference();
							params = new Vector();
							params.add(new Integer(modelColumnRefId));
							params.add("ModelColumn");
							params.add(code);
							params.add(new Integer(0));
							params.add(tlm.getTableName().replace("_","") + " " + tlm.getJavaFieldName());
							Db.access.executeUpdate(MainGenerator.getInsertReference(), params);
							Db.access.conn.commit();						}
						modelHash.put(code, new Integer(modelColumnRefId));
					} catch (Exception ex) {
						ex.printStackTrace();
						System.err.println(params);
					}

					if (tlm.getJavaType().compareTo("int")==0) {
						sb.append("			case " + modelColumnRefId + ": " + tlm.getJavaFieldSetter() + "(((Integer) o).intValue()); break;" + nl);
					} else if (tlm.getJavaType().compareTo("double")==0) { 
						sb.append("			case " + modelColumnRefId + ": " + tlm.getJavaFieldSetter() + "(((Double) o).doubleValue()); break;" + nl);
					} else if (tlm.getJavaType().compareTo("GregorianCalendar")==0) { 
						sb.append("			case " + modelColumnRefId + ": " + tlm.getJavaFieldSetter() + "((GregorianCalendar) o); break;" + nl);
					} else {
						sb.append("			case " + modelColumnRefId + ": " + tlm.getJavaFieldSetter() + "((String) o); break;" + nl);
					}
					if (tlm.getLowerColumnName().endsWith(ref_id)) {
						modelColumnRefId=0;
						params = new Vector();
						code = tlm.getTableName().replace("_","").toUpperCase()
						+ tlm.getJavaFieldName().replaceAll(RefId, RefDisplay).toUpperCase();
						params.add(code);
						try {
							ResultSet rset = Db.access.executeQuery(sql,params);
							if (rset.next()) {
								modelColumnRefId = rset.getInt("ref_id");
							}
							rset.close();
							if (modelColumnRefId==0) {
								modelColumnRefId = startSeq;
								startSeq++;
								sql = MainGenerator.getInsertReference();
								params = new Vector();
								params.add(new Integer(modelColumnRefId));
								params.add("ModelColumn");
								params.add(code);
								params.add(new Integer(0));
								params.add(tlm.getTableName().replace("_","") + " " + tlm.getJavaFieldName().replaceAll(RefId,RefDisplay).toUpperCase());
								Db.access.executeUpdate(MainGenerator.getInsertReference(), params);
								Db.access.conn.commit();
							}
							modelHash.put(code, new Integer(modelColumnRefId));
						} catch (Exception ex) {
							ex.printStackTrace();
							System.err.println(sql);
							System.err.println(params);
						}
					}
					if (tlm.getLowerColumnName().endsWith(user_id)) {
						modelColumnRefId=0;
						params = new Vector();
						code = tlm.getTableName().replace("_","").toUpperCase()
						+ tlm.getJavaFieldName().replaceAll(UserId, UserName).toUpperCase();
						params.add(code);
						try {
							ResultSet rset = Db.access.executeQuery(sql,params);
							if (rset.next()) {
								modelColumnRefId = rset.getInt("ref_id");
							}
							rset.close();
							if (modelColumnRefId==0) {
								modelColumnRefId = startSeq;
								startSeq++;
								sql = MainGenerator.getInsertReference();
								params = new Vector();
								params.add(new Integer(modelColumnRefId));
								params.add("ModelColumn");
								params.add(code);
								params.add(new Integer(0));
								params.add(tlm.getTableName().replace("_","") + " " + tlm.getJavaFieldName().replaceAll(UserId, UserName).toUpperCase());
								Db.access.executeUpdate(MainGenerator.getInsertReference(), params);
								Db.access.conn.commit();
							}
							modelHash.put(code, new Integer(modelColumnRefId));
						} catch (Exception ex) {
							ex.printStackTrace();
							System.err.println(params);
						}
						sb.append("			case " + modelColumnRefId + ": " + tlm.getJavaFieldSetter().replaceAll(UserId,UserName) + "((String) o); break;" + nl);
					}
				}
			}
			if (m.getUserDefinedInd()==1) {
				sb.append("		default: setModelColExtendedDataAt(o, modelCol); break;" + nl);
			} else {
				sb.append("			default: Debug.LogWarning(this, ReferenceServices.getMsg(SystemMessageFramework.SWITCHSTATEMENTFAILED) + \" modelColRefId: \" + modelCol);" + nl);
			}
			sb.append("	} " + nl	+ "}" + nl + nl);
			
			
			
			sb.append("	public Object getModelColDataAt(int modelCol) {" + nl);
			Enumeration e8 = maingen.getTableLibraryList().elements();
			int i=0;
			while (e8.hasMoreElements()) {
				TableLibraryModel tlm = (TableLibraryModel) e8.nextElement();
				if (tlm.getTableName().compareTo(m.getTableName())==0) {
					String code = tlm.getTableName().replace("_","").toUpperCase()
					+ tlm.getJavaFieldName().toUpperCase();
					Integer integer = (Integer) modelHash.get(code);
					int modelColumnRefId = integer.intValue();
					if (tlm.getJavaType().compareTo("int")==0) {
						sb.append("			if (modelCol == " + modelColumnRefId + ") return new Integer(" + tlm.getJavaFieldName() + ");" + nl);
					} else if (tlm.getJavaType().compareTo("double")==0) { 
						sb.append("			if (modelCol == " + modelColumnRefId + ") return new Double(" + tlm.getJavaFieldName() + ");" + nl);
					} else {
						sb.append("			if (modelCol == " + modelColumnRefId + ") return " + tlm.getJavaFieldName() + ";" + nl);
					}
					if (tlm.getLowerColumnName().endsWith(ref_id)) {
						sb.append("			if (modelCol == " + modelColumnRefId + ") return " + tlm.getJavaFieldName().replaceAll(RefId,RefDisplay) + ";" + nl);
					}
					if (tlm.getLowerColumnName().endsWith(user_id)) {
						sb.append("			if (modelCol == " + modelColumnRefId + ") return " + tlm.getJavaFieldName().replaceAll(UserId,UserName) + ";" + nl);
					}
				}
			}
			if (m.getUserDefinedInd()==1) {
				sb.append("		return getModelColExtendedDataAt(modelCol);" + nl);
			} else {
				sb.append("		return null;" + nl);
			}
			sb.append("	}" + nl + nl);
			
			sb.append("	public int getModelColDatabaseDataType(int modelCol) {" + nl);
			Enumeration e21 = maingen.getTableLibraryList().elements();
			i=0;
			while (e21.hasMoreElements()) {
				TableLibraryModel tlm = (TableLibraryModel) e21.nextElement();
				if (tlm.getTableName().compareTo(m.getTableName())==0) {
					String code = tlm.getTableName().replace("_","").toUpperCase()
					+ tlm.getJavaFieldName().toUpperCase();
					Integer integer = (Integer) modelHash.get(code);
					int modelColumnRefId = integer.intValue();
					if (tlm.getJavaType().compareTo("int")==0) {
						sb.append("			if (modelCol == " + modelColumnRefId + ") return DatabaseDataTypeFramework.INTEGER;" + nl);
					} else if (tlm.getJavaType().compareTo("double")==0) { 
						sb.append("			if (modelCol == " + modelColumnRefId + ") return DatabaseDataTypeFramework.DOUBLE;" + nl);
					} else if (tlm.getJavaType().compareTo("GregorianCalendar")==0) { 
						sb.append("			if (modelCol == " + modelColumnRefId + ") return DatabaseDataTypeFramework.DATE;" + nl);
					} else {
						sb.append("			if (modelCol == " + modelColumnRefId + ") return DatabaseDataTypeFramework.STRING;" + nl);
					}
					if (tlm.getLowerColumnName().endsWith(ref_id)) {
						sb.append("			if (modelCol == " + modelColumnRefId + ") return DatabaseDataTypeFramework.STRING;" + nl);
					}
					if (tlm.getLowerColumnName().endsWith(user_id)) {
						sb.append("			if (modelCol == " + modelColumnRefId + ") return DatabaseDataTypeFramework.STRING;" + nl);
					}
				}
			}
			if (m.getUserDefinedInd()==1) {
				sb.append("		return getModelColExtendedDatabaseDataType(modelCol);" + nl);
			} else {
				sb.append("			return DatabaseDataTypeFramework.STRING;" + nl);
			}
			sb.append("	}" + nl + nl);
			
			String pkeyColumn = maingen.getIndexLibraryList().getPrimaryKey(m.getTableName());
			if (pkeyColumn!=null) { 
				pkeyColumn = m.FormatName(pkeyColumn);
				sb.append("	public boolean isNew() {" + nl
						+ "		return get" + pkeyColumn + "() == 0;" + nl
						+ "	}" + nl + nl);				
				Enumeration e22 = maingen.getTableLibraryList().elements();
				while (e22.hasMoreElements()) {
					TableLibraryModel tlm = (TableLibraryModel) e22.nextElement();
					if (tlm.getTableName().compareTo(m.getTableName())==0
							&& tlm.getJavaFieldName().compareToIgnoreCase(pkeyColumn)==0) {
						sb.append("	public Object getPrimaryValue() {" + nl
						+ "		return new Integer(" + tlm.getJavaFieldGetter() + "());" + nl
						+ "	}" + nl + nl);				
					}
				}
			}
			sb.append("	public Object getDataAt(int i) {" + nl);
			Enumeration e26 = maingen.getTableLibraryList().elements();
			i=0;
			while (e26.hasMoreElements()) {
				TableLibraryModel tlm = (TableLibraryModel) e26.nextElement();
				if (tlm.getTableName().compareTo(m.getTableName())==0) {
					if (tlm.getJavaType().compareTo("int")==0) {
						sb.append("			if (i == " + i + ") return new Integer(" + tlm.getJavaFieldName() + ");" + nl);
					} else if (tlm.getJavaType().compareTo("double")==0) { 
						sb.append("			if (i == " + i + ") return new Double(" + tlm.getJavaFieldName() + ");" + nl);
					} else {
						sb.append("			if (i == " + i + ") return " + tlm.getJavaFieldName() + ";" + nl);
					}
					i++;
					if (tlm.getLowerColumnName().endsWith(ref_id)) {
						sb.append("			if (i == " + i + ") return " + tlm.getJavaFieldName().replaceAll(RefId,RefDisplay) + ";" + nl);
						i++;
					}
					if (tlm.getLowerColumnName().endsWith(user_id)) {
						sb.append("			if (i == " + i + ") return " + tlm.getJavaFieldName().replaceAll(UserId,UserName) + ";" + nl);
						i++;
					}
				}
			}
			if (m.getUserDefinedInd()==1) {
				sb.append("		return getExtendedDataAt(i-" + fieldcnt + ");" + nl);
			} else {
				sb.append("		return null;" + nl);
			}
			sb.append("	}" + nl + nl);
			
			sb.append("	public int getDatabaseDataType(int i) {" + nl);
			Enumeration e27 = maingen.getTableLibraryList().elements();
			i=0;
			while (e27.hasMoreElements()) {
				TableLibraryModel tlm = (TableLibraryModel) e27.nextElement();
				if (tlm.getTableName().compareTo(m.getTableName())==0) {
					if (tlm.getJavaType().compareTo("int")==0) {
						sb.append("			if (i == " + i + ") return DatabaseDataTypeFramework.INTEGER;" + nl);
					} else if (tlm.getJavaType().compareTo("double")==0) { 
						sb.append("			if (i == " + i + ") return DatabaseDataTypeFramework.DOUBLE;" + nl);
					} else if (tlm.getJavaType().compareTo("GregorianCalendar")==0) { 
						sb.append("			if (i == " + i + ") return DatabaseDataTypeFramework.DATE;" + nl);
					} else {
						sb.append("			if (i == " + i + ") return DatabaseDataTypeFramework.STRING;" + nl);
					}
					i++;
					if (tlm.getLowerColumnName().endsWith(ref_id)) {
						sb.append("			if (i == " + i + ") return DatabaseDataTypeFramework.STRING;" + nl);
						i++;
					}
					if (tlm.getLowerColumnName().endsWith(user_id)) {
						sb.append("			if (i == " + i + ") return DatabaseDataTypeFramework.STRING;" + nl);
						i++;
					}
				}
			}
			if (m.getUserDefinedInd()==1) {
				sb.append("		return getDatabaseExtendedDataType(i-" + fieldcnt + ");" + nl);
			} else {
				sb.append("			return DatabaseDataTypeFramework.STRING;" + nl);
			}
			sb.append("	}" + nl + nl);
			
			sb.append("	public void setDataAt(Object o, int i) {" + nl);
			sb.append("		switch(i) {" + nl);
			Enumeration e28 = maingen.getTableLibraryList().elements();
			i=0;
			while (e28.hasMoreElements()) {
				TableLibraryModel tlm = (TableLibraryModel) e28.nextElement();
				if (tlm.getTableName().compareTo(m.getTableName())==0) {
					if (tlm.getJavaType().compareTo("int")==0) {
						sb.append("			case " + i + ": " + tlm.getJavaFieldSetter() + "(((Integer) o).intValue()); break;" + nl);
					} else if (tlm.getJavaType().compareTo("double")==0) { 
						sb.append("			case " + i + ": " + tlm.getJavaFieldSetter() + "(((Double) o).doubleValue()); break;" + nl);
					} else if (tlm.getJavaType().compareTo("GregorianCalendar")==0) { 
						sb.append("			case " + i + ": " + tlm.getJavaFieldSetter() + "((GregorianCalendar) o); break;" + nl);
					} else {
						sb.append("			case " + i + ": " + tlm.getJavaFieldSetter() + "((String) o); break;" + nl);
					}
					i++;
					if (tlm.getLowerColumnName().endsWith(ref_id)) {
						sb.append("			case " + i + ": " + tlm.getJavaFieldSetter().replaceAll(RefId,RefDisplay) + "((String) o); break;" + nl);
						i++;
					}
					if (tlm.getLowerColumnName().endsWith(user_id)) {
						sb.append("			case " + i + ": " + tlm.getJavaFieldSetter().replaceAll(UserId,UserName) + "((String) o); break;" + nl);
						i++;
					}
				}
			}
			if (m.getUserDefinedInd()==1) {
				sb.append("		default: setExtendedDataAt(o, i-" + i + ");" + nl);
			}
			sb.append("		}" + nl);
			sb.append("	}" + nl + nl);
			
		}
				
/*		if (m.getTableModelInd()==1) {
			sb.append("	public static Class[] getColumnClasses() {" + nl);
			sb.append("		Class[] classes = new Class[getNumberOfColumns()];" + nl);
			Enumeration e9 = maingen.getTableLibraryList().elements();
			int i=0;
			while (e9.hasMoreElements()) {
				TableLibraryModel tlm = (TableLibraryModel) e9.nextElement();
				if (tlm.getTableName().compareTo(m.getTableName())==0) {
					if (tlm.getJavaType().compareTo("int")==0) {
						sb.append("			classes[getColumnOrder(" + i + ")] = Integer.class;" + nl);
					} else if (tlm.getJavaType().compareTo("double")==0) { 
						sb.append("			classes[getColumnOrder(" + i + ")] = Double.class;" + nl);
					} else {
						sb.append("			classes[getColumnOrder(" + i + ")] = " + tlm.getJavaType() + ".class;" + nl);
					}
					i++;
					if (tlm.getLowerColumnName().endsWith(ref_id)) {
						sb.append("			classes[getColumnOrder(" + i + ")] = String.class;" + nl);
						i++;
					}
				}
			}
			sb.append("		return classes;" + nl);
			sb.append("	}" + nl);
		}
		
		sb.append("	private static int nbrColumns = 0;" + nl);
		sb.append("	public static int getNumberOfColumns() {" + nl);
		sb.append("		if (nbrColumns == 0) {" + nl);
		sb.append("			nbrColumns = getColumnNames().size();" + nl);
		sb.append("		}" + nl);
		sb.append("		return nbrColumns;" + nl);
		sb.append("	}" + nl + nl);
		
		sb.append("	public static int getColumnOrder(int originalPosition) { " + nl);
		sb.append("		switch (originalPosition) {" + nl);
		Enumeration e14 = maingen.getTableLibraryList().elements();
		int i=0;
		while (e14.hasMoreElements()) {
			TableLibraryModel tlm = (TableLibraryModel) e14.nextElement();
			if (tlm.getTableName().compareTo(m.getTableName())==0) {
				sb.append("			case " + i + ": return " + i + "; //" + tlm.getColumnName() + nl);
				i++;
				if (tlm.getLowerColumnName().endsWith(ref_id)) {
					sb.append("			case " + i + ": return " + i + "; //" + tlm.getColumnName().replaceAll(ref_id,ref_display) + nl);
					i++;
				}
			}
		}
		sb.append("		}" + nl);
		sb.append("		return originalPosition;" + nl);
		sb.append("	}" + nl);
			
*/	
				
		sb.append("}");
		return sb.toString();
	
	}

	
	public String getListSource(ModelLibraryModel m) {
		StringBuffer sb = new StringBuffer(1024);
		
		String pkeyColumn = maingen.getIndexLibraryList().getPrimaryKey(m.getTableName());
		if (pkeyColumn!=null) { 
			pkeyColumn = m.FormatName(pkeyColumn);
		}

		
		sb.append(MainGenerator.getLicense(m.getDescription()) + nl
		+ "package " + m.getJavaPackage() + ";" + nl
		+ "" + nl
		+ "import java.util.*;" + nl
		+ "import javax.swing.*;" + nl
		+ "import javax.swing.event.*;" + nl
		+ "import com." + MainGenerator.framework + ".modellibrary.common.*;" + nl
		+ "import com." + MainGenerator.framework + ".modellibrary.reference.common.*;" + nl
		+ "import com." + MainGenerator.framework + ".modellibrary.reference.security.*;" + nl
		+ "import com." + MainGenerator.framework + ".ejb.reference.security.*;" + nl
		+ "import com." + MainGenerator.framework + ".ejb.reference.common.*;" + nl
		+ "import com." + MainGenerator.framework + ".framework.logging.*;" + nl);
		if (m.getUserDefinedInd()==1) {
			sb.append("import com." + MainGenerator.framework + ".modellibrary.framework.*;");
		}
		sb.append("" + nl
		+ "/**" + nl
		+ m.getDescription() + nl
		+ "*/" + nl
		+ "public class " + m.getListName());
		if (m.getUserDefinedInd()==1) {
			sb.append(" extends ExtendedResultList");
		} else if (m.getTableModelInd()==1) {
			sb.append(" extends ResultList");
		} 
		if (m.getListModelInd() == 1) {
			sb.append(" implements  Enumeration, ListModel, java.io.Serializable {" + nl);
		} else {
			sb.append(" implements  Enumeration, java.io.Serializable {" + nl);
		}
		sb.append( "" + nl
		+ "	private static final long serialVersionUID = 1L;" +nl);
		if (pkeyColumn==null || m.getTableModelInd()==1) {
			sb.append("	private Vector list = new Vector();" + nl);
		} else {
			sb.append("	private Hashtable list = new Hashtable();" + nl);
		}
		if (m.getTableModelInd()==1) {
			sb.append("	private ResultColumnList columns = " + m.getModelName()
					+ ".getResultColumnList();"+nl);			
		}
		sb.append("	private transient Enumeration enumeration = list.elements();" + nl
		+ "	" + nl);
		sb.append("	public " + m.getListName() + "() {" + nl
		+ "	" + nl
		+ "	} " + nl + nl);
		sb.append("	public " + m.getListName() + "(int initialCapacity) {" + nl);
		if (pkeyColumn==null || m.getTableModelInd()==1) {
			sb.append("		list = new Vector(initialCapacity);" + nl);
		} else {
			sb.append("		list = new Hashtable(initialCapacity);" + nl);
		}
		sb.append("	}" + nl + nl);
		sb.append("	public Enumeration elements() {" + nl
		+ "		enumeration = list.elements();" + nl
		+ "		return enumeration;" + nl
		+ "	}" + nl
		+ "	" + nl
		+ "	public Object nextElement() {" + nl
		+ "		return enumeration.nextElement();" + nl
		+ "	}" + nl
		+ "	" + nl
		+ "	public boolean hasMoreElements() {" + nl
		+ "		return enumeration.hasMoreElements();" + nl
		+ "	}" + nl
		+ "	" + nl
		+ "	" + nl
		+ "	public void sort() {" + nl);
		if (pkeyColumn==null || m.getTableModelInd()==1) {
			sb.append("		Collections.sort(list);" + nl);
		} else {
			sb.append("		return;" + nl);
		}
		sb.append("		" + nl
		+ "	}" + nl + nl);
		if (pkeyColumn==null || m.getTableModelInd()==1) {
			sb.append( "	public void sortList() {" + nl);
			sb.append("			Collections.sort(list);" + nl);
			sb.append("		}" + nl + nl);
		}		
		sb.append( "	public void add(" + m.getModelName() + " m) {" + nl);
		if (pkeyColumn==null || m.getTableModelInd()==1) {
			sb.append("		this.list.add(m);" + nl);
		} else {
			sb.append("		this.list.put(\"\" + m.get" + pkeyColumn + "(), m);" + nl);
		}
		sb.append("		" + nl
		+ "	}" + nl);
		sb.append( "	public void addAll(" + m.getListName() + " list) {" + nl);
		if (pkeyColumn==null || m.getTableModelInd()==1) {
			sb.append("		Enumeration e1 = list.elements();" + nl);
			sb.append("		while (e1.hasMoreElements()) {" + nl);
			sb.append("			" + m.getModelName() + " m = (" + m.getModelName() + ") e1.nextElement();" + nl);
			sb.append("			this.list.add(m);" + nl);
			sb.append("		}" + nl);
		}
		sb.append("		" + nl
		+ "	}" + nl);
		if (pkeyColumn!=null && m.getTableModelInd()==0) {
			sb.append("	public " + m.getModelName() + " get(int id) {" + nl
			+ "		Object o = this.list.get(\"\" + id);" + nl
			+ "		if (o == null) {" + nl
			+ "			return new " + m.getModelName() + "();" + nl
			+ "		} else { " + nl
			+ "			return (" + m.getModelName() + ") o;" + nl
			+ "		}" + nl
			+ "	}" + nl);
		}
		sb.append("" + nl
		+ "	public int size() {" + nl
		+ "		return this.list.size();" + nl
		+ "	}" + nl
		+ "" + nl);
		
		if (m.getListModelInd() ==1) {
			sb.append("" + nl
					+ "	public int getSize() {" + nl
					+ "		return size();" + nl
					+ "	}" + nl
					+ "" + nl);
			sb.append("" + nl
					+ "	public Object getElementAt(int i) {" + nl
					+ "		return get(i);" + nl
					+ "	}" + nl
					+ "" + nl);
			sb.append("	private Vector dataListeners = new Vector();" + nl
					+ "	public void addListDataListener(ListDataListener dataListener) {" + nl
					+ "		dataListeners.add(dataListener);" + nl
					+ "	}" + nl
					+ "" + nl);
			sb.append("" + nl
					+ "	public void removeListDataListener(ListDataListener dataListener) {" + nl
					+ "		dataListeners.remove(dataListener);" + nl
					+ "	}" + nl
					+ "" + nl);
		}
		
		if (m.getTableModelInd()==1) {
			sb.append("	public int getRowCount() {" + nl
	        + "		return list.size();" + nl
			+ "	}" + nl + nl
			+ "	public int getColumnCount() {" + nl
			+ "		return columns.getColumnCount();" + nl
			+ "	}" + nl + nl
			+ "	public " + m.getModelName() + " elementAt(int rowIndex) {" + nl 
			+ "			return (" + m.getModelName() + ") list.get(rowIndex);" + nl
			+ "	}" + nl + nl
			+ "	public void remove(" + m.getModelName() + " m) {" + nl 
			+ "			list.remove(m);" + nl
			+ "	}" + nl + nl
			+ "	public Object getValueAt(int rowIndex, int columnIndex) {" + nl 
			+ "		try {" + nl
			+ "			" + m.getModelName() + " m = (" + m.getModelName() + ") list.get(rowIndex);" + nl
			+ "			int position = columns.getOriginalPosition(columnIndex);" + nl
			+ "			return m.getDataAt(position);" + nl
			+ "		} catch (ResultColumnException e) {" + nl
			+ "			Debug.LogException(this, e);" + nl
			+ "			return null;" + nl
			+ "		}" + nl
			+ "	}" + nl + nl 
			+ "	public void setModelValueAt(Object o, int rowIndex, int columnIndex) {" + nl 
			+ "		try {" + nl
			+ "			" + m.getModelName() + " m = (" + m.getModelName() + ") list.get(rowIndex);" + nl
			+ "			int position = columns.getOriginalPosition(columnIndex);" + nl
			+ "			m.setDataAt(o, position);" + nl
			+ "		} catch (ResultColumnException e) {" + nl
			+ "			Debug.LogException(this, e);" + nl
			+ "		}" + nl
			+ "	}" + nl + nl
			+ "	public Comparable getValueAt(int rowIndex) {" + nl 
			+ "		" + m.getModelName() + " m = (" + m.getModelName() + ") list.get(rowIndex);" + nl
				+ "		return m;" + nl
				+ "	}" + nl + nl
				+ "	public Class[] getClasses() {" + nl
				+ "		return columns.getClasses();" + nl
				+ "	}" + nl
				+ "	" + nl
				+ "	public Vector getColumnNames() {" + nl
				+ "		return columns.getColumnNames();" + nl
				+ "	}"+nl+nl);

		}

		if (m.getTableModelInd()==1 || m.getListModelInd() == 1) {
		sb.append("	public " + m.getModelName() + " getFirst() {" + nl
				+ "		if (list.size() > 0) {" + nl
				+ "			return (" + m.getModelName() + ") list.elementAt(0);" + nl
				+ "		} else {" + nl
				+ "			return new " + m.getModelName() + "();" + nl
				+ "		}" + nl
				+ "	}" + nl + nl);
		} else {
			sb.append("	public " + m.getModelName() + " getFirst() {" + nl
					+ "		Enumeration e1 = list.elements();" + nl
					+ "		if (e1.hasMoreElements()) {" + nl
					+ "			return (" + m.getModelName() + ") e1.nextElement();" + nl
					+ "		} else {" + nl
					+ "			return new " + m.getModelName() + "();" + nl
					+ "		} " + nl
					+ "	}" + nl + nl);
		}

		sb.append("	public void setReferenceDisplay(IReferenceMap reference, ISecurity security) {" + nl 
		+ "		Enumeration e1 = list.elements();" + nl
		+ "		while (e1.hasMoreElements()) {" + nl
		+ "			" + m.getModelName() + " m = (" + m.getModelName() + ") e1.nextElement();" + nl
		+ " 			m.setReferenceDisplay(reference, security);" + nl
		+ "		}" + nl
		+ "	}" + nl + nl);
		if (m.getTableModelInd()==1) {
			sb.append( "	public void setColumnOrder(ApplicationControlList fields) throws ResultColumnException { " + nl
			+ "		Enumeration e1 = fields.elements();" + nl
			+ "		int pos=0;" + nl
			+ "		while (e1.hasMoreElements()){" + nl
			+ "			ApplicationControlModel acm = (ApplicationControlModel) e1.nextElement();" + nl
			+ "			columns.updatePosition(acm.getControlRefDisplay(), pos, acm.getControlText());" + nl
			+ "			pos++;" + nl
			+ "		}" + nl
			+ "		columns.setColumnCount(fields.size());" + nl
			+ "		if (fields.size()==0) {" + nl
			+ "			Debug.LogWarning(this, \"No columns found, check application security and controls\");" + nl
			+ "		}" + nl
			+ "	}" + nl+ nl);
		}
		if (m.getUserDefinedInd()==1) {
			sb.append("	public void setRecordExtensionColumnList(RecordExtensionColumnList recordExtensionColumnList) {" + nl
					+ "		super.setRecordExtensionColumnList(recordExtensionColumnList);" + nl
					+ "		for (int i=0; i< recordExtensionColumnList.size(); i++) {" + nl
					+ "			RecordExtensionColumnModel m = (RecordExtensionColumnModel) recordExtensionColumnList.elementAt(i);" + nl
					+ " 		columns.addColumn(m.getColumnRefDisplay(), m.getColumnClass());" + nl
					+ "		}" + nl
					+ "	}" + nl + nl);
		}
		sb.append("	public void resetModified() {" + nl
				+ "		Enumeration e1 = list.elements();" + nl
				+ "		while (e1.hasMoreElements()) {" + nl
				+ "			" + m.getModelName() + " m = (" + m.getModelName() + ") e1.nextElement();" + nl
				+ " 		m.resetModified();" + nl
				+ "		}" + nl
				+ "	}" + nl + nl);
		sb.append( "	public String toString() {" + nl 
		+ "		StringBuffer sb = new StringBuffer();" + nl
		+ "		sb.append(\"" + m.getListName() + "\");" + nl
		+ "		sb.append(\"\\r\\n\");" + nl
		+ "		Enumeration e1 = list.elements();" + nl
		+ "		while (e1.hasMoreElements()) {" + nl
		+ "			" + m.getModelName() + " m = (" + m.getModelName() + ") e1.nextElement();" + nl
		+ "				sb.append(m);" + nl
		+ "				sb.append(\"\\r\\n\");" + nl
		+ "		}" + nl
		+ "		return sb.toString();" + nl
		+ "	}" + nl
		+ "}");

		return sb.toString();
	}

	public String getVectorListSource(ModelLibraryModel m) {
		StringBuffer sb = new StringBuffer(1024);
		
		String pkeyColumn = maingen.getIndexLibraryList().getPrimaryKey(m.getTableName());
		if (pkeyColumn!=null) { 
			pkeyColumn = m.FormatName(pkeyColumn);
		}

		
		sb.append(MainGenerator.getLicense(m.getDescription()) + nl
		+ "package " + m.getJavaPackage() + ";" + nl
		+ "" + nl
		+ "import java.util.*;" + nl
		+ "import javax.swing.*;" + nl
		+ "import javax.swing.event.*;" + nl
		+ "import com." + MainGenerator.framework + ".ejb.reference.common.*;" + nl
		+ "import com." + MainGenerator.framework + ".ejb.reference.security.*;" + nl
		+ "import com." + MainGenerator.framework + ".framework.logging.*;" + nl
		+ "" + nl
		+ "/**" + nl
		+ m.getDescription() + nl
		+ "*/" + nl
		+ "public class " + m.getListName());
		sb.append(" extends Vector");
		sb.append(" implements java.io.Serializable {" + nl);
		sb.append( "" + nl
		+ "	private static final long serialVersionUID = 1L;" +nl+nl);
		sb.append("	public " + m.getListName() + "() {" + nl
		+ "	" + nl
		+ "	} " + nl + nl);
		sb.append("	public " + m.getListName() + "(int initialCapacity) {" + nl);
		sb.append("		super(initialCapacity);" + nl);
		sb.append("	}" + nl + nl
		+ "	public void add(" + m.getModelName() + " m) {" + nl);
		sb.append("		super.add(m);" + nl);
		sb.append("		" + nl
		+ "	}" + nl);
		sb.append("	public boolean has" + m.getModelName() + "(int id) {" + nl
				+ "		Enumeration e1 = super.elements();" + nl
				+ "		while (e1.hasMoreElements()) {" + nl
				+ "			" + m.getModelName() + " acm = (" + m.getModelName() + ") e1.nextElement();" + nl
				+ "			if (acm.get" + pkeyColumn + "() == id) {" + nl
				+ "				return true;" + nl
				+ "			}" + nl
				+ "		}" + nl
				+ "		return false;" + nl
				+ "	}" + nl + nl

				+ "	public " + m.getModelName() + " get" + m.getModelName() + "(int id) {" + nl
				+ "		Enumeration e1 = super.elements();" + nl
				+ "		while (e1.hasMoreElements()) {" + nl
				+ "			" + m.getModelName() + " acm = (" + m.getModelName() + ") e1.nextElement();" + nl
				+ "			if (acm.get" + pkeyColumn + "() == id) {" + nl
				+ "				return acm;" + nl
				+ "			}" + nl
				+ "		}" + nl
				+ "		return new " + m.getModelName() + "();" + nl
				+ "	}" +nl + nl);		
		sb.append("	public void setReferenceDisplay(IReferenceMap reference, ISecurity security) {" + nl 
		+ "		Enumeration e1 = elements();" + nl
		+ "		while (e1.hasMoreElements()) {" + nl
		+ "			" + m.getModelName() + " m = (" + m.getModelName() + ") e1.nextElement();" + nl
		+ " 			m.setReferenceDisplay(reference, security);" + nl
		+ "		}" + nl
		+ "	}" + nl + nl);
		sb.append("	public String toString() {" + nl 
		+ "		StringBuffer sb = new StringBuffer();" + nl
		+ "		sb.append(\"" + m.getListName() + "\");" + nl
		+ "		sb.append(\"\\r\\n\");" + nl
		+ "		Enumeration e1 = elements();" + nl
		+ "		while (e1.hasMoreElements()) {" + nl
		+ "			" + m.getModelName() + " m = (" + m.getModelName() + ") e1.nextElement();" + nl
		+ "				sb.append(m);" + nl
		+ "				sb.append(\"\\r\\n\");" + nl
		+ "		}" + nl
		+ "		return sb.toString();" + nl
		+ "	}" + nl
		+ "}");

		return sb.toString();
	}
	
	
	public String getExtendedModelSource(ModelLibraryModel m) {
		StringBuffer sb = new StringBuffer(1024);
		
		sb.append(MainGenerator.getLicense(m.getDescription()) + nl
		+ "package " + m.getJavaPackage() + ";" + nl
		+ "" + nl
		+ "" + nl
		+ "/**" + nl
		+ m.getDescription() + nl
		+ "*/" + nl
		+ "public class " + m.getModelName() + " extends " + m.getDataModelName() + " implements Comparable {" + nl
		+ "" + nl
		+ "	private static final long serialVersionUID = 1L;" + nl
		+ "	public " + m.getModelName() + "() {" + nl + nl
		+ "	}" + nl + nl
		+ "	public int compareTo(Object m) {" + nl
		+ "		return 0;" + nl
		+ "}" + nl
		+ "" + nl
		+ "}");

		return sb.toString();
	}

	
}
