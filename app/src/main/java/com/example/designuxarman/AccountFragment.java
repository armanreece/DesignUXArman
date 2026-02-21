/*
 * File: AccountFragment.java
 * Author(s): Arman
 * Purpose: Displays user information and provides account management options.
 * Date created: 2023-10-27
 * Last modified: 2023-10-27
 * Dependencies: UserRepository, AuthActivity, PreferencesActivity
 */
package com.example.designuxarman;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import com.example.designuxarman.repository.UserRepository;

public class AccountFragment extends Fragment {

    private UserRepository userRepository;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        userRepository = new UserRepository(requireContext());

        TextView tvEmail = view.findViewById(R.id.tv_user_email);
        Button btnEditPrefs = view.findViewById(R.id.btn_edit_preferences);
        Button btnSignOut = view.findViewById(R.id.btn_sign_out);
        Button btnDeleteAccount = view.findViewById(R.id.btn_delete_account);

        tvEmail.setText(userRepository.getUserEmail());

        btnEditPrefs.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), PreferencesActivity.class);
            startActivity(intent);
        });

        btnSignOut.setOnClickListener(v -> signOut());

        btnDeleteAccount.setOnClickListener(v -> showDeleteConfirmation());

        return view;
    }

    private void signOut() {
        userRepository.logout();
        navigateToAuth();
    }

    private void showDeleteConfirmation() {
        new AlertDialog.Builder(requireContext())
                .setTitle(R.string.delete_account)
                .setMessage(R.string.confirm_delete)
                .setPositiveButton(R.string.confirm, (dialog, which) -> {
                    userRepository.deleteAccount();
                    navigateToAuth();
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    private void navigateToAuth() {
        Intent intent = new Intent(getContext(), AuthActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
