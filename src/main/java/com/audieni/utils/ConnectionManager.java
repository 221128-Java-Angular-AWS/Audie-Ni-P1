package com.audieni.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {
    private static Connection connection;

    private ConnectionManager() {}

    public static Connection getConnection() {
        if (connection == null) {
            connection = connect();
        }
        return connection;
    }

    private static Connection connect() {
        try {
            Properties props = new Properties();
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            InputStream input = loader.getResourceAsStream("jdbc.properties");
            props.load(input);

            // jdbc:postgresql://<host>:<port>/<database>?user=<username>&password=<password>
            StringBuilder sb = new StringBuilder();
            sb.append("jdbc:postgresql://");
            sb.append(props.getProperty("host"));
            sb.append(":");
            sb.append(props.getProperty("port"));
            sb.append("/");
            sb.append(props.getProperty("database"));
            sb.append("?user=");
            sb.append(props.getProperty("username"));
            sb.append("&password=");
            sb.append(props.getProperty("password"));
            sb.append("&currentSchema=");
            sb.append(props.getProperty("schema"));

            Class.forName(props.getProperty("driver"));
            connection = DriverManager.getConnection(sb.toString());
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }
}
