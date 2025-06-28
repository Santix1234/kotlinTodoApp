package com.todoapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a todo item in the application.
 *
 * @property id Unique identifier for the todo item
 * @property title Title or description of the todo item
 * @property isCompleted Indicates whether the todo item is completed
 * @property createdAt Timestamp of when the todo item was created
 * @property updatedAt Timestamp of when the todo item was last updated
 */
@Entity(tableName = "todo_items")
data class TodoItem(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val isCompleted: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
) {
    /**
     * Validates the todo item's properties.
     *
     * @throws IllegalArgumentException if the title is invalid
     */
    init {
        require(title.isNotBlank()) { "Todo item title cannot be blank" }
        require(title.length <= 200) { "Todo item title cannot exceed 200 characters" }
    }

    /**
     * Creates a copy of the todo item with updated completion status.
     *
     * @param completed New completion status
     * @return A new TodoItem with updated completion status and updatedAt timestamp
     */
    fun updateCompletionStatus(completed: Boolean): TodoItem {
        return copy(
            isCompleted = completed,
            updatedAt = System.currentTimeMillis()
        )
    }
}