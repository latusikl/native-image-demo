package pl.latusikl.demo.app.library.service.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import pl.latusikl.demo.app.library.dto.ItemBorrowDto;
import pl.latusikl.demo.app.library.entity.ItemBorrow;

@Component
public class ItemBorrowToItemBorrowDtoConverter implements Converter<ItemBorrow, ItemBorrowDto>
{
	@Override
	public ItemBorrowDto convert(final ItemBorrow source)
	{
		return ItemBorrowDto.builder()
		                    .memberId(source.getId().toString())
		                    .itemId(source.getItemId().toString())
		                    .startDate(source.getStartDate())
		                    .endDate(source.getEndDate())
		                    .build();
	}
}
