package com.example.luna.sounds;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.example.luna.R;
import com.example.luna.base.BaseActivity;

/**
 * Created by sunhao on 17-11-13.
 */

public class RingtoneSoundActivity extends BaseActivity {

    private SoundPool sp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ringtone_sound);
        sp = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
        sp.load(this, R.raw.living, 1);

    }

    public void ringtoneSound(View v) {
        switch (v.getId()) {
            case R.id.living:
                sp.play(1, 1, 1, 0, 0, 1);
                break;
        }
    }

}
