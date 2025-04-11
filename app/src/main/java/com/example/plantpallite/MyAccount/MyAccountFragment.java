package com.example.plantpallite.MyAccount;

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

import com.example.plantpallite.R;
import com.example.plantpallite.databinding.MyAccountFragmentBinding;

public class MyAccountFragment extends Fragment {

    private MyAccountViewModel mViewModel;
    private MyAccountFragmentBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = MyAccountFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(MyAccountViewModel.class);

        /// Retrieve userId from arguments
        Bundle args = getArguments();
        int userId = (args != null) ? args.getInt("userId", -1) : -1;

        if (userId == -1) {
            Log.e("MyShowAll", "Invalid userId passed!");
        }
        Log.d("MyAccount", "Retrieved userId: " + userId);

        //delete button
        binding.deleteAccountButton.setOnClickListener(v -> {
            mViewModel.deleteAccount(userId);
            // Navigate back to the login screen
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.action_myAccountFragment2_to_myLoginFragment);
        });

        // Logout button functionality
        binding.logOutButton.setOnClickListener(v -> {
            // Clear any stored user session (shared preferences, etc.)
            Log.d("MyAccountFragment", "User logged out: " );

            // Navigate back to the login screen
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.action_myAccountFragment2_to_myLoginFragment);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
