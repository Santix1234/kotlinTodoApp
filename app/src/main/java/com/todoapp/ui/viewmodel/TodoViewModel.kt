package com.todoapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.todoapp.data.model.Todo
import com.todoapp.data.repository.TodoRepository
import com.todoapp.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val repository: TodoRepository
) : ViewModel() {

    private val _editStatus = MutableStateFlow<EditStatus>(EditStatus.Idle)
    val editStatus: StateFlow<EditStatus> = _editStatus.asStateFlow()

    fun updateTodo(todo: Todo) {
        viewModelScope.launch {
            try {
                // Validate todo item before updating
                if (!validateTodo(todo)) {
                    _editStatus.value = EditStatus.Error("Invalid todo item")
                    return@launch
                }

                val result = repository.updateTodo(todo)
                when (result) {
                    is Result.Success -> {
                        _editStatus.value = EditStatus.Success("Todo updated successfully")
                    }
                    is Result.Error -> {
                        _editStatus.value = EditStatus.Error(result.message ?: "Failed to update todo")
                    }
                }
            } catch (e: Exception) {
                _editStatus.value = EditStatus.Error("Unexpected error: ${e.localizedMessage}")
            }
        }
    }

    private fun validateTodo(todo: Todo): Boolean {
        return todo.title.isNotBlank() && todo.title.length <= 100
    }

    // Reset edit status to idle after handling
    fun resetEditStatus() {
        _editStatus.value = EditStatus.Idle
    }
}

// Sealed class to represent different states of edit operation
sealed class EditStatus {
    object Idle : EditStatus()
    data class Success(val message: String) : EditStatus()
    data class Error(val message: String) : EditStatus()
}