/*
 * File: SignupFragment.java
 * Author(s): Arman
 * Purpose: Handles user registration with validation.
 * Date created: 2023-10-27
 * Last modified: 2023-10-27
 * Dependencies: UserRepository, AuthActivity
 */
package com.example.designuxarman;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.designuxarman.repository.UserRepository;

public class SignupFragment extends Fragment {

    private EditText etEmail, etPassword;
    private UserRepository userRepository;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        userRepository = new UserRepository(requireContext());
        etEmail = view.findViewById(R.id.et_email);
        etPassword = view.findViewById(R.id.et_password);
        Button btnSignup = view.findViewById(R.id.btn_signup);
        TextView tvLogin = view.findViewById(R.id.tv_goto_login);

        btnSignup.setOnClickListener(v -> attemptSignup());
        tvLogin.setOnClickListener(v -> ((AuthActivity) requireActivity()).showLogin());

        return view;
    }

    private void attemptSignup() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (!email.contains("@")) {
            etEmail.setError(getString(R.string.invalid_email));
            return;
        }
        if (password.length() < 6) {
            etPassword.setError(getString(R.string.invalid_password));
            return;
        }

        if (userRepository.signup(email, password)) {
            userRepository.login(email, password); // Log in immediately after signup
            ((AuthActivity) requireActivity()).onAuthSuccess();
        } else {
            Toast.makeText(getContext(), "Signup failed", Toast.LENGTH_SHORT).show();
        }
    }
}
