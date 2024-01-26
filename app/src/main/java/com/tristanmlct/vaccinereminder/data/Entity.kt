package com.tristanmlct.vaccinereminder.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "entities")
data class Entity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val vaccineName: String,
    val date: String
)
