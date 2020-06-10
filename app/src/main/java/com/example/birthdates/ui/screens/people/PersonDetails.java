package com.example.birthdates.ui.screens.people;

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
import com.example.birthdates.models.Person;
import com.example.birthdates.ui.components.NoBottomNavigationFragment;
import com.example.birthdates.utils.Utils;
import com.google.android.material.textfield.TextInputEditText;

public class PersonDetails extends NoBottomNavigationFragment {

    PeopleViewModel peopleViewModel;
    private TextInputEditText mainDateInput;
    private TextInputEditText nameInput;
    Person currentPerson;


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

        View root = inflater.inflate(R.layout.fragment_person_details, container, false);

        /* Get ViewModel */
        peopleViewModel =
                ViewModelProviders.of(this).get(PeopleViewModel.class);

        mainDateInput = root.findViewById(R.id.input_main_date);
        nameInput = root.findViewById(R.id.input_name);

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
            PersonDetailsArgs args = PersonDetailsArgs.fromBundle(getArguments());
            int id = args.getId();

            peopleViewModel.getPerson(id).observe(getViewLifecycleOwner(), person -> {
                currentPerson = person;
                nameInput.setText(person.getName());
                mainDateInput.setText(Utils.dateFormat.format(person.getBday()));
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
                deletePerson();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showEditScreen() {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        AddEditPersonDialog dialog = new AddEditPersonDialog(currentPerson);
        dialog.show(ft, AddEditPersonDialog.TAG);
    }

    private void deletePerson() {
        new AlertDialog.Builder(getActivity())
            .setTitle("Are you sure?")
            .setMessage("Do you want to delete " + currentPerson.getName())
            .setIcon(R.drawable.ic_delete_red_24)
            .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                peopleViewModel.delete(currentPerson);
                Navigation.findNavController(getView()).navigateUp();
            })
            .setNegativeButton(android.R.string.no, null)
            .show();
    }

}
