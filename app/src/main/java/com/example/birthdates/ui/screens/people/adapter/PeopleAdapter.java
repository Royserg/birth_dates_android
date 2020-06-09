package com.example.birthdates.ui.screens.people.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.birthdates.R;
import com.example.birthdates.models.Person;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

public class PeopleAdapter extends ListAdapter<Person, PeopleAdapter.PersonViewHolder> {
    private OnItemClickListener listener;

    public PeopleAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Person> DIFF_CALLBACK = new DiffUtil.ItemCallback<Person>() {
        @Override
        public boolean areItemsTheSame(@NonNull Person oldItem, @NonNull Person newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Person oldItem, @NonNull Person newItem) {
            return oldItem.getName().equals(newItem.getName()) &&
                    oldItem.getBday().getTime() == newItem.getBday().getTime();
        }
    };

    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View personRow = inflater.inflate(R.layout.person_row, parent, false);

        return new PersonViewHolder(personRow);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, int position) {
        Person currentPerson = getItem(position);
        holder.setData(currentPerson);
    }

    /* ViewHolder */
    class PersonViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTextView;
        private TextView bdayTextView;
        private ImageView avatarImageView;
        private ImageView bdayIcon;

        public PersonViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.text_view_name);
            bdayTextView = itemView.findViewById(R.id.text_view_bday);
            avatarImageView = itemView.findViewById(R.id.image_view_avatar);
            bdayIcon = itemView.findViewById(R.id.bday_icon);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(getItem(position));
                }
            });
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        public void setData(Person person) {
            nameTextView.setText(person.getName());

            /* http://www.java2s.com/Tutorials/Java_Date_Time/Example/Date/Calculate_the_span_of_time_from_today_until_your_birthday.htm */
            Calendar c = Calendar.getInstance();
            c.setTime(person.getBday());

            LocalDate today = LocalDate.now();
            LocalDate birthday = LocalDate.ofYearDay(c.get(Calendar.YEAR), c.get(Calendar.DAY_OF_YEAR));
            LocalDate nextBDay = birthday.withYear(today.getYear());

            if (nextBDay.isBefore(today)) {
                nextBDay = nextBDay.plusYears(1);
            }

            if (nextBDay.isEqual(today)) {
                bdayTextView.setText("Happy Bday!!!");
                bdayTextView.setTextColor(Color.MAGENTA);
                bdayIcon.setColorFilter(Color.MAGENTA);
            } else {
                long daysPeriod = ChronoUnit.DAYS.between(today, nextBDay);
                bdayTextView.setText("in " + daysPeriod + " days");
            }
//            bdayTextView.setText(Utils.dateFormat.format(person.getBday().getTime()));
        }
    }

    /* OnClick Interface */
    public interface OnItemClickListener {
        void onItemClick(Person person);
    }

    public void setOnClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
