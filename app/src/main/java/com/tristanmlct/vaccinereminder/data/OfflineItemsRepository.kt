package com.tristanmlct.vaccinereminder.data

import kotlinx.coroutines.flow.Flow

class OfflineEntitiesRepository(private val entityDao: EntityDao) : EntitiesRepository {
    override fun getAllEntitiesStream(): Flow<List<Entity>> = entityDao.getAllEntities()

    override fun getEntityStream(id: Int): Flow<Entity?> = entityDao.getEntity(id)

    override suspend fun insertEntity(entity: Entity) = entityDao.insert(entity)

    override suspend fun deleteEntity(entity: Entity) = entityDao.delete(entity)

    override suspend fun updateEntity(entity: Entity) = entityDao.update(entity)
}