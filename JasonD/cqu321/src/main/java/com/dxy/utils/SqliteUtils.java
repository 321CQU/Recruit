package com.dxy.utils;

import org.apache.log4j.Logger;

import java.sql.*;
/**
 * @Author: JasonD
 * @date: 2023/3/15 10:15
 * @Description:
 */
public class SqliteUtils {
    public static String DriveName = "org.sqlite.JDBC";

    public static String url = "jdbc:sqlite:/Users/jason/Downloads/cqu321/src/main/resources/sqlitedatabase.db";

    public static Connection connection;

    public static Logger logger = Logger.getLogger(SqliteUtils.class);

    static {
        try {
            Class.forName(DriveName);
            connection = DriverManager.getConnection(url);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
    }
}
