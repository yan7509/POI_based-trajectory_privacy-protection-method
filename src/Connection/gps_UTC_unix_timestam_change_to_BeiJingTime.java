package Connection;

import sun.java2d.pipe.BufferedBufImgOps;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class gps_UTC_unix_timestam_change_to_BeiJingTime {
    public gps_UTC_unix_timestam_change_to_BeiJingTime(int unix_timestamp,int[] week,int count) {
        String date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date(unix_timestamp).getTime() * 1000);
        int day=Integer.parseInt(date.substring(0,2));
        int month=Integer.parseInt(date.substring(3,5));
        int year=Integer.parseInt(date.substring(6,10));
//        String timer=date.substring(11,19);
        Calendar calendar = Calendar.getInstance();//获得一个日历
        calendar.set(year, month-1, day);//设置当前时间,月份是从0月开始计算
        int number = calendar.get(Calendar.DAY_OF_WEEK);//星期表示1-7，是从星期日开始，
        String [] str = {"","星期日","星期一","星期二","星期三","星期四","星期五","星期六",};
//        System.out.println(timer);
        if (number==5){
            System.out.println("Beijing time is " + date +" "+str[number]);
        }
        week[count]=number;
    }


    public gps_UTC_unix_timestam_change_to_BeiJingTime(int unix_timestamp,String timer){
        String date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date(unix_timestamp).getTime() * 1000);
        timer=date.substring(11,19);
    }



}
