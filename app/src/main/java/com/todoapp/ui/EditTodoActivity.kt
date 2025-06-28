package com.todoapp.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.todoapp.R
import com.todoapp.data.Todo
import com.todoapp.viewmodel.TodoViewModel
import kotlinx.coroutines.launch

class EditTodoActivity : AppCompatActivity() {

    private lateinit var todoViewModel: TodoViewModel
    private lateinit var titleEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var completedSwitch: Switch
    private lateinit var saveButton: Button
    private lateinit var cancelButton: Button

    private var todoId: Long = -1

    companion object {
        const val EXTRA_TODO_ID = "extra_todo_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_todo)

        // Initialize ViewModel
        todoViewModel = ViewModelProvider(this)[TodoViewModel::class.java]

        // Initialize views
        titleEditText = findViewById(R.id.edit_todo_title)
        descriptionEditText = findViewById(R.id.edit_todo_description)
        completedSwitch = findViewById(R.id.edit_todo_completed_switch)
        saveButton = findViewById(R.id.save_todo_button)
        cancelButton = findViewById(R.id.cancel_todo_button)

        // Get todo item ID from intent
        todoId = intent.getLongExtra(EXTRA_TODO_ID, -1)

        // Load existing todo item if editing
        if (todoId != -1L) {
            loadTodoItem()
        }

        // Save button click listener
        saveButton.setOnClickListener {
            saveTodoItem()
        }

        // Cancel button click listener
        cancelButton.setOnClickListener {
            finish()
        }
    }

    private fun loadTodoItem() {
        lifecycleScope.launch {
            val todo = todoViewModel.getTodoById(todoId)
            todo?.let {
                titleEditText.setText(it.title)
                descriptionEditText.setText(it.description)
                completedSwitch.isChecked = it.isCompleted
            }
        }
    }

    private fun saveTodoItem() {
        val title = titleEditText.text.toString().trim()
        val description = descriptionEditText.text.toString().trim()
        val isCompleted = completedSwitch.isChecked

        // Validate input
        if (title.isEmpty()) {
            titleEditText.error = "Title cannot be empty"
            return
        }

        lifecycleScope.launch {
            try {
                // Create or update todo item
                val todo = Todo(
                    id = if (todoId == -1L) 0 else todoId,
                    title = title,
                    description = description,
                    isCompleted = isCompleted
                )

                if (todoId == -1L) {
                    todoViewModel.insertTodo(todo)
                    Toast.makeText(this@EditTodoActivity, "Todo created", Toast.LENGTH_SHORT).show()
                } else {
                    todoViewModel.updateTodo(todo)
                    Toast.makeText(this@EditTodoActivity, "Todo updated", Toast.LENGTH_SHORT).show()
                }

                // Close the activity
                finish()
            } catch (e: Exception) {
                Toast.makeText(this@EditTodoActivity, "Error saving todo", Toast.LENGTH_SHORT).show()
            }
        }
    }
}