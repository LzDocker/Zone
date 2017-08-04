package com.dcoker.zone.home.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.target.Target;
import com.dcoker.zone.R;
import com.dcoker.zone.Zone;
import com.dcoker.zone.adapter.commentAdapter;
import com.dcoker.zone.config.NetConfig;
import com.dcoker.zone.entity.Article;
import com.dcoker.zone.entity.ArticledetilBean;
import com.dcoker.zone.entity.HotArticleBean;
import com.dcoker.zone.entity.User;
import com.dcoker.zone.util.FileUtils;
import com.dcoker.zone.weight.DetailListView;
import com.dcoker.zone.weight.DetailScrollView;
import com.dcoker.zone.weight.GlideRoundTransform;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ArticleDetilActivity extends AppCompatActivity {

    ArticledetilBean articleData;
    private WebView mEditor;
    private TextView tv_title;
    int artid;
    @BindView(R.id.ed_comment)
    EditText ed_comment;
    @BindView(R.id.btn_submit)
    Button btn_submit;
    private DetailScrollView mScrollView;
    private DetailListView mListView;
    private int mListScrollState= AbsListView.OnScrollListener.SCROLL_STATE_IDLE;
    private int mFirstVisibleItem;
    private int mWebViewHeight;
    private int mScreenHeight ;
    private boolean isMoving= false;
  @BindView(R.id.ll_submit)
  LinearLayout ll_submit;

    public Article article;

    @BindView(R.id.tv_atten)
    TextView tv_atten;

    @BindView(R.id.tv_likes)
    TextView tv_likes;

    Zone zone;
    @BindView(R.id.ll_load)
    LinearLayout ll_load;
    int uid;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detil);
        zone = (Zone) getApplication();
        ButterKnife.bind(this);
        artid= getIntent().getExtras().getInt("atrid");
        uid= getIntent().getExtras().getInt("uid");

        getSupportActionBar().hide();
        mEditor = (WebView) findViewById(R.id.editor);
        tv_title = (TextView) findViewById(R.id.tv_title);


        mScreenHeight =getResources().getDisplayMetrics().heightPixels;
        mScrollView = (DetailScrollView) findViewById(R.id.scrollView);
        mListView = (DetailListView) findViewById(R.id.list_view);


        initWebView();
        initScrollView();

    }


    @OnClick(R.id.tv_atten)
    public void Atten(){




        if(zone.Getuser().getId()!=article.getUid()){


            OkGo.<String>get(NetConfig.attenUser)
                    .cacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)
                    .cacheKey("jksgk")
                    .params("uid",article.getUid())
                    .params("auid",zone.Getuser().getId())
                    .tag(this)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                            Gson gson = new Gson();
                            String s = response.body().toString();
                            HotArticleBean indexdata =  gson.fromJson(/*Testdata.indexData*/s, HotArticleBean.class);

                            //  Log.i("000",indexdata.getData().getArticleCommentReplyList().get(0).getArticle().getDescption());

                            if(indexdata.getData()!=null&&indexdata.getData().getState()==0){


                                Toast.makeText(ArticleDetilActivity.this,"Success.",Toast.LENGTH_LONG).show();
                                if(articleData.getData().getArticle().getAttenState()){
                                    tv_atten.setText("取消关注");

                                }else{

                                    tv_atten.setText("点击关注");
                                }

                            }else{


                            }
                        }
                    });

        }else {

            Toast.makeText(ArticleDetilActivity.this,"这是自己的文章...",Toast.LENGTH_LONG).show();

        }


    }


    @OnClick(R.id.tv_likes)
    public void Likes(){

        if(zone.Getuser().getId()!=article.getUid()){
            OkGo.<String>get(NetConfig.Articlelikes)
                    .cacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)
                    .cacheKey("2343")
                    .params("artid",artid)
                    .params("auid",zone.Getuser().getId())
                    .tag(this)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                            Gson gson = new Gson();
                            String s = response.body().toString();
                            HotArticleBean indexdata =  gson.fromJson(/*Testdata.indexData*/s, HotArticleBean.class);
                            //  Log.i("000",indexdata.getData().getArticleCommentReplyList().get(0).getArticle().getDescption());
                            if(indexdata.getData()!=null&&indexdata.getData().getState()==0){
                                Toast.makeText(ArticleDetilActivity.this,"Success.",Toast.LENGTH_LONG).show();



                                if(articleData.getData().getArticle().getLikeState()){

                                    tv_likes.setText("点个赞吧");
                                }else{

                                    tv_likes.setText("取消点赞");
                                }


                            }else{

                            }
                        }
                    });
        }else {
            Toast.makeText(ArticleDetilActivity.this,"啪啪啪 这是自己的文章，要脸...",Toast.LENGTH_LONG).show();
        }
    }
    @SuppressLint("JavascriptInterface")
    public void initImageClick(){

        mEditor.loadUrl(
                "javascript:(function(){"
                        + "  var objs = document.getElementsByTagName(\"img\"); "
                        + "  for(var i=0;i<objs.length;i++){"
                        + "     objs[i].onclick=function(){"
                        + "          window.webImage.jsInvokeJava(this.src);  "
                        + "     }"
                        + "  }"
                        + "})()");
    }

    AlertDialog myDialog;

    @JavascriptInterface
    public void jsInvokeJava(final String img) {

        if("".equals(img)||img==null){
            return;
        }

        ArticleDetilActivity.this.runOnUiThread(new Runnable()
        {
            public void run()
            {
                myDialog = new AlertDialog.Builder(ArticleDetilActivity.this,R.style.Dialog_Fullscreen).create();
                myDialog.show();
                //这里注意一定要先show dialog 再去加载 contentView。否则会出现异常。
                View view = getLayoutInflater().inflate(R.layout.dailog_image_layout,null);
                ImageView imageView = (ImageView) view.findViewById(R.id.iv_image);
                TextView tvsave = (TextView) view.findViewById(R.id.tv_save);
                Glide.with(ArticleDetilActivity.this).load(img).error(R.mipmap.nodata).placeholder(R.mipmap.image_load).fitCenter().dontAnimate().into(imageView);
                tvsave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        download(img);
                    }
                });
                myDialog.getWindow().setContentView(view);
            }

        });
    }


    public void download(final String url) {
        new Thread(){

            @Override
            public void run() {
                super.run();

                FutureTarget future = Glide
                        .with(ArticleDetilActivity.this)
                        .load(url)
                        .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);

                try {
                    File file = (File) future.get();

                   // Environment.getExternalStorageDirectory().getAbsolutePath() ;
                    String  pictureFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath();
                    File appDir = new File(pictureFolder ,"Zone");
                    if (!appDir.exists()) {
                        appDir.mkdirs();
                    }

                    String prefix=url.substring(url.lastIndexOf(".")+1);
                    String fileName = System.currentTimeMillis() + prefix;
                    final File destFile = new File(appDir, fileName);
                    FileUtils.copyFile(file, destFile);

                    Log.i("#####",destFile.getPath());
                    // 最后通知图库更新
                    LocalBroadcastManager.getInstance(ArticleDetilActivity.this).sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                            Uri.fromFile(destFile.getParentFile())));

                  ArticleDetilActivity.this.runOnUiThread(new Runnable() {
                      @Override
                      public void run() {

                          if(myDialog!=null){

                              myDialog.dismiss();
                          }

                          Toast.makeText(ArticleDetilActivity.this,"图片保存到"+destFile.getPath(),Toast.LENGTH_SHORT).show();
                      }
                  });

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }.start();


    }



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void initWebView(){
        WebSettings settings = mEditor.getSettings();
        settings.setJavaScriptEnabled(true);
        mEditor.addJavascriptInterface(this, "webImage");
        settings.setDomStorageEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        } else {
            settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        }


        mEditor.setWebViewClient(new WebViewClient() {


            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                ll_load.setVisibility(View.VISIBLE);
                Log.i("444444444","start..........");
            }

            @Override
            public void onPageFinished(final WebView view, String url) {
                super.onPageFinished(view, url);
                ll_load.setVisibility(View.GONE);
                Log.i("444444444","finish..........");
                int w = View.MeasureSpec.makeMeasureSpec(0,
                        View.MeasureSpec.UNSPECIFIED);
                int h = View.MeasureSpec.makeMeasureSpec(0,
                        View.MeasureSpec.UNSPECIFIED);
                //重新测量
                //view.measure(w, h);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mWebViewHeight = view.getHeight();
                      //  Log.i(TAG, "WEBVIEW高度:" + view.getHeight());
                    }
                },100);

                initImageClick();
            }
        });
        getArticle(artid);

    }

    public void initScrollView(){

        mScrollView.setChildListView(mListView);
        mScrollView.setScanScrollChangedListener(new DetailScrollView.ISmartScrollChangedListener() {
            @Override
            public void onScrolledToBottom(int vericalY) {
                //到底的时候,事件交给listview,此时,需要让scrollview惯性滚动一下,没滚动完之前,不运行scrollview拦截
                if(!mListView.isHandleTouchEvent() && vericalY<0 && !mScrollView.isTouchingScrollView()){
                    handleListViewTouchEvent();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        mListView.fling(Math.abs(vericalY/3));
                    }else{
                        mListView.startFling(Math.abs(vericalY/3)/*5000*/);
                    }
                    mListView.setHandleTouchEvent(true);
                    //Log.d(TAG,"==》onScrolledToBottom 让listview fling了!!");
                    return;
                }
                handleListViewTouchEvent();

            }

            @Override
            public void onScrolledToTop() {

            }
        });
        mScrollView.setMoveListener(new DetailScrollView.onMoveListener() {
            //当按下scrollview的时候,如果listview还在fling,强制重置它的位置,并抢夺事件;
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onDown() {

                if(mListView!=null && mListScrollState== AbsListView.OnScrollListener.SCROLL_STATE_FLING){
                   // Log.d(TAG,"onDown listView还在fling,重置它的位置");
                    mListView.stopFling();
                    mListView.setSelectionFromTop(0,1);
                    mListView.setHandleTouchEvent(false);
                }
            }

            //ListView过渡到ScrollView的时候,需要再惯性让ScrollView再滚动一下
            @Override
            public void onUp(int velocityY) {

                if(isMoving){
                   // Log.d(TAG,"mScrollView onUp:"+velocityY);
                    isMoving = false;
                    if(mScrollView!=null && isWebViewOverScreen()){
                        mScrollView.fling(velocityY);
                    }
                }
               // Log.d(TAG,"mScrollView onUp");
                handleListViewTouchEvent();
                //防止自带弹性效果的scrollview 计算不准确,再计算一次
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        handleListViewTouchEvent();
                    }
                },1000);

            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onMove(float distance) {
                if(mListView!=null && mListScrollState==AbsListView.OnScrollListener.SCROLL_STATE_FLING){
                    //Log.d(TAG,"onMove listView还在fling,重置它的位置");
                    mListView.stopFling();
                    mListView.setSelectionFromTop(0,1);
                    mListView.setHandleTouchEvent(false);
                    mListScrollState = AbsListView.OnScrollListener.SCROLL_STATE_IDLE;
                }
            }
        });


    }


   /* private void appendListView(){
        Toast.makeText(getApplicationContext(),"正在加载更多",Toast.LENGTH_SHORT).show();
        int count = list.size();
        for (int i = count; i < 20+count; i++) {
            HashMap<String, String> map = new HashMap<String, String>();       //为避免产生空指针异常，有几列就创建几个map对象
            map.put("id", "userId:  " + i);
            map.put("name", "userName：" + i);
            list.add(map);
        }
        mAdapter.notifyDataSetChanged();
    }
*/


    //webview是否超过可见范围,也就是,是否可滚动
    private boolean isWebViewOverScreen(){
        return mWebViewHeight>(mScreenHeight-getTopAndBottomHeight())?true:false;
    }



    private int getListViewHeight(){
        int value = getResources().getDisplayMetrics().heightPixels-getTopAndBottomHeight();
        return value;
    }
    private int getTopHeight(){
        return dip2px(getApplicationContext(),50+25);
        //状态栏+标题栏
    }

    private int getTopAndBottomHeight(){
        return getTopHeight()+dip2px(getApplicationContext(),45);
        //状态栏+标题栏+回复栏
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    private int getListViewPositionAtScreen(){
        try {
            int[] location = new int[2];
            mListView.getLocationInWindow(location);
            int y = location[1];
            return y;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return -1;
    }

    //停止后,决定事件是由谁来处理的;
    private void handleListViewTouchEvent(){
        try{
            if(mListView==null)
                return;
            if(mListView.getChildCount()>0 && getListViewPositionAtScreen()>getTopHeight()){
                mListView.setHandleTouchEvent(false);
              //  Log.d(TAG,"==》handleListViewTouchEvent listview 露出屏幕,由外部处理事件");
            }else{
                mListView.setHandleTouchEvent(true);
              //  Log.d(TAG,"==》handleListViewTouchEvent listview 沾满全屏,自己处理事件");
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void getArticle(int id){

        OkGo.<String>get(NetConfig.ARTICLE_DETIL)
                .cacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)
                .cacheKey("ArticleDetilActivity")
                .params("id",id)
                .params("uid",zone.Getuser().getId())
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                        String s = response.body().toString();
                        Log.i("ArticleDetilActivity",s);
                        Gson gson = new Gson();
                        articleData =  gson.fromJson(s,ArticledetilBean.class);
                        article = articleData.getData().getArticle();
                        processView(articleData);
                    }
                });
    }
    public void processView(ArticledetilBean articleData){
          tv_title.setText(articleData.getData().getArticle().getTitle());
         // mEditor.setHtml(articleData.getData().getArticle().getDescption());

        if(articleData.getData().getArticle().getAttenState()){

            tv_atten.setText("点击关注");
        }else{
            tv_atten.setText("取消关注");
        }

        if(articleData.getData().getArticle().getLikeState()){

            tv_likes.setText("取消点赞");
        }else{
            tv_likes.setText("点个赞吧");
        }


        if(articleData.getData().getArticle().getUid()==zone.Getuser().getId()){

            tv_likes.setVisibility(View.GONE);
            tv_atten.setVisibility(View.GONE);
            ll_submit.setVisibility(View.INVISIBLE);
        }


        //封装头文件
        String sHead=   "<html><head><meta name=\"viewport\" content=\"width=device-width, " +
                "initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes\" />"+
                "<style>img{max-width:100% !important;height:auto !important;}</style>"
                +"<style>body{max-width:100% !important;}</style>"+"</head><body>";
        String html =articleData.getData().getArticle().getDescption();
        mEditor.loadDataWithBaseURL(null,sHead+html+"</body></html>","text/html", "UTF-8", null);
        if(articleData.getData().getCommentReplyList()!=null&&articleData.getData().getCommentReplyList().size()>0){
            initListView(articleData);
        }



    }




    public void initListView(ArticledetilBean articleData){



        commentAdapter adapter = new commentAdapter(this,articleData);
        View view = getLayoutInflater().inflate(R.layout.article_footer,null);
        //mListView.addHeaderView(view);
        mListView.setAdapter(adapter);
        mListView.addFooterView(view);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ArticleDetilActivity.this,"---"+position,Toast.LENGTH_SHORT).show();




            }
        });

        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                mListScrollState = scrollState;
                int lastIndex = mListView.getAdapter().getCount() - 1;
                //滚动到底部
                if(scrollState== AbsListView.OnScrollListener.SCROLL_STATE_IDLE  && mListView.getLastVisiblePosition() == lastIndex){
                    // Log.d(TAG,"=======>listview 滚动到底部 加载更多,处理事件");
                    //appendListView();
                    //handleListViewTouchEvent();
                    return;
                }
                //滚动到顶部
                if(scrollState== AbsListView.OnScrollListener.SCROLL_STATE_IDLE  && mFirstVisibleItem==0 && mListView.getChildAt(0).getTop()==0 ){
                    //Log.d(TAG,"=======>listview 滚动到顶部,不处理事件了,scrollview往上滚动5,让scrollview处理事件");
                    if(mScrollView!=null)
                        mScrollView.smoothScrollBy(0,-5);
                    mListView.setHandleTouchEvent(false);
                    return;
                }
                //停止的时候,进行事件决定
                if(scrollState== AbsListView.OnScrollListener.SCROLL_STATE_IDLE){
                    handleListViewTouchEvent();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                mFirstVisibleItem = firstVisibleItem;
                //Log.d(TAG,"onScroll firstVisibleItem:"+firstVisibleItem+"==>visibleItemCount:"+visibleItemCount+"==totalItemCount:"+totalItemCount);
            }
        });
        int height = getListViewHeight();
        mListView.getLayoutParams().height = height;
        mListView.requestLayout();
        mListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // Log.d(TAG,"MyListView onTouch ACTION_DOWN");
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    //  Log.d(TAG,"MyListView onTouch ACTION_MOVE");
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    // Log.d(TAG,"MyListView onTouch ACTION_UP");
                }
                //触摸的时候,让父控件不要拦截我的所有事件
                if (mScrollView != null && mListView.isHandleTouchEvent()) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        mScrollView.requestDisallowInterceptTouchEvent(false);
                    } else {
                        mScrollView.requestDisallowInterceptTouchEvent(true);
                    }
                }
                return false;
            }
        });
        mListView.setMoveListener(new DetailListView.onMoveListener() {
            @Override
            public void onMove(float distance) {
                //listview在顶部的时候,往下滑动,此时需要scrollview跟着一起滚动,进行过渡
                if(mScrollView!=null && mListView!=null && mListView.getChildCount()>0 && getListViewPositionAtScreen()>=getTopHeight() && distance>0 && mFirstVisibleItem ==0 && mListView.getChildAt(0).getTop()==0){
                    // 滚动
                    mScrollView.smoothScrollBy(0, -(int)distance);
                    // 取消listview的事件消费,会在其onTouchEvent返回false
                    mListView.setHandleTouchEvent(false);
                    isMoving = true;
                }
            }

            //拖动scrollview一起滚动之后,在松开的时候,需要有个惯性滚动
            @Override
            public void onUp(final int velocityY) {
                if(isMoving){
                    // Log.d(TAG,"mListView onUp:"+velocityY);
                    //  isMoving = false;
                    //延迟一下fling才有效果。
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(mScrollView!=null)
                                mScrollView.fling(-velocityY);
                        }
                    },50);
                }
            }
        });


    }



    @OnClick(R.id.btn_submit)
    public void submit(){

        String desc = ed_comment.getText().toString().trim();

        if(TextUtils.isEmpty(desc)){
            Toast.makeText(ArticleDetilActivity.this,"评论不能为空",Toast.LENGTH_SHORT).show();

        }else{

            //auid  评论者的id  （必须）
           // artid   文章id    （必须）
           // descption  评论的内容    （必须）



             User user= zone.Getuser();



            OkGo.<String>get(NetConfig.CommentArticle)
                    .cacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)
                    .cacheKey("ArticleDetil")
                    .params("auid",user.getId())
                    .params("artid",artid)
                    .params("descption",ed_comment.getText().toString().trim())
                    .tag(this)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            String s = response.body().toString();
                            Toast.makeText(ArticleDetilActivity.this,s,Toast.LENGTH_SHORT).show();
                            Log.i("444--------",s);

                        }
                    });

        }

    }

}
