package com.meida.cosmeticsshopuser.MyView;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

public class ObservableScrollView extends NestedScrollView {

    /* if (!isUp&&dy>move_distance){
        areaSec.setTextColor(Color.parseColor("#333333"));
        topC.setBackgroundColor(Color.WHITE);
        shadow.setVisibility(View.VISIBLE);
        searchC.setBackgroundResource(R.drawable.main_search_bg_gray);
        topShader.setVisibility(View.VISIBLE);
        areaSec.setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.dark_angle,0);
    }else if (isUp&&dy<=move_distance){
        areaSec.setTextColor(Color.WHITE);
        areaSec.setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.white_angle,0);
        topC.setBackgroundColor(Color.TRANSPARENT);
        shadow.setVisibility(View.INVISIBLE);
        searchC.setBackgroundResource(R.drawable.main_search_bg);
        topShader.setVisibility(View.GONE);
    }*/

    private ScrollViewListener scrollViewListener = null;

    public ObservableScrollView(Context context) {
        super(context);
    }

    public ObservableScrollView(Context context, AttributeSet attrs,
                                int defStyle) {
        super(context, attrs, defStyle);
    }

    public ObservableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOnScrollListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }



    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        Log.e("onScrollChanged",x+"  "+y+"  "+oldx+"  "+oldy);
        if (scrollViewListener != null) {

            if (oldy < y ) {// 手指向上滑动，屏幕内容下滑
                scrollViewListener.onScroll(oldy,y,false);

            } else if (oldy > y ) {// 手指向下滑动，屏幕内容上滑
                scrollViewListener.onScroll(oldy,y,true);
            }
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(ev);
    }

    public  interface ScrollViewListener{//dy Y轴滑动距离，isUp 是否返回顶部
         void onScroll(int oldy, int dy, boolean isUp);
    }
}