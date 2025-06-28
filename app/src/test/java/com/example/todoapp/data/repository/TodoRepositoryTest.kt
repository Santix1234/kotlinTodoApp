package com.example.todoapp.data.repository

import com.example.todoapp.data.local.TodoDao
import com.example.todoapp.data.model.Todo
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class TodoRepositoryTest {

    @Mock
    private lateinit var mockTodoDao: TodoDao

    private lateinit var todoRepository: TodoRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        todoRepository = TodoRepository(mockTodoDao)
    }

    @Test
    fun `test getAllTodos returns flow of todos`() = runTest {
        val mockTodos = listOf(
            Todo(1, "Test Todo 1", false),
            Todo(2, "Test Todo 2", true)
        )
        `when`(mockTodoDao.getAllTodos()).thenReturn(flowOf(mockTodos))

        val result = todoRepository.getAllTodos().first()
        assertEquals(2, result.size)
        assertEquals("Test Todo 1", result[0].title)
    }

    @Test
    fun `test insertTodo with valid todo`() = runTest {
        val todo = Todo(title = "New Todo", isCompleted = false)
        todoRepository.insertTodo(todo)
        verify(mockTodoDao).insert(todo)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `test insertTodo with empty title throws exception`() = runTest {
        val todo = Todo(title = "", isCompleted = false)
        todoRepository.insertTodo(todo)
    }

    @Test
    fun `test updateTodo with valid todo`() = runTest {
        val todo = Todo(id = 1, title = "Updated Todo", isCompleted = true)
        todoRepository.updateTodo(todo)
        verify(mockTodoDao).update(todo)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `test updateTodo with invalid ID throws exception`() = runTest {
        val todo = Todo(id = 0, title = "Invalid Todo", isCompleted = false)
        todoRepository.updateTodo(todo)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `test updateTodo with empty title throws exception`() = runTest {
        val todo = Todo(id = 1, title = "", isCompleted = false)
        todoRepository.updateTodo(todo)
    }

    @Test
    fun `test deleteTodo`() = runTest {
        val todo = Todo(id = 1, title = "Todo to Delete", isCompleted = false)
        todoRepository.deleteTodo(todo)
        verify(mockTodoDao).delete(todo)
    }

    @Test
    fun `test getTodoById returns correct todo`() = runTest {
        val todo = Todo(id = 1, title = "Specific Todo", isCompleted = false)
        `when`(mockTodoDao.getTodoById(1)).thenReturn(todo)

        val result = todoRepository.getTodoById(1)
        assertNotNull(result)
        assertEquals("Specific Todo", result?.title)
    }
}