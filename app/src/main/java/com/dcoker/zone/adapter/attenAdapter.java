package com.dcoker.zone.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dcoker.zone.R;
import com.dcoker.zone.config.NetConfig;
import com.dcoker.zone.entity.Article;
import com.dcoker.zone.entity.Attenlist;
import com.dcoker.zone.entity.IndexData;
import com.dcoker.zone.entity.mineArticleBean;
import com.dcoker.zone.listener.MyItemClickListener;
import com.dcoker.zone.weight.GlideCircleTransform;
import com.dcoker.zone.weight.GlideRoundTransform;

/**
 * Created by Mr.Zhang on 2017/7/31.
 */

public class attenAdapter extends RecyclerView.Adapter<attenAdapter.ViewHolder> {
    public IndexData indexData= null;
    public Context context;
    private MyItemClickListener mListener;

    public void setItemListener(MyItemClickListener mListener){
        this.mListener = mListener;
    }

    public attenAdapter(IndexData indexData, Context context) {
        this.indexData = indexData;
        this.context = context;
    }
    //创建新View，被LayoutManager所调用
    @Override
    public attenAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_attenlist,viewGroup,false);
        attenAdapter.ViewHolder vh = new attenAdapter.ViewHolder(view,mListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Attenlist index = indexData.getData().getAttenList().get(position);
        Glide.with(context).load(NetConfig.Base+index.getUser().getUicon()).transform(new GlideCircleTransform(context)).placeholder(R.mipmap.my_nomal).error(R.mipmap.ic_launcher).into(holder.imageView);
        holder.tv_title.setText(index.getUser().getUname());
        if(index.getArticle()!=null&&index.getArticle().getTitle()!=null){

            holder.tv_desc.setText("最新文章："+index.getArticle().getTitle());
        }else if (index.getComment()!=null){

            holder.tv_desc.setText("有最新评论"+index.getComment().getDescption());
        }else{
            holder.tv_desc.setText("这货很懒~ 咩有动态");
        }


    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        return indexData.getData().getAttenList().size();
    }
    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tv_title;
        public TextView tv_desc;
        public ImageView imageView;



        public ViewHolder(View view,MyItemClickListener mListener){
            super(view);
            tv_desc = (TextView) view.findViewById(R.id.tv_desc);
            tv_title = (TextView) view.findViewById(R.id.tv_title);
            imageView = (ImageView) view.findViewById(R.id.iv_icon);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mListener != null){
                mListener.onItemClick(v,getPosition());
            }
        }
    }




}
