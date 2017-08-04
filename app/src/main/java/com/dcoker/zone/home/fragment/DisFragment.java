package com.dcoker.zone.home.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.dcoker.zone.R;
import com.dcoker.zone.adapter.hotAdapter;
import com.dcoker.zone.config.NetConfig;
import com.dcoker.zone.entity.HotArticleBean;
import com.dcoker.zone.entity.bannerData;
import com.dcoker.zone.home.activity.ArticleDetilActivity;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.kevin.banner.BannerAdapter;
import cn.kevin.banner.BannerViewPager;
import cn.kevin.banner.IBannerItem;
import cn.kevin.banner.ImageLoader;

public class DisFragment extends BaseFragment {


    BannerViewPager Banner;


    @BindView(R.id.listview)
    ListView listView;


    View headview;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_dis;
    }

    @Override
    public void finishCreateView(Bundle state) {




        headview= getActivity().getLayoutInflater().inflate(R.layout.fragment_dis_head,null);
        Banner = (BannerViewPager) headview.findViewById(R.id.bvp);



        if(isVisible()){
            lazyLoad();
        }

    }

    //这里举例为Glide,实际使用时需配合自己项目中的图片加载框架完成图片加载
    class GlideImageLoader implements ImageLoader {
        @Override
        public void onDisplayImage(Context context, ImageView imageView, String url) {
            Glide.with(context).load(url).placeholder(R.mipmap.image_load).centerCrop().error(R.mipmap.nodata).into(imageView);
        }
    }

    @Override
    public void lazyLoad() {

          // ?GroupTag=%E6%9C%AA%E5%88%86%E7%B1%BB&page=0
        OkGo.<String>get(NetConfig.hotarticle)
                .cacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)
                .cacheKey("AttenFragment")
                .params("GroupTag","未分类")
                .params("page",0)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                        Gson gson = new Gson();
                        String s = response.body().toString();
                        HotArticleBean indexdata =  gson.fromJson(/*Testdata.indexData*/s, HotArticleBean.class);

                     Log.i("-----------000",s);

                        if(indexdata.getData()!=null&&indexdata.getData().getState()==0){



                            showList(indexdata);

                        }else{


                        }
                    }
                });


    }





    public void showList(final HotArticleBean minedata){

        hotAdapter Adapter  = new hotAdapter(getActivity(),minedata);
        listView.addHeaderView(headview);
        BannerAdapter adapter = new BannerAdapter();
        //item需要实现IBannerItem接口
        List<IBannerItem> list = new ArrayList<>();
        list.add(new bannerData("http://flad.feiliu.com/zone_docker/upload/pictures/2/4/zz$zone$magazine-unlock-05-2.3.680-_6e523b6d197b4f7ca40591e6e03ea930.jpg"));
        list.add(new bannerData("http://flad.feiliu.com/zone_docker/upload/pictures/2/4/zz$zone$magazine-unlock-05-2.3.694-_bfe542f6ac8740f8b221264cbe209ade.jpg"));
        list.add(new bannerData("http://flad.feiliu.com/zone_docker/upload/pictures/2/4/zz$zone$magazine-unlock-03-2.3.685-_b120391dd28d46188bdc521311424e35.jpg"));
        list.add(new bannerData("http://flad.feiliu.com/zone_docker/upload/pictures/2/4/zz$zone$magazine-unlock-04-2.3.689-_d7d634e8c48f4c4eb0bd8e63b119b58a.jpg"));
        list.add(new bannerData("http://flad.feiliu.com/zone_docker/upload/pictures/2/4/zz$zone$magazine-unlock-05-2.3.696-_dd7aaf1dee464abdae85e78345fc0b6c.jpg"));

        adapter.setData(getActivity(), list);
        adapter.setImageLoader(new GlideImageLoader());
        Banner.setBannerAdapter(adapter);
        Banner.setBannerItemClick(new BannerViewPager.OnBannerItemClick(){
            @Override
            public void onClick(IBannerItem data) {
                data.ImageUrl();
            }
        });



        listView.setAdapter(Adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(position==0){

                    return;
                }

                Intent intent = new Intent(getActivity(), ArticleDetilActivity.class);
                intent.putExtra("atrid",minedata.getData().getArticleCommentReplyList().get(position-1).getArticle().getId());
                startActivity(intent);

            }
        });

    }


}
