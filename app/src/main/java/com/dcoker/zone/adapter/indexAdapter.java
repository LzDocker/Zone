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
import com.dcoker.zone.entity.Attenlist;
import com.dcoker.zone.entity.IndexData;
import com.dcoker.zone.weight.GlideCircleTransform;
import com.dcoker.zone.weight.GlideRoundTransform;


/**
 * Created by Mr.Zhang on 2017/7/25.
 */

public class indexAdapter extends BaseAdapter {

        IndexData data;
        Activity context;

        public indexAdapter(Activity context ,IndexData data){
            this.data =data;
            this.context=context;

        }


        @Override
        public int getCount() {
            return data.getData().getAttenList().size();
        }

        @Override
        public Object getItem(int i) {
            return data.getData().getAttenList().get(i);
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
                convertView = context.getLayoutInflater().inflate(R.layout.item_attenlist,null);
                viewHolder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
                viewHolder.tv_desc = (TextView) convertView.findViewById(R.id.tv_desc);
                viewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            Attenlist index = data.getData().getAttenList().get(i);
            Glide.with(context).load(NetConfig.Base+index.getUser().getUicon()).transform(new GlideCircleTransform(context)).placeholder(R.mipmap.my_nomal).error(R.mipmap.ic_launcher).into(viewHolder.iv_icon);
            viewHolder.tv_title.setText(index.getUser().getUname());



            if(index.getArticle()!=null&&index.getArticle().getTitle()!=null){

                viewHolder.tv_desc.setText("最新文章："+index.getArticle().getTitle());
            }else if (index.getComment()!=null){

                viewHolder.tv_desc.setText("有最新评论"+index.getComment().getDescption());
            }else{
                viewHolder.tv_desc.setText("这货很懒~ 咩有动态");
            }


            return convertView;
        }


        class ViewHolder {

            ImageView iv_icon;
            TextView tv_desc;
            TextView tv_title;

        }

    }



