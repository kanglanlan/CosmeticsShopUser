package com.meida.cosmeticsshopuser.Activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ToggleButton;

import com.meida.cosmeticsshopuser.Bean.ActionBean;
import com.meida.cosmeticsshopuser.Bean.BaseBean;
import com.meida.cosmeticsshopuser.Bean.LoginBean;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.base.BaseActivity;
import com.meida.cosmeticsshopuser.nohttp.CallServer;
import com.meida.cosmeticsshopuser.nohttp.CustomHttpListener;
import com.meida.cosmeticsshopuser.nohttp.Params;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Forgotpassword2Activity extends BaseActivity {

    @Bind(R.id.et_reg_psw)
    EditText etRegPsw;
    @Bind(R.id.img_reg_eye)
    ToggleButton imgRegEye;
    @Bind(R.id.et_reg_psw2)
    EditText etRegPsw2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword2);
        ButterKnife.bind(this);
        changeTitle("忘记密码");
        imgRegEye.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //如果选中，显示密码
                    etRegPsw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    etRegPsw.setSelection(etRegPsw.getText().toString().trim().length());
                } else {
                    //否则隐藏密码
                    etRegPsw.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    etRegPsw.setSelection(etRegPsw.getText().toString().trim().length());
                }
            }
        });
    }

    @OnClick(R.id.bt_psdmima)
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.bt_psdmima:
                doSetPwd();
                break;
        }
    }


    /*忘记密码*/
    private void doSetPwd(){
        String pwdStr = etRegPsw.getText().toString().trim();
        String pwd2Str = etRegPsw2.getText().toString().trim();
        if (TextUtils.isEmpty(pwdStr)){
            showtoa("请输入密码");
            return;
        }

        if (pwdStr.length()<6 || pwdStr.length()>20){
            showtoa("密码长度需在6-20位");
            return;
        }

        if (!pwdStr.equals(pwd2Str)){
            showtoa("两次输入密码不一致");
            return;
        }

        mRequest01 = getRequest(Params.findPwd, false);
        mRequest01.add("username", getIntent().getStringExtra("id"));
        mRequest01.add("password", pwdStr);
        mRequest01.add("confirm_password", pwd2Str);
        CallServer.getRequestInstance().add(baseContext, 0, mRequest01,
                new CustomHttpListener<BaseBean>(baseContext, true, BaseBean.class) {
                    @Override
                    public void doWork(BaseBean data, String code) {
                        showtoa(data.getMsg());
                        finish();
                    }
                }, false, true);


    }



}
