package com.osframework.modellibrary.common;

import java.util.*;

import com.osframework.ejb.reference.common.IReferenceMap;
import com.osframework.ejb.reference.security.ISecurity;

public interface IControlModel {
	
	public abstract void setReferenceDisplay(IReferenceMap reference, ISecurity security);
	
	/**
	 * Returns true if this model has not been saved to the database.
	 * Typically the primary key value is 0
	 * @return
	 */
	public boolean isNew(); 

	/**
	 * Returns the value of the model field identified by the id
	 * 
	 * @param id Originally the position in the model future a reference id
	 * @return
	 */
	public Object getModelColDataAt(int modelColRefId);
	
	/**
	 * Sets the field identified by the id to the value
	 * 
	 * @param id Originally the position in the model future a reference id
	 * @return
	 */
	public void setModelColDataAt(Object value, int modelColRefId);
	
	/**
	 * Returns the database type ref id for the field identified by id
	 * 
	 * @param id Originally the position in the model future a reference id
	 * @return
	 */
	public int getModelColDatabaseDataType(int modelColRefId);
	
	/**
	 * Returns the primary key value or another value which is used to save 
	 * parameters etc.
	 * 
	 * @return
	 */
	public Object getPrimaryValue();
	
}
