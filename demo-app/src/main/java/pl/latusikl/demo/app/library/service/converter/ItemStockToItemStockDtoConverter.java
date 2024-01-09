package pl.latusikl.demo.app.library.service.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import pl.latusikl.demo.app.library.dto.ItemStockDto;
import pl.latusikl.demo.app.library.entity.ItemStock;

@Component
public class ItemStockToItemStockDtoConverter implements Converter<ItemStock, ItemStockDto>
{
	@Override
	public ItemStockDto convert(final ItemStock source)
	{
		final var allItemsCount = source.getItemCount();
		final var availableItemsCount = source.getAvailableItemCount();

		return ItemStockDto.builder()
		                   .itemId(source.getId().toString())
		                   .itemCount(allItemsCount)
		                   .availableItemsCount(availableItemsCount)
		                   .borrowedItemsCount(allItemsCount - availableItemsCount)
		                   .build();
	}
}
