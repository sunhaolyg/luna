package com.example.luna.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class IOUtils {


    public static void writeText(String content) {

        if (content == null || content.equals("")) {
            return;
        }

        String filePath = "/storage/emulated/0/eaglelog/";
        File dirFile = new File(filePath);
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".txt";
        File file = new File(filePath + fileName);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(content.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
