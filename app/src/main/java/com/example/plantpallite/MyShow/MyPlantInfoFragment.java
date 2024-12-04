package com.example.plantpallite.MyShow;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
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

        // Initialize ViewModel directly
        mViewModel = new ViewModelProvider(this).get(MyPlantInfoViewModel.class);
//
//        // Retrieve Plant ID from arguments
//        Bundle args = getArguments();
//        if (args == null || !args.containsKey("plantId")) {
//            Log.e("MyPlantInfoFragment", "Plant ID not passed!");
//            return;
//        }
//        int plantId = args.getInt("plantId");
//        Log.d("MyPlantInfoFragment", "Retrieved plantId in info: " + plantId);
//
//        // Observe plant details and bind to UI
//        mViewModel.getPlantById(plantId).observe(getViewLifecycleOwner(), this::bindPlantData);

        // nEW Retrieve arguments
        Bundle args = getArguments();
        assert args != null; //prevent accessing null args
        int plantId = args.getInt("plantId", -1); // Default to -1 if not found

        if (args.containsKey("plantId")) {
                Log.d("MyPlantInfoFragment", "Retrieved plantId: " + plantId);

            if (plantId != -1) {
                //get plant ID from viewmodel
                mViewModel.getPlantById(plantId).observe(getViewLifecycleOwner(), plant -> {
                    if (plant != null) {
                        // Update UI with plant data
                        binding.plantNameButton.setText(plant.getName());
                        binding.plantTypeValue.setText(plant.getType());
                        binding.lastUpdateValue.setText(String.valueOf(plant.getLastUpdate()));
                        binding.plantAgeValue.setText(String.valueOf(plant.getPlantAgeInDays()));
                        binding.plantImage.setImageResource(R.drawable.plant_placeholder); // Placeholder image for now
                        binding.lastWateredValue.setText(String.valueOf(plant.getLastWateringDate()));
                        binding.lastFertilizedValue.setText(String.valueOf(plant.getLastFertilizingDate()));
                    }
                });
            } else {
                Log.e("MyPlantInfoFragment", "Invalid plantId passed!");
            }
        } else {
            Log.e("MyPlantInfoFragment", "No plantId passed!");
        }

        //Schedule tab
        binding.scheduleTabButton.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("plantID", plantId);
            Navigation.findNavController(view).navigate(R.id.action_myPlantInfoFragment_to_myPlantScheduleFragment, bundle);

            Log.d("MyPlantInfoFragment", "Passing plantId: " + plantId);

        });


        // Back button functionality
      //  binding.plantInfoBackButton.setOnClickListener(v ->
     //    Navigation.findNavController(view).navigate(R.id.action_myShowAllPlantFragment_to_myAddPlantFragment, bundle);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Prevent memory leaks
    }
}