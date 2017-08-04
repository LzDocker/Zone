package com.dcoker.zone.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by Mr.Zhang on 2017/8/2.
 */

public class FileUtils {


    public static void copyFile(File oldfile, File newfile) {
        try {
            int bytesum = 0;
            int byteread = 0;
            if (oldfile!=null) { //文件存在时
                InputStream inStream = new FileInputStream(oldfile.getPath()); //读入原文件
                FileOutputStream fs = new FileOutputStream(newfile.getPath());
                byte[] buffer = new byte[1444];
                while ((byteread = inStream.read(buffer))!= -1) {
                    bytesum += byteread; //字节数 文件大小
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        }
        catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();

        }

    }

}
