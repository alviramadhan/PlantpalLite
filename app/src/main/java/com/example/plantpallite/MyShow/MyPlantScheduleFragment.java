package com.example.plantpallite.MyShow;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.example.plantpallite.Plant;
import com.example.plantpallite.R;
import com.example.plantpallite.databinding.MyPlantScheduleFragmentBinding;
import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MyPlantScheduleFragment extends Fragment {

    private MyPlantScheduleViewModel mViewModel;
    private MyPlantScheduleFragmentBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
       // return inflater.inflate(R.layout.my_plant_schedule_fragment, container, false);
        binding = MyPlantScheduleFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int plantId = getArguments() != null ? getArguments().getInt("plantId", -1) : -1;

        if (plantId == -1) {
            Snackbar.make(view, "Plant ID not provided!", Snackbar.LENGTH_SHORT).show();
            return;
        }

        mViewModel = new ViewModelProvider(this).get(MyPlantScheduleViewModel.class);

        // Observe the plant data
        mViewModel.getPlantById(plantId).observe(getViewLifecycleOwner(), plant -> {
            if (plant != null) {
                populateSchedules(plant);
            } else {
                Snackbar.make(view, "Plant not found!", Snackbar.LENGTH_SHORT).show();
            }
        });

        //Binding to Info Tab
        binding.infoTabButton.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.action_myPlantScheduleFragment_to_myPlantInfoFragment);
        });
    }

    private void populateSchedules(Plant plant) {

        // Retrieve Plant ID
        Bundle args = getArguments();
        if (args == null || !args.containsKey("plantId")) {
            Log.e("MyPlantScheduleFragment", "Plant ID not passed!");
            return;
        }
        int plantId = args.getInt("plantId");

        // Parse frequencies
        int wateringFrequency = Integer.parseInt(plant.getWateringFrequency());
        Date lastWateredDate = plant.getLastUpdate();

        int fertilizingFrequency = Integer.parseInt(plant.getFertilizingFrequency());
        Date lastFertilizedDate = plant.getLastUpdate();

        // Generate schedules via ViewModel
        List<String> wateringSchedule = mViewModel.generateWateringSchedule(wateringFrequency, lastWateredDate);
        populateScheduleUI(binding.wateringScheduleLayout, wateringSchedule);

        List<String> fertilizingSchedule = mViewModel.generateFertilizingSchedule(fertilizingFrequency, lastFertilizedDate);
        populateScheduleUI(binding.fertilizingScheduleLayout, fertilizingSchedule);
    }

    private void populateScheduleUI(ViewGroup container, List<String> schedule) {
        container.removeAllViews();
        for (String date : schedule) {
            CheckBox checkBox = new CheckBox(requireContext());
            checkBox.setText(date);
            container.addView(checkBox);
        }
    }

    // Helper method to parse String to Date
    private Date parseDate(String dateString) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date(); // Default to current date if parsing fails
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}