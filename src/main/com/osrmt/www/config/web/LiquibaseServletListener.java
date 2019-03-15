package com.osrmt.www.config.web;

import com.osrmt.config.db.DatabaseSetup;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.SQLException;

public class LiquibaseServletListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            DatabaseSetup.getInstance().applyDbUpdates();
        } catch (SQLException e) {
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
