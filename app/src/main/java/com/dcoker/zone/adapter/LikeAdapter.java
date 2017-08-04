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
import com.dcoker.zone.entity.Article;
import com.dcoker.zone.entity.ArticledetilBean;
import com.dcoker.zone.entity.mineArticleBean;
import com.dcoker.zone.listener.MyItemClickListener;
import com.dcoker.zone.weight.GlideRoundTransform;

/**
 * Created by Mr.Zhang on 2017/7/28.
 */

public class LikeAdapter extends RecyclerView.Adapter<LikeAdapter.ViewHolder> {
    public ArticledetilBean mineBean = null;
    public Context context;
    private MyItemClickListener mListener;

    public void setItemListener(MyItemClickListener mListener){
        this.mListener = mListener;
    }

    public LikeAdapter(ArticledetilBean mineBean, Context context) {
        this.mineBean = mineBean;
        this.context = context;
    }
    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_minelist,viewGroup,false);
        ViewHolder vh = new ViewHolder(view,mListener);
        return vh;
    }
    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Article article = mineBean.getData().getArticleCommentReplyList().get(position).getArticle();

        viewHolder.tv_title.setText(article.getTitle());
        viewHolder.tv_desc.setText("."+article.getLikces()+"赞 ."+article.getComment()+"评");
        Glide.with(context).load(article.getPicture()).transform(new GlideRoundTransform(context)).placeholder(R.mipmap.ic_launcher).error(R.mipmap.my_nomal).centerCrop().into(viewHolder.imageView);

    }
    //获取数据的数量
    @Override
    public int getItemCount() {
        return mineBean.getData().getArticleCommentReplyList().size();
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
