package com.dk.mentoring.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.dk.mentoring.entity.Computer;


@Repository
@Transactional
public class ComputerDao
{
	@PersistenceContext
	protected EntityManager entityManager;
	private final Class<Computer> entityClass;

	public ComputerDao()
	{
		this(Computer.class);
	}

	protected ComputerDao(final Class<Computer> entityClass)
	{
		this.entityClass = entityClass;
	}

	public Computer getComputer(final Integer id)
	{
		return entityManager.find(getEntityClass(), id);
	}

	public Computer insert(final Computer entity)
	{
		entityManager.persist(entity);
		return entity;
	}

	public Computer update(Computer entity)
	{
		entity = entityManager.merge(entity);
		entityManager.flush();
		return entity;
	}

	public boolean delete(final Integer id)
	{
		if (entityManager.createQuery(String.format("delete from %s e where e.id = :id", entityClass.getSimpleName()))
				.setParameter("id", id).executeUpdate() != 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public EntityManager getEntityManager()
	{
		return entityManager;
	}

	public Class<Computer> getEntityClass()
	{
		return entityClass;
	}

}
