package com.example.todoapp.repository

import com.example.todoapp.data.TodoItem
import com.example.todoapp.database.TodoDao
import kotlinx.coroutines.flow.Flow

class TodoRepository(private val todoDao: TodoDao) {
    val allTodos: Flow<List<TodoItem>> = todoDao.getAllTodos()

    suspend fun insert(todoItem: TodoItem) = todoDao.insertTodo(todoItem)

    suspend fun update(todoItem: TodoItem) = todoDao.updateTodo(todoItem)

    suspend fun delete(todoItem: TodoItem) = todoDao.deleteTodo(todoItem)

    suspend fun updateCompletionStatus(todoId: Long, isCompleted: Boolean) {
        todoDao.updateTodoCompletionStatus(todoId, isCompleted)
    }
}