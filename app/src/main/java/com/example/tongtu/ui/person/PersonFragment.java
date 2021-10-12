package com.example.tongtu.ui.person;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.tongtu.R;
import com.example.tongtu.databinding.FragmentPBinding;
import com.example.tongtu.ui.person.PersonViewModel;


public class PersonFragment extends Fragment {

    private PersonViewModel personViewModel;
//    private FragmentPBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        personViewModel = new ViewModelProvider(this).get(PersonViewModel.class);
//
//        binding = FragmentPBinding.inflate(inflater, container, false);
//        View root = binding.getRoot();
//
//        final TextView textView = binding.textPerson;
//        personViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
//        return root;

        View root = inflater.inflate(R.layout.fragment_p,container,false);

        return root;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        binding = null;
    }
}
