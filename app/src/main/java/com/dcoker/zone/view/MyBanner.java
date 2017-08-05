package com.dcoker.zone.view;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dcoker.zone.R;
import com.dcoker.zone.entity.bannerData;

import java.util.ArrayList;
import java.util.List;

import cn.kevin.banner.BannerAdapter;
import cn.kevin.banner.BannerViewPager;
import cn.kevin.banner.IBannerItem;
import cn.kevin.banner.ImageLoader;

/**
 * Created by zhangtuo on 2017/8/5.
 */

public class MyBanner extends FrameLayout {

    public MyBanner(@NonNull Context context) {
        this(context, null);
    }

    public MyBanner(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyBanner(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {

        List<IBannerItem> list = new ArrayList<>();
        list.add(new bannerData("http://flad.feiliu.com/zone_docker/upload/pictures/2/4/zz$zone$magazine-unlock-05-2.3.680-_6e523b6d197b4f7ca40591e6e03ea930.jpg"));
        list.add(new bannerData("http://flad.feiliu.com/zone_docker/upload/pictures/2/4/zz$zone$magazine-unlock-05-2.3.694-_bfe542f6ac8740f8b221264cbe209ade.jpg"));
        list.add(new bannerData("http://flad.feiliu.com/zone_docker/upload/pictures/2/4/zz$zone$magazine-unlock-03-2.3.685-_b120391dd28d46188bdc521311424e35.jpg"));
        list.add(new bannerData("http://flad.feiliu.com/zone_docker/upload/pictures/2/4/zz$zone$magazine-unlock-04-2.3.689-_d7d634e8c48f4c4eb0bd8e63b119b58a.jpg"));
        list.add(new bannerData("http://flad.feiliu.com/zone_docker/upload/pictures/2/4/zz$zone$magazine-unlock-05-2.3.696-_dd7aaf1dee464abdae85e78345fc0b6c.jpg"));


        View view = LayoutInflater.from(context).inflate(R.layout.fragment_dis_head, null);
        addView(view);
        BannerViewPager viewPager = (BannerViewPager) view.findViewById(R.id.bvp);
        BannerAdapter adapter = new BannerAdapter();

        adapter.setData(context, list);
        adapter.setImageLoader(new GlideImageLoader());
        viewPager.setBannerAdapter(adapter);
        viewPager.setBannerItemClick(new BannerViewPager.OnBannerItemClick() {
            @Override
            public void onClick(IBannerItem data) {
                data.ImageUrl();
            }
        });
    }
    class GlideImageLoader implements ImageLoader {
        @Override
        public void onDisplayImage(Context context, ImageView imageView, String url) {
            Glide.with(context).load(url).placeholder(R.mipmap.image_load).centerCrop().error(R.mipmap.nodata).into(imageView);
        }
    }
}
