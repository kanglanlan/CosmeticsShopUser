package com.meida.cosmeticsshopuser.MyView.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.NumberKeyListener;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.meida.cosmeticsshopuser.Bean.GoodsDetailBean;
import com.meida.cosmeticsshopuser.MyView.MUIToast;
import com.meida.cosmeticsshopuser.adapter.FlowLayoutManager;
import com.meida.cosmeticsshopuser.adapter.SpaceItemDecoration;
import com.meida.cosmeticsshopuser.adapter.viewpager.RadioSpecifyAdapter;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.utils.NumberInputFilter;
import com.meida.cosmeticsshopuser.utils.DisplayUtil;
import com.meida.cosmeticsshopuser.utils.FormatterUtil;
import com.meida.cosmeticsshopuser.utils.GlideUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/12/21 0021.
 */

public class ProductSpecifyDialog extends Dialog{

    private ImageView img;
    private TextView price,stock,specify;
    private TextView specifyName;
    private View close;
    private RecyclerView specifyRecycler;
    private List<GoodsDetailBean.Specify> specifyData = new ArrayList<>();
    private RadioSpecifyAdapter adapter;
    private View minus,add;
    private EditText buyNum;
    private View addToCar,buyNow;

    //private int buyNumValue = 1;

    private GoodsDetailBean.DataBean data;

    public ProductSpecifyDialog(@NonNull Context context,GoodsDetailBean.DataBean data) {
        super(context, R.style.ActionSheetDialogStyle);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_goods_pick_specify,null);
        this.data = data;
        img = (ImageView) dialogView.findViewById(R.id.img);
        specifyName = dialogView.findViewById(R.id.specifyName);
        price = (TextView) dialogView.findViewById(R.id.price);
        stock = (TextView) dialogView.findViewById(R.id.stock);
        specify = (TextView) dialogView.findViewById(R.id.specify);
        close = (ImageView) dialogView.findViewById(R.id.close);
        minus = (ImageView) dialogView.findViewById(R.id.minus);
        buyNum = dialogView.findViewById(R.id.buyNum);
        add = (ImageView) dialogView.findViewById(R.id.add);
        addToCar = (TextView) dialogView.findViewById(R.id.addToCar);
        buyNow = (TextView) dialogView.findViewById(R.id.buyNow);
        specifyRecycler = (RecyclerView) dialogView.findViewById(R.id.specifyRecycler);
        specifyRecycler.setLayoutManager(new FlowLayoutManager());
        specifyRecycler.addItemDecoration(new SpaceItemDecoration
                (DisplayUtil.dp2px( 0)
                , DisplayUtil.dp2px( 0)
                , DisplayUtil.dp2px( 0)
                , DisplayUtil.dp2px( 0)));
        specifyData.clear();

        adapter = new RadioSpecifyAdapter(getContext(),R.layout.item_specify,specifyData);
        specifyRecycler.setAdapter(adapter);

        if (data.getSpec()!=null && data.getSpec().size()>0){
            specifyData.addAll(data.getSpec());
            /*specifyData.get(0).setStock(0);*/
            adapter.setSelectIndex(0);
            setDetail(specifyData.get(0));
            buyNow.setEnabled(true);
            addToCar.setEnabled(true);
        }else{
            buyNow.setEnabled(false);
            addToCar.setEnabled(false);
        }

        //specifyName.setText(data.getSpectitle());
        GlideUtils.loadGoods2(data.getImgs(),img);/*TODO 规格弹窗的图片*/

        setRecyclerMaxHeight(specifyRecycler,200);

        initEvent();

        this.setContentView(dialogView);
        this.setCancelable(true);
        this.setCanceledOnTouchOutside(true);

        Window window = this.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.bottomDialog);
        window.setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);



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

    NumberInputFilter numberInputFilter = new NumberInputFilter();
    private void initEvent(){

        buyNum.setKeyListener(new NumberKeyListener() {
            @NonNull
            @Override
            protected char[] getAcceptedChars() {
                return "0123456789".toCharArray();
            }

            @Override
            public int getInputType() {
                return 3;
            }
        });


       /* numberInputFilter.setMaxValue(data.getSpec().get(adapter.getSelectIndex()).getStock());
        buyNum.setFilters(new InputFilter[]{numberInputFilter});*/

        /*buyNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s.toString().trim())){
                    buyNumValue = 0;
                }else{
                    int tempValue = Integer.parseInt(s.toString().trim());
                    if (tempValue>data.getSpec().get(adapter.getSelectIndex()).getStock()){
                        MUIToast.show(getContext(),"库存不足");
                        //buyNumValue = data.getSpec().get(adapter.getSelectIndex()).getStock();
                        buyNum.setText(data.getSpec().get(adapter.getSelectIndex()).getStock()+"");
                        buyNum.setSelection(buyNum.getText().toString().length());
                    }else if (tempValue<=0){
                        //MUIToast.show(getContext(),"");
                        buyNumValue = 0;
                    }else{
                        buyNumValue = tempValue;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/

        adapter.setListener(new RadioSpecifyAdapter.OnCheckChangeListener() {
            @Override
            public void onCheckChange(int position) {
                setDetail(data.getSpec().get(position));
                if (listener!=null){
                    listener.onCheckChange(position);
                }
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int buyNumValue= getBuyNumValue();
                if (buyNumValue-1<=0){

                }else{
                    buyNumValue = buyNumValue-1;
                }

                buyNum.setText(buyNumValue+"");
                buyNum.setSelection(buyNum.getText().toString().length());

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Log.e("11",adapter.getSelectIndex()+"");
                    Log.e("22",data.getSpec().get(adapter.getSelectIndex()).getStock()+"");
                    int buyNumValue= getBuyNumValue();
                    if (buyNumValue+1>data.getSpec().get(adapter.getSelectIndex()).getStock()){
                        MUIToast.show(getContext(),"库存不足");
                    }else{
                        buyNumValue = buyNumValue+1;
                        buyNum.setText(buyNumValue+"");
                        buyNum.setSelection(buyNum.getText().toString().length());
                    }
                }catch (Exception e){

                }

            }
        });
        addToCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int buyNumValue= getBuyNumValue();
                if (buyNumValue<=0){
                    return;
                }
                dismiss();
                if (actionListener!=null){
                    actionListener.addToCar();
                }
            }
        });

        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int buyNumValue= getBuyNumValue();
                if (buyNumValue<=0){
                    return;
                }
                dismiss();
                if (actionListener!=null){
                    actionListener.buyNow();
                }
            }
        });

    }

    private void setDetail(GoodsDetailBean.Specify specifyData){
        /*价格*/
        String priceStr = specifyData.getPrice();
        FormatterUtil.formatPrice2(priceStr,price);
        /*库存*/
        int stockValue = specifyData.getStock();
        int buyNumValue= getBuyNumValue();
        if (stockValue>0){
            if (buyNumValue<=0){
                buyNumValue = 1;
            }else if (buyNumValue>stockValue){
                buyNumValue = stockValue;
            }
            stock.setText("库存："+stockValue);
        }else{
            buyNumValue = 0;
            stock.setText("库存不足");
        }

        /*规格名称*/
        specify.setText("已选："+specifyData.getTitle());
        buyNum.setText(buyNumValue+"");
        buyNum.setSelection(buyNum.getText().toString().length());

        numberInputFilter.setMaxValue(data.getSpec().get(adapter.getSelectIndex()).getStock());
        buyNum.setFilters(new InputFilter[]{numberInputFilter});

    }

    public interface OnSpedifyChangeListener{
        void onCheckChange(int position);
    }

    public int getBuyNumValue() {
        try{
            if (!TextUtils.isEmpty(buyNum.getText().toString().trim())){
                return Integer.parseInt(buyNum.getText().toString().trim());
            }
        }catch (Exception e){

        }
        return 0;

    }


    private OnSpedifyChangeListener listener;

    public void setListener(OnSpedifyChangeListener listener) {
        this.listener = listener;
    }


    public interface OnActionListener{

        void addToCar();
        void buyNow();

    }

    private OnActionListener actionListener;

    public void setActionListener(OnActionListener actionListener) {
        this.actionListener = actionListener;
    }
}

