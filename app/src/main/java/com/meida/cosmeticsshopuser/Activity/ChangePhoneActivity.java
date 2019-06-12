package com.meida.cosmeticsshopuser.Activity;

import android.os.Bundle;
import android.widget.TextView;

import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangePhoneActivity extends BaseActivity {

    @Bind(R.id.tv_phone)
    TextView tvPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_phone);
        ButterKnife.bind(this);
        changeTitle("更换手机号");
    }

    @OnClick(R.id.bt_changephone)
    public void onViewClicked() {
        StartActivity(ChangePhone2Activity.class);
        finish();
    }
}
