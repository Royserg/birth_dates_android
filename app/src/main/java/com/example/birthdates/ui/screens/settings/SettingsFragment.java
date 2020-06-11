package com.example.birthdates.ui.screens.settings;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.PreferenceFragmentCompat;

import com.example.birthdates.R;
import com.example.birthdates.ui.components.NoBottomNavigationFragment;

public class SettingsFragment extends NoBottomNavigationFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        View root = inflater.inflate(R.layout.settings_layout, container, false);

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings_frame, new SettingsFragment.PreferencesFragment())
                .addToBackStack(null)
                .commit();

        return root;
    }


    public static class PreferencesFragment extends PreferenceFragmentCompat {

        public static final String FRAGMENT_TAG = "PREFERENCE_FRAGMENT";

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            ActionBar actionBar = getActivity().getActionBar();
            if (actionBar != null) actionBar.setTitle("Custom Title");

            super.onActivityCreated(savedInstanceState);
        }
    }
}
