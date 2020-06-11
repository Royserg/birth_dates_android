package com.example.birthdates.ui.screens.people.adapter;

import android.content.Context;
import android.graphics.Color;
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

        public void setData(Person person) {
            nameTextView.setText(person.getName());

            if (person.getDaysRemaining() == 0) {
                bdayTextView.setText("Birthday!!!");
                bdayTextView.setTextColor(Color.MAGENTA);
                bdayIcon.setColorFilter(Color.MAGENTA);
            } else {
                bdayTextView.setText("in " + person.getDaysRemaining() + " days");
            }
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
