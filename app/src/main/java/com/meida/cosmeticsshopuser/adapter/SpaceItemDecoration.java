package com.meida.cosmeticsshopuser.adapter;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2018/4/26.
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int space_left;
    private int space_top;
    private int space_right;
    private int space_bottom;


    public SpaceItemDecoration(int space) {
        this.space_left = space;
        this.space_top = space;
        this.space_right = space;
        this.space_bottom = space;
    }

    public SpaceItemDecoration(int left, int top, int right, int bottom) {
        this.space_left = left;
        this.space_top = top;
        this.space_right = right;
        this.space_bottom = bottom;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.top = space_top;
        outRect.left = space_left;
        outRect.right = space_right;
        outRect.bottom = space_bottom;
    }

}
