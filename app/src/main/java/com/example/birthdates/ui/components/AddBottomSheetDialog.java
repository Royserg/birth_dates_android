package com.example.birthdates.ui.components;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.birthdates.MainActivity;
import com.example.birthdates.R;
import com.example.birthdates.ui.screens.people.AddEditPersonDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddBottomSheetDialog extends BottomSheetDialogFragment {

    public static String TAG = "AddBottomSheetDialog";

    MainActivity mainActivity;
    LinearLayout addPersonLayout;
    LinearLayout addEventLayout;

    public AddBottomSheetDialog(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.add_bottom_sheet, container, false);

        addPersonLayout = root.findViewById(R.id.add_person_layout);
        addEventLayout = root.findViewById(R.id.add_event_layout);

        addPersonLayout.setOnClickListener(this::handleAddPersonClicked);
        addEventLayout.setOnClickListener(this::handleAddEventClicked);

        return root;
    }

    public void handleAddPersonClicked(View view) {
        // Close Bottom Sheet Dialog
        dismiss();

        openFullScreenDialog(new AddEditPersonDialog());
    }

    public void handleAddEventClicked(View view) {
        // Close Bottom Sheet Dialog
        dismiss();

//        openFullScreenDialog(FullScreenDialog.Type.EVENT);
    }

    private void openFullScreenDialog(DialogFragment dialog) {

        FragmentTransaction ft = mainActivity.getSupportFragmentManager().beginTransaction();
        dialog.show(ft, AddEditPersonDialog.TAG);
    }
}
