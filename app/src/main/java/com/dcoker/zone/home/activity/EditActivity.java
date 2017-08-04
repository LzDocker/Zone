package com.dcoker.zone.home.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.InputMethodManager;
import android.webkit.JavascriptInterface;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dcoker.zone.R;
import com.dcoker.zone.config.NetConfig;
import com.dcoker.zone.entity.User;
import com.dcoker.zone.entity.commenResult;
import com.dcoker.zone.home.edit.richeditor.RichEditor;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.iwf.photopicker.PhotoPicker;

@SuppressLint("JavascriptInterface")
public class EditActivity extends AppCompatActivity implements View.OnClickListener {

    private InputMethodManager imm;//软键盘管理器
    private RelativeLayout rl_layout_editor;
    private ImageButton action_undo, action_redo, action_font, action_add;
    private RichEditor mEditor;
    private LinearLayout ll_layout_add, ll_layout_font;//添加布局，字体布局

    private ImageButton ib_Bold, ib_Italic, ib_StrikeThough, ib_BlockQuote, ib_H1, ib_H2, ib_H3, ib_H4;
    private boolean flag1, flag2, flag3, flag4, flag5, flag6, flag7, flag8;

    private TextView tv_submit;
    private EditText ed_title;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit);// AndroidBug5497Workaround.assistActivity(this);
        getSupportActionBar().hide();
        user = (User) getIntent().getExtras().get("user");
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        initViews();
        initEvents();

    }


   /* public void getArticle(){

        // String url = "https://www.baidu.com/";

    *//*    String reqUrl = "http://172.16.192.177/zone_docker/getArticle";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(reqUrl)
                .build();
        Call call = okHttpClient.newCall(request);


            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }


                @Override
                public void onResponse(Call call, Response response) throws IOException {

                  //  System.out.println(response.body().string());
                   final String result =  response.body().string();


                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Log.d("result----",result);
                            mEditor.setHtml(result);

                            initImageClick();
                            mEditor.insertHr();
                        }
                    });


                }

            });*//*


        String dd = "<img src=\"http://172.16.192.177/zone_docker/upload/pictures/1/zz$zone$magazine-unlock-05-2.3.678-_bdba2944bdc4491e8d3856a60bc26045.jpg\" alt=\"??\">\n" +
                "<p align=\"center\" style=\"color:#aaaaaa\">??zone?????</p>\n" +
                "<hr align=\"center\" width=\"200\" color=\"#aaaaaa\" size=\"1\"><br>\n" +
                "<img src=\"http://172.16.192.177/zone_docker/upload/pictures/1/zz$zone$magazine-unlock-05-2.3.678-_76fd5d05b8fc420bb992c9b963ecb281.jpg\" alt=\"??\">\n" +
                "<p align=\"center\" style=\"color:#aaaaaa\">??zone?????</p><hr align=\"center\" width=\"200\" color=\"#aaaaaa\" size=\"1\"><br>\n" +
                "<img src=\"http://172.16.192.177/zone_docker/upload/pictures/1/zz$zone$magazine-unlock-05-2.3.678-_05622621ba5c44ac91e5e2c57a6f9d8b.jpg\" alt=\"??\">\n" +
                "<p align=\"center\" style=\"color:#aaaaaa\">??zone?????</p><hr align=\"center\" width=\"200\" color=\"#aaaaaa\" size=\"1\">\n" +
                "<br><img src=\"http://172.16.192.177/zone_docker/upload/pictures/1/zz$zone$magazine-unlock-05-2.3.678-_3481fc63998c4eeaa9434c1d6b772485.jpg\" alt=\"??\">\n" +
                "<p align=\"center\" style=\"color:#aaaaaa\">??zone?????</p><hr align=\"center\" width=\"200\" color=\"#aaaaaa\" size=\"1\"><br>?20";


        mEditor.setHtml(dd);

        initImageClick();
        mEditor.insertHr();
    }*/

    private void initViews() {

        //富文本编辑初始化
        mEditor = (RichEditor) findViewById(R.id.editor);




        //启用javascript
        mEditor.getSettings().setJavaScriptEnabled(true);
        // 添加js交互接口类，并起别名 webtest
        mEditor.addJavascriptInterface(this, "webtest");
        mEditor.setEditorFontSize(15);
        mEditor.setPadding(10, 10, 10, 50);
        mEditor.setPlaceholder("这里是文章正文");




        rl_layout_editor = (RelativeLayout) findViewById(R.id.rl_layout_editor);
        tv_submit = (TextView) findViewById(R.id.tv_submit);
        ed_title = (EditText) findViewById(R.id.ed_title);
        ll_layout_add = (LinearLayout) findViewById(R.id.ll_layout_add);
        ll_layout_font = (LinearLayout) findViewById(R.id.ll_layout_font);

        action_undo = (ImageButton) findViewById(R.id.action_undo);
        action_redo = (ImageButton) findViewById(R.id.action_redo);
        action_font = (ImageButton) findViewById(R.id.action_font);
        action_add = (ImageButton) findViewById(R.id.action_add);

        //字体布局
        ib_Bold = (ImageButton) findViewById(R.id.action_bold);
        ib_Italic = (ImageButton) findViewById(R.id.action_italic);
        ib_StrikeThough = (ImageButton) findViewById(R.id.action_strikethrough);
        ib_BlockQuote = (ImageButton) findViewById(R.id.action_blockquote);
        ib_H1 = (ImageButton) findViewById(R.id.action_heading1);
        ib_H2 = (ImageButton) findViewById(R.id.action_heading2);
        ib_H3 = (ImageButton) findViewById(R.id.action_heading3);
        ib_H4 = (ImageButton) findViewById(R.id.action_heading4);

      //  getArticle();
    }




    @SuppressLint("JavascriptInterface")
    public void initImageClick(){

        mEditor.loadUrl(
                "javascript:(function(){"
                        + "  var objs = document.getElementsByTagName(\"img\"); "
                        + "  for(var i=0;i<objs.length;i++){"
                        + "     objs[i].onclick=function(){"
                        + "          window.webtest.jsInvokeJava(this.src);  "
                        + "     }"
                        + "  }"
                        + "})()");
    }


    String current;
    String subc;
    @JavascriptInterface
    public void jsInvokeJava(final String img) {

        if("".equals(img)||img==null){
            return;
        }

        EditActivity.this.runOnUiThread(new Runnable()
        {
            public void run()
            {
                mEditor.clearFocusEditor();
                showDelteImg(img);

            }

        });

    }


    public void showDelteImg(final String img){


        android.support.v7.app.AlertDialog.Builder dialog = new android.support.v7.app.AlertDialog.Builder(this);
        dialog.setMessage("删除此图片？");
        dialog.setNegativeButton("确定" ,new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                current= mEditor.getHtml();

                List<String> imlist = getImg(current);
                String ooo = img.substring(7);
                Log.i("ooo", "被点击的图片地址为：" + ooo);

                if(imlist.contains(ooo)){
                    subc =  current.replace("<img src=\""+ooo+"\" alt=\"图片\">","");
                    Log.i("current", "被点击的图片地址为：" + current);
                    Log.i("subc", "被点击的图片地址为：" + "<img src=\""+ooo+"\" alt=\"图片\">");
                    setcurrent(subc);
                }



            }
        });

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {

                mEditor.focusEditor();
                //启用javascript
                mEditor.getSettings().setJavaScriptEnabled(true);
                // 添加js交互接口类，并起别名 webtest
                mEditor.addJavascriptInterface(this, "webtest");
                initImageClick();

            }
        });

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {

                mEditor.focusEditor();
                //  mEditor.setFocusableInTouchMode(true);
                //启用javascript
                mEditor.getSettings().setJavaScriptEnabled(true);
                // 添加js交互接口类，并起别名 webtest
                mEditor.addJavascriptInterface(this, "webtest");
                initImageClick();

            }
        });

        dialog.create();
        dialog.show();
    }



    public void setcurrent(String ht){
        mEditor.setHtml(ht);
    }

    public static List<String> getImg(String s)
    {
        String regex;
        List<String> list = new ArrayList<String>();
        regex = "src=\"(.*?)\"";
        Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
        Matcher ma = pa.matcher(s);
        while (ma.find())
        {
            String path = ma.group().replace("src=", "");
            String sub = path.substring(1,path.length()-1);
            list.add(sub);
        }
        return list;
    }




    private void initEvents() {

        action_add.setOnClickListener(this);
        action_font.setOnClickListener(this);
        action_redo.setOnClickListener(this);
        action_undo.setOnClickListener(this);
        tv_submit.setOnClickListener(this);


        ib_Bold.setOnClickListener(this);
        ib_Italic.setOnClickListener(this);
        ib_StrikeThough.setOnClickListener(this);
        ib_BlockQuote.setOnClickListener(this);
        ib_H1.setOnClickListener(this);
        ib_H2.setOnClickListener(this);
        ib_H3.setOnClickListener(this);
        ib_H4.setOnClickListener(this);


        mEditor.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //  imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
                    rl_layout_editor.setVisibility(View.VISIBLE);
//                    clickableType = 1;
                } else {
                    imm.hideSoftInputFromWindow(mEditor.getWindowToken(), 0); //强制隐藏键盘
                    // rl_layout_editor.setVisibility(View.INVISIBLE);
                }
            }
        });


        /**
         *获取点击出文本的标签类型
         */
        mEditor.setOnDecorationChangeListener(new RichEditor.OnDecorationStateListener() {
            @Override
            public void onStateChangeListener(String text, List<RichEditor.Type> types) {

                if (types.contains(RichEditor.Type.BOLD)) {
                    ib_Bold.setImageResource(R.mipmap.bold_l);
                    flag1 = true;
                    isBold = true;
                } else {
                    ib_Bold.setImageResource(R.mipmap.bold_d);
                    flag1 = false;
                    isBold = false;
                }

                if (types.contains(RichEditor.Type.ITALIC)) {
                    ib_Italic.setImageResource(R.mipmap.italic_l);
                    flag2 = true;
                    isItalic = true;
                } else {
                    ib_Italic.setImageResource(R.mipmap.italic_d);
                    flag2 = false;
                    isItalic = false;
                }

                if (types.contains(RichEditor.Type.STRIKETHROUGH)) {
                    ib_StrikeThough.setImageResource(R.mipmap.strikethrough_l);
                    flag3 = true;
                    isStrikeThrough = true;
                } else {
                    ib_StrikeThough.setImageResource(R.mipmap.strikethrough_d);
                    flag3 = false;
                    isStrikeThrough = false;
                }

                //块引用
                if (types.contains(RichEditor.Type.BLOCKQUOTE)) {
                    flag4 = true;
                    flag5 = false;
                    flag6 = false;
                    flag7 = false;
                    flag8 = false;
                    isclick = true;
                    ib_BlockQuote.setImageResource(R.mipmap.blockquote_l);
                    ib_H1.setImageResource(R.mipmap.h1_d);
                    ib_H2.setImageResource(R.mipmap.h2_d);
                    ib_H3.setImageResource(R.mipmap.h3_d);
                    ib_H4.setImageResource(R.mipmap.h4_d);
                } else {
                    ib_BlockQuote.setImageResource(R.mipmap.blockquote_d);
                    flag4 = false;
                    isclick = false;
                }


                if (types.contains(RichEditor.Type.H1)) {
                    flag4 = false;
                    flag5 = true;
                    flag6 = false;
                    flag7 = false;
                    flag8 = false;

                    isclick = true;
                    ib_BlockQuote.setImageResource(R.mipmap.blockquote_d);
                    ib_H1.setImageResource(R.mipmap.h1_l);
                    ib_H2.setImageResource(R.mipmap.h2_d);
                    ib_H3.setImageResource(R.mipmap.h3_d);
                    ib_H4.setImageResource(R.mipmap.h4_d);
                } else {
                    ib_H1.setImageResource(R.mipmap.h1_d);
                    flag5 = false;
                    isclick = false;
                }

                if (types.contains(RichEditor.Type.H2)) {
                    flag4 = false;
                    flag5 = false;
                    flag6 = true;
                    flag7 = false;
                    flag8 = false;

                    isclick = true;
                    ib_BlockQuote.setImageResource(R.mipmap.blockquote_d);
                    ib_H1.setImageResource(R.mipmap.h1_d);
                    ib_H2.setImageResource(R.mipmap.h2_l);
                    ib_H3.setImageResource(R.mipmap.h3_d);
                    ib_H4.setImageResource(R.mipmap.h4_d);
                } else {
                    ib_H2.setImageResource(R.mipmap.h2_d);
                    flag6 = false;
                    isclick = false;
                }

                if (types.contains(RichEditor.Type.H3)) {
                    flag4 = false;
                    flag5 = false;
                    flag6 = false;
                    flag7 = true;
                    flag8 = false;
                    isclick = true;
                    ib_BlockQuote.setImageResource(R.mipmap.blockquote_d);
                    ib_H1.setImageResource(R.mipmap.h1_d);
                    ib_H2.setImageResource(R.mipmap.h2_d);
                    ib_H3.setImageResource(R.mipmap.h3_l);
                    ib_H4.setImageResource(R.mipmap.h4_d);
                } else {
                    ib_H4.setImageResource(R.mipmap.h3_d);
                    flag7 = false;
                    isclick = false;
                }

                if (types.contains(RichEditor.Type.H4)) {
                    flag4 = false;
                    flag5 = false;
                    flag6 = false;
                    flag7 = false;
                    flag8 = true;
                    isclick = true;
                    ib_BlockQuote.setImageResource(R.mipmap.blockquote_d);
                    ib_H1.setImageResource(R.mipmap.h1_d);
                    ib_H2.setImageResource(R.mipmap.h2_d);
                    ib_H3.setImageResource(R.mipmap.h3_d);
                    ib_H4.setImageResource(R.mipmap.h4_l);
                } else {
                    ib_H4.setImageResource(R.mipmap.h4_d);
                    flag8 = false;
                    isclick = false;
                }
            }
        });


        //布局全局改变监听
        rl_layout_editor.getViewTreeObserver().addOnGlobalLayoutListener(onGroupCollapseListener);


        /**
         * 插入图片
         */
        findViewById(R.id.action_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //调用第三方图库选择
                PhotoPicker.builder()
                        .setPhotoCount(9)//可选择图片数量
                        .setShowCamera(true)//是否显示拍照按钮
                        .setShowGif(true)//是否显示动态图
                        .setPreviewEnabled(true)//是否可以预览
                        .start(EditActivity.this, PhotoPicker.REQUEST_CODE);
            }
        });

        /**
         * 插入链接
         */
        findViewById(R.id.action_link).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInsertLinkDialog();
            }
        });


        /**
         * 插入分割线
         */
        findViewById(R.id.action_split).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.insertHr();

               // Toast.makeText(EditActivity.this,mEditor.getHtml(),Toast.LENGTH_LONG).show();

            }
        });
    }

    ViewTreeObserver.OnGlobalLayoutListener onGroupCollapseListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {

            WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            int height = wm.getDefaultDisplay().getHeight();

            if (rl_layout_editor.getHeight() <= height * 0.75) {//当布局y轴坐标小于于屏幕高度的3/4，居于中部
                rl_layout_editor.setVisibility(View.VISIBLE);

            } else if (rl_layout_editor.getHeight() > height * 0.75) {
                // rl_layout_editor.setVisibility(View.INVISIBLE);
                if (ll_layout_add.getVisibility() == View.VISIBLE) {
                    // ll_layout_add.setVisibility(View.INVISIBLE);
                }

                if (ll_layout_font.getVisibility() == View.VISIBLE) {
                    // ll_layout_font.setVisibility(View.INVISIBLE);
                }
            }
        }
    };


    boolean isclick = true;
    boolean isItalic;//是否斜体
    boolean isBold;//是否加粗
    boolean isStrikeThrough;//是否有删除线
    //富文本图片保存的集合
    private ArrayList<String> selectedRichImage = new ArrayList<>();

    @Override
    public void onClick(View v) {

        switch (v.getId()) {


            case R.id.tv_submit:

                String title = ed_title.getText().toString().trim();

                String descption = mEditor.getHtml();


                if(!"".equals(descption)&&descption!=null){


                    Map<String,Object> map = new HashMap<>();
                    map.put("title",title);
                    map.put("descption",descption);
                    map.put("grouptag","未命名");

                    uploadArticle(title,descption,NetConfig.uploadArticle);

                }else{

                    Toast.makeText(EditActivity.this,"文章内容为空",Toast.LENGTH_SHORT).show();

                }
                break;

            case R.id.action_undo:

                mEditor.undo();
                break;
            //复原
            case R.id.action_redo:

                mEditor.redo();
                break;
            //字体
            case R.id.action_font:

                if (ll_layout_font.getVisibility() == View.VISIBLE) {
                    ll_layout_font.setVisibility(View.GONE);
                } else {
                    if (ll_layout_add.getVisibility() == View.VISIBLE) {
                        ll_layout_add.setVisibility(View.GONE);
                    }
                    ll_layout_font.setVisibility(View.VISIBLE);
                    startAnimation(ll_layout_font);
                }
                break;


            //添加
            case R.id.action_add:

                if (ll_layout_add.getVisibility() == View.VISIBLE) {
                    ll_layout_add.setVisibility(View.GONE);
                } else {
                    if (ll_layout_font.getVisibility() == View.VISIBLE) {
                        ll_layout_font.setVisibility(View.GONE);
                    }
                    ll_layout_add.setVisibility(View.VISIBLE);
                    startAnimation(ll_layout_add);
                }
                break;
            /**
             *粗体
             */
            case R.id.action_bold:
                if (flag1) {
                    ib_Bold.setImageResource(R.mipmap.bold_d);
                    flag1 = false;
                    isBold = false;
                } else {
                    ib_Bold.setImageResource(R.mipmap.bold_l);
                    flag1 = true;
                    isBold = true;
                }
                mEditor.setBold();
                break;
            //斜体
            case R.id.action_italic:
                if (flag2) {
                    ib_Italic.setImageResource(R.mipmap.italic_d);
                    flag2 = false;
                    isItalic = false;
                } else {
                    ib_Italic.setImageResource(R.mipmap.italic_l);
                    flag2 = true;
                    isItalic = true;
                }
                mEditor.setItalic();
                break;
            //删除线
            case R.id.action_strikethrough:
                if (flag3) {
                    ib_StrikeThough.setImageResource(R.mipmap.strikethrough_d);
                    flag3 = false;
                    isStrikeThrough = false;
                } else {
                    ib_StrikeThough.setImageResource(R.mipmap.strikethrough_l);
                    flag3 = true;
                    isStrikeThrough = true;
                }
                mEditor.setStrikeThrough();
                break;
            //块引用
            case R.id.action_blockquote:
                if (flag4) {
                    ib_BlockQuote.setImageResource(R.mipmap.blockquote_d);
                    flag4 = false;
                    isclick = false;
                } else {
                    flag4 = true;
                    flag5 = false;
                    flag6 = false;
                    flag7 = false;
                    flag8 = false;
                    isclick = true;
                    ib_BlockQuote.setImageResource(R.mipmap.blockquote_l);
                    ib_H1.setImageResource(R.mipmap.h1_d);
                    ib_H2.setImageResource(R.mipmap.h2_d);
                    ib_H3.setImageResource(R.mipmap.h3_d);
                    ib_H4.setImageResource(R.mipmap.h4_d);
                }
                Log.e("BlockQuote", "isItalic:" + isItalic + "，isBold：" + isBold + "，isStrikeThrough:" + isStrikeThrough);
                mEditor.setBlockquote(isclick, isItalic, isBold, isStrikeThrough);
                break;
            /**
             * H1-H4字体
             */
            case R.id.action_heading1:
                if (flag5) {
                    ib_H1.setImageResource(R.mipmap.h1_d);
                    flag5 = false;
                    isclick = false;

                    //使加粗灰显并去除效果
                    ib_Bold.setImageResource(R.mipmap.bold_d);
                    flag1 = false;
                    isBold = false;
                } else {
                    flag4 = false;
                    flag5 = true;
                    flag6 = false;
                    flag7 = false;
                    flag8 = false;
                    isclick = true;
                    ib_BlockQuote.setImageResource(R.mipmap.blockquote_d);
                    ib_H1.setImageResource(R.mipmap.h1_l);
                    ib_H2.setImageResource(R.mipmap.h2_d);
                    ib_H3.setImageResource(R.mipmap.h3_d);
                    ib_H4.setImageResource(R.mipmap.h4_d);
                }
                mEditor.setHeading(1, isclick, isItalic, isBold, isStrikeThrough);
                break;
            case R.id.action_heading2:
                if (flag6) {
                    ib_H2.setImageResource(R.mipmap.h2_d);
                    flag6 = false;
                    isclick = false;

                    //使加粗灰显并去除效果
                    ib_Bold.setImageResource(R.mipmap.bold_d);
                    flag1 = false;
                    isBold = false;
                } else {
                    flag4 = false;
                    flag5 = false;
                    flag6 = true;
                    flag7 = false;
                    flag8 = false;

                    isclick = true;
                    ib_BlockQuote.setImageResource(R.mipmap.blockquote_d);
                    ib_H1.setImageResource(R.mipmap.h1_d);
                    ib_H2.setImageResource(R.mipmap.h2_l);
                    ib_H3.setImageResource(R.mipmap.h3_d);
                    ib_H4.setImageResource(R.mipmap.h4_d);
                }
                mEditor.setHeading(2, isclick, isItalic, isBold, isStrikeThrough);
                break;
            case R.id.action_heading3:
                if (flag7) {
                    ib_H3.setImageResource(R.mipmap.h3_d);
                    flag7 = false;
                    isclick = false;

                    //使加粗灰显并去除效果
                    ib_Bold.setImageResource(R.mipmap.bold_d);
                    flag1 = false;
                    isBold = false;
                } else {
                    flag4 = false;
                    flag5 = false;
                    flag6 = false;
                    flag7 = true;
                    flag8 = false;
                    isclick = true;
                    ib_BlockQuote.setImageResource(R.mipmap.blockquote_d);
                    ib_H1.setImageResource(R.mipmap.h1_d);
                    ib_H2.setImageResource(R.mipmap.h2_d);
                    ib_H3.setImageResource(R.mipmap.h3_l);
                    ib_H4.setImageResource(R.mipmap.h4_d);
                }
                mEditor.setHeading(3, isclick, isItalic, isBold, isStrikeThrough);
                break;
            case R.id.action_heading4:
                if (flag8) {
                    ib_H4.setImageResource(R.mipmap.h4_d);
                    flag8 = false;
                    isclick = false;

                    //使加粗灰显并去除效果
                    ib_Bold.setImageResource(R.mipmap.bold_d);
                    flag1 = false;
                    isBold = false;
                } else {
                    flag4 = false;
                    flag5 = false;
                    flag6 = false;
                    flag7 = false;
                    flag8 = true;
                    isclick = true;
                    ib_BlockQuote.setImageResource(R.mipmap.blockquote_d);
                    ib_H1.setImageResource(R.mipmap.h1_d);
                    ib_H2.setImageResource(R.mipmap.h2_d);
                    ib_H3.setImageResource(R.mipmap.h3_d);
                    ib_H4.setImageResource(R.mipmap.h4_l);
                }
                mEditor.setHeading(4, isclick, isItalic, isBold, isStrikeThrough);
                break;

        }

    }

    // 执行动画效果
    public void startAnimation(View mView) {

        AlphaAnimation aa = new AlphaAnimation(0.4f, 1.0f); // 0完全透明 1 完全不透明
        // 以(0%,0.5%)为基准点，从0.5缩放至1
        ScaleAnimation sa = new ScaleAnimation(0.5f, 1, 0.5f, 1,
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0.5f);

        // 添加至动画集合
        AnimationSet as = new AnimationSet(false);
        as.addAnimation(aa);
        as.addAnimation(sa);
        as.setDuration(500);
        // 执行动画
        mView.startAnimation(as);
    }


    private AlertDialog linkDialog;

    /**
     * 插入链接Dialog
     */
    private void showInsertLinkDialog() {

        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        linkDialog = adb.create();

        View view = getLayoutInflater().inflate(R.layout.dialog_insertlink, null);

        final EditText et_link_address = (EditText) view.findViewById(R.id.et_link_address);
        final EditText et_link_title = (EditText) view.findViewById(R.id.et_link_title);

        Editable etext = et_link_address.getText();
        Selection.setSelection(etext, etext.length());

        //点击确实的监听
        view.findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String linkAddress = et_link_address.getText().toString();
                String linkTitle = et_link_title.getText().toString();

                if (linkAddress.endsWith("http://") || TextUtils.isEmpty(linkAddress)) {
                    Toast.makeText(EditActivity.this, "请输入超链接地址", Toast.LENGTH_SHORT);
                } else if (TextUtils.isEmpty(linkTitle)) {
                    Toast.makeText(EditActivity.this, "请输入超链接标题", Toast.LENGTH_SHORT);
                } else {
                    mEditor.insertLink(linkAddress, linkTitle);
                    linkDialog.dismiss();
                }
            }
        });
        //点击取消的监听
        view.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linkDialog.dismiss();
            }
        });
        linkDialog.setCancelable(false);
        linkDialog.setView(view, 0, 0, 0, 0); // 设置 view
        linkDialog.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (data != null) {
                if (requestCode == 1){
                    //处理调用系统图库
                } else if (requestCode == PhotoPicker.REQUEST_CODE){
                    //异步方式插入图片

                    if(data!=null){


                        insertImagesSync(data);
                    }


                }
            }
        }

    }




    public void insertImagesSync(Intent data){

        ArrayList<String> photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);


        for (String imagePath : photos) {

//插入图片


            File file = new File(imagePath);
            mEditor.insertImage(imagePath, "图片");
            uploadImg(file, NetConfig.uploadPic);
        }
        initImageClick();
        // 上传图片到服务器
    }
    public void uploadImg(File file, String url){


        OkGo.<String>post(url)
                .tag(this)
                .params("file_",file)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {

                        Log.i("--",response.body().toString());
                    }
                });

    }
    public void uploadArticle(String title,String descption, String url){

        OkGo.<String>post(url)
                .tag(this)
                .isMultipart(true)
                .params("title",title)
                .params("descption",descption)
                .params("grouptag","未分类")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                        Log.i("=====",response.body().toString());
                        String s = response.body().toString();
                        Gson gson = new Gson();
                        commenResult result =  gson.fromJson(s,commenResult.class);
                        if(!"".equals(result)&&result!=null){
                            if(result.getState()&&result.getData().getState()==0){
                                Toast.makeText(EditActivity.this,"发布成功",Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                    }
                });
    }






}



