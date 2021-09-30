package com.example.tongtu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements View.OnClickListener {
////  声明ViewPager
//    private ViewPager mViewPager;
////    适配器
//    private FragmentPagerAdapter mAdapter;
////    装载fragment的集合
//    private List<Fragment> mFragments;
//
////    四个tab相应的布局
//    private LinearLayout tab1;
//    private LinearLayout tab2;
//    private LinearLayout tab3;
//    private LinearLayout tab4;
//
////    四个tab相应的button
//    private ImageButton mImg1;
//    private ImageButton mImg2;
//    private ImageButton mImg3;
//    private ImageButton mImg4;
//
//
//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
//        initViews();//初始化控件
//        initEvents();//初始化事件
//        initDatas();//初始化数据
//
    }
//    private void initDatas(){
//        mFragments = new ArrayList<>();
////
//        mFragments.add(new PageFragment1());
//        mFragments.add(new PageFragment2());
//        mFragments.add(new PageFragment3());
//        mFragments.add(new PageFragment4());
//
////        初始化适配器
//        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
//            @NonNull
//            @NotNull
//            @Override
//            public Fragment getItem(int position) {//从集合中获取对应位置的Fragment
//                return mFragments.get(position);
//            }
//
//            @Override
//            public int getCount() {//获得集合中Fragment的总数
//                return mFragments.size();
//            }
//        };
//
////        ViewPager的适配器
//        mViewPager.setAdapter(mAdapter);
////        ViewPager的切换监听
//        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
////            页面滚动事件
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
////            页面选中事件
//            @Override
//            public void onPageSelected(int position) {
//                //设置position对应的集合中的Fragment
//                mViewPager.setCurrentItem(position);
//                resetImgs();
//                selectTab(position);
//            }
////            页面滚动状态转换事件
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
//    }
//
//    private void initEvents() {
//        //设置四个Tab的点击事件
//        tab1.setOnClickListener(this);
//        tab2.setOnClickListener(this);
//        tab3.setOnClickListener(this);
//        tab4.setOnClickListener(this);
//
//    }
//
//    //初始化控件
//    private void initViews() {
//        mViewPager = (ViewPager) findViewById(R.id.);
//
//        tab1 = (LinearLayout) findViewById(R.id.id_tab1);
//        tab2 = (LinearLayout) findViewById(R.id.id_tab2);
//        tab3 = (LinearLayout) findViewById(R.id.id_tab3);
//        tab4 = (LinearLayout) findViewById(R.id.id_tab4);
//
//        mImg1 = (ImageButton) findViewById(R.id.id_tab_img1);
//        mImg2 = (ImageButton) findViewById(R.id.id_tab_img2);
//        mImg3 = (ImageButton) findViewById(R.id.id_tab_img3);
//        mImg4 = (ImageButton) findViewById(R.id.id_tab_img4);
//
//    }
//
//    //将四个ImageButton设置为灰色
//    private void resetImgs() {
////        切换图片
//        mImg1.setImageResource(R.mipmap.icon2);
//        mImg2.setImageResource(R.mipmap.icon2);
//        mImg3.setImageResource(R.mipmap.icon2);
//        mImg4.setImageResource(R.mipmap.icon2);
//    }
////    切换页面
//    private void selectTab(int i) {
//        //根据点击的Tab设置对应的ImageButton为绿色
//        switch (i) {
//            case 0:
//                mImg1.setImageResource(R.mipmap.icon1);
//                    break;
//                case 1:
//                mImg2.setImageResource(R.mipmap.icon1);
//                break;
//            case 2:
//                mImg3.setImageResource(R.mipmap.icon1);
//                break;
//            case 3:
//                mImg4.setImageResource(R.mipmap.icon1);
//                break;
//        }
//        //设置当前点击的Tab所对应的页面
//        mViewPager.setCurrentItem(i);
//    }
//
//
    @Override
    public void onClick(View v) {
//        //先将四个ImageButton置为灰色
//        resetImgs();
//
//        //根据点击的Tab切换不同的页面及设置对应的ImageButton为绿色
//        switch (v.getId()) {
//            case R.id.id_tab1:
//                selectTab(0);
//                break;
//            case R.id.id_tab2:
//                selectTab(1);
//                break;
//            case R.id.id_tab3:
//                selectTab(2);
//                break;
//            case R.id.id_tab4:
//                selectTab(3);
//                break;
//        }
    }
}