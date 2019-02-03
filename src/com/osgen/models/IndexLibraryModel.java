package models;

public class IndexLibraryModel {

	private String indexName;
	private String tableName;
	private String columnName;
	private int columnPosition;
	private int primaryInd;
	private int uniqueInd;
	private int foreignInd;
	private String version;
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public int getColumnPosition() {
		return columnPosition;
	}
	public void setColumnPosition(int columnPosition) {
		this.columnPosition = columnPosition;
	}
	public int getForeignInd() {
		return foreignInd;
	}
	public void setForeignInd(int foreignInd) {
		this.foreignInd = foreignInd;
	}
	public String getIndexName() {
		return indexName;
	}
	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}
	public int getPrimaryInd() {
		return primaryInd;
	}
	public void setPrimaryInd(int primaryInd) {
		this.primaryInd = primaryInd;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName.toLowerCase();
	}
	public int getUniqueInd() {
		return uniqueInd;
	}
	public void setUniqueInd(int uniqueInd) {
		this.uniqueInd = uniqueInd;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
	public String toString() {
		return "IndexLibraryModel:" + indexName
		+ "," + tableName
		+ "," + columnName
		+ "," + columnPosition;
	}
}
