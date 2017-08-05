package com.dcoker.zone.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dcoker.zone.R;
import com.dcoker.zone.config.NetConfig;
import com.dcoker.zone.entity.Attenlist;
import com.dcoker.zone.entity.IndexData;
import com.dcoker.zone.home.activity.ArticleDetilActivity;
import com.dcoker.zone.home.activity.FriendDetilActivity;
import com.dcoker.zone.weight.GlideCircleTransform;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr.Zhang on 2017/7/31.
 */

public class AttenAdapter extends RecyclerView.Adapter<AttenAdapter.ViewHolder> {

    List<Attenlist> mList = new ArrayList<>();
    public Context context;
    boolean flag;

    public AttenAdapter(Context context) {
        this.context = context;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public AttenAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_attenlist, viewGroup, false);
        return new AttenAdapter.ViewHolder(view);
    }

    public void bindData(List<Attenlist> mList, boolean b) {
        this.mList.addAll(mList);
        this.flag = b;
    }


    //获取数据的数量
    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        Attenlist index = mList.get(position);

        Glide.with(context).load(NetConfig.Base + index.getUser().getUicon()).transform(new GlideCircleTransform(context)).placeholder(R.mipmap.my_nomal).error(R.mipmap.ic_launcher).into(holder.imageView);
        holder.tv_title.setText(index.getUser().getUname());

        if (index.getArticle() != null && index.getArticle().getTitle() != null) {
            holder.tv_desc.setText("最新文章：" + index.getArticle().getTitle());
        } else if (index.getComment() != null) {
            holder.tv_desc.setText("有最新评论" + index.getComment().getDescption());
        } else {
            holder.tv_desc.setText("这货很懒~ 咩有动态");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;

                if (flag) {
                    if (mList.get(position).getArticle() != null) {
                        intent = new Intent(context, ArticleDetilActivity.class);
                        intent.putExtra("atrid", mList.get(position).getArticle().getId());
                    } else {
                        intent = new Intent(context, FriendDetilActivity.class);
                        intent.putExtra("User", mList.get(position).getUser());
                    }
                } else {
                    intent = new Intent(context, FriendDetilActivity.class);
                    intent.putExtra("User", mList.get(position).getUser());
                }

                context.startActivity(intent);
            }
        });

    }


    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_title;
        public TextView tv_desc;
        public ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            tv_desc = (TextView) view.findViewById(R.id.tv_desc);
            tv_title = (TextView) view.findViewById(R.id.tv_title);
            imageView = (ImageView) view.findViewById(R.id.iv_icon);
        }
    }


}
