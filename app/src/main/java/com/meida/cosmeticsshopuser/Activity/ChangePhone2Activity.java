package com.meida.cosmeticsshopuser.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangePhone2Activity extends BaseActivity {

    @Bind(R.id.tv_kefu)
    TextView tvKefu;
    @Bind(R.id.et_changephone_phone)
    EditText etChangephonePhone;
    @Bind(R.id.et_changephone_yzm)
    EditText etChangephoneYzm;
    @Bind(R.id.tv_changephone_yzm)
    TextView tvChangephoneYzm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_phone2);
        ButterKnife.bind(this);
        changeTitle("更换手机号");
    }

    @OnClick({R.id.img_changephone_del, R.id.tv_changephone_yzm, R.id.bt_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_changephone_del:
                break;
            case R.id.tv_changephone_yzm:
                break;
            case R.id.bt_next:
                StartActivity(ChangePhone3Activity.class);
                finish();
                break;
        }
    }
}
