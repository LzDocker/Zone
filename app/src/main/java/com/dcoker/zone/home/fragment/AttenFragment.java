package com.dcoker.zone.home.fragment;

import android.os.Bundle;

import com.dcoker.zone.Activity.HomeActivity;
import com.dcoker.zone.R;
import com.dcoker.zone.adapter.AttenAdapter;
import com.dcoker.zone.config.NetConfig;
import com.dcoker.zone.entity.Attenlist;
import com.dcoker.zone.entity.IndexData;
import com.dcoker.zone.entity.User;
import com.dcoker.zone.home.activity.FriendDetilActivity;
import com.dcoker.zone.widget.SwipeRecyclerView;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Mr.Zhang on 2017/7/31.
 */

public class AttenFragment extends BaseFragment {


    @BindView(R.id.refresh)
    SwipeRecyclerView recyclerView;
    AttenAdapter mAdapter;

    int size = 1;

    User user;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_atten;
    }

    @Override
    public void finishCreateView(Bundle state) {
        try {
            user = ((HomeActivity) getActivity()).getUser();
        } catch (Exception e) {
            user = ((FriendDetilActivity) getActivity()).getUser();
        }

        if (isVisible()) {
            lazyLoad();
        }
    }

    @Override
    public void lazyLoad() {
        mAdapter = new AttenAdapter(getActivity());
        recyclerView.setAdapter(mAdapter);

        recyclerView.setOnLoadListener(new SwipeRecyclerView.OnLoadListener() {
            @Override
            public void onRefresh() {
                size = 1;
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

    private void initData(int i) {
        OkGo.<String>get(NetConfig.INDEX)
                .cacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)
                .cacheKey("AttenFragment")
                .params("uid", user.getId())
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                        Gson gson = new Gson();
                        String s = response.body().toString();
                        IndexData indexdata = gson.fromJson(/*Testdata.indexData*/s, IndexData.class);
                        if (indexdata.getData() != null && indexdata.getData().getState() == 0) {
                            List<Attenlist> attenList = indexdata.getData().getAttenList();
                            mAdapter.bindData(attenList,false);
                            recyclerView.complete();
                        } else {
                            recyclerView.setEmptyView("暂无数据～");
                            recyclerView.complete();
                        }
                    }
                });
    }

}
