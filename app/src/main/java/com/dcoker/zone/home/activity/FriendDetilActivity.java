package com.dcoker.zone.home.activity;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dcoker.zone.R;
import com.dcoker.zone.adapter.MinePagerAdapter;
import com.dcoker.zone.config.NetConfig;
import com.dcoker.zone.entity.User;
import com.dcoker.zone.home.fragment.AttenFragment;
import com.dcoker.zone.home.fragment.BaseFragment;
import com.dcoker.zone.home.fragment.LikeFragment;
import com.dcoker.zone.home.fragment.articleFrgment;
import com.dcoker.zone.weight.GlideCircleTransform;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FriendDetilActivity extends AppCompatActivity {


    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<String> titleList = new ArrayList<String>();
    private List<BaseFragment> fmList=new ArrayList<BaseFragment>();



    @BindView(R.id.iv_icon)
    ImageView iv_icon;

    @BindView(R.id.iv_edit)
    ImageView iv_edit;

    @BindView(R.id.tv_name)
    TextView tv_name;

    @BindView(R.id.textView)
    TextView textView;

    private User user;


    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_mine_process2);
        ButterKnife.bind(this);
        getSupportActionBar().hide();

        user  = (User) getIntent().getExtras().get("User");

        inintview();
    }



    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    public void inintview(){

        iv_edit.setVisibility(View.GONE);
        tv_name.setVisibility(View.GONE);
       // tv_name.setText(user.getUname());

        if(user.getUname()!=null){
            textView.setText(user.getUname());
        }

        Glide.with(this).load(NetConfig.Base+user.getUicon()).transform(new GlideCircleTransform(this)).placeholder(R.mipmap.my_nomal).error(R.mipmap.ic_launcher).into(iv_icon);

        Log.i("uuuu----",NetConfig.Base+user.getUicon());


        viewPager= (ViewPager)findViewById(R.id.viewpager);
        tabLayout= (TabLayout)findViewById(R.id.tabs);

        //初始化标题以及Fragment内容


        articleFrgment articleFrgment = new articleFrgment();
        fmList.add(articleFrgment);
        AttenFragment attenFragment = new AttenFragment();
        fmList.add(attenFragment);
        LikeFragment likeFragment = new LikeFragment();
        fmList.add(likeFragment);

        titleList.add("文章");
        titleList.add("关注");
        titleList.add("喜欢");



        viewPager.setAdapter(new MinePagerAdapter(getSupportFragmentManager(),fmList,titleList));
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(0);

    }

    public User getUser(){

        return  user;
    }


}
