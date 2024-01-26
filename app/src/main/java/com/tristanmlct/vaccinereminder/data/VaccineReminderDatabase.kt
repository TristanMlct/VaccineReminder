package com.tristanmlct.vaccinereminder.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Entity::class], version = 2, exportSchema = false)
abstract class VaccineReminderDatabase : RoomDatabase() {
    abstract fun entityDao(): EntityDao

    companion object {
        @Volatile
        private var Instance: VaccineReminderDatabase? = null

        fun getDatabase(context: Context): VaccineReminderDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, VaccineReminderDatabase::class.java, "entity_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }

        }
    }
}