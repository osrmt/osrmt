package models;

import java.util.Enumeration;

public class postgresqlCreateIndex extends CreateIndexModel {

	public String dropSql() {
		/*
		StringBuffer sb = new StringBuffer(1024);
		if (indexes != null) {
			Enumeration e1 = indexes.elements();
			String prevIndex = "";
			while (e1.hasMoreElements()) {
				IndexLibraryModel indexColumn = (IndexLibraryModel) e1.nextElement();
				if (indexColumn.getIndexName().compareTo(prevIndex)!=0) {
					sb.append(getDropIndex(indexColumn)); 
				} else {
					// skip to next index
				}
				prevIndex = indexColumn.getIndexName();
			}
		} else {
			return "";
		}
		return sb.toString();
		*/
		return "";
	}
	public String getPrimaryKeyConstraint(IndexLibraryModel index) {
		/*return nl + "alter table " + index.getTableName().toLowerCase()
		+ " add constraint " + index.getIndexName().toLowerCase()
		+ " primary key (" + index.getColumnName().toLowerCase() + ");";
		*/
		return "";
	}
	

	protected String getDropIndex(IndexLibraryModel index) {
		String s = nl + "drop index ";
		s += index.getIndexName().toLowerCase()
		+ " on " + index.getTableName().toLowerCase() + ";" + nl;
		return s;
	}

}
