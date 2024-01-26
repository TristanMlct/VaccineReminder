package com.tristanmlct.vaccinereminder.data

import android.content.Context

interface AppContainer {
    val entitiesRepository: EntitiesRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val entitiesRepository: EntitiesRepository by lazy {
        OfflineEntitiesRepository(VaccineReminderDatabase.getDatabase(context).entityDao())
    }
}