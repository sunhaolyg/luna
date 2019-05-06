package com.example.luna.write;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;
import com.example.luna.base.BaseActivity;
import com.example.luna.utils.Logutils;

import java.io.*;
import java.util.zip.GZIPOutputStream;

public class WriteActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        writeText(System.currentTimeMillis() + "");
        getFileList();
//        read();
//        zipEagle();
    }

    private void zipEagle() {
        File desFile = new File("/storage/emulated/0/eaglelog/");
//        File srcFile = new File(desFile, "eagle" + System.currentTimeMillis() + ".zip");
        File srcFile = new File(desFile, "1.zip");
        if (!desFile.exists()) {
            desFile.mkdir();
        }
        zip(srcFile, desFile);
    }

    public void zip(File srcFile, File desFile) {
        GZIPOutputStream zos = null;
        FileInputStream fis = null;
        try {
            //创建压缩输出流,将目标文件传入
            try {
                if (desFile.isDirectory()) {
                    File[] files = desFile.listFiles();
                    for (File f : files) {
                        zip(srcFile, f);
                    }
                } else {
                    zos = new GZIPOutputStream(new FileOutputStream(desFile));
                    //创建文件输入流,将源文件传入
                    fis = new FileInputStream(srcFile);
                    byte[] buffer = new byte[1024];
                    int len = -1;
                    //利用IO流写入写出的形式将源文件写入到目标文件中进行压缩
                    while ((len = (fis.read(buffer))) != -1) {
                        zos.write(buffer, 0, len);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } finally {
            try {
                if (zos != null) {
                    zos.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void stringTxt(String str) {
        try {
            FileWriter fw = new FileWriter("/sdcard/aaa" + "/cmd.txt");//SD卡中的路径
            fw.flush();
            fw.write(str);
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getFileList() {
        File file = new File("/storage/emulated/0/eaglelog/");
        File[] files = file.listFiles();
        for (File f : files) {
            Logutils.d("RegisterCodeTAG", "File[] = " + f);

        }
    }

    public void read() {
        File file = new File("/storage/emulated/0/eaglelog/1531359535365.txt");
        String all = "";
        String str = null;
        try {
            InputStream is = new FileInputStream(file);
            InputStreamReader input = new InputStreamReader(is, "UTF-8");
            BufferedReader reader = new BufferedReader(input);
            while ((str = reader.readLine()) != null) {
                all += str + "\n";
//                tvInfo.append("\n");
            }
            Logutils.d("RegisterCodeTAG", "all = " + all);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeText(String content) {

        if (content == null || content.equals("")) {
            return;
        }

        // 外部存储私有路径：Android文件夹
//        String privatePath = getExternalFilesDir(null).getPath();// 私有路径不分类为null
//        String filePath = privatePath + "/abc/";

        // 外部存储公共路径：DICM，DOWNLOAD，MUSIC等系统提供的文件夹
//        String publicPath = Environment
//                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
//                .getPath();
//        String filePath = publicPath + "/abc/";

        String dir = getExternalCacheDir() + "";
        // 自定义文件路径
        String filePath = "/storage/emulated/0/eaglelog/";// 外部存储路径（根目录）
        File dirFile = new File(filePath);
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".txt";
        File file = new File(filePath + fileName);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(dir.getBytes());
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
