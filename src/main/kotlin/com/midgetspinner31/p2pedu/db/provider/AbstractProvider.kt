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

    open fun getById(id: TID): TEntity {
        return findById(id) ?: throw notFoundException()
    }

    open fun findById(id: TID): TEntity? {
        return repository.findByIdOrNull(id)
    }

    open fun findAllById(ids: Iterable<TID>): List<TEntity> {
        return repository.findAllById(ids)
    }

    open fun findAll(): List<TEntity> {
        return repository.findAll()
    }

    open fun existsById(id: TID): Boolean {
        return repository.existsById(id)
    }

    open fun save(entity: TEntity): TEntity {
        return repository.save(entity)
    }

    open fun saveAll(entities: Iterable<TEntity>): Iterable<TEntity> {
        return repository.saveAll(entities)
    }

    open fun count(): Long {
        return repository.count()
    }

    open fun delete(entity: TEntity) {
        return repository.delete(entity)
    }

    open fun deleteAll(entities: Iterable<TEntity>) {
        return repository.deleteAll(entities)
    }

    open fun deleteAll() {
        return repository.deleteAll()
    }

    open fun flush() {
        return repository.flush()
    }
}
