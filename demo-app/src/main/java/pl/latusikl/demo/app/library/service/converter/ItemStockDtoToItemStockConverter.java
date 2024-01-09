package pl.latusikl.demo.app.library.service.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import pl.latusikl.demo.app.library.dto.ItemStockDto;
import pl.latusikl.demo.app.library.entity.ItemStock;

@Component
public class ItemStockDtoToItemStockConverter implements Converter<ItemStockDto, ItemStock>
{
	@Override
	public ItemStock convert(final ItemStockDto source)
	{
		final var itemStock = new ItemStock();

		itemStock.setItemCount(source.getItemCount());
		//itemStock.setId(UUID.fromString(source.getItemId()));
		itemStock.setAvailableItemCount(source.getItemCount());
		return itemStock;
	}
}
