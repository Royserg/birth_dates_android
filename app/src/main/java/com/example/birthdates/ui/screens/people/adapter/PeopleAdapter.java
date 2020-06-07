package com.example.birthdates.ui.screens.people.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.birthdates.R;
import com.example.birthdates.models.Person;
import com.example.birthdates.utils.Utils;

public class PeopleAdapter extends ListAdapter<Person, PeopleAdapter.PersonViewHolder> {
    private Context context;

    public PeopleAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
    }

    private static final DiffUtil.ItemCallback<Person> DIFF_CALLBACK = new DiffUtil.ItemCallback<Person>() {
        @Override
        public boolean areItemsTheSame(@NonNull Person oldItem, @NonNull Person newItem) {
            return false;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Person oldItem, @NonNull Person newItem) {
            return false;
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

        public PersonViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.text_view_name);
            bdayTextView = itemView.findViewById(R.id.text_view_bday);
        }

        public void setData(Person person) {
            nameTextView.setText(person.name);
            bdayTextView.setText(Utils.dateFormat.format(person.bday));
        }
    }
}
