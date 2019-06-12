package com.meida.cosmeticsshopuser.nohttp;


import android.app.Dialog;
import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.meida.cosmeticsshopuser.R;


/**
 * Created by Administrator on 2017/4/7.
 */
public class CustomDialog extends Dialog {



    public CustomDialog(Context context, int theme) {
        super(context, theme);
        this.setCancelable(false);
        this.setCanceledOnTouchOutside(false);

    }
    public CustomDialog(Context context) {
        super(context);

    }

    /**
     * 当窗口焦点改变时调用
     */
    public void onWindowFocusChanged(boolean hasFocus) {
        ImageView imageView = (ImageView) findViewById(R.id.spinnerImageView);
        // 获取ImageView上的动画背景
//        AnimationDrawable spinner = (AnimationDrawable) imageView.getBackground();
//        // 开始动画
//        spinner.start();
        Animation rotate = AnimationUtils.loadAnimation(getContext(),R.anim.spinner);
        LinearInterpolator interpolator = new LinearInterpolator();  //设置匀速旋转，在xml文件中设置会出现卡顿
        rotate.setInterpolator(interpolator);
        imageView.setAnimation(rotate);
        imageView.startAnimation(rotate);
    }
}

