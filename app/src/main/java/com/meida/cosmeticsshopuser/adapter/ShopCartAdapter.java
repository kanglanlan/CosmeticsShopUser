package com.meida.cosmeticsshopuser.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.meida.cosmeticsshopuser.Bean.ShopCartBean;
import com.meida.cosmeticsshopuser.MyView.MUIToast;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.Activity.SubmitOrderActivity;
import com.meida.cosmeticsshopuser.utils.FormatterUtil;
import com.meida.cosmeticsshopuser.utils.GlideUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/12/21 0021.
 */

public class ShopCartAdapter extends BaseExpandableListAdapter{

    private Context context;
    private ArrayList<ShopCartBean.Shop> data;
    private boolean isEditMode = false;
    private OnShopCartDataChangeListener listener;

    public void setListener(OnShopCartDataChangeListener listener) {
        this.listener = listener;
    }

    public boolean isEditMode() {
        return isEditMode;
    }

    public void setEditMode(boolean editMode) {
        isEditMode = editMode;
        notifyDataSetChanged();
    }

    public ShopCartAdapter(Context context, ArrayList<ShopCartBean.Shop> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getGroupCount() {
        if (data==null){
            return 0;
        }
        return data.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        ShopCartBean.Shop shop = data.get(groupPosition);
        if (shop.getGoodslist()==null){
            return 0;
        }
        return shop.getGoodslist().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return data.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return data.get(groupPosition).getGoodslist().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder holder = null;
        if (convertView==null){
            holder = new GroupHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_shop_header,null);
            holder.checkImg = convertView.findViewById(R.id.checkImg);
            holder.shopName = convertView.findViewById(R.id.shopName);
            convertView.setTag(holder);
        }else{
            holder = (GroupHolder) convertView.getTag();
        }

        holder.shopName.setText(data.get(groupPosition).getTitle());

        final boolean b = data.get(groupPosition).isCheck();
        if (b){
            holder.checkImg.setImageResource(R.drawable.ico_img67);
        }else{
            holder.checkImg.setImageResource(R.drawable.ico_img69);
        }

        holder.checkImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkGroup(!b,groupPosition);
                if (listener!=null){
                    listener.onDataAllSelect(isAllSelect());
                }
            }
        });

        holder.shopName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener!=null){
                    listener.onShopClick(data.get(groupPosition));
                }
            }
        });

        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder holder = null;
        if (convertView==null){
            holder = new ChildHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_cart_product,null);
            holder.checkImg = convertView.findViewById(R.id.checkImg);
            holder.productImg = convertView.findViewById(R.id.img);
            holder.add = convertView.findViewById(R.id.add);
            holder.minus = convertView.findViewById(R.id.minus);
            holder.productName = convertView.findViewById(R.id.name);
            holder.specifyName = convertView.findViewById(R.id.specifyName);
            holder.buyNum = convertView.findViewById(R.id.buyNum);
            holder.price = convertView.findViewById(R.id.price);
            holder.tailView = convertView.findViewById(R.id.tailView);
            holder.line = convertView.findViewById(R.id.line);
            holder.checkAllImg = convertView.findViewById(R.id.checkAllImg);
            holder.priceSum = convertView.findViewById(R.id.priceSum);
            holder.settlement = convertView.findViewById(R.id.settlement);
            holder.goodsView = convertView.findViewById(R.id.goodsView);
            convertView.setTag(holder);
        }else{
            holder = (ChildHolder) convertView.getTag();
        }

        GlideUtils.loadGoods2(data.get(groupPosition).getGoodslist().get(childPosition).getImgs(),holder.productImg);
        holder.productName.setText(data.get(groupPosition).getGoodslist().get(childPosition).getTitle());
        holder.specifyName.setText(data.get(groupPosition).getGoodslist().get(childPosition).getSpectitle());
        holder.buyNum.setText(String.valueOf(data.get(groupPosition).getGoodslist().get(childPosition).getNum()));
        holder.price.setText(FormatterUtil.formatPrice2(data.get(groupPosition).getGoodslist().get(childPosition).getPriceValue()));
        holder.priceSum.setText(FormatterUtil.formatPrice2(data.get(groupPosition).getShopSumPrice()));

        if (childPosition==(data.get(groupPosition).getGoodslist().size()-1)){
            if (isEditMode){
                holder.tailView.setVisibility(View.GONE);
                holder.line.setVisibility(View.VISIBLE);
            }else{
                holder.tailView.setVisibility(View.VISIBLE);
                holder.line.setVisibility(View.VISIBLE);
            }
        }else{
            holder.tailView.setVisibility(View.GONE);
            holder.line.setVisibility(View.GONE);
        }

        int checkNum = 0;
        for (int i = 0;i<data.get(groupPosition).getGoodslist().size(); i++){
            if (data.get(groupPosition).getGoodslist().get(i).isCheck()){
                checkNum++;
            }
        }
        holder.settlement.setText("去结算("+checkNum+")");

        /*单个商品选中状态*/
        final boolean productCheckState = data.get(groupPosition).getGoodslist().get(childPosition).isCheck();
        /*所属店铺选中状态  是否全选*/
        final boolean shopCheckState = data.get(groupPosition).isCheck();

        if (productCheckState){
            holder.checkImg.setImageResource(R.drawable.ico_img67);
        }else{
            holder.checkImg.setImageResource(R.drawable.ico_img69);
        }

        if (shopCheckState){
            holder.checkAllImg.setImageResource(R.drawable.ico_img67);
        }else{
            holder.checkAllImg.setImageResource(R.drawable.ico_img69);
        }

        holder.checkImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.get(groupPosition).getGoodslist().get(childPosition).setCheck(!productCheckState);
                updateShopCheckState(groupPosition);
                if (listener!=null){
                    listener.onDataAllSelect(isAllSelect());
                }
            }
        });

        holder.checkAllImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkGroup(!shopCheckState,groupPosition);
                if (listener!=null){
                    listener.onDataAllSelect(isAllSelect());
                }
            }
        });

        holder.settlement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settlementShop(groupPosition);
            }
        });

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num = data.get(groupPosition).getGoodslist().get(childPosition).getNum();
                int stock = data.get(groupPosition).getGoodslist().get(childPosition).getStock();
                if (num+1>stock){
                    MUIToast.show(context,"库存不足");
                }else{
                    if (listener!=null){
                        listener.onItemReduce(groupPosition,childPosition, data.get(groupPosition).getGoodslist().get(childPosition),num+1);
                    }
                }
            }
        });

        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num = data.get(groupPosition).getGoodslist().get(childPosition).getNum();
                if (num-1>0){
                    if (listener!=null){
                        listener.onItemAdd(groupPosition,childPosition, data.get(groupPosition).getGoodslist().get(childPosition),num-1);
                    }
                }else{
                    MUIToast.show(context,"不能再减啦");
                }
            }
        });

        holder.goodsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener!=null){
                    listener.onGoodsClick(data.get(groupPosition).getGoodslist().get(childPosition));
                }
            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    class GroupHolder{
        ImageView checkImg;
        TextView shopName;
    }

    class ChildHolder{

        ImageView checkImg,productImg;
        View add,minus;
        TextView productName,specifyName,buyNum,price;

        View tailView,line;
        ImageView checkAllImg;
        TextView priceSum,settlement;

        View goodsView;

    }


    public interface OnShopCartDataChangeListener {

        /*结算单个店铺  单个店铺消费金额*/
        void onDataChange(double total_price);

        /*全选 或 全不选*/
        void onDataAllSelect(boolean is_all_selet);

        /*添加一个商品数量*/
        void onItemAdd(int position, int item_position, ShopCartBean.CartGoods bean,int num);

        /*减少一个商品数量*/
        void onItemReduce(int position, int item_position, ShopCartBean.CartGoods bean,int num);

        /*点击商品*/
        void onGoodsClick(ShopCartBean.CartGoods bean);

        /*点击店铺*/
        void onShopClick(ShopCartBean.Shop shopBean);

    }


    /*选中或取消这个店铺下的所有商品*/
    private void checkGroup(boolean b,int groupIndex){
        data.get(groupIndex).setCheck(b);
        int size = data.get(groupIndex).getGoodslist().size();
        for (int i = 0; i<size; i++){
            data.get(groupIndex).getGoodslist().get(i).setCheck(b);
        }
        updateShopPrice(groupIndex);
        notifyDataSetChanged();
    }

    /*选中或取消所有商品*/
    public void checkAll(boolean b){
        for (int i = 0; i<data.size(); i++){
            data.get(i).setCheck(b);
            for (int j = 0; j<data.get(i).getGoodslist().size(); j++){
                data.get(i).getGoodslist().get(j).setCheck(b);
            }
            updateShopPrice(i);
        }
        notifyDataSetChanged();
    }



    /*更新店铺价格*/
    public void updateShopPrice(int groupIndex){
        double shopSumPrice = 0;
        int size = data.get(groupIndex).getGoodslist().size();
        for (int i = 0; i<size; i++){
            if (data.get(groupIndex).getGoodslist().get(i).isCheck()){
                shopSumPrice = shopSumPrice +
                        data.get(groupIndex).getGoodslist().get(i).getPriceValue()
                                *data.get(groupIndex).getGoodslist().get(i).getNum();
            }
        }
        data.get(groupIndex).setShopSumPrice(shopSumPrice);
    }


    /*店铺是否全选*/
    private void updateShopCheckState(int groupIndex){
        boolean isCheckAll = true;
        int size = data.get(groupIndex).getGoodslist().size();
        for (int i = 0; i<size; i++){
            if (!data.get(groupIndex).getGoodslist().get(i).isCheck()){
                isCheckAll = false;
                break;
            }
        }
        data.get(groupIndex).setCheck(isCheckAll);
        updateShopPrice(groupIndex);
        notifyDataSetChanged();
    }

    /*是否全选*/
    private boolean isAllSelect(){
        for (int i = 0; i<data.size(); i++){
            for (int j = 0; j<data.get(i).getGoodslist().size(); j++){
                if (!data.get(i).getGoodslist().get(j).isCheck()){
                    return false;
                }
            }
        }
        return true;
    }

    public String getSelectIds(){
        String ids = "";
        for (int i = 0; i<data.size(); i++){
            for (int j = 0; j<data.get(i).getGoodslist().size(); j++){
                if (data.get(i).getGoodslist().get(j).isCheck()){
                    ids = ids+","+data.get(i).getGoodslist().get(j).getId();
                }
            }
        }
        if (ids.length()>1){
            return ids.substring(1,ids.length());
        }else{
            return "";
        }
    }

    private void settlementShop(int groupIndex){
        List<ShopCartBean.CartGoods> goodsList = new ArrayList<>();
        for (int i = 0; i<data.get(groupIndex).getGoodslist().size();i++){
            if (data.get(groupIndex).getGoodslist().get(i).isCheck()){
                goodsList.add(data.get(groupIndex).getGoodslist().get(i));
            }
        }
        if (goodsList.size()<=0){
            MUIToast.show(context,"请选择您要结算的商品");
            return;
        }
        ShopCartBean.Shop shopBean = new ShopCartBean.Shop();
        shopBean.setId(data.get(0).getId());
        shopBean.setTitle(data.get(0).getTitle());
        /*shopBean.setId(data.get(0).getId());
        shopBean.setId(data.get(0).getId());*/
        shopBean.setGoodslist(goodsList);
        Intent intent = new Intent(context, SubmitOrderActivity.class);
        intent.putExtra("data",shopBean);
        intent.putExtra("flag","1");
        context.startActivity(intent);

    }




}
