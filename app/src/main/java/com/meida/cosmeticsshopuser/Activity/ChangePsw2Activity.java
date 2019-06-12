package com.meida.cosmeticsshopuser.Activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
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

public class ChangePsw2Activity extends BaseActivity {

    @Bind(R.id.et_psw)
    EditText etPsw;
    @Bind(R.id.img_reg_eye)
    ToggleButton imgRegEye;
    @Bind(R.id.et_psw2)
    EditText etPsw2;
    @Bind(R.id.img_reg_eye2)
    ToggleButton imgRegEye2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_psw2);
        ButterKnife.bind(this);
        changeTitle("输入密码");

        imgRegEye.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //如果选中，显示密码
                    etPsw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    etPsw.setSelection(etPsw.getText().toString().trim().length());
                } else {
                    //否则隐藏密码
                    etPsw.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    etPsw.setSelection(etPsw.getText().toString().trim().length());
                }
            }
        });

        imgRegEye2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //如果选中，显示密码
                    etPsw2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    etPsw2.setSelection(etPsw2.getText().toString().trim().length());
                } else {
                    //否则隐藏密码
                    etPsw2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    etPsw2.setSelection(etPsw2.getText().toString().trim().length());
                }
            }
        });

    }

    @OnClick(R.id.bt_psdmima)
    public void onViewClicked() {
        doSetPwd();
    }

    /*忘记密码*/
    private void doSetPwd(){
        String pwdStr = etPsw.getText().toString().trim();
        String pwd2Str = etPsw2.getText().toString().trim();
        if (TextUtils.isEmpty(pwdStr)){
            showtoa("请输入密码");
            return;
        }

        if (pwdStr.length()<6 || pwdStr.length()>20){
            showtoa("请输入6-20位数字或字母密码");
            return;
        }

        if (!pwdStr.equals(pwd2Str)){
            showtoa("两次输入密码不一致");
            return;
        }

        mRequest01 = getRequest(Params.resetPwd, true);
        mRequest01.add("password", pwdStr);
        mRequest01.add("confirm_password", pwd2Str);
        CallServer.getRequestInstance().add(baseContext, 0, mRequest01,
                new CustomHttpListener<BaseBean>(baseContext, true, BaseBean.class) {
                    @Override
                    public void doWork(BaseBean data, String code) {
                        finish();
                    }
                }, false, true);


    }



}
