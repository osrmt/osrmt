//************ AUTO GENERATED DO NOT EDIT *********//
package com.osframework.modellibrary.reference.common;

import com.osframework.ejb.reference.common.IReferenceMap;
import com.osframework.ejb.reference.security.ISecurity;
import com.osframework.modellibrary.common.ResultColumnList;
import com.osframework.modellibrary.reference.group.DatabaseDataTypeFramework;
import com.osframework.modellibrary.reference.group.ModelColumnFramework;


public class ReferenceTreeModel extends ReferenceTreeDataModel implements Comparable {

	static final long serialVersionUID = 1L;
	
	private String parentTableKeyDisplay = "abc";
	private String tableKeyDisplay = "def";
	
	public ReferenceTreeModel() {

	}
	
	public int compareTo(Object arg0) {
		return 0;
	}
	
	

	public static ResultColumnList getResultColumnList() {
		ResultColumnList columns = ReferenceTreeDataModel.getResultColumnList();
		
			columns.addColumn("parentTableKeyDisplay", String.class);
			columns.addColumn("tableKeyDisplay", String.class);
			return columns;
		}
	
	@Override
	public Object getModelColDataAt(int modelCol) {
		if (modelCol == ModelColumnFramework.REFERENCETREETABLEKEYDISPLAY) {
			return tableKeyDisplay;
		} else if (modelCol == ModelColumnFramework.REFERENCETREEPARENTTABLEKEYDISPLAY) {
			return parentTableKeyDisplay;
		} else {
			return super.getModelColDataAt(modelCol);
		}
	}
	
	@Override
	public Object getDataAt(int i) {
		if (i == ReferenceTreeDataModel.getResultColumnList().getColumnCount()-2) return parentTableKeyDisplay;
		if (i == ReferenceTreeDataModel.getResultColumnList().getColumnCount()-1) return tableKeyDisplay;
		return super.getDataAt(i);
	}
	
	@Override
	public int getModelColDatabaseDataType(int modelCol) {
		if (modelCol == ModelColumnFramework.REFERENCETREETABLEKEYDISPLAY) {
			return DatabaseDataTypeFramework.STRING;
		} else if (modelCol == ModelColumnFramework.REFERENCETREEPARENTTABLEKEYDISPLAY) {
			return DatabaseDataTypeFramework.STRING;
		} else {
			return super.getDatabaseDataType(modelCol);
		}
	}
	
	@Override
	public void setReferenceDisplay(IReferenceMap reference, ISecurity security) {
		super.setReferenceDisplay(reference, security);
		try {
			tableKeyDisplay = reference.getDisplay(super.getTableKeyId());
			parentTableKeyDisplay = reference.getDisplay(super.getParentTableKeyId());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}