package com.example.luna.sounds;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Toast;
import com.example.luna.R;
import com.example.luna.base.BaseActivity;
import com.example.luna.base.MainActivity;


/**
 * Created by sunhao on 17-11-13.
 */

public class SoundActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound);
//        notify1();
        heansUp();
    }

    private void notify1(){
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)//设置小图标
                .setContentTitle("这是标题")
                .setContentText("这是内容")
                .setCategory("msg")
                .setTicker("sss")
                .build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(121, notification);
    }

    private void heansUp(){
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Toast.makeText(this, "此类通知在Android 5.0以上版本才会有横幅有效！", Toast.LENGTH_SHORT).show();
        }
        //为了版本兼容  选择V7包下的NotificationCompat进行构造
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("横幅通知");
        builder.setContentText("请在设置通知管理中开启消息横幅提醒权限");
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);

        builder.setSmallIcon(R.drawable.usb);
//        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.usb));
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 1, intent, 0);
        builder.setContentIntent(pIntent);
        builder.setFullScreenIntent(pIntent, true);
        builder.setAutoCancel(true);
        Notification notification = builder.build();
        notificationManager.notify(1, notification);//注意这里 1 为当前通知栏的 Id 号，和 Fragment 设置 Id 是一样的
    }

    public void sound(View v){
        switch (v.getId()){
            case R.id.alarms:
                startActivity(new Intent(this,SoundAlarmsActivty.class));
                break;
            case R.id.animationsounds:
                startActivity(new Intent(this,AnimationSoundsActivity.class));

                break;
            case R.id.notifications:
                startActivity(new Intent(this,NotificationSoundActivity.class));

                break;
            case R.id.ringtones:
                startActivity(new Intent(this,RingtoneSoundActivity.class));

                break;
            case R.id.ui:
                startActivity(new Intent(this,UISoundActivity.class));

                break;
            default:
                break;
        }
    }

}
