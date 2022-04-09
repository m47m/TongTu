package com.example.tongtu.mvp;

import android.util.Log;

import com.example.tongtu.R;
import com.example.tongtu.base.BasePresenter;
import com.example.tongtu.filelist.FileList;
import com.example.tongtu.filerecycle.FileRecycle;
import com.example.tongtu.folderlist.FolderList;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PersonMsgPre extends BasePresenter<PersonMsgView> {
    private PersonMsg personMsg;
    public PersonMsgPre(){this.personMsg = new PersonMsg();}

    public void get_UserMsg(String token){
        this.personMsg.get_UserMsg(token, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try{
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                    if(getView() != null){
                        getView().get_usermsg_result(jsonObject1);
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }
    public void get_Folder(String token){
        this.personMsg.get_Folder(token, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try{
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    if(jsonObject.getString("code").equals("0")){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        if(getView() != null){
                            getView().get_folder_result(jsonArray);
                        }
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }

    public void get_File(String token){
        this.personMsg.get_file(token, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                   if(jsonObject.getString("code").equals("0")){
                       List<FileList> fileLists = new ArrayList<>();
                       JSONArray jsonArray = jsonObject.getJSONArray("data");
                       for(int i =0;i<jsonArray.length();i++){
                           fileLists.add(new FileList(
                                   jsonArray.getJSONObject(i).getString("name"),
                                   R.drawable.document_type_new,
                                   jsonArray.getJSONObject(i).getString("size"),
                                   jsonArray.getJSONObject(i).getString("uploadAt"),
                                   jsonArray.getJSONObject(i).getJSONObject("device").getString("id")
                           ));
                       }

                       if(getView() != null){
                           getView().get_file_result(fileLists);
                       }
                   }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public void get_FolderFile(String token,String folder){
        this.personMsg.get_FolderFile(folder, token, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());

                    if(jsonObject.getString("code").equals("0")){
                        List<FolderList> folderLists = new ArrayList<>();
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 0;i<jsonArray.length();i++){
                            folderLists.add(new FolderList(
                                    jsonArray.getJSONObject(i).getString("name"),
                                    jsonArray.getJSONObject(i).getString("description"),
                                    jsonArray.getJSONObject(i).getJSONObject("device").getString("name"),
                                    R.drawable.document_type_new
                            ));
                        }

                        if(getView() != null){
                            getView().get_folder_file_result(folderLists);
                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void LoadMoreFile(int page,String token){
        this.personMsg.LoadMoreFile(page,token, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    Log.d("testNoti gcode",jsonObject.getString("code"));
                    String code = jsonObject.getString("code");
                    List<FileList> fileLists = new ArrayList<>();
                    if(code.equals("0")){

                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i =0;i<jsonArray.length();i++){
                            fileLists.add(new FileList(
                                    jsonArray.getJSONObject(i).getString("name"),
                                    R.drawable.document_type_new,
                                    jsonArray.getJSONObject(i).getString("size"),
                                    jsonArray.getJSONObject(i).getString("uploadAt"),
                                    jsonArray.getJSONObject(i).getJSONObject("device").getString("id")
                            ));
                        }

                        if(getView() != null){
                            getView().loadmore_folder_file_reuslt("0",fileLists);
                        }
                    }else if(code.equals("1")){
                        if(getView() != null){
                            getView().loadmore_folder_file_reuslt("1",fileLists);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void get_BinFile(String token){

        Log.d("testDash","pre");
        this.personMsg.get_BinFile(token, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Log.d("testDash","succ");
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    Log.d("testDash",jsonObject.getString("code"));
                    String code = jsonObject.getString("code");
                    if(code.equals("0")){
                        List<FileRecycle> fileRecycles = new ArrayList<>();
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        Log.d("testDash",jsonArray.toString());
                        for(int i =0;i<jsonArray.length();i++){
                            fileRecycles.add(new FileRecycle(
                                jsonArray.getJSONObject(i).getString("name"),
                                jsonArray.getJSONObject(i).getString("fileType"),
                                jsonArray.getJSONObject(i).getString("size"),
                                R.drawable.document_type_new
                         ));
                      }

                    if(getView() != null){
                            getView().get_bin_file_result(fileRecycles);
                         }
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void LoadMoreBinFile(int page,String token){
        this.personMsg.LoadMoreBinFile(page, token, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response.body().string());
                    String code = jsonObject.getString("code");
                    List<FileRecycle> fileRecycles = new ArrayList<>();
                    if(code.equals("0")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        Log.d("testDash", jsonArray.toString());
                        for (int i = 0; i < jsonArray.length(); i++) {
                            fileRecycles.add(new FileRecycle(
                                    jsonArray.getJSONObject(i).getString("name"),
                                    jsonArray.getJSONObject(i).getString("fileType"),
                                    jsonArray.getJSONObject(i).getString("size"),
                                    R.drawable.document_type_new
                            ));
                        }

                        if(getView() != null){
                            getView().loadmore_bin_file_result("0",fileRecycles);
                        }
                    }else if(code.equals("1")){
                        if(getView() != null){
                            getView().loadmore_bin_file_result("1", fileRecycles);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }






            }
        });
    }
}

