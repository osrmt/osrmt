package models;

import generator.MainGenerator;

import java.util.*;

public class CreateIndexModel extends CreateBaseModel {
	
	protected IndexLibraryList indexes;
	protected TableLibraryModel table;
	protected String inset = "   ";
	
	protected static String nl = MainGenerator.nl;
	
	
	public void initialize(TableLibraryList columns, IndexLibraryList indexes) {
		this.indexes = indexes;
		Enumeration e1 = columns.elements();
		if (e1.hasMoreElements()) {
			table = (TableLibraryModel) e1.nextElement();
		}
	}
	
	public String getSql() {
		StringBuffer sb = new StringBuffer(1024);
		if (indexes != null) {
			Enumeration e1 = indexes.elements();
			String prevIndex = "";
			while (e1.hasMoreElements()) {
				IndexLibraryModel indexColumn = (IndexLibraryModel) e1.nextElement();
				if (indexColumn.getIndexName().compareTo(prevIndex)!=0) {
					sb.append(getCreateIndex(indexColumn)
							   + getIndexColumns(indexColumn) 
							   + endCreateIndex()); 
					if (indexColumn.getPrimaryInd()==1) {
						sb.append(getPrimaryKeyConstraint(indexColumn));
					}
				} else {
					// skip to next index
				}
				prevIndex = indexColumn.getIndexName();
			}
		} else {
			return "";
		}
		return sb.toString();
	}
	
	public String getPrimaryKeyConstraint(IndexLibraryModel index) {
		return nl + "alter table " + index.getTableName().toLowerCase()
		+ " add constraint " + index.getIndexName().toLowerCase()
		+ " primary key (" + index.getColumnName().toLowerCase() + ");";
	}
	
	public String dropSql() {
		StringBuffer sb = new StringBuffer(1024);
		if (indexes != null) {
			Enumeration e1 = indexes.elements();
			String prevIndex = "";
			while (e1.hasMoreElements()) {
				IndexLibraryModel index = (IndexLibraryModel) e1.nextElement();
				if (index.getIndexName().compareTo(prevIndex)!=0) {
					//sb.append( nl + "drop index " + index.getIndexName() + ";"); 
				} else {
					// skip to next index
				}
				prevIndex = index.getIndexName();
			}
		} else {
			return "";
		}
		return sb.toString();
	}
	
	protected String getIndexColumns(IndexLibraryModel index) {
		StringBuffer sb = new StringBuffer(256);
		Enumeration e1= indexes.elements();
		boolean first = true;
		while (e1.hasMoreElements()) {
			IndexLibraryModel col = (IndexLibraryModel) e1.nextElement();
			if (col.getIndexName().compareTo(index.getIndexName())==0) {
				sb.append(getIndexColumn(first, col));
				first = false;
			}
		}
		return sb.toString();
	}
	protected String getIndexColumn(boolean first, IndexLibraryModel col) {
		String line = "";
		if (!first) {
			line += ", ";
		}
		return line + col.getColumnName().toLowerCase();
	}
	
	protected String getCreateIndex(IndexLibraryModel index) {
		String s = nl + "create index ";
		if (index.getUniqueInd()==1) {
			s = nl + "create unique index ";
		}
		s += index.getIndexName().toLowerCase()
		+ " on " + index.getTableName().toLowerCase() + " (";
		return s;
	}
	protected String endCreateIndex() {
		return nl + ");";
	}
	
	public String toString() {
		return getSql();
	}
	

}
