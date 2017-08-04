package com.dcoker.zone.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.refresh.R;


/**
 * @auther zhangtuo
 */
public class SimpleFooterView extends BaseFooterView {

    private TextView mText;

    private ProgressBar progressBar;

    private View view;

    private SwipeRecyclerView swipeRecyclerView;

    public static Handler mHandler = new Handler(Looper.getMainLooper());
    public static final int DELAY_TIME = 500;


    public SimpleFooterView(Context context) {
        this(context, null);
    }

    public SimpleFooterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleFooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setLayoutParams(new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        view = LayoutInflater.from(getContext()).inflate(R.layout.module_base_layout_footer_view, this);
        progressBar = (ProgressBar) view.findViewById(R.id.footer_view_progressbar);
        mText = (TextView) view.findViewById(R.id.footer_view_tv);
        mText.setText("疯狂加载中。。。");
    }


    @Override
    public void setSwipeRecyclerView(SwipeRecyclerView swipeRecyclerView) {
        this.swipeRecyclerView = swipeRecyclerView;
    }

    @Override
    public void onLoadingMore() {
        progressBar.setVisibility(VISIBLE);
        mText.setVisibility(VISIBLE);
        mText.setText("疯狂加载中。。。");
        view.setOnClickListener(null);
    }

    public void showText() {
        progressBar.setVisibility(GONE);
        mText.setVisibility(VISIBLE);
    }

    @Override
    public void onNoMore() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showText();
                mText.setText("-- 没有更多了 --");
                view.setOnClickListener(null);
            }
        }, DELAY_TIME);
    }

    /**
     * error后pager自行减1
     */
    @Override
    public void onError() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showText();
                mText.setText("--  出错了  --");
                view.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //变更footerView ui，重新执行加载
                        onLoadingMore();
                        swipeRecyclerView.mListener.onLoadMore();
                    }
                });
            }
        }, DELAY_TIME);
    }

}
