package pl.latusikl.demo.nativeimage.library.controler;

import java.util.UUID;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.latusikl.demo.nativeimage.library.dto.ItemBorrowDto;
import pl.latusikl.demo.nativeimage.library.entity.ItemBorrow;
import pl.latusikl.demo.nativeimage.library.service.ItemBorrowService;

@RestController
@RequestMapping("/borrowings")
public class ItemBorrowController extends AbstractCRUDController<ItemBorrow, ItemBorrowDto>
{
	private final ItemBorrowService itemBorrowService;

	protected ItemBorrowController(final ItemBorrowService itemBorrowService)
	{
		super(itemBorrowService);
		this.itemBorrowService = itemBorrowService;
	}

	@Override
	protected void throwValidationErrorBeforeCreationIfNeeded(final ItemBorrowDto dto)
	{
		itemBorrowService.checkIfBorrowingPossible(UUID.fromString(dto.getItemId()), UUID.fromString(dto.getMemberId()));
	}
}
