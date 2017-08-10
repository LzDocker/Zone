package com.dcoker.zone.home.fragment;

import android.os.Bundle;
import android.util.Log;

import com.dcoker.zone.R;
import com.dcoker.zone.adapter.DiscoveryAdapter;
import com.dcoker.zone.config.NetConfig;
import com.dcoker.zone.entity.HotArticleBean;
import com.dcoker.zone.entity.articleCommentRepy;
import com.dcoker.zone.widget.SwipeRecyclerView;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;

import java.util.List;

import butterknife.BindView;

public class DisFragment extends BaseFragment {

    @BindView(R.id.refresh)
    SwipeRecyclerView recyclerView;
    DiscoveryAdapter adapter;
    int size = 0;


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_dis;
    }

    @Override
    public void finishCreateView(Bundle state) {
        if (isVisible()) {
            lazyLoad();
        }
    }

    @Override
    public void lazyLoad() {
        adapter = new DiscoveryAdapter(getActivity());
        recyclerView.setAdapter(adapter);

        recyclerView.setOnLoadListener(new SwipeRecyclerView.OnLoadListener() {
            @Override
            public void onRefresh() {
                size = 0;
                initData(size);

            }

            @Override
            public void onLoadMore() {
                size = size + 1;
                initData(size);

            }
        });

        recyclerView.setRefreshing(true);
    }

    /**
     * 根据页码请求数据
     *
     * @param size
     */
    private void initData(final int size) {

        OkGo.<String>get(NetConfig.hotarticle)
                .cacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)
                .cacheKey("AttenFragment")
                .params("GroupTag", "未分类")
                .params("page", size)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                        Gson gson = new Gson();
                        String s = response.body().toString();
                        HotArticleBean indexdata = gson.fromJson(/*Testdata.indexData*/s, HotArticleBean.class);

                        Log.i("-----------000", s);

                        if (indexdata.getData() != null && indexdata.getData().getState() == 0) {
                            List<articleCommentRepy> replyList = indexdata.getData().getArticleCommentReplyList();
                            //造一条数据，作为header的占位
                            if(size == 0){
                                articleCommentRepy repy = new articleCommentRepy();
                                replyList.add(0,repy);
                                adapter.bindData(replyList,true);
                            }else{
                                adapter.bindData(replyList,false);
                            }
                            recyclerView.complete();
                            if (replyList.size()<10){
                                recyclerView.onNoMore();
                            }
                        } else {
                            recyclerView.setEmptyView("没有更多了～");
                            recyclerView.complete();
                        }
                    }
                });
    }

}
