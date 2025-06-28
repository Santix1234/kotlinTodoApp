package com.todoapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.todoapp.data.entity.TodoItem
import com.todoapp.data.repository.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val todoRepository: TodoRepository
) : ViewModel() {

    private val _todoItems = MutableStateFlow<List<TodoItem>>(emptyList())
    val todoItems: StateFlow<List<TodoItem>> = _todoItems.asStateFlow()

    init {
        fetchTodoItems()
    }

    private fun fetchTodoItems() {
        viewModelScope.launch {
            todoRepository.getAllTodoItems().collect { items ->
                _todoItems.value = items
            }
        }
    }

    fun toggleTodoItemCompletionStatus(todoItem: TodoItem) {
        viewModelScope.launch {
            val updatedItem = todoRepository.toggleTodoItemCompletionStatus(todoItem)
            // Automatically updates the list due to Flow
        }
    }

    fun addTodoItem(todoItem: TodoItem) {
        viewModelScope.launch {
            todoRepository.insertTodoItem(todoItem)
        }
    }

    fun deleteTodoItem(todoItem: TodoItem) {
        viewModelScope.launch {
            todoRepository.deleteTodoItem(todoItem.id)
        }
    }
}