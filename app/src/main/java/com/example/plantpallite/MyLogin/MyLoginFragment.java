package com.example.plantpallite.MyLogin;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.plantpallite.R;
import com.example.plantpallite.databinding.MyLoginFragmentBinding;
import com.google.android.material.snackbar.Snackbar;

public class MyLoginFragment extends Fragment {

    private MyLoginViewModel mViewModel;
    private MyLoginFragmentBinding binding;

    public static MyLoginFragment newInstance() {
        return new MyLoginFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // return inflater.inflate(R.layout.my_login_fragment, container, false);
        binding = MyLoginFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        mViewModel = new ViewModelProvider(this).get(MyLoginViewModel.class);
//        // TODO: Use the ViewModel
//    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(MyLoginViewModel.class);

//        binding.loginLoginButton.setOnClickListener(v -> {
//            String email = binding.loginEmailTIL.getEditText().getText().toString();
//            String password = binding.loginPasswordTIL.getEditText().getText().toString();
//
//            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
//                Snackbar.make(view, "Please fill in all fields.", Snackbar.LENGTH_LONG).show();
//                return;
//            }

            // Trigger login in the ViewModel
//
//           // Observe the login result
//mViewModel.getLoginResult().observe(getViewLifecycleOwner(), user -> {         if (user != null) {
//                    NavController navController = Navigation.findNavController(view);
//                    navController.navigate(R.id.action_myLoginFragment_to_myShowAllPlantFragment);
//                } else {
//                    Snackbar.make(view, "Invalid email or password.", Snackbar.LENGTH_LONG).show();
//                }
//            });

            binding.loginLoginButton.setOnClickListener(v -> {
                String email = binding.loginEmailTIL.getEditText().getText().toString();
                String password = binding.loginPasswordTIL.getEditText().getText().toString();

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Snackbar.make(view, "Please fill in all fields.", Snackbar.LENGTH_LONG).show();
                    return;
                }

                mViewModel.login(email, password).observe(getViewLifecycleOwner(), user -> {
        if (user != null) {
// Navigate to the next fragment on successful login
NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_myLoginFragment_to_myShowAllPlantFragment);
        } else {
                // Show error message if login failed
                Snackbar.make(view, "Invalid email or password.", Snackbar.LENGTH_LONG).show();
        }

                    // Dynamically set the image resource
                    ImageView logo = view.findViewById(R.id.imageView); // Ensure  ImageView ID matches
                    logo.setImageResource(R.drawable.logo_color);   // Ensure the drawable name is correct

                });
                });

    }
    }
