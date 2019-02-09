package com.osrmt.appclient.setup;

import java.util.Enumeration;
import java.io.*;

import com.osframework.datalibrary.common.ConnectionProperty;
import com.osrmt.modellibrary.reference.group.RelationGroup;
import com.osrmt.modellibrary.reqmanager.*;
// import com.osupgrade.utility.SerializeUtility;
// import com.osupgrade.model.*;


public class UpgradeManager extends ConsoleManager {

	public void upgrade(ConnectionProperty target) throws Exception {
		
		d("Execute <db>_create_schema_upgrade.sql");
		d("Execute <db>_alter_schema_upgrade.sql");
		d(" (unless using MS Access in which case migrate data)");
		doption("Schema upgrade successful? Y|N [N]: ");
		if (! getStringData("N").equals("Y")) {
			return;
		}		
		d("upgrade_target.xml Version 1.4 Target:");
		d(target.getUrl());
		d(target.getUsername());
		d(target.getEnvironment());
		doption("Target correct? Y|N [N]: ");
		if (! getStringData("N").equals("Y")) {
			return;
		}
		
		//migrate(target);
	}
	/*
	private void migrate(ConnectionProperty target) throws Exception {
		File file = new File("updatelist.bin");
		if (file.exists()) {
			UpdateList updates = (UpdateList) SerializeUtility.deserialize(file);
			com.osupgrade.db.common.JDBCAccess access = getJDBCAccess(target);
			for (UpdateModel update : updates) {
				update.execute(access);
			}
			customUpdate(access);
		} else {
			throw new java.io.FileNotFoundException("updatelist.bin");
		}
	}
	
	private void customUpdate(com.osupgrade.db.common.JDBCAccess access) throws Exception {
		if (com.osframework.appclient.services.ReferenceServices.getLastUpdated().equals("26-Mar-2007")) {
			// relationships
			String sql = "update requirement_tree set relation_ref_id = 1178 where requirement_tree_id in ("
			+ " select rt.requirement_tree_id from artifact a, requirement_tree rt, artifact b"
			+ " where a.artifact_id = rt.parent_id"
			+ " and rt.child_id = b.artifact_id"
			+ " and not a.artifact_ref_id = b.artifact_ref_id)";
			access.executeUpdate(sql);
			String sql2 = "update application_custom_control set class_name = 'com.osrmt.appclient.reqmanager.ArtifactHistoryList' "
				+ " where application_custom_control_id = 16";
			access.executeUpdate(sql2);
		}
	}

	private com.osupgrade.db.common.JDBCAccess getJDBCAccess(ConnectionProperty target) throws Exception {
		com.osupgrade.db.common.ConnectionProperty cp = new com.osupgrade.db.common.ConnectionProperty();
		cp.setConnectionType(target.getConnectionType());
		cp.setConnectToURL(target.isConnectToURL());
		cp.setDriverClass(target.getDriverClass());
		cp.setEnvironment(target.getEnvironment());
		cp.setPassword(target.getPassword());
		cp.setUnicodeConnection(target.isUnicodeConnection());
		cp.setUrl(target.getUrl());
		cp.setUsername(target.getUsername());
		return new com.osupgrade.db.common.JDBCAccess(cp);
	}*/
}

