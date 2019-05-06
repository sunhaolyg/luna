package com.example.luna.number;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.TextView;
import com.example.luna.R;
import com.example.luna.base.BaseActivity;

public class NumberGameActivity extends BaseActivity {

    private GameParentView mParentView;
    private TextView mCountTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_game);
        mParentView = findViewById(R.id.game_parent);
        numberView();
        mCountTextView = findViewById(R.id.cout_num);

    }

    private GameParentView.OnCountListener mOnCountListener = new GameParentView.OnCountListener() {
        @Override
        public void onCount(int count) {
            mCountTextView.setText(count + "");
        }

        @Override
        public void onSuccess() {
            mCountTextView.append(",success");
        }
    };

    private void numberView() {
        LayoutInflater inflater = LayoutInflater.from(this);
        TextView textView = (TextView) inflater.inflate(R.layout.number_textview, null);
        textView.setBackgroundResource(R.drawable.empty_shape);
        mParentView.addChild(textView, new NumberEntity(0, textView));

        for (int i = 1; i < 20; i++) {
            TextView tv = (TextView) inflater.inflate(R.layout.number_textview, null);
            tv.setText((20 - i) + "");
            tv.setPadding(NumberConfig.NUMBER_ICON_WIDTH / 3, NumberConfig.NUMBER_ICON_HEIGHT / 3, 0, 0);
            mParentView.addChild(tv, new NumberEntity(i, tv));
        }

        TextView textView2 = (TextView) inflater.inflate(R.layout.number_textview, null);
        textView2.setBackgroundResource(R.drawable.number_shape);
        textView2.setText("1");
        mParentView.addChild(textView2, new NumberEntity(10,textView2));

        TextView textView1 = (TextView) inflater.inflate(R.layout.number_textview, null);
        textView1.setBackgroundResource(R.drawable.number_shape);
        textView1.setText("2");
        mParentView.addChild(textView1, new NumberEntity(11, textView1));

        mParentView.setOnCountListener(mOnCountListener);
    }
}
