package com.dcoker.zone.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by admin on 2016/12/6.
 */

public class MypagerAdapter extends FragmentPagerAdapter {

        ArrayList<Fragment> Flist;

        public MypagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public MypagerAdapter(FragmentManager fm, ArrayList<Fragment> Flist) {
             super(fm);
            this.Flist =Flist;
        }

        @Override
        public Fragment getItem(int arg0) {
            return Flist.get(arg0);
        }

        @Override
        public int getCount() {
            return Flist.size();
        }

}
