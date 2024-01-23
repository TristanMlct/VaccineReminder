package com.tristanmlct.vaccinereminder.ui.home

import androidx.lifecycle.ViewModel
import com.tristanmlct.vaccinereminder.data.Entity


/**
 * ViewModel to retrieve all items in the Room database.
 */
class HomeViewModel : ViewModel() {
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

/**
 * Ui State for HomeScreen
 */
data class HomeUiState(val entityList: List<Entity> = listOf())
