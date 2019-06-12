package com.meida.cosmeticsshopuser.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.meida.cosmeticsshopuser.Bean.HotSearch;
import com.meida.cosmeticsshopuser.MyView.ClearEditText;
import com.meida.cosmeticsshopuser.MyView.FlowLiner;
import com.meida.cosmeticsshopuser.MyView.dialog.ActionDialog;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.adapter.SearchHistoryAdapter;
import com.meida.cosmeticsshopuser.base.BaseActivity;
import com.meida.cosmeticsshopuser.nohttp.CallServer;
import com.meida.cosmeticsshopuser.nohttp.CustomHttpListener;
import com.meida.cosmeticsshopuser.nohttp.Params;
import com.meida.cosmeticsshopuser.utils.DisplayUtil;
import com.meida.cosmeticsshopuser.utils.PreferencesUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends BaseActivity {

    @Bind(R.id.et_search)
    ClearEditText etSearch;
    @Bind(R.id.fl1)
    FlowLiner fl1;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.tv_qingkong)
    View tv_qingkong;
    private List<String> data = new ArrayList<>();
    private SearchHistoryAdapter adapter;
    private String historyKeyWord = "";
    private ActionDialog deleteDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        fl1.removeAllViews();

        LinearLayoutManager lm = new LinearLayoutManager(baseContext,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(lm);
        adapter = new SearchHistoryAdapter(baseContext,R.layout.item_search_history,data);
        recyclerView.setAdapter(adapter);
        adapter.setListener(new SearchHistoryAdapter.OnItemClickListener() {
            @Override
            public void onClickItem(int position, String s) {
                goSearch(s, false);
            }

            @Override
            public void onDelete(int position) {
                historyKeyWord = historyKeyWord.replace(data.get(position),"");
                PreferencesUtils.putString(baseContext,"HISTORY_KEYWORD",historyKeyWord);
                data.remove(position);
                if (position!= RecyclerView.NO_POSITION){
                    adapter.notifyItemRemoved(position);
                    adapter.notifyItemRangeChanged(position, data.size());
                }else{
                    adapter.notifyDataSetChanged();
                }
                checkHistory();

            }
        });

        deleteDialog = new ActionDialog(baseContext,"清空历史","取消","清空");
        deleteDialog.setOnActionClickListener(new ActionDialog.OnActionClickListener() {
            @Override
            public void onLeftClick() {

            }

            @Override
            public void onRightClick() {
                PreferencesUtils.putString(baseContext,"HISTORY_KEYWORD","");
                data.clear();
                adapter.notifyDataSetChanged();
            }
        });

       historyKeyWord = PreferencesUtils.getString(baseContext,"HISTORY_KEYWORD");
        if (!TextUtils.isEmpty(historyKeyWord)){
            String oldArray[] = historyKeyWord.split(",");
            List list = Arrays.asList(oldArray);
            Set set = new HashSet(list);
            oldArray = (String[])set.toArray(new String[0]);
            List<String> strs = Arrays.asList(oldArray);
            for (String str : strs){
                if (!TextUtils.isEmpty(str)){
                    data.add(str);
                }
            }
        }
        adapter.notifyDataSetChanged();
        checkHistory();

       getHotSearch();

    }
    private TextView buildLabel(final String text) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        textView.setPadding((int) dpToPx(16), (int) dpToPx(8), (int) dpToPx(16), (int) dpToPx(8));
        textView.setBackgroundResource(R.drawable.huibt10);
        textView.setTextSize(13);
        textView.setTextColor(Color.parseColor("#666666"));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, DisplayUtil.dp2px(10),DisplayUtil.dp2px(8),0);
        textView.setLayoutParams(lp);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goSearch(text,true);
            }
        });
        return textView;
    }

    private float dpToPx(float dp) {
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }


    @OnClick({ R.id.tv_quxiao, R.id.tv_qingkong})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_quxiao:
                finish();
                break;
            case R.id.tv_qingkong:
                deleteDialog.show();
                break;
        }
    }

    /*获取热门搜索*/
    private void getHotSearch(){
        mRequest01 = getRequest(Params.getHotSearch,false);
        mRequest01.add("page",1);
        mRequest01.add("size",20);
        CallServer.getRequestInstance().add(baseContext, 0, mRequest01,
                new CustomHttpListener<HotSearch>(baseContext,true,HotSearch.class) {
                    @Override
                    public void doWork(HotSearch result, String code) {
                        try{
                            List<HotSearch.HotItem> beans = result.getData().getData();
                            if (beans!=null && beans.size()>0){
                                fl1.removeAllViews();
                                for (int i = 0; i<beans.size(); i++){
                                    TextView textView = buildLabel(beans.get(i).getTitle());
                                    fl1.addView(textView);
                                }
                            }
                        }catch (Exception e){

                        }

                    }
                },false,false);


    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction()) {
            String edStr = etSearch.getText().toString().trim();
            if (TextUtils.isEmpty(edStr)){
                showtoa("请输入关键词");
            }else{
                goSearch(edStr,true);
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    private void checkHistory(){
        if (adapter.getItemCount()>0){
            tv_qingkong.setVisibility(View.VISIBLE);
        }else{
            tv_qingkong.setVisibility(View.GONE);
        }
    }

    private void goSearch(String string,boolean save){
        if (save){
            if (TextUtils.isEmpty(historyKeyWord)){
                historyKeyWord = string;
            }else{
                historyKeyWord = historyKeyWord+","+string;
            }
            PreferencesUtils.putString(baseContext,"HISTORY_KEYWORD",historyKeyWord);
        }

        Intent intent = new Intent(baseContext,StoreGoodsListActivity.class);
        intent.putExtra("str",string);
        startActivity(intent);
        finish();

    }



}
