package com.example.tongtu.mvp;

import android.util.Log;

import com.example.tongtu.R;
import com.example.tongtu.base.BasePresenter;
import com.example.tongtu.filelist.FileList;
import com.example.tongtu.filerecycle.FileRecycle;
import com.example.tongtu.folderlist.FolderList;
import com.example.tongtu.utils.FileTypeutils;
import com.example.tongtu.utils.TimeTypeutils;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PersonMsgPre extends BasePresenter<PersonMsgView> {
    private PersonMsg personMsg;
    public PersonMsgPre(){this.personMsg = new PersonMsg();}
    TimeTypeutils timeTypeutils = new TimeTypeutils();
    FileTypeutils fileTypeutils = new FileTypeutils();

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
                                   jsonArray.getJSONObject(i).getString("id"),
                                   jsonArray.getJSONObject(i).getString("name"),
                                   fileTypeutils.getType(jsonArray.getJSONObject(i).getString("fileType")),
                                   jsonArray.getJSONObject(i).getString("size"),
                                   jsonArray.getJSONObject(i).getString("uploadAt"),
                                   jsonArray.getJSONObject(i).getJSONObject("device").getString("id"),
                                   jsonArray.getJSONObject(i).getString("folder")
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
                                    jsonArray.getJSONObject(i).getString("id"),
                                    jsonArray.getJSONObject(i).getString("name"),
                                    jsonArray.getJSONObject(i).getString("description"),
                                    timeTypeutils.toNormal(jsonArray.getJSONObject(i).getString("uploadAt")),
                                    fileTypeutils.getType(jsonArray.getJSONObject(i).getString("fileType")),
                                    jsonArray.getJSONObject(i).getString("folder")
                            ));
                        }


                        if(getView() != null){
                            getView().get_folder_file_result(folderLists);
                        }
                    }


                } catch (JSONException | ParseException e) {
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
                                    jsonArray.getJSONObject(i).getString("id"),
                                    jsonArray.getJSONObject(i).getString("name"),
                                    fileTypeutils.getType(jsonArray.getJSONObject(i).getString("fileType")),
                                    jsonArray.getJSONObject(i).getString("size"),
                                    jsonArray.getJSONObject(i).getString("uploadAt"),
                                    jsonArray.getJSONObject(i).getJSONObject("device").getString("id"),
                                    jsonArray.getJSONObject(i).getString("folder")
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
                                    fileTypeutils.getType(jsonArray.getJSONObject(i).getString("fileType"))
                         ));
                      }

                    if(getView() != null){
                        Log.d("testDash one",String.valueOf(fileRecycles.size()));
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
                                    fileTypeutils.getType(jsonArray.getJSONObject(i).getString("fileType"))
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

    public void  DeleteFile(String fileID,String token){
        this.personMsg.DeleteFile(fileID, token, new Callback() {
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

                        if(getView() != null){
                            getView().delete_file_result(code);
                        }
                    }else if(code.equals("1")){
                        if(getView() != null){
                            getView().delete_file_result(code);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void RestoreFile(String fileID,String token){
        this.personMsg.RestoreFile(fileID, token, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

            }
        });
    }

    public void CPDeleteFile(String fileID,String token){
        this.personMsg.CPDeleteFile(fileID, token, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

            }
        });
    }

}

