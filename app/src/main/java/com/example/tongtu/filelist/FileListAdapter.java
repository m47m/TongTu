package com.example.tongtu.filelist;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.Group;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tongtu.FileDownload;
import com.example.tongtu.MainActivity;
import com.example.tongtu.R;
import com.example.tongtu.filepost.FileAdapter;
import com.example.tongtu.utils.TimeTypeutils;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.util.List;

public class FileListAdapter extends RecyclerView.Adapter<FileListAdapter.ViewHolder> {

    private List<FileList> file_list_list;
    private Context context;

    public FileListAdapter(List<FileList> file_list_list, Context f){
        this.file_list_list = file_list_list;
        this.context = f;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView file_class_left;
        ImageView file_class_right;
        ImageView file_device_left;
        ImageView file_device_right;
        TextView file_name_left;
        TextView file_name_right;
        TextView file_size_cloud_left;
        TextView file_size_cloud_right;

        TextView file_time;

        CardView card_file_message_left;
        CardView card_file_message_right;

        Group file_source_left;
        Group file_source_right;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            file_name_left = (TextView) itemView.findViewById(R.id.text_file_name_left);
            file_name_right = (TextView) itemView.findViewById(R.id.text_file_name_right) ;
            file_size_cloud_left = (TextView) itemView.findViewById(R.id.text_file_size_cloud_left);
            file_size_cloud_right = (TextView) itemView.findViewById(R.id.text_file_size_cloud_right);
            file_class_left = (ImageView) itemView.findViewById(R.id.img_file_class_left);
            file_class_right = (ImageView) itemView.findViewById(R.id.img_file_class_right);
            file_device_left = (ImageView) itemView.findViewById(R.id.img_device_left);
            file_device_right = (ImageView) itemView.findViewById(R.id.img_device_right);
            file_time = (TextView)itemView.findViewById(R.id.text_file_time);

            card_file_message_left = (CardView)itemView.findViewById(R.id.card_file_message_left);
            card_file_message_right = (CardView) itemView.findViewById(R.id.card_file_message_right);
            file_source_left = (Group)itemView.findViewById(R.id.file_format_left);
            file_source_right = (Group)itemView.findViewById(R.id.file_format_right);


        }
    }

    @NonNull
    @NotNull
    @Override
    public FileListAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.file_format,parent,false);
        final ViewHolder holder = new ViewHolder(view);


        holder.card_file_message_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Toast.makeText(holder.itemView.getContext(),"点击了右card", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, FileDownload.class);
                intent.putExtra("file_class",file_list_list.get(holder.getAdapterPosition()).getFile_class());
                intent.putExtra("file_name",file_list_list.get(holder.getAdapterPosition()).getFile_name());
                context.startActivity(intent);
            }
        });

        holder.card_file_message_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(holder.itemView.getContext(),"点击了左card", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, FileDownload.class);
                intent.putExtra("file_class",file_list_list.get(holder.getAdapterPosition()).getFile_class());
                intent.putExtra("file_name",file_list_list.get(holder.getAdapterPosition()).getFile_name());
                context.startActivity(intent);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        FileList fileList = file_list_list.get(position);

        TimeTypeutils timeTypeutils = new TimeTypeutils();
        try {
            //Log.d("testFileList time:" ,timeTypeutils.toNormal(fileList.getFile_time()));
            holder.file_time.setText(timeTypeutils.toNormal(fileList.getFile_time()));
        } catch (ParseException e) {
            e.printStackTrace();
        }



        if(fileList.getFile_source().equals("1")){
            holder.file_source_left.setVisibility(View.INVISIBLE);
            holder.file_source_right.setVisibility(View.VISIBLE);
            holder.file_name_right.setText(fileList.getFile_name());
            holder.file_size_cloud_right.setText(fileList.getFile_size_cloud());
            holder.file_class_right.setImageResource(fileList.getFile_class());
        }else{
            holder.file_source_left.setVisibility(View.VISIBLE);
            holder.file_source_right.setVisibility(View.INVISIBLE);
            holder.file_name_left.setText(fileList.getFile_name());
            holder.file_size_cloud_left.setText(fileList.getFile_size_cloud());
            holder.file_class_left.setImageResource(fileList.getFile_class());
            if(fileList.getFile_source().equals("2")){
                holder.file_device_left.setImageResource(R.drawable.device_phone);
            }else {
                holder.file_device_left.setImageResource(R.drawable.device_com);
            }
        }

    }



    @Override
    public int getItemCount() {
        return this.file_list_list.size();
    }
}
