package com.meida.cosmeticsshopuser.rongim;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.base.BaseActivity;

/**
 * ConversationListActivity:
 *
 * @author Administrator-LFC
 * @date 2018-12-1 18:07:22
 */
public class ConversationListActivity extends BaseActivity {

    /**
     * 快捷跳转本页面
     */
    public static void EnterThis(Context ctx) {
        Intent intent = new Intent(ctx, ConversationListActivity.class);
        //        intent.putExtra("", "");
        ctx.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_list);
        initView();
        getData();
    }

    private void initView() {

    }

    private void getData() {

    }
}
