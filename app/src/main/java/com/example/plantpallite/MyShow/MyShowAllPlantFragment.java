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

        // Retrieve userId from arguments
        Bundle args = getArguments();
        assert args != null;
        int userId = args.getInt("userId", -1);

        if (userId == -1) {
            Log.e("MyShowAllPlantFragment", "User ID not passed correctly!");
            return;
        }
        Log.d("MyShowAllPlantFragment", "Retrieved userId: " + userId);

        // Initialize ViewModel
        mViewModel = new ViewModelProvider(this).get(MyShowAllPlantViewModel.class);

        // Set userId in ViewModel
        mViewModel.setUserId(userId);
            // Setup RecyclerView
            RecyclerView recyclerView = binding.plantCardsRecyclerView; // Replace with actual RecyclerView ID
            recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
            // Initialize the adapter
            plantViewAdapter = new PlantViewAdapter(
                    //the constructor use list, clickListener, edit listener
                    null,
                    plant -> {
                        // Navigate to Plant Info screen
                        Bundle bundle = new Bundle();
                        bundle.putInt("plantId", plant.getId());
                        Navigation.findNavController(view).navigate(R.id.action_myShowAllPlantFragment_to_myPlantInfoFragment, bundle);
                    },
                    plant -> {
                        // Edit plant logic
                    }

            );
            recyclerView.setAdapter(plantViewAdapter);

            // Observe LiveData from ViewModel
            //I just use the admin ID for prototype
            mViewModel.fetchPlantsByUserId(userId)(getViewLifecycleOwner(), plants -> {
                if (plants != null) {
                    Log.d("PlantListSize", "Number of plants: " + plants.size());
                    plantViewAdapter.updateData(plants);
                }
            });




            // Add button functionality
            binding.myShowAllPlantAddButton.setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putInt("userID", userId);
                Navigation.findNavController(view).navigate(R.id.action_myShowAllPlantFragment_to_myAddPlantFragment, bundle);

            });


            //to avoid coming back to the login page. just set the pop behaviour as inclusive true
        }

    }
}