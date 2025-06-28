package com.todoapp.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.todoapp.data.model.Todo
import com.todoapp.data.repository.TodoRepository
import com.todoapp.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.Mockito.verify
import org.junit.Assert.*

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class TodoViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    @Mock
    private lateinit var mockRepository: TodoRepository

    private lateinit var viewModel: TodoViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = TodoViewModel(mockRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `updateTodo with valid todo sets success status`() = runTest {
        // Arrange
        val validTodo = Todo(id = 1, title = "Test Todo", isCompleted = false)
        `when`(mockRepository.updateTodo(validTodo)).thenReturn(Result.success(Unit))

        // Act
        viewModel.updateTodo(validTodo)
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        assertTrue(viewModel.editStatus.value is EditStatus.Success)
        assertEquals("Todo updated successfully", (viewModel.editStatus.value as EditStatus.Success).message)
    }

    @Test
    fun `updateTodo with empty title sets error status`() = runTest {
        // Arrange
        val invalidTodo = Todo(id = 1, title = "", isCompleted = false)

        // Act
        viewModel.updateTodo(invalidTodo)
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        assertTrue(viewModel.editStatus.value is EditStatus.Error)
        assertEquals("Invalid todo item", (viewModel.editStatus.value as EditStatus.Error).message)
    }

    @Test
    fun `updateTodo with repository error sets error status`() = runTest {
        // Arrange
        val validTodo = Todo(id = 1, title = "Test Todo", isCompleted = false)
        `when`(mockRepository.updateTodo(validTodo)).thenReturn(
            Result.error("Database error")
        )

        // Act
        viewModel.updateTodo(validTodo)
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        assertTrue(viewModel.editStatus.value is EditStatus.Error)
        assertEquals("Failed to update todo", (viewModel.editStatus.value as EditStatus.Error).message)
    }
}