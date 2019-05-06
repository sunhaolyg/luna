package com.example.luna.bezier;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import com.example.luna.R;
import com.example.luna.base.BaseActivity;


/**
 * Created by sunhao on 17-10-18.
 */

public class Bezier3Activity extends BaseActivity {

    private Bezier3View view;
    private Button first, second;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bezier3);
        view = (Bezier3View) findViewById(R.id.bezier3);
        first = (Button) findViewById(R.id.first);
        second = (Button) findViewById(R.id.second);
        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.changeControllPoint(Bezier3View.POINT_CONTROL_FIRST);
            }
        });
        second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.changeControllPoint(Bezier3View.POINT_CONTROL_SECOND);
            }
        });
    }
}
