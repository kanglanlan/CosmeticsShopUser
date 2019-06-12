package com.meida.cosmeticsshopuser.adapter.viewpager;

import android.content.Context;
import android.widget.TextView;

import com.meida.cosmeticsshopuser.Bean.CityBean;
import com.meida.cosmeticsshopuser.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者 亢兰兰
 * 时间 2018/10/11 0011
 * 公司  郑州软盟
 */

public class HotCityAdapter extends CommonAdapter<CityBean.Area> {

    private List<CityBean.Area> datas = new ArrayList<>();
    Context mContext;

    public HotCityAdapter(Context context, int layoutId, List<CityBean.Area> datas) {
        super(context, layoutId, datas);
        this.datas = datas;
        mContext = context;
    }

    @Override
    public void convert(final ViewHolder holder, final CityBean.Area bean, int position) {
        TextView hotCity = holder.getView(R.id.hotCity);
        hotCity.setText(bean.getName());
    }
}