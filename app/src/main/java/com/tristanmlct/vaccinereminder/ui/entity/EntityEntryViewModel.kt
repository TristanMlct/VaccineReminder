package com.tristanmlct.vaccinereminder.ui.entity

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.tristanmlct.vaccinereminder.data.Entity

class EntityEntryViewModel : ViewModel() {
    var entityUiState by mutableStateOf(EntityUiState())

    fun updateUiState(entityDetails: EntityDetails) {
        entityUiState =
            EntityUiState(entityDetails = entityDetails, isEntryValid = validateInput(entityDetails))
    }

    private fun validateInput(uiState: EntityDetails = entityUiState.entityDetails): Boolean {
        return with(uiState) {
            name.isNotBlank()
        }
    }
}

data class EntityUiState(
    val entityDetails: EntityDetails = EntityDetails(),
    var isEntryValid: Boolean = false
)

data class EntityDetails(
    val id: Int = 0,
    val name: String = ""
)

fun EntityDetails.toEntity(): Entity = Entity(
    id = id,
    name = name
)