package com.wp.stock.utils;

import java.sql.*;

public class JDBCUtils {

    private static final String DB_URL = "jdbc:mysql://localhost/stock_portrait";
    private static final String USER = "root";
    private static final String PASS = "wp980325";

    private static Connection conn = null;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ResultSet getResultSetWithPreparedStatement(String sql) {
        System.out.println("Creating preparedStatement...");
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("can not getPreparedStatement");
        }
        try {
            rs = pstmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("can not executeQuery");
            System.out.println("sql is " + sql);
        }
        return rs;
    }
}
