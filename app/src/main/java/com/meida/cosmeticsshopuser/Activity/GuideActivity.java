package com.meida.cosmeticsshopuser.Activity;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.meida.cosmeticsshopuser.Bean.BannerItemBean;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.base.BaseActivity;
import com.meida.cosmeticsshopuser.nohttp.Params;
import com.meida.cosmeticsshopuser.utils.GlideUtils;
import com.meida.cosmeticsshopuser.utils.PreferencesUtils;
import com.meida.cosmeticsshopuser.utils.ProjectUtils;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;

public class GuideActivity extends BaseActivity {

    private BGABanner banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        banner = (BGABanner) findViewById(R.id.banner);

        PreferencesUtils.putBoolean(baseContext, Params.KEY_HAS_SHOW_GUIDE,true);

        final List<Integer> imgIds = new ArrayList<>();
        imgIds.add(R.mipmap.guide1);
        imgIds.add(R.mipmap.guide2);
        imgIds.add(R.mipmap.guide3);
        imgIds.add(R.mipmap.guide4);
        imgIds.add(R.mipmap.guide5);
        imgIds.add(R.mipmap.guide6);
        banner.setAdapter(new BGABanner.Adapter() {
            @Override
            public void fillBannerItem(BGABanner banner, View itemView, @Nullable Object model, int position) {
                ImageView imageView = itemView.findViewById(R.id.img);
                int resourceId = (int)model;
                imageView.setImageResource(resourceId);
                View enter = itemView.findViewById(R.id.enter);
                if (position==(imgIds.size()-1)){
                    enter.setVisibility(View.VISIBLE);
                }else{
                    enter.setVisibility(View.GONE);
                }
                enter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        StartActivity(MainActivity.class);
                        finish();
                    }
                });
            }
        });

        banner.setData(R.layout.layout_guide_img,imgIds,null);

    }



}
