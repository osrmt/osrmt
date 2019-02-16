package generator;

import java.util.Enumeration;

import models.EjbLibraryModel;
import utilities.FileSystemUtil;

public class GenerateConfiguration {
	private MainGenerator maingen = null;
	private String nl = "\r\n";
	
	public GenerateConfiguration(MainGenerator m) {
		this.maingen = m;
	}
	
	public void CreateFiles() throws java.io.IOException {
		
		String genSourceDir = maingen.props.getProperty(MainGenerator.propCONFIGDIR);
		// each model library has a package of format example
		// com." + MainGenerator.root + ".modellibrary.registration.patient
		// each . will equate to a directory
		String ejbXmlSource = getEjbXml();
		String ejbPath = genSourceDir + "\\dd\\ejb";
		FileSystemUtil.CreateDirectory(ejbPath);
		FileSystemUtil.CreateFile(ejbPath, "ejb-jar.xml", ejbXmlSource);
		
		String jbossXmlSource = getJbossXml();
		String jbossPath = genSourceDir + "\\dd\\ejb";
		FileSystemUtil.CreateDirectory(jbossPath);
		FileSystemUtil.CreateFile(ejbPath, "jboss.xml", jbossXmlSource);
		
		String clientXmlSource = getClientXml();
		String clientPath = genSourceDir + "\\dd\\client";
		FileSystemUtil.CreateDirectory(clientPath);
		FileSystemUtil.CreateFile(clientPath, "application-client.xml", clientXmlSource);
		
		String jbossClientXmlSource = getJbossClientXml();
		String jbossClientPath = genSourceDir + "\\dd\\client";
		FileSystemUtil.CreateDirectory(jbossClientPath);
		FileSystemUtil.CreateFile(clientPath, "jboss-client.xml", jbossClientXmlSource);
		
	}
	
	public String getJbossClientXml() {

		StringBuffer sb = new StringBuffer(2*1024);
		sb.append("<jboss-client>" + nl
		+ "   <jndi-name>" + MainGenerator.root + "-client</jndi-name>" + nl
		+ "" + nl);
		
		Enumeration e1 = maingen.getEjbLibraryList().elements();
		while (e1.hasMoreElements()) {
			// Ejb
			EjbLibraryModel ejb = (EjbLibraryModel) e1.nextElement();
			sb.append(
			  "   <ejb-ref>" + nl
			+ "      <ejb-ref-name>ejb/" + ejb.getBean() + "</ejb-ref-name>" + nl
			+ "      <jndi-name>" + ejb.getBean() + "Name</jndi-name>" + nl
			+ "   </ejb-ref>" + nl
			+ "" + nl);
		}
		sb.append("</jboss-client>" + nl);
		return sb.toString();

	}
	public String getClientXml() {
		
		StringBuffer sb = new StringBuffer(4*1024);
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + nl
		+ "" + nl
		+ "<!DOCTYPE application-client PUBLIC " + nl
		+ "          '-//Sun Microsystems, Inc.//DTD J2EE Application Client 1.3//EN' " + nl
		+ "          'http://java.sun.com/dtd/application-client_1_3.dtd'>" + nl
		+ "<application-client>" + nl
		+ "    <display-name>" + MainGenerator.root + "Admin</display-name>" + nl);
		
		Enumeration e1 = maingen.getEjbLibraryList().elements();
		while (e1.hasMoreElements()) {
			// Ejb
			EjbLibraryModel ejb = (EjbLibraryModel) e1.nextElement();
			sb.append(
			  "    <ejb-ref>" + nl
			+ "        <ejb-ref-name>ejb/" + ejb.getBean() + "</ejb-ref-name>" + nl
			+ "        <ejb-ref-type>Session</ejb-ref-type>" + nl
			+ "        <home>" + ejb.getJavaPackage() + "." + ejb.getBean() + "Home</home>" + nl
			+ "        <remote>" + ejb.getJavaPackage() + "." + ejb.getBean() + "</remote>" + nl
			+ "    </ejb-ref>" + nl);
		}
		
		sb.append("</application-client>" + nl);
		return sb.toString();
	}
	public String getJbossXml() {
		StringBuffer sb = new StringBuffer(4*1024);
		sb.append("<!DOCTYPE jboss PUBLIC" + nl
		+ "          \"-//JBoss//DTD JBOSS 4.0//EN\"" + nl
		+ "          \"http://www.jboss.org/j2ee/dtd/jboss_4_0.dtd\">" + nl
		+ "<jboss>" + nl
		+ "    <security-domain>java:/jaas/" + MainGenerator.root + "</security-domain>" + nl
		+ "    <enterprise-beans>" + nl);
		
		Enumeration e1 = maingen.getEjbLibraryList().elements();
		while (e1.hasMoreElements()) {
			// Ejb
			EjbLibraryModel ejb = (EjbLibraryModel) e1.nextElement();
			sb.append("        <" + ejb.getBeanType().toLowerCase() + ">" + nl
			+ "            <ejb-name>" + ejb.getBeanName() + "</ejb-name>" + nl
			+ "            <jndi-name>" + ejb.getBean() + "Name</jndi-name>" + nl
			+ "            <local-jndi-name>Local" + ejb.getBean() + "Name</local-jndi-name>" + nl
			+ "         </" + ejb.getBeanType().toLowerCase() + ">" + nl);			
		}
		sb.append("    </enterprise-beans>" + nl
		+ "</jboss>" + nl);
		return sb.toString();
	}
	
	public String getEjbXml() {
		StringBuffer sb = new StringBuffer(16*1024);
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + nl
		+ "<ejb-jar xmlns=\"http://java.sun.com/xml/ns/j2ee\" version=\"2.1\"" + nl
		+ "    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"" + nl
		+ "    xsi:schemaLocation=\"http://java.sun.com/xml/ns/j2ee http://java.sun.com/xml/ns/j2ee/ejb-jar_2_1.xsd\">" + nl
		+ "    <display-name>" + MainGenerator.root.toUpperCase() + "</display-name>" + nl
		+ "    <enterprise-beans>" + nl);
		
		Enumeration e1 = maingen.getEjbLibraryList().elements();
		while (e1.hasMoreElements()) {
			// Ejb
			EjbLibraryModel ejb = (EjbLibraryModel) e1.nextElement();

			sb.append( "        <" + ejb.getBeanType().toLowerCase() + ">" + nl
			+ "            <ejb-name>" + ejb.getBeanName() + "</ejb-name>" + nl
			+ "            <home>" + ejb.getJavaPackage() + "." + ejb.getBean() +"Home</home>" + nl
			+ "            <remote>" + ejb.getJavaPackage() + "." + ejb.getBean() + "</remote>" + nl
			+ "            <ejb-class>" + ejb.getJavaPackage() + "." + ejb.getBeanName() + "</ejb-class>" + nl);
			if (ejb.isSession()) {
				sb.append("            <session-type>Stateful</session-type>" + nl);
			} else {
				sb.append("            <persistence-type>Bean</persistence-type>" + nl);
				sb.append("            <prim-key-class>java.lang.String</prim-key-class>" + nl);
				sb.append("            <reentrant>false</reentrant>" + nl);
			}
			sb.append("            <transaction-type>Container</transaction-type>" + nl
			+ "            <ejb-local-ref>" + nl
			+ "                <ejb-ref-name>ejb/" + ejb.getBean() + "</ejb-ref-name>" + nl
			+ "                <ejb-ref-type>Entity</ejb-ref-type>" + nl
			+ "                <local-home>" + ejb.getJavaPackage() + ".Local" + ejb.getBean() + "Home</local-home>" + nl
			+ "                <local>" + ejb.getJavaPackage() + ".Local" + ejb.getBean() + "</local>" + nl
			+ "                <ejb-link>Local" + ejb.getBeanName() + "</ejb-link>" + nl
			+ "            </ejb-local-ref>" + nl
			+ "            <resource-ref>" + nl
			+ "                <res-ref-name>jdbc/" + MainGenerator.root + "DB</res-ref-name>" + nl
			+ "                <res-type>javax.sql.DataSource</res-type>" + nl
			+ "                <res-auth>Container</res-auth>" + nl
			+ "                <res-sharing-scope>Shareable</res-sharing-scope>" + nl
			+ "            </resource-ref>" + nl
			+ "            <security-identity>" + nl
			+ "                <use-caller-identity/>" + nl
			+ "            </security-identity>" + nl
			+ "        </" + ejb.getBeanType().toLowerCase() + ">" + nl);
			}
		
		sb.append("    </enterprise-beans>" + nl
		+ "    <assembly-descriptor>" + nl
		+ "        <security-role>" + nl
		+ "            <role-name>" + MainGenerator.root + "Admin</role-name>" + nl
		+ "        </security-role>" + nl);
		
		Enumeration e2 = maingen.getEjbLibraryList().elements();
		while (e2.hasMoreElements()) {
			// Ejb
			EjbLibraryModel ejb = (EjbLibraryModel) e2.nextElement();

			sb.append(
			  "        <method-permission>" + nl
			+ "            <role-name>" + MainGenerator.root + "Admin</role-name>" + nl
			+ "            <method>" + nl
			+ "                <ejb-name>" + ejb.getBeanName() + "</ejb-name>" + nl
			+ "                <method-name>*</method-name>" + nl
			+ "            </method>" + nl
			+ "        </method-permission>" + nl
			+ "        <container-transaction>" + nl
			+ "            <method>" + nl
			+ "                <ejb-name>" + ejb.getBeanName() + "</ejb-name>" + nl
			+ "                <method-name>*</method-name>" + nl
			+ "            </method>" + nl);
			if (ejb.getBeanType().compareTo("Entity")==0) {
				sb.append("            <trans-attribute>Required</trans-attribute>" + nl);
			} else {
				sb.append("            <trans-attribute>NotSupported</trans-attribute>" + nl);
			}
			sb.append("	</container-transaction>" + nl);
		}
		sb.append(
		  "    </assembly-descriptor>" + nl
		+ "</ejb-jar>");

		return sb.toString();
		

	}

}
