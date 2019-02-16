package com.osrmt.appclient.setup;

import java.io.*;

public abstract class MainManager extends ConsoleManager {

	private final static int stateMain = 0;
	private final static int stateConnection = 1;
	private final static int stateSaveConneciton = 2;
	private final static int stateInitialize = 3;
	private final static int stateTestConnection = 4;
	private boolean started = false;
	private int currentState = 0;
	
	public void displayState() {
		switch (currentState) {
		case 0: 
			if (started) {
				doption("Press enter to continue...");
				getStringInput();
			}
			d("Select configuration option 1,2,3,4,5,6,7,8 or 0 to exit");
			d("1) Define a new connection");
			d("2) Test the connection");
			d("3) Save the new connection");
			d("4) Initialize a new database");
			d("5) Upgrade 1.4 to 1.5 database");
			d("6) Migrate database contents");
			d("7) Export language file");
			d("8) Import language file");
			nl();
			d("0) Exit");
			doption("Enter option number [Exit]: ");
			started = true;
			process(getIntInput());
		}
	}
	
	private void process(int option) {
		switch (currentState) {
		case stateMain:
			switch (option) {
			case 0: System.exit(0);
					break;
			case 1: defineNewConnection();
					break;
			case 2: testConnection();
					break;
			case 3: saveConnection();
					break;
			case 4: initializeDatabase();
					break;
			case 5: upgradeDatabase();
					break;
			case 6: migrateDatabase();
					break;
			case 7: exportLanguageFile();
					break;
			case 8: importLanguageFile();
					break;
			}
		}
	}
	
	public abstract void defineNewConnection();
	public abstract void testConnection();
	public abstract void saveConnection();
	public abstract void initializeDatabase();
	public abstract void upgradeDatabase();
	public abstract void migrateDatabase();
	public abstract void exportLanguageFile();
	public abstract void importLanguageFile();
	
}

