package com.dcoker.zone.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.dcoker.zone.home.fragment.BaseFragment;

import java.util.List;

/**
 * Created by Mr.Zhang on 2017/7/27.
 */

public class MinePagerAdapter extends FragmentStatePagerAdapter {
    List<String> titileList;
    List<BaseFragment> fmList;
    public MinePagerAdapter(FragmentManager fm, List<BaseFragment> fmList, List<String> titileList) {
        super(fm);
        this.fmList=fmList;
        this.titileList=titileList;
    }


    @Override
    public Fragment getItem(int arg0) {
        return fmList.get(arg0);
    }

    @Override
    public int getCount() {
        if(fmList==null){
            return 0;
        }else{
            return fmList.size();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titileList.get(position);
    }
}
