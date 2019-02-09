package com.osframework.datalibrary.common;

import java.sql.*;

/**
 * BaseAdapter is inherited by all data adapters.  Methods common
 * to all adapters are implemented here.
 *
 */
public class BaseAdapter {
	
	/**
	 * Returns true if the column name exists within the resultset
	 * @param rset
	 * @param columnName
	 * @return true if the column name exists
	 */
	public static boolean columnExists(ResultSet rset, String columnName) {
		try {
			if (rset.findColumn(columnName) > -1) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			return false;
		}
	}

}
