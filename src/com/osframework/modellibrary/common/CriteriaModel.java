package com.osframework.modellibrary.common;
import java.io.Serializable;
import java.util.Vector;

public class CriteriaModel implements Serializable {

	private Vector parameters = new Vector();
	private StringBuffer sql = null;
	
	public static String operatorEquals = "=";
	public static String operatorLessThan = "<";
	public static String operatorLessThanOrEquals = "<=";
	public static String operatorGreaterThan = ">";
	public static String operatorGreaterThanOrEquals = ">=";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CriteriaModel() {
		super();
	}
	
	public String getSqlWhereClause(boolean includeWordWhere) {
		if (sql == null) {
			return "";
		} else {
			if (includeWordWhere) {
				return " where " + sql;
			} else {
				return sql.toString();
			}
		}
	}

	protected void addParameter(String fieldname, String operator, Object value) {
		if (sql != null) {
			sql.append(" and ");
		}  else {
			sql = new StringBuffer(1024);
		}
		sql.append(fieldname);
		sql.append(" ");
		sql.append(operator);
		sql.append(" ");
		sql.append("?");
		parameters.add(value);
	}

	public Vector getParameters() {
		return parameters;
	}

	public void setParameters(Vector parameters) {
		this.parameters = parameters;
	}
	
	
}

