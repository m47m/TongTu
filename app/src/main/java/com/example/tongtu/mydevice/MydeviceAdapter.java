package com.example.tongtu.mydevice;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tongtu.ChangeDeviceActivity;
import com.example.tongtu.MainActivity;
import com.example.tongtu.MyDeviceActivity;
import com.example.tongtu.PostActivity;
import com.example.tongtu.R;
import com.example.tongtu.utils.TimeTypeutils;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.util.List;

public class MydeviceAdapter extends RecyclerView.Adapter<MydeviceAdapter.ViewHolder>{

    private List<Mydevice> my_device_list;
    private Context context;
    // private SharedPreferences pref;
    //private SharedPreferences.Editor editor;

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView device_name;
        TextView deivce_alias;
        TextView device_time;
        ImageView img_deivce_type;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            device_name = (TextView)itemView.findViewById(R.id.text_device_name);
            deivce_alias = (TextView)itemView.findViewById(R.id.text_device_alias);
            device_time = (TextView)itemView.findViewById(R.id.text_device_time);
            img_deivce_type = (ImageView)itemView.findViewById(R.id.img_device_class);
        }
    }

    public MydeviceAdapter(List<Mydevice>my_device_list,Context f){
           this.my_device_list = my_device_list;
           this.context = f;
    }


    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_device,parent,false);
        final ViewHolder holder = new ViewHolder(view);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Intent intent_device = new Intent(context, ChangeDeviceActivity.class);
                intent_device.putExtra("selected_id",my_device_list.get(position).getDevice_id());
                context.startActivity(intent_device);
            }
        });


        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Mydevice mydevice = my_device_list.get(position);
        holder.device_name.setText(mydevice.getDevice_name());
        TimeTypeutils timeTypeutils = new TimeTypeutils();
        try {
            holder.device_time.setText(timeTypeutils.toNormal(mydevice.getDevice_time()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.deivce_alias.setText(mydevice.getDevice_alias());

        if(mydevice.getDevice_type().equals("1")){
            holder.img_deivce_type.setImageResource(R.drawable.device_phone);
        }else if(mydevice.getDevice_type().equals("2")){
            holder.img_deivce_type.setImageResource(R.drawable.device_com);
        }

    }

    @Override
    public int getItemCount() {
        return this.my_device_list.size();
    }



}
