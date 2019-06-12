package com.meida.cosmeticsshopuser.MyView.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;

import com.meida.cosmeticsshopuser.R;

/**
 * @Description 选择上传图片方式的popupwindow
 */
public class HeadPopuWindow extends Dialog {
    Context context;//上下文

    public HeadPopuWindow(Context context, View view) {
        super(context, R.style.ActionSheetDialogStyle);
        this.context = context;
        this.setContentView(view);
        this.setCancelable(true);
        this.setCanceledOnTouchOutside(true);

        Window window = this.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.bottomDialog);
        window.setLayout(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
    }
}

