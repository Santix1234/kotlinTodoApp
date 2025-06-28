package com.example.todoapp

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.data.Todo

class TodoAdapter(
    private val todos: List<Todo>,
    private val onTodoToggled: (Todo) -> Unit
) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    inner class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.todoTitleTextView)
        private val completedCheckbox: CheckBox = itemView.findViewById(R.id.todoCompletedCheckbox)

        fun bind(todo: Todo) {
            // Set title
            titleTextView.text = todo.title

            // Apply completed styling
            if (todo.isCompleted) {
                // Strikethrough text
                titleTextView.paintFlags = titleTextView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                
                // Reduce opacity for completed items
                titleTextView.alpha = 0.5f
            } else {
                // Remove strikethrough for incomplete items
                titleTextView.paintFlags = titleTextView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                
                // Reset opacity
                titleTextView.alpha = 1.0f
            }

            // Set checkbox state
            completedCheckbox.isChecked = todo.isCompleted

            // Toggle listener
            completedCheckbox.setOnClickListener {
                onTodoToggled(todo)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_todo, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(todos[position])
    }

    override fun getItemCount() = todos.size
}