package com.dcoker.zone.adapter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dcoker.zone.R;
import com.dcoker.zone.config.NetConfig;
import com.dcoker.zone.entity.ArticledetilBean;
import com.dcoker.zone.entity.Reply;
import com.dcoker.zone.entity.User;
import com.dcoker.zone.home.activity.ArticleDetilActivity;
import com.dcoker.zone.home.activity.FriendDetilActivity;
import com.dcoker.zone.weight.GlideCircleTransform;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr.Zhang on 2017/8/1.
 */

public class commentAdapter  extends BaseAdapter {

    ArticledetilBean articleData;
    Activity context;
    public int pos;


    public commentAdapter(Activity context, ArticledetilBean articleData){
        this.context=context;
        this.articleData = articleData;
    }


    @Override
    public int getCount() {
        return articleData.getData().getCommentReplyList().size();
    }

    @Override
    public Object getItem(int i) {
        return articleData.getData().getCommentReplyList().get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {

        commentAdapter.ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new commentAdapter.ViewHolder();
            convertView = context.getLayoutInflater().inflate(R.layout.list_item,null);
            viewHolder.iv_icon = (ImageView) convertView.findViewById(R.id.ivm_icon);
            viewHolder.user_desc = (TextView) convertView.findViewById(R.id.user_desc);
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tv_comments = (TextView) convertView.findViewById(R.id.tv_comments);
            viewHolder.tv_reply = (TextView) convertView.findViewById(R.id.tv_reply);
            viewHolder.tv_repycount = (TextView) convertView.findViewById(R.id.tv_repycount);
            viewHolder.listView = (ListView) convertView.findViewById(R.id.sub_list);
            viewHolder.listView.clearFocus();
            viewHolder.listView.setPressed(false);
            viewHolder.listView.setEnabled(false);
            viewHolder.listView.setFocusable(false);
            viewHolder.listView.setClickable(false);


            convertView.setTag(viewHolder);
        }else {
            viewHolder = (commentAdapter.ViewHolder) convertView.getTag();
        }

        final User user = articleData.getData().getCommentReplyList().get(i).getUser();

        Log.i("21222",NetConfig.Base+user.getUicon());
        Glide.with(context).load(NetConfig.Base+user.getUicon()).transform(new GlideCircleTransform(context)).placeholder(R.mipmap.my_nomal).error(R.mipmap.ic_launcher).into(viewHolder.iv_icon);
        viewHolder.tv_name.setText(user.getUname());
        viewHolder.user_desc.setText(user.getArticles()+"篇文章 . 获得了"+user.getLikes()+"个赞");

        viewHolder.tv_comments.setText(articleData.getData().getCommentReplyList().get(i).getComment().getDescption());
        List <Reply> replayList = articleData.getData().getCommentReplyList().get(i).getRepList();
        if(replayList!=null&&replayList.size()>0){

            viewHolder.tv_repycount.setVisibility(View.VISIBLE);
            viewHolder.tv_repycount.setText(replayList.size()+"条回复");
            viewHolder.listView.setVisibility(View.VISIBLE);
            viewHolder.listView.setAdapter(new subCommentAdapter(context,replayList));
            setListViewHeightBasedOnChildren(viewHolder.listView);
        }else {
            viewHolder.listView.setVisibility(View.GONE);
            viewHolder.tv_repycount.setVisibility(View.GONE);
        }


        viewHolder.tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Toast.makeText(context,"---"+i,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, FriendDetilActivity.class);
                intent.putExtra("User",user);
                context.startActivity(intent);
            }
        });


        viewHolder.tv_reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 添加回复

            }
        });


        return convertView;
    }







    class ViewHolder {

        ImageView iv_icon;
        TextView tv_name;
        TextView user_desc;
        TextView tv_comments;
        TextView tv_repycount;
        TextView tv_reply;
        ListView listView;
        //TextView tv_title;

    }






    public  void setListViewHeightBasedOnChildren(ListView listView) {
            ListAdapter listAdapter = listView.getAdapter();
            if (listAdapter == null) {
                // pre-condition
                return;
            }

            int totalHeight = 0;
            for (int i = 0; i < listAdapter.getCount(); i++) {
                View listItem = listAdapter.getView(i, null, listView);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }

            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
            listView.setLayoutParams(params);
        }








}