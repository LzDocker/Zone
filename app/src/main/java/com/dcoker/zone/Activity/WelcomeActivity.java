package com.dcoker.zone.Activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.dcoker.zone.R;
import com.dcoker.zone.entity.ModelSVG;
import com.jrummyapps.android.widget.AnimatedSvgView;

public class WelcomeActivity extends AppCompatActivity {
    AnimatedSvgView mSvgView;
    SharedPreferences sp ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //透明状态栏
      //  getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);




        sp = this.getPreferences(Context.MODE_PRIVATE);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        getSupportActionBar().hide();
        // 欢迎页面
        mSvgView= (AnimatedSvgView) findViewById(R.id.animated_svg_view);
        setSvg(ModelSVG.values()[3]);
        CheckUpdate();

    }






    private void setSvg(ModelSVG modelSvg) {
        mSvgView.setGlyphStrings(modelSvg.glyphs);
        mSvgView.setFillColors(modelSvg.colors);
        mSvgView.setViewportSize(modelSvg.width, modelSvg.height);
        mSvgView.setTraceResidueColor(0x32000000);
        mSvgView.setTraceColors(modelSvg.colors);
        mSvgView.rebuildGlyphData();
        mSvgView.start();
    }

    /**
     * 检查是否有新版本，如果有就升级
     */
    private void CheckUpdate() {
        new Thread() {
            public void run() {
                try {
                    Thread.sleep(500);
                    Message msg = checkhandler.obtainMessage();
                    checkhandler.sendMessage(msg);
                    Thread.sleep(2000);
                    toMain();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }.start();
    }


    private Handler checkhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
//            mAppName.setVisibility(View.VISIBLE);
        }
    };

    public void toMain() {

     if(sp.getString("name",null)!=null&&sp.getString("pwd",null)!=null){

         // 进入主界面
         Intent intent = new Intent(WelcomeActivity.this,HomeActivity.class);
         startActivity(intent);
         finish();

     }else{

       //
         Intent intent = new Intent(WelcomeActivity.this,LoginActivity.class);
         startActivity(intent);
         finish();
     }


    }

}
