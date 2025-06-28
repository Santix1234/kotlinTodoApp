package com.todoapp.model

import org.junit.Test
import org.junit.Assert.*

class TodoItemTest {

    @Test
    fun `create todo item with default values`() {
        val todoItem = TodoItem(title = "Test Todo")
        
        assertFalse(todoItem.isCompleted)
        assertEquals("Test Todo", todoItem.title)
        assertTrue(todoItem.createdAt > 0)
        assertTrue(todoItem.updatedAt > 0)
    }

    @Test
    fun `update completion status`() {
        val todoItem = TodoItem(title = "Test Todo")
        val updatedItem = todoItem.updateCompletionStatus(true)
        
        assertTrue(updatedItem.isCompleted)
        assertNotEquals(todoItem.updatedAt, updatedItem.updatedAt)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `fail when creating todo item with blank title`() {
        TodoItem(title = "")
    }

    @Test(expected = IllegalArgumentException::class)
    fun `fail when creating todo item with title exceeding max length`() {
        TodoItem(title = "a".repeat(201))
    }

    @Test
    fun `todo item data class properties`() {
        val todoItem1 = TodoItem(title = "Task 1")
        val todoItem2 = TodoItem(title = "Task 1")
        val todoItem3 = todoItem1.copy(id = 1)

        // Test equals and hashCode
        assertEquals(todoItem1, todoItem2)
        assertNotEquals(todoItem1, todoItem3)
        assertEquals(todoItem1.hashCode(), todoItem2.hashCode())
    }
}