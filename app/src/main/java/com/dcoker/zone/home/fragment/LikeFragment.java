package com.dcoker.zone.home.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dcoker.zone.Activity.HomeActivity;
import com.dcoker.zone.R;
import com.dcoker.zone.adapter.LikeAdapter;
import com.dcoker.zone.adapter.attenAdapter;
import com.dcoker.zone.config.NetConfig;
import com.dcoker.zone.entity.Article;
import com.dcoker.zone.entity.ArticledetilBean;
import com.dcoker.zone.entity.IndexData;
import com.dcoker.zone.entity.User;
import com.dcoker.zone.home.activity.ArticleDetilActivity;
import com.dcoker.zone.home.activity.FriendDetilActivity;
import com.dcoker.zone.listener.MyItemClickListener;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import butterknife.BindView;

/**
 * Created by Mr.Zhang on 2017/7/31.
 */

public class LikeFragment extends BaseFragment {


    @BindView(R.id.recycleview)
    RecyclerView recycleview;

    User user;
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_atten;
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

        OkGo.<String>get(NetConfig.LIIKELIST)
                .cacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)
                .cacheKey("lIKEFragment")
                .params("uid",user.getId())
                .params("page",0)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        String s = response.body().toString();
                        ArticledetilBean data =  gson.fromJson(/*Testdata.indexData*/s, ArticledetilBean.class);

                        if(data.getData().getArticleCommentReplyList()!=null&&data.getData().getArticleCommentReplyList().size()>0){

                            showList(data);
                        }else{

                        }
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    public void showList(final ArticledetilBean data){

//创建默认的线性LayoutManager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recycleview.setLayoutManager(mLayoutManager);
//如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        recycleview.setHasFixedSize(true);

        LikeAdapter mAdapter = new LikeAdapter(data,activity);

        recycleview.setAdapter(mAdapter);
        mAdapter.setItemListener(new MyItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {

                Intent intent = new Intent(getActivity(),ArticleDetilActivity.class);
                Article article = data.getData().getArticleCommentReplyList().get(postion).getArticle();
                if(article!=null){
                    intent.putExtra("atrid",article.getId());
                    startActivity(intent);
                }else{

                    //
                }


            }
        });

    }


}
