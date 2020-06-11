package com.example.birthdates.ui.screens.home.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.birthdates.R;
import com.example.birthdates.models.Event;
import com.example.birthdates.models.Person;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private OnItemClickListener listener;

    private final static int TYPE_PERSON = 1;
    private final static int TYPE_EVENT = 2;

    private List<Comparable> data = new ArrayList<>();

    public HomeAdapter() {
    }

    public void add(List<Comparable> list) {
        if (data == null) {
            data = new ArrayList<>();
        }
        data.clear();
        data.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view;
        if (viewType == TYPE_PERSON) {
            view = LayoutInflater.from(context).inflate(R.layout.person_row, parent, false);
            return new PersonViewHolder(view);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.event_row, parent, false);
            return new EventViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        /* This worked*/
        int viewType = holder.getItemViewType();
        if (viewType == TYPE_PERSON) {
            ((PersonViewHolder) holder).setDetails((Person) data.get(position));
        } else {
            ((EventViewHolder) holder).setDetails((Event) data.get(position));
        }
    }


    @Override
    public int getItemViewType(int position) {
        Comparable element = data.get(position);
        if (element instanceof Person) {
            return TYPE_PERSON;
        } else if (element instanceof Event) {
            return TYPE_EVENT;
        }

        throw new IllegalArgumentException("Invalid position " + position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    /* Person View Holder */
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
                    listener.onPersonClick((Person) data.get(position));
                }
            });
        }

        private void setDetails(Person person) {
            nameTextView.setText(person.getName());
            bdayTextView.setText("Birthday!!!");
            bdayTextView.setTextColor(Color.MAGENTA);
            bdayIcon.setColorFilter(Color.MAGENTA);
        }
    }

    /* Event View Holder */
    class EventViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTextView;
        private TextView dateTextView;
        private TextView dateTextViewLabel;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.text_view_name);
            dateTextView = itemView.findViewById(R.id.text_view_date);
            dateTextViewLabel = itemView.findViewById(R.id.text_view_date_label);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onEventClick((Event) data.get(position));
                }
            });
        }

        private void setDetails(Event event) {
            nameTextView.setText(event.getName());
            dateTextView.setText("Today!!!");
            dateTextView.setTextColor(Color.MAGENTA);
            dateTextViewLabel.setTextColor(Color.MAGENTA);
        }
    }

    /* OnClick Interface */
    public interface OnItemClickListener {
        void onPersonClick(Person person);
        void onEventClick(Event event);
    }

    public void setOnClickListener(HomeAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}
