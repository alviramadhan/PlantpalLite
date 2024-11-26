package com.example.plantpallite.MyShow;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.plantpallite.R;
import com.example.plantpallite.databinding.MyPlantInfoFragmentBinding;

public class MyPlantInfoFragment extends Fragment {

    private MyPlantInfoViewModel mViewModel;
    private MyPlantInfoFragmentBinding binding;

    public static MyPlantInfoFragment newInstance() {
        return new MyPlantInfoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //   return inflater.inflate(R.layout.my_plant_info_fragment, container, false);
        binding = MyPlantInfoFragmentBinding.inflate(inflater, container, false);
        return  binding.getRoot();
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        mViewModel = new ViewModelProvider(this).get(MyPlantInfoViewModel.class);
//        // TODO: Use the ViewModel
//    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(MyPlantInfoViewModel.class);

    }
}