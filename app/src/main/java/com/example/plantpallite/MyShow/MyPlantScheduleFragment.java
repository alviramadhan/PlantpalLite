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

    /**
     * I will work on the dynamic part later.
     * For now, the schedule will use hardcoded data
     * /
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //initalize view model
        mViewModel = new ViewModelProvider(this).get(MyPlantScheduleViewModel.class);
        // Retrieve Plant ID from arguments
//        Bundle args = getArguments();
//        if (args == null || !args.containsKey("plantId")) {
//            Log.e("MyPlantInfoFragment", "Plant ID not passed!");
//            return;
//        }

        Bundle args = getArguments();
        assert args != null; //prevent accessing nul args
        int plantId = args.getInt("plantID", -1); // Default to -1 if plantId is not found

        if (plantId != -1) {
            Log.d("MyPlantScheduleFragment", "Retrieved plantId in schedule: " + plantId);
            // Use plantId as needed
        } else {
            Log.e("MyPlantScheduleFragment", "Invalid plantId passed!");
        }
        // Observe plant details and bind to UI
        mViewModel.getPlantById(plantId).observe(getViewLifecycleOwner(), this::bindPlantData);

        mViewModel.getPlantById(plantId).observe(getViewLifecycleOwner(), plant -> {
            if (plant != null) {
           //       populateSchedules(plant);
            } else {
                Snackbar.make(view, "Plant not found!", Snackbar.LENGTH_SHORT).show();
                Log.e("MyPlantScheduleFragment", "Plant data not found for ID: " + plantId);
            }
        });

        binding.infoTabButton.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("plantId", plantId);
            Navigation.findNavController(view).navigate(R.id.action_myPlantScheduleFragment_to_myPlantInfoFragment, bundle);

            Log.d("MyPlantInfoFragment", "Passing plantId from SChedule: " + plantId);
        });

        // Back button functionality
        binding.plantScheduleBackButton.setOnClickListener(v ->Navigation.findNavController(v).navigate(R.id.action_myPlantScheduleFragment_to_myShowAllPlantFragment));

    }


    private void bindPlantData(Plant plant) {
        if (plant == null) {
            Log.e("MyPlantInfoFragment", "Plant not found!");
            return;
        }

        // Update UI with plant data
        binding.plantNameButton.setText(plant.getName());
        binding.plantImage.setImageResource(R.drawable.plant_placeholder); // Placeholder image for now

    }


    private void populateSchedules(Plant plant) {

        Bundle args = getArguments();
        assert args != null; //prevent accessing nul args
        int plantId = args.getInt("plantID", -1); // Default to -1 if plantId is not found

        if (plantId != -1) {
            Log.d("MyPlantScheduleFragment", "Retrieved plantId in schedule2: " + plantId);
            // Use plantId as needed
        } else {
            Log.e("MyPlantScheduleFragment", "Invalid plantId passed!");
        }

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

    // method to parse String to Date
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