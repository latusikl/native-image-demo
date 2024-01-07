package pl.latusikl.demo.nativeimage.library.controler;

import org.hibernate.validator.constraints.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.latusikl.demo.nativeimage.library.dto.ItemDto;
import pl.latusikl.demo.nativeimage.library.dto.ItemStockDto;
import pl.latusikl.demo.nativeimage.library.entity.Item;
import pl.latusikl.demo.nativeimage.library.entity.ItemStock;
import pl.latusikl.demo.nativeimage.library.exception.BadRequestException;
import pl.latusikl.demo.nativeimage.library.service.ItemService;
import pl.latusikl.demo.nativeimage.library.service.ItemStockService;

@RestController
@RequestMapping("/stocks")
public class ItemStockController extends AbstractCRUDController<ItemStock, ItemStockDto>
{
	private final ItemStockService itemStockService;

	protected ItemStockController(final ItemStockService itemStockService)
	{
		super(itemStockService);
		this.itemStockService = itemStockService;
	}

	@Override
	protected void throwValidationErrorBeforeCreationIfNeeded(final ItemStockDto dto)
	{
		if (!itemStockService.hasMatchingItem(dto))
		{
			throw new BadRequestException("Item with specified ID not exist");
		}
		if (itemStockService.existForItem(dto))
		{
			throw new BadRequestException("Stock level already exist for item");
		}
	}
}
