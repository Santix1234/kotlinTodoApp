package com.todoapp.model

data class Todo(
    val id: Long = 0,
    val title: String,
    val description: String? = null,
    val isCompleted: Boolean = false
)