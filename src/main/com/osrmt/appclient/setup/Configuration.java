package com.osrmt.appclient.setup;

import com.osframework.datalibrary.common.ConnectionList;
import com.osframework.datalibrary.common.ConnectionProperty;

public class Configuration {
	
	private static final int Client2Tier = 0;
	private static final int Client3Tier = 1;
	private static final int ServerTier = 2;
	
	private ConnectionProperty connectionProperty = new ConnectionProperty();
	private int clientServer = 0;
	private MainManager state;

	public Configuration() {
		super();
	}

	/**
	 * Parameters [server|client]
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Configuration config = new Configuration();
			config.start();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void start() {
		try {
			ConnectionList list = ConnectionList.loadConnections();
			connectionProperty = (ConnectionProperty) list.getElementAt(0);
		} catch (Exception ex) {
		}
		
		state = new MainManager(){

			public void defineNewConnection() {
				ConnectionManager cm = new ConnectionManager();
				cm.updateConnection(connectionProperty);
			}

			public void initializeDatabase() {
				ConnectionManager cm = new ConnectionManager();
				cm.importReference(connectionProperty);
			}

			public void saveConnection() {
				ConnectionManager cm = new ConnectionManager();
				cm.saveConnection(connectionProperty);
			}

			public void testConnection() {
				ConnectionManager cm = new ConnectionManager();
				cm.testConnection(connectionProperty);
			}
			
			public void migrateDatabase() {
				try {
					ConnectionList.setConfigFileName("migration.xml");			
					ConnectionList connections = ConnectionList.loadConnections();
					ConnectionProperty source = connections.getEnvironment("source");
					ConnectionProperty destination = connections.getEnvironment("destination");
					if (source == null || destination == null) {
						throw new Exception("connection.xml must contain source and destination environments");
					} else {
						d("Enter connection.xml file name for source:");
						d(source.getUrl());
						d(source.getUsername());
						d(source.getEnvironment());
						doption("Source correct? Y|N [Y]: ");
						if (! getStringData("Y").equals("Y")) {
							return;
						}
						
						d("migration.xml Destination:");
						d(destination.getUrl());
						d(destination.getUsername());
						d(destination.getEnvironment());
						doption("Target correct? Y|N [N]: ");
						if (! getStringData("N").equals("Y")) {
							return;
						}
						DatabaseMigration migrate = new DatabaseMigration(source, destination);
						String tables = "APP_CONTROL_USER_DEFINED,APPLICATION_CONTROL,APP_CONTROL_TEMPLATE,APPLICATION_CUSTOM_CONTROL,"
							+ "APPLICATION_SECURITY,APPLICATION_SETTING,APPLICATION_USER,APPLICATION_USER_GROUP,"
							+ "APPLICATION_VIEW,ARTIFACT,ARTIFACT_HISTORY,ARTIFACT_IDENTITY,BASELINE,ISSUE,"
							+ "ISSUE_LOG,RECORD_EXTENSION,RECORD_EXTENSION_COLUMN,RECORD_FILE,RECORD_FILE_HISTORY,RECORD_PARAMETER,"
							+ "RECORD_PARAMETER_VALUE,REFERENCE,REFERENCE_HISTORY,REFERENCE_GROUP,REFERENCE_TREE,REPORT,"
							+ "REQUIREMENT_TREE,REQUIREMENT_TREE_HISTORY";
						migrate.replaceReference(DatabaseMigration.getTableList(tables));
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			
			public void upgradeDatabase() {
				try {
					ConnectionList.setConfigFileName("upgrade_target.xml");
					ConnectionList list2 = ConnectionList.loadConnections();
					ConnectionProperty targetConnection = (ConnectionProperty) list2.getElementAt(0);
					
					ConnectionManager cm2 = new ConnectionManager();
					cm2.testConnection(targetConnection);
					
					UpgradeManager um = new UpgradeManager();
					um.upgrade(targetConnection);
					
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

			@Override
			public void exportLanguageFile() {
				LanguageManager cm = new LanguageManager();
				cm.exportLanguageFile();
			}

			@Override
			public void importLanguageFile() {
				LanguageManager cm = new LanguageManager();
				cm.importLanguageFile();
			}
			
		};
		while (true) {
			try {
				state.displayState();
			} catch (Exception ex){}
		}
	}

	public int getClientServer() {
		return clientServer;
	}

	public void setClientServer(int clientServer) {
		this.clientServer = clientServer;
	}
	
	

}

