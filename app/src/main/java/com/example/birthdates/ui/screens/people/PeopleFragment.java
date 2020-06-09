package com.example.birthdates.ui.screens.people;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.birthdates.R;
import com.example.birthdates.ui.screens.people.adapter.PeopleAdapter;

public class PeopleFragment extends Fragment {

    private PeopleViewModel peopleViewModel;
    private PeopleAdapter peopleAdapter;
    private RecyclerView peopleRecyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        /* Get ViewModel */
        peopleViewModel =
                ViewModelProviders.of(this).get(PeopleViewModel.class);

        /* Inflate layout */
        View root = inflater.inflate(R.layout.fragment_people, container, false);

        /* Set Recycler View adapter */
        peopleRecyclerView = root.findViewById(R.id.people_recycler_view);
        peopleAdapter = new PeopleAdapter();
        peopleRecyclerView.setAdapter(peopleAdapter);

        /* Person Row clicked */
        peopleAdapter.setOnClickListener(person -> {
            /* Show Edit Dialog */
//            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//            AddEditPersonDialog dialog = new AddEditPersonDialog(person);
//            dialog.show(ft, AddEditPersonDialog.TAG);

        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        peopleViewModel.getAllPersons().observe(getViewLifecycleOwner(), people -> peopleAdapter.submitList(people));
    }
}
