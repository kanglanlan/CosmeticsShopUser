package com.meida.cosmeticsshopuser.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;

import com.meida.cosmeticsshopuser.Bean.WebDetailBean;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.base.BaseActivity;
import com.meida.cosmeticsshopuser.nohttp.CallServer;
import com.meida.cosmeticsshopuser.nohttp.CustomHttpListener;
import com.meida.cosmeticsshopuser.nohttp.Params;
import com.meida.cosmeticsshopuser.utils.WebViewUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WebViewActivity extends BaseActivity {

     /*flag  文档标识  系统文档  1、隐私条款 2、关于我们 3、保证金说明
     4、开店教程 5、城市加盟 6、商务合作 7、关于我们  8、帮助中心 9防护功能*/

    @Bind(R.id.webview)
    WebView webview;

    private String flag = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);
        flag = getIntent().getStringExtra("id")+"";
        switch (flag) {
            case "1":
                changeTitle("隐私条款");
                break;
            case "2":
                changeTitle("关于我们");
                break;
            case "3":
                changeTitle("保证金说明");
                break;
            case "4":
                changeTitle("开店教程");
                break;
            case "5":
                changeTitle("城市加盟代理");
                break;
            case "6":
                changeTitle("商务合作");
                break;
            case "7":
                changeTitle("关于我们");
                break;
            case "8":
                changeTitle("帮助中心");
                break;
            case "9":
                changeTitle("防护功能");
                break;
            case "10":
                changeTitle("广告投放合作");
                break;
            default:
                String topTitle = getIntent().getStringExtra("topTitle");
                if (TextUtils.isEmpty(topTitle)){
                    changeTitle("详情");
                }else{
                    changeTitle(topTitle);
                }
                break;
        }

        getWebDetail();

        if ("9".equals(flag)){
            tv_right_title.setText("设置紧急联系人");
            tv_right_title.setVisibility(View.VISIBLE);
            tv_right_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(baseContext,ContactActivity.class);
                    intent.putExtra("isForOrder",false);
                    startActivity(intent);
                }
            });
        }

    }

    private void getWebDetail(){
        mRequest01 = getRequest(Params.getWebDetail,false);
        mRequest01.add("flag",flag);
        mRequest01.add("user_type","2");/*2用户端  3商家端*/
        CallServer.getRequestInstance().add(baseContext, 0, mRequest01,
                new CustomHttpListener<WebDetailBean>(baseContext,true,WebDetailBean.class) {
            @Override
            public void doWork(WebDetailBean data, String code) {
                try{
                    int index = getIntent().getIntExtra("index",0);
                    String html = data.getData().get(index).getContent();
                    WebViewUtil.setWebHtml(webview,html);
                }catch (Exception e){

                }
            }
        },false,true);
    }

}
