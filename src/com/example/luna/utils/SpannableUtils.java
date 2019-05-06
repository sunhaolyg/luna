package com.example.luna.utils;

import android.content.Context;
import android.content.Intent;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;
import com.example.luna.R;
import com.example.luna.privision.AgreementActivity;
import com.example.luna.privision.AgreementDetailActivity;

public class SpannableUtils {

    public static ClickableSpan getClickableSpan(final int type, final Context context) {
        return getClickableSpan(type, context, 0);
    }

    public static ClickableSpan getClickableSpan(final int type, final Context context, int color) {
        return new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent1 = new Intent(context, AgreementDetailActivity.class);
                intent1.putExtra(AgreementDetailActivity.POLICY_KEY, type);
                context.startActivity(intent1);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                if (color == 0) {
                    ds.setColor(context.getResources().getColor(R.color.switch_accent_color)); //设置颜色
                } else {
                    ds.setColor(color);
                }
            }
        };
    }

    public static void setSpannable(TextView tv, String source, ClickableSpan span) {
        SpannableString str = new SpannableString(source);
        str.setSpan(span, 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.append(str);
        tv.setMovementMethod(LinkMovementMethod.getInstance());
    }

}
