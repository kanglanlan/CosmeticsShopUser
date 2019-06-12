package com.meida.cosmeticsshopuser.adapter;

import android.content.Context;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;

/**
 * 作者 亢兰兰
 * 时间 2018/10/11 0011
 * 公司  郑州软盟
 */

public class BrowsingshangpinAdapter extends CommonAdapter<String> {
    private ArrayList<String> datas = new ArrayList<String>();
    Context mContext;

    public BrowsingshangpinAdapter(Context context, int layoutId, ArrayList<String> datas) {
        super(context, layoutId, datas);
        this.datas = datas;
        mContext = context;
    }

    @Override
    public void convert(final ViewHolder holder, final String s, int position) {


    }
}