package com.example.tongtu.mvp;

import com.example.tongtu.base.BaseView;
import com.example.tongtu.filelist.FileList;
import com.example.tongtu.filerecycle.FileRecycle;
import com.example.tongtu.folderlist.FolderList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public interface PersonMsgView extends BaseView {
    void get_usermsg_result(JSONObject jsonObject);
    void get_folder_result(JSONArray jsonArray);
    void get_file_result(List<FileList> fileLists);
    void get_folder_file_result(List<FolderList> folderLists);
    void loadmore_folder_file_reuslt(String code,List<FileList> fileLists);
    void get_bin_file_result(List<FileRecycle> fileRecycleList);
}
