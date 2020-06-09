package com.example.birthdates.ui.components;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.birthdates.R;
import com.example.birthdates.models.Person;
import com.example.birthdates.ui.screens.people.PeopleViewModel;
import com.example.birthdates.utils.Utils;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Date;

public class FullScreenDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    /* === Enum === */
    public enum Type {
        PERSON, EVENT
    }

    public static String TAG = "FullScreenDialog";

    private PeopleViewModel peopleViewModel;

    private Calendar mainDateCalendar = Calendar.getInstance();
    private Type type;

    private TextInputEditText nameInput;
    private TextInputEditText mainDateInput;
    private Button saveButton;

    /* === Constructor === */
    public FullScreenDialog(Type type) {
        this.type = type;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        /* Get People ViewModel */
        peopleViewModel =
                ViewModelProviders.of(this).get(PeopleViewModel.class);

        View root = new View(getActivity());
        if (type == Type.PERSON) {
            root = inflater.inflate(R.layout.add_person_dialog, container, false);
        } else if (type == Type.EVENT) {
            root = inflater.inflate(R.layout.add_event_dialog, container, false);
        }

        Toolbar toolbar = root.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_close_white_24);
        toolbar.setNavigationOnClickListener(view -> dismiss());

        switch(type) {
            case PERSON:
                toolbar.setTitle("Add Person");
                break;
            case EVENT:
                toolbar.setTitle("Add Event");
                break;
        }

        // Name input field
        nameInput = root.findViewById(R.id.input_name);
        nameInput.addTextChangedListener(nameTextWatcher);

        // Bday input field
        mainDateInput = root.findViewById(R.id.input_main_date);
            // Set Today's date for input field
        mainDateInput.setText(Utils.dateFormat.format(mainDateCalendar.getTime()));
        mainDateInput.setOnClickListener(this::handleBdayInputClicked);

        // Save button
        saveButton = root.findViewById(R.id.save_button);
        saveButton.setOnClickListener(this::handleSaveButtonClicked);

        return root;
    }

    /* Enable Save button when Name field is filled */
    private TextWatcher nameTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String nameInputText = nameInput.getText().toString().trim();
            saveButton.setEnabled(!nameInputText.isEmpty());
        }
        @Override
        public void afterTextChanged(Editable s) {}
    };


    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    /* Open DatePicker Dialog when bday input field pressed */
    public void handleBdayInputClicked(View view) {
        System.out.println("Bday Input Clicked");

        DialogFragment datePicker = new DatePickerFragment(mainDateCalendar, getActivity(), this);
        datePicker.show(getActivity().getSupportFragmentManager(), "DatePicker");
    }

    /* Method called when date from DatePicker changed */
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//        Calendar c = Calendar.getInstance();
//        c.set(Calendar.YEAR, year);
//        c.set(Calendar.MONTH, month);
//        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        mainDateCalendar.set(year, month, dayOfMonth);
//        DateFormat.getDateInstance().format(c.getTime());
        String currentDateString = Utils.dateFormat.format(mainDateCalendar.getTime());
        mainDateInput.setText(currentDateString);
    }

    public void handleSaveButtonClicked(View view) {

        if (type == Type.PERSON) {
            String nameInputText = nameInput.getText().toString();
            Date birthDate = mainDateCalendar.getTime();

            Person newPerson = new Person(nameInputText, birthDate);
            peopleViewModel.insert(newPerson);
        }

        dismiss();

    }
}
