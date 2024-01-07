package pl.latusikl.demo.nativeimage.library.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import pl.latusikl.demo.nativeimage.library.dto.BaseDto;
import pl.latusikl.demo.nativeimage.library.entity.BaseEntity;

public interface CRUDService<ENTITY extends BaseEntity,DTO extends BaseDto>
{
	Optional<DTO> getEntity(UUID id);

	List<DTO> getAll();

	UUID create(final DTO entity);

	void remove(final UUID id);

	boolean existById(final UUID id);

	Class<ENTITY> getEntityClass();
	Class<DTO> getDtoClass();

}
