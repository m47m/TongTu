package com.example.tongtu.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment {
    //获取TAG的fragment名称
    protected final String TAG = this.getClass().getSimpleName();
    public Context context;

    @Override
    public void onAttach(@NonNull Context ctx) {
        super.onAttach(ctx);
        context = ctx;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(initLayout(), container, false);
        initData(context);
        initView(rootview);
        return rootview;
    }

    protected abstract int initLayout();//初始化布局

    protected abstract void initView(View view);//控件获取

    protected abstract void initData(Context context);//数据结构

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
