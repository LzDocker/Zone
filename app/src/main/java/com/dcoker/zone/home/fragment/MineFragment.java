package com.dcoker.zone.home.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dcoker.zone.Activity.HomeActivity;
import com.dcoker.zone.R;
import com.dcoker.zone.adapter.MinePagerAdapter;
import com.dcoker.zone.adapter.indexAdapter;
import com.dcoker.zone.adapter.mineAdapter;
import com.dcoker.zone.config.NetConfig;
import com.dcoker.zone.entity.IndexData;
import com.dcoker.zone.entity.User;
import com.dcoker.zone.entity.mineArticleBean;
import com.dcoker.zone.home.activity.ArticleDetilActivity;
import com.dcoker.zone.home.activity.EditActivity;
import com.dcoker.zone.util.uriUtil;
import com.dcoker.zone.weight.GlideCircleTransform;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.iwf.photopicker.PhotoPicker;

import static android.app.Activity.RESULT_OK;

public class MineFragment extends BaseFragment {



    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<String> titleList = new ArrayList<String>();
    private List<BaseFragment> fmList=new ArrayList<BaseFragment>();
    @BindView(R.id.iv_icon)
    ImageView iv_icon;


    private User user;


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_mine_process2;
    }


    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    public void finishCreateView(Bundle state) {
        user =((HomeActivity)getActivity()).getUser();
        if(isVisible) {
            lazyLoad();
        }

        Glide.with(activity).load(NetConfig.Base+user.getUicon()).transform(new GlideCircleTransform(activity)).placeholder(R.mipmap.my_nomal).error(R.mipmap.ic_launcher).into(iv_icon);

        Log.i("uuuu----",NetConfig.Base+user.getUicon());


        viewPager= (ViewPager)$(R.id.viewpager);
        tabLayout= (TabLayout)$(R.id.tabs);

        //初始化标题以及Fragment内容


        articleFrgment articleFrgment = new articleFrgment();
        fmList.add(articleFrgment);
        AttenFragment attenFragment = new AttenFragment();
        fmList.add(attenFragment);
        LikeFragment likeFragment = new LikeFragment();
        fmList.add(likeFragment);

        titleList.add("我的");
        titleList.add("关注");
        titleList.add("喜欢");



        viewPager.setAdapter(new MinePagerAdapter(this.getChildFragmentManager(),fmList,titleList));
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(0);

    }


    @Override
    public void onVisible() {
        super.onVisible();

        lazyLoad();
    }

    @Override
    public void lazyLoad() {


        /*OkGo.<String>get(NetConfig.ARTLIST)
                .cacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)
                .cacheKey("mine")
                .params("uid",user.getId())
                .params("page",0)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                        Gson gson = new Gson();
                        String s = response.body().toString();
                        Log.i("*****",s);
                        mineArticleBean minedata =  gson.fromJson(*//*Testdata.indexData*//*s, mineArticleBean.class);

                        if(minedata.getData().getState()==0||minedata.getData().getArticleList()!=null){


                            listView.setVisibility(View.VISIBLE);

                            showList(minedata);

                        }else{


                            listView.setVisibility(View.INVISIBLE);

                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);

                        Log.i("onerror",response.body().toString());
                    }
                });
*/
    }



    public void showList(final mineArticleBean minedata){



       /* mineAdapter adapter = new mineAdapter(getActivity(),minedata);
        View headview = getActivity().getLayoutInflater().inflate(R.layout.mine_head,null);

        if(listView.getHeaderViewsCount()==0){

            listView.addHeaderView(headview);
        }
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(position==0){

                    return;
                }
                //Toast.makeText(getActivity(),position+"",Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity(),ArticleDetilActivity.class);
                intent.putExtra("atrid",minedata.getData().getArticleList().get(position-1).getId());
                startActivity(intent);

            }
        });*/
    }


    private static int REQUEST_CODE_PICK_IMAGE = 0;

  @OnClick(R.id.iv_icon)
  public  void ChangeHead(){

      Intent intent = new Intent(Intent.ACTION_PICK);
      intent.setType("image/*");//相片类型
      startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);

  }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (data != null) {
                if (requestCode == 1){
                    //处理调用系统图库
                } else if (requestCode == REQUEST_CODE_PICK_IMAGE){
                    //异步方式插入图片

                    if(data!=null){


                        Uri uri =  data.getData();

                        String path = uriUtil.getRealPathFromUri(this.getActivity(),uri);
                        Log.i("uuuu------------",path);
                      //  Glide.with(getActivity()).load(path).placeholder(R.mipmap.my_nomal).error(R.mipmap.ic_launcher).centerCrop().into(iv_icon);
                        Glide.with(activity).load(uri).transform(new GlideCircleTransform(activity)).placeholder(R.mipmap.my_nomal).error(R.mipmap.ic_launcher).into(iv_icon);

                        uploadImg(new File(path),NetConfig.ICON_UPLOAD);

                    }
                }
            }
        }

    }

    public void uploadImg(File file, String url){


        OkGo.<String>post(url)
                .tag(this)
                .params("imageFile",file)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {

                        Log.i("--",response.body().toString());
                    }
                });

    }

}
