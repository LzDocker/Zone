package com.dcoker.zone.home.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dcoker.zone.R;
import com.dcoker.zone.adapter.MinePagerAdapter;
import com.dcoker.zone.home.fragment.BaseFragment;
import com.dcoker.zone.home.fragment.articleFrgment;

import java.util.ArrayList;
import java.util.List;

public class CommentDetilActivity extends AppCompatActivity {


    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<String> titleList = new ArrayList<String>();
    private List<BaseFragment> fmList=new ArrayList<BaseFragment>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_mine_process2);
        getSupportActionBar().hide();


        viewPager= (ViewPager)findViewById(R.id.viewpager);
        tabLayout= (TabLayout)findViewById(R.id.tabs);

        //初始化标题以及Fragment内容


        articleFrgment articleFrgment = new articleFrgment();
        fmList.add(articleFrgment);
        articleFrgment startFragment1 = new articleFrgment();
        fmList.add(startFragment1);
        articleFrgment startFragment2 = new articleFrgment();
        fmList.add(startFragment2);

        titleList.add("文章");
        titleList.add("关注");
        titleList.add("粉丝");


        viewPager.setAdapter(new MinePagerAdapter(getSupportFragmentManager(),fmList,titleList));
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(0);

    }
}
