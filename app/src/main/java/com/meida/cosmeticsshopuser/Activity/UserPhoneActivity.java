package com.meida.cosmeticsshopuser.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.meida.cosmeticsshopuser.Activity.ModifyPhoneActivity;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.base.BaseActivity;
import com.meida.cosmeticsshopuser.nohttp.Params;
import com.meida.cosmeticsshopuser.utils.PreferencesUtils;

public class UserPhoneActivity extends BaseActivity {

    private TextView currPhone;
    private Button change;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_phone);
        changeTitle("更换手机号");

        currPhone = (TextView) findViewById(R.id.currPhone);
        change = (Button) findViewById(R.id.change);

        String phoneStr = PreferencesUtils.getString(baseContext, Params.KEY_PHONE);
        currPhone.setText("当前手机号："+phoneStr);

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartActivity(ModifyPhoneActivity.class);
                finish();
            }
        });
    }



}
