package com.UIAutomation.helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseHelpers {

    public DatabaseHelpers() {
        super();
    }

    public static Connection getMySQLConnection(String hostName, String dbName, String userName, String password) throws SQLException {

        String connectionURL = "jdbc:sqlserver://" + hostName + ":1433;databaseName=" + dbName;

        Connection connect = DriverManager.getConnection(connectionURL, userName, password);

        return connect;
    }

}