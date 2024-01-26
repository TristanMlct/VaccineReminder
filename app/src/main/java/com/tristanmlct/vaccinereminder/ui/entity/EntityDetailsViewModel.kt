package com.tristanmlct.vaccinereminder.ui.entity

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tristanmlct.vaccinereminder.data.EntitiesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class EntityDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val entitiesRepository: EntitiesRepository
) : ViewModel() {
     private val entityId: Int = checkNotNull(savedStateHandle[EntityDetailsDestination.entityIdArg])

    val uiState: StateFlow<EntityDetailsUiState> =
        entitiesRepository.getEntityStream(entityId)
            .filterNotNull()
            .map {
                EntityDetailsUiState(entityDetails = it.toEntityDetails())
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = EntityDetailsUiState()
            )

    suspend fun deleteEntity() {
        entitiesRepository.deleteEntity(uiState.value.entityDetails.toEntity())
    }
            
            
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class EntityDetailsUiState(
    val entityDetails: EntityDetails = EntityDetails()
)