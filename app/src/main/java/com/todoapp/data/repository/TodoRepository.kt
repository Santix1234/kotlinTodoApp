package com.todoapp.data.repository

import com.todoapp.data.dao.TodoDao
import com.todoapp.data.entity.TodoItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TodoRepository @Inject constructor(
    private val todoDao: TodoDao
) {
    fun getAllTodoItems(): Flow<List<TodoItem>> = todoDao.getAllTodoItems()

    suspend fun insertTodoItem(todoItem: TodoItem): Long = todoDao.insertTodoItem(todoItem)

    suspend fun updateTodoItem(todoItem: TodoItem) = todoDao.updateTodoItem(todoItem)

    suspend fun toggleTodoItemCompletionStatus(todoItem: TodoItem): TodoItem {
        val updatedItem = todoItem.toggleCompletion()
        todoDao.updateTodoItemCompletionStatus(updatedItem.id, updatedItem.isCompleted)
        return updatedItem
    }

    suspend fun deleteTodoItem(itemId: Long) = todoDao.deleteTodoItem(itemId)
}