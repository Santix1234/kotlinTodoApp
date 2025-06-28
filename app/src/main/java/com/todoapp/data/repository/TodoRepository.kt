package com.todoapp.data.repository

import com.todoapp.data.local.TodoDao
import com.todoapp.data.model.Todo
import com.todoapp.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TodoRepository @Inject constructor(
    private val todoDao: TodoDao
) {
    suspend fun updateTodo(todo: Todo): Result<Unit> = withContext(Dispatchers.IO) {
        return@withContext try {
            // Perform update operation
            todoDao.update(todo)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.error("Failed to update todo: ${e.localizedMessage}", e)
        }
    }
}