package com.tristanmlct.vaccinereminder.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tristanmlct.vaccinereminder.VaccineReminderApplication
import com.tristanmlct.vaccinereminder.ui.entity.EntityDetailsViewModel
import com.tristanmlct.vaccinereminder.ui.entity.EntityEditViewModel
import com.tristanmlct.vaccinereminder.ui.entity.EntityEntryViewModel
import com.tristanmlct.vaccinereminder.ui.home.HomeViewModel


/**
 * Provides Factory to create instance of ViewModel for the entire Vaccine Reminder app
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(vaccineReminderApplication().container.entitiesRepository)
        }

        initializer {
            EntityEntryViewModel(vaccineReminderApplication().container.entitiesRepository)
        }

        initializer {
            EntityDetailsViewModel(
                this.createSavedStateHandle(),
                vaccineReminderApplication().container.entitiesRepository
            )
        }

        initializer {
            EntityEditViewModel(
                this.createSavedStateHandle(),
                vaccineReminderApplication().container.entitiesRepository
            )
        }
    }
}

fun CreationExtras.vaccineReminderApplication(): VaccineReminderApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as VaccineReminderApplication)