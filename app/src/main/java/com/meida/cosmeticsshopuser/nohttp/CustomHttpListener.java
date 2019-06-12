package com.meida.cosmeticsshopuser.nohttp;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.meida.cosmeticsshopuser.MyView.MUIToast;
import com.meida.cosmeticsshopuser.utils.PreferencesUtils;
import com.meida.cosmeticsshopuser.utils.ProjectUtils;
import com.yolanda.nohttp.rest.Response;

import org.json.JSONObject;


public abstract class CustomHttpListener<T> implements HttpListener<String> {

    private JSONObject object;
    private Context context;
    private boolean isGson;
    private Class<T> dataM;

    public CustomHttpListener(Context context, boolean isGson, Class<T> dataM) {
        this.context = context;
        this.isGson = isGson;
        this.dataM = dataM;
    }

    private boolean showToast = true;
    public CustomHttpListener(Context context, boolean isGson, boolean showToast,Class<T> dataM) {
        this.context = context;
        this.isGson = isGson;
        this.dataM = dataM;
        this.showToast = showToast;
    }

    @Override
    public void onSucceed(int what, Response<String> response) {
        Log.e("onSucceed", "请求成功：\n" + response.get());

        if (!response.get().matches("^\\{(.+:.+,*){1,}\\}$")) {
            return;
        }
        try {
            object = new JSONObject(response.get());
            try {
                if ("10001".equals(object.getString("code"))) {
                    ProjectUtils.doLocalExit(context);
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (dataM == null && "0".equals(object.getString("code"))) {
                if (showToast){
                    Toast.makeText(context, object.getString("msg"), Toast.LENGTH_SHORT).show();
                }
                return;
            }
            if ("1".equals(object.getString("code"))) {
                if (isGson && dataM != null) {
                    Gson gson = new Gson();
                    doWork(gson.fromJson(object.toString(), dataM), object.getString("code"));
                } else {
                    doWork((T) object, object.getString("code"));
                }
            } else {
                if (!"暂无数据".equals(object.getString("msg"))) {
                    if (showToast){
                        MUIToast.show(context,object.getString("msg"));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (!isGson && dataM == null && !"0".equals(object.getString("code"))) {
                    if (showToast){
                        Toast.makeText(context, object.getString("msg"), Toast.LENGTH_SHORT).show();
                    }
                }
                onFinally(object, object.getString("code"), true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public abstract void doWork(T result, String code);

    public void onFinally(JSONObject obj, String code, boolean isNetSucceed) {
    } // 解析完成，如要执行操作，可重写该方法。

    @Override
    public void onFailed(int what, Response<String> response) {
        Log.i("onFailed", "请求失败：\n" + response.get());

        // Toast.makeText(context, "网络请求数据失败", Toast.LENGTH_SHORT).show();

        onFinally(new JSONObject(), "-1", false); // JSON数据请求失败

    }







}
