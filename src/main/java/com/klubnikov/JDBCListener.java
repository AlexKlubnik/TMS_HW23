package com.klubnikov;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.sql.Connection;
import java.sql.SQLException;

@WebListener
public class JDBCListener implements ServletContextListener {


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            Connection connection1 = JDBCConnector.getJdbcConnection();
            sce.getServletContext().setAttribute("connectionToDb", connection1);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            JDBCConnector.closeJdbcConnection((Connection) sce.getServletContext()
                    .getAttribute("connectionToDb"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
