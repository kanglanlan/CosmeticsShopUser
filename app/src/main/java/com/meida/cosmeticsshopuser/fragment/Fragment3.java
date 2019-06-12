package com.meida.cosmeticsshopuser.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.meida.cosmeticsshopuser.Activity.MainActivity;
import com.meida.cosmeticsshopuser.Bean.FindTypeBean;
import com.meida.cosmeticsshopuser.base.BaseFragment;
import com.meida.cosmeticsshopuser.Activity.AddFindActivity;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.nohttp.CallServer;
import com.meida.cosmeticsshopuser.nohttp.CustomHttpListener;
import com.meida.cosmeticsshopuser.nohttp.Params;
import com.meida.cosmeticsshopuser.utils.ProjectUtils;

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
public class Fragment3 extends BaseFragment implements RadioGroup.OnCheckedChangeListener {


    @Bind(R.id.rb_guanzhu)
    RadioButton rbGuanzhu;
    @Bind(R.id.rb_faxian)
    RadioButton rbFaxian;
    @Bind(R.id.rb_fujin)
    RadioButton rbFujin;
    @Bind(R.id.rg_faxian)
    RadioGroup rgFaxian;

    Fragmentfaxianlist listfragment;
    Fragmentfaxian faxianfragment;
    Fragmentfaxian1 fujinfragment;

    public static Fragment3 instantiation() {
        Fragment3 fragment = new Fragment3();
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
        View view = inflater.inflate(R.layout.fragment3, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rgFaxian.setOnCheckedChangeListener(this);
        rbFaxian.performClick();
        loadType();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (rgFaxian.getCheckedRadioButtonId()) {
            case R.id.rb_guanzhu:
               setSelect(0);
                break;
            case R.id.rb_faxian:
                setSelect(1);
                break;
            case R.id.rb_fujin:
                setSelect(2);
                break;
        }
    }


    @OnClick({R.id.img_faxiansearch, R.id.tv_fabu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_faxiansearch:

                break;
            case R.id.tv_fabu:
                if (needLogin()){
                    return;
                }
                Intent intent = new Intent(getContext(),AddFindActivity.class);
                intent.putExtra("typeData",typeData);
                startActivity(intent);
                break;
        }
    }

    public void setSelect(int i){
        FragmentManager manager = getChildFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        hideFragment(transaction);//先隐藏所有界面
        switch (i) {
            case 0:
                if (listfragment == null) {
                    listfragment = new Fragmentfaxianlist("1", "");
                    transaction.add(R.id.fl_faxian_fragment, listfragment);
                } else {
                    transaction.show(listfragment);
                }
                break;
            case 1:
                if (faxianfragment == null) {
                    faxianfragment = Fragmentfaxian.newInstance("2",typeData);
                    transaction.add(R.id.fl_faxian_fragment, faxianfragment);
                } else {
                    transaction.show(faxianfragment);
                }
                break;
            case 2:
                if (fujinfragment == null) {
                    fujinfragment = Fragmentfaxian1.newInstance("3",typeData);
                    transaction.add(R.id.fl_faxian_fragment, fujinfragment);
                } else {
                    transaction.show(fujinfragment);
                }
                break;
            default:
                break;
        }
        transaction.commitAllowingStateLoss();
    }

    public void hideFragment(FragmentTransaction transaction){
        if (listfragment!=null){
            transaction.hide(listfragment);
        }
        if (faxianfragment!=null){
            transaction.hide(faxianfragment);
        }
        if (fujinfragment!=null){
            transaction.hide(fujinfragment);
        }
    }

    /*获取分享类别*/
    private ArrayList<FindTypeBean.TypeBean> typeData = new ArrayList<>();
    private void loadType(){
        mRequest02 = getRequest(Params.getFindType,true);
        CallServer.getRequestInstance().add(getContext(), 0, mRequest02,
                new CustomHttpListener<FindTypeBean>(getContext(),true,FindTypeBean.class) {
                    @Override
                    public void doWork(FindTypeBean result, String code) {
                        try{
                            List<FindTypeBean.TypeBean> beans = result.getData();
                            if (beans!=null && beans.size()>0){
                                typeData.clear();
                                typeData.addAll(beans);
                                if (faxianfragment!=null){
                                    faxianfragment.setTypeData(typeData);
                                }
                                if (fujinfragment!=null){
                                    fujinfragment.setTypeData(typeData);
                                }
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                },false,false);

    }



}
