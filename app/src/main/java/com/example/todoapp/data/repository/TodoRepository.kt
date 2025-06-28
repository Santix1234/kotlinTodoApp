package com.example.todoapp.data.repository

import com.example.todoapp.data.local.TodoDao
import com.example.todoapp.data.model.Todo
import kotlinx.coroutines.flow.Flow

/**
 * Repository for managing Todo items, providing an abstraction layer over the data source.
 *
 * @property todoDao Data Access Object for performing database operations
 */
class TodoRepository(private val todoDao: TodoDao) {

    /**
     * Retrieves all todo items as a Flow of List
     * @return Flow of List<Todo>
     */
    fun getAllTodos(): Flow<List<Todo>> = todoDao.getAllTodos()

    /**
     * Inserts a new todo item into the database
     * @param todo The todo item to be inserted
     */
    suspend fun insertTodo(todo: Todo) {
        // Validate input before insertion
        if (todo.title.isBlank()) {
            throw IllegalArgumentException("Todo title cannot be empty")
        }
        todoDao.insert(todo)
    }

    /**
     * Updates an existing todo item in the database
     * @param todo The todo item to be updated
     * @throws IllegalArgumentException if the todo item has an invalid ID or empty title
     */
    suspend fun updateTodo(todo: Todo) {
        // Validate input before update
        if (todo.id <= 0) {
            throw IllegalArgumentException("Invalid todo ID")
        }
        if (todo.title.isBlank()) {
            throw IllegalArgumentException("Todo title cannot be empty")
        }
        todoDao.update(todo)
    }

    /**
     * Deletes a todo item from the database
     * @param todo The todo item to be deleted
     */
    suspend fun deleteTodo(todo: Todo) {
        todoDao.delete(todo)
    }

    /**
     * Retrieves a specific todo item by its ID
     * @param id The unique identifier of the todo item
     * @return Todo item or null if not found
     */
    suspend fun getTodoById(id: Long): Todo? {
        return todoDao.getTodoById(id)
    }
}