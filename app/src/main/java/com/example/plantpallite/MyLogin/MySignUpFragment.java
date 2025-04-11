package com.example.plantpallite.MyLogin;

import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.plantpallite.R;
import com.example.plantpallite.databinding.MySignUpFragmentBinding;
import com.example.plantpallite.User;
import com.google.android.material.snackbar.Snackbar;

public class MySignUpFragment extends Fragment {

    private MySignUpViewModel mViewModel;
    private MySignUpFragmentBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = MySignUpFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MySignUpViewModel.class);

        binding.signUpButton.setOnClickListener(v -> {
            String firstName = binding.firstNameInput.getEditText().getText().toString();
            String lastName = binding.lastNameInput.getEditText().getText().toString();
            String email = binding.emailInput.getEditText().getText().toString();
            String password = binding.passwordInput.getEditText().getText().toString();

            if (validateInputs(firstName, lastName, email, password)) {
                User newUser = new User(null, email, password, firstName, lastName);
                mViewModel.createUser(newUser).observe(getViewLifecycleOwner(), isSuccessful -> {
                    if (isSuccessful) {
                        Snackbar.make(view, "Account created successfully!", Snackbar.LENGTH_LONG).show();
                        getParentFragmentManager().popBackStack();
                    } else {
                        Snackbar.make(view, "Failed to create account. Email might already exist.", Snackbar.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    private boolean validateInputs(String firstName, String lastName, String email, String password) {
        if (TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName) ||
                TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Snackbar.make(binding.getRoot(), "Please fill in all fields.", Snackbar.LENGTH_LONG).show();
            return false;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Snackbar.make(binding.getRoot(), "Invalid email address.", Snackbar.LENGTH_LONG).show();
            return false;
        }
        if (password.length() < 6) {
            Snackbar.make(binding.getRoot(), "Password must be at least 6 characters.", Snackbar.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
