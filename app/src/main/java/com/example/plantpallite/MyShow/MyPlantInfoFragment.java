package com.example.plantpallite.MyShow;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.plantpallite.Database.MyPlantpalRepository;
import com.example.plantpallite.Plant;
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

        // Retrieve Plant ID from arguments
        Bundle args = getArguments();
        if (args == null || !args.containsKey("plantId")) {
            Log.e("MyPlantInfoFragment", "Plant ID not passed!");
            return;
        }
        int plantId = args.getInt("plantId");

        // Observe plant details and bind to UI
        mViewModel.getPlantById(plantId).observe(getViewLifecycleOwner(), this::bindPlantData);

        // Back button functionality
      //  binding.backButton.setOnClickListener(v -> Navigation.findNavController(v).navigateUp());
    }

    private void bindPlantData(Plant plant) {
        if (plant == null) {
            Log.e("MyPlantInfoFragment", "Plant not found!");
            return;
        }

        // Update UI with plant data
        binding.plantNameButton.setText(plant.getName());
        binding.plantTypeValue.setText(plant.getType());
        binding.plantAgeValue.setText(String.valueOf(plant.getPlantAgeInDays()));
        binding.plantImage.setImageResource(R.drawable.plant_placeholder); // Placeholder image for now
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Prevent memory leaks
    }
}