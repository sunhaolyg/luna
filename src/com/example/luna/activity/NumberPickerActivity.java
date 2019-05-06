package com.example.luna.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.luna.R;
import com.example.luna.base.BaseActivity;
import com.example.luna.utils.Logutils;
import com.example.luna.view.custom.ActionBarView;

import java.util.ArrayList;
import java.util.List;

public class NumberPickerActivity extends BaseActivity {

    private NumberPicker np2, np1;
    private ActionBarView mActionBarView;
    private TextView mRepeatTv;
    private Switch mVibrateSwitch;
    private TextView mClockName;
    private TextView mRingTonTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numberpicker);
        initActionBar();
        np2 = (NumberPicker) findViewById(R.id.np2);
        np2.setMaxValue(100);
        np2.setMinValue(0);
        np2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                Logutils.d("GuestResumeSessionReceiver", "newVal = " + newVal);
            }
        });

        np1 = (NumberPicker) findViewById(R.id.np1);
        np1.setMaxValue(100);
        np1.setMinValue(0);
        mRepeatTv = (TextView) findViewById(R.id.repeat_tv);
        mVibrateSwitch = (Switch) findViewById(R.id.vibrate_switch);
        mVibrateSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Logutils.d("GuestResumeSessionReceiver", "ischecked = " + isChecked);
            }
        });
        mClockName = (TextView) findViewById(R.id.name_tv);
        mRingTonTitle = (TextView)findViewById(R.id.rington_tv);
        getIntent().getParcelableExtra("alarm");
    }

    public void details(View v) {
        switch (v.getId()) {
            case R.id.repeat:
                showRepeatDialog();
                break;
            case R.id.rington:
                Logutils.d("GuestResumeSessionReceiver", "rington");
                Uri oldRingtone =  null ;
                final Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, oldRingtone);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_ALARM);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, false);
                startActivityForResult(intent, 1);
                break;
            case R.id.vibrate:
                mVibrateSwitch.setChecked(!mVibrateSwitch.isChecked());
                break;
            case R.id.name:
                showEditNameDialog();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 1:
                    Uri uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
                    Logutils.d("GuestResumeSessionReceiver","uri = "+uri);
                    final Ringtone ringTone = RingtoneManager.getRingtone(this, uri);
                    if (ringTone == null) {
                        return ;
                    }
                    String title = ringTone.getTitle(this);
                    Logutils.d("GuestResumeSessionReceiver","title = "+title);
                    mRingTonTitle.setText(title);
                    break;
                default:
            }
        }
    }

    private void showEditNameDialog() {
        final AlertDialog customizeDialog = new AlertDialog.Builder(this).create();
        final View dialogView = LayoutInflater.from(this)
                .inflate(R.layout.dialog_edit_name, null);
        customizeDialog.setView(dialogView);
        customizeDialog.show();
        final EditText et = (EditText) customizeDialog.findViewById(R.id.name_et);
        final Button cancel = (Button) customizeDialog.findViewById(R.id.cancel);
        final Button ensure = (Button) customizeDialog.findViewById(R.id.ensure);
        et.setText(mClockName.getText());
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Logutils.d("GuestResumeSessionReceiver", "ss = " + s.toString());
            }
        });
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == cancel) {
                    customizeDialog.dismiss();
                } else if (v == ensure) {
                    String result = et.getText().toString();
                    if (result == null || result.equals("")) {
                        return;
                    }
                    Logutils.d("GuestResumeSessionReceiver", "et = " + result);
                    customizeDialog.dismiss();
                    mClockName.setText(result);
                }
            }
        };
        cancel.setOnClickListener(clickListener);
        ensure.setOnClickListener(clickListener);
    }

    int select_repeat;

    private void showRepeatDialog() {
        final AlertDialog customizeDialog = new AlertDialog.Builder(this).create();
        final View dialogView = LayoutInflater.from(this)
                .inflate(R.layout.dialog_repeat, null);
        customizeDialog.setView(dialogView);
        customizeDialog.show();
        final View once = dialogView.findViewById(R.id.once);
        final View friday = dialogView.findViewById(R.id.friday);
        final View everyday = dialogView.findViewById(R.id.everyday);
        final View custom = dialogView.findViewById(R.id.custom);
        final ImageView once_iv = (ImageView) dialogView.findViewById(R.id.once_iv);
        final ImageView friday_iv = (ImageView) dialogView.findViewById(R.id.friday_iv);
        final ImageView everyday_iv = (ImageView) dialogView.findViewById(R.id.everyday_iv);
        final ImageView custom_iv = (ImageView) dialogView.findViewById(R.id.custom_iv);
        final Button cancel = (Button) dialogView.findViewById(R.id.cancel);
        if (select_repeat == 0) {
            once_iv.setImageResource(R.drawable.selected_circle);
        } else if (select_repeat == 1) {
            friday_iv.setImageResource(R.drawable.selected_circle);
        } else if (select_repeat == 2) {
            everyday_iv.setImageResource(R.drawable.selected_circle);
        } else {
            custom_iv.setImageResource(R.drawable.selected_circle);
        }
        View.OnClickListener click = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == once) {
                    once_iv.setImageResource(R.drawable.selected_circle);
                    select_repeat = 0;
                    mRepeatTv.setText(R.string.once);
                } else {
                    once_iv.setImageResource(R.drawable.circular);
                }
                if (v == friday) {
                    friday_iv.setImageResource(R.drawable.selected_circle);
                    select_repeat = 1;
                    mRepeatTv.setText(R.string.friday);
                } else {
                    friday_iv.setImageResource(R.drawable.circular);
                }
                if (v == everyday) {
                    everyday_iv.setImageResource(R.drawable.selected_circle);
                    select_repeat = 2;
                    mRepeatTv.setText(R.string.everyday);
                } else {
                    everyday_iv.setImageResource(R.drawable.circular);
                }
                customizeDialog.dismiss();
                if (v == custom) {
                    custom_iv.setImageResource(R.drawable.selected_circle);
                    showCustomDialog();
                    customizeDialog.dismiss();
                } else {
                    custom_iv.setImageResource(R.drawable.circular);
                }
                if (v == cancel) {
                    customizeDialog.dismiss();
                }
            }
        };
        once.setOnClickListener(click);
        friday.setOnClickListener(click);
        everyday.setOnClickListener(click);
        custom.setOnClickListener(click);
        cancel.setOnClickListener(click);
    }

    private void showCustomDialog() {
        final AlertDialog customizeDialog = new AlertDialog.Builder(this).create();
        final View dialogView = LayoutInflater.from(this)
                .inflate(R.layout.dialog_custom_clock, null);
        customizeDialog.setView(dialogView);
        customizeDialog.show();
        final View monday = customizeDialog.findViewById(R.id.monday);
        final View tuesday = customizeDialog.findViewById(R.id.tuesday);
        final View wednesday = customizeDialog.findViewById(R.id.wednesday);
        final View thursday = customizeDialog.findViewById(R.id.thursday);
        final View friday2 = customizeDialog.findViewById(R.id.friday2);
        final View saturday = customizeDialog.findViewById(R.id.saturday);
        final View sunday = customizeDialog.findViewById(R.id.sunday);
        final ImageView monday_iv = (ImageView) customizeDialog.findViewById(R.id.monday_iv);
        final ImageView tuesday_iv = (ImageView) customizeDialog.findViewById(R.id.tuesday_iv);
        final ImageView wednesday_iv = (ImageView) customizeDialog.findViewById(R.id.wednesday_iv);
        final ImageView thursday_iv = (ImageView) customizeDialog.findViewById(R.id.thursday_iv);
        final ImageView friday2_iv = (ImageView) customizeDialog.findViewById(R.id.friday2_iv);
        final ImageView saturday_iv = (ImageView) customizeDialog.findViewById(R.id.saturday_iv);
        final ImageView sunday_iv = (ImageView) customizeDialog.findViewById(R.id.sunday_iv);
        Button cancel = (Button) customizeDialog.findViewById(R.id.cancel);
        Button ensure = (Button) customizeDialog.findViewById(R.id.ensure);
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logutils.d("GuestResumeSessionReceiver", "tag = " + v.getTag());
                if (v.getTag() != null) {
                    ImageView imageView = (ImageView) ((ViewGroup) v).getChildAt(1);
                    if (v.getTag().equals("unchecked")) {
                        v.setTag("checked");
                        imageView.setImageResource(R.drawable.selected_circle);
                    } else {
                        v.setTag("unchecked");
                        imageView.setImageResource(R.drawable.circular);
                    }
                } else {
                    if (v.getId() == R.id.cancel) {
                        customizeDialog.dismiss();
                    } else if (v.getId() == R.id.ensure) {
                        List<Integer> selected = new ArrayList<>();
                        if (monday.getTag().equals("checked")) {
                            selected.add(1);
                        }
                        if (tuesday.getTag().equals("checked")) {
                            selected.add(2);
                        }
                        if (wednesday.getTag().equals("checked")) {
                            selected.add(3);
                        }
                        if (thursday.getTag().equals("checked")) {
                            selected.add(4);
                        }
                        if (friday2.getTag().equals("checked")) {
                            selected.add(5);
                        }
                        if (saturday.getTag().equals("checked")) {
                            selected.add(6);
                        }
                        if (sunday.getTag().equals("checked")) {
                            selected.add(7);
                        }
                        for (int s : selected) {
                            Logutils.d("GuestResumeSessionReceiver", "s = " + s);
                        }
                        if (selected.size() != 0) {
                            select_repeat = 3;
                            customizeDialog.dismiss();
                            mRepeatTv.setText(R.string.custom2);
                        }
                    }
                }
            }
        };
        monday.setOnClickListener(clickListener);
        tuesday.setOnClickListener(clickListener);
        wednesday.setOnClickListener(clickListener);
        thursday.setOnClickListener(clickListener);
        friday2.setOnClickListener(clickListener);
        saturday.setOnClickListener(clickListener);
        sunday.setOnClickListener(clickListener);
        cancel.setOnClickListener(clickListener);
        ensure.setOnClickListener(clickListener);
    }

    private void initActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.hide();
        mActionBarView = (ActionBarView) findViewById(R.id.custom_action_bar);
        mActionBarView.setView(R.drawable.sign_out, "修改", R.drawable.deter_mine);
        mActionBarView.setOnActionBarViewClickListener(new ActionBarView.OnActionBarViewClickListener() {
            @Override
            public void onLeftClick(View view) {
                Logutils.d("GuestResumeSessionReceiver", "onLeftClick = ");
            }

            @Override
            public void onCenterClick(View view) {
                Logutils.d("GuestResumeSessionReceiver", "onCenterClick = ");
            }

            @Override
            public void onRightClick(View view) {
                Logutils.d("GuestResumeSessionReceiver", "onRightClick = ");
//                Intent intent;
//                intent.putExtra("",)
                setResult(RESULT_OK);

            }
        });
    }
}
