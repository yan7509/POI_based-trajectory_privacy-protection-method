package sql_INPUT;

import org.junit.Test;

import java.io.File;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class Connection {
    // MySQL 8.0 以下版本 - JDBC 驱动名及数据库 URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/taxi_of_shanghai";

    // 数据库的用户名与密码，需要根据自己的设置
    static final String USER = "root";
    static final String PASS = "yannian002";

    @Test

    public void User() {
        List<Object> output = new LinkedList<>();
        java.sql.Connection conn = null;
        Statement stmt = null;
        try{
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);

            // 打开链接
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
//            语句接口提供了三种执行SQL语句的方法：executeQuery，executeUpdate和execute
            // 执行
            System.out.println("实例化Statement对象...");
            stmt = conn.createStatement();
            String sql;


            Read_by_line read_by_line=new Read_by_line();
            File file = new File("C:\\Users\\Administrator\\Desktop\\研究进展\\数据集\\taxi\\Taxi_070220");
            //listFiles是获取该目录下所有文件和目录的绝对路径
            File[] fs = file.listFiles();
            for (File f : fs){
                sql = "INSERT IGNORE INTO `taxi_of_shanghai`.`taxi`(gps_userid,gps_UTC_timestamp,gps_latitude,gps_longitude,angle,speed,if_carrying_passengers)" +
                        " VALUES" +
                        "(105,'2007-02-20 00:00:48',31.220800,121.466600,0,45,0),";
                String array[][]=read_by_line.toArrayByFileReader1(f.toString());
                for (int i=0;i<array.length;i++){
                    sql=sql+"("+array[i][0]+",'"+array[i][1]+"',"+array[i][2]+","+array[i][3]+","+array[i][4]+","+array[i][5]+","+array[i][6]+"),";
                }
                sql=sql+"(105,'2007-02-20 00:00:48',31.220800,121.466600,0,45,0);";
                int outs=stmt.executeUpdate(sql);
                System.out.println(outs);
            }
//            sql=sql+"(105,'2007-02-20 00:00:48',31.220800,121.466600,0,45,0);";
//            int outs=stmt.executeUpdate(sql);

            // 展开结果集数据库
//            INSERT IGNORE INTO `taxi_of_shanghai`.`taxi`(gps_userid,gps_UTC_timestamp,gps_latitude,gps_longitude,angle,speed,if_carrying_passengers)
            //VALUES
            //(`105`,`2007-02-20 00:00:48`,`121.466600`,`31.220800`,`0`, `45`,`0`);
            // 完成后关闭

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
//        return output;
    }
}
