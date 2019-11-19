package Connection;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Submit {
    public static void main(String[] args) throws IOException {
        Connect out=new Connect();
        LocationUtils dis=new LocationUtils();
        List<Object> output=out.User();
        double [] latitude= (double[]) output.get(0);
        double [] longitude= (double[]) output.get(1);
        File file = new File("d:\\array_stop_3.txt");  //存放数组数据的文件

        FileWriter writer = new FileWriter(file);  //文件写入流

        //将数组中的数据写入到文件中。每行各数据之间TAB间隔
        for(int i=1;i<latitude.length;i++){
            if (latitude[i]<35) {
                if (latitude[i]==latitude[i-1] && longitude[i]==longitude[i-1]){
                    writer.write(latitude[i]+"\t");
                    writer.write(longitude[i]+"\t");
                    writer.write("\r\n");
                }
                double distence=dis.getDistance(latitude[i],longitude[i],latitude[i-1],longitude[i-1]);
                System.out.println("Distence:"+distence);
                }

        }
        writer.close();
    }
}
