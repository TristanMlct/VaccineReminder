package com.tristanmlct.vaccinereminder.ui

import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tristanmlct.vaccinereminder.ui.entity.EntityDetailsViewModel
import com.tristanmlct.vaccinereminder.ui.entity.EntityEntryViewModel
import com.tristanmlct.vaccinereminder.ui.home.HomeViewModel


/**
 * Provides Factory to create instance of ViewModel for the entire Inventory app
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel()
        }

        initializer {
            EntityEntryViewModel()
        }

        initializer {
            EntityDetailsViewModel(
                this.createSavedStateHandle()
            )
        }
    }
}