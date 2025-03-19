package com.midgetspinner31.p2pedu.db.provider

import com.midgetspinner31.p2pedu.exception.NotFoundException
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull

abstract class AbstractProvider<TEntity : Any, TRepository : JpaRepository<TEntity, TID>, TID : Any>(
    protected val repository: TRepository
) {
    protected open fun notFoundException(): RuntimeException {
        return NotFoundException()
    }

    fun getById(id: TID): TEntity {
        return repository.findByIdOrNull(id) ?: throw notFoundException()
    }

    fun findById(id: TID): TEntity? {
        return repository.findByIdOrNull(id)
    }

    fun findAll(): List<TEntity> {
        return repository.findAll()
    }

    fun existsById(id: TID): Boolean {
        return repository.existsById(id)
    }

    fun save(entity: TEntity): TEntity {
        return repository.save(entity)
    }

    fun saveAll(entities: Iterable<TEntity>): Iterable<TEntity> {
        return repository.saveAll(entities)
    }

    fun count(): Long {
        return repository.count()
    }

    fun delete(entity: TEntity) {
        return repository.delete(entity)
    }

    fun deleteAll(entities: Iterable<TEntity>) {
        return repository.deleteAll(entities)
    }

    fun deleteAll() {
        return repository.deleteAll()
    }
}
