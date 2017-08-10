package com.dcoker.zone.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.dcoker.zone.R;
import com.dcoker.zone.Zone;
import com.dcoker.zone.adapter.MypagerAdapter;
import com.dcoker.zone.entity.User;
import com.dcoker.zone.home.activity.EditActivity;
import com.dcoker.zone.home.fragment.DisFragment;
import com.dcoker.zone.home.fragment.MineFragment;
import com.dcoker.zone.home.fragment.MsgFragment;
import com.dcoker.zone.home.fragment.StartFragment;
import com.dcoker.zone.weight.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity {

    SharedPreferences sp;
    @BindView(R.id.viewpager)
    NoScrollViewPager viewPager;

    @BindView(R.id.btn_start)
    RadioButton start;

    @BindView(R.id.image_add)
    ImageView add;


    @BindView(R.id.btn_dis)
    RadioButton dis;


    @BindView(R.id.btn_msg)
    RadioButton msg;

    @BindView(R.id.btn_mine)
    RadioButton mine;


    ArrayList<Fragment> flist = new ArrayList<>();
    public User user;


    private ArrayList<Fragment> fragments = new ArrayList<>();

    private Fragment currentFragment = new Fragment();

    private static final String CURRENT_FRAGMENT = "STATE_FRAGMENT_SHOW";
    private int currentIndex = 0;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        ButterKnife.bind(this);

        user = (User) getIntent().getExtras().get("user");

        Zone zone = (Zone) getApplication();
        zone.Setuser(user);
        sp = this.getPreferences(Context.MODE_PRIVATE);
//        getSupportActionBar().hide();
        start.setChecked(true);


        initview();
/*
        if (savedInstanceState != null) { // “内存重启”时调用

            //获取“内存重启”时保存的索引下标
            currentIndex = savedInstanceState.getInt(CURRENT_FRAGMENT,0);

            //注意，添加顺序要跟下面添加的顺序一样！！！！
            fragments.removeAll(fragments);
            fragments.add(fragmentManager.findFragmentByTag(0+""));
            fragments.add(fragmentManager.findFragmentByTag(1+""));
            fragments.add(fragmentManager.findFragmentByTag(2+""));

            //恢复fragment页面
            restoreFragment();


        }else{      //正常启动时调用


            showFragment();
        }*/

    }






  /*  @Override
    protected void onSaveInstanceState(Bundle outState) {

        //“内存重启”时保存当前的fragment名字
        outState.putInt(CURRENT_FRAGMENT,currentIndex);
        super.onSaveInstanceState(outState);
    }*/

    public void initview() {

        StartFragment startFragment = new StartFragment();
        DisFragment disFragment = new DisFragment();
        MsgFragment msgFragment = new MsgFragment();
        MineFragment mineFragment = new MineFragment();


        fragments.add(startFragment);
        fragments.add(disFragment);
        fragments.add(msgFragment);
        fragments.add(mineFragment);

        MypagerAdapter adapter = new MypagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.noScroll = true;
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(4);
        viewPager.setCurrentItem(0);
    }


    @OnClick({R.id.btn_start, R.id.btn_dis, R.id.btn_msg, R.id.btn_mine, R.id.image_add})
    public void onClick(View view) {


        switch (view.getId()) {

            case R.id.btn_start:
                currentIndex = 0;

                viewPager.setCurrentItem(0, false);


                break;

            case R.id.btn_dis:

                viewPager.setCurrentItem(1, false);


                break;

            case R.id.btn_msg:

                viewPager.setCurrentItem(2, false);

                break;

            case R.id.btn_mine:
                viewPager.setCurrentItem(3, false);
                break;
            case R.id.image_add:
                Intent intent = new Intent(HomeActivity.this, EditActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
                break;
        }
    }





    public User getUser() {

        return user;
    }


}
