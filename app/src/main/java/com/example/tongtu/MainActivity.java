package com.example.tongtu;

import android.app.ActionBar;
import android.app.Activity;
import android.app.StatusBarManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tongtu.base.BaseActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

//import com.example.tongtu.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupUI(((ViewGroup) findViewById(android.R.id.content)).getChildAt(0));

        pref = this.getSharedPreferences("login_message",MODE_PRIVATE);
        String test = pref.getString("token","11");

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        NavController navController = Navigation.findNavController(this,R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(bottomNavigationView,navController);
    }

    public void setupUI(View view) {
        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(MainActivity.this);
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


    public void post_file(View view) {
        Intent intent_post = new Intent(MainActivity.this,PostActivity.class);
//        startActivity(intent_post);
        startActivityForResult(intent_post,3);
    }

    public void search_file(View view){
        Intent intent_search = new Intent(MainActivity.this,SearchActivity.class);
        startActivityForResult(intent_search,2);
    }

    public void document_search(View v){
        Toast.makeText(v.getContext(),"www",Toast.LENGTH_SHORT).show();
    }

    public void photo_search(View v){
        Toast.makeText(v.getContext(),"www",Toast.LENGTH_SHORT).show();
    }

    public void video_search(View v){
        Toast.makeText(v.getContext(),"www",Toast.LENGTH_SHORT).show();
    }

    public void on_logout(View v){
        editor = pref.edit();
        editor.putBoolean("islogin",false);
        editor.apply();
        editor.clear();
        Intent intent_login =new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent_login);
        finish();
    }
}