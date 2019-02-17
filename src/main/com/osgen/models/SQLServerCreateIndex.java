package models;

public class SQLServerCreateIndex extends CreateIndexModel {

	
	public String getPrimaryKeyConstraint(IndexLibraryModel index) {
		return "";
	}
	
	public String getSql() {
		return super.getSql();
	}
	
	protected String getCreateIndex(IndexLibraryModel index) {
		String s = nl + "create index ";
		if (index.getUniqueInd()==1) {
			s = nl + "create unique index ";
		}
		String indexName = "";
		if (index.getPrimaryInd()==1) {
			indexName = index.getIndexName().toLowerCase().replace("_pk","_uk0");
		} else {
			indexName = index.getIndexName().toLowerCase();
		}
		s += indexName
		+ " on " + index.getTableName().toLowerCase() + " (";
		return s;
	}
	public String dropSql() {
		return "";
	}
}
