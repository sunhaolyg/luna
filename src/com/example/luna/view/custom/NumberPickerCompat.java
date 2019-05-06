package com.example.luna.view.custom;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.NumberPicker;
import com.example.luna.R;

import java.lang.reflect.Field;

/**
 * Subclass of NumberPicker that allows customizing divider color.
 */
public class NumberPickerCompat extends NumberPicker {

    private static Field sSelectionDivider;
    private static boolean sTrySelectionDivider = true;

    public NumberPickerCompat(Context context) {
        this(context, null /* attrs */);
    }

    public NumberPickerCompat(Context context, AttributeSet attrs) {
        super(context, attrs);
//        tintSelectionDivider(context);
    }

    public NumberPickerCompat(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        tintSelectionDivider(context);
    }

    @Override
    public void addView(View child) {
        super.addView(child);
        updateView(child);
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        super.addView(child, params);
        updateView(child);

    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        updateView(child);

    }

    private void updateView(View view){
        if (view instanceof EditText) {
            //这里修改显示字体的属性，主要修改颜色
            ((EditText) view).setTextColor(Color.parseColor("#48006d"));
            ((EditText) view).setTextSize(12);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setNumberPickerDividerColor(this);
    }

    public void setNumberPickerDividerColor(NumberPicker numberPicker) {
        NumberPicker picker = numberPicker;
        Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (Field pf : pickerFields) {
            if (pf.getName().equals("mSelectionDivider")) {
                pf.setAccessible(true);
                try {
                    //设置分割线的颜色值 透明
//                    pf.set(picker, new ColorDrawable(this.getResources().getColor(R.color.red)));
                    pf.set(picker, new ColorDrawable(Color.parseColor("#979797")));
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        // 分割线高度
        for (Field pf2 : pickerFields) {
            if (pf2.getName().equals("mSelectionDividerHeight")) {
                pf2.setAccessible(true);
                try {
                    int result = 3;
                    pf2.set(picker, result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void tintSelectionDivider(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP
                || Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            // Accent color in KK will stay system blue, so leave divider color matching.
            // The divider is correctly tinted to controlColorNormal in M.
            return;
        }

        if (sTrySelectionDivider) {
            final TypedArray a = context.obtainStyledAttributes(
                    new int[] { android.R.attr.colorControlNormal });
            // White is default color if colorControlNormal is not defined.
            final int color = a.getColor(0, Color.WHITE);
            a.recycle();

            try {
                if (sSelectionDivider == null) {
                    sSelectionDivider = NumberPicker.class.getDeclaredField("mSelectionDivider");
                    sSelectionDivider.setAccessible(true);
                }
                final Drawable selectionDivider = (Drawable) sSelectionDivider.get(this);
                if (selectionDivider != null) {
                    // setTint is API21+, but this will only be called in API21
                    selectionDivider.setTint(color);
                }
            } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
                sTrySelectionDivider = false;
            }
        }
    }

}