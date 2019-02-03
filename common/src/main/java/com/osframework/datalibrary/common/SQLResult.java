package com.osframework.datalibrary.common;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.osframework.framework.logging.Debug;

public class SQLResult {

	private ResultSet rset = null;
	private int rowsUpdated = 0;
	private DbConnection connection = null;
	private PreparedStatement preparedStatement = null;
	private Statement statement = null;
	
	public SQLResult(Statement statement, ResultSet rset, DbConnection connection) {
		setConnection(connection);
		setRset(rset);
		setStatement(statement);
	}
	
	public SQLResult(Statement statement, ResultSet rset) {
		setRset(rset);
		setStatement(statement);
	}
	
	public SQLResult(PreparedStatement preparedStatement, ResultSet rset, DbConnection connection) {
		setConnection(connection);
		setRset(rset);
		setPreparedStatement(preparedStatement);
	}
	
	public SQLResult(PreparedStatement preparedStatement, ResultSet rset) {
		setRset(rset);
		setPreparedStatement(preparedStatement);
	}
	
	public SQLResult(ResultSet rset, DbConnection connection) {
		setConnection(connection);
		setRset(rset);
	}
	
	public SQLResult(PreparedStatement preparedStatement, int rows, DbConnection connection) {
		setConnection(connection);
		setRowsUpdated(rows);
		setPreparedStatement(preparedStatement);
	}
	
	public SQLResult(int rows, DbConnection connection) {
		setConnection(connection);
		setRowsUpdated(rows);
	}
	
	public SQLResult(int rows) {
		setRowsUpdated(rows);
	}
	
	public void close() {
		try {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (Exception pse) {
					Debug.LogException(this,pse,"Closing prepared statement");
				}
			}
			if (statement != null) {
				try {
					statement.close();
				} catch (Exception rse) {
					Debug.LogException(this,rse,"Closing statement");
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (Exception cse) {
					Debug.LogException(this,cse,"Closing connection");
				}
			}
			
		} catch (Exception ex) {}
	}
	
	public void closeStatements() {
		try {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (Exception pse) {
					Debug.LogException(this,pse,"Closing prepared statement");
				}
			}
			if (statement != null) {
				try {
					statement.close();
				} catch (Exception rse) {
					Debug.LogException(this,rse,"Closing statement");
				}
			}
		} catch (Exception ex) {}
	}
	
	public DbConnection getConnection() {
		return connection;
	}
	public void setConnection(DbConnection connection) {
		this.connection = connection;
	}
	public PreparedStatement getPreparedStatement() {
		return preparedStatement;
	}
	public void setPreparedStatement(PreparedStatement preparedStatement) {
		this.preparedStatement = preparedStatement;
	}
	public int getRowsUpdated() {
		return rowsUpdated;
	}
	public void setRowsUpdated(int rowsUpdated) {
		this.rowsUpdated = rowsUpdated;
	}
	public ResultSet getRset() {
		return rset;
	}
	public void setRset(ResultSet rset) {
		this.rset = rset;
	}
	
	public Statement getStatement() {
		return statement;
	}

	public void setStatement(Statement statement) {
		this.statement = statement;
	}

}

