package com.example.tongtu.filepost;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tongtu.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.ViewHolder> {
    private List<FilePost>file_post_list;

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView fileImage;
        TextView fileName;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            fileImage = (ImageView)itemView.findViewById(R.id.file_image);
            fileName = (TextView)itemView.findViewById(R.id.file_name);

        }
    }

    public FileAdapter(List<FilePost>file_post_list){
        this.file_post_list = file_post_list;
    }


    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.file_post,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.fileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                FilePost filePost = file_post_list.get(position);
                Toast.makeText(view.getContext(),"you chicked image"+filePost.getName(),Toast.LENGTH_SHORT).show();
            }
        });

        holder.fileName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                FilePost filePost = file_post_list.get(position);
                Toast.makeText(view.getContext(),"you chicked name"+filePost.getName(),Toast.LENGTH_SHORT).show();
            }
        });


        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        FilePost  file_post = file_post_list.get(position);
        holder.fileImage.setImageResource(file_post.getImageId());
        holder.fileName.setText(file_post.getName());

    }

    @Override
    public int getItemCount() {
        return this.file_post_list.size();
    }


}
