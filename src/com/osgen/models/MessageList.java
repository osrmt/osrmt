package models;

import java.util.*;
import java.sql.*;
import database.*;

public class MessageList implements  Enumeration {

	private Vector list = new Vector(1000);
	private Enumeration enumeration;
	public Vector languages = new Vector();
	
	public Enumeration elements() {
		enumeration = list.elements();
		return enumeration;
	}
	
	public Object nextElement() {
		return enumeration.nextElement();
	}
	
	public boolean hasMoreElements() {
		return enumeration.hasMoreElements();
	}
	
	public void add(MessageModel tlm) {
		this.list.add(tlm);
	}
	
	
	public void load(Access access) throws SQLException, ClassNotFoundException {
		String sql = "select * from MessageBundle";
		ResultSet rset = access.executeQuery(sql);
		int cnt=0;
		while (rset.next()) {
			MessageModel tlm = new MessageModel();
			String source = rset.getString("Source");
			String defaultEnglish = rset.getString("DefaultEnglish");
			tlm.add("Default",source,defaultEnglish);
			languages.add("Default");
			
			for (int i=0; i<rset.getMetaData().getColumnCount(); i++) {
				String columnName = rset.getMetaData().getColumnName(i+1);
				if (columnName.indexOf('_') > 0) {
					String value = rset.getString(columnName);
					if (value != null  & !rset.wasNull()) {
						tlm.add(columnName,source,value);
						if (cnt==0) {
							languages.add(columnName);
						}
						if (rset.wasNull()) {
							tlm.add(columnName,source,"");
						}
					}
				}
			}
			cnt++;
			this.add(tlm);			
		}
		rset.close();
	}

}
