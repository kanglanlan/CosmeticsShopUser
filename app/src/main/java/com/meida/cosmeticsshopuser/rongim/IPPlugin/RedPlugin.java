package com.meida.cosmeticsshopuser.rongim.IPPlugin;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;


import com.meida.cosmeticsshopuser.R;

import io.rong.imkit.RongExtension;
import io.rong.imkit.plugin.IPluginModule;
import io.rong.imkit.plugin.IPluginRequestPermissionResultCallback;

/**
 * Created by LFC
 * on 2018/12/3.
 */

public class RedPlugin implements IPluginModule, IPluginRequestPermissionResultCallback {

    private Context ctx;

    @Override
    public Drawable obtainDrawable(Context context) {
        this.ctx = context;
        return context.getResources().getDrawable(R.mipmap.img_redplugin);
    }

    @Override
    public String obtainTitle(Context context) {
        this.ctx = context;

        return "红包";
    }

    @Override
    public void onClick(Fragment fragment, RongExtension rongExtension) {

        /*红包*/

    }

    @Override
    public void onActivityResult(int i, int i1, Intent intent) {

    }

    @Override
    public boolean onRequestPermissionResult(Fragment fragment, RongExtension rongExtension, int i, @NonNull String[] strings, @NonNull int[] ints) {
        return false;
    }
}
