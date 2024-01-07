package pl.latusikl.demo.nativeimage.library.service;

import java.util.UUID;

import org.springframework.core.convert.ConversionService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import pl.latusikl.demo.nativeimage.library.dto.ItemDto;
import pl.latusikl.demo.nativeimage.library.entity.Item;

@Service
public class ItemService extends AbstractEntityService<Item, ItemDto>
{
	protected ItemService(final JpaRepository<Item, UUID> repository,
	                      final ConversionService conversionService)
	{
		super(repository, conversionService, ItemDto.class, Item.class);
	}
}
