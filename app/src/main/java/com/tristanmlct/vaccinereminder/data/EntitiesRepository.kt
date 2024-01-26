package com.tristanmlct.vaccinereminder.data

import kotlinx.coroutines.flow.Flow

interface EntitiesRepository {
    fun getAllEntitiesStream(): Flow<List<Entity>>

    fun getEntityStream(id: Int): Flow<Entity?>

    suspend fun insertEntity(entity: Entity)

    suspend fun deleteEntity(entity: Entity)

    suspend fun updateEntity(entity: Entity)
}