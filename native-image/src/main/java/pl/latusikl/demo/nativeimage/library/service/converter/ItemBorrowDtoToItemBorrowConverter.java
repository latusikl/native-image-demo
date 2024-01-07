package pl.latusikl.demo.nativeimage.library.service.converter;

import java.util.UUID;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import pl.latusikl.demo.nativeimage.library.dto.ItemBorrowDto;
import pl.latusikl.demo.nativeimage.library.entity.ItemBorrow;

@Component
public class ItemBorrowDtoToItemBorrowConverter implements Converter<ItemBorrowDto, ItemBorrow>
{
	@Override
	public ItemBorrow convert(final ItemBorrowDto source)
	{
		final var itemBorrow = new ItemBorrow();
		itemBorrow.setItemId(UUID.fromString(source.getItemId()));
		itemBorrow.setMemberId(UUID.fromString(source.getMemberId()));
		return itemBorrow;
	}
}
