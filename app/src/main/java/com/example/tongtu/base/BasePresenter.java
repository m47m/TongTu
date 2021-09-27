package com.example.tongtu.base;

public abstract class  BasePresenter<V extends BaseView> {
    private V view;

    public V getView(){
        return view;
    }

    public void attachView(V view_){
        this.view = view_;
    }

    public void detachView(){
        this.view = null;
    }

}
