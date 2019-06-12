package com.meida.cosmeticsshopuser.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import com.meida.cosmeticsshopuser.Bean.ActionBean;
import com.meida.cosmeticsshopuser.Bean.BaseBean;
import com.meida.cosmeticsshopuser.Bean.UrgentContactBean;
import com.meida.cosmeticsshopuser.MyView.MUIToast;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.base.BaseActivity;
import com.meida.cosmeticsshopuser.nohttp.CallServer;
import com.meida.cosmeticsshopuser.nohttp.CustomHttpListener;
import com.meida.cosmeticsshopuser.nohttp.Params;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 紧急联系人
 */
public class ContactActivity extends BaseActivity {

    private boolean isForOrder = true;

    @Bind(R.id.et_name1)
    EditText etName1;
    @Bind(R.id.et_phone1)
    EditText etPhone1;
    @Bind(R.id.et_name2)
    EditText etName2;
    @Bind(R.id.et_phone2)
    EditText etPhone2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        ButterKnife.bind(this);
        isForOrder = getIntent().getBooleanExtra("isForOrder",true);
        changeTitle("添加紧急联系人");
        getData();
    }

    @OnClick(R.id.tv_tijiao)
    public void onViewClicked() {
        if (isForOrder){
            packData();
        }else{
            submit();
        }
    }


    private void packData(){
        String name1Str= etName1.getText().toString().trim();
        String phone1Str = etPhone1.getText().toString().trim();
        String name2Str = etName2.getText().toString().trim();
        String phone2Str = etPhone2.getText().toString().trim();

        if (TextUtils.isEmpty(name1Str) && TextUtils.isEmpty(phone1Str) &&
                (TextUtils.isEmpty(name2Str) && TextUtils.isEmpty(phone2Str))){
            MUIToast.show(baseContext,"请填写联系人信息");
            return;
        }else{
            Intent intent = new Intent();
            intent.putExtra("name1",name1Str);
            intent.putExtra("name2",name2Str);
            intent.putExtra("phone1",phone1Str);
            intent.putExtra("phone2",phone2Str);
            setResult(RESULT_OK,intent);
            finish();
        }

    }

    private void submit(){
        String name1Str= etName1.getText().toString().trim();
        String phone1Str = etPhone1.getText().toString().trim();
        String name2Str = etName2.getText().toString().trim();
        String phone2Str = etPhone2.getText().toString().trim();

        if (TextUtils.isEmpty(name1Str) && TextUtils.isEmpty(phone1Str) &&
                (TextUtils.isEmpty(name2Str) && TextUtils.isEmpty(phone2Str))){
            MUIToast.show(baseContext,"请填写联系人信息");
            return;
        }
        mRequest01 = getRequestNoUid(Params.setUrgentContact,true);
        mRequest01.add("name1",name1Str);
        mRequest01.add("phone1",phone1Str);
        mRequest01.add("name2",name2Str);
        mRequest01.add("phone2",phone2Str);

        CallServer.getRequestInstance().add(baseContext, 0, mRequest01,
                new CustomHttpListener<BaseBean>
                        (baseContext,true,BaseBean.class) {
                    @Override
                    public void doWork(BaseBean result, String code) {
                        showtoa(result.getMsg());
                        finish();
                    }
                },false,true);

    }


    private void getData(){

        mRequest02 = getRequest(Params.getUrgentContact,true);

        CallServer.getRequestInstance().add(baseContext, 0, mRequest02,
                new CustomHttpListener<UrgentContactBean>
                        (baseContext,true,UrgentContactBean.class) {
                    @Override
                    public void doWork(UrgentContactBean result, String code) {
                        try{
                            etName1.setText(result.getData().getName1());
                            etName2.setText(result.getData().getName2());
                            etPhone1.setText(result.getData().getPhone1());
                            etPhone2.setText(result.getData().getPhone2());
                            etName1.setSelection(etName1.getText().toString().length());
                        }catch (Exception e){

                        }
                    }
                },false,true);

    }





}
