package com.meida.cosmeticsshopuser.Activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.meida.cosmeticsshopuser.Bean.LoginBean;
import com.meida.cosmeticsshopuser.MyView.ClearEditText;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.base.BaseActivity;
import com.meida.cosmeticsshopuser.nohttp.CallServer;
import com.meida.cosmeticsshopuser.nohttp.CustomHttpListener;
import com.meida.cosmeticsshopuser.nohttp.Params;
import com.meida.cosmeticsshopuser.utils.MyCountDownTimer;
import com.meida.cosmeticsshopuser.utils.PreferencesUtils;

public class ModifyPhoneActivity extends BaseActivity {

    private ClearEditText edPhone,edCode;
    private TextView getCode;
    private Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_phone);
        changeTitle("更换手机号");
        initView();
        initEvent();
    }

    private void initView(){
        edPhone = (com.meida.cosmeticsshopuser.MyView.ClearEditText) findViewById(R.id.edPhone);
        edCode = (com.meida.cosmeticsshopuser.MyView.ClearEditText) findViewById(R.id.edCode);
        getCode = (TextView) findViewById(R.id.getCode);
        next = (Button) findViewById(R.id.next);

        TextView tip = (TextView) findViewById(R.id.tip);
        String clientPhone = PreferencesUtils.getString(baseContext,Params.CLIENT_PHONE);
        if (TextUtils.isEmpty(clientPhone)){
            tip.setText(R.string.tip_changePhone);
        }else{
            tip.setText(String.format("%s，客服电话%s", getResources().getString(R.string.tip_changePhone), clientPhone));
        }

    }


    private void initEvent(){
        getCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneStr = edPhone.getText().toString().trim();
                if (TextUtils.isEmpty(phoneStr) || phoneStr.length()!=11){
                    showtoa(R.string.formatPhone);
                    return;
                }
                if (phoneStr.length()!=11){
                    showtoa("请输入正确的手机号");
                    return;
                }
                netGetCode(phoneStr);

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneStr = edPhone.getText().toString().trim();
                if (TextUtils.isEmpty(phoneStr) || phoneStr.length()!=11){
                    showtoa(R.string.formatPhone);
                    return;
                }

                if (!msg_phone.equals(phoneStr)){
                    showtoa("请先获取验证码");
                    return;
                }

                String codeStr = edCode.getText().toString().trim();
                if (TextUtils.isEmpty(codeStr)){
                    showtoa("请输入验证码");
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

                netCheckCode(phoneStr,codeStr);

            }
        });



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
                        MyCountDownTimer timer = new MyCountDownTimer(baseContext,getCode,1000*60,1000);
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
                            StartActivity(ModifyPhone2Activity.class,phoneStr);
                            finish();
                        }else{
                            showtoa("验证码输入有误，请重新操作");
                        }

                    }
                },false,true);
    }



}
