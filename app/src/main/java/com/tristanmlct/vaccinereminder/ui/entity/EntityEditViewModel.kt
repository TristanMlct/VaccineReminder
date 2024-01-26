package com.tristanmlct.vaccinereminder.ui.entity

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tristanmlct.vaccinereminder.data.EntitiesRepository
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class EntityEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val entitiesRepository: EntitiesRepository
) : ViewModel() {

    /**
     * Holds current entity ui state
     */
    var entityUiState by mutableStateOf(EntityUiState())
        private set

    private val entityId: Int = checkNotNull(savedStateHandle[EntityEditDestination.entityIdArg])

    init {
        viewModelScope.launch {
            entityUiState = entitiesRepository.getEntityStream(entityId)
                .filterNotNull()
                .first()
                .toEntityUiState(true)
        }
    }

    suspend fun updateEntity() {
        if (validateInput(entityUiState.entityDetails)) {
            entitiesRepository.updateEntity(entityUiState.entityDetails.toEntity())
        }
    }

    fun updateUiState(entityDetails: EntityDetails) {
        entityUiState = EntityUiState(entityDetails = entityDetails, isEntryValid = validateInput(entityDetails))
    }

    private fun validateInput(uiState: EntityDetails = entityUiState.entityDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && vaccineName.isNotBlank() && date.isNotBlank()
        }
    }
}
