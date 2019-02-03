package models;

import java.util.*;
import java.sql.*;
import database.*;

public class BusinessMethodList implements  Enumeration {

	private Vector list = new Vector(1000);
	private Enumeration enumeration;
	
	public Enumeration elements() {
		enumeration = list.elements();
		return enumeration;
	}
	
	public Object nextElement() {
		return enumeration.nextElement();
	}
	
	public boolean hasMoreElements() {
		return enumeration.hasMoreElements();
	}
	
	public void add(BusinessMethodModel bmm) {
		this.list.add(bmm);
	}
	
	public String getImportList(String bean) {
		Enumeration e1 = this.elements();
		Hashtable importList = new Hashtable();
		StringBuffer sb = new StringBuffer(256);
		while (e1.hasMoreElements()) {
			BusinessMethodModel bmm = (BusinessMethodModel) e1.nextElement();
			if (bean.compareTo(bmm.getBean())==0 
					&& bmm.getModelPackage() != null
					&& !importList.containsKey(bmm.getModelPackage())) {
				importList.put(bmm.getModelPackage(),"");
				sb.append("import " + bmm.getModelPackage() + ".*;\r\n");
			}
		}
		return sb.toString();
	}
	
	public void load(Access access) throws SQLException, ClassNotFoundException {
		String sql = "select signature, bean, comments, modelPackage, exceptionList,"
			+ " created, version from BusinessMethods";
		ResultSet rset = access.executeQuery(sql);
		while (rset.next()) {
			BusinessMethodModel mlm = new BusinessMethodModel();
			mlm.setSignature(rset.getString("signature"));
			mlm.setBean(rset.getString("bean"));
			mlm.setComments(rset.getString("comments"));
			mlm.setModelPackage(rset.getString("modelPackage"));
			if (rset.wasNull()) mlm.setModelPackage(null);
			mlm.setCreated(rset.getTimestamp("created")); 
			mlm.setVersion(rset.getString("version"));
			if (rset.wasNull()) mlm.setVersion(null);
			mlm.setExceptionList(rset.getString("exceptionList"));
			if (rset.wasNull()) mlm.setExceptionList(null);
			this.add(mlm);			
		}
		rset.close();
	}

}
