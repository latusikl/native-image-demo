package pl.latusikl.demo.app.library.controler;

import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.latusikl.demo.app.library.dto.ItemDto;
import pl.latusikl.demo.app.library.entity.Item;
import pl.latusikl.demo.app.library.service.ItemService;

@RestController
@RequestMapping("/items")
@RegisterReflectionForBinding(ItemDto.class)
public class ItemController extends AbstractCRUDController<Item, ItemDto>
{
	protected ItemController(final ItemService itemService)
	{
		super(itemService);
	}
}
