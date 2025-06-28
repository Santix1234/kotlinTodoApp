package com.todoapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.todoapp.data.entity.TodoItem
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Query("SELECT * FROM todo_items")
    fun getAllTodoItems(): Flow<List<TodoItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodoItem(todoItem: TodoItem): Long

    @Update
    suspend fun updateTodoItem(todoItem: TodoItem)

    @Query("UPDATE todo_items SET isCompleted = :completed WHERE id = :itemId")
    suspend fun updateTodoItemCompletionStatus(itemId: Long, completed: Boolean)

    @Query("DELETE FROM todo_items WHERE id = :itemId")
    suspend fun deleteTodoItem(itemId: Long)
}