package pl.latusikl.demo.nativeimage.library.service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.latusikl.demo.nativeimage.library.dto.ItemBorrowDto;
import pl.latusikl.demo.nativeimage.library.entity.ItemBorrow;
import pl.latusikl.demo.nativeimage.exception.BadRequestException;
import pl.latusikl.demo.nativeimage.library.service.repository.ItemBorrowRepository;

@Service
public class ItemBorrowService extends AbstractEntityService<ItemBorrow, ItemBorrowDto>
{
	private final ConfigurationPropertyService configurationPropertyService;
	private final ItemStockService itemStockService;
	private final ItemService itemService;
	private final MemberService memberService;
	private final ItemBorrowRepository itemBorrowRepository;

	protected ItemBorrowService(final ConversionService conversionService,
	                            ConfigurationPropertyService configurationPropertyService,
	                            ItemBorrowRepository itemBorrowRepository, ItemStockService itemStockService,
	                            ItemService itemService, MemberService memberService)
	{
		super(itemBorrowRepository, conversionService, ItemBorrowDto.class, ItemBorrow.class);
		this.configurationPropertyService = configurationPropertyService;
		this.itemStockService = itemStockService;
		this.itemService = itemService;
		this.memberService = memberService;
		this.itemBorrowRepository = itemBorrowRepository;
	}

	@Override
	@Transactional
	public UUID create(final ItemBorrowDto dto)
	{
		itemStockService.reduceAvailableStock(UUID.fromString(dto.getItemId()));
		return super.create(dto);
	}

	@Override
	protected ItemBorrow adjustBeforeItemCreation(final ItemBorrow entity)
	{
		LocalDate currentDate = LocalDate.now();
		LocalDate endDate = currentDate.plusDays(configurationPropertyService.getDefaultBorrowingLengthDays());
		entity.setStartDate(currentDate);
		entity.setEndDate(endDate);
		return entity;
	}

	@Override
	@Transactional
	public void remove(final UUID id)
	{
		final Optional<ItemBorrow> itemBorrowOptional = itemBorrowRepository.findById(id);
		if (itemBorrowOptional.isPresent())
		{
			final var itemBorrowToRemove = itemBorrowOptional.get();
			itemStockService.increaseAvailableStock(itemBorrowToRemove.getItemId());
			itemBorrowRepository.delete(itemBorrowToRemove);
		}
	}

	public void checkIfBorrowingPossible(UUID itemId, UUID memberId)
	{
		if (!itemService.existById(itemId))
		{
			throw new BadRequestException("No item found for given ID");
		}
		if (!memberService.existById(memberId))
		{
			throw new BadRequestException("No member found for given ID");
		}
		if (!itemStockService.isItemAvailable(itemId))
		{
			throw new BadRequestException("No item instance available for borrowing");
		}
	}

}
