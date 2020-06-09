package com.example.birthdates.ui.components;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment {

    private Calendar calendar;
    private Context ctx;
    private DatePickerDialog.OnDateSetListener listener;

    public DatePickerFragment(Calendar calendar, Context ctx, DatePickerDialog.OnDateSetListener listener) {
        this.calendar = calendar;
        this.ctx = ctx;
        this.listener = listener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(ctx, AlertDialog.THEME_DEVICE_DEFAULT_DARK, listener, year, month, day);
    }
}
