package com.meida.cosmeticsshopuser.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ToggleButton;

import com.meida.cosmeticsshopuser.Bean.LoginBean;
import com.meida.cosmeticsshopuser.Bean.eventbus.UserInfoRefresh;
import com.meida.cosmeticsshopuser.MyView.ClearEditText;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.base.App;
import com.meida.cosmeticsshopuser.base.BaseActivity;
import com.meida.cosmeticsshopuser.nohttp.CallServer;
import com.meida.cosmeticsshopuser.nohttp.CustomHttpListener;
import com.meida.cosmeticsshopuser.nohttp.HttpIp;
import com.meida.cosmeticsshopuser.nohttp.Params;
import com.meida.cosmeticsshopuser.utils.LoggerUtil;
import com.meida.cosmeticsshopuser.utils.PreferencesUtils;
import com.meida.cosmeticsshopuser.utils.ProjectUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;

public class LoginActivity extends BaseActivity {

    @Bind(R.id.et_login_phone)
    ClearEditText etLoginPhone;
    @Bind(R.id.et_login_psw)
    EditText etLoginPsw;
    @Bind(R.id.img_login_eye)
    ToggleButton imgLoginEye;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        changeTitle("登录");
        imgLoginEye.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //如果选中，显示密码
                    etLoginPsw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    etLoginPsw.setSelection(etLoginPsw.getText().toString().trim().length());
                } else {
                    //否则隐藏密码
                    etLoginPsw.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    etLoginPsw.setSelection(etLoginPsw.getText().toString().trim().length());
                }
            }
        });

        String lastPhone = PreferencesUtils.getString(baseContext,Params.KEY_PHONE);
        String lastPwd = PreferencesUtils.getString(baseContext,Params.KEY_PWD);
        if (TextUtils.isEmpty(lastPhone) || TextUtils.isEmpty(lastPwd)){

        }else{
            etLoginPhone.setText(lastPhone);
            //etLoginPsw.setText(lastPwd);
            etLoginPhone.setSelection(lastPhone.length());
        }

    }

    @OnClick({R.id.bt_login, R.id.tv_zhuce, R.id.tv_wangjimima})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_login:
                doLogin();
                break;
            case R.id.tv_zhuce:
                StartActivity(RegisteredActivity.class);
                break;
            case R.id.tv_wangjimima:
                StartActivity(ForgotpasswordActivity.class);
                break;
        }
    }

    private void doLogin(){
        String phoneStr = etLoginPhone.getText().toString().trim();
        String pwdStr = etLoginPsw.getText().toString().trim();
        if (TextUtils.isEmpty(phoneStr)){
            showtoa("请输入手机号");
            return;
        }

        if (phoneStr.length()!=11){
            showtoa("请输入正确的手机号");
            return;
        }

        if (TextUtils.isEmpty(pwdStr)){
            showtoa("请输入密码");
            return;
        }

        netLogin(phoneStr,pwdStr);

    }

    private void netLogin(final String phoneStr, final String pwdStr) {
        mRequest01 = getRequest(Params.login, false);
        mRequest01.add("username", phoneStr);
        mRequest01.add("password", pwdStr);
        mRequest01.add("user_nickname", "");
        mRequest01.add("user_type", "2");
        mRequest01.add("isstudent", "");
        mRequest01.add("device_type", "mobile");
        CallServer.getRequestInstance().add(baseContext, 0, mRequest01,
                new CustomHttpListener<LoginBean>(baseContext, true, LoginBean.class) {
                    @Override
                    public void doWork(LoginBean data, String code) {
                        try{
                            PreferencesUtils.putString(baseContext,Params.UserID,data.getData().getUser().getId());
                            PreferencesUtils.putString(baseContext,Params.Token,data.getData().getToken());
                            PreferencesUtils.putString(baseContext,Params.KEY_PHONE,phoneStr);
                            PreferencesUtils.putString(baseContext,Params.KEY_PWD,pwdStr);
                            PreferencesUtils.putString(baseContext,Params.KEY_SEX,data.getData().getUser().getSex());
                            PreferencesUtils.putString(baseContext,Params.KEY_BIRTHDAY,data.getData().getUser().getBirthday());
                            PreferencesUtils.putString(baseContext,Params.KEY_NICKNAME,data.getData().getUser().getUser_nickname());
                            PreferencesUtils.putString(baseContext,Params.KEY_USERHEAD, HttpIp.BaseIp+data.getData().getUser().getAvatar());
                            PreferencesUtils.putString(baseContext,Params.KEY_USERSIGN,data.getData().getUser().getSignature());
                            PreferencesUtils.putString(baseContext,Params.RONG_TOKEN,data.getData().getUser().getRytoken());
                            ProjectUtils.setAlias(baseContext,data.getData().getUser().getUse_alias());
                            ProjectUtils.setTags(baseContext, data.getData().getUser().getUse_tag());

                            String user_id = data.getData().getUser().getId();
                            String nickname = data.getData().getUser().getUser_nickname();
                            String userhead = data.getData().getUser().getAvatar();
                            RongIM.getInstance().refreshUserInfoCache(new UserInfo(user_id, nickname,
                                    Uri.parse(userhead)));

                            /*是否参与过调查问卷 1 是*/
                            connectRongIM(data.getData().getUser().getExamine());

                        }catch (Exception e){

                        }
                    }
                }, false, true);

    }


    private void connectRongIM(final String hasExm){
        /*if (TextUtils.isEmpty(PreferencesUtils.getString(baseContext, Params.UserID))) {
            LoggerUtil.e("RONGIM","UN LOGIN");
            return ;
        }
        String userid = PreferencesUtils.getString(baseContext,Params.UserID)+"";
*/
       /* if (userid.equals(RongIM.getInstance().getCurrentUserId()) &&
                (RongIM.getInstance().getCurrentConnectionStatus() == RongIMClient.ConnectionStatusListener.ConnectionStatus.CONNECTED)) {
            LoggerUtil.e("RONGIM","HAS CONNECT");
            String user_id = PreferencesUtils.getString(baseContext,Params.UserID);
            String nickname = PreferencesUtils.getString(baseContext,Params.KEY_NICKNAME);
            String userhead = PreferencesUtils.getString(baseContext,Params.KEY_USERHEAD);
            RongIM.getInstance().refreshUserInfoCache(new UserInfo(user_id, nickname,
                    Uri.parse(userhead)));
            return;
        }*/
        String token = PreferencesUtils.getString(baseContext,Params.RONG_TOKEN);
        LoggerUtil.e("rong_token",token);
        if (getApplicationInfo().packageName.equals(App.getCurProcessName(getApplicationContext()))) {

            RongIM.connect(token, new RongIMClient.ConnectCallback() {

                /**
                 * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
                 *                  2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
                 */
                @Override
                public void onTokenIncorrect() {

                }

                /**
                 * 连接融云成功
                 * @param userid 当前 token 对应的用户 id
                 */
                @Override
                public void onSuccess(String userid) {
                    LoggerUtil.e("LoginActivity", "--onSuccess" + userid);
                    if ("1".equals(hasExm)){

                    }else{
                        Intent intent = new Intent(baseContext,QuestionPaperActivity.class);
                        startActivity(intent);
                    }
                    EventBus.getDefault().post(new UserInfoRefresh());
                    finish();

                }

                /**
                 * 连接融云失败
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {

                }
            });
        }
    }




}
