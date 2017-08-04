package com.dcoker.zone.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.dcoker.zone.R;
import com.dcoker.zone.entity.Reply;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;

/**
 * Created by Mr.Zhang on 2017/8/1.
 */

public class subCommentAdapter  extends BaseAdapter {

    List <Reply> replayList;
    Activity context;



    public subCommentAdapter(Activity context, List <Reply> replayList){
        this.context=context;
        this.replayList = replayList;

    }


    @Override
    public int getCount() {

        if(replayList.size()>3){
            return  3;
        }
        return replayList.size();
    }

    @Override
    public Object getItem(int i) {
        return replayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {

        subCommentAdapter.ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new subCommentAdapter.ViewHolder();
            convertView = context.getLayoutInflater().inflate(R.layout.item_attenlist,null);
            viewHolder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
            viewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (subCommentAdapter.ViewHolder) convertView.getTag();
        }

        viewHolder.tv_title.setText(replayList.get(i).getDescption());

        return convertView;
    }


    class ViewHolder {

        ImageView iv_icon;
        TextView tv_title;

        //TextView tv_title;

    }


}
