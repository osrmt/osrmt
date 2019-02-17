package com.osframework.modellibrary.system;

import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Vector;

import com.osframework.framework.logging.Debug;
import com.osframework.modellibrary.common.IControlModel;
import com.osframework.modellibrary.reference.group.DatabaseDataTypeFramework;

public class RecordParameterControlList extends RecordParameterList implements
		IControlModel {

	/**
	 * Always fill out parameters with their default values
	 */
	public boolean isNew() {
		return true;
	}

	/**
	 * Returns the RecordParameterValueList
	 */
	public Object getModelColDataAt(int modelColRefId) {
		Enumeration e1 = super.elements();		
		while (e1.hasMoreElements()) {
			RecordParameterModel m = (RecordParameterModel) e1.nextElement();
			if (m.getModelColumnRefId() == modelColRefId) {
				return m.getRecordParameterValueList();
			}
		}
		Debug.LogError(this,"Model column ref id " + modelColRefId + " not found");
		return null;
	}

	/**
	 * This removes the old values and creates a new
	 * The only valid types are for primitives e.g. Integer, String, GregorianCalendar, Double
	 * Alternatively a Vector of those objects is allowed
	 * 
	 */
	public void setDataAt(Object value, int i) {
		RecordParameterModel m = elementAt(i);
		// convert value to RecordParameterValue(s)
		RecordParameterValueList values = getRecordParameterValueList(value);
		// deactivate the original list of values
		Enumeration e1 = m.getRecordParameterValueList();
		while (e1.hasMoreElements()) {
			RecordParameterValueModel rpm = (RecordParameterValueModel) e1.nextElement();
			rpm.setNotActive();
		}
		// replace with a new list of values
		Enumeration e2 = values.elements();
		while (e2.hasMoreElements()) {
			RecordParameterValueModel rpm = (RecordParameterValueModel) e2.nextElement();
			m.getRecordParameterValueList().add(rpm);
		}
		
	}

	/**
	 * This removes the old values and creates a new
	 * The only valid types are for primitives e.g. Integer, String, GregorianCalendar, Double
	 * Alternatively a Vector of those objects is allowed
	 * 
	 */
	public void setModelColDataAt(Object value, int modelRefId) {
		Enumeration e1 = super.elements();		
		while (e1.hasMoreElements()) {
			RecordParameterModel m = (RecordParameterModel) e1.nextElement();
			if (m.getModelColumnRefId() == modelRefId) {
				// convert value to RecordParameterValue(s)
				RecordParameterValueList values = getRecordParameterValueList(value);
				m.setRecordParameterValueList(values);
			}
		}
	}

	/**
	 * Do not use
	 */
	public int getDatabaseDataType(int i) {
		RecordParameterModel m = getFirst();
		if (m != null) {
			return m.getDataTypeRefId();
		} else {
			return 0;
		}
	}
	
	/**
	 * Do not use
	 */
	public int getModelColDatabaseDataType(int modelRefId) {
		RecordParameterModel m = getFirst();
		if (m != null) {
			return m.getDataTypeRefId();
		} else {
			return 0;
		}
	}
	
	/**
	 * Converts a vector full of primitive objects or a single primitive object
	 * to a list
	 * 
	 * @param o
	 * @return
	 */
	private RecordParameterValueList getRecordParameterValueList(Object o) {
		RecordParameterValueList list = new RecordParameterValueList();
		RecordParameterValueModel m = new RecordParameterValueModel();
		if (o instanceof Vector) {
			Enumeration e1 = ((Vector) o).elements();
			while (e1.hasMoreElements()) {
				RecordParameterValueModel rpvm = new RecordParameterValueModel();
				rpvm.setValue(e1.nextElement());
				list.add(rpvm);
			}
		} else {
			m.setValue(o);
			list.add(m);
		}
		return list;
	}
	
	/**
	 * This is a list of values so not applicable
	 */
	public Object getPrimaryValue() {
		return null;
	}
	
	/**
	 * @return  value,value,... or 'value','value',...
	 */
	public String getValuesSqlIn(String parameterName, boolean leadWithComma) {
		Enumeration e1 = super.elements();
		String sqlin = "";
		boolean first = true;
		while (e1.hasMoreElements()) {
			RecordParameterModel p = (RecordParameterModel) e1.nextElement();
			if (p.getParameterName().compareToIgnoreCase(parameterName)==0
					&& p.isActive()) {
				Enumeration e2 = p.getRecordParameterValueList().elements();
				while (e2.hasMoreElements()) {
					RecordParameterValueModel m = (RecordParameterValueModel) e2.nextElement();
					if (m.isActive()) {
						if (!first || leadWithComma) {
							sqlin += ",";
						}
						switch (m.getDataTypeRefId()) {
						case DatabaseDataTypeFramework.INTEGER: sqlin += m.getValueInt(); break;
						case DatabaseDataTypeFramework.DOUBLE: sqlin += m.getValueDouble(); break;
						case DatabaseDataTypeFramework.DATE: sqlin += m.getValueDate(); break;
						case DatabaseDataTypeFramework.STRING: sqlin += "'" + m.getValueString() + "'"; break;
						}
						first = false;
					}
				}
			}
		}
		return sqlin;
	}
	
	public boolean hasValues() {
		Enumeration e1 = super.elements();
		while (e1.hasMoreElements()) {
			RecordParameterModel m = (RecordParameterModel) e1.nextElement();
			if (m.getRecordParameterValueList().size() > 0) {
				return true;
			}
		}
		return false;
	}

}
