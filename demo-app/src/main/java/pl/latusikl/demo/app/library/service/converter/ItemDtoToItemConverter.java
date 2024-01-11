package pl.latusikl.demo.app.library.service.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import pl.latusikl.demo.app.library.dto.ItemDto;
import pl.latusikl.demo.app.library.entity.Item;

@Component
public class ItemDtoToItemConverter implements Converter<ItemDto, Item>
{
	@Override
	public Item convert(final ItemDto source)
	{
		final var item = new Item();
		item.setName(source.getName());
		item.setAuthorName(source.getAuthorName());
		item.setAuthorSurname(source.getAuthorSurname());
		item.setPublicationYear(source.getPublicationYear());
		return item;
	}
}