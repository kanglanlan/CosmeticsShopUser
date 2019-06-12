package com.meida.cosmeticsshopuser.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.request.RequestOptions;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.base.BaseActivity;
import com.meida.cosmeticsshopuser.utils.GlideUtils;
import com.meida.cosmeticsshopuser.utils.ProjectUtils;

import java.util.ArrayList;
import java.util.List;

public class SingleImgActivity extends BaseActivity {

    private String pic = "";
    private String topTitle = "";

    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_img);

        img = (ImageView) findViewById(R.id.img);
        pic = getIntent().getStringExtra("pic");
        topTitle = getIntent().getStringExtra("topTitle");
        changeTitle(topTitle);
        int pd = R.mipmap.moren;
        RequestOptions options = new RequestOptions();
        options.error(pd);
        options.placeholder(pd);
        options.centerInside();
        GlideUtils.loadImg(pic,img,options);
        final List<String> list = new ArrayList<>();
        list.add(pic);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ProjectUtils.ShowLargeImg(baseContext,list,0);
            }
        });


    }



}
