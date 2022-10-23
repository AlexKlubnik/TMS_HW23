package com.klubnikov;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnector {

    public static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String URL = "jdbc:mysql://localhost:3306/test_db";
    public static final String USER = " ";
    public static final String PASSWORD = " ";

    public static Connection getJdbcConnection() throws ClassNotFoundException, SQLException {

        Class.forName(JDBC_DRIVER);
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

        return connection;
    }

    public static void  closeJdbcConnection (Connection connection) throws SQLException {
        connection.close();

    }
}

