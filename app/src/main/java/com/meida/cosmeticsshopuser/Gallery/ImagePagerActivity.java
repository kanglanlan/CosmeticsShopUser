package com.meida.cosmeticsshopuser.Gallery;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.meida.cosmeticsshopuser.Activity.AddFindActivity;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.base.BaseActivity;
import com.meida.cosmeticsshopuser.utils.ImgDonwload;
import com.meida.cosmeticsshopuser.utils.MPermissionUtils;

import java.util.List;

public class ImagePagerActivity extends BaseActivity {

    private static final String STATE_POSITION = "STATE_POSITION";
    public static final String EXTRA_IMAGE_INDEX = "image_index";
    public static final String EXTRA_IMAGE_URLS = "image_urls";
    public static final String EXTRA_IMAGE_SAVE = "image_save";

    private HackyViewPager mPager;
    private int pagerPosition;
    private TextView indicator;
    private String[] urls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //无title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //此两段代码必须设置在setContentView()方法之前

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_image_pager);

        pagerPosition = getIntent().getIntExtra(EXTRA_IMAGE_INDEX, 0);
        urls = getIntent().getStringArrayExtra(EXTRA_IMAGE_URLS);
        boolean isSave = getIntent().getBooleanExtra(EXTRA_IMAGE_SAVE, false);

        if (pagerPosition >= urls.length)
            pagerPosition = urls.length - 1;

        TextView tv_save = (TextView) findViewById(R.id.tv_imagepager_save);
        ImageView img_save = (ImageView) findViewById(R.id.img_imagepager_save);
        indicator = (TextView) findViewById(R.id.tv_imagepager_indicator);
        mPager = (HackyViewPager) findViewById(R.id.hv_imagepager_img);
        ImagePagerAdapter mAdapter = new ImagePagerAdapter(getSupportFragmentManager(), urls);
        mPager.setAdapter(mAdapter);

        CharSequence text = getString(R.string.viewpager_indicator, 1, mPager.getAdapter().getCount());
        indicator.setText(text);
        tv_save.setVisibility(View.GONE);
        if (!isSave) {
//            tv_save.setVisibility(View.INVISIBLE);
            img_save.setVisibility(View.INVISIBLE);
        } else {
            img_save.setVisibility(View.VISIBLE);
        }
        img_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (MPermissionUtils.checkPermissionAllGranted(baseContext,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE})) {
                    SaveIMG();
                } else {
                    MPermissionUtils.requestPermissionsResult(baseContext, 1,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            new MPermissionUtils.OnPermissionListener() {
                                @Override
                                public void onPermissionGranted() {
                                    SaveIMG();
                                }

                                @Override
                                public void onPermissionDenied() {
                                    MPermissionUtils.showTipsDialog(baseContext);
                                }
                            });
                }
            }
        });

        // 更新下标
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrollStateChanged(int state) {
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                CharSequence text = getString(
                        R.string.viewpager_indicator,
                        position + 1,
                        mPager.getAdapter().getCount());
                indicator.setText(text);

                pagerPosition = position;
            }

        });
        if (savedInstanceState != null) {
            pagerPosition = savedInstanceState.getInt(STATE_POSITION);
        }

        mPager.setCurrentItem(pagerPosition);

    }



    private void SaveIMG() {
        ImgDonwload.donwloadImg(this, urls[pagerPosition]);


    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_POSITION, mPager.getCurrentItem());
    }


    /**
     * 权限回调接口
     */
    protected static final int RC_PERM = 123;
    protected static int reSting = R.string.ask_again;//默认提示语句

    private CheckPermListener mListener;

    public interface CheckPermListener {
        //权限通过后的回调方法
        void superPermission();
    }


    private class ImagePagerAdapter extends FragmentStatePagerAdapter {

        private String[] fileList;

        private ImagePagerAdapter(FragmentManager fm, String[] fileList) {
            super(fm);
            this.fileList = fileList;
        }

        @Override
        public int getCount() {
            return fileList == null ? 0 : fileList.length;
        }

        @Override
        public Fragment getItem(int position) {
            String url = fileList[position];
            return ImageDetailFragment.newInstance(url);
        }

    }

}
