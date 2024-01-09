package pl.latusikl.demo.app.library.controler;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.latusikl.demo.app.library.dto.ItemStockDto;
import pl.latusikl.demo.app.library.entity.ItemStock;
import pl.latusikl.demo.app.exception.BadRequestException;
import pl.latusikl.demo.app.library.service.ItemStockService;

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
