package deal_with_taxi;

import org.junit.Test;
import Connection.Tecnet_Api;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class Connect_to_taxi {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/taxi_of_shanghai";
    // 数据库的用户名与密码，需要根据自己的设置
    static final String USER = "root";
    static final String PASS = "yannian002";
    Tecnet_Api POIS=new Tecnet_Api();

    @Test
    public void User() {

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
            sql = "SELECT gps_latitude, gps_longitude,if_carrying_passengers FROM new_taxi WHERE gps_userid=105";
            ResultSet rs = stmt.executeQuery(sql);

            int mark=0;
            double prelatitude=121.4681,prelongtude=31.2211;
            // 展开结果集数据库
            int count=0,secret=0;
            String [] rrs=new String[2];
            while(rs.next()){
                // 通过字段检索
//                Date times  = rs.getDate("gps_UTC_unix_timestamp");

                double latitude = rs.getDouble("gps_latitude");
                double longitude = rs.getDouble("gps_longitude");
                int carring_passenger = rs.getInt("if_carrying_passengers");
                if (carring_passenger!=mark && carring_passenger!=0){
                    //上客了
                    secret=4;
                    rrs=POIS.soutis(longitude,latitude);
                    System.out.print("上客点是：经度："+latitude+" 纬度："+longitude+" "+"POI_1="+rrs[0]+" POI_2="+rrs[1]+" ");
                    mark=carring_passenger;
                }
                if (carring_passenger!=mark &&carring_passenger==0){
                    //下客了
                    if (secret!=4) System.out.println("不匹配发生了：count= "+count);
                    rrs=POIS.soutis(longitude,latitude);
                    System.out.println("下客点是：经度："+latitude+" 纬度："+longitude+" "+ "POI_1="+rrs[0]+" POI_2="+rrs[1]+" ");
                    mark=carring_passenger;
                }

//              System.out.println("count:"+count);
                count++;

            }
            System.out.println(count);

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

    }
}
