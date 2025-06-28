package com.todoapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.todoapp.R
import com.todoapp.model.Todo

class TodoListAdapter(
    private val onItemClick: (Todo) -> Unit,
    private val onCompletionToggle: (Todo, Boolean) -> Unit
) : ListAdapter<Todo, TodoListAdapter.TodoViewHolder>(TodoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_todo, parent, false)
        return TodoViewHolder(view, onItemClick, onCompletionToggle)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class TodoViewHolder(
        itemView: View,
        private val onItemClick: (Todo) -> Unit,
        private val onCompletionToggle: (Todo, Boolean) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.todo_title)
        private val completedCheckBox: CheckBox = itemView.findViewById(R.id.todo_completed_checkbox)

        fun bind(todo: Todo) {
            titleTextView.text = todo.title
            completedCheckBox.isChecked = todo.isCompleted

            itemView.setOnClickListener { onItemClick(todo) }

            completedCheckBox.setOnCheckedChangeListener { _, isChecked ->
                onCompletionToggle(todo, isChecked)
            }
        }
    }

    // DiffUtil for efficient list updates
    class TodoDiffCallback : DiffUtil.ItemCallback<Todo>() {
        override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem == newItem
        }
    }
}