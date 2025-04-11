package com.example.plantpallite.MyShow;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.plantpallite.R;
import com.example.plantpallite.databinding.MyShowAllPlantFragmentBinding;

public class MyShowAllPlantFragment extends Fragment {

    private MyShowAllPlantViewModel mViewModel;
    private MyShowAllPlantFragmentBinding binding;
    private PlantViewAdapter plantViewAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = MyShowAllPlantFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Retrieve userId from arguments
        Bundle args = getArguments();
        int userId = (args != null) ? args.getInt("userId", -1) : -1;

        if (userId == -1) {
            Log.e("MyShowAllPlantFragment", "Invalid userId passed!");
        }
        Log.d("MyShowAllPlantFragment", "Retrieved userId: " + userId);


        // Initialize ViewModel
        mViewModel = new ViewModelProvider(this).get(MyShowAllPlantViewModel.class);

        // Set userId in ViewModel
        mViewModel.fetchPlantsByUserId(userId);

        // Setup RecyclerView
        RecyclerView recyclerView = binding.plantCardsRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Initialize the adapter
        plantViewAdapter = new PlantViewAdapter(
                null,
                plant -> {
                    // Navigate to Plant Info screen
                    Bundle bundle = new Bundle();
                    bundle.putInt("plantId", plant.getId());
                    Navigation.findNavController(view).navigate(R.id.action_myShowAllPlantFragment_to_myPlantInfoFragment, bundle);
                },
                plant -> {
                    // Delete plant logic
                    mViewModel.deletePlant(plant);
                }
        );
        recyclerView.setAdapter(plantViewAdapter);

        // Observe LiveData from ViewModel
        mViewModel.getPlants().observe(getViewLifecycleOwner(), plants -> {
            if (plants != null) {
                Log.d("PlantListSize", "Number of plants: " + plants.size());
                plantViewAdapter.updateData(plants);
            }
        });

        // Add button functionality
        binding.myShowAllPlantAddButton.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("userId", userId); // Pass userId to the AddPlantFragment
            Navigation.findNavController(view).navigate(R.id.action_myShowAllPlantFragment_to_myAddPlantFragment, bundle);
        });

        //account button
        binding.showAllAccountButton.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("userId", userId);
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.action_myShowAllPlantFragment_to_myAccountFragment2, bundle);
        });
    }
}
