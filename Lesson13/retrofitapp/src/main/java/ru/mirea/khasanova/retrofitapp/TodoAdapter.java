package ru.mirea.khasanova.retrofitapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {
    private List<Todo> todos;
    private Context context;
    private OnTodoStatusChangeListener listener;

    public interface OnTodoStatusChangeListener {
        void onStatusChanged(Todo todo);
    }

    public TodoAdapter(List<Todo> todos, Context context, OnTodoStatusChangeListener listener) {
        this.todos = todos;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todo, parent, false);
        return new TodoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        Todo todo = todos.get(position);
        holder.textViewTitle.setText(todo.getTodo());
        holder.checkBoxCompleted.setChecked(todo.getCompleted());

        String imageUrl = "https://api.dicebear.com/7.x/identicon/png?seed=" + todo.getId();
        Picasso.get()
                .load(imageUrl)
                .placeholder(android.R.drawable.ic_menu_rotate)
                .error(android.R.drawable.ic_delete)
                .into(holder.imageViewTodo);

        holder.checkBoxCompleted.setOnClickListener(v -> {
            todo.setCompleted(holder.checkBoxCompleted.isChecked());
            listener.onStatusChanged(todo);
        });
    }

    @Override
    public int getItemCount() { return todos.size(); }

    public static class TodoViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        CheckBox checkBoxCompleted;
        ImageView imageViewTodo;

        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            checkBoxCompleted = itemView.findViewById(R.id.checkBoxCompleted);
            imageViewTodo = itemView.findViewById(R.id.imageViewTodo);
        }
    }
}