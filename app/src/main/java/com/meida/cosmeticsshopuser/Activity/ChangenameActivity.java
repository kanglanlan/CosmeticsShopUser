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

public class ChangenameActivity extends BaseActivity {

    @Bind(R.id.tv_title_right)
    TextView tvTitleRight;
    @Bind(R.id.et_nicheng)
    EditText etNicheng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changename);
        ButterKnife.bind(this);
        changeTitle("昵称设置");

    }

    @OnClick({R.id.tv_title_right, R.id.img_del})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_title_right:
                break;
            case R.id.img_del:
                break;
        }
    }
}
