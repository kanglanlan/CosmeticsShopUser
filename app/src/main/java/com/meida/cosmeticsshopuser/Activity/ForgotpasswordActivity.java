package com.meida.cosmeticsshopuser.Activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.meida.cosmeticsshopuser.Bean.LoginBean;
import com.meida.cosmeticsshopuser.MyView.ClearEditText;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.base.BaseActivity;
import com.meida.cosmeticsshopuser.nohttp.CallServer;
import com.meida.cosmeticsshopuser.nohttp.CustomHttpListener;
import com.meida.cosmeticsshopuser.nohttp.Params;
import com.meida.cosmeticsshopuser.utils.MyCountDownTimer;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgotpasswordActivity extends BaseActivity {

    @Bind(R.id.et_psd_phone)
    ClearEditText etPsdPhone;
    @Bind(R.id.et_psd_yzm)
    EditText etPsdYzm;
    @Bind(R.id.tv_psd_yzm)
    TextView tvPsdYzm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);
        ButterKnife.bind(this);
        changeTitle("忘记密码");

    }

    @OnClick({R.id.tv_psd_yzm, R.id.bt_psdnext})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_psd_yzm:
                doGetCode();
                break;
            case R.id.bt_psdnext:
                doCheckCode();
                break;
        }
    }

    private void doGetCode(){
        String phoneStr = etPsdPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phoneStr)){
            showtoa("请输入手机号");
            return;
        }
        if (phoneStr.length()!=11){
            showtoa("请输入正确的手机号");
            return;
        }
        netGetCode(phoneStr);

    }

    private void doCheckCode(){
        String phoneStr = etPsdPhone.getText().toString().trim();
        String codeStr = etPsdYzm.getText().toString().trim();
        if (TextUtils.isEmpty(phoneStr)){
            showtoa("请输入手机号");
            return;
        }

        if (!msg_phone.equals(phoneStr)){
            showtoa("请先获取验证码");
            return;
        }

        if (phoneStr.length()!=11){
            showtoa("请输入正确的手机号");
            return;
        }
        if (TextUtils.isEmpty(msg_id)){
            showtoa("请先获取验证码");
            return;
        }
        if (TextUtils.isEmpty(codeStr)){
            showtoa("请输入验证码");
            return;
        }
        netCheckCode(phoneStr,codeStr);

    }

    private String msg_id = "";
    private String msg_phone = "";
    private void netGetCode(final String phoneStr) {
        mRequest01 = getRequest(Params.sendCode, false);
        mRequest01.add("phone", phoneStr);
        mRequest01.add("user_type", "2");
        CallServer.getRequestInstance().add(baseContext, 0, mRequest01,
                new CustomHttpListener<LoginBean>(baseContext, true, LoginBean.class) {
                    @Override
                    public void doWork(LoginBean data, String code) {
                        showtoa("验证码已发送成功，请注意查收");
                        msg_id = data.getData().getMsg_id();
                        msg_phone = phoneStr;
                        MyCountDownTimer timer = new MyCountDownTimer(baseContext,tvPsdYzm,1000*60,1000);
                        timer.start();
                    }
                }, false, true);

    }

    private void netCheckCode(final String phoneStr, String codeStr) {
        mRequest02 = getRequest(Params.checkCode, false);
        mRequest02.add("msg_id",msg_id);
        mRequest02.add("code",codeStr);
        mRequest02.add("user_type", "2");
        CallServer.getRequestInstance().add(baseContext, 0, mRequest02,
                new CustomHttpListener<LoginBean>(baseContext,true,LoginBean.class) {
                    @Override
                    public void doWork(LoginBean data, String code) {
                        if (data.getData().isIs_valid()){
                            StartActivity(Forgotpassword2Activity.class,phoneStr);
                            finish();
                        }else{
                            showtoa("验证码输入有误，请重新操作");
                        }

                    }
                },false,true);
    }


}
