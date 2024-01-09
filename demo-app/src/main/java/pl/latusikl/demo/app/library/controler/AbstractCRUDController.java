package pl.latusikl.demo.app.library.controler;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;
import pl.latusikl.demo.app.library.dto.BaseDto;
import pl.latusikl.demo.app.library.entity.BaseEntity;
import pl.latusikl.demo.app.library.service.AbstractEntityService;

public abstract class AbstractCRUDController<ENTITY extends BaseEntity, DTO extends BaseDto>
{
	protected final AbstractEntityService<ENTITY, DTO> entityService;

	protected AbstractCRUDController(AbstractEntityService<ENTITY, DTO> entityService)
	{
		this.entityService = entityService;
	}


	@GetMapping("/{id}")
	public ResponseEntity<DTO> getEntity(@Valid @org.hibernate.validator.constraints.UUID @PathVariable String id)
	{
		final UUID uuid = UUID.fromString(id);
		final Optional<DTO> entityOptional = entityService.getEntity(uuid);

		return entityOptional.map(ResponseEntity::ok)
		                     .orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping
	public ResponseEntity<List<DTO>> getEntities()
	{
		final List<DTO> allEntities = entityService.getAll();

		if (allEntities.isEmpty())
		{
			return ResponseEntity.notFound().build();
		}
		else
		{
			return ResponseEntity.ok(allEntities);
		}
	}

	@PostMapping
	public ResponseEntity<Void> createEntity(@RequestBody @Valid DTO dto)
	{
		throwValidationErrorBeforeCreationIfNeeded(dto);
		final UUID id = entityService.create(dto);
		return ResponseEntity.created(buildLocationHeaderValue(id)).build();
	}

	protected URI buildLocationHeaderValue(UUID id)
	{
		return ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(id.toString())
				.toUri();
	}

	protected void throwValidationErrorBeforeCreationIfNeeded(DTO dto)
	{
		//To be filled by super classes if required
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removeEntity(@PathVariable @Valid @org.hibernate.validator.constraints.UUID String id)
	{
		final UUID uuid = UUID.fromString(id);
		entityService.remove(uuid);
	}

}
