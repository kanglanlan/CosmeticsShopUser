package com.meida.cosmeticsshopuser.MyView.dialog;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.meida.cosmeticsshopuser.Activity.AddFindActivity;
import com.meida.cosmeticsshopuser.Bean.VersionBean;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.utils.Download_NohttpUtils;
import com.meida.cosmeticsshopuser.utils.MPermissionUtils;
import com.meida.cosmeticsshopuser.utils.WebViewUtil;

/**
 * Created by Administrator on 2019/1/14 0014.
 */

public class UpdateVersionDialog extends Dialog{

    private VersionBean.DataBean versionBean;
    private Context context;
    private Activity activity;

    public UpdateVersionDialog(@NonNull final Context context, final VersionBean.DataBean versionBean, final Activity activity) {
        super(context);
        this.versionBean = versionBean;
        this.context = context;
        this.activity = activity;

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_update_version,null);
        WebView webView = view.findViewById(R.id.webView);
        TextView tip = view.findViewById(R.id.tip);
        View cancel =  view.findViewById(R.id.cancel);
        View update = view.findViewById(R.id.update);

        tip.setText("发现新版本 V"+versionBean.getVersion_number());
        webView.getSettings().setTextSize(WebSettings.TextSize.SMALLER);
        webView.loadDataWithBaseURL(null,versionBean.getContent(),"text/html","utf-8",null);

        boolean isForce;
        if ("1".equals(versionBean.getIs_force())){
            cancel.setVisibility(View.GONE);/*强制*/
            isForce = true;
        }else{
            cancel.setVisibility(View.VISIBLE);
            isForce = false;
        }

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        final boolean b = isForce;
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                MPermissionUtils.requestPermissionsResult(activity, 0x88,
                        permissions, new MPermissionUtils.OnPermissionListener() {
                            @Override
                            public void onPermissionGranted() {
                                new Download_NohttpUtils().downMyfile(versionBean.getUrl(), activity, b);
                            }

                            @Override
                            public void onPermissionDenied() {
                                MPermissionUtils.showTipsDialog(context);
                            }
                        });

                dismiss();
            }
        });


        this.setContentView(view);
        this.setCancelable(false);
        this.setCanceledOnTouchOutside(false);

        Window window = this.getWindow();
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawableResource(R.color.transparent);
        window.setWindowAnimations(R.style.ActionSheetDialogStyle);
        window.setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

    }





}
