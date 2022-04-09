package com.example.tongtu.utils;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public interface IItemTouchHelperAdapter {
    /**
     * 当item被移动时调用
     *
     * @param fromPosition 被操作的item的起点
     * @param toPosition  被操作的item的终点
     */
    void onItemMove(int fromPosition, int toPosition);

    /**
     * 当item被侧滑时调用
     *
     * @param position 被侧滑的item的position
     */
    void onItemDismiss(int position);

    void onChildDraw(RecyclerView.ViewHolder viewHolder, float dX, float dY);

    View getItemFrontView(RecyclerView.ViewHolder mPreOpened);
}

