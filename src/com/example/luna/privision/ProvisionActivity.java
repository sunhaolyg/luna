package com.example.luna.privision;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.LocaleList;
import android.os.RemoteException;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import com.example.luna.base.BaseActivity;
import com.example.luna.R;

import java.lang.reflect.Method;
import java.util.Locale;

import com.android.internal.app.LocaleStore;
import com.android.internal.app.LocalePicker;
import com.android.internal.app.LocalePickerWithRegion;
import com.android.internal.app.LocalePickerWithRegion.LocaleSelectedListener;
import com.example.luna.utils.Logutils;
import android.app.ActivityManager;
import android.app.IActivityManager;

public class ProvisionActivity extends ProvisionBaseActivity implements LocaleSelectedListener {

    private Button provision_init, provision_jump;
    private FragmentTransaction fm;
    private LocalePickerWithRegion fragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provision);
        provision_init = findViewById(R.id.provision_init);
        provision_jump = findViewById(R.id.provision_jump);
        provision_jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startWifiActivity();
            }
        });
        provision_init.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                LocaleList old = LocalePicker.getLocales();
//                LocaleList news = new LocaleList(locale, old);
//                Logutils.d("RegisterCodeTAG", "old = " + old + ",locale = " + locale + ",news = " + news);
//                LocalePicker.updateLocales(news);
//                startSecureActivity();
                startWifiActivity();
//                startActivity(new Intent(ProvisionActivity.this,FingerPrintActivity.class));
//                startActivity(new Intent(ProvisionActivity.this,AgreementActivity.class));
            }
        });
        addLanguage();
    }

    private void startWifiActivity() {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.android.settings", "com.android.settings.wifi.WifiPickerActivity"));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("provision", true);
        startActivity(intent);

    }

    private void startSecureActivity() {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.android.settings", "com.android.settings.fingerprint.FingerprintEnrollIntroduction"));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("provision", true);
        startActivity(intent);

    }

    private void addLanguage() {
        fragment = LocalePickerWithRegion.createLanguagePicker(
                this, this, false);
//        fm = getFragmentManager().beginTransaction();
//
//        fm.replace(R.id.right_layout, fragment).
//                commit();

        getFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.right_layout, fragment)
                .addToBackStack("localeListEditor")
                .commit();
    }

    public void onLocaleSelected(LocaleStore.LocaleInfo locale) {
        Logutils.d("RegisterCodeTAG", "locale = " + locale);
//        07-02 09:44:18.168: D/RegisterCodeTAG(6327): locale = zh-Hans-MO
        LocaleList ll = new LocaleList(locale.getLocale());
        LocalePicker.updateLocales(ll);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        newConfig = getResources().getConfiguration();
    }

    private void setLanguage(LocaleList locales) {
        try {

            final IActivityManager am = ActivityManager.getService();
            final Configuration config = am.getConfiguration();

            config.setLocales(locales);
            config.userSetLocale = true;

            am.updatePersistentConfiguration(config);
        } catch (RemoteException e) {

        }
    }

    private void resetSetting() {
//        Settings.Global.putInt(getContentResolver(), Settings.Global.DEVICE_PROVISIONED, 0);
//        Settings.Secure.putInt(getContentResolver(), Settings.Secure.USER_SETUP_COMPLETE, 0);
    }

    private void updateLanguage(Locale locale) {
        try {
            Class classActivityManagerNative = Class.forName("android.app.ActivityManagerNative");
            Method getDefault = classActivityManagerNative.getDeclaredMethod("getDefault");
            // IActivityManager iActMag = ActivityManagerNative.getDefault();
            Object objIActivityManager = getDefault.invoke(classActivityManagerNative);

            Class classIActivityManager = Class.forName("android.app.IActivityManager");
            // Configuration config = iActMag.getConfiguration();
            Method getConfiguration = classIActivityManager.getDeclaredMethod("getConfiguration");
            Configuration config = (Configuration) getConfiguration.invoke(objIActivityManager);
            config.locale = locale;
            // 此处需要声明权限:android.permission.CHANGE_CONFIGURATION
            // 会重新调用 onCreate();
            Class[] clzParams = {Configuration.class};
            Method updateConfiguration = classIActivityManager.getDeclaredMethod("updateConfiguration", clzParams);
            // iActMag.updateConfiguration(config);
            updateConfiguration.invoke(objIActivityManager, config);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
