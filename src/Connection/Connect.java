package Connection;

//import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Connect {
    // MySQL 8.0 以下版本 - JDBC 驱动名及数据库 URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/geolife";

    // 数据库的用户名与密码，需要根据自己的设置
    static final String USER = "root";
    static final String PASS = "yannian002";
    static double [] gps_latitude = new double[108607];
    static double [] gps_longitude = new double[108607];
    static int [] gps_UTC_unix_timestamp = new int[108607];

//    @Test

    public  List<Object> User() {
        List<Object> output = new LinkedList<>();
        Connection conn = null;
        Statement stmt = null;
        try{
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);

            // 打开链接
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            // 执行查询
            System.out.println("实例化Statement对象...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT gps_UTC_unix_timestamp, gps_latitude, gps_longitude FROM geolife WHERE gps_userid=1 ORDER BY gps_UTC_unix_timestamp";
            ResultSet rs = stmt.executeQuery(sql);

            // 展开结果集数据库
            int count=0;
            while(rs.next()){
                // 通过字段检索
                int times  = rs.getInt("gps_UTC_unix_timestamp");
                double latitude = rs.getDouble("gps_latitude");
                double longitude = rs.getDouble("gps_longitude");
                gps_latitude[count]=latitude;
                gps_longitude[count]=longitude;
                gps_UTC_unix_timestamp[count]=times;
//              System.out.println("count:"+count);
                count++;
                //if (count>20) break;
                // 输出数据
//                if (gps_latitude[gps_latitude.length-1]==40.21096 && gps_longitude[gps_longitude.length-1]==116.495582)
//                    System.out.println("Yes correct!");

            }
            System.out.println(count);
            output.add(gps_latitude);
            output.add(gps_longitude);
            output.add(gps_UTC_unix_timestamp);
            // 完成后关闭
            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){
            // 处理 JDBC 错误
            se.printStackTrace();
        }catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
        }finally{
            // 关闭资源
            try{
                if(stmt!=null) stmt.close();
            }catch(SQLException se2){
            }// 什么都不做
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        System.out.println("Goodbye!");
        return output;
    }
}
