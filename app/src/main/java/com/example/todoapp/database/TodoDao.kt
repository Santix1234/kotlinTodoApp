package com.example.todoapp.database

import androidx.room.*
import com.example.todoapp.data.TodoItem
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Query("SELECT * FROM todo_items")
    fun getAllTodos(): Flow<List<TodoItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todoItem: TodoItem): Long

    @Update
    suspend fun updateTodo(todoItem: TodoItem)

    @Delete
    suspend fun deleteTodo(todoItem: TodoItem)

    @Query("UPDATE todo_items SET isCompleted = :completed, updatedAt = :timestamp WHERE id = :todoId")
    suspend fun updateTodoCompletionStatus(todoId: Long, completed: Boolean, timestamp: Long = System.currentTimeMillis())
}