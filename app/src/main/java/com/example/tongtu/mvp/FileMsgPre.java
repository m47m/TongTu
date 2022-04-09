package com.example.tongtu.mvp;

import android.util.Log;

import com.example.tongtu.base.BasePresenter;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class FileMsgPre extends BasePresenter<FileMsgView> {
    private FileMsg fileMsg;
    public FileMsgPre(){this.fileMsg = new FileMsg();}

    public void toFileTest(String token,String size,String md5,String id){
        this.fileMsg.FileTest(token, size, md5, id, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    Log.d("testPostActivity fileTest:",jsonObject.toString());

                    if(getView() != null){
                        getView().ResultofFileTest(jsonObject.getString("code"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
