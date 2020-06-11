package com.example.birthdates.ui.screens.events.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.birthdates.R;
import com.example.birthdates.models.Event;


public class EventsAdapter extends ListAdapter<Event, EventsAdapter.EventViewHolder> {
    private EventsAdapter.OnItemClickListener listener;

    public EventsAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Event> DIFF_CALLBACK = new DiffUtil.ItemCallback<Event>() {
        @Override
        public boolean areItemsTheSame(@NonNull Event oldItem, @NonNull Event newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Event oldItem, @NonNull Event newItem) {
            return oldItem.getName().equals(newItem.getName()) &&
                    oldItem.getDate().getTime() == newItem.getDate().getTime() &&
                    oldItem.getDescription().equals(newItem.getDescription());
        }
    };

    @NonNull
    @Override
    public EventsAdapter.EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View eventRow = inflater.inflate(R.layout.event_row, parent, false);

        return new EventsAdapter.EventViewHolder(eventRow);
    }

    @Override
    public void onBindViewHolder(@NonNull EventsAdapter.EventViewHolder holder, int position) {
        Event currentEvent = getItem(position);
        holder.setData(currentEvent);
    }

    /* ViewHolder */
    class EventViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTextView;
        private TextView dateTextView;
        private TextView dateLabelTextView;
        private TextView descriptionTextView;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.text_view_name);
            dateTextView = itemView.findViewById(R.id.text_view_date);
            dateLabelTextView = itemView.findViewById(R.id.text_view_date_label);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(getItem(position));
                }
            });
        }

        public void setData(Event event) {
            nameTextView.setText(event.getName());

            int daysRemaining = event.getDaysRemaining();
            if (daysRemaining == 0) {
                dateTextView.setText("Today!!!");
                dateTextView.setTextColor(Color.MAGENTA);
                dateLabelTextView.setTextColor(Color.MAGENTA);
            } else if (daysRemaining < 0) {
                dateTextView.setText(Math.abs(daysRemaining) + " days ago");
                dateTextView.setTextColor(Color.LTGRAY);
                dateLabelTextView.setTextColor(Color.LTGRAY);
            } else {
                dateTextView.setText("in " + event.getDaysRemaining() + " day(s)");
            }
        }
    }

    /* OnClick Interface */
    public interface OnItemClickListener {
        void onItemClick(Event event);
    }

    public void setOnClickListener(EventsAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}
