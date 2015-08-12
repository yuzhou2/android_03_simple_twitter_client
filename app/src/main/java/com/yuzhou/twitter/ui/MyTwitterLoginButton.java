package com.yuzhou.twitter.ui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.Button;

import com.yuzhou.twitter.R;

/**
 * Created by yuzhou on 2015/08/09.
 */
public class MyTwitterLoginButton extends Button
{
    public MyTwitterLoginButton(Context context)
    {
        super(context);
        setupButton();
    }

    public MyTwitterLoginButton(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        setupButton();
    }

    public MyTwitterLoginButton(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        setupButton();
    }

    private void setupButton()
    {
        Resources res = this.getResources();
        setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.tw__ic_logo_blue), null, null, null);
        setCompoundDrawablePadding(res.getDimensionPixelSize(com.twitter.sdk.android.core.R.dimen.tw__login_btn_drawable_padding));
        setText(com.twitter.sdk.android.core.R.string.tw__login_btn_txt);
        setTextColor(getResources().getColor(R.color.tw__blue_default));
        setTextSize(0, (float) res.getDimensionPixelSize(com.twitter.sdk.android.core.R.dimen.tw__login_btn_text_size));
        setTypeface(Typeface.DEFAULT_BOLD);
        setPadding(res.getDimensionPixelSize(com.twitter.sdk.android.core.R.dimen.tw__login_btn_left_padding), 0, res.getDimensionPixelSize(com.twitter.sdk.android.core.R.dimen.tw__login_btn_right_padding), 0);
        setBackgroundResource(R.drawable.blue_button);

        if(Build.VERSION.SDK_INT >= 21) {
            setAllCaps(false);
        }
    }

}
