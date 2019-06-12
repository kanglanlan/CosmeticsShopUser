package com.meida.cosmeticsshopuser.rongim;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.base.BaseActivity;


/**
 * SubConversationListActivtiy:
 *
 * @author Administrator-LFC
 * @date 2018-12-1 8:54:10
 */
public class SubConversationListActivtiy extends BaseActivity {

    /**
     * 快捷跳转本页面
     */
    public static void EnterThis(Context ctx) {
        Intent intent = new Intent(ctx, SubConversationListActivtiy.class);
        //        intent.putExtra("", "");
        ctx.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_conversation_list_activtiy);
        initView();
        getData();
    }

    private void initView() {

    }

    private void getData() {

    }
}
