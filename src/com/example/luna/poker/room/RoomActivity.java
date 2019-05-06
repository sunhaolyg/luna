package com.example.luna.poker.room;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.luna.R;
import com.example.luna.base.BaseActivity;
import com.example.luna.poker.middle.MiddleClient;
import com.example.luna.utils.Logutils;

import java.util.ArrayList;
import java.util.List;

public class RoomActivity extends BaseActivity {

    private Button room_one, room_two, room_three, room_four;
    private Button start, reset;
    private ViewGroup viewGroup;
    private boolean joinOrReady;

    private List<JoinMember> joinMembers = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        MiddleClient client = new MiddleClient(mContext);
        int room_id = getIntent().getIntExtra("room_id", 0);
        JoinMember member;
        room_one = findViewById(R.id.room_one);
        member = new JoinMember(room_one, 1, client, room_id);
        joinMembers.add(member);

        room_two = findViewById(R.id.room_two);
        member = new JoinMember(room_two, 2, client, room_id);
        joinMembers.add(member);

        room_three = findViewById(R.id.room_three);
        member = new JoinMember(room_three, 3, client, room_id);
        joinMembers.add(member);

        room_four = findViewById(R.id.room_four);
        member = new JoinMember(room_four, 4, client, room_id);
        joinMembers.add(member);
        start = findViewById(R.id.room_start);
        reset = findViewById(R.id.room_reset);
        viewGroup = findViewById(R.id.room_parent);
    }

    public void room(View v) {
        switch (v.getId()) {
            case R.id.room_one:
            case R.id.room_two:
            case R.id.room_three:
            case R.id.room_four:
                for (JoinMember member : joinMembers) {
                    if (member.equalSButton((Button) v)) {
                        member.callOnClick();
                    }
                }
                break;
            case R.id.room_start:
                List<Button> buttons = getButtons(viewGroup);
                Logutils.d("WaterFallLayout", buttons.size() + "");
                for (Button button : buttons) {
                    Logutils.d("WaterFallLayout", button.getText().toString());

                }
                break;
        }
    }

    private void applyClick(Button button) {
        String text = button.getText().toString();
        if (text.equals(getString(R.string.unready))) {
            button.setText(getString(R.string.ready));
        } else {
            button.setText(getString(R.string.unready));
        }
    }

    private List<Button> getButtons(View view) {
        List<Button> buttons = new ArrayList<>();
        if (view instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) view;
            int count = vg.getChildCount();
            for (int i = 0; i < count; i++) {
                View child = vg.getChildAt(i);
                if (child instanceof ViewGroup) {
                    buttons.addAll(getButtons(child));
                } else if (child instanceof Button) {
                    buttons.add((Button) child);
                }
            }
        }
        return buttons;
    }

}
