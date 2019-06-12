package com.meida.cosmeticsshopuser.Activity;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.meida.cosmeticsshopuser.Bean.ActionBean;
import com.meida.cosmeticsshopuser.Bean.eventbus.UserInfoRefresh;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.base.BaseActivity;
import com.meida.cosmeticsshopuser.nohttp.CallServer;
import com.meida.cosmeticsshopuser.nohttp.CustomHttpListener;
import com.meida.cosmeticsshopuser.nohttp.Params;
import com.meida.cosmeticsshopuser.utils.PreferencesUtils;

import org.greenrobot.eventbus.EventBus;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;

public class ModifyNickNameActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_nick_name);
        changeTitle("修改昵称","确定");
        final EditText editText = (EditText) findViewById(R.id.edName);
        tv_right_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameStr = editText.getText().toString().trim();
                if (TextUtils.isEmpty(nameStr)){
                    showtoa("请输入您要修改的昵称");
                    return;
                }
                modifyUserInfo(nameStr);

            }
        });

    }


    /*修改用户基本信息*/
    private void modifyUserInfo(final String nameStr){
        mRequest01 = getRequest(Params.modifyUserInfo,true);
        mRequest01.add("user_nickname",nameStr);
        CallServer.getRequestInstance().add(baseContext, 0, mRequest01,
                new CustomHttpListener<ActionBean>(baseContext,true,ActionBean.class) {
                    @Override
                    public void doWork(ActionBean result, String code) {
                        try{
                            showtoa(result.getMsg());
                            finish();
                            PreferencesUtils.putString(baseContext,Params.KEY_NICKNAME,nameStr);
                            EventBus.getDefault().post(new UserInfoRefresh());

                        }catch (Exception e){

                        }
                    }
                },false,true);
    }


}
