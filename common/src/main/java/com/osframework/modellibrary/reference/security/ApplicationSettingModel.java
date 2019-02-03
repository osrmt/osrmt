//************ AUTO GENERATED DO NOT EDIT *********//
package com.osframework.modellibrary.reference.security;

import java.util.GregorianCalendar;

import com.osframework.framework.logging.Debug;
import com.osframework.framework.utility.CalendarUtility;
import com.osframework.modellibrary.common.DatabaseDataType;
import com.osframework.modellibrary.common.IControlModel;


public class ApplicationSettingModel extends ApplicationSettingDataModel implements Comparable {

	static final long serialVersionUID = 1L;
	public ApplicationSettingModel() {

	}
	
	public void setValueInt(int valueInt) {
		super.setValueInt(valueInt);
	}
	
	public void setValueDouble(double valueDouble) {
		super.setValueDouble(valueDouble);
	}	
	
	public void setValueDate(long milliseconds) {
		super.setValueDate(milliseconds);
	}
	
	public void setValueString(String valueString) {
		super.setValueString(valueString);
	}	

	public void setValueDate(GregorianCalendar valueDate) {
		super.setValueDate(valueDate);
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
		try {
			if (o == null) {
				switch (getDataTypeRefId()) {
				case DatabaseDataType.DateType: setValueDate(null); break;
				case DatabaseDataType.DoubleType: setValueDouble(0); break;
				case DatabaseDataType.IntegerType: setValueInt(0); break;
				case DatabaseDataType.StringType: setValueString(null); break;
				default: Debug.LogError(this,"DataType not accounted for: " + getDataTypeRefId());
				}
				return;
			}
			//Keep this in sync with validValue below
			if (o instanceof Integer && getDataTypeRefId()==DatabaseDataType.IntegerType) {
				setValueInt(((Integer) o).intValue());
			} else if (o instanceof Double && getDataTypeRefId()==DatabaseDataType.DoubleType) {
				setValueDouble(((Double) o).doubleValue());
			} else if (o instanceof GregorianCalendar && getDataTypeRefId()==DatabaseDataType.DateType) {
				setValueDate((GregorianCalendar) o);
			} else if (o instanceof String) {
				
				switch (getDataTypeRefId()) {
				case DatabaseDataType.DateType: setValueDate(CalendarUtility.Parse(o.toString())); break;
				case DatabaseDataType.DoubleType: setValueDouble(Double.parseDouble(o.toString())); break;
				case DatabaseDataType.IntegerType: setValueInt(Integer.parseInt(o.toString())); break;
				case DatabaseDataType.StringType: setValueString(o.toString()); break;
				default: Debug.LogError(this,"DataType not accounted for: " + getDataTypeRefId());
				}
			
			} else if (o instanceof IControlModel) {
				setValue(((IControlModel) o).getPrimaryValue());
			} else {
				Debug.LogError("ApplicationSettingModel", "Object data type mismatch: " + o.getClass());
				setValueString(o.toString());
			}
		} catch (Exception ex) {
			Debug.LogException(this, ex, "" + o);
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