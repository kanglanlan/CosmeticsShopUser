package com.meida.cosmeticsshopuser.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.base.App;
import com.meida.cosmeticsshopuser.base.BaseActivity;
import com.meida.cosmeticsshopuser.nohttp.Params;
import com.meida.cosmeticsshopuser.utils.ProjectUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PayOkActivity extends BaseActivity {

    @Bind(R.id.tv_tishi)
    TextView tvTishi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_ok);
        ButterKnife.bind(this);
        changeTitle("支付成功");
    }

    @OnClick({R.id.img_title_back,R.id.tv_order, R.id.tv_home})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_title_back:
                ProjectUtils.backToHome();
                break;
            case R.id.tv_order:
                if (TextUtils.isEmpty(Params.orderid)){
                    ProjectUtils.backToHome();
                }else{
                    Intent intent = new Intent(baseContext,MyOrderActivity.class);
                    //intent.putExtra("id", Params.orderid);
                    startActivity(intent);
                    App.finishAllActivity();
                }
                break;
            case R.id.tv_home:
                ProjectUtils.backToHome();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        ProjectUtils.backToHome();
    }

}
