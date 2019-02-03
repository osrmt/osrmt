package generator;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.GregorianCalendar;

import javax.naming.NoInitialContextException;

import com.osframework.ejb.reference.common.ReferenceMapBean;

import models.*;
import utilities.CalendarUtility;
import utilities.FileSystemUtil;
import utilities.GUI;

public class GenerateEjb {
	private MainGenerator maingen = null;
	private String nl = "\r\n";
	
	public GenerateEjb(MainGenerator m) {
		this.maingen = m;
	}
	
	public void CreateBeans() throws java.io.IOException {
		
		String genSourceDir = maingen.props.getProperty(MainGenerator.propGENSRCDIR);
		Enumeration e1 = maingen.getEjbLibraryList().elements();
		while (e1.hasMoreElements()) {
			// Ejb
			EjbLibraryModel ejb = (EjbLibraryModel) e1.nextElement();
			String beanSource = getBeanSource(ejb);
			String frameworkSourceDir = maingen.props.getProperty(MainGenerator.propFRAMEWORKSRCDIR);
			String javaPath;
			if (ejb.getJavaPackage().indexOf("framework")>0) {
				javaPath = ejb.getJavaPath(frameworkSourceDir);
			} else {
				javaPath = ejb.getJavaPath(genSourceDir);
			}
			FileSystemUtil.CreateDirectory(javaPath);
			if (ejb.getOverwriteBeanInd() == 1 && !FileSystemUtil.Exists(javaPath, ejb.getBeanName() + ".java")) {
				FileSystemUtil.CreateFile(javaPath, ejb.getBeanName() + ".java", beanSource);
			}
			
			String remoteSource = getRemoteSource(ejb);
			FileSystemUtil.CreateFile(javaPath, ejb.getBean() + ".java", remoteSource);
			
			String interfaceSource = getInterfaceSource(ejb);
			FileSystemUtil.CreateFile(javaPath, "I" + ejb.getBean() + ".java", interfaceSource);
			
			String remoteHomeSource = getRemoteHomeSource(ejb);
			FileSystemUtil.CreateFile(javaPath, ejb.getBean() + "Home.java", remoteHomeSource);
			
			String localSource = getLocalSource(ejb);
			FileSystemUtil.CreateFile(javaPath, "Local" + ejb.getBean() + ".java", localSource);
			
			String localHomeSource = getLocalHomeSource(ejb);
			FileSystemUtil.CreateFile(javaPath, "Local" + ejb.getBean() + "Home.java", localHomeSource);
			
			String utilSource = getUtilSource(ejb);
			FileSystemUtil.CreateFile(javaPath, ejb.getBean() + "Util.java", utilSource);
			
		}
	}
	
	public String getRemoteHomeSource(EjbLibraryModel m) {
		StringBuffer sb = new StringBuffer(1024);
		sb.append(MainGenerator.getLicense(m.getDescription()) + nl
		+ "package " + m.getJavaPackage() + ";" + nl + nl
		+ "import java.util.*;" + nl 
		+ "import com." + MainGenerator.framework + ".modellibrary.common.*;" + nl
		+ "import com." + MainGenerator.framework + ".modellibrary.reference.security.*;" + nl
		+ "import com." + MainGenerator.framework + ".appclient.ui.tree.*;" + nl
		+ "import javax.swing.tree.*;" + nl
		+ "import com." + MainGenerator.framework + ".modellibrary.reference.group.*;" + nl);
		if (m.getJavaPackage().contains(MainGenerator.root)) {
			sb.append("import com." + MainGenerator.root + ".modellibrary.reference.group.*;" + nl);
		}		
		sb.append(nl +  "public interface " + m.getBean() + "Home extends javax.ejb.EJBHome" + nl
		+ "{" + nl
		+ "\tpublic static final String COMP_NAME=\"" + m.getBean() + "Name\";" + nl
		+ "\tpublic static final String JNDI_NAME=\"ejb/" + m.getBean() + "\";" + nl + nl
		+ "\tpublic " + m.getBean() + " create() throws javax.ejb.CreateException,java.rmi.RemoteException;" + nl + nl);
		
		if (m.isEntity()){
			sb.append("\tpublic " + m.getBean() + " find() throws java.rmi.RemoteException, javax.ejb.FinderException;" + nl + nl);
		}
		sb.append( "}" + nl);

		return sb.toString();
	}
	
	public String getLocalHomeSource(EjbLibraryModel m) {
		StringBuffer sb = new StringBuffer(1024);
		sb.append(MainGenerator.getLicense(m.getDescription()) + nl
		+ "package " + m.getJavaPackage() + ";" + nl + nl
		+ "import com." + MainGenerator.framework + ".appclient.ui.tree.*;" + nl
		+ "import java.util.*;" + nl 
		+ "import javax.swing.tree.*;" + nl
		+ "import com." + MainGenerator.framework + ".modellibrary.common.*;" + nl
		+ "public interface Local" + m.getBean() + "Home extends javax.ejb.EJBLocalHome" + nl
		+ "{" + nl
		+ "	public static final String COMP_NAME=\"Local" + m.getBean() + "Name\";" + nl
		+ "	public static final String JNDI_NAME=\"ejb/Local" + m.getBean() + "\";" + nl
		+ "\tpublic " + m.getBean() + " create();" + nl + nl
		+ "\tpublic " + m.getBean() + " find();" + nl + nl
		);
		sb.append( "}" + nl);

		return sb.toString();
	}
	

	public String getInterfaceSource(EjbLibraryModel m) {
		StringBuffer sb = new StringBuffer(1024);
		sb.append(MainGenerator.getLicense(m.getDescription()) + nl
		+ "package " + m.getJavaPackage() + ";" + nl + nl
		+ "import com." + MainGenerator.framework + ".datalibrary.common.*;" + nl
		+ "import com." + MainGenerator.framework + ".modellibrary.common.*;" + nl
		+ "import com." + MainGenerator.framework + ".modellibrary.reference.security.*;" + nl
		+ "import com." + MainGenerator.framework + ".modellibrary.reference.group.*;" + nl
		+ "import com." + MainGenerator.framework + ".appclient.ui.tree.*;" + nl
		+ "import java.util.*;" + nl
		+ "import javax.swing.tree.*;" + nl
		);
		if (m.getJavaPackage().contains(MainGenerator.root)) {
			sb.append("import com." + MainGenerator.root + ".modellibrary.reference.group.*;" + nl);
		}
		sb.append(maingen.getBusinessMethodList().getImportList(m.getBean()) + nl);
		sb.append( "public interface I" + m.getBean() + nl
		+ "{" + nl);
		Enumeration e1 = maingen.getBusinessMethodList().elements();
		while (e1.hasMoreElements()) {
			BusinessMethodModel bmm = (BusinessMethodModel) e1.nextElement();
			if (m.getBean().compareTo(bmm.getBean()) == 0) {
				sb.append("    /**  " + nl);
				sb.append("     *  " + bmm.getComments() + nl);
				sb.append("     */ " + nl);
				sb.append("    public " + bmm.getSignature() + " throws java.rmi.RemoteException" + bmm.getExceptionList() + ";" + nl);
				sb.append(nl + "" + nl);
			}
		}
		sb.append( "}" + nl);

		return sb.toString();
	}
	
	public String getLocalSource(EjbLibraryModel m) {
		StringBuffer sb = new StringBuffer(1024);
		sb.append(MainGenerator.getLicense(m.getDescription()) + nl
		+ "package " + m.getJavaPackage() + ";" + nl + nl
		+ "import com." + MainGenerator.framework + ".datalibrary.common.*;" + nl
		+ "import com." + MainGenerator.framework + ".modellibrary.common.*;" + nl
		+ "import com." + MainGenerator.framework + ".modellibrary.reference.security.*;" + nl
		+ "import com." + MainGenerator.framework + ".modellibrary.reference.group.*;" + nl
		+ "import com." + MainGenerator.framework + ".appclient.ui.tree.*;" + nl
		+ "import java.util.*;" + nl
		+ "import javax.swing.tree.*;" + nl
		);
		if (m.getJavaPackage().contains(MainGenerator.root)) {
			sb.append("import com." + MainGenerator.root + ".modellibrary.reference.group.*;" + nl);
		}
        sb.append(maingen.getBusinessMethodList().getImportList(m.getBean()) + nl);
		sb.append("public interface Local" + m.getBean() + " extends javax.ejb.EJBLocalObject, I" + m.getBean() + nl
		+ "{" + nl);
		Enumeration e1 = maingen.getBusinessMethodList().elements();
		while (e1.hasMoreElements()) {
			BusinessMethodModel bmm = (BusinessMethodModel) e1.nextElement();
			if (m.getBean().compareTo(bmm.getBean()) == 0) {
				sb.append("    /**  " + nl);
				sb.append("     *  " + bmm.getComments() + nl);
				sb.append("     */ " + nl);
				sb.append("    public " + bmm.getSignature() + " throws java.rmi.RemoteException" + bmm.getExceptionList() + ";" + nl);
				sb.append(nl + "" + nl);
			}
		}
		sb.append( "}" + nl);

		return sb.toString();
	}

	public String getUtilSource(EjbLibraryModel m) {
		StringBuffer sb = new StringBuffer(1024);
		sb.append(MainGenerator.getLicense(m.getDescription()) + nl
		+ "package " + m.getJavaPackage() + ";" + nl
		+ "import javax.naming.NoInitialContextException;" + nl
		+ "import com." + MainGenerator.framework + ".modellibrary.common.*;" + nl
		+ "" + nl
		+ "public class " + m.getBean() + "Util" + nl
		+ "{" + nl
		+ "   private static " + m.getBean() + "Home cachedRemoteHome = null;" + nl
		+ "   private static Local" + m.getBean() + "Home cachedRemoteLocalHome = null;" + nl
		+ "" + nl
		+ "   private static Object lookupHome(java.util.Hashtable environment, String jndiName, Class narrowTo) throws javax.naming.NamingException {" + nl
		+ "      // Obtain initial context" + nl
		+ "      javax.naming.InitialContext initialContext = new javax.naming.InitialContext(environment);" + nl
		+ "      try {" + nl
		+ "         Object objRef = initialContext.lookup(jndiName);" + nl
		+ "         // only narrow if necessary" + nl
		+ "         if (java.rmi.Remote.class.isAssignableFrom(narrowTo))" + nl
		+ "            return javax.rmi.PortableRemoteObject.narrow(objRef, narrowTo);" + nl
		+ "         else" + nl
		+ "            return objRef;" + nl
		+ "      } finally {" + nl
		+ "         initialContext.close();" + nl
		+ "      }" + nl
		+ "   }" + nl
		+ "" + nl
		+ "   // Home interface lookup methods" + nl
		+ "" + nl
		+ "   private static " + m.getBean() + "Home getHome() throws javax.naming.NamingException" + nl
		+ "   {" + nl
		+ "      if (cachedRemoteHome == null) {" + nl
		+ "            cachedRemoteHome = (" + m.getBean() + "Home) lookupHome(null, " + m.getBean() + "Home.COMP_NAME, " + m.getBean() + "Home.class);" + nl
		+ "      }" + nl
		+ "      return cachedRemoteHome;" + nl
		+ "   }" + nl
		+ "" + nl 
		+ "   public static Local" + m.getBean() + "Home getLocalHome() throws javax.naming.NamingException" + nl
		+ "   {" + nl
		+ "      if (cachedRemoteLocalHome == null) {" + nl
		+ "            cachedRemoteLocalHome = (Local" + m.getBean() + "Home) lookupHome(null, Local" + m.getBean() + "Home.COMP_NAME, " + m.getBean() + "Home.class);" + nl
		+ "      }" + nl
		+ "      return cachedRemoteLocalHome;" + nl
		+ "   }" + nl
		+ "" + nl + nl
		+ "	public static I" + m.getBean() + " get" + m.getBean() + "() throws Exception {" + nl
		+ "		try {" + nl
		+ "			" + m.getBean() + "Home ejbhome = getHome();" + nl);
		if (m.getBeanType().compareTo("Entity")==0) {
			sb.append( "			" + m.getBean() + " refejb = ejbhome.find();" + nl);
		} else {
			sb.append( "			" + m.getBean() + " refejb = ejbhome.create();" + nl);
		}
		sb.append( "			return refejb;" + nl
		+ "		} catch (NoInitialContextException nice) {" + nl
		+ "			return " + m.getBean() + "Bean.get2TierInstance();" + nl
		+ "		}" + nl
		+ "	}" + nl
		+ "" + nl
		+ "" + nl
		+ "}" + nl);
		return sb.toString();
	}
	
	
	public String getBeanSource(EjbLibraryModel m) {
		StringBuffer sb = new StringBuffer(1024);
		sb.append(MainGenerator.getLicense(m.getDescription()) + nl
		+ "//************ UNLESS YOU SET OVERWRITE IND = 0 IN TABLE EJBLIBRARY *********//" + nl
		+ "package " + m.getJavaPackage() + ";" + nl
		+ "" + nl
		+ "import javax.ejb.*;" + nl
		+ "import java.util.*;" + nl
		+ "import com." + MainGenerator.framework + ".framework.logging.*;" + nl
		+ "import com." + MainGenerator.framework + ".datalibrary.common.*;" + nl
		+ "import com." + MainGenerator.framework + ".modellibrary.common.*;" + nl
		+ "import com." + MainGenerator.framework + ".ejb.common.*;" + nl
		+ "import com." + MainGenerator.framework + ".modellibrary.reference.common.*;" + nl
		+ "import com." + MainGenerator.framework + ".modellibrary.reference.security.*;" + nl
		+ "import " + m.getJavaPackage().replaceAll(".ejb.",".datalibrary.") + ".*;" + nl);
		sb.append(maingen.getBusinessMethodList().getImportList(m.getBean()) + nl);
		sb.append( "" + nl + nl
		+ "public class " + m.getBeanName() + " extends BaseBean implements " + m.getBeanType() + "Bean, I" + m.getBean() + " {" + nl
		+ "" + nl
		+ "	private " + m.getBeanType() + "Context context= null;" + nl
		+ "	static final long serialVersionUID = 1L;" + nl
		+ "	private " + m.getDataAdapterName() + " da = new " + m.getDataAdapterName() + "();" + nl
		+ "" + nl);
		
		sb.append("	public " + m.getBeanName() + "() {" + nl);
		if (m.isEntity()) {
			sb.append("		try {" + nl
			+ "			Debug.LogDebug(this, \"Constructor\");" + nl
			+ "			loadCache();" + nl
			+ "		} catch (Exception ex) {" + nl
			+ "			Debug.LogException(this, ex);" + nl
			+ "		}");
		}
		
		sb.append(nl
		+ "	}" + nl
		+ "" + nl
		+ "" + nl
		+ "	public void set" + m.getBeanType() + "Context(" + m.getBeanType() + "Context context) {" + nl
		+ "		this.context = context;" + nl
		+ "	}" + nl + nl);
		
		if (m.getBeanType().compareTo("Entity")==0) {
			sb.append(
			"	public void loadCache() {" + nl + nl
			+ "	}" + nl + nl
			+ "	public String ejbFind() {" + nl
			+ "		//Debug.LogDebug(this, \"Find\");" + nl
			+ "		return \"ReferenceSearchBean\";" + nl
			+ "}" + nl + nl
			+ "	public void unsetEntityContext() {" + nl
			+ "		this.context = null;" + nl
			+ "}" + nl + nl
			+ "	public void ejbStore() {" +nl 
			+ "		//Debug.LogDebug(this, \"Store\");" + nl
			+ "	}" + nl + nl
			+ "	public void ejbLoad() {" +nl 
			+ "		Debug.LogDebug(this, \"Load\");" + nl
			+ "	}" + nl
			+ "	public String ejbCreate() {" + nl
			+ "		Debug.LogDebug(this, \"Create\");" + nl
			+ "		return \"" + m.getBeanName() + "\";" + nl
			+ "	}" + nl
			+ "	public void ejbPostCreate() {" + nl
			+ "		Debug.LogDebug(this, \"PostCreate\");" + nl
			+ "" + nl
			+ "	}" + nl
			+ "	public String ejbFindByPrimaryKey(String key) {" + nl
			+ "		Debug.LogDebug(this, \"FindByPrimaryKey\");" + nl
			+ "		return \"" + m.getBeanName() + "\";" + nl
			+ "	}" + nl);
		} else {
			sb.append("	public void ejbCreate() {" + nl
			+ "		" + nl
			+ "	}" + nl);
		}
		sb.append("" + nl
		+ "	public void ejbRemove() {" + nl
		+ "" + nl
		+ "	} " + nl
		+ "" + nl
		+ "	public void ejbActivate() {" + nl
		+ "" + nl
		+ "	}" + nl
		+ "" + nl
		+ "	public void ejbPassivate() {" + nl
		+ "" + nl
		+ "	}" + nl
		+ "" + nl);
		Enumeration e1 = maingen.getBusinessMethodList().elements();
		while (e1.hasMoreElements()) {
			BusinessMethodModel bmm = (BusinessMethodModel) e1.nextElement();
			if (m.getBean().compareTo(bmm.getBean()) == 0) {
				sb.append(getBeanMethod(bmm, m));
				clipboardNewMethods(bmm, m);
			}
		}
		sb.append("}" + nl);		
		return sb.toString();
	}
	
	private void clipboardNewMethods(BusinessMethodModel bmm, EjbLibraryModel ejb) {
		StringBuffer sb = new StringBuffer(1024);
		GregorianCalendar time15minsago = new GregorianCalendar();
		time15minsago.setTimeInMillis(Calendar.getInstance().getTimeInMillis());
		time15minsago.add(Calendar.MINUTE, -15);
		if (bmm.getCreated().after(time15minsago)) {
			sb.append(nl + "	//" + bmm.getBean() + "Bean" + nl);
			sb.append(getBeanMethod(bmm, ejb));
			sb.append(nl + "	//" + bmm.getBean() + "Service" + nl);
			sb.append(getServiceMethod(bmm, ejb));
			GUI.setClipboard(GUI.getClipboardText() + nl + sb.toString());
			
		}
	}
	
	private String getBeanMethod(BusinessMethodModel bmm, EjbLibraryModel ejb) {
		StringBuffer sb = new StringBuffer(1024);
		if (bmm.getSignature().toLowerCase().contains("update")) {
			sb.append("	/**  " + nl);
			sb.append("	 *  " + bmm.getComments() + nl);
			sb.append("	 */ " + nl);
			sb.append("	public " + bmm.getSignature() + " throws java.rmi.RemoteException" + bmm.getExceptionList() + " {" + nl);
			if (bmm.getSignature() != null &&  bmm.getReturnLine(maingen.getModelLibraryList(), ejb.getTableName())!=null) {
				sb.append("			" + bmm.getReturnLine(maingen.getModelLibraryList(), ejb.getTableName()) + nl);
			} else if (bmm.getSignature().startsWith("void")){
				sb.append("			return;" + nl);
			} else if (bmm.getSignature().startsWith("int")){
				sb.append("			return 0;" + nl);
			} else {
				sb.append("			return null;" + nl);
			}
			sb.append("	}" + nl + "" + nl);
			sb.append("	/**  " + nl);
			sb.append("	 *  " + bmm.getComments() + nl);
			sb.append("	 */ " + nl);
			sb.append("	public " + bmm.getSignature() + " throws java.rmi.RemoteException" + bmm.getExceptionList() + " {" + nl);
			sb.append("		BeanTransaction transaction = new BeanTransaction() {" + nl
					+ "			protected void execute(ServiceCall call, DbConnection conn) throws Exception {" + nl);
			if (bmm.getSignature() != null &&  bmm.getReturnLine(maingen.getModelLibraryList(), ejb.getTableName())!=null) {
				sb.append("				setIntReturn(" + bmm.getBeanParam() + ");" + nl);
			} else if (bmm.getSignature().startsWith("void")){
				sb.append("				setIntReturn();" + nl);
			} else if (bmm.getSignature().startsWith("int")){
				sb.append("				setIntReturn();" + nl);
			} else {
				sb.append("				setIntReturn();" + nl);
			}
			sb.append("			}" + nl
					+ "		};" + nl
					+ "		transaction.start(call);" + nl
					+ "		return transaction.getIntReturn();" + nl);
			sb.append("	}" + nl + "" + nl);
			return sb.toString();
		} else {
			sb.append("	/**  " + nl);
			sb.append("	 *  " + bmm.getComments() + nl);
			sb.append("	 */ " + nl);
			sb.append("	public " + bmm.getSignature() + " throws java.rmi.RemoteException" + bmm.getExceptionList() + " {" + nl);
			sb.append("		try {" + nl
					+ "			startService(call);" + nl);
			if (bmm.getSignature() != null &&  bmm.getReturnLine(maingen.getModelLibraryList(), ejb.getTableName())!=null) {
				sb.append("			" + bmm.getReturnLine(maingen.getModelLibraryList(), ejb.getTableName()) + nl);
			} else if (bmm.getSignature().startsWith("void")){
				sb.append("			return;" + nl);
			} else if (bmm.getSignature().startsWith("int")){
				sb.append("			return 0;" + nl);
			} else {
				sb.append("			return null;" + nl);
			}
			sb.append("			stopService(call);" + nl + "" + nl);
			sb.append("		} catch (Exception ex) {" + nl + "" + nl);
			sb.append("			Debug.LogException(this, ex);" + nl + "" + nl);
			sb.append("			throw ex;" + nl + "" + nl);
			sb.append("		}" + nl);
			sb.append("	}" + nl + "" + nl);
			
			return sb.toString();
		}
	}	


	private String getServiceMethod(BusinessMethodModel bmm, EjbLibraryModel ejb) {
		StringBuffer sb = new StringBuffer(1024);
		sb.append("	/**  " + nl);
		sb.append("	 *  " + bmm.getComments() + nl);
		sb.append("	 */ " + nl);
		sb.append("	public static " + bmm.getSignature().replace("ServiceCall call","") + " {" + nl);
		sb.append("		try { " + nl
				+ "			//get" + ejb.getBeanName() + "Ref()." + bmm.getSignature().replace("ServiceCall call","getServiceCall()") + ";" + nl);
		if (bmm.getSignature() != null &&  bmm.getReturnLine(maingen.getModelLibraryList(), ejb.getTableName())!=null) {
			sb.append("			" + bmm.getReturnLine(maingen.getModelLibraryList(), ejb.getTableName()) + nl);
		} else if (bmm.getSignature().startsWith("void")){
			sb.append("			return;" + nl);
		} else if (bmm.getSignature().startsWith("int")){
			sb.append("			return 0;" + nl);
		} else {
			sb.append("			return null;" + nl);
		}
		sb.append("		} catch (Exception e) { " + nl
				+ "			Debug.LogException(\"" + bmm.getBean() + "Service\", e);" + nl);
		if (bmm.getSignature() != null &&  bmm.getReturnLine(maingen.getModelLibraryList(), ejb.getTableName())!=null) {
			sb.append("			" + bmm.getReturnLine(maingen.getModelLibraryList(), ejb.getTableName()) + nl);
		} else if (bmm.getSignature().startsWith("void")){
			sb.append("			return;" + nl);
		} else if (bmm.getSignature().startsWith("int")){
			sb.append("			return 0;" + nl);
		} else {
			sb.append("			return null;" + nl);
		}
				sb.append("		}" + nl);
		sb.append("	}" + nl + "" + nl);
		return sb.toString();
	}

	public String getRemoteSource(EjbLibraryModel m) {
		StringBuffer sb = new StringBuffer(1024);
		sb.append(MainGenerator.getLicense(m.getDescription()) + nl
		+ "package " + m.getJavaPackage() + ";" + nl + nl
		+ "import com." + MainGenerator.framework + ".datalibrary.common.*;" + nl
		+ "import com." + MainGenerator.framework + ".modellibrary.common.*;" + nl
		+ "import com." + MainGenerator.framework + ".modellibrary.reference.security.*;" + nl
		+ "import com." + MainGenerator.framework + ".appclient.ui.tree.*;" + nl
		+ "import java.util.*;" + nl
		+ "import javax.swing.tree.*;" + nl
		+ "import com." + MainGenerator.framework + ".modellibrary.reference.group.*;" + nl
		);
		if (m.getJavaPackage().contains(MainGenerator.root)) {
			sb.append("import com." + MainGenerator.root + ".modellibrary.reference.group.*;" + nl);
		}
		sb.append(maingen.getBusinessMethodList().getImportList(m.getBean()) + nl);
		sb.append( "public interface " + m.getBean() + " extends javax.ejb.EJBObject, I" + m.getBean() + nl
		+ "{" + nl);
		Enumeration e1 = maingen.getBusinessMethodList().elements();
		while (e1.hasMoreElements()) {
			BusinessMethodModel bmm = (BusinessMethodModel) e1.nextElement();
			if (m.getBean().compareTo(bmm.getBean()) == 0) {
				sb.append("    /**  " + nl);
				sb.append("     *  " + bmm.getComments() + nl);
				sb.append("     */ " + nl);
				sb.append("    public " + bmm.getSignature() + " throws java.rmi.RemoteException" + bmm.getExceptionList() + ";" + nl);
				sb.append(nl + "" + nl);
			}
		}
		sb.append( "}" + nl);
	
		return sb.toString();
	}

	
}
