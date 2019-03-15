package com.osrmt.config.db;

import com.osframework.datalibrary.common.Db;
import com.osframework.framework.logging.Debug;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.SQLException;

public class DatabaseSetup {
    private static final DatabaseSetup INSTANCE = new DatabaseSetup();

    private DatabaseSetup() {}

    public static DatabaseSetup getInstance() {
        return INSTANCE;
    }

    public void applyDbUpdates() throws SQLException {
        java.sql.Connection c = Db.getConnection().getConnection();
        Liquibase liquibase = null;
        try {
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(c));

            liquibase = new Liquibase("db/liquibase.xml",
                    new ClassLoaderResourceAccessor(), database);
            liquibase.update((String) null);
        } catch (Exception ex) {
            Debug.LogException(null, ex, "Liquibase DB update exception.");
        }finally {
            if (c != null) {
                try {
                    c.rollback();
                    c.close();
                } catch (SQLException e) {
                    //nothing to do
                }
            }
        }
    }
}
