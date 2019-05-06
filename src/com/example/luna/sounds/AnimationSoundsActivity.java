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

public class AnimationSoundsActivity extends BaseActivity {

    private SoundPool soundPool;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_sound);
        soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
        soundPool.load(this, R.raw.boot_sound, 1);
    }

    public void animationSound(View v) {
        switch (v.getId()) {
            case R.id.boot_sound:
                soundPool.play(1, 1, 1, 0, 0, 1);
                break;
            default:
                break;
        }
    }

}
