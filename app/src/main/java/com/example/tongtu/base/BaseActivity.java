package com.example.tongtu.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.Nullable;

public abstract class BaseActivity<V extends BaseView, P extends BasePresenter<V>> extends Activity {

    private P presenter;
    private V view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (this.presenter == null) {
            this.presenter = create_presenter();
        }
        if (this.view == null) {
            this.view = create_view();
        }
        if (this.presenter != null && this.view != null) {
            this.presenter.attachView(this.view);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (this.presenter != null && this.view != null) {
            this.presenter.detachView();
        }
    }

    public void setupUI(View view) {
        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(BaseActivity.this);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        if (activity.getCurrentFocus() != null)
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(), 0);
    }

    public P getPresenter() {
        return presenter;
    }

    public abstract P create_presenter();

    public abstract V create_view();
}
