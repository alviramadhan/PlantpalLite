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
       // return inflater.inflate(R.layout.my_show_all_plant_fragment, container, false);
        binding = MyShowAllPlantFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        mViewModel = new ViewModelProvider(this).get(MyShowAllPlantViewModel.class);
//        // TODO: Use the ViewModel
//    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize ViewModel
        mViewModel = new ViewModelProvider(this).get(MyShowAllPlantViewModel.class);

        // Setup RecyclerView
        RecyclerView recyclerView = binding.plantCardsRecyclerView; // Replace with actual RecyclerView ID
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        plantViewAdapter = new PlantViewAdapter(null, plant -> {
            // Navigate to EditPlantFragment with selected plant
            // Placeholder for edit action
        });
        recyclerView.setAdapter(plantViewAdapter);

        // Observe LiveData from ViewModel
        mViewModel.getPlantsByUserId(1).observe(getViewLifecycleOwner(), plants -> {
            plantViewAdapter = new PlantViewAdapter(plants, plant -> {
                // Navigate to EditPlantFragment or handle plant edit
            });
            recyclerView.setAdapter(plantViewAdapter);
        });

        // Add button functionality
        binding.myShowAllPlantAddButton.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.action_myShowAllPlantFragment_to_myAddPlantFragment);
        });




        //to avoid coming back to the login page. just set the pop behaviour as inclusive true
    }
}