package com.example.todoapp

import android.graphics.Paint
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import com.example.todoapp.data.Todo
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class TodoAdapterTest {

    private lateinit var adapter: TodoAdapter
    private lateinit var todos: MutableList<Todo>

    @Before
    fun setup() {
        todos = mutableListOf(
            Todo(1, "Test Todo 1", false),
            Todo(2, "Test Todo 2", true)
        )
        adapter = TodoAdapter(todos) { todo ->
            todo.isCompleted = !todo.isCompleted
        }
    }

    @Test
    fun `completed todo items have strikethrough text`() {
        // Create a view holder for the completed todo
        val view = View.inflate(RuntimeEnvironment.getApplication(), R.layout.item_todo, null)
        val viewHolder = adapter.TodoViewHolder(view)
        
        // Bind the completed todo
        val completedTodo = todos[1]
        viewHolder.bind(completedTodo)

        // Find the text view
        val titleTextView = view.findViewById<TextView>(R.id.todoTitleTextView)
        
        // Check strikethrough is applied
        assertTrue((titleTextView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG) == Paint.STRIKE_THRU_TEXT_FLAG)
        
        // Check opacity is reduced
        assertEquals(0.5f, titleTextView.alpha, 0.01f)
    }

    @Test
    fun `incomplete todo items do not have strikethrough`() {
        // Create a view holder for the incomplete todo
        val view = View.inflate(RuntimeEnvironment.getApplication(), R.layout.item_todo, null)
        val viewHolder = adapter.TodoViewHolder(view)
        
        // Bind the incomplete todo
        val incompleteTodo = todos[0]
        viewHolder.bind(incompleteTodo)

        // Find the text view
        val titleTextView = view.findViewById<TextView>(R.id.todoTitleTextView)
        
        // Check strikethrough is not applied
        assertFalse((titleTextView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG) == Paint.STRIKE_THRU_TEXT_FLAG)
        
        // Check opacity is full
        assertEquals(1.0f, titleTextView.alpha, 0.01f)
    }

    @Test
    fun `checkbox state reflects todo completion status`() {
        // Create a view holder for both completed and incomplete todos
        val completedView = View.inflate(RuntimeEnvironment.getApplication(), R.layout.item_todo, null)
        val incompletedView = View.inflate(RuntimeEnvironment.getApplication(), R.layout.item_todo, null)
        
        val completedViewHolder = adapter.TodoViewHolder(completedView)
        val incompleteViewHolder = adapter.TodoViewHolder(incompletedView)
        
        // Bind todos
        completedViewHolder.bind(todos[1])
        incompleteViewHolder.bind(todos[0])
        
        // Find checkboxes
        val completedCheckbox = completedView.findViewById<CheckBox>(R.id.todoCompletedCheckbox)
        val incompleteCheckbox = incompletedView.findViewById<CheckBox>(R.id.todoCompletedCheckbox)
        
        // Check checkbox states
        assertTrue(completedCheckbox.isChecked)
        assertFalse(incompleteCheckbox.isChecked)
    }
}