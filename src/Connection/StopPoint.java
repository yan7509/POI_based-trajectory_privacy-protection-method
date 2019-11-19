package Connection;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class StopPoint {
    public static void main(String[] args) throws Exception {
//       int [] week=new int[37];
        gps_UTC_unix_timestam_change_to_BeiJingTime Timer = null;
        Connect out = new Connect();
        LocationUtils dis = new LocationUtils();
        List<Object> output = out.User();
        double[] latitude = (double[]) output.get(0);
        double[] longitude = (double[]) output.get(1);
        int[] times = (int[]) output.get(2);
        File file = new File("d:\\BaiDu_stop.txt");  //存放数组数据的文件

        FileWriter writer = new FileWriter(file);  //文件写入流
        Tecnet_Api api=new Tecnet_Api();
        //将数组中的数据写入到文件中。每行各数据之间TAB间隔
        int mark=0,count=0,weekcount=0;
        for (int i = 1; i < latitude.length; i++) {
            if (latitude[i] > 35){
                double distence = dis.getDistance(latitude[mark], longitude[mark], latitude[i - 1], longitude[i - 1]);
                int time = times[mark] - times[i - 1];
                if (distence < 200) {
                    if (time < 1200) {
                        count++;
                        continue;
                    }
                }
                if (count>10 && distence > 200){
//                    String date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date(times[mark]).getTime() * 1000);
//                    String timerr=date.substring(11,19);
//                    writer.write(timerr+"\t");
                    api.soutis(latitude[mark],longitude[mark]);
                    writer.write(latitude[mark]+",");
                    writer.write(longitude[mark]+"\t");
                    writer.write("\r\n");
//                    System.out.println("count is:"+count);
//                   gps_UTC_unix_timestam_change_to_BeiJingTime C=new gps_UTC_unix_timestam_change_to_BeiJingTime(times[mark],week,weekcount);
                    weekcount++;
                    if (weekcount>5) break;
                    count=0;
                    continue;
                }
                if (count<10 && distence > 200){
                    mark=i;
                }
            }

        }
        writer.close();
        int counts=0;
        System.out.println(weekcount);
//        for (int i=0;i<weekcount;i++){
//            if (week[i]==5){
//                counts++;
//            }
//        }
        System.out.println("counts="+counts);
    }
}
