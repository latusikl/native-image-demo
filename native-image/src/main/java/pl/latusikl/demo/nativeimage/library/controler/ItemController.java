package pl.latusikl.demo.nativeimage.library.controler;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.latusikl.demo.nativeimage.library.dto.ItemDto;
import pl.latusikl.demo.nativeimage.library.entity.Item;
import pl.latusikl.demo.nativeimage.library.service.ItemService;

@RestController
@RequestMapping("/items")
public class ItemController extends AbstractCRUDController<Item, ItemDto>
{
	protected ItemController(final ItemService itemService)
	{
		super(itemService);
	}
}
