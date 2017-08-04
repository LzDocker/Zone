package com.dcoker.zone.home.fragment;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by admin on 2016/12/6.
 */

public abstract class BaseFragment extends Fragment {

    public View parentView;

    public FragmentActivity activity;

    // 标志位 标志已经初始化完成。
    public boolean isPrepared;

    //标志位 fragment是否可见
    public boolean isVisible;

    public Unbinder bind;


    public abstract
    @LayoutRes
    int getLayoutResId();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        parentView = inflater.inflate(getLayoutResId(), container, false);
        initStatBar();
        activity = getSupportActivity();
        return parentView;
    }

    public void initStatBar(){};

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        bind = ButterKnife.bind(this, view);
        finishCreateView(savedInstanceState);
    }


    public abstract void finishCreateView(Bundle state);


    @Override
    public void onResume() {

        super.onResume();
    }


    @Override
    public void onDestroyView() {

        super.onDestroyView();
        bind.unbind();
    }


    @Override
    public void onAttach(Activity activity) {

        super.onAttach(activity);
        this.activity = (FragmentActivity) activity;
    }


    @Override
    public void onDetach() {

        super.onDetach();
        this.activity = null;
    }


    public FragmentActivity getSupportActivity() {

        return super.getActivity();
    }


    public android.app.ActionBar getSupportActionBar() {

        return getSupportActivity().getActionBar();
    }


    public Context getApplicationContext() {

        return this.activity == null
                ? (getActivity() == null ? null :
                getActivity().getApplicationContext())
                : this.activity.getApplicationContext();
    }


    /**
     * Fragment数据的懒加载
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }


    public void onVisible() {

       // lazyLoad();
    }


    public void lazyLoad() {}


    public void onInvisible() {}


    public void loadData() {}


    public void showProgressBar() {}


    public void hideProgressBar() {}


    public void initRecyclerView() {}


    public void initRefreshLayout() {}


    public void finishTask() {}


    @SuppressWarnings("unchecked")
    public <T extends View> T $(int id) {

        return (T) parentView.findViewById(id);
    }
}
