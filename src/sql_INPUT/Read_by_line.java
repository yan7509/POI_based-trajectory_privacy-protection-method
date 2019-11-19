package sql_INPUT;

//import org.junit.Test;

import java.io.*;
import java.util.ArrayList;

public class Read_by_line {


    public static void main(String[] args) {
        File file = new File("/Users/nicolas/Downloads/Taxi_070220");
        //listFiles是获取该目录下所有文件和目录的绝对路径
        File[] fs = file.listFiles();
        for (File f : fs){
            System.out.println(f.toString());
        }
//        String array[][]=toArrayByFileReader1("C:\\Users\\Administrator\\Desktop\\研究进展\\数据集\\taxi\\Taxi_070220\\Taxi_10001");
    }


    public  String[][] toArrayByFileReader1(String name) {
        // 使用ArrayList来存储每行读取到的字符串
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            FileReader fr = new FileReader(name);
            BufferedReader bf = new BufferedReader(fr);
            String str;
            // 按行读取字符串
            while ((str = bf.readLine()) != null) {
                arrayList.add(str);
            }
            bf.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对ArrayList中存储的字符串进行处理
        int length = arrayList.size();
        int width = arrayList.get(0).split(",").length;
        String array[][] = new String[length][width];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                String s = arrayList.get(i).split(",")[j];
                array[i][j] = s;
            }
        }
        // 返回数组
        return array;
    }
}
