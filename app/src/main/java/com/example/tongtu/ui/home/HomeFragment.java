package com.example.tongtu.ui.home;

import android.os.Bundle;
import android.telephony.mbms.MbmsErrors;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tongtu.R;
import com.example.tongtu.databinding.FragmentHBinding;
import com.example.tongtu.filepost.FileAdapter;
import com.example.tongtu.filepost.FilePost;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    //    private FragmentHBinding binding;
    private List<FilePost> file_post_list = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
//
//        binding = FragmentHBinding.inflate(inflater, container, false);
//        View root = binding.getRoot();
//
//        init_file();
//
////        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recycler_view);
////        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
////        recyclerView.setLayoutManager(layoutManager);
////        FileAdapter adapter = new FileAdapter(file_post_list);
////        recyclerView.setAdapter(adapter);
//
//
//        return root;

        View root = inflater.inflate(R.layout.fragment_h,container,false);

        init_file();

        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recycler_view);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        LinearLayoutManager layoutManager = new LinearLayoutManager(root.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        FileAdapter adapter = new FileAdapter(file_post_list);
        recyclerView.setAdapter(adapter);

        return root;
    }
    private void init_file(){
        for(int i = 0;i<20;i++){
            FilePost file_post_1 = new FilePost("这是个"+Integer.toString(i)+"文件",R.drawable.ic_baseline_settings_24);
            file_post_list.add(file_post_1);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        binding = null;
    }
}