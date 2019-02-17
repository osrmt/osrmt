package models;

import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

public class BusinessMethodModel {
	
	private String signature;
	private String bean;
	private String version;
	private String comments;
	private String modelPackage;
	private String exceptionList;
	private GregorianCalendar created;
	
	public String getExceptionList() {
		if (exceptionList == null) {
			return "";
		} else {
			return ", " + exceptionList;
		}
	}

	public void setExceptionList(String exceptionList) {
		this.exceptionList = exceptionList;
	}
	
	public String getModelPackage() {
		return modelPackage;
	}
	public void setModelPackage(String modePackage) {
		this.modelPackage = modePackage;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getSignature() {
		return signature;
	}
	public String getBeanParam() {
		StringTokenizer st = new StringTokenizer(signature," ");
		String s = "";
		int i=0;
		while (st.hasMoreTokens()) {
			if (i%2==1) {
				s += st.nextToken().toString();
			} else {
				st.nextToken();
			}
			i++;
		}
		return s;
	}
	public void setSignature(String signature) {
		this.signature = signature;
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
	
	/*
	 * Convert the signature format from 
	 * int UpdatePatient(PatientModel m)
	 * to 
	 * return da.UpdatePatient(m);
	 */
	public String getReturnLine(ModelLibraryList list, String tableName) {
		String s = getSignature();
		if (s.indexOf(" ")> 0 && s.length() > s.indexOf(" ")+1) {
			// now we have UpdatePatient(PatientModel m)
			s = s.substring(s.indexOf(" ")+1);
			if (s.indexOf("Update")==0 && s.indexOf("(") > "Update".length()+1) {
				java.util.Enumeration e1 = list.elements();
				String parentSoModel = "";
				while (e1.hasMoreElements()) {
					ModelLibraryModel mlm = (ModelLibraryModel) e1.nextElement();
					if (tableName != null && mlm.getTableName().compareTo(tableName)==0) {
						if (mlm.isParent(list)) {
							parentSoModel = "Model";
						}
					}
				}
				return "return da.Update" + this.bean.replaceAll("Update",parentSoModel + "(m);"); 
			}
		}
		return null;
	}
	
	public String toString() {
		return "BusinessMethodModel:" + signature
		+ "," + bean;
	}

	public GregorianCalendar getCreated() {
		return created;
	}

	public void setCreated(GregorianCalendar created) {
		this.created = created;
	}
	
	public void setCreated(Timestamp created) {
		this.created = new GregorianCalendar();
		this.created.setTimeInMillis(created.getTime());
	}
	
	
}
