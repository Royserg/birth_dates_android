package com.example.birthdates.ui.screens.people;

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
import com.example.birthdates.ui.components.DatePickerFragment;
import com.example.birthdates.utils.Utils;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class AddEditPersonDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    public static String TAG = "FullScreenDialog";

    private PeopleViewModel peopleViewModel;
    private Date mainDate;

    private Person person;
    private TextInputEditText nameInput;
    private TextInputEditText mainDateInput;
    private Button saveButton;

    /* === Constructor === */
    public AddEditPersonDialog() {

    }

    public AddEditPersonDialog(Person person) {
        this.person = person;
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

        View root = inflater.inflate(R.layout.add_edit_person_dialog, container, false);

        Toolbar toolbar = root.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_close_white_24);
        toolbar.setNavigationOnClickListener(view -> dismiss());

        // Name input field
        nameInput = root.findViewById(R.id.input_name);
        // Bday input field
        mainDateInput = root.findViewById(R.id.input_main_date);
        mainDateInput.setOnClickListener(this::handleBdayInputClicked);
        // Save button
        saveButton = root.findViewById(R.id.save_button);
        saveButton.setOnClickListener(this::handleSaveButtonClicked);

        // Attach Text Changed listener
        nameInput.addTextChangedListener(nameTextWatcher);

        /* Change toolbar title */
        if (person == null) {
            toolbar.setTitle("Add Person");
            mainDate = Calendar.getInstance().getTime();
        } else {
            toolbar.setTitle("Edit " + person.getName());
            mainDate = person.getBday();
            nameInput.setText(person.getName());
            saveButton.setEnabled(true);
        }

        // Set Today's date for input field
        mainDateInput.setText(Utils.dateFormat.format(mainDate));

        return root;
    }

    /* Enable Save button when Name field is filled */
    private TextWatcher nameTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
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
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            dialog.getWindow().setLayout(width, height);
        }
    }

    /* Open DatePicker Dialog when bday input field pressed */
    public void handleBdayInputClicked(View view) {
        System.out.println("Bday Input Clicked");

        Calendar c = Calendar.getInstance();
        c.setTime(mainDate);

        DialogFragment datePicker = new DatePickerFragment(c, getActivity(), this);
        datePicker.show(getActivity().getSupportFragmentManager(), "DatePicker");
    }

    /* Method called when date from DatePicker changed */
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, dayOfMonth);

        String currentDateString = Utils.dateFormat.format(c.getTime());
        mainDateInput.setText(currentDateString);
    }

    public void handleSaveButtonClicked(View view) {
        String nameInputText = nameInput.getText().toString();
        String dateText = mainDateInput.getText().toString();

        Date newDate = null;
        try {
            newDate = Utils.dateFormat.parse(dateText);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Person newPerson = new Person(nameInputText, newDate);

        if (person == null) {
            // Saving a new person
            peopleViewModel.insert(newPerson);
        } else {
            // Updaing existing person
            newPerson.setId(person.getId());
            peopleViewModel.update(newPerson);
        }

        dismiss();
    }
}
