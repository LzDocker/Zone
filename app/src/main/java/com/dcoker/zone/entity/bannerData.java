package com.dcoker.zone.entity;

import cn.kevin.banner.IBannerItem;

/**
 * Created by Mr.Zhang on 2017/8/3.
 */

public class bannerData implements IBannerItem {

    String ImageUrl;


    @Override
    public String ImageUrl() {
        return ImageUrl;
    }

    public bannerData( String ImageUrl){
        this. ImageUrl = ImageUrl;
    }

}
