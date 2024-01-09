package pl.latusikl.demo.app.library.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.core.convert.ConversionService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import pl.latusikl.demo.app.library.dto.BaseDto;
import pl.latusikl.demo.app.library.entity.BaseEntity;
import pl.latusikl.demo.app.exception.InternalServerErrorException;

public abstract class AbstractEntityService<ENTITY extends BaseEntity, DTO extends BaseDto> implements CRUDService<ENTITY, DTO>
{
	private final JpaRepository<ENTITY, UUID> repository;
	protected final ConversionService conversionService;
	private final Class<DTO> dtoClass;
	private final Class<ENTITY> entityClass;

	protected AbstractEntityService(JpaRepository<ENTITY, UUID> repository, ConversionService conversionService,
	                                Class<DTO> dtoClass, Class<ENTITY> entityClass)
	{
		this.repository = repository;
		this.conversionService = conversionService;
		this.dtoClass = dtoClass;
		this.entityClass = entityClass;
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<DTO> getEntity(UUID id)
	{
		return repository.findById(id)
		                 .map(entity -> conversionService.convert(entity, getDtoClass()));
	}

	@Transactional(readOnly = true)
	@Override
	public List<DTO> getAll()
	{
		return repository.findAll()
		                 .stream()
		                 .map(entity -> conversionService.convert(entity, getDtoClass()))
		                 .toList();
	}

	@Transactional
	@Override
	public UUID create(final DTO dto)
	{
		return saveEntity(dto).getId();
	}

	protected ENTITY saveEntity(final DTO dto)
	{
		ENTITY entity = conversionService.convert(dto, getEntityClass());
		if (entity != null)
		{
			entity = adjustBeforeItemCreation(entity);
			return repository.save(entity);
		}
		else
		{
			throw new InternalServerErrorException("Unable to create instance of the object");
		}
	}

	protected ENTITY adjustBeforeItemCreation(ENTITY entity)
	{
		return entity;
	}

	@Transactional
	@Override
	public void remove(final UUID id)
	{
		removeEntity(id);
	}

	protected void removeEntity(final UUID id)
	{
		if (repository.existsById(id))
		{
			repository.deleteById(id);
		}
	}

	@Override
	public boolean existById(final UUID id)
	{
		return repository.existsById(id);
	}

	@Override
	public Class<ENTITY> getEntityClass()
	{
		return this.entityClass;
	}

	@Override
	public Class<DTO> getDtoClass()
	{
		return this.dtoClass;
	}
}
