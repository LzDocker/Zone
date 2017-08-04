package com.dcoker.zone.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dcoker.zone.R;
import com.dcoker.zone.entity.Article;
import com.dcoker.zone.entity.Attenlist;
import com.dcoker.zone.entity.IndexData;
import com.dcoker.zone.entity.mineArticleBean;
import com.dcoker.zone.weight.GlideRoundTransform;


/**
 * Created by Mr.Zhang on 2017/7/25.
 */

public class mineAdapter extends BaseAdapter {

    mineArticleBean data;
        Activity context;

        public mineAdapter(Activity context , mineArticleBean data){
            this.data =data;
            this.context=context;

        }


        @Override
        public int getCount() {
            return data.getData().getArticleList().size();
        }

        @Override
        public Object getItem(int i) {
            return data.getData().getArticleList().get(i);
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
                convertView = context.getLayoutInflater().inflate(R.layout.item_minelist,null);
                viewHolder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
                viewHolder.tv_desc = (TextView) convertView.findViewById(R.id.tv_desc);
                viewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            Article article = data.getData().getArticleList().get(i);
            Glide.with(context).load(article.getPicture()).transform(new GlideRoundTransform(context)).placeholder(R.mipmap.ic_launcher).error(R.mipmap.my_nomal).centerCrop().into(viewHolder.iv_icon);
            viewHolder.tv_title.setText(article.getTitle());
            viewHolder.tv_desc.setText("获得了"+article.getLikces()+"个赞  "+article.getComment()+"条评论");


            return convertView;
        }


        class ViewHolder {

            ImageView iv_icon;
            TextView tv_desc;
            TextView tv_title;

        }

    }



