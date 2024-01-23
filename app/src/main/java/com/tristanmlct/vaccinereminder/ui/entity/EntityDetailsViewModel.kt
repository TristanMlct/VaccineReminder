package com.tristanmlct.vaccinereminder.ui.entity

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class EntityDetailsViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    // private val entityId: Int = checkNotNull(savedStateHandle)

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class EntityDetailsUiState(
    val outOfStock: Boolean = true,
    val entityDetails: EntityDetails = EntityDetails()
)