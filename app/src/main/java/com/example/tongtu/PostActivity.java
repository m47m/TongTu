package com.example.tongtu;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.FileUtils;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.OpenableColumns;
import android.util.Log;

import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSAuthCredentialsProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.example.tongtu.base.BaseActivity;
import com.example.tongtu.mvp.FileMsgPre;
import com.example.tongtu.mvp.FileMsgView;
import com.example.tongtu.utils.FileClassutils;
import com.example.tongtu.utils.FileTypeutils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;



public class PostActivity extends BaseActivity<FileMsgView, FileMsgPre> implements FileMsgView {
    private TextView text_file_name;
    private EditText et_message;
    private ProgressBar pb_post_progress;
    private ImageView img_file_class;
    private Button btn_select_file_calss;

    private String path_file_manager;
    private String path_file_other;
    private String path_final;
    private Uri uri_final;
    private String object_key;
    private int mProgress = 0;

    private SharedPreferences pref;
    private String token;
    private static String O_Url = "http";
    private static String Url = "http://api.tongtu.xyz";
    private static String CallbackUrl = "http://api.tongtu.xyz/oss/callback";


    private String description;
    private String folder = "test";
    private int device;
    private String md5;
    private String object;
    private String name;
    private int target;
    private int size;
    private int type = 3;

    private static final int REQUEST_SELECT_FILE = 1;
    private static final int REQUEST_SELECT_CLASS =2;

    @SuppressLint("HandlerLeak")
    Handler handler;
    {
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if(msg.what ==0x111)
                {
                    //给进度条赋值
                    pb_post_progress.setProgress(mProgress);
                }else if(msg.what == 0x110)
                {
                    Toast.makeText(PostActivity.this,"上传成功",Toast.LENGTH_SHORT).show();
                    pb_post_progress.setVisibility(View.GONE);
                }else if(msg.what == 0){
                    Toast.makeText(PostActivity.this,"文件可上传",Toast.LENGTH_SHORT).show();
                }else if (msg.what == 1){
                    Toast.makeText(PostActivity.this,"空间不足",Toast.LENGTH_SHORT).show();
                }


            }
        };
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        path_file_other = "失败";
        path_final = "0";
        object_key = "";

        pref = this.getSharedPreferences("login_message",MODE_PRIVATE);
        token = pref.getString("token","");
        //设备id
        device = 1;

        Intent intent = getIntent();
        String action = intent.getAction();

        text_file_name = (TextView)findViewById(R.id.text_file_name);
        et_message = (EditText)findViewById(R.id.edit_file_message) ;
        pb_post_progress = (ProgressBar)findViewById(R.id.pb_post_progress);
        img_file_class = (ImageView) findViewById(R.id.img_file_class);
        img_file_class.setImageResource(R.drawable.document_type_gif);
        btn_select_file_calss = (Button)findViewById(R.id.btn_select_class);
        ContentResolver resolver = getContentResolver();

        if(Intent.ACTION_VIEW.equals(action)){
            Uri data_uri_2 = intent.getData();
            if(data_uri_2 != null){
                path_file_other = Uri.decode(data_uri_2.getEncodedPath());
                uri_final = data_uri_2;
            }
            File f = new File(path_file_other);
            FileClassutils fileClassutils = new FileClassutils();
            text_file_name.setText(f.getName());
            et_message.setText(f.getName());
            object_key = f.getName();
            img_file_class.setImageResource(fileClassutils.toclass(resolver.getType(data_uri_2)));
            Toast.makeText(this,f.getName(), Toast.LENGTH_SHORT).show();
            path_final = path_file_other;
        }


        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    //定义一个标识，标识进度条是否加载完成
                    Message message = new Message();
                    if (mProgress < 100) {
                        //表示还没加载到100  则向进度条报告进度
                        message.what = 0x111;
                        handler.sendMessage(message);
                    } else {
                        //表示此时已经加载完成，向Handler发送消息  并跳出循环
                        message.what = 0x110;
                        handler.sendMessage(message);
                        break;
                    }
                    try{
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        select_file();

    }

    @Override
    public FileMsgPre create_presenter() {
        return new FileMsgPre();
    }

    @Override
    public FileMsgView create_view() {
        return this;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("onActivityResult","1:" + requestCode);
        Log.d("onActivityResult","2:" + resultCode);
        Log.d("onActivityResult","3:" + data);


        if ( requestCode == REQUEST_SELECT_FILE && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();

            //更新文件名和文件大小
            get_name_from_uir(uri);

            File file =  uriToFileApiQ(uri,this);
            //md5 = getFileMD5(file);
            Log.d("testPostActivity old",getFileMD5(file));
            md5 = fileToMD5(file.getPath());
//            Log.d("testPostActivity MD5:",getFileMD5(file));
//            Log.d("testPostActivity name:",this.name);
//            Log.d("testPostActivity size:",String.valueOf(this.size));
//            Log.d("testPostActivity description:",this.et_message.getText().toString());
//            Log.d("testPostActivity folder:",this.folder);

            //文件检测
            if(getPresenter() != null){
                Log.d("testPostActivity",file.getAbsolutePath());
                Log.d("testPostActivity",md5);
                getPresenter().toFileTest(token,String.valueOf(size),md5,String.valueOf(device));
            }


            //更新post中的uri
            uri_final = uri;
            //使用第三方应用打开
            if ("file".equalsIgnoreCase(uri.getScheme())) {
                path_file_other = uri.getPath();
                ContentResolver resolver = getContentResolver();
                Toast.makeText(this, path_file_other, Toast.LENGTH_SHORT).show();
                return;
            }
            path_file_manager = uri.getPath();
            //文件类型
            FileClassutils fileClassutils = new FileClassutils();
            ContentResolver resolver = getContentResolver();
            img_file_class.setImageResource(fileClassutils.toclass(resolver.getType(uri)));
            //文件type
            FileTypeutils fileTypeutils = new FileTypeutils();
            Log.d("testPostActivity fileType",String.valueOf(fileTypeutils.totype(resolver.getType(uri))) );
            this.type = fileTypeutils.totype(resolver.getType(uri));

            //更新路径
            path_final = path_file_manager;


        }else if(requestCode == REQUEST_SELECT_CLASS && resultCode == Activity.RESULT_OK){
            btn_select_file_calss.setText(data.getStringExtra("file_class"));
            this.folder = data.getStringExtra("file_class");
            Log.d("testPostActivity folder:",this.folder);
            Log.d("testPostActivity description:",this.et_message.getText().toString());
        }


    }

    public static String fileToMD5(String filePath) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(filePath);
            byte[] buffer = new byte[1024];
            MessageDigest digest = MessageDigest.getInstance("MD5");
            int numRead = 0;
            while (numRead != -1) {
                numRead = inputStream.read(buffer);
                if (numRead > 0)
                    digest.update(buffer, 0, numRead);
            }
            byte [] md5Bytes = digest.digest();
            return convertHashToString(md5Bytes);
        } catch (Exception e) {
            return null;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) { }
            }
        }
    }

    private static String convertHashToString(byte[] md5Bytes) {
        String returnVal = "";
        for (int i = 0; i < md5Bytes.length; i++) {
            returnVal += Integer.toString(( md5Bytes[i] & 0xff ) + 0x100, 16).substring(1);
        }
        return returnVal.toUpperCase();
    }

    //返回上一级
    public void back_home(View v){
        Intent intent_main = new Intent();
        setResult(RESULT_OK,intent_main);
        finish();
    }
    //选择文件
    public void select_file(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        //intent.setType("image/*");
        //intent.setType("audio/*"); //选择音频
        //intent.setType(“video/*”); //选择视频 （mp4 3gp 是android支持的视频格式）
        //intent.setType(“video/*;image/*”);//同时选择视频和图片
        intent.setType("*/*");//无类型限制
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent,REQUEST_SELECT_FILE);
    }

    public void test_post_file(View v){
        this.description = et_message.getText().toString();
        Log.d("testPostActivity callback:","--------callback---------");
        Log.d("testPostActivity auth",token);
        Log.d("testPostActivity md5:",md5);
        Log.d("testPostActivity object:",this.name);
        Log.d("testPostActivity size:",String.valueOf(this.size));
        Log.d("testPostActivity description:",this.et_message.getText().toString());
        Log.d("testPostActivity folder:",this.folder);
        Log.d("testPostActivity device",String.valueOf(device));
        Log.d("testPostActivity type",String.valueOf(type));
        Log.d("testPostActivity target" ,String.valueOf(target));
        Log.d("testPostActivity md5:","-------------------------");
    }

    //上传文件
    public void post_file(View v){
        if(path_final.equals("0")){
            Toast.makeText(this,"请选择文件",Toast.LENGTH_SHORT).show();
        }else{
            String endpoint = "http://oss-cn-beijing.aliyuncs.com";
            String stsServer = O_Url+"://api.tongtu.xyz"+"/oss/sts/"+token;
            Log.d("PutObject",path_final);
            Log.d("PutObject",stsServer.toString());
            OSSCredentialProvider credentialProvider = new OSSAuthCredentialsProvider(stsServer);

            // 配置类如果不设置，会有默认配置。
            ClientConfiguration conf = new ClientConfiguration();
            conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒。
            conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒。
            conf.setMaxConcurrentRequest(5); // 最大并发请求数，默认5个。
            conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次。

            OSS oss = new OSSClient(getApplicationContext(), endpoint, credentialProvider);
            //path_final = "/storage/emulated/0/Android/data/com.tencent.mobileqq/Tencent/QQfile_recv/“一二九”观众报名.xlsx";
            // 构造上传请求。
            // 依次填写Bucket名称（例如examplebucket）、
            // Object完整路径（例如exampledir/exampleobject.txt）
            // 和本地文件完整路径（例如/storage/emulated/0/oss/examplefile.txt）。
            // Object完整路径中不能包含Bucket名称。
            PutObjectRequest put = new PutObjectRequest("examplesbucket", object_key, uri_final);

            put.setCallbackParam(new HashMap<String, String>(){
                {
                    put("callbackUrl", CallbackUrl);
                    put("callbackBodyType", "application/json");
                    put("callbackBody", "{\"object\":${object},\"description\":${x:description},\"device\":${x:device},\"folder\":${x:folder},\"md5\":${x:md5},\"name\":${x:name},\"size\":${x:size},\"type\":${x:type},\"auth\":${x:auth},\"target\":${target}}");
                }
            });
            put.setCallbackVars(new HashMap<String, String>(){
                {
                    put("x:auth",token);
                    put("x:description", description);
                    put("x:device", String.valueOf(device));
                    //put("x:folder", folder);
                    put("x:md5", md5);
                    put("x:object", name);
                    put("x:size", String.valueOf(size));
                    put("x:type", String.valueOf(type));
                    put("x:target",String.valueOf(target));
                }
            });

            // 异步上传时可以设置进度回调。
            put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
                @Override
                public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                    Log.d("PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize);
                    int cur = Long.valueOf(currentSize).intValue();
                    int tol = Long.valueOf(totalSize).intValue() ;
                    double temp = ((double)cur/(double)tol);
                    Log.d("pb","temp"+temp);
                    mProgress = (int)temp*100;

                    Message message = new Message();
                    if (mProgress < 100) {
                        //表示还没加载到100  则向进度条报告进度
                        message.what = 0x111;
                        handler.sendMessage(message);
                    } else {
                        //表示此时已经加载完成，向Handler发送消息  并跳出循环
                        message.what = 0x110;
                        handler.sendMessage(message);

                    }
                }
            });

            //异步
            OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
                @Override
                public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                    Log.d("PutObject", "UploadSuccess");
                    Log.d("ETag", result.getETag());
                    Log.d("RequestId", result.getRequestId());

                    // 只有设置了servercallback，该值才有数据。
                    String serverCallbackReturnJson = result.getServerCallbackReturnBody();

                    Log.d("servercallback", serverCallbackReturnJson);

                }

                @Override
                public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                    // 请求异常。
                    if (clientExcepion != null) {
                        // 客户端异常，例如网络异常等。
                        clientExcepion.printStackTrace();
                    }
                    if (serviceException != null) {
                        // 服务端异常。
                        Log.e("ErrorCode", serviceException.getErrorCode());
                        Log.e("RequestId", serviceException.getRequestId());
                        Log.e("HostId", serviceException.getHostId());
                        Log.e("RawMessage", serviceException.getRawMessage());
                    }
                }
            });
            // task.cancel(); // 可以取消任务。
            task.waitUntilFinished(); // 等待任务完成。

        }

    }
    //从系统文件管理器uri获得文件文件名
    public void get_name_from_uir(Uri uri){
        Cursor cursor = getContentResolver().query(uri,null, null, null, null, null);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                //文件名
                String displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                //更新文件名
                text_file_name.setText(displayName);
                //文件描述

                et_message.setText(displayName);

                object_key = displayName;
                this.object = displayName;
                this.name = displayName;
                this.description = displayName;

                //文件大小
                int sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE);
                String size = null;
                if (!cursor.isNull(sizeIndex)) {
                    size = cursor.getString(sizeIndex);
                    this.size = Integer.valueOf(size);
                }else {
                    size = "Unknown";
                }
                //文件路径
            }
        }finally {
            cursor.close();
        }

    }
    //文件分类
    public void select_file_class(View view) {
        Intent intent_select = new Intent(PostActivity.this,SelectClassActivity.class);
        startActivityForResult(intent_select,REQUEST_SELECT_CLASS);
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public static File uriToFileApiQ(Uri uri, Context context) {
        File file = null;
        if(uri == null) return file;
        //android10以上转换
        if (uri.getScheme().equals(ContentResolver.SCHEME_FILE)) {
            file = new File(uri.getPath());
        } else if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            //把文件复制到沙盒目录
            ContentResolver contentResolver = context.getContentResolver();
            String displayName = System.currentTimeMillis()+ Math.round((Math.random() + 1) * 1000)
                    +"."+ MimeTypeMap.getSingleton().getExtensionFromMimeType(contentResolver.getType(uri));

//            注释掉的方法可以获取到原文件的文件名，但是比较耗时
//            Cursor cursor = contentResolver.query(uri, null, null, null, null);
//            if (cursor.moveToFirst()) {
//                String displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));}

            try {
                InputStream is = contentResolver.openInputStream(uri);
                File cache = new File(context.getCacheDir().getAbsolutePath(), displayName);
                FileOutputStream fos = new FileOutputStream(cache);
                FileUtils.copy(is, fos);
                file = cache;
                fos.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public static String getFileMD5(File file) {
//        if (!file.isFile()) {
//            return "1";
//        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);

            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        BigInteger bigInt = new BigInteger(1, digest.digest());
        return bigInt.toString(16);
    }

    @Override
    public void ResultofFileTest(String code) {
        Message message = new Message();
        if(code.equals("0")){
            message.what = 0;

        }else if (code.equals("1")){
            message.what = 1;
        }
        handler.sendMessage(message);
    }
}