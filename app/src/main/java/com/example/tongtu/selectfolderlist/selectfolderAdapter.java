package com.example.tongtu.selectfolderlist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tongtu.R;

import java.util.List;

public class selectfolderAdapter  extends RecyclerView.Adapter<selectfolderAdapter.MyViewHolder> {
    private List<String> mData;
    private Context mContext;
    private int defItem = -1;//默认值
    private OnItemListener onItemListener;

    public selectfolderAdapter(List<String> data, Context context)
    {
        mData = data;
        mContext = context;
    }

    public void setOnItemListener(OnItemListener onItemListener)
    {
        this.onItemListener = onItemListener;
    }
    //设置点击事件
    public interface OnItemListener
    {
        void onClick(View v, int pos);
    }
    //获取点击的位置
    public void setDefSelect(int position)
    {
        this.defItem = position;
        notifyDataSetChanged();
    }


    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position)
    {
        MyViewHolder myViewHolder = new MyViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.folder_select,viewGroup,false));
        return myViewHolder;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder barberListViewHolder,final int position)
    {
        barberListViewHolder.mTextView.setText(mData.get(position));

        if (defItem != -1)
        {
            /*点的位置跟点击的textview位置一样设置点击后的不同样式*/
            if (defItem == position)
            {
                /*设置选中的样式*/

                barberListViewHolder.mConstraintLayout.setBackground(mContext.getResources().getDrawable(R.drawable.folder_selected));

            } else {
                /*其他的变为未选择状态
                 *设置未选中的样式
                 */
                barberListViewHolder.mConstraintLayout.setBackground(mContext.getResources().getDrawable(R.drawable.folder_unselected));
            }
        }

    }

    @Override
    public int getItemCount()
    {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {

        TextView mTextView;
        ConstraintLayout mConstraintLayout;
        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            mTextView = itemView.findViewById(R.id.recycle_text);
            mConstraintLayout = itemView.findViewById(R.id.folder_model);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemListener != null) {
                        onItemListener.onClick(v, getLayoutPosition());
                    }
                }
            });
        }
    }

}
