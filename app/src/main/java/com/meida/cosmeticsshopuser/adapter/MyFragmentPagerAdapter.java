package com.meida.cosmeticsshopuser.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.meida.cosmeticsshopuser.Bean.FindTypeBean;

import java.util.ArrayList;
import java.util.List;

/*
* FragmentStatePagerAdapter 和 FragmentPagerAdapter
* */
public class MyFragmentPagerAdapter extends FragmentStatePagerAdapter {
    List<FindTypeBean.TypeBean> titles;
    ArrayList<Fragment> fragments;
    public MyFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> list,List<FindTypeBean.TypeBean> ftitles) {
        super(fm);
        this.fragments = list;
        this.titles = ftitles;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public Fragment getItem(int arg0) {
        return fragments.get(arg0);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position).getTitle();
    }


}
