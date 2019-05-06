package com.example.luna.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;
import com.example.luna.R;
import com.example.luna.base.BaseActivity;
import com.example.luna.base.MainActivity;

public class NotificationActivity extends BaseActivity {

    NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

//        View decorView = getWindow().getDecorView();
//        int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//        decorView.setSystemUiVisibility(option);
//        getWindow().setNavigationBarColor(Color.TRANSPARENT);
//        getWindow().setStatusBarColor(Color.TRANSPARENT);
//
//        ActionBar actionBar = getActionBar();
//        actionBar.hide();
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        final PackageManager pm = mContext.getPackageManager();

        String label = (String) pm.getApplicationLabel(mContext.getApplicationInfo());
        Log.d("WaterFallLayout", "2,label = " + label);
    }

    private static final int NOTIFICATION_ID = 1000;

    private void sendNotification(int what) {
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                1,
                new Intent(this, NotificationActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT);
        //1、NotificationManager
        /** 2、Builder->Notification
         *  必要属性有三项
         *  小图标，通过 setSmallIcon() 方法设置
         *  标题，通过 setContentTitle() 方法设置
         *  内容，通过 setContentText() 方法设置*/
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentInfo("Content info " + what)
                .setContentText("Content text " + what)//设置通知内容
                .setContentTitle("Content title " + what)//设置通知标题
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher))
                .setSmallIcon(R.drawable.ic_launcher)//不能缺少的一个属性
                .setSubText("Subtext " + what)
                .setTicker("滚动消息......" + what)
                .setColor(getColor(R.color.ruyi_blue))
                .setContentIntent(pendingIntent)
                .setWhen(System.currentTimeMillis());//设置通知时间，默认为系统发出通知的时间，通常不用设置

//        final Bundle extras = new Bundle();
//        extras.putString(Notification.EXTRA_SUBSTITUTE_APP_NAME, "eagle11");
//        builder.addExtras(extras);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("00" + what, "my_channel", NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableLights(true); //是否在桌面icon右上角展示小红点
            channel.setLightColor(Color.GREEN); //小红点颜色
            channel.setShowBadge(true); //是否在久按桌面图标时显示此渠道的通知
            notificationManager.createNotificationChannel(channel);
            builder.setChannelId("00" + what);

        }

        Notification n = builder.build();

        //3、manager.notify()
        notificationManager.notify(NOTIFICATION_ID + what, n);
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            sendNotification(msg.what);
        }
    };


    public void notification(View v) {
        switch (v.getId()) {
            case R.id.notification1:
//                heansUp();
                mHandler.sendEmptyMessageDelayed(1, 1000);
                break;
            case R.id.notification2:
//                notify1();
                mHandler.sendEmptyMessageDelayed(2, 1000);
                break;
            case R.id.notification3:
//                btn_no_custom();
                mHandler.sendEmptyMessageDelayed(3, 1000);
                break;
            case R.id.cancel:
                notificationManager.cancelAll();
                break;
            case R.id.show_content:
                startActivity(new Intent(this, ShowContentActivity.class));
                break;
        }
    }

    private void btn_no_custom() {
        RemoteViews views = new RemoteViews(getPackageName(), R.layout.remote);
        NotificationCompat.Builder nb4 = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContent(views);
        views.setImageViewResource(R.id.iv_re, R.drawable.background);
        views.setTextViewText(R.id.tv_re, "文字文字");
        /**
         * 2.请求码
         * 3、意图Intent要执行的跳转动作
         * 4、int标识，用于设置PendingIntent对象创建的特点
         * 5、bundle对象，是否携带数据，可不写
         */
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                1,
                new Intent(this, NotificationActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT);
        //设置RemoveView中的点击事件
        //当前设置代表点击id为R.id.but_re的按钮时，要执行跳转到TwoActivity页面的操作
        views.setOnClickPendingIntent(R.id.iv_re, pendingIntent);
        notificationManager.notify(5, nb4.build());
    }

    private void btn_no_multi() {
        NotificationCompat.InboxStyle style = new NotificationCompat.InboxStyle();
        for (int i = 0; i < 10; i++) {
            style.addLine("这是第" + i + "行" + "文本");
        }
        style.setBigContentTitle("多行文本标题标题");

        NotificationCompat.Builder nb3 = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("多行文本")
                .setStyle(style)
                //设置声音和振动
                .setDefaults(Notification.DEFAULT_VIBRATE);
        ;
        notificationManager.notify(4, nb3.build());

    }

    private void btn_no_common() {

        //1、创建通知对象
        NotificationCompat.Builder nb = new NotificationCompat.Builder(this);
        //2、设置通知对象的各种信息
        //注意：以下6点信息的设置，必须要写小图标，其余的可选择性省略
        nb.setContentTitle("设置标题" + System.currentTimeMillis());
        //设置大图标
        nb.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));
        nb.setContentText("内容文本部分");
        //设置info信息，即设置显示在时间右下角的文字
        nb.setContentInfo("info信息");
        //必须要设置的小图标
        nb.setSmallIcon(R.drawable.ic_launcher);
        //设置通知时间
        nb.setWhen(System.currentTimeMillis());
        //设置声音和振动
        nb.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
        nb.setTicker("设置滚动提示的文字");
        //不能手动移除，模态，需要代码控制
        //nb.setOngoing(true);
        notificationManager.notify(121212, nb.build());   //发送通知

    }

    private void heansUp() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Toast.makeText(this, "此类通知在Android 5.0以上版本才会有横幅有效！", Toast.LENGTH_SHORT).show();
        }
        //为了版本兼容  选择V7包下的NotificationCompat进行构造
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("横幅通知");
        builder.setContentText("请在设置通知管理中开启消息横幅提醒权限");
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);

        builder.setSmallIcon(R.drawable.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.background));
        Intent intent = new Intent(this, MainActivity.class);
//        PendingIntent pIntent = PendingIntent.getActivity(this, 1, intent, 0);
//        builder.setContentIntent(pIntent);
//        builder.setFullScreenIntent(pIntent, true);
        builder.setAutoCancel(true);
        Notification notification = builder.build();
        notificationManager.notify(1, notification);//注意这里 1 为当前通知栏的 Id 号，和 Fragment 设置 Id 是一样的
    }

    private void notify1() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_x);
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher_x)//设置小图标
                .setLargeIcon(bitmap)
                .setContentTitle("这是标题")
                .setContentText("这是内容")
                .setCategory("msg")
                .setTicker("sss")
                .build();

        notificationManager.notify(121, notification);
    }

    private void notify2() {
        /*实例化NotificationManager以获取系统服务*/

        //点击的意图ACTION是跳转到Intent
        Intent resultIntent = new Intent(this, MainActivity.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(this)
                /*设置large icon*/
                .setLargeIcon(bitmap)
                /*设置small icon*/
                .setSmallIcon(R.drawable.ic_launcher)
                /*设置title*/
                .setContentTitle("通知")
                /*设置详细文本*/
                .setContentText("Hello world")
                /*设置发出通知的时间为发出通知时的系统时间*/
                .setWhen(System.currentTimeMillis())
                /*设置发出通知时在status bar进行提醒*/
                .setTicker("来自问月的祝福")
                /*setOngoing(boolean)设为true,notification将无法通过左右滑动的方式清除
                 * 可用于添加常驻通知，必须调用cancle方法来清除
                 */
                .setOngoing(true)
                /*设置点击后通知消失*/
                .setAutoCancel(true)
                /*设置通知数量的显示类似于QQ那种，用于同志的合并*/
                .setNumber(2)
                /*点击跳转到MainActivity*/
                .setContentIntent(pendingIntent);

        notificationManager.notify(121, notifyBuilder.build());
    }

    private void notify3() {

        /*获取bitmap*/
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(this)
                /*设置small icon*/
                .setSmallIcon(R.drawable.ic_launcher)
                /*设置title*/
                .setContentTitle("通知")
                /*设置详细文本*/
                .setContentText("Hello world")
                .setWhen(System.currentTimeMillis())
                .setOngoing(true)
                .setNumber(2);

        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
        bigPictureStyle.bigPicture(bitmap);
        notifyBuilder.setStyle(bigPictureStyle);

        notificationManager.notify(121, notifyBuilder.build());
    }

}
