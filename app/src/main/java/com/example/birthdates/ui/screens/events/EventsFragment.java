package com.example.birthdates.ui.screens.events;

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
import com.example.birthdates.ui.screens.events.adapter.EventsAdapter;

public class EventsFragment extends Fragment {

    private EventsViewModel eventsViewModel;
    private EventsAdapter eventsAdapter;
    private RecyclerView eventsRecyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        /* Get ViewModel */
        eventsViewModel =
                ViewModelProviders.of(this).get(EventsViewModel.class);

        /* Inflate layout */
        View root = inflater.inflate(R.layout.fragment_events, container, false);

        /* Set Recycler View adapter */
        eventsRecyclerView = root.findViewById(R.id.events_recycler_view);
        eventsAdapter = new EventsAdapter();
        eventsRecyclerView.setAdapter(eventsAdapter);

        /* Person Row clicked */
        eventsAdapter.setOnClickListener(event -> {
            EventsFragmentDirections.ActionNavigateToEventDetails action = EventsFragmentDirections.actionNavigateToEventDetails();
            action.setId(event.getId());

            Navigation.findNavController(root).navigate(action);
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

        eventsViewModel.getAllEvents().observe(getViewLifecycleOwner(), events -> eventsAdapter.submitList(events));
    }
}
