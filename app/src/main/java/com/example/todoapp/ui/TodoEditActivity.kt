package com.example.todoapp.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.todoapp.R
import com.example.todoapp.data.Todo
import com.example.todoapp.viewmodel.TodoEditViewModel

class TodoEditActivity : AppCompatActivity() {

    private val viewModel: TodoEditViewModel by viewModels()
    private lateinit var titleEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_edit)

        initializeViews()
        handleIncomingTodo()
        setupSaveButton()
    }

    private fun initializeViews() {
        titleEditText = findViewById(R.id.todoTitleEditText)
        descriptionEditText = findViewById(R.id.todoDescriptionEditText)
        saveButton = findViewById(R.id.saveTodoButton)
    }

    private fun handleIncomingTodo() {
        val existingTodo = intent.getParcelableExtra<Todo>(TodoListActivity.TODO_ITEM_EXTRA)
        existingTodo?.let { todo ->
            titleEditText.setText(todo.title)
            descriptionEditText.setText(todo.description)
        }
    }

    private fun setupSaveButton() {
        saveButton.setOnClickListener {
            val title = titleEditText.text.toString().trim()
            val description = descriptionEditText.text.toString().trim()

            if (validateInput(title, description)) {
                val todo = intent.getParcelableExtra<Todo>(TodoListActivity.TODO_ITEM_EXTRA)
                if (todo != null) {
                    // Update existing todo
                    viewModel.updateTodo(todo.copy(title = title, description = description))
                } else {
                    // Create new todo
                    viewModel.addTodo(Todo(title = title, description = description))
                }
                setResult(RESULT_OK)
                finish()
            }
        }
    }

    private fun validateInput(title: String, description: String): Boolean {
        var isValid = true
        if (title.isEmpty()) {
            titleEditText.error = "Title cannot be empty"
            isValid = false
        }
        return isValid
    }
}