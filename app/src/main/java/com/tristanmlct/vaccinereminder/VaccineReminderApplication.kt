package com.tristanmlct.vaccinereminder

import android.app.Application
import com.tristanmlct.vaccinereminder.data.AppContainer
import com.tristanmlct.vaccinereminder.data.AppDataContainer

class VaccineReminderApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}