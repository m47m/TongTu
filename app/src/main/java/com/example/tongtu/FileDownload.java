package com.example.tongtu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSAuthCredentialsProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.common.utils.BinaryUtil;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.GetObjectRequest;
import com.alibaba.sdk.android.oss.model.GetObjectResult;
import com.example.tongtu.base.BaseActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static android.os.Environment.getExternalStoragePublicDirectory;

public class FileDownload extends AppCompatActivity {
    private Intent intent;
    private String file_name;
    private String file_size_cloud;
    private String file_time;

    private ImageView img_file_class;
    private TextView text_file_name;

    private SharedPreferences pref;
    private String token;
    private static String O_Url = "http";
    private static String Url = "http://api.tongtu.xyz";
    private static String CallbackUrl = "http://api.tongtu.xyz/oss/callback";

    private static final int DOWNLOAD_SUCCESS = 1;
    private static final int DOWNLOAD_FAIL =2;

    public FileGetCallback getCallback;

    @SuppressLint("HandlerLeak")
    Handler handler;
    {
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
               switch (msg.what){
                   case DOWNLOAD_SUCCESS:
                       Toast.makeText(FileDownload.this,"已存储至系统DownLoad目录下",Toast.LENGTH_SHORT).show();
                       break;
                   case DOWNLOAD_FAIL:
                       Toast.makeText(FileDownload.this,"下载失败，请重新尝试",Toast.LENGTH_SHORT).show();
                       break;
               }
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_download);
        intent = this.getIntent();

        file_name = intent.getStringExtra("file_name");

        img_file_class = (ImageView)findViewById(R.id.img_file_class);
        text_file_name = (TextView)findViewById(R.id.text_file_name);

        text_file_name.setText(file_name);
        img_file_class.setImageResource(intent.getIntExtra("file_class",R.drawable.document_type_new));


        pref = this.getSharedPreferences("login_message",MODE_PRIVATE);
        token = pref.getString("token","");


    }

    public void back(View view) {
        finish();
    }

    public void download_file(View view) {
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        String stsServer = O_Url+"://api.tongtu.xyz"+"/oss/sts/"+token;

        OSSCredentialProvider credentialProvider = new OSSAuthCredentialsProvider(stsServer);

        // 配置类如果不设置，会有默认配置。
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒。
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒。
        conf.setMaxConcurrentRequest(5); // 最大并发请求数，默认5个。
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次。

        OSS mClient = new OSSClient(getApplicationContext(), endpoint, credentialProvider);

        GetObjectRequest get = new GetObjectRequest("examplesbucket", this.file_name);

        getCallback = new FileGetCallback();

        try {
            mClient.getObject(get);
        } catch (ClientException e1) {
            e1.printStackTrace();
        } catch (ServiceException e1) {
            e1.printStackTrace();
        }

        OSSAsyncTask getTask = mClient.asyncGetObject(get, getCallback);

        getTask.waitUntilFinished();

    }

    public final  class FileGetCallback implements OSSCompletedCallback<GetObjectRequest, GetObjectResult> {

        public GetObjectRequest request;
        public GetObjectResult result;
        public ClientException clientException;
        public ServiceException serviceException;


        @Override
        public void onSuccess(GetObjectRequest request, GetObjectResult result) {
            Log.v("AvceService","onSuccess");
            this.request = request;
            this.result = result;
            writeFile();




        }

        @Override
        public void onFailure(GetObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
            Log.v("AvceService","onFailure");
            this.request = request;
            this.clientException = clientExcepion;
            this.serviceException = serviceException;

            Message message = new Message();
            message.what = DOWNLOAD_FAIL;
            handler.sendMessage(message);
        }
    }


    public void writeFile() {
        String srcFileBase64Md5 = null;
        String filePath ="/"+"test"+"/"+this.file_name;
//        try {
//            srcFileBase64Md5 = BinaryUtil.toBase64String(BinaryUtil.calculateMd5(filePath));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        Log.i("testFileDownload",""+srcFileBase64Md5);

        Log.i("testFileDownload","haha-- "+getCallback.result.getStatusCode());
       // assertEquals(200, getCallback.result.getStatusCode());
        Log.i("testFileDownload","haha---");
        long length = getCallback.result.getContentLength();
        Log.i("testFileDownload","downloadFile "+length );
        byte[] buffer = new byte[(int) length];
        int readCount = 0;
        InputStream inputStream = getCallback.result.getObjectContent();
        Log.i("testFileDownload",""+inputStream);
        while (readCount < length) {
            try {
                readCount += inputStream.read(buffer, readCount, (int) length - readCount);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        File file=null;
        try {
            Log.i("testFileDownload","new file " );
           // file= new File(this.getExternalCacheDir(),this.file_name);
           file = new File(getDownDirs(),this.file_name);

            if (!file.exists()) {
                file.createNewFile();
            }

            FileOutputStream fout = new FileOutputStream(file);
            Log.i("testFileDownload","downloadFile -- "+file.getAbsolutePath() );
            fout.write(buffer);
            fout.close();

            Message message = new Message();
            message.what = DOWNLOAD_SUCCESS;
            handler.sendMessage(message);

        } catch (Exception e) {
            OSSLog.logInfo(e.toString());
            Log.i("FileDownload",e.toString() );
            Message message = new Message();
            message.what = DOWNLOAD_FAIL;
            handler.sendMessage(message);
        }
//        String downloadFileBase64Md5 = null;
//        try {
//            downloadFileBase64Md5 = BinaryUtil.toBase64String(BinaryUtil.calculateMd5(filePath));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
       // assertEquals(srcFileBase64Md5, downloadFileBase64Md5);
    }

    //获得系统Download目录
    public static File getDownDirs(){
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

}