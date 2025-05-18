package ru.mirea.event.add_event.presentation

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.mirea.core.util.BaseViewModel
import ru.mirea.event.add_event.domain.AddEventRepository
import javax.inject.Inject

@HiltViewModel
class AddEventViewModel @Inject constructor(
    private val repository: AddEventRepository,
) :
    BaseViewModel<AddEventState, AddEventEvent, AddEventEffect>(AddEventState()) {

    init {
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            repository.getEventCategories()
                .onSuccess { categories ->
//                    updateState { it.copy(categories = categories) }
                }
            // onFailure можно добавить обработку ошибок
        }
    }

    override fun event(event: AddEventEvent) {
        when (event) {
            is AddEventEvent.NameChanged -> {
                updateState { it.copy(name = event.name) }
            }

            is AddEventEvent.DescriptionChanged -> {
                updateState { it.copy(description = event.description) }
            }

            is AddEventEvent.CategoryChanged -> {
                updateState { it.copy(categoryId = event.categoryId) }
            }

            is AddEventEvent.UpdateMembers -> {
                updateState { it.copy(members = event.members) }
            }
        }
    }
} 