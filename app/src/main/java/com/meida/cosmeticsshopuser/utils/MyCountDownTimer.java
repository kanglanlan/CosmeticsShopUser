package com.meida.cosmeticsshopuser.utils;

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/11/23.
 */

public class MyCountDownTimer extends CountDownTimer {
    private Context context;
    private TextView textView;

    public MyCountDownTimer(Context context, TextView textView, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.textView = textView;
        this.context = context;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        textView.setClickable(false);
        textView.setText(millisUntilFinished / 1000 + "s");
    }

    @Override
    public void onFinish() {
        textView.setText("重新获取");
        textView.setClickable(true);
    }
}
