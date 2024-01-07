package pl.latusikl.demo.nativeimage.library.service;

import static java.util.Objects.requireNonNull;

import java.util.Optional;
import java.util.UUID;

import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import pl.latusikl.demo.nativeimage.library.dto.ItemStockDto;
import pl.latusikl.demo.nativeimage.library.entity.Item;
import pl.latusikl.demo.nativeimage.library.entity.ItemStock;
import pl.latusikl.demo.nativeimage.exception.BadRequestException;
import pl.latusikl.demo.nativeimage.library.service.repository.ItemRepository;
import pl.latusikl.demo.nativeimage.library.service.repository.ItemStockRepository;

@Service
public class ItemStockService extends AbstractEntityService<ItemStock, ItemStockDto>
{
	private final ItemRepository itemRepository;
	private final ItemStockRepository itemStockRepository;

	protected ItemStockService(final ItemStockRepository itemStockRepository,
	                           final ConversionService conversionService, ItemRepository itemRepository)
	{
		super(itemStockRepository, conversionService, ItemStockDto.class, ItemStock.class);
		this.itemRepository = itemRepository;
		this.itemStockRepository = itemStockRepository;
	}

	public boolean hasMatchingItem(ItemStockDto itemStockDto)
	{
		final var id = itemStockDto.getItemId();
		return itemRepository.existsById(UUID.fromString(id));
	}

	public boolean existForItem(ItemStockDto itemStockDto)
	{
		final var id = itemStockDto.getItemId();
		return itemStockRepository.existsById(UUID.fromString(id));
	}

	public boolean isItemAvailable(final UUID id)
	{
		final Optional<ItemStock> itemStockOptional = itemStockRepository.findById(id);
		final Integer numberOfAvailableItems = itemStockOptional.map(ItemStock::getAvailableItemCount).orElse(0);
		return numberOfAvailableItems > 0;
	}

	public void increaseAvailableStock(final UUID id)
	{
		adjustStock(id, 1);
	}

	public void reduceAvailableStock(final UUID id)
	{
		adjustStock(id, -1);
	}

	private void adjustStock(final UUID id, final Integer increaseOrDecreaseOfAvailableItems)
	{
		final ItemStock itemStock = itemStockRepository.findById(id)
		                                               .orElseThrow(() -> new BadRequestException(
				                                               "Not items available for given item ID"));
		final var currentAvailableItems = itemStock.getAvailableItemCount();
		if (currentAvailableItems > 0)
		{
			itemStock.setAvailableItemCount(currentAvailableItems + increaseOrDecreaseOfAvailableItems);
		}
		else
		{
			throw new BadRequestException("No item available for the given item ID");
		}
	}

	@Override
	public UUID create(final ItemStockDto dto)
	{
		final ItemStock itemStock = requireNonNull(conversionService.convert(dto, getEntityClass()));

		final Item item = itemRepository.findById(UUID.fromString(dto.getItemId()))
		                                .orElseThrow(() -> new BadRequestException("No item found for given Item ID"));

		itemStock.setItem(item);
		item.setItemStock(itemStock);
		return itemStockRepository.save(itemStock).getId();
	}
}
