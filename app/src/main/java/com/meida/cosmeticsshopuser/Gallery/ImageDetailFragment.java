package com.meida.cosmeticsshopuser.Gallery;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.meida.cosmeticsshopuser.R;

import io.rong.photoview.PhotoView;
import io.rong.photoview.PhotoViewAttacher;

/**
 * Created by Administrator on 2019/1/9 0009.
 */

public class ImageDetailFragment extends Fragment {

    private String mImageUrl;
    private PhotoView mImageView;
    private ProgressBar progressBar;

    public static ImageDetailFragment newInstance(String imageUrl) {
        final ImageDetailFragment f = new ImageDetailFragment();

        final Bundle args = new Bundle();
        args.putString("url", imageUrl);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageUrl = getArguments() != null ? getArguments().getString("url") : null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_img_detail, container, false);
        mImageView = (PhotoView) v.findViewById(R.id.image);

        progressBar = (ProgressBar) v.findViewById(R.id.loading);
        progressBar.setVisibility(View.VISIBLE);

        mImageView.setOnSingleFlingListener(new PhotoViewAttacher.OnSingleFlingListener() {
            @Override
            public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                Log.e("OnSingleFling","setOnSingleFlingListener");
                //getActivity().finish();
                return false;
            }
        });

        mImageView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float v, float v1) {
                Log.e("onPhotoTap","onPhotoTap");
                getActivity().finish();
            }

            @Override
            public void onOutsidePhotoTap() {
                Log.e("onOutsidePhotoTap","onOutsidePhotoTap");
            }
        });

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("onClick","onClick");
            }
        });

        mImageView.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
            @Override
            public void onViewTap(View view, float v, float v1) {
                Log.e("onViewTap","onViewTap");
            }
        });


        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.moren)
                .error(R.mipmap.moren)
                .diskCacheStrategy(DiskCacheStrategy.ALL);// 缓存所有尺寸的图片;
        progressBar.setVisibility(View.VISIBLE);
//
        Glide.with(getActivity())
                .load(mImageUrl)
                .apply(options)
                .thumbnail(0.1f)//先加载原图大小的十分之一
                .addListener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        //在这里添加一些图片加载完成的操作
                        progressBar.setVisibility(View.GONE);
//                        mAttacher.update();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        //在这里添加一些图片加载完成的操作
                        progressBar.setVisibility(View.GONE);
//                        mAttacher.update();
                        return false;
                    }
                })
                .into(mImageView);

    }


}
