package com.todoapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_items")
data class TodoItem(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String? = null,
    val isCompleted: Boolean = false, // Add completion status
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
) {
    // Convenience method to toggle completion status
    fun toggleCompletion(): TodoItem {
        return this.copy(
            isCompleted = !this.isCompleted,
            updatedAt = System.currentTimeMillis()
        )
    }
}