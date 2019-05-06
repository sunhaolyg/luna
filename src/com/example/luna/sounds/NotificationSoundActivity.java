package com.example.luna.sounds;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.example.luna.R;
import com.example.luna.base.BaseActivity;


/**
 * Created by sunhao on 17-11-13.
 */

public class NotificationSoundActivity extends BaseActivity {

    private SoundPoolUtil sp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_sound);
        sp.init(this);
    }

    /*<!-- mSoundPlayer.load(mContext, R.raw.arrow, 1);// 1
    mSoundPlayer.load(mContext, R.raw.bell_tree, 1);// 2
    mSoundPlayer.load(mContext, R.raw.bongo, 1);// 3
    mSoundPlayer.load(mContext, R.raw.car_lock, 1);// 4
    mSoundPlayer.load(mContext, R.raw.cave, 1);// 5
    mSoundPlayer.load(mContext, R.raw.chess, 1);// 6
    mSoundPlayer.load(mContext, R.raw.crystal_drop, 1);// 7
    mSoundPlayer.load(mContext, R.raw.cuckoo, 1);// 8
    mSoundPlayer.load(mContext, R.raw.doorbell, 1);// 9
    mSoundPlayer.load(mContext, R.raw.drip, 1);// 10
    mSoundPlayer.load(mContext, R.raw.echo, 1);// 11-->*/
    public void notificationSound(View v) {
        switch (v.getId()) {
            case R.id.arrow:
                sp.play(1);
                break;
            case R.id.bell_tree:
                sp.play(2);
                break;
            case R.id.bongo:
                sp.play(3);
                break;
            case R.id.car_lock:
                sp.play(4);
                break;
            case R.id.cave:
                sp.play(5);
                break;
            case R.id.chess:
                sp.play(6);
                break;
            case R.id.crystal_drop:
                sp.play(7);
                break;
            case R.id.cuckoo:
                sp.play(8);
                break;
            case R.id.doorbell:
                sp.play(9);
                break;
            case R.id.drip:
                sp.play(10);
                break;
            case R.id.echo:
                sp.play(11);
                break;
            case R.id.tejat:
                sp.play(12);
                break;
            default:
                break;
        }
    }

}
