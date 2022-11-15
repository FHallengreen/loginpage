package com.example.cookielogin.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    private static Connection conn;

    public static Connection getConnection(String db_url, String uid, String pwd) {

        if (conn != null) {
            return conn;
        }

        /*String db_url = System.getenv("JDBCUrl");
        String uid = System.getenv("JDBCUsername");
        String pwd = System.getenv("JDBCPassword");*/

        try {
            conn = DriverManager.getConnection(db_url, uid, pwd);

        } catch (SQLException e) {
            System.out.printf("Couldn't connect to db");
            e.printStackTrace();
        }
        return conn;
    }
}
