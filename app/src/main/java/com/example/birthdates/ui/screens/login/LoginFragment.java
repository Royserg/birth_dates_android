package com.example.birthdates.ui.screens.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.birthdates.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static androidx.navigation.fragment.NavHostFragment.findNavController;

public class LoginFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_login, container, false);

        Button signInButton = root.findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(this::handleSuccessLogin);

        return root;
    }

    public void handleSuccessLogin(View view) {

        // Target bottom appbar
        BottomNavigationView navView = getActivity().findViewById(R.id.bottom_app_bar);

        getActivity().findViewById(R.id.fab).setVisibility(View.VISIBLE);

        // Re-inflate appbar with main bottom menu
        navView.getMenu().clear();
        navView.inflateMenu(R.menu.bottom_nav_menu);

        findNavController(this).navigate(R.id.navigation_people);

    }

}
