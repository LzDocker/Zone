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
import android.widget.TextView;
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
import com.dcoker.zone.home.activity.FriendDetilActivity;
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




    @BindView(R.id.iv_uicon)
    ImageView iv_icon;

    @BindView(R.id.ll_mine)
    LinearLayout ll_mine;


 @BindView(R.id.tv_name)
 TextView tv_name;


    private User user;


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_mine;
    }


    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    public void finishCreateView(Bundle state) {
        user =((HomeActivity)getActivity()).getUser();
        if(isVisible) {
            lazyLoad();
        }

        Glide.with(activity).load(NetConfig.Base+user.getUicon()).transform(new GlideCircleTransform(activity)).placeholder(R.mipmap.my_nomal).error(R.mipmap.ic_launcher).into(iv_icon);
        tv_name.setText(user.getUname());

        Log.i("uuuu----",NetConfig.Base+user.getUicon());




        //初始化标题以及Fragment内容



    }


    @Override
    public void onVisible() {
        super.onVisible();

        lazyLoad();
    }

    @Override
    public void lazyLoad() {


    }


   @OnClick(R.id.ll_mine)
   public void enterMine(){

       Intent intent = new Intent(getActivity(), FriendDetilActivity.class);
       intent.putExtra("User",user);
       startActivity(intent);


   }




    private static int REQUEST_CODE_PICK_IMAGE = 0;

  @OnClick(R.id.iv_uicon)
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
