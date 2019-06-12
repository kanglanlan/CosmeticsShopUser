package com.meida.cosmeticsshopuser.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.meida.cosmeticsshopuser.Bean.CityBean;
import com.meida.cosmeticsshopuser.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/12/22 0022.
 */

public class CityAdapter extends BaseExpandableListAdapter {

    private String[] letters = new String[]{
            "A","B","C","D","E","F","G","H","I","J","K","L","M","N"
            ,"O","P","Q","R","S","T","U","V","W","X","Y","Z"
    };

    private HashMap<String,Integer> indexPos = new HashMap<>();


    private Context context;
    private List<CityBean.City> data;
    private View hotView;

    public interface OnItemClickListener{
        void onItemClick(int groupPosition, int childPosition);
    }

    private OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public CityAdapter(Context context, List<CityBean.City> data) {
        this.context = context;
        this.data = data;
        sortIndexPos();
    }

    private void sortIndexPos(){
        if (data!=null && data.size()>0){

            indexPos.put(data.get(0).getPinyin(),0);
            for (int i = 1;i <data.size(); i++){
                String thisLetter = data.get(i).getPinyin();
                String lastLetter = data.get(i-1).getPinyin();
                if (!TextUtils.equals(thisLetter, lastLetter)){
                    indexPos.put(thisLetter, i);
                }
            }

            indexPos.put("#",0);
            indexPos.put("A",0);
            for (int i = 1; i<letters.length; i++){
                if (indexPos.get(letters[i])==null){
                    indexPos.put(letters[i], indexPos.get(letters[i-1]));
                }
            }
        }

        Log.e("indexPos",indexPos.toString());
    }

    public int childCount(int groupIndex){
        int count = 0;
        for (int i = 0;i<groupIndex;i++){
            count = count+data.get(i).getLists().size();
        }
        return count;
    }

    public void setHotView(View hotView) {
        this.hotView = hotView;
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
        if (data.get(groupPosition).getLists()==null){
            return 0;
        }
        return data.get(groupPosition).getLists().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return data.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return data.get(groupPosition).getLists().get(childPosition);
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
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder holder = null;
        if (convertView==null){
            holder = new GroupHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_area_header,null);
            holder.headerName = convertView.findViewById(R.id.headerName);
            holder.hotParent = convertView.findViewById(R.id.hotParent);
            convertView.setTag(holder);
        }else{
            holder = (GroupHolder) convertView.getTag();
        }
        if (groupPosition==0){
            holder.hotParent.setVisibility(View.VISIBLE);
            holder.hotParent.removeAllViews();
            holder.hotParent.addView(hotView);
        }else{
            holder.hotParent.setVisibility(View.GONE);
            holder.hotParent.removeAllViews();
        }
        holder.headerName.setText(data.get(groupPosition).getPinyin());
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder holder = null;
        if (convertView==null){
            holder = new ChildHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_area_name,null);
            holder.areaName = convertView.findViewById(R.id.areaName);
            convertView.setTag(holder);
        }else{
            holder = (ChildHolder) convertView.getTag();
        }
        holder.areaName.setText(data.get(groupPosition).getLists().get(childPosition).getName());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener!=null){
                    listener.onItemClick(groupPosition,childPosition);
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
        TextView headerName;
        FrameLayout hotParent;
    }

    class ChildHolder{
        TextView areaName;
    }

    public int getLetterPosition(String string){
        if (null==indexPos.get(string)){
            return 0;
        }else{
            return indexPos.get(string);
        }
    }

    public void refreshData(List<CityBean.City> data){
        this.data = data;
        sortIndexPos();
        notifyDataSetChanged();
    }


}
