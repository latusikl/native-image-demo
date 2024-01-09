package pl.latusikl.demo.app.library.service.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import pl.latusikl.demo.app.library.dto.ItemDto;
import pl.latusikl.demo.app.library.entity.Item;

@Component
public class ItemToItemDtoConverter implements Converter<Item, ItemDto>
{
	@Override
	public ItemDto convert(final Item source)
	{
		return ItemDto.builder()
		              .name(source.getName())
		              .authorName(source.getAuthorName())
		              .authorSurname(source.getAuthorSurname())
		              .publicationYear(source.getPublicationYear())
		              .build();
	}
}
