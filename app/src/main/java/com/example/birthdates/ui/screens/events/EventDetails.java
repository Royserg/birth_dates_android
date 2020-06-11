package com.example.birthdates.ui.screens.events;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.birthdates.R;
import com.example.birthdates.models.Event;
import com.example.birthdates.ui.components.NoBottomNavigationFragment;
import com.example.birthdates.utils.Utils;
import com.google.android.material.textfield.TextInputEditText;

public class EventDetails extends NoBottomNavigationFragment {

    EventsViewModel eventsViewModel;
    private TextInputEditText nameInput;
    private TextInputEditText descriptionInput;
    private TextInputEditText mainDateInput;
    Event currentEvent;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        /* Call super implementing hiding bottom Nav bar */
        super.onCreateView(inflater, container, savedInstanceState);

        View root = inflater.inflate(R.layout.fragment_event_details, container, false);

        /* Get ViewModel */
        eventsViewModel =
                ViewModelProviders.of(this).get(EventsViewModel.class);

        nameInput = root.findViewById(R.id.input_name);
        descriptionInput = root.findViewById(R.id.input_description);
        mainDateInput = root.findViewById(R.id.input_main_date);

        Toolbar toolbar = getActivity().findViewById(R.id.top_toolbar);
        toolbar.setNavigationOnClickListener(view -> Navigation.findNavController(root).navigateUp());
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24);
        toolbar.setTitle("Details");

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            EventDetailsArgs args = EventDetailsArgs.fromBundle(getArguments());
            int id = args.getId();

            eventsViewModel.getEvent(id).observe(getViewLifecycleOwner(), event -> {
                currentEvent = event;
                nameInput.setText(event.getName());
                descriptionInput.setText(event.getDescription());
                mainDateInput.setText(Utils.dateFormat.format(event.getDate()));
            });
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.details_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.details_edit:
                showEditScreen();
                break;
            case R.id.details_delete:
                deleteEvent();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showEditScreen() {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        AddEditEventDialog dialog = new AddEditEventDialog(currentEvent);
        dialog.show(ft, AddEditEventDialog.TAG);
    }

    private void deleteEvent() {
        /*https://stackoverflow.com/a/5127506/8421735*/
        new AlertDialog.Builder(getActivity())
                .setTitle("Are you sure?")
                .setMessage("Do you want to delete " + currentEvent.getName())
                .setIcon(R.drawable.ic_delete_red_24)
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    eventsViewModel.delete(currentEvent);
                    Navigation.findNavController(getView()).navigateUp();
                })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }
}
