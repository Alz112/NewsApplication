package com.example.newsapplication;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class VerticalSpace extends RecyclerView.ItemDecoration {
    int Space;
    public VerticalSpace(int Space){
        this.Space = Space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state){
        outRect.left = Space;
        outRect.right = Space;
        outRect.bottom = Space;
        if(parent.getChildLayoutPosition(view)==0){
            outRect.top = Space;
        }
    }
}
