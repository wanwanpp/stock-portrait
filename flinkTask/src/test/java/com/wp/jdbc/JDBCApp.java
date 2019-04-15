package com.wp.jdbc;

import com.wp.stock.utils.JDBCUtils;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;

public class JDBCApp {

    static final String DB_URL = "jdbc:mysql://localhost/stock_portrait";
    static final String USER = "root";
    static final String PASS = "wp980325";

    Connection conn = null;
    Statement stmt = null;
    PreparedStatement pstmt = null;

    @Before
    public void before() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        System.out.println("Connecting to database...");
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
    }


    @Test
    public void select() throws SQLException {
        System.out.println("Creating statement...");
        stmt = conn.createStatement();
        String sql;
        sql = "SELECT count(*) FROM trade_calendar";
        ResultSet rs = stmt.executeQuery(sql);
        int count = 0;
        if (rs.next()) {
            count = rs.getInt(1);
            System.out.println("count:" + count);
        }
    }

    @Test
    public void selectWithPreparedStatement() throws SQLException {
        System.out.println("Creating preparedStatement...");
        String sql;
        String tableName = "trade_calendar";
        sql = "SELECT count(*) FROM " + tableName;
        pstmt = conn.prepareStatement(sql);

        ResultSet rs = pstmt.executeQuery();
        int count = 0;
        if (rs.next()) {
            count = rs.getInt(1);
            System.out.println("count:" + count);
        }
    }

    @Test
    public void getResultSetWithPreparedStatement() throws SQLException {
        ResultSet rs = JDBCUtils.getResultSetWithPreparedStatement("SELECT count(*) FROM trade_calendar");
        if (rs.next()) {
            System.out.println("count:" + rs.getInt(1));
        }
    }

    @Test
    public void getResultSetWithPreparedStatement2() throws SQLException {
        ResultSet rs = JDBCUtils.getResultSetWithPreparedStatement("SELECT cal_date,is_open FROM trade_calendar where cal_date < '20190328' and is_open=1 " +
                "order by cal_date desc limit 5");
        int count = 0;
        while (rs.next()) {
            System.out.println(rs.getString(1)+"   "+rs.getInt(2));
            count++;
        }
        System.out.println("=============================================");
        System.out.println(count);
    }
}
