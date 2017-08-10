package com.dcoker.zone.home.fragment;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.dcoker.zone.R;
import com.dcoker.zone.home.Listener.AppBarStateChangeListener;

public class MsgFragment extends BaseFragment {


  //  private CollapsingToolbarLayout collapsingToolbarLayout;
  //  private Toolbar toolbar;
   // private AppBarLayout appbar;


    @Override
    public int getLayoutResId() {

      /*  if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){//4.4 全透明状态栏
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0 全透明实现
            Window window = getActivity().getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);//calculateStatusColor(Color.WHITE, (int) alphaValue)
        }*/

        return R.layout.test22;
    }

    @Override
    public void finishCreateView(Bundle state) {
       // collapsingToolbarLayout = (CollapsingToolbarLayout)$(R.id.collapsing_toolbar_layout);
      //  toolbar =$(R.id.toolbar);
      //  appbar = $(R.id.appbar);
        //setSupportActionBar(toolbar);
     //   collapsingToolbarLayout.setCollapsedTitleGravity(Gravity.CENTER);//设置收缩后标题的位置
      //  collapsingToolbarLayout.setExpandedTitleGravity(Gravity.CENTER);////设置展开后标题的位置
     //   collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
      //  collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);


/*
        appbar.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                Log.d("STATE", state.name());
                if( state == State.EXPANDED ) {

                    //展开状态
                    collapsingToolbarLayout.setTitle("");

                }else if(state == State.COLLAPSED){

                    //折叠状态
                    collapsingToolbarLayout.setTitle("发现");

                }else {

                    //中间状态
                    collapsingToolbarLayout.setTitle("");
                }
            }
        });*/

    }

    @Override
    public void lazyLoad() {


    }







}
