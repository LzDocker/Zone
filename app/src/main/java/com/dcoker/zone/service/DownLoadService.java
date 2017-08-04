package com.dcoker.zone.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.target.Target;
import com.dcoker.zone.home.activity.ArticleDetilActivity;
import com.dcoker.zone.util.FileUtils;

import java.io.File;
import java.util.concurrent.ExecutionException;

public class DownLoadService extends Service {



    public DownLoadService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String imagepath = (String) intent.getExtras().get("imagepath");

        download(imagepath);



        return super.onStartCommand(intent, flags, startId);
    }


    public void download(final String url) {


        new Thread() {

            @Override
            public void run() {
                super.run();

                FutureTarget future = Glide
                        .with(DownLoadService.this)
                        .load(url)
                        .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);

                try {
                    File file = (File) future.get();
                    final String pictureFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath();
                    File appDir = new File(pictureFolder, "Zone");
                    if (!appDir.exists()) {
                        appDir.mkdirs();
                    }

                    String prefix = url.substring(url.lastIndexOf(".") + 1);
                    String fileName = System.currentTimeMillis() + prefix;
                    File destFile = new File(appDir, fileName);
                    FileUtils.copyFile(file, destFile);

                    Log.i("#####", destFile.getPath());


                    //Intent intent = new Intent(Intent.ACTION_MEDIA_MOUNTED); //这是刷新SD卡
                    Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);  // 这是刷新单个文件
                    Uri uri = Uri.fromFile(new File(pictureFolder));
                    intent.setData(uri);
                    LocalBroadcastManager.getInstance(DownLoadService.this).sendBroadcast(intent);
                    Toast.makeText(DownLoadService.this,"图片保存成功 "+destFile.getPath(),Toast.LENGTH_LONG).show();
                    stopSelf();


                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }



    @Override
    public void onDestroy() {
        super.onDestroy();

       Log.i("####service","service stop");
    }
}
