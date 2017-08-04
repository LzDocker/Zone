package com.dcoker.zone.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dcoker.zone.R;
import com.dcoker.zone.config.NetConfig;
import com.dcoker.zone.entity.Article;
import com.dcoker.zone.entity.Attenlist;
import com.dcoker.zone.entity.CommentReply;
import com.dcoker.zone.entity.HotArticleBean;
import com.dcoker.zone.entity.IndexData;
import com.dcoker.zone.entity.User;
import com.dcoker.zone.entity.mineArticleBean;
import com.dcoker.zone.weight.GlideCircleTransform;
import com.dcoker.zone.weight.GlideRoundTransform;

import java.util.List;


/**
 * Created by Mr.Zhang on 2017/7/25.
 */

public class hotAdapter extends BaseAdapter {

    HotArticleBean data;
        Activity context;

        public hotAdapter(Activity context , HotArticleBean data){
            this.data =data;
            this.context=context;

        }


        @Override
        public int getCount() {
            return data.getData().getArticleCommentReplyList().size();
        }

        @Override
        public Object getItem(int i) {
            return data.getData().getArticleCommentReplyList().get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {

            ViewHolder viewHolder = null;
            if(convertView == null){
                viewHolder = new ViewHolder();
                convertView = context.getLayoutInflater().inflate(R.layout.item_dislist,null);
                viewHolder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
                viewHolder.iv_uicon = (ImageView) convertView.findViewById(R.id.iv_uicon);
                viewHolder.tv_desc = (TextView) convertView.findViewById(R.id.tv_desc);
                viewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
                viewHolder.tv_user_desc = (TextView) convertView.findViewById(R.id.tv_user_desc);
                viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);



                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            Article article = data.getData().getArticleCommentReplyList().get(i).getArticle();
            User user = data.getData().getArticleCommentReplyList().get(i).getUser();

            if(article.getPicture()!=null){
                viewHolder.iv_icon.setVisibility(View.VISIBLE);
                Glide.with(context).load(article.getPicture()).transform(new GlideRoundTransform(context)).placeholder(R.mipmap.my_nomal).error(R.mipmap.ic_launcher).centerCrop().into(viewHolder.iv_icon);
            }else{

                viewHolder.iv_icon.setVisibility(View.INVISIBLE);
            }
            viewHolder.tv_title.setText(article.getTitle());

           List<CommentReply> clist =  data.getData().getArticleCommentReplyList().get(i).getCommentRepyList();

            if(clist!=null){

                viewHolder.tv_desc.setText(". "+clist.size()+"评论 ."+article.getLikces()+"赞");
            }else {
                viewHolder.tv_desc.setText(". 0 评论 ."+article.getLikces()+"赞");
            }

            viewHolder.tv_name.setText(user.getUname());
            viewHolder.tv_user_desc.setText("发表"+user.getArticles()+"篇文章 . "+"获得了"+user.getLikes()+"个赞");
           // Glide.with(context).load(NetConfig.Base+user.getUicon()).transform(new GlideCircleTransform(context)).placeholder(R.mipmap.my_nomal).error(R.mipmap.ic_launcher).centerCrop().into(viewHolder.iv_uicon);

            Glide.with(context).load(NetConfig.Base+user.getUicon()).transform(new GlideCircleTransform(context)).placeholder(R.mipmap.my_nomal).error(R.mipmap.ic_launcher).into(viewHolder.iv_uicon);
            return convertView;
        }


        class ViewHolder {

            ImageView iv_icon;
            ImageView iv_uicon;
            TextView tv_desc;
            TextView tv_title;
            TextView tv_user_desc;
            TextView tv_name;




        }

    }



