/*
    //usage

    Copyright (C) 2006  Aron Lancout Smith

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA

*/
package com.osframework.modellibrary.system;

import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Vector;

import com.osframework.framework.logging.Debug;
import com.osframework.modellibrary.common.DatabaseDataType;
import com.osframework.modellibrary.common.IControlModel;
import com.osframework.modellibrary.reference.group.DatabaseDataTypeFramework;


/**
null
*/
public class RecordParameterValueModel extends RecordParameterValueDataModel implements Comparable{

	private static final long serialVersionUID = 1L;
	public RecordParameterValueModel() {

	}
	public void setValueInt(int valueInt) {
		super.setValueInt(valueInt);
		super.setDataTypeRefId(DatabaseDataTypeFramework.INTEGER);
	}
	
	public void setValueDouble(double valueDouble) {
		super.setValueDouble(valueDouble);
		super.setDataTypeRefId(DatabaseDataTypeFramework.DOUBLE);
	}	
	
	public void setValueDate(long milliseconds) {
		super.setValueDate(milliseconds);
		super.setDataTypeRefId(DatabaseDataTypeFramework.DATE);
	}
	
	public void setValueString(String valueString) {
		super.setValueString(valueString);
		super.setDataTypeRefId(DatabaseDataTypeFramework.STRING);
	}	

	public void setValueDate(GregorianCalendar valueDate) {
		super.setValueDate(valueDate);
		super.setDataTypeRefId(DatabaseDataTypeFramework.DATE);
	}
	
	public Object getValue() {
		switch (getDataTypeRefId()) {
			case DatabaseDataType.DateType: return getValueDate();
			case DatabaseDataType.DoubleType: return new Double(getValueDouble());
			case DatabaseDataType.IntegerType: return new Integer(getValueInt());
			case DatabaseDataType.StringType: return getValueString();
			default: Debug.LogError(this,"DataType not accounted for: " + getDataTypeRefId());
		}
		return null;
	}

	
	public void setValue(Object o) {
		//Keep this in sync with validValue below
		if (o instanceof Integer) {
			setValueInt(((Integer) o).intValue());
		} else if (o instanceof Double) {
			setValueDouble(((Double) o).doubleValue());
		} else if (o instanceof GregorianCalendar) {
			setValueDate((GregorianCalendar) o);
		} else if (o instanceof String) {
			setValueString((String) o);
		} else if (o instanceof IControlModel) {
			setValue(((IControlModel) o).getPrimaryValue());
		} else {
			Debug.LogError("RecordParameterValueModel", "Object data type not found: " + o.getClass());
			setValueString((String) o);
		}
	}
	
	public static boolean validValue(Object o) {
		if (o == null) {
			return true;
		} else if (o instanceof Integer) {
			return true;
		} else if (o instanceof Double) {
			return true;
		} else if (o instanceof GregorianCalendar) {
			return true;
		} else if (o instanceof String) {
			return true;
		} else if (o instanceof IControlModel) {
			return true;
		} else {
			return false;
		}
	}

	public int compareTo(Object arg0) {
		return 0;
	}
}