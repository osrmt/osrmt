package generator;

import java.sql.ResultSet;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import models.*;
import utilities.*;

public class DatabaseSchema {

	private MainGenerator maingen = null;
	private String nl = "\r\n";
	public static final int oracleDatabase = 0;
	public static final int sqlServerDatabase = 1;
	public static final int mysqlDatabase = 2;
	public static final int postgresqlDatabase = 3;
	public static String reservedWords;
	public static Hashtable reserved = new Hashtable();
	
	public class SchemaContent {
		public int database;
		public String databaseName;
		public StringBuffer createSql = new StringBuffer(1024*1024);
		public StringBuffer dropSql = new StringBuffer(1024);
		public SchemaContent (int db, String dbname) {
			this.database = db;
			this.databaseName = dbname;
		}
	}
	
	public DatabaseSchema(MainGenerator m) {
		this.maingen = m;
	}
	
	public void CreateSchema() throws Exception {
		String getSchemaDir = maingen.props.getProperty(MainGenerator.propSCHEMADIR);
		this.reservedWords = FileSystemUtil.getContents(maingen.props.getProperty(MainGenerator.propROOTDIR) +"\\database"
				, "reserved_db_words.txt");
		Vector list = new Vector();
		list.add(new SchemaContent(oracleDatabase,"oracle"));
		list.add(new SchemaContent(sqlServerDatabase,"sqlServer"));
		list.add(new SchemaContent(mysqlDatabase,"mysql"));
		list.add(new SchemaContent(postgresqlDatabase,"postgresql"));
		
		FileSystemUtil.CreateDirectory(getSchemaDir);
		Enumeration e1= list.elements();
		while (e1.hasMoreElements()) {
			SchemaContent content = (SchemaContent) e1.nextElement();
			CreateDatabaseSchema(content);
			FileSystemUtil.CreateFile(getSchemaDir, content.databaseName + "_create_schema.sql", content.createSql.toString());
			FileSystemUtil.CreateFile(getSchemaDir, content.databaseName + "_drop_schema.sql", content.dropSql.toString());
		}
	}

	public void CreateDatabaseSchema(SchemaContent content) throws Exception {		
		CreateTableModel createTable = getCreateTableModel(content.database);
		CreateIndexModel createIndex = getCreateIndexModel(content.database);
		
		String sql = "select tableName from tableLibrary where tableind=1 group by tableName";
		ResultSet rset = Db.access.executeQuery(sql);
		while (rset.next()) {
			String tableName = rset.getString("tableName").toLowerCase();
			if (reservedWords.toLowerCase().indexOf(tableName.toLowerCase())> -1) {
				System.err.println("Warning reserved word found: " + tableName);
			}
			// add the fields 
			createTable.initialize(getColumns(tableName), getIndexes(tableName));
			createIndex.initialize(getColumns(tableName), getIndexes(tableName));
			
			// generate the Create Table statements
			content.createSql.append(createTable.getSql());
			content.createSql.append(createIndex.getSql());
			// generate the Drop Table statement
			content.dropSql.append(createTable.dropSql());
//			sb.append(new InsertTableData(tableList));
			content.dropSql.append(createIndex.dropSql());
//			sb.append(new CreateIndexModel(tableList));
//			sb.append(new CreateConstrainteModel(tableList));
		}
		rset.close();
	}
	
	private CreateTableModel getCreateTableModel(int database) {
		CreateTableModel createTable = new CreateTableModel();
		switch (database) {
		case oracleDatabase: return new OracleCreateTable();
		case sqlServerDatabase: return new SQLServerCreateTable();
		case mysqlDatabase: return new mysqlCreateTable();
		case postgresqlDatabase: return new postgresqlCreateTable();
			default: System.err.println("database " + database + " not found in DatabaseSchema.getCreateTableModel");
		}
		return createTable;
	}
	
	private CreateIndexModel getCreateIndexModel(int database) {
		CreateIndexModel createIndex = new CreateIndexModel();
		switch (database) {
			case oracleDatabase: return new OracleCreateIndex();
			case sqlServerDatabase: return new SQLServerCreateIndex();
			case mysqlDatabase: return new mysqlCreateIndex();
			case postgresqlDatabase: return new postgresqlCreateIndex();
			default: System.err.println("database " + database + " not found in DatabaseSchema.getCreateIndexModel");
		}
		return createIndex;
	}
	
	private TableLibraryList getColumns(String tableName) {
		
		Enumeration e1 = maingen.getTableLibraryList().elements();
		// Create the list of columns
		TableLibraryList columns = new TableLibraryList();
		while (e1.hasMoreElements()) {
			// Data Model
			TableLibraryModel mlm = (TableLibraryModel) e1.nextElement();
			if (mlm.getTableName().compareTo(tableName)==0) {
				columns.add(mlm);
				if (reservedWords.toLowerCase().indexOf(mlm.getColumnName().toLowerCase())> -1) {
					if (reserved.containsKey(mlm.getColumnName())) {
						
					} else {
						reserved.put(mlm.getColumnName(),mlm.getColumnName());
						System.err.println("Warning reserved word found: " + mlm.getColumnName());
					}
				}
			}
		}	
		return columns;
	}
	
	private IndexLibraryList getIndexes(String tableName) {
		
		Enumeration e1 = maingen.getIndexLibraryList().elements();
		// Create the list of columns
		IndexLibraryList columns = new IndexLibraryList();
		while (e1.hasMoreElements()) {
			// Data Model
			IndexLibraryModel mlm = (IndexLibraryModel) e1.nextElement();
			if (mlm.getTableName().compareTo(tableName)==0) {
				columns.add(mlm);
			}
		}	
		return columns;
	}
	
	
	public String getCreateTable(TableLibraryList tlm) {
		StringBuffer sb = new StringBuffer(1024);
		
		return sb.toString();
	}

	public String getInsertData(TableLibraryList tlm) {
		StringBuffer sb = new StringBuffer(1024);
		
		return sb.toString();
	}

	public String getCreateIndexes(TableLibraryList tlm) {
		StringBuffer sb = new StringBuffer(1024);
		
		return sb.toString();
	}

	public String getCreateConstraints(TableLibraryList tlm) {
		StringBuffer sb = new StringBuffer(1024);
		
		return sb.toString();
	}

}
