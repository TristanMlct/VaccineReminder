package com.tristanmlct.vaccinereminder.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tristanmlct.vaccinereminder.data.EntitiesRepository
import com.tristanmlct.vaccinereminder.data.Entity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn


/**
 * ViewModel to retrieve all items in the Room database.
 */
class HomeViewModel(entitiesRepository: EntitiesRepository) : ViewModel() {

    val HomeUiState: StateFlow<HomeUiState> = entitiesRepository.getAllEntitiesStream()
        .map {
            HomeUiState(it)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = HomeUiState()
        )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

/**
 * Ui State for HomeScreen
 */
data class HomeUiState(val entityList: List<Entity> = listOf())
