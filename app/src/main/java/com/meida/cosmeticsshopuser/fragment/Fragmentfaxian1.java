package com.meida.cosmeticsshopuser.fragment;

import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.PopupWindow;

import com.meida.cosmeticsshopuser.Bean.FindTypeBean;
import com.meida.cosmeticsshopuser.adapter.FlowLayoutManager;
import com.meida.cosmeticsshopuser.adapter.MyFragmentPagerAdapter;
import com.meida.cosmeticsshopuser.adapter.RadioLabelAdapter;
import com.meida.cosmeticsshopuser.adapter.SpaceItemDecoration;
import com.meida.cosmeticsshopuser.base.BaseFragment;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者 亢兰兰
 * 时间 2018/2/24 0024
 * 公司  郑州软盟
 */
@SuppressLint("ValidFragment")
public class Fragmentfaxian1 extends BaseFragment {

    MyFragmentPagerAdapter pagerAdapter;
    String type;
    @Bind(R.id.topView)
    View topView;
    @Bind(R.id.tablayout_mo)
    TabLayout tablayoutMo;
    @Bind(R.id.viewpager1)
    ViewPager viewpager;
    private List<Fragment> mList_Fragments = new ArrayList<>();

    /*筛选弹窗*/
    private PopupWindow filterPop;
    private RecyclerView labelRecycler;
    private List<FindTypeBean.TypeBean> labelData = new ArrayList<>();
    private RadioLabelAdapter labelAdapter;

    public static Fragmentfaxian1 newInstance(String type,ArrayList<FindTypeBean.TypeBean> typeData){
        Bundle args = new Bundle();
        args.putString("type",type);
        args.putSerializable("typeData",typeData);
        Fragmentfaxian1 fragment = new Fragmentfaxian1();
        fragment.setArguments(args);
        return fragment;
    }

    //调用这个方法切换时不会释放掉Fragment
    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (this.getView() != null)
            this.getView().setVisibility(menuVisible ? View.VISIBLE : View.GONE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_faxian1, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            type = bundle.getString("type");
            ArrayList<FindTypeBean.TypeBean> typeData = (ArrayList<FindTypeBean.TypeBean>)bundle.getSerializable("typeData");
            labelData.clear();
            if (typeData!=null && typeData.size()>0){
                labelData.addAll(typeData);
            }

        }

        if (labelData!=null && labelData.size()>0){
            setData(labelData);
        }else{
            tablayoutMo.setTabMode(TabLayout.MODE_SCROLLABLE);
            tablayoutMo.removeAllTabs();
            pagerAdapter = new MyFragmentPagerAdapter(getActivity().getSupportFragmentManager(),
                    (ArrayList<Fragment>) mList_Fragments,labelData);
            viewpager.setAdapter(pagerAdapter);
            tablayoutMo.setupWithViewPager(viewpager);
        }
    }

    /*创建页面时 数据 还有传送 过来*/
    public void setTypeData(ArrayList<FindTypeBean.TypeBean> typeData){
        Log.e("111111111111111","3333333333333333333333333333");
        if (typeData!=null && typeData.size()>0){
            if (labelData!=null && labelData.size()>0){

            }else{
                labelData.clear();
                labelData.addAll(typeData);
                setData(labelData);
            }
        }



    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.img_xz)
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.img_xz:
                safeShowFilterPop();
                break;
        }
    }

    private void safeShowFilterPop(){
        if (filterPop!=null && (!filterPop.isShowing()) ){
            labelAdapter.setSelectIndex(viewpager.getCurrentItem());
            filterPop.showAsDropDown(topView);
        }
    }

    private void safeCloseFilterPop(){
        if (filterPop!=null && (filterPop.isShowing()) ){
            filterPop.dismiss();
        }
    }

    private void initFilterPop(){
        filterPop = new PopupWindow(getContext());
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_filter,null);
        dialogView.findViewById(R.id.dismissView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                safeCloseFilterPop();
            }
        });
        dialogView.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                safeCloseFilterPop();
            }
        });
        labelRecycler = dialogView.findViewById(R.id.recycler);
        labelRecycler.setLayoutManager(new FlowLayoutManager());
        labelRecycler.addItemDecoration(new SpaceItemDecoration
                (DisplayUtil.dp2px( 0)
                        , DisplayUtil.dp2px( 0)
                        , DisplayUtil.dp2px( 0)
                        , DisplayUtil.dp2px( 0)));
        labelAdapter = new RadioLabelAdapter(getContext(),R.layout.item_label,labelData);
        labelAdapter.setListener(new RadioLabelAdapter.OnSelectChangeListener() {
            @Override
            public void onSelectChange(int position) {
                viewpager.setCurrentItem(position);
                safeCloseFilterPop();
            }
        });
        labelAdapter.setSelectIndex(0);
        labelRecycler.setAdapter(labelAdapter);
        setRecyclerMaxHeight(labelRecycler,200);
        filterPop.setContentView(dialogView);
        filterPop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        filterPop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        filterPop.setBackgroundDrawable(new ColorDrawable(0x0000));
        filterPop.setFocusable(true);
        filterPop.setTouchable(true);
        filterPop.setOutsideTouchable(true);

    }


    private void setRecyclerMaxHeight(final RecyclerView recyclerview, final int maxHeight){
        recyclerview.getViewTreeObserver().addOnGlobalLayoutListener
                (new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        ViewGroup.LayoutParams params = (ViewGroup.LayoutParams)
                                recyclerview.getLayoutParams();
                        recyclerview.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        if (maxHeight==-2){
                            params.height = -2;
                        }else{
                            if (recyclerview.getHeight() > DisplayUtil.dp2px(maxHeight)) {
                                params.height = DisplayUtil.dp2px(maxHeight);
                            } else {
                                if (recyclerview.getHeight()<DisplayUtil.dp2px(maxHeight)){
                                    params.height = -2;
                                }else{
                                    params.height = recyclerview.getHeight();
                                }
                            }
                        }
                        recyclerview.setLayoutParams(params);
                    }
                });
    }


    public void setData(List<FindTypeBean.TypeBean> data){
        tablayoutMo.removeAllTabs();
        tablayoutMo.setTabMode(TabLayout.MODE_SCROLLABLE);
        for (int i = 0;i<data.size(); i++){
            tablayoutMo.addTab(tablayoutMo.newTab().setText(data.get(i).getTitle()));
            tablayoutMo.setTag(data.get(i).getId());
        }
        mList_Fragments.clear();
        for (int i = 0; i <data.size() ; i++) {
            Fragmentfaxianlist fragmentfaxianlist=new Fragmentfaxianlist(type,data.get(i).getId());
            mList_Fragments.add(fragmentfaxianlist);
        }
        pagerAdapter = new MyFragmentPagerAdapter(getActivity().getSupportFragmentManager(),
                (ArrayList<Fragment>) mList_Fragments,data);
        viewpager.setAdapter(pagerAdapter);
        tablayoutMo.setupWithViewPager(viewpager);
        initFilterPop();


    }



}
