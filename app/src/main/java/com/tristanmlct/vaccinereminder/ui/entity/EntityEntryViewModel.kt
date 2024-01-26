package com.tristanmlct.vaccinereminder.ui.entity

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.tristanmlct.vaccinereminder.data.EntitiesRepository
import com.tristanmlct.vaccinereminder.data.Entity

class EntityEntryViewModel(private val entitiesRepository: EntitiesRepository) : ViewModel() {
    var entityUiState by mutableStateOf(EntityUiState())

    fun updateUiState(entityDetails: EntityDetails) {
        entityUiState =
            EntityUiState(entityDetails = entityDetails, isEntryValid = validateInput(entityDetails))
    }

    suspend fun saveEntity() {
        if (validateInput()) {
            entitiesRepository.insertEntity(entityUiState.entityDetails.toEntity())
        }
    }

    private fun validateInput(uiState: EntityDetails = entityUiState.entityDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && vaccineName.isNotBlank() && date.isNotBlank()
        }
    }
}

data class EntityUiState(
    val entityDetails: EntityDetails = EntityDetails(),
    var isEntryValid: Boolean = false
)

data class EntityDetails(
    val id: Int = 0,
    val name: String = "",
    val vaccineName: String = "",
    val date: String = ""
)

fun EntityDetails.toEntity(): Entity = Entity(
    id = id,
    name = name,
    vaccineName = vaccineName,
    date = date
)

fun Entity.toEntityUiState(isEntryValid: Boolean = false): EntityUiState = EntityUiState(
        entityDetails = this.toEntityDetails(),
        isEntryValid = isEntryValid
    )

fun Entity.toEntityDetails(): EntityDetails = EntityDetails(
    id = id,
    name = name,
    vaccineName = vaccineName,
    date = date
)