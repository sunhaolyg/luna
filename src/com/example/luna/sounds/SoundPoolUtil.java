package com.example.luna.sounds;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import com.example.luna.R;


/**
 * Created by sunhao on 17-11-13.
 */

public class SoundPoolUtil {

    public static SoundPool mSoundPlayer = new SoundPool(10,
            AudioManager.STREAM_SYSTEM, 5);
    public static SoundPoolUtil soundPlayUtils;
    static Context mContext;

    public static SoundPoolUtil init(Context context) {
        if (soundPlayUtils == null) {
            soundPlayUtils = new SoundPoolUtil();
        }

        // 初始化声音
        mContext = context;

        mSoundPlayer.load(mContext, R.raw.arrow, 1);// 1
        mSoundPlayer.load(mContext, R.raw.bell_tree, 1);// 2
        mSoundPlayer.load(mContext, R.raw.bongo, 1);// 3
        mSoundPlayer.load(mContext, R.raw.car_lock, 1);// 4
        mSoundPlayer.load(mContext, R.raw.cave, 1);// 5
        mSoundPlayer.load(mContext, R.raw.chess, 1);// 6
        mSoundPlayer.load(mContext, R.raw.crystal_drop, 1);// 7
        mSoundPlayer.load(mContext, R.raw.cuckoo, 1);// 8
        mSoundPlayer.load(mContext, R.raw.doorbell, 1);// 9
        mSoundPlayer.load(mContext, R.raw.drip, 1);// 10
        mSoundPlayer.load(mContext, R.raw.echo, 1);// 11
        mSoundPlayer.load(mContext, R.raw.tejat, 1);// 12

        return soundPlayUtils;
    }

    public static void play(int soundID) {
        mSoundPlayer.play(soundID, 1, 1, 0, 0, 1);
    }

}
