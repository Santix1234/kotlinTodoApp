package com.todoapp.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.todoapp.data.dao.TodoDao
import com.todoapp.data.database.TodoDatabase
import com.todoapp.data.entity.TodoItem
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TodoRepositoryTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: TodoDatabase
    private lateinit var todoDao: TodoDao
    private lateinit var todoRepository: TodoRepository

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            TodoDatabase::class.java
        ).allowMainThreadQueries().build()

        todoDao = database.todoDao()
        todoRepository = TodoRepository(todoDao)
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun toggleTodoItemCompletionStatus_shouldUpdateStatusInDatabase() = runBlocking {
        // Arrange
        val todoItem = TodoItem(title = "Test Todo", isCompleted = false)
        val insertedId = todoDao.insertTodoItem(todoItem)
        val originalItem = todoDao.getAllTodoItems().first().first()

        // Act
        val updatedItem = todoRepository.toggleTodoItemCompletionStatus(originalItem)

        // Assert
        val retrievedItems = todoDao.getAllTodoItems().first()
        assertEquals(1, retrievedItems.size)
        assertTrue(retrievedItems[0].isCompleted)
        assertNotEquals(originalItem.isCompleted, retrievedItems[0].isCompleted)
    }

    @Test
    fun toggleTodoItemCompletionStatus_multipleTimes_shouldToggleCorrectly() = runBlocking {
        // Arrange
        val todoItem = TodoItem(title = "Test Todo", isCompleted = false)
        todoDao.insertTodoItem(todoItem)
        val originalItem = todoDao.getAllTodoItems().first().first()

        // Act & Assert
        val firstToggle = todoRepository.toggleTodoItemCompletionStatus(originalItem)
        assertTrue(firstToggle.isCompleted)

        val secondToggle = todoRepository.toggleTodoItemCompletionStatus(firstToggle)
        assertFalse(secondToggle.isCompleted)
    }
}