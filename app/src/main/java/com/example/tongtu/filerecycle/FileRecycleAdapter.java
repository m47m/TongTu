package com.example.tongtu.filerecycle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tongtu.R;
import com.example.tongtu.filepost.FileAdapter;
import com.example.tongtu.filepost.FilePost;
import com.example.tongtu.utils.IItemTouchHelperAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class FileRecycleAdapter extends RecyclerView.Adapter<FileRecycleAdapter.ViewHolder> implements IItemTouchHelperAdapter {

    private List<FileRecycle> file_recycle_list;

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Collections.swap(file_recycle_list, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {
        file_recycle_list.remove(position);
        Log.d("recycleView","测滑："+String.valueOf(position));
        notifyItemRemoved(position);
    }

    @Override
    public void onChildDraw(RecyclerView.ViewHolder viewHolder, float dX, float dY) {

    }

    @Override
    public View getItemFrontView(RecyclerView.ViewHolder mPreOpened) {
        return null;
    }


    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView fileImage;
        //ImageView fileRecycle;
        TextView fileName;
        TextView fileOverdue;
        TextView fileSize;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            fileImage = (ImageView)itemView.findViewById(R.id.file_image);
            fileName = (TextView)itemView.findViewById(R.id.file_name);
            fileName.getPaint().setFakeBoldText(true);
            fileOverdue = (TextView)itemView.findViewById(R.id.file_overdue);
            fileSize = (TextView)itemView.findViewById(R.id.file_size);
            //fileRecycle = (ImageView)itemView.findViewById(R.id.btn_reply);

        }
    }

    public FileRecycleAdapter(List<FileRecycle>file_recycle_list){
        this.file_recycle_list = file_recycle_list;
    }
    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.file_recycle,parent,false);
        final ViewHolder holder = new ViewHolder(view);

//        holder.fileRecycle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int position = holder.getAdapterPosition();
//                Toast.makeText(view.getContext(),"cacaw",Toast.LENGTH_SHORT).show();
//            }
//        });
//        holder.fileImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int position = holder.getAdapterPosition();
//                FilePost filePost = file_post_list.get(position);
//                Toast.makeText(view.getContext(),"you chicked image"+filePost.getName(),Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        holder.fileName.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int position = holder.getAdapterPosition();
//                FilePost filePost = file_post_list.get(position);
//                Toast.makeText(view.getContext(),"you chicked name"+filePost.getName(),Toast.LENGTH_SHORT).show();
//            }
//        });

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(holder.itemView.getContext(),"点击了", Toast.LENGTH_SHORT).show();
//            }
//        });


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        FileRecycle  file_recycle = file_recycle_list.get(position);
        holder.fileImage.setImageResource(file_recycle.getImageId());
        holder.fileName.setText(file_recycle.getName());
        holder.fileOverdue.setText(file_recycle.getFileoverdue());
        holder.fileSize.setText(file_recycle.getFilesize());

    }

    @Override
    public int getItemCount() {
        return this.file_recycle_list.size();
    }
}
