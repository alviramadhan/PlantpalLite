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
import java.util.Calendar;
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

        mViewModel.getPlantById(plantId).observe(getViewLifecycleOwner(), plant -> {
            if (plant != null) {
                binding.plantNameButton.setText(plant.getName());

//                // Calculate watering and fertilizing schedules
//                String[] wateringDates = mViewModel.calculateWateringDates(plant);
//                String[] fertilizingDates = mViewModel.calculateFertilizingDates(plant);
//
//                // Update UI
//                updateWateringUI(wateringDates);
//                updateFertilizingUI(fertilizingDates);
//
//                // Bind checkbox click listeners
//                setupCheckboxListeners(plant, wateringDates, fertilizingDates);

                String[] wateringDates = mViewModel.calculateWateringDates(plant);
                String[] fertilizingDates = mViewModel.calculateFertilizingDates(plant);

                setupWateringCheckboxListeners(plant, wateringDates);
                setupFertilizingCheckboxListeners(plant, fertilizingDates);

                binding.wateringDateText1.setText(wateringDates[0]);
                binding.wateringDateText2.setText(wateringDates[1]);
                binding.wateringDateText3.setText(wateringDates[2]);
                binding.wateringDateText4.setText(wateringDates[3]);

                binding.fertilizingDateText1.setText(fertilizingDates[0]);
                binding.fertilizingDateText2.setText(fertilizingDates[1]);
                binding.fertilizingDateText3.setText(fertilizingDates[2]);
                binding.fertilizingDateText4.setText(fertilizingDates[3]);

                binding.wateringButton.setOnClickListener(v -> {
                    String selectedDate = binding.wateringDateText1.getText().toString();
                    Date newDate = parseDate(selectedDate); // Parse the selected date
                    mViewModel.updateLastWateredDate(plant, newDate); // Update last watered date
                    mViewModel.setLastUpdate(plant, newDate);
                    shiftAndUpdateWateringDates(plant);
                    Snackbar.make(view, "Watering dates updated!", Snackbar.LENGTH_SHORT).show();
                });

                binding.fertilizingButton.setOnClickListener(v -> {
                    String selectedDate = (String) binding.fertilizingDateText1.getText();
                    Date newDate = parseDate(selectedDate);
                    mViewModel.updateLastFertilizingDate(plant, newDate);
                    mViewModel.setLastUpdate(plant, newDate);
                    Snackbar.make(view, "Fertilizing dates updated!", Snackbar.LENGTH_SHORT).show();
                });

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
        binding.plantScheduleBackButton.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_myPlantScheduleFragment_to_myShowAllPlantFragment));


    }


//    private void setupWateringCheckboxListeners(Plant plant, String[] wateringDates) {
//      binding.wateringCheckbox1.setOnCheckedChangeListener((buttonView, isChecked) ->{
//
//      } );
//
//        binding.wateringCheckbox4.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            if (isChecked) {
//                Date newDate = parseDate(wateringDates[0]);
//                mViewModel.updateLastWateredDate(plant, newDate);
//                shiftAndUpdateWateringDates(plant);
//
//            }
//        });
//    }
//
//    private void setupFertilizingCheckboxListeners(Plant plant, String[] fertilizingDates) {
//        binding.fertilizingCheckbox4.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            if (isChecked) {
//                Date newDate = parseDate(fertilizingDates[0]);
//                mViewModel.updateLastFertilizingDate(plant, newDate);
//                shiftAndUpdateFertilizingDates(plant);
//            }
//        });

    private void setupWateringCheckboxListeners(Plant plant, String[] wateringDates) {
        binding.wateringCheckbox1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                String selectedDate = binding.wateringDateText1.getText().toString();
                Date newDate = parseDate(selectedDate); // Parse the selected date
                mViewModel.updateLastWateredDate(plant, newDate); // Update last watered date
                binding.wateringCheckbox1.setChecked(true); // Keep it checked
                mViewModel.setLastUpdate(plant, newDate);            }
        });

        binding.wateringCheckbox2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                String selectedDate = binding.wateringDateText2.getText().toString();
                Date newDate = parseDate(selectedDate);
                mViewModel.updateLastWateredDate(plant, newDate);
                binding.wateringCheckbox2.setChecked(true);
            }
        });

        binding.wateringCheckbox3.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                String selectedDate = binding.wateringDateText3.getText().toString();
                Date newDate = parseDate(selectedDate);
                mViewModel.updateLastWateredDate(plant, newDate);
                binding.wateringCheckbox3.setChecked(true);
            }
        });


        binding.wateringCheckbox4.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                String selectedDate = binding.wateringDateText4.getText().toString();
                Date newDate = parseDate(selectedDate);
                mViewModel.updateLastWateredDate(plant, newDate);
                binding.wateringCheckbox4.setChecked(true);
                shiftAndUpdateWateringDates(plant);
            }
        });

        // Set initial states for checkboxes based on the last update
        Date lastUpdateDate = plant.getLastUpdate();
        if (lastUpdateDate != null) {
            if (parseDate(wateringDates[0]).before(lastUpdateDate) || parseDate(wateringDates[0]).equals(lastUpdateDate)) {
                binding.wateringCheckbox1.setChecked(true);
            }
            if (parseDate(wateringDates[1]).before(lastUpdateDate) || parseDate(wateringDates[1]).equals(lastUpdateDate)) {
                binding.wateringCheckbox2.setChecked(true);
            }
            if (parseDate(wateringDates[2]).before(lastUpdateDate) || parseDate(wateringDates[2]).equals(lastUpdateDate)) {
                binding.wateringCheckbox3.setChecked(true);
            }
            if (parseDate(wateringDates[3]).before(lastUpdateDate) || parseDate(wateringDates[3]).equals(lastUpdateDate)) {
                binding.wateringCheckbox4.setChecked(true);
            }
        }
    }

        private void setupFertilizingCheckboxListeners(Plant plant, String[] fertilizingDates) {
            binding.fertilizingCheckbox1.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    String selectedDate = (String) binding.fertilizingDateText1.getText();
                    Date newDate = parseDate(selectedDate);
                    mViewModel.updateLastFertilizingDate(plant, newDate);
                    binding.fertilizingCheckbox1.setChecked(true);
                }
            });

            binding.fertilizingCheckbox2.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    String selectedDate = binding.fertilizingDateText2.getText().toString();
                    Date newDate = parseDate(selectedDate);
                    mViewModel.updateLastFertilizingDate(plant, newDate);
                    binding.fertilizingCheckbox2.setChecked(true);
                }
            });

            binding.fertilizingCheckbox3.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    String selectedDate = binding.fertilizingDateText3.getText().toString();
                    Date newDate = parseDate(selectedDate);
                    mViewModel.updateLastFertilizingDate(plant, newDate);
                    binding.fertilizingCheckbox3.setChecked(true);
                }
            });

            binding.fertilizingCheckbox4.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    String selectedDate = binding.fertilizingDateText4.getText().toString();
                    Date newDate = parseDate(selectedDate);
                    mViewModel.updateLastFertilizingDate(plant, newDate);
                    binding.fertilizingCheckbox4.setChecked(true);
                }
            });

            // Set initial states for checkboxes based on the last update
            Date lastUpdateDate = plant.getLastFertilizingDate();
            if (lastUpdateDate != null) {
                if (parseDate(fertilizingDates[0]).before(lastUpdateDate) || parseDate(fertilizingDates[0]).equals(lastUpdateDate)) {
                    binding.fertilizingCheckbox1.setChecked(true);
                }
                if (parseDate(fertilizingDates[1]).before(lastUpdateDate) || parseDate(fertilizingDates[1]).equals(lastUpdateDate)) {
                    binding.fertilizingCheckbox2.setChecked(true);
                }
                if (parseDate(fertilizingDates[2]).before(lastUpdateDate) || parseDate(fertilizingDates[2]).equals(lastUpdateDate)) {
                    binding.fertilizingCheckbox3.setChecked(true);
                }
                if (parseDate(fertilizingDates[3]).before(lastUpdateDate) || parseDate(fertilizingDates[3]).equals(lastUpdateDate)) {
                    binding.fertilizingCheckbox4.setChecked(true);
                }
            }
    }


        private void shiftAndUpdateWateringDates (Plant plant){
            String[] updatedDates = mViewModel.calculateWateringDates(plant);
            binding.wateringDateText1.setText(updatedDates[0]);
            binding.wateringDateText2.setText(updatedDates[1]);
            binding.wateringDateText3.setText(updatedDates[2]);
            binding.wateringDateText4.setText(updatedDates[3]);
        }

        private void shiftAndUpdateFertilizingDates (Plant plant){
            String[] updatedDates = mViewModel.calculateFertilizingDates(plant);
            binding.fertilizingDateText1.setText(updatedDates[0]);
            binding.fertilizingDateText2.setText(updatedDates[1]);
            binding.fertilizingDateText3.setText(updatedDates[2]);
            binding.fertilizingDateText4.setText(updatedDates[3]);
        }

        private Date parseDate (String dateString){
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                return dateFormat.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
                return new Date(); // Default to current date on error
            }
        }

//
//    private void updateWateringUI(String[] dates) {
//        binding.wateringDateText1.setText(dates[0]);
//        binding.wateringDateText2.setText(dates[1]);
//        binding.wateringDateText3.setText(dates[2]);
//        binding.wateringDateText4.setText(dates[3]);
//    }
//
//    private void updateFertilizingUI(String[] dates) {
//        binding.fertilizingDateText1.setText(dates[0]);
//        binding.fertilizingDateText2.setText(dates[1]);
//        binding.fertilizingDateText3.setText(dates[2]);
//        binding.fertilizingDateText4.setText(dates[3]);
//    }


        @Override
        public void onDestroyView () {
            super.onDestroyView();
            binding = null;
        }

    }

