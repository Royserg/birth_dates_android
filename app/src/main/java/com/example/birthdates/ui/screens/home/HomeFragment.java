package com.example.birthdates.ui.screens.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.birthdates.R;
import com.example.birthdates.models.Event;
import com.example.birthdates.models.Person;
import com.example.birthdates.ui.screens.home.adapter.HomeAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private HomeAdapter homeAdapter;
    private RecyclerView homeRecyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        /* Get ViewModel */
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);

        /* Set Recycler View adapter */
        homeRecyclerView = root.findViewById(R.id.home_recycler_view);
        homeAdapter = new HomeAdapter();
        homeRecyclerView.setAdapter(homeAdapter);

        homeAdapter.setOnClickListener(new HomeAdapter.OnItemClickListener() {
            @Override
            public void onPersonClick(Person person) {
                HomeFragmentDirections.ActionNavigateHomeToPersonDetails action = HomeFragmentDirections.actionNavigateHomeToPersonDetails();
                action.setId(person.getId());

                Navigation.findNavController(root).navigate(action);
            }

            @Override
            public void onEventClick(Event event) {
                HomeFragmentDirections.ActionNavigateHomeToEventDetails action = HomeFragmentDirections.actionNavigateHomeToEventDetails();
                action.setId(event.getId());

                Navigation.findNavController(root).navigate(action);
            }
        });

        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main_app_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        homeViewModel.getBirthdayPersons().observe(getViewLifecycleOwner(), people -> {
            homeViewModel.getTodayEvents().observe(getViewLifecycleOwner(), events -> {
                List<Comparable> data = new ArrayList<>();

                data.addAll(people);
                data.addAll(events);
                homeAdapter.add(data);
            });
        });
    }


}
