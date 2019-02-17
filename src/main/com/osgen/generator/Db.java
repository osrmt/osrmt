package generator;

import database.*;

public class Db {

	public static Access access = null;
	
	static {
		try {
		access = new Access();
		access.connectToAccessDatabase(MainGenerator.schemaAccessDbFile ,"admin","");
		} catch (Exception ex) {
			ex.printStackTrace(System.err);
		}
	}
}
