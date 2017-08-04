package com.dcoker.zone.home.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.dcoker.zone.Activity.HomeActivity;
import com.dcoker.zone.R;
import com.dcoker.zone.adapter.MyrecycleAdapter;
import com.dcoker.zone.adapter.mineAdapter;
import com.dcoker.zone.config.NetConfig;
import com.dcoker.zone.entity.Article;
import com.dcoker.zone.entity.User;
import com.dcoker.zone.entity.mineArticleBean;
import com.dcoker.zone.home.activity.ArticleDetilActivity;
import com.dcoker.zone.home.activity.FriendDetilActivity;
import com.dcoker.zone.listener.MyItemClickListener;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Mr.Zhang on 2017/7/27.
 */

public class articleFrgment extends BaseFragment {

   /* @BindView(R.id.listview)
    ListView listView;
*/


   @BindView(R.id.recycleview)
    RecyclerView recyclerView;

    private User user;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_article;
    }

   // @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void finishCreateView(Bundle state) {
        try {
            user =((HomeActivity)getActivity()).getUser();
        }catch (Exception e){
            user =((FriendDetilActivity)getActivity()).getUser();
        }
        if(isVisible()){
            lazyLoad();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    public void showList(final mineArticleBean minedata){

//创建默认的线性LayoutManager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
//如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        recyclerView.setHasFixedSize(true);

        MyrecycleAdapter mAdapter = new MyrecycleAdapter(minedata,activity);

        recyclerView.setAdapter(mAdapter);
        mAdapter.setItemListener(new MyItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {

                Intent intent = new Intent(getActivity(),ArticleDetilActivity.class);
                intent.putExtra("atrid",minedata.getData().getArticleList().get(postion).getId());
                startActivity(intent);
            }
        });

    }



    @Override
    public void lazyLoad() {

        OkGo.<String>get(NetConfig.ARTLIST)
                .cacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)
                .cacheKey("mine")
                .params("uid",user.getId())
                .params("page",0)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                        Gson gson = new Gson();
                        String s = response.body().toString();
                        Log.i("*****",s);
                        mineArticleBean minedata =  gson.fromJson(s, mineArticleBean.class);

                        if(minedata.getData().getState()==0||minedata.getData().getArticleList()!=null){

                            //  listView.setVisibility(View.VISIBLE);
                            showList(minedata);

                        }else{
                            // listView.setVisibility(View.INVISIBLE);

                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);

                        Log.i("onerror",response.body().toString());
                    }
                });


    }



}
