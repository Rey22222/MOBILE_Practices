package ru.mirea.khasanova.succuforest.presentation;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import ru.mirea.khasanova.succuforest.R;
import ru.mirea.khasanova.succuforest.domain.models.Succulent;

public class SucculentAdapter extends RecyclerView.Adapter<SucculentAdapter.VH> {

    private List<Succulent> list;
    private final Context context;

    public SucculentAdapter(List<Succulent> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void updateData(List<Succulent> newList) {
        this.list = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_succulent, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Succulent s = list.get(position);
        holder.name.setText(s.getName());
        holder.price.setText(s.getPrice());

        if (s.getImageUrl() != null && !s.getImageUrl().isEmpty()) {
            com.squareup.picasso.Picasso.get()
                    .load(s.getImageUrl())
                    .placeholder(android.R.drawable.ic_menu_gallery)
                    .error(android.R.drawable.stat_notify_error)
                    .fit()
                    .centerCrop()
                    .into(holder.img);
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra("ID", s.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView name, price;
        ImageView img;

        VH(View v) {
            super(v);
            name = v.findViewById(R.id.tvName);
            price = v.findViewById(R.id.tvPrice);
            img = v.findViewById(R.id.ivImage);
        }
    }
}