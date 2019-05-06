package com.example.luna.activity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.widget.TextView;
import com.example.luna.R;
import com.example.luna.base.BaseActivity;
import com.example.luna.utils.Logutils;
import com.example.luna.utils.StorageUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class StorageActivity extends BaseActivity {

    private TextView all, can, all_o, can_o;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);
        all = (TextView) findViewById(R.id.all_s);
        can = (TextView) findViewById(R.id.can_s);
        all_o = (TextView) findViewById(R.id.all_o);
        can_o = (TextView) findViewById(R.id.can_o);
        all.setText(getAvailableInternalMemorySize(this));
        can.setText(getInternalMemorySize(this));
        all_o.setText(getOperators(this));
        can_o.setText(getAvailableExternalMemorySize(this));
        String s1 = StorageUtils.getInternalToatalSpace(this);
        String s2 = StorageUtils.getMemoryFree(this, "");
        String s3 = StorageUtils.getTotalMemory(this, "");
        all.setText(s1);
        can.setText(s2);
        String a = "adsf", b = "s";
        int last = a.lastIndexOf(b);
        all_o.setText(last + "");
        StringBuilder rstPathString = new StringBuilder("file://").append(
                a.substring(0, last)).append(File.separator);
        double memory = getTotalMemory() / 1024 / 1024 / 1024;
        String runMemory = "runMemory = " + memory + ",ceil = " + Math.ceil(memory);
        runMemory += "\n";
        runMemory += getAvailableInternalMemorySize(this);
        can_o.setText(runMemory);
    }

    //获取总内存的大小
    private double getTotalMemory() {
//        MemTotal:         341780 kB
        try {
            FileInputStream fis = new FileInputStream(new File("/proc/meminfo"));
            //包装一个一行行读取的流
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fis));
            //取到所有的内存信息
            String memTotal = bufferedReader.readLine();

            StringBuffer sb = new StringBuffer();

            for (char c : memTotal.toCharArray()) {

                if (c >= '0' && c <= '9') {
                    sb.append(c);
                }
            }
            //为了方便格式化 所以乘以1024
            long totalMemory = Long.parseLong(sb.toString()) * 1024;

            return totalMemory;

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }


    private String getEncodeUriString(String filePathString) {
        if (TextUtils.isEmpty(filePathString)) {
            return null;
        }
        int lastSeparatorIndex = filePathString.lastIndexOf(File.separator);
        StringBuilder rstPathString = new StringBuilder("file://").append(
                filePathString.substring(0, lastSeparatorIndex)).append(File.separator);
        String encodeFileName = Uri.encode(filePathString.substring(lastSeparatorIndex + 1,
                filePathString.length()));
        rstPathString.append(encodeFileName);
        return rstPathString.toString();
    }

    public static String getAvailableInternalMemorySize(Context context) {
        File file = Environment.getDataDirectory();
        StatFs statFs = new StatFs(file.getPath());
//        long availableBlocksLong = statFs.getAvailableBlocksLong();
//        long blockSizeLong = statFs.getBlockSizeLong();
//        return Formatter.formatFileSize(context, availableBlocksLong
//                * blockSizeLong);
        return statFs.getTotalBytes() + "";
    }

    public static String getInternalMemorySize(Context context) {
        File file = Environment.getDataDirectory();
        Logutils.d("UnKnownTile", "getInternalMemorySize" + file);
        StatFs statFs = new StatFs(file.getPath());
        long blockSizeLong = statFs.getBlockSizeLong();
        long blockCountLong = statFs.getBlockCountLong();
        long size = blockCountLong * blockSizeLong;
        return Formatter.formatFileSize(context, size);
    }

    public static String getExternalMemorySize(Context context) {
        File file = Environment.getExternalStorageDirectory();
        Logutils.d("UnKnownTile", "getExternalStorageDirectory" + file);
        StatFs statFs = new StatFs(file.getPath());
        long blockSizeLong = statFs.getBlockSizeLong();
        long blockCountLong = statFs.getBlockCountLong();
        return Formatter
                .formatFileSize(context, blockCountLong * blockSizeLong);
    }

    public static String getAvailableExternalMemorySize(Context context) {
        File file = Environment.getExternalStorageDirectory();
        StatFs statFs = new StatFs(file.getPath());
        long availableBlocksLong = statFs.getAvailableBlocksLong();
        long blockSizeLong = statFs.getBlockSizeLong();
        return Formatter.formatFileSize(context, availableBlocksLong
                * blockSizeLong);
    }

    public static String getOperators(Context context) {
        int count = TelephonyManager.getDefault().getPhoneCount();
        for (int i = 0; i < count; i++) {

//            int subId = SubscriptionManager.getSubIdUsingPhoneId(i);
//            Logutils.d("UnKnownTile","subId = "+subId);
        }
        return count + "";
//        TelephonyManager tm = (TelephonyManager) context
//                .getSystemService(Context.TELEPHONY_SERVICE);
//        String operator = "no";
//        String IMSI = tm.getSubscriberId();
//        if (IMSI == null || IMSI.equals("")) {
//            return operator;
//        }
//        if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
//            operator = "中国移动";
//        } else if (IMSI.startsWith("46001")) {
//            operator = "中国联通";
//        } else if (IMSI.startsWith("46003")) {
//            operator = "中国电信";
//        }
//        return operator;
    }
}
