package com.dcoker.zone.home.fragment;

import android.os.Bundle;
import android.util.Log;;

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
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import java.util.List;

import butterknife.BindView;

public class StartFragment extends BaseFragment {

    @BindView(R.id.refresh)
    SwipeRecyclerView recyclerView;
    AttenAdapter mAdapter;

    User user;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_start;
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

        mAdapter = new AttenAdapter(activity);
        recyclerView.setAdapter(mAdapter);

        recyclerView.setOnLoadListener(new SwipeRecyclerView.OnLoadListener() {
            @Override
            public void onRefresh() {
                initData();
            }

            @Override
            public void onLoadMore() {

            }
        });

        recyclerView.setRefreshing(true);
    }

    private void initData() {
        OkGo.<String>get(NetConfig.INDEX)
                .cacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)
                .cacheKey("AttenFragment")
                .params("uid", user.getId())
                .tag(this)
                .execute(new StringCallback() {


                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        recyclerView.setEmptyView("你还没有关注好友哦");

                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        recyclerView.isLoadMoreEnable = false;

                    }

                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);


                    }

                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                        Gson gson = new Gson();
                        String s = response.body().toString();
                        Log.i("StartFragment",s);
                        IndexData indexdata = gson.fromJson(/*Testdata.indexData*/s, IndexData.class);

                        if (indexdata.getData() != null && indexdata.getData().getState() == 0) {
                            List<Attenlist> attenList = indexdata.getData().getAttenList();
                            mAdapter.bindData(attenList,true);
                            recyclerView.complete();
                        } else {
                            recyclerView.setEmptyView("你还没有关注好友哦");
                            recyclerView.complete();
                        }
                    }
                });
    }

}
