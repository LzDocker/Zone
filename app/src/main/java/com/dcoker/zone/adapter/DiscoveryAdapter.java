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
import com.dcoker.zone.entity.Article;
import com.dcoker.zone.entity.CommentReply;
import com.dcoker.zone.entity.User;
import com.dcoker.zone.entity.articleCommentRepy;
import com.dcoker.zone.home.activity.ArticleDetilActivity;
import com.dcoker.zone.view.MyBanner;
import com.dcoker.zone.weight.GlideCircleTransform;
import com.dcoker.zone.weight.GlideRoundTransform;

import java.util.ArrayList;
import java.util.List;

import cn.kevin.banner.BannerViewPager;

/**
 * Created by zhangtuo on 2017/8/5.
 */

public class DiscoveryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private int TYPE_HEADER = 0x1000;
    private int TYPE_NORMAL = 0x1001;

    private Context context;
    List<articleCommentRepy> replyList = new ArrayList<>();

    public DiscoveryAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
//            View view = LayoutInflater.from(context).inflate(R.layout.fragment_dis_head, parent, false);
            MyBanner view = new MyBanner(context);
            return new HeaderViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_dislist, parent, false);
            return new NormalViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        articleCommentRepy repy = replyList.get(position);
        if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder headerVH = (HeaderViewHolder) holder;
            //TODO  header添加数据

        } else {
            NormalViewHolder normalVH = (NormalViewHolder) holder;

            Article article = repy.getArticle();
            List<CommentReply> clist = repy.getCommentRepyList();
            User user = repy.getUser();

            if (article.getPicture() != null) {
                normalVH.iv_icon.setVisibility(View.VISIBLE);
                Glide.with(context).load(article.getPicture())
                        .transform(new GlideRoundTransform(context))
                        .placeholder(R.mipmap.my_nomal)
                        .error(R.mipmap.ic_launcher)
                        .centerCrop()
                        .into(normalVH.iv_icon);
            } else {
                normalVH.iv_icon.setVisibility(View.INVISIBLE);
            }

            if (clist != null) {
                normalVH.tv_desc.setText(". " + clist.size() + "评论 ." + article.getLikces() + "赞");
            } else {
                normalVH.tv_desc.setText(". 0 评论 ." + article.getLikces() + "赞");
            }

            normalVH.tv_title.setText(article.getTitle());
            normalVH.tv_name.setText(user.getUname());
            normalVH.tv_user_desc.setText(article.getCreatetime());
            Glide.with(context).load(NetConfig.Base + user.getUicon())
                    .transform(new GlideCircleTransform(context))
                    .placeholder(R.mipmap.my_nomal)
                    .error(R.mipmap.ic_launcher)
                    .into(normalVH.iv_uicon);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ArticleDetilActivity.class);
                    intent.putExtra("atrid", replyList.get(position).getArticle().getId());
                    context.startActivity(intent);

                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        } else {
            return TYPE_NORMAL;
        }
    }

    @Override
    public int getItemCount() {
        return replyList == null ? 0 : replyList.size();
    }

    public void bindData(List<articleCommentRepy> replyList,Boolean isrefresh) {

        if(isrefresh){
            this.replyList.clear();
        }

        int start = 0;
        if(this.replyList!=null&&this.replyList.size()>0){
            start = this.replyList.size();
        }

        this.replyList.addAll(start,replyList);
        notifyDataSetChanged();



    }


    private class HeaderViewHolder extends RecyclerView.ViewHolder {

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }


    private class NormalViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_icon;
        ImageView iv_uicon;
        TextView tv_desc;
        TextView tv_title;
        TextView tv_user_desc;
        TextView tv_name;

        public NormalViewHolder(View itemView) {
            super(itemView);
            iv_icon = (ImageView) itemView.findViewById(R.id.iv_icon);
            iv_uicon = (ImageView) itemView.findViewById(R.id.iv_uicon);
            tv_desc = (TextView) itemView.findViewById(R.id.tv_desc);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_user_desc = (TextView) itemView.findViewById(R.id.tv_user_desc);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }
}
