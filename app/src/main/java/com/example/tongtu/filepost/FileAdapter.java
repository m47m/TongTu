package com.example.tongtu.filepost;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tongtu.FileDownload;
import com.example.tongtu.FolderActivity;
import com.example.tongtu.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;


//文件分组
public class FileAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<FilePost>file_post_list;
    private Context context;

    static class FileViewHolder extends RecyclerView.ViewHolder{
        ImageView fileImage;
        TextView fileName;
        TextView fileDeviceNumber;
        TextView fileTime;
        public FileViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            fileImage = (ImageView)itemView.findViewById(R.id.file_image);
            fileName = (TextView)itemView.findViewById(R.id.file_name);
            fileName.getPaint().setFakeBoldText(true);
            fileDeviceNumber = (TextView)itemView.findViewById(R.id.file_device_number);
            fileTime = (TextView)itemView.findViewById(R.id.file_time);
        }
    }

    public FileAdapter(List<FilePost>file_post_list, Context f){
        this.file_post_list = file_post_list;
        this.context = f;
    }


    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.file_post,parent,false);
        final FileViewHolder holder = new FileViewHolder(view);
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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(holder.itemView.getContext(),"点击了"+file_post_list.get(holder.getAdapterPosition()).getName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, FolderActivity.class);
                intent.putExtra("folder_name",file_post_list.get(holder.getAdapterPosition()).getName());
                context.startActivity(intent);
            }
        });


        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        FilePost  file_post = file_post_list.get(position);
        FileViewHolder fileViewHolder = (FileViewHolder)holder;
        fileViewHolder.fileImage.setImageResource(file_post.getImageId());
        fileViewHolder.fileName.setText(file_post.getName());
        fileViewHolder.fileDeviceNumber.setText(file_post.getFile_device_number());
        fileViewHolder.fileTime.setText(file_post.getFile_time());

    }

    @Override
    public int getItemCount() {
        return this.file_post_list.size();
    }


}
