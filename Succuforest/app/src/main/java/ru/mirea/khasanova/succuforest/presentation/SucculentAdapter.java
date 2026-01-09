package ru.mirea.khasanova.succuforest.presentation;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import ru.mirea.khasanova.succuforest.R;
import ru.mirea.khasanova.succuforest.domain.models.Succulent;

public class SucculentAdapter extends RecyclerView.Adapter<SucculentAdapter.SuccuViewHolder> {

    private final List<Succulent> succulentList;
    private final Context context;

    public SucculentAdapter(List<Succulent> succulentList, Context context) {
        this.succulentList = succulentList;
        this.context = context;
    }

    @NonNull
    @Override
    public SuccuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_succulent, parent, false);
        return new SuccuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SuccuViewHolder holder, int position) {
        Succulent succulent = succulentList.get(position);

        holder.tvName.setText(succulent.getName());
        holder.tvPrice.setText(succulent.getPrice());

        int resId = context.getResources().getIdentifier(
                succulent.getImageUrl(),
                "drawable",
                context.getPackageName()
        );

        Glide.with(context)
                .load(resId != 0 ? resId : android.R.drawable.ic_menu_gallery)
                .centerCrop()
                .into(holder.ivImage);
        holder.itemView.setOnClickListener(v -> {
            Toast.makeText(context, "Открываю: " + succulent.getName(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra("ID", succulent.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return succulentList != null ? succulentList.size() : 0;
    }

    public static class SuccuViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;
        TextView tvName, tvPrice;

        public SuccuViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
        }
    }
}