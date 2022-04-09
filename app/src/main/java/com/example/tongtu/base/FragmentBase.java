package com.example.tongtu.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public abstract class FragmentBase <V extends BaseView, P extends BasePresenter<V>> extends Fragment {
    private P presenter;
    private V view;

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

        if (this.presenter == null) {
            this.presenter = create_presenter();
        }
        if (this.view == null) {
            this.view = create_view();
        }
        if (this.presenter != null && this.view != null) {
            this.presenter.attachView(this.view);
        }

        initView(rootview);
        initData(context);

        return rootview;
    }

    protected abstract int initLayout();//初始化布局

    protected abstract void initView(View view);//控件获取

    protected abstract void initData(Context context);//数据结构

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (this.presenter != null && this.view != null) {
            this.presenter.detachView();
        }
    }

    public P getPresenter() {
        return presenter;
    }

    public abstract P create_presenter();

    public abstract V create_view();
}
