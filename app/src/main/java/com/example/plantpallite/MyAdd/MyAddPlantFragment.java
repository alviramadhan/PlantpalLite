package com.example.plantpallite.MyAdd;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.example.plantpallite.Plant;
import com.example.plantpallite.R;
import com.example.plantpallite.databinding.MyAddPlantFragmentBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.Date;

public class MyAddPlantFragment extends Fragment {

    private MyAddPlantViewModel mViewModel;
    private MyAddPlantFragmentBinding binding;

    public static MyAddPlantFragment newInstance() {
        return new MyAddPlantFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //this one does not use viewbinding that's why get commented
        // return inflater.inflate(R.layout.my_add_plant_fragment, container, false);
        binding = MyAddPlantFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    // creating the UI first before manipulating
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize ViewModel
        mViewModel = new ViewModelProvider(this).get(MyAddPlantViewModel.class);

        // Retrieve userId from arguments
        Bundle args = getArguments();
        if (args != null && args.containsKey("userId")) {
            int userId = args.getInt("userId", -1); // Default to -1 if not found
            if (userId != -1) {
                mViewModel.setUserId(userId); // Pass userId to ViewModel
                Log.d("MyAddPlantFragment", "Retrieved and set userId: " + userId);
            } else {
                Log.e("MyAddPlantFragment", "Invalid userId passed!");
            }
        } else {
            Log.e("MyAddPlantFragment", "No userId found in arguments!");
        }

        //if the data from viewmodel exist already, get and set it.
        Plant myPlant = mViewModel.getMyPlant();
        binding.myAddPlantNameTIL.getEditText().setText(myPlant.getName());
        if (myPlant != null) {

            binding.myAddPlantWateringFrequencySpin.setSelection(getSpinnerIndex(binding.myAddPlantWateringFrequencySpin, myPlant.getWateringFrequency()));
            binding.myAddPlantFertilizerFrequencySpin.setSelection(getSpinnerIndex(binding.myAddPlantFertilizerFrequencySpin, myPlant.getFertilizingFrequency()));
            if (myPlant.getType() != null) {
                for (int i = 0; i < binding.myAddPlantTypeRadioGroup.getChildCount(); i++) {
                    RadioButton radioButton = (RadioButton) binding.myAddPlantTypeRadioGroup.getChildAt(i);
                    if (radioButton.getText().toString().equals(myPlant.getType())) {
                        radioButton.setChecked(true);
                        break;
                    }
                }

                // Update ViewModel when EditText changes
                binding.myAddPlantNameTIL.getEditText().addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        // Update ViewModel's plant name
                        myPlant.setName(s.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

            }
        }



        binding.myAddPlantSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.myAddPlantNameTIL.getEditText().getText().toString();
                if (name.isBlank()) {
                    Snackbar.make(view, "Please name your plant", Snackbar.LENGTH_LONG).show();
                    return;
                }

                // Get selected plant type
                int selectedTypeId = binding.myAddPlantTypeRadioGroup.getCheckedRadioButtonId();
                RadioButton selectedRadioButton = binding.myAddPlantTypeRadioGroup.findViewById(selectedTypeId);
                String plantType = (selectedRadioButton != null) ? selectedRadioButton.getText().toString() : "Unknown";

                // Get selected watering frequency
                String wateringFrequency = binding.myAddPlantWateringFrequencySpin.getSelectedItem().toString();

                // Get selected fertilizer frequency
                String fertilizerFrequency = binding.myAddPlantFertilizerFrequencySpin.getSelectedItem().toString();

//                Plant plant = new Plant();
//                plant.setName(name);
//                plant.setId(1);
//                plant.setUserID(1);
//                plant.setFertilizingFrequency(fertilizerFrequency);
//                plant.setWateringFrequency(wateringFrequency);
//                plant.setType(plantType);

                //Snackbar.make(view, binding.getRoot(), Snackbar.LENGTH_LONG).show();
          //      Snackbar.make(view, myPlant.toString(), Snackbar.LENGTH_LONG).setTextMaxLines(8).show();

                // Update the Plant object in ViewModel
                myPlant.setName(name);
                myPlant.setType(plantType);
               // myPlant.setId(myPlant.getId());
                myPlant.setWateringFrequency(wateringFrequency);
                myPlant.setFertilizingFrequency(fertilizerFrequency);
                myPlant.setLastUpdate(new Date(System.currentTimeMillis()));

                mViewModel.saveNewPlant();
                Snackbar.make(view, "Saved!\n" + myPlant.toString(), Snackbar.LENGTH_LONG).setTextMaxLines(8).show();

                //nav to show all plant
                Bundle bundle = new Bundle();
                int userId = myPlant.getUserID();
                bundle.putInt("userId", userId);
                Navigation.findNavController(view).navigate(R.id.action_myAddPlantFragment_to_myShowAllPlantFragment, bundle);
            }
        });

// Cancel button functionality
        binding.myAddPlantCancelBtn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Snackbar.make(binding.getRoot(), "Action cancelled.", Snackbar.LENGTH_SHORT).show();
//                NavController navController = Navigation.findNavController(v);
//               navController.popBackStack(); //this one will delete the add plant then redirect to show all UI.

            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private int getSpinnerIndex(Spinner spinner, String value) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equals(value)) {
                return i;
            }
        }
        return 0; // Default to first item if value not found

    }
}
