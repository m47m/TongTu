package com.example.tongtu.pselect;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tongtu.LoginActivity;
import com.example.tongtu.MainActivity;
import com.example.tongtu.MyDeviceActivity;
import com.example.tongtu.PostActivity;
import com.example.tongtu.R;
import com.example.tongtu.SettingActivity;


import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PselectAdapter extends RecyclerView.Adapter<PselectAdapter.ViewHolder>{

    private List<Pselect> p_select_list;
    private Context context;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView select_name;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            select_name = (TextView)itemView.findViewById(R.id.text_select);
        }
    }

    public PselectAdapter(List<Pselect>p_select_list,Context f){
           this.p_select_list = p_select_list;
           this.context = f;
    }


    @NonNull
    @NotNull
    @Override
    public PselectAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.p_select,parent,false);
        final PselectAdapter.ViewHolder holder = new PselectAdapter.ViewHolder(view);

        pref = holder.itemView.getContext().getSharedPreferences("login_message", Context.MODE_PRIVATE);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Pselect pselect = p_select_list.get(position);
                //Toast.makeText(holder.itemView.getContext(),"点击了"+pselect.getSelect_name()+Integer.toString(position), Toast.LENGTH_SHORT).show();

                to_new_Activity(position);
            }
        });


        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PselectAdapter.ViewHolder holder, int position) {
        Pselect p_select = p_select_list.get(position);
        holder.select_name.setText(p_select.getSelect_name());
    }

    @Override
    public int getItemCount() {
        return this.p_select_list.size();
    }

    public void to_new_Activity(int index){

        switch (index){
            case 0://设置
                Intent intent_0 = new Intent(context, SettingActivity.class);
                context.startActivity(intent_0);
                break;
            case 1://我的设备
                Intent intent_1 = new Intent(context,MyDeviceActivity.class);
                context.startActivity(intent_1);
                break;
            case 2://检查更新
                break;
            case 3://软件说明
                break;

        }


    }
}
