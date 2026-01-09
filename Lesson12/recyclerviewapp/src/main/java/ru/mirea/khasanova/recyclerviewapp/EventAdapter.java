package ru.mirea.khasanova.recyclerviewapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private final List<HistorycalEvent> events;

    public EventAdapter(List<HistorycalEvent> events) {
        this.events = events;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_event, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        HistorycalEvent event = events.get(position);
        holder.titleTextView.setText(event.getTitle());
        holder.descriptionTextView.setText(event.getDescription());
        holder.eventImageView.setImageResource(event.getImageResId());
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        ImageView eventImageView;
        TextView titleTextView;
        TextView descriptionTextView;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            eventImageView = itemView.findViewById(R.id.eventImageView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
        }
    }
}