package com.javakaihua.farmgame.utils;

import javax.sound.midi.Soundbank;
import java.sql.*;

public class SQLConnector {
    //数据库连接器


    //腾讯云端数据库，，只要联网就可访问已分配用户账号（只有对JavaGame数据库有select、update和insert权限）
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://cdb-mbj2rcby.cd.tencentcdb.com:10009/JavaGame?autoReconnect=true&amp;autoReconnectForPools=true";
    static final String USER = "javagameuser";
    static final String PASS = "javakaihua123";
    private static Connection conn = null;

    private static Statement stmt = null;

    static {//类初始化时就调用，以完成连接，接下来就不用频繁创建连接
        try{
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);

            // 打开链接
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            // 执行查询
            stmt = conn.createStatement();
            System.out.println("数据库连接成功！");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("数据库连接失败，使用本地存储！");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ResultSet readData(String sql) throws SQLException {
        //输入查询语句，返回数据
        ResultSet rs = null;
        rs=stmt.executeQuery(sql);
        return rs;
    }

    public static void writeData(String sql) {
        //输入sql语句，向数据库写入数据

        try {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static Connection getConn() {
        return conn;
    }

    public static Statement getStmt() {
        return stmt;
    }




}
