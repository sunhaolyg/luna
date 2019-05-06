package com.example.luna.base;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.example.luna.R;
import com.example.luna.activity.*;
import com.example.luna.activity.blur.BlurActivity;
import com.example.luna.activity.interfaces.InterfaceActivity;
import com.example.luna.activity.panel.PanelActivity;
import com.example.luna.activity.view.ViewActivity;
import com.example.luna.bonus.BonusActivity;
import com.example.luna.click.ClickActivity;
import com.example.luna.hanoi.HanoiActivity;
import com.example.luna.java.JavaActivity;
import com.example.luna.kill.KillActivity;
import com.example.luna.leetcode.BeforeActivity;
import com.example.luna.markfilter.MarkFilterActivity;
import com.example.luna.music.MusicActivity;
import com.example.luna.note.NoteActivity;
import com.example.luna.notification.NotificationActivity;
import com.example.luna.number.NumberGameActivity;
import com.example.luna.poker.PokerActivity;
import com.example.luna.privision.AgreementActivity;
import com.example.luna.privision.ProvisionActivity;
import com.example.luna.r10.RegisterActivity;
import com.example.luna.ripple.RippleActivity;
import com.example.luna.sounds.SoundActivity;
import com.example.luna.sports.CirleSportsActivity;
import com.example.luna.thread.ThreadActivity;
import com.example.luna.time.TimeActivity;
import com.example.luna.utils.Logutils;
import com.example.luna.view.custom.ToggleSwitch;
import com.example.luna.wallpaper.WallPaperActivity;
import com.example.luna.write.WriteActivity;
import com.example.luna.xml.XMLActivity;

import java.util.List;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivityTAG";
    private ToggleSwitch mSwitchView;
    private final Configuration mCurConfig = new Configuration();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Button one = findViewById(R.id.one);
//        one.setText(DateUtil.getCurrentDay());
        Time time = new Time();
        time.set(System.currentTimeMillis());
        Logutils.d(TAG,""+time.month+",day = "+time.monthDay);
    }

    private void statusBar() {
        Window window = getWindow();
        //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        window.setStatusBarColor(getResources().getColor(R.color.gray));
        window.setNavigationBarColor(getResources().getColor(R.color.gray));

//        View decor = window.getDecorView();
//        int ui = decor.getSystemUiVisibility();
//        if (false) {
//            ui |=View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
//        } else {
//            ui &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
//        }
//        decor.setSystemUiVisibility(ui);
    }

    private boolean isThirdApp(String pkg) {
        PackageManager packageManager = getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> listAllApps = packageManager.queryIntentActivities(intent, 0);
        for (ResolveInfo appInfo : listAllApps) {
            String pkgName = appInfo.activityInfo.packageName;
            try {
                PackageInfo packageInfo = getPackageManager().getPackageInfo(pkgName, 0);
                if (pkg.equals(pkgName) && (packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {
                    return true;
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private void setStatusBarColor() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#ececec"));
    }


    public void buttonClick(View view) {
        switch (view.getId()) {
            case R.id.leetcode:
                startActivity(new Intent(this, BeforeActivity.class));
                break;
            case R.id.poker:
                startActivity(new Intent(this, PokerActivity.class));
                break;
            case R.id.hanoi:
                startActivity(new Intent(this, HanoiActivity.class));
                break;
            case R.id.kill:
                startActivity(new Intent(this, KillActivity.class));
                break;
            case R.id.thread:
                startActivity(new Intent(this, ThreadActivity.class));
                break;
            case R.id.wall_paper:
                startActivity(new Intent(this, WallPaperActivity.class));
                break;
            case R.id.xml:
                startActivity(new Intent(this, XMLActivity.class));
                break;
            case R.id.music:
                startActivity(new Intent(this, MusicActivity.class));
                break;
            case R.id.number_game:
                startActivity(new Intent(this, NumberGameActivity.class));
                break;
            case R.id.circlesport:
                startActivity(new Intent(this, CirleSportsActivity.class));
                break;
            case R.id.ripple:
                startActivity(new Intent(this, RippleActivity.class));
                break;
            case R.id.markfilter:
                startActivity(new Intent(this, MarkFilterActivity.class));
                break;
            case R.id.click:
                startActivity(new Intent(this, ClickActivity.class));
                break;
            case R.id.txt:
                startActivity(new Intent(this, AgreementActivity.class));
                break;
            case R.id.write:
                startActivity(new Intent(this, WriteActivity.class));
                break;
            case R.id.time:
                startActivity(new Intent(this, TimeActivity.class));
                break;
            case R.id.one:
                startActivity(new Intent(this, ViewActivity.class));
//                Intent intent =  new Intent("con.zhougb.test.second");
//                Intent intent =  new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS);
//                startActivity(intent);Intent.EXTRA_USER_HANDLE
//                Intent intent = new Intent();
//                intent.setAction(Intent.ACTION_USER_SWITCHED);
//                intent.putExtra(Intent.EXTRA_USER_HANDLE,1024);
//                sendBroadcast(intent);
//                startActivity(new Intent(android.provider.Settings.ACTION_DISPLAY_SETTINGS));
//                mSwitchView.setChecked(false);ACTION_INPUT_METHOD_SETTINGS
//                try{
                //                  mCurConfig.fontScale = 1.3f;
                //                 ActivityManagerNative.getDefault().updatePersistentConfiguration(mCurConfig);
//
                //              }catch (RemoteException e){
                //                e.printStackTrace();
                //          }
                break;
            case R.id.blur:
//                mSwitchView.setChecked(true);
                startActivity(new Intent(this, BlurActivity.class));
//                try{
//                    mCurConfig.fontScale = 1f;
//                    ActivityManagerNative.getDefault().updatePersistentConfiguration(mCurConfig);
//
//                }catch (RemoteException e){
//                    e.printStackTrace();
//                }
                break;
            case R.id.java:
                startActivity(new Intent(this, JavaActivity.class));
                break;
            case R.id.note:
                startActivity(new Intent(this, NoteActivity.class));
                break;
            case R.id.bonus:
                startActivity(new Intent(this, BonusActivity.class));
                break;
            case R.id.sound:
                startActivity(new Intent(this, SoundActivity.class));
                break;
            case R.id.layout:
                startActivity(new Intent(this, LayoutActivity.class));
                break;
            case R.id.dialog:
                startActivity(new Intent(this, DialogActivity.class));
                break;
            case R.id.storage:
                startActivity(new Intent(this, StorageActivity.class));
                break;
            case R.id.notification:
                startActivity(new Intent(this, NotificationActivity.class));
                break;
            case R.id.bitmap:
                startActivity(new Intent(this, BitmapActivity.class));
                break;
            case R.id.panel:
                startActivity(new Intent(this, PanelActivity.class));
                break;
            case R.id.interfaces:
                startActivity(new Intent(this, InterfaceActivity.class));
                break;
            case R.id.r10:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.provision:
                startActivity(new Intent(this, ProvisionActivity.class));
                break;
            default:
                break;
        }
    }

    private void calculate() {
        int a = 70000;
        int sum = 0;
        for (int i = 0; a > 0; i++) {
            a -= 4655;
            sum += a * 0.0005 * 30;
            Logutils.d("SwitchView", "i=" + i + ",a=" + a + ",sum=" + sum);
        }
    }

}
