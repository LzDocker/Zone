package com.dcoker.zone.home.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.dcoker.zone.Activity.HomeActivity;
import com.dcoker.zone.R;
import com.dcoker.zone.adapter.attenAdapter;
import com.dcoker.zone.adapter.indexAdapter;
import com.dcoker.zone.config.NetConfig;
import com.dcoker.zone.entity.Article;
import com.dcoker.zone.entity.Comment;
import com.dcoker.zone.entity.IndexData;
import com.dcoker.zone.entity.User;
import com.dcoker.zone.home.activity.ArticleDetilActivity;
import com.dcoker.zone.home.activity.FriendDetilActivity;
import com.dcoker.zone.listener.MyItemClickListener;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import butterknife.BindView;

public class StartFragment extends BaseFragment {

    @BindView(R.id.recycleview)
    RecyclerView recycleview;

    @BindView(R.id.ll_nodata)
    LinearLayout ll_nodata;

    User user;
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_start;
    }

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

    @Override
    public void lazyLoad() {



        OkGo.<String>get(NetConfig.INDEX)
                .cacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)
                .cacheKey("AttenFragment")
                .params("uid",user.getId())
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                        Gson gson = new Gson();
                        String s = response.body().toString();
                        IndexData indexdata =  gson.fromJson(/*Testdata.indexData*/s, IndexData.class);

                        if(indexdata.getData()!=null&&indexdata.getData().getState()==0){


                            ll_nodata.setVisibility(View.GONE);
                            recycleview.setVisibility(View.VISIBLE);
                            showList(indexdata);

                        }else{

                            ll_nodata.setVisibility(View.VISIBLE);
                            recycleview.setVisibility(View.GONE);
                        }
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    public void showList(final IndexData minedata){

//创建默认的线性LayoutManager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recycleview.setLayoutManager(mLayoutManager);
//如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        recycleview.setHasFixedSize(true);

        attenAdapter mAdapter = new attenAdapter(minedata,activity);

        recycleview.setAdapter(mAdapter);
        mAdapter.setItemListener(new MyItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                Intent intent;

                if(minedata.getData().getAttenList().get(postion).getArticle()!=null){

                    intent = new Intent(getActivity(),ArticleDetilActivity.class);
                    intent.putExtra("atrid",minedata.getData().getAttenList().get(postion).getArticle().getId());

                }else {

                    intent = new Intent(getActivity(),FriendDetilActivity.class);
                    intent.putExtra("User",minedata.getData().getAttenList().get(postion).getUser());
                }


                startActivity(intent);
            }
        });

    }
}
