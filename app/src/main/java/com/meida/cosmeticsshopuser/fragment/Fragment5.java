package com.meida.cosmeticsshopuser.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.meida.cosmeticsshopuser.Activity.ContactActivity;
import com.meida.cosmeticsshopuser.Activity.MainActivity;
import com.meida.cosmeticsshopuser.Activity.ReturnGoodsActivity;
import com.meida.cosmeticsshopuser.Activity.ShopCartActivity;
import com.meida.cosmeticsshopuser.Activity.WebViewActivity;
import com.meida.cosmeticsshopuser.Bean.BaseBean;
import com.meida.cosmeticsshopuser.Bean.UserInfoBean;
import com.meida.cosmeticsshopuser.Bean.eventbus.UserInfoRefresh;
import com.meida.cosmeticsshopuser.MyView.CircleImageView;
import com.meida.cosmeticsshopuser.MyView.ObservableScrollView;
import com.meida.cosmeticsshopuser.MyView.dialog.ActionDialog;
import com.meida.cosmeticsshopuser.base.BaseActivity;
import com.meida.cosmeticsshopuser.base.BaseFragment;
import com.meida.cosmeticsshopuser.Activity.BrowsinghistoryActivity;
import com.meida.cosmeticsshopuser.Activity.CollectionGoodActivity;
import com.meida.cosmeticsshopuser.Activity.CollectionshopActivity;
import com.meida.cosmeticsshopuser.Activity.CouponsActivity;
import com.meida.cosmeticsshopuser.Activity.MyAddActivity;
import com.meida.cosmeticsshopuser.Activity.MyCommentActivity;
import com.meida.cosmeticsshopuser.Activity.MyOrderActivity;
import com.meida.cosmeticsshopuser.Activity.MyShareActivity;
import com.meida.cosmeticsshopuser.Activity.PersonalInfoActivity;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.Activity.SettingActivity;
import com.meida.cosmeticsshopuser.nohttp.CallServer;
import com.meida.cosmeticsshopuser.nohttp.CustomHttpListener;
import com.meida.cosmeticsshopuser.nohttp.Params;
import com.meida.cosmeticsshopuser.utils.DisplayUtil;
import com.meida.cosmeticsshopuser.utils.FormatterUtil;
import com.meida.cosmeticsshopuser.utils.GlideUtils;
import com.meida.cosmeticsshopuser.utils.PreferencesUtils;
import com.meida.cosmeticsshopuser.utils.ProjectUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;

@SuppressLint("ValidFragment")
public class Fragment5 extends BaseFragment {


    @Bind(R.id.scrollView)
    ObservableScrollView scrollView;
    @Bind(R.id.topView)
    View topView;
    @Bind(R.id.img_minephoto)
    CircleImageView imgMinephoto;
    @Bind(R.id.loginNow)
    TextView loginNow;
    @Bind(R.id.tv_nickname)
    TextView tv_nickname;
    @Bind(R.id.tv_phone)
    TextView tvPhone;
    @Bind(R.id.tv_num1)
    TextView tvNum1;
    @Bind(R.id.tv_num2)
    TextView tvNum2;
    @Bind(R.id.tv_num3)
    TextView tvNum3;
    @Bind(R.id.tv_num4)
    TextView tvNum4;
    @Bind(R.id.tv_num5)
    TextView tvNum5;
    @Bind(R.id.clientView)
    View clientView;
    @Bind(R.id.tv_client)
    TextView tv_client;
    @Bind(R.id.share_msg_num)
    TextView share_msg_num;

    public static Fragment5 instantiation() {
        Fragment5 fragment = new Fragment5();
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
        View view = inflater.inflate(R.layout.fragment5, container, false);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @OnClick({R.id.ll_mine, R.id.ll_lookall, R.id.ll_order1, R.id.ll_order2, R.id.ll_order3,
            R.id.ll_order4, R.id.ll_order5, R.id.tv_ss, R.id.tv_ssdp, R.id.tv_youhuiquan,
            R.id.view_fenxiang, R.id.tv_guanzhufenxiang, R.id.tv_lishi, R.id.tv_dizhi,
            R.id.tv_jiameng, R.id.tv_fahu, R.id.tv_setting,R.id.tv_guanggao,R.id.tv_returnGoods
            ,R.id.tv_shopCart,R.id.clientView})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_shopCart:
                if (needLogin()){
                    return;
                }
                StartActivity(ShopCartActivity.class);
                break;
            case R.id.tv_returnGoods:
                if (needLogin()){
                    return;
                }
                StartActivity(ReturnGoodsActivity.class);
                break;
            case R.id.ll_mine:
                if (needLogin()){
                    return;
                }
                StartActivity(PersonalInfoActivity.class);
                break;
            case R.id.ll_lookall:
                if (needLogin()){
                    return;
                }
                startActivity(new Intent(getContext(),MyOrderActivity.class).putExtra("pos",0));
                break;
            case R.id.ll_order1:
                if (needLogin()){
                    return;
                }
                startActivity(new Intent(getContext(),MyOrderActivity.class).putExtra("pos",1));
                break;
            case R.id.ll_order2:
                if (needLogin()){
                    return;
                }
                startActivity(new Intent(getContext(),MyOrderActivity.class).putExtra("pos",2));
                break;
            case R.id.ll_order3:
                if (needLogin()){
                    return;
                }
                startActivity(new Intent(getContext(),MyOrderActivity.class).putExtra("pos",3));
                break;
            case R.id.ll_order4:
                if (needLogin()){
                    return;
                }
                startActivity(new Intent(getContext(),MyOrderActivity.class).putExtra("pos",4));
                break;
            case R.id.ll_order5:
                if (needLogin()){
                    return;
                }
                StartActivity(MyCommentActivity.class);
                break;
            case R.id.tv_ss:
                if (needLogin()){
                    return;
                }
                StartActivity(CollectionGoodActivity.class);
                break;
            case R.id.tv_ssdp:
                if (needLogin()){
                    return;
                }
                StartActivity(CollectionshopActivity.class);
                break;
            case R.id.tv_youhuiquan:
                if (needLogin()){
                    return;
                }
                StartActivity(CouponsActivity.class);
                break;
            case R.id.view_fenxiang:
                if (needLogin()){
                    return;
                }
                ProjectUtils.clearFindMsgNum(getContext(),share_msg_num);
                ProjectUtils.clearFindMsgNum(getContext(), MainActivity.instance.tvFindMsgNum);
                StartActivity(MyShareActivity.class,0);
                break;
            case R.id.tv_guanzhufenxiang:
                if (needLogin()){
                    return;
                }
                StartActivity(MyShareActivity.class,1);
                break;
            case R.id.tv_lishi:
                if (needLogin()){
                    return;
                }
                StartActivity(BrowsinghistoryActivity.class);
                break;
            case R.id.tv_dizhi:
                if (needLogin()){
                    return;
                }
                StartActivity(MyAddActivity.class);
                break;
            case R.id.tv_jiameng:
                if (needLogin()){
                    return;
                }
                startActivity(new Intent(getContext(), WebViewActivity.class).putExtra("id","5"));
                break;
            case R.id.tv_guanggao:
                if (needLogin()){
                    return;
                }
                startActivity(new Intent(getContext(), WebViewActivity.class).putExtra("id","10"));
                break;
            case R.id.tv_fahu:
                if (needLogin()){
                    return;
                }
                startActivity(new Intent(getContext(), WebViewActivity.class).putExtra("id","9"));
                //StartActivity(ContactActivity.class);
                break;
            case R.id.tv_setting:
                if (needLogin()){
                    return;
                }
                StartActivity(SettingActivity.class);
                break;
            case R.id.clientView:
                contactClient();
                break;
        }
    }

    private void contactClient(){
        final String clientPhone = PreferencesUtils.getString(getContext(),Params.CLIENT_PHONE);
        ActionDialog actionDialog = new ActionDialog(getContext(),"客服电话",clientPhone,"取消","拨打");
        actionDialog.setOnActionClickListener(new ActionDialog.OnActionClickListener() {
            @Override
            public void onLeftClick() {

            }

            @Override
            public void onRightClick() {
                ProjectUtils.doCallTo(getActivity(),clientPhone);
            }
        });
        actionDialog.show();
    }

    private void initData(){
        try{
            if (TextUtils.isEmpty(PreferencesUtils.getString(getContext(), Params.UserID))){
                loginNow.setVisibility(View.VISIBLE);
                tv_nickname.setVisibility(View.GONE);
                tvPhone.setVisibility(View.GONE);
                return;
            }else{
                /*TODO debug*/
                //getUserInfo();
                String nicknameStr = PreferencesUtils.getString(getContext(),Params.KEY_NICKNAME);
                String phoneStr = PreferencesUtils.getString(getContext(),Params.KEY_PHONE);
                String headStr = PreferencesUtils.getString(getContext(),Params.KEY_USERHEAD);
                if (TextUtils.isEmpty(phoneStr)){
                    loginNow.setVisibility(View.VISIBLE);
                    tv_nickname.setVisibility(View.GONE);
                    tvPhone.setVisibility(View.GONE);
                    getUserInfo();
                }else{
                    loginNow.setVisibility(View.GONE);
                    tv_nickname.setVisibility(View.VISIBLE);
                    tvPhone.setVisibility(View.VISIBLE);
                    tv_nickname.setText(nicknameStr);
                    GlideUtils.loadHead(headStr,imgMinephoto);
                    FormatterUtil.formatPhone(phoneStr,tvPhone);
                }
            }
        }catch (Exception e){

        }

        final int move_distance = DisplayUtil.dp2px(40);
        scrollView.setOnScrollListener(new ObservableScrollView.ScrollViewListener() {
            @Override
            public void onScroll(int oldy, int dy, boolean isUp) {
                if (!isUp && dy > move_distance) {
                    topView.setVisibility(View.VISIBLE);
                } else if (isUp && dy <= move_distance) {
                    topView.setVisibility(View.GONE);
                }
            }
        });

       /* String clientPhone = PreferencesUtils.getString(getContext(),Params.CLIENT_PHONE);
        tv_client.setText(clientPhone);*/

    }

    @Override
    public void onResume() {
        super.onResume();
        getUserInfo();
        String clientPhone = PreferencesUtils.getString(getContext(),Params.CLIENT_PHONE);
        tv_client.setText(clientPhone);
        ProjectUtils.updateFindMsgNum(getContext(),share_msg_num);
        ProjectUtils.updateFindMsgNum(getContext(),MainActivity.instance.tvFindMsgNum);
    }

    private void getUserInfo() {
        if (TextUtils.isEmpty(PreferencesUtils.getString(getContext(), Params.UserID))){
            return;
        }
        mRequest01 = getRequest(Params.getUserInfo, true);
        CallServer.getRequestInstance().add(getContext(), 0, mRequest01,
                new CustomHttpListener<UserInfoBean>(getContext(), true, UserInfoBean.class) {
                    @Override
                    public void doWork(UserInfoBean data, String code) {
                        try{
                            FormatterUtil.sortBadger(data.getData().getDfk(),tvNum1);
                            FormatterUtil.sortBadger(data.getData().getDfh(),tvNum2);
                            FormatterUtil.sortBadger(data.getData().getDsh(),tvNum3);
                            FormatterUtil.sortBadger(data.getData().getDpj(),tvNum4);
                            FormatterUtil.sortBadger(data.getData().getMypj(),tvNum5);

                            PreferencesUtils.putString(getContext(),Params.KEY_USERHEAD,data.getData().getAvatar());
                            PreferencesUtils.putString(getContext(),Params.KEY_NICKNAME,data.getData().getUser_nickname());

                            String user_id = data.getData().getId();
                            String nickname = data.getData().getUser_nickname();
                            String userhead = data.getData().getAvatar();
                            RongIM.getInstance().refreshUserInfoCache(new UserInfo(user_id, nickname,
                                    Uri.parse(userhead)));

                        }catch (Exception e){

                        }
                    }
                }, false, false);

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (isVisible()){
            ProjectUtils.updateFindMsgNum(getContext(),share_msg_num);
            ProjectUtils.updateFindMsgNum(getContext(),MainActivity.instance.tvFindMsgNum);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBus(UserInfoRefresh refresh){
        initData();
    }



}
