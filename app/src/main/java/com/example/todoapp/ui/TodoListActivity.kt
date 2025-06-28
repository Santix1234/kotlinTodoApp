package com.example.todoapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.data.Todo
import com.example.todoapp.viewmodel.TodoListViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TodoListActivity : AppCompatActivity(), TodoAdapter.TodoItemClickListener {

    private val viewModel: TodoListViewModel by viewModels()
    private lateinit var todoAdapter: TodoAdapter

    companion object {
        const val EDIT_TODO_REQUEST_CODE = 1
        const val TODO_ITEM_EXTRA = "todo_item_extra"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_list)

        setupRecyclerView()
        setupAddButton()
        observeTodos()
    }

    private fun setupRecyclerView() {
        val recyclerView: RecyclerView = findViewById(R.id.todoRecyclerView)
        todoAdapter = TodoAdapter(emptyList(), this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = todoAdapter
    }

    private fun setupAddButton() {
        val addButton: FloatingActionButton = findViewById(R.id.addTodoButton)
        addButton.setOnClickListener {
            navigateToEditTodo(null)
        }
    }

    private fun observeTodos() {
        viewModel.todos.observe(this) { todos ->
            todoAdapter.updateTodos(todos)
        }
    }

    override fun onTodoItemClicked(todo: Todo) {
        navigateToEditTodo(todo)
    }

    private fun navigateToEditTodo(todo: Todo?) {
        val intent = Intent(this, TodoEditActivity::class.java).apply {
            // Passing the todo item to edit, or null for creating a new todo
            putExtra(TODO_ITEM_EXTRA, todo)
        }
        startActivityForResult(intent, EDIT_TODO_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == EDIT_TODO_REQUEST_CODE && resultCode == RESULT_OK) {
            // Refresh todos or handle the result if needed
            viewModel.refreshTodos()
        }
    }
}