/**
 * 
 */
package com.osrmt.modellibrary.reports;

import java.util.*;
import java.io.Serializable;
import com.osframework.ejb.reference.common.IReferenceMap;
import com.osframework.ejb.reference.security.ISecurity;
import com.osframework.modellibrary.common.*;
import com.osframework.modellibrary.reference.common.*;
import com.osframework.framework.logging.*;

import com.osrmt.modellibrary.reqmanager.*;
import com.osrmt.modellibrary.issue.*;
/**
 *
 */
public class ReportParameterModel implements IControlModel, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Map<String,Object> params = new HashMap<String,Object>();
	private Map<Integer, String> idToName = new HashMap<Integer, String>();

	public Object getModelColDataAt(int modelColRefId) {
		return null;
	}

	public int getModelColDatabaseDataType(int modelColRefId) {
		return 0;
	}

	public Object getPrimaryValue() {
		return null;
	}

	public boolean isNew() {
		return false;
	}

	public void setModelColDataAt(Object value, int modelColRefId) {
		if (modelColRefId > 0 && idToName.containsKey(modelColRefId)) {
			if (value == null) {
				addParameter(idToName.get(modelColRefId), null);
			} else {
				if (value instanceof Collection) {
					StringBuilder sb = new StringBuilder(64);
					Vector list = new Vector();
					boolean first = true;
					for (Object v : ((Collection)value)) {
						if (!first) {
							sb.append(",");
						}
						sb.append(v);
						first = false;
					}					
					addParameter(idToName.get(modelColRefId), sb.toString());
					//System.out.println(idToName.get(modelColRefId) + "=" + sb.toString());
				} else {
					addParameter(idToName.get(modelColRefId), value);
					//System.out.println(idToName.get(modelColRefId) + "=" + value);
				}
			}
		}
	}

	public void setReferenceDisplay(IReferenceMap reference, ISecurity security) {
		
	}

	public void addParameter(String parameterName, Object parameterValue) {
		this.params.put(parameterName, parameterValue);
	}

	public void addIdToName(Integer modelColRefId, String parameterName) {
		this.idToName.put(modelColRefId, parameterName);
	}

	public Map getParams() {
		return params;
	}

	public void addParameter(ArtifactList artifacts) {
		StringBuffer sb = new StringBuffer(128);
		Enumeration e1 = artifacts.elements();
		boolean first = true;
		while (e1.hasMoreElements()) {
			ArtifactModel am = (ArtifactModel) e1.nextElement();
			if (!first) {
				sb.append(",");
			}
			sb.append(am.getArtifactId());
			first = false;
		}
		this.params.put("artifacts", sb.toString());
	}
	
	public void addParameter(IssueList Issues) {
		StringBuffer sb = new StringBuffer(128);
		Enumeration e1 = Issues.elements();
		boolean first = true;
		while (e1.hasMoreElements()) {
			IssueModel am = (IssueModel) e1.nextElement();
			if (!first) {
				sb.append(",");
			}
			sb.append(am.getIssueId());
			first = false;
		}
		this.params.put("Issues", sb.toString());
	}
	
}
