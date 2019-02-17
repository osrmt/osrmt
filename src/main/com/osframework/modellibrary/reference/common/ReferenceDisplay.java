package com.osframework.modellibrary.reference.common;

import com.osframework.ejb.reference.common.IReferenceMap;
import com.osframework.ejb.reference.security.ISecurity;
import com.osframework.modellibrary.common.IControlModel;

/**
 * ReferenceDisplay is a lighter version of ReferenceModel
 * with just the unique id and display.
 *
 */
public class ReferenceDisplay implements java.io.Serializable, IControlModel {
	static final long serialVersionUID = 1L;
	
	private int refId = 0;
	private String display = "";
	
	public String getDisplay() {
		return display;
	}
	public void setDisplay(String display) {
		this.display = display;
	}
	public int getRefId() {
		return refId;
	}
	public void setRefId(int refId) {
		this.refId = refId;
	}
	public String toString() {
		return display;
	}
	public Object getPrimaryValue() {
		return new Integer(getRefId());
	}
	
	public Object getModelColDataAt(int modelColRefId) {
		return null;
	}	
	public int getModelColDatabaseDataType(int modelColRefId) {
		return 0;
	}
	public void setModelColDataAt(Object value, int modelColRefId) {
	}
	public boolean isNew() {
		return false;
	}
	
	public void setReferenceDisplay(IReferenceMap reference, ISecurity security) {
	}
}
