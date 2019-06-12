package com.meida.cosmeticsshopuser.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.meida.cosmeticsshopuser.Bean.ActionBean;
import com.meida.cosmeticsshopuser.Bean.AddrBean;
import com.meida.cosmeticsshopuser.Bean.eventbus.EditAddrBean;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.base.BaseActivity;
import com.meida.cosmeticsshopuser.nohttp.CallServer;
import com.meida.cosmeticsshopuser.nohttp.CustomHttpListener;
import com.meida.cosmeticsshopuser.nohttp.Params;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewAddActivity extends BaseActivity {

    private static final int REQUEST_MAP = 0x33;

    @Bind(R.id.et_name)
    EditText etName;
    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.tv_shengshiqu)
    TextView tvShengshiqu;
    @Bind(R.id.et_jiedaodizhi)
    EditText etJiedaodizhi;
    @Bind(R.id.cb_morendizhi)
    CheckBox cbMorendizhi;

    private String id = "";
    private String longitude = "",latitude = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_add);
        ButterKnife.bind(this);
        changeTitle("新建收货地址");

        AddrBean.AddrItemBean intentData = (AddrBean.AddrItemBean)
                getIntent().getSerializableExtra("data");
        if (intentData!=null){
            etName.setText(intentData.getConsignee());
            etName.setSelection(intentData.getConsignee().toString().trim().length());
            etPhone.setText(intentData.getMobile());
            tvShengshiqu.setText(intentData.getAddress());
            etJiedaodizhi.setText(intentData.getDoornumber());
            id = intentData.getId();
            longitude = intentData.getLongitude();
            latitude = intentData.getLatitude();
            if ("1".equals(intentData.getDefaultStr())){
                cbMorendizhi.setChecked(true);
            }else{
                cbMorendizhi.setChecked(false);
            }
        }
        changeTitle("编辑收货地址");

    }

    @OnClick({R.id.tv_shengshiqu, R.id.bt_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_shengshiqu:
                Intent intent = new Intent(baseContext,MapChoiceActivity.class);
                startActivityForResult(intent,REQUEST_MAP);
                break;
            case R.id.bt_save:
                submitData();
                break;
        }
    }


    private void submitData(){

        final String nameStr = etName.getText().toString().trim();
        final String phoneStr = etPhone.getText().toString().trim();
        final String addStr = tvShengshiqu.getText().toString().trim();
        final String doorNumStr = etJiedaodizhi.getText().toString().trim();

        if (TextUtils.isEmpty(nameStr)){
            showtoa("请输入收件人姓名");
            return;
        }

        if (TextUtils.isEmpty(phoneStr)||phoneStr.length()!=11){
            showtoa("请输入手机号码");
            return;
        }

        if (TextUtils.isEmpty(addStr)
                || TextUtils.isEmpty(longitude)
                || TextUtils.isEmpty(latitude)){
            showtoa("请选择收货地址");
            return;
        }

        if (TextUtils.isEmpty(doorNumStr)){
            showtoa("请输入门牌号");
            return;
        }

        String ipPath;
        if (TextUtils.isEmpty(id)){
            ipPath = Params.addAddr;
        }else{
            ipPath = Params.modifyAddr;
        }

        mRequest01 = getRequest(ipPath,true);
        if (TextUtils.isEmpty(id)){

        }else{
            mRequest01.add("id",id);
        }
        if (cbMorendizhi.isChecked()){
            mRequest01.add("default","1");
        }else{
            mRequest01.add("default","0");
        }
        mRequest01.add("consignee",nameStr);
        mRequest01.add("mobile",phoneStr);
        mRequest01.add("address",addStr);
        mRequest01.add("doornumber",doorNumStr);
        mRequest01.add("longitude",longitude);
        mRequest01.add("latitude",latitude);

        CallServer.getRequestInstance().add(baseContext, 0, mRequest01,
                new CustomHttpListener<ActionBean>(baseContext,true,ActionBean.class) {
                    @Override
                    public void doWork(ActionBean result, String code) {
                        showtoa(result.getMsg());
                        finish();
                        if (!TextUtils.isEmpty(id)){
                            AddrBean.AddrItemBean itemBean = new AddrBean.AddrItemBean();
                            itemBean.setId(id);
                            itemBean.setConsignee(nameStr);
                            itemBean.setMobile(phoneStr);
                            itemBean.setAddress(addStr);
                            itemBean.setDoornumber(doorNumStr);
                            itemBean.setLatitude(longitude);
                            itemBean.setLatitude(latitude);
                            EventBus.getDefault().post(new EditAddrBean(false,id,itemBean));

                        }



                    }
                },false,true);



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intentData) {
        super.onActivityResult(requestCode, resultCode, intentData);

        if (requestCode == REQUEST_MAP && resultCode == 100 && intentData != null) {
            //PoiItem poiItem = data.getParcelableExtra("poi");

            String provinceStr = intentData.getStringExtra("province");
            String cityStr = intentData.getStringExtra("city");
            String countryStr  = intentData.getStringExtra("district");

            StringBuilder builder = new StringBuilder();
            builder.append(provinceStr);
            builder.append(cityStr);
            builder.append(countryStr);

            tvShengshiqu.setText(builder.toString().trim());

            //if (poiItem != null) {
            etJiedaodizhi.setText(intentData.getStringExtra("detailAddr"));
            longitude = intentData.getStringExtra("lng");
            latitude = intentData.getStringExtra("lat");
            //}
        }

    }
}
