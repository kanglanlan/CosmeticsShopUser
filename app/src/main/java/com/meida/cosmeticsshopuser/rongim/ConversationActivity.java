package com.meida.cosmeticsshopuser.rongim;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;


import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.base.BaseActivity;
import com.meida.cosmeticsshopuser.utils.SoftHideKeyBoardUtil;


import org.json.JSONObject;

/**
 * ConversationActivity:
 *
 * @author Administrator-LFC
 * @date 2018-12-1 9:00:20
 */
public class ConversationActivity extends BaseActivity {


    /**
     * 快捷跳转本页面
     */
    public static void EnterThis(Context ctx) {
        Intent intent = new Intent(ctx, ConversationActivity.class);
        //        intent.putExtra("", "");
        ctx.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        /*getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);*/
        SoftHideKeyBoardUtil.assistActivity(this);
        initView();
        getData();

    }

    private void initView() {

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            findViewById(R.id.baseStatus).setVisibility(View.GONE);
        }*/

        /*String str_id = getIntent().getData().getQueryParameter("targetid");
        String str_name = getIntent().getData().getQueryParameter("title");
        //changeTitle(str_name);
        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText(str_name);

        findViewById(R.id.img_title_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });*/

        changeTitle(getIntent().getData().getQueryParameter("title"));


    }

    private void getData() {

    }

    @Override
    protected void onResume() {
        super.onResume();

    }



}
