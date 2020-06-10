package com.example.birthdates.ui.components;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.birthdates.R;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NoBottomNavigationFragment extends Fragment {

    private BottomAppBar bottomAppBar;
    private FloatingActionButton fab;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            bottomAppBar = getActivity().findViewById(R.id.bottom_app_bar);
            fab = getActivity().findViewById(R.id.fab);
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (bottomAppBar != null && fab != null) {
            bottomAppBar.setVisibility(View.GONE);
            fab.setVisibility(View.GONE);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (bottomAppBar != null && fab != null) {
            bottomAppBar.setVisibility(View.VISIBLE);
            fab.setVisibility(View.VISIBLE);
        }
    }

}
